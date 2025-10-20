package br.com.rotafuturo.carreiras.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rotafuturo.carreiras.model.AreaBean;
import br.com.rotafuturo.carreiras.model.AreaSubBean;
import br.com.rotafuturo.carreiras.model.TesteBean;
import br.com.rotafuturo.carreiras.model.TesteQuestaoBean;
import br.com.rotafuturo.carreiras.model.TesteQuestaoRespondidaBean;
import br.com.rotafuturo.carreiras.model.UsuarioAreaBean;
import br.com.rotafuturo.carreiras.model.UsuarioBean;
import br.com.rotafuturo.carreiras.repository.AreaRepository;
import br.com.rotafuturo.carreiras.repository.AreaSubRepository;
import br.com.rotafuturo.carreiras.repository.TesteQuestaoRespondidaRepository;
import br.com.rotafuturo.carreiras.repository.TesteRepository;
import br.com.rotafuturo.carreiras.repository.UsuarioAreaRepository;
import br.com.rotafuturo.carreiras.repository.UsuarioRepository;

/**
 * Serviço para gerenciar o fluxo completo dos testes vocacionais.
 * 
 * Fluxo:
 * 1. Teste Vocacional (ENEM) - Define ÁREA
 *    - Teste: AREA_ID = NULL, AREAS_ID = NULL
 *    - Questões: AREA_ID = NULL, AREAS_ID != NULL
 *    - Resultado: USUARIOAREA.AREA_ID
 * 
 * 2. Teste de Afinidade - Define SUBÁREA (curso)
 *    - Teste: AREA_ID != NULL, AREAS_ID = NULL
 *    - Questões: AREA_ID != NULL, AREAS_ID != NULL
 *    - Resultado: USUARIOAREA.AREAS_ID
 */
@Service
public class TesteFluxoService {

    @Autowired
    private TesteRepository testeRepository;

    @Autowired
    private TesteQuestaoRespondidaRepository testeQuestaoRespondidaRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private AreaSubRepository areaSubRepository;

    @Autowired
    private UsuarioAreaRepository usuarioAreaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Busca o teste vocacional (ENEM) - sem área definida.
     * Questões deste teste têm apenas AREAS_ID.
     */
    public TesteBean buscarTesteVocacional() {
        List<TesteBean> testes = testeRepository.findVocationalTests();
        
        if (testes.isEmpty()) {
            throw new RuntimeException("Teste vocacional não encontrado");
        }
        
        return testes.get(0);
    }

    /**
     * Busca o teste de afinidade para uma área específica.
     * Questões deste teste têm AREA_ID e AREAS_ID.
     */
    public TesteBean buscarTesteAfinidadePorArea(Integer areaId) {
        List<TesteBean> testes = testeRepository.findByAreaId(areaId);
        
        if (testes.isEmpty()) {
            throw new RuntimeException("Teste de afinidade não encontrado para área: " + areaId);
        }
        
        // Filtrar apenas testes sem subárea definida (testes de área completos)
        return testes.stream()
            .filter(t -> t.getAreaSub() == null)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Teste de afinidade não encontrado para área: " + areaId));
    }

    /**
     * Calcula a área com maior afinidade baseado nas respostas do teste vocacional.
     * As questões têm AREAS_ID mas não AREA_ID.
     * 
     * @param usuarioId ID do usuário
     * @param testeId ID do teste vocacional
     * @return ID da área com maior afinidade
     */
    @Transactional
    public Integer calcularAreaTesteVocacional(Integer usuarioId, Integer testeId) {
        // 1. Buscar todas as respostas do usuário para este teste
        List<TesteQuestaoRespondidaBean> respostas = 
            testeQuestaoRespondidaRepository.findMostRecentResponsesByTesteAndUsuario(testeId, usuarioId);
        
        if (respostas.isEmpty()) {
            throw new RuntimeException("Nenhuma resposta encontrada para o teste vocacional");
        }
        
        // 2. Agrupar por subárea e calcular pontuação
        Map<Integer, List<Integer>> respostasPorSubarea = new HashMap<>();
        
        for (TesteQuestaoRespondidaBean resposta : respostas) {
            if (resposta.getTesteQuestaoVinculo() == null || 
                resposta.getTesteQuestaoVinculo().getTesteQuestao() == null) {
                continue;
            }
            
            TesteQuestaoBean questao = resposta.getTesteQuestaoVinculo().getTesteQuestao();
            
            // Questões do teste vocacional têm apenas AREAS_ID (sem AREA_ID)
            if (questao.getAreaSub() != null && questao.getArea() == null) {
                Integer subareaId = questao.getAreaSub().getAreasId();
                Integer pontos = resposta.getTesqrResposta();
                
                respostasPorSubarea
                    .computeIfAbsent(subareaId, k -> new ArrayList<>())
                    .add(pontos);
            }
        }
        
        // 3. Calcular média por subárea
        Map<Integer, Double> mediaPorSubarea = new HashMap<>();
        for (Map.Entry<Integer, List<Integer>> entry : respostasPorSubarea.entrySet()) {
            double media = entry.getValue().stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
            mediaPorSubarea.put(entry.getKey(), media);
        }
        
        // 4. Agrupar subáreas por área e calcular pontuação da área
        Map<Integer, Double> pontuacaoPorArea = new HashMap<>();
        Map<Integer, Integer> contagemPorArea = new HashMap<>();
        
        for (Map.Entry<Integer, Double> entry : mediaPorSubarea.entrySet()) {
            Integer subareaId = entry.getKey();
            Double mediaSubarea = entry.getValue();
            
            // Buscar a área da subárea
            Optional<AreaSubBean> subareaOpt = areaSubRepository.findById(subareaId);
            if (subareaOpt.isPresent() && subareaOpt.get().getArea() != null) {
                Integer areaId = subareaOpt.get().getArea().getAreaId();
                
                pontuacaoPorArea.merge(areaId, mediaSubarea, Double::sum);
                contagemPorArea.merge(areaId, 1, Integer::sum);
            }
        }
        
        // 5. Calcular média final por área
        Map<Integer, Double> mediaFinalPorArea = new HashMap<>();
        for (Map.Entry<Integer, Double> entry : pontuacaoPorArea.entrySet()) {
            Integer areaId = entry.getKey();
            Double somaMedias = entry.getValue();
            Integer contagem = contagemPorArea.get(areaId);
            
            mediaFinalPorArea.put(areaId, somaMedias / contagem);
        }
        
        // 6. Retornar a área com maior pontuação média
        return mediaFinalPorArea.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElseThrow(() -> new RuntimeException("Não foi possível calcular a área"));
    }

    /**
     * Salva a área escolhida pelo usuário após o teste vocacional.
     * 
     * @param usuarioId ID do usuário
     * @param areaId ID da área escolhida
     */
    @Transactional
    public void salvarAreaUsuario(Integer usuarioId, Integer areaId) {
        // Buscar ou criar UsuarioAreaBean
        UsuarioBean usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        AreaBean area = areaRepository.findById(areaId)
            .orElseThrow(() -> new RuntimeException("Área não encontrada"));
        
        // Remover vínculo anterior se existir
        usuarioAreaRepository.findByUsuario_UsuId(usuarioId)
            .ifPresent(usuarioAreaRepository::delete);
        
        // Criar novo vínculo
        UsuarioAreaBean usuarioArea = new UsuarioAreaBean();
        usuarioArea.setUsuario(usuario);
        usuarioArea.setArea(area);
        usuarioArea.setAreaSub(null); // Ainda não definiu subárea
        usuarioArea.setUsuaDatacadastro(LocalDate.now());
        usuarioArea.setUsuaHoracadastro(LocalTime.now());
        
        usuarioAreaRepository.save(usuarioArea);
        
        System.out.println("Área " + area.getAreaDescricao() + " vinculada ao usuário " + usuarioId);
    }

    /**
     * Calcula a subárea (curso) com maior afinidade dentro da área escolhida.
     * As questões têm AREA_ID e AREAS_ID.
     * 
     * @param usuarioId ID do usuário
     * @param testeId ID do teste de afinidade
     * @param areaId ID da área (para validação)
     * @return ID da subárea com maior afinidade
     */
    @Transactional
    public Integer calcularSubareaTesteAfinidade(Integer usuarioId, Integer testeId, Integer areaId) {
        // 1. Buscar todas as respostas do usuário para este teste
        List<TesteQuestaoRespondidaBean> respostas = 
            testeQuestaoRespondidaRepository.findMostRecentResponsesByTesteAndUsuario(testeId, usuarioId);
        
        if (respostas.isEmpty()) {
            throw new RuntimeException("Nenhuma resposta encontrada para o teste de afinidade");
        }
        
        // 2. Agrupar por subárea e calcular pontuação
        Map<Integer, List<Integer>> respostasPorSubarea = new HashMap<>();
        
        for (TesteQuestaoRespondidaBean resposta : respostas) {
            if (resposta.getTesteQuestaoVinculo() == null || 
                resposta.getTesteQuestaoVinculo().getTesteQuestao() == null) {
                continue;
            }
            
            TesteQuestaoBean questao = resposta.getTesteQuestaoVinculo().getTesteQuestao();
            
            // Questões do teste de afinidade têm AREA_ID e AREAS_ID
            if (questao.getArea() != null && 
                questao.getArea().getAreaId().equals(areaId) && 
                questao.getAreaSub() != null) {
                
                Integer subareaId = questao.getAreaSub().getAreasId();
                Integer pontos = resposta.getTesqrResposta();
                
                respostasPorSubarea
                    .computeIfAbsent(subareaId, k -> new ArrayList<>())
                    .add(pontos);
            }
        }
        
        // 3. Calcular média por subárea
        Map<Integer, Double> mediaPorSubarea = new HashMap<>();
        for (Map.Entry<Integer, List<Integer>> entry : respostasPorSubarea.entrySet()) {
            double media = entry.getValue().stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
            mediaPorSubarea.put(entry.getKey(), media);
        }
        
        // 4. Retornar a subárea com maior pontuação
        return mediaPorSubarea.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElseThrow(() -> new RuntimeException("Não foi possível calcular a subárea"));
    }

    /**
     * Salva a subárea (curso) escolhida pelo usuário após o teste de afinidade.
     * 
     * @param usuarioId ID do usuário
     * @param subareaId ID da subárea escolhida
     */
    @Transactional
    public void salvarSubareaUsuario(Integer usuarioId, Integer subareaId) {
        // Buscar vínculo existente
        UsuarioAreaBean usuarioArea = usuarioAreaRepository.findByUsuario_UsuId(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuário não possui área definida. Execute o teste vocacional primeiro."));
        
        // Buscar subárea
        AreaSubBean areaSub = areaSubRepository.findById(subareaId)
            .orElseThrow(() -> new RuntimeException("Subárea não encontrada"));
        
        // Validar se a subárea pertence à área do usuário
        if (!areaSub.getArea().getAreaId().equals(usuarioArea.getArea().getAreaId())) {
            throw new RuntimeException("Subárea não pertence à área do usuário");
        }
        
        // Atualizar subárea
        usuarioArea.setAreaSub(areaSub);
        usuarioArea.setUsuaDatacadastro(LocalDate.now());
        usuarioArea.setUsuaHoracadastro(LocalTime.now());
        
        usuarioAreaRepository.save(usuarioArea);
        
        System.out.println("Subárea " + areaSub.getAreasDescricao() + " vinculada ao usuário " + usuarioId);
    }

    /**
     * Verifica se o usuário completou o teste vocacional.
     */
    public boolean usuarioCompletoTesteVocacional(Integer usuarioId) {
        Optional<UsuarioAreaBean> usuarioAreaOpt = usuarioAreaRepository.findByUsuario_UsuId(usuarioId);
        return usuarioAreaOpt.isPresent() && usuarioAreaOpt.get().getArea() != null;
    }

    /**
     * Verifica se o usuário completou o teste de afinidade.
     */
    public boolean usuarioCompletoTesteAfinidade(Integer usuarioId) {
        Optional<UsuarioAreaBean> usuarioAreaOpt = usuarioAreaRepository.findByUsuario_UsuId(usuarioId);
        return usuarioAreaOpt.isPresent() && 
               usuarioAreaOpt.get().getArea() != null && 
               usuarioAreaOpt.get().getAreaSub() != null;
    }

    /**
     * Retorna o status do fluxo de testes do usuário.
     */
    public StatusFluxoTeste getStatusFluxo(Integer usuarioId) {
        StatusFluxoTeste status = new StatusFluxoTeste();
        status.setUsuarioId(usuarioId);
        
        Optional<UsuarioAreaBean> usuarioAreaOpt = usuarioAreaRepository.findByUsuario_UsuId(usuarioId);
        
        if (usuarioAreaOpt.isEmpty()) {
            status.setEtapaAtual("TESTE_VOCACIONAL");
            status.setTesteVocacionalCompleto(false);
            status.setTesteAfinidadeCompleto(false);
            return status;
        }
        
        UsuarioAreaBean usuarioArea = usuarioAreaOpt.get();
        
        if (usuarioArea.getArea() == null) {
            status.setEtapaAtual("TESTE_VOCACIONAL");
            status.setTesteVocacionalCompleto(false);
            status.setTesteAfinidadeCompleto(false);
        } else if (usuarioArea.getAreaSub() == null) {
            status.setEtapaAtual("TESTE_AFINIDADE");
            status.setTesteVocacionalCompleto(true);
            status.setTesteAfinidadeCompleto(false);
            status.setAreaId(usuarioArea.getArea().getAreaId());
            status.setAreaDescricao(usuarioArea.getArea().getAreaDescricao());
        } else {
            status.setEtapaAtual("COMPLETO");
            status.setTesteVocacionalCompleto(true);
            status.setTesteAfinidadeCompleto(true);
            status.setAreaId(usuarioArea.getArea().getAreaId());
            status.setAreaDescricao(usuarioArea.getArea().getAreaDescricao());
            status.setSubareaId(usuarioArea.getAreaSub().getAreasId());
            status.setSubareaDescricao(usuarioArea.getAreaSub().getAreasDescricao());
        }
        
        return status;
    }

    // Classe para retorno do status
    public static class StatusFluxoTeste {
        private Integer usuarioId;
        private String etapaAtual; // TESTE_VOCACIONAL, TESTE_AFINIDADE, COMPLETO
        private boolean testeVocacionalCompleto;
        private boolean testeAfinidadeCompleto;
        private Integer areaId;
        private String areaDescricao;
        private Integer subareaId;
        private String subareaDescricao;

        // Getters e Setters
        public Integer getUsuarioId() { return usuarioId; }
        public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }

        public String getEtapaAtual() { return etapaAtual; }
        public void setEtapaAtual(String etapaAtual) { this.etapaAtual = etapaAtual; }

        public boolean isTesteVocacionalCompleto() { return testeVocacionalCompleto; }
        public void setTesteVocacionalCompleto(boolean testeVocacionalCompleto) { 
            this.testeVocacionalCompleto = testeVocacionalCompleto; 
        }

        public boolean isTesteAfinidadeCompleto() { return testeAfinidadeCompleto; }
        public void setTesteAfinidadeCompleto(boolean testeAfinidadeCompleto) { 
            this.testeAfinidadeCompleto = testeAfinidadeCompleto; 
        }

        public Integer getAreaId() { return areaId; }
        public void setAreaId(Integer areaId) { this.areaId = areaId; }

        public String getAreaDescricao() { return areaDescricao; }
        public void setAreaDescricao(String areaDescricao) { this.areaDescricao = areaDescricao; }

        public Integer getSubareaId() { return subareaId; }
        public void setSubareaId(Integer subareaId) { this.subareaId = subareaId; }

        public String getSubareaDescricao() { return subareaDescricao; }
        public void setSubareaDescricao(String subareaDescricao) { this.subareaDescricao = subareaDescricao; }
    }
}
