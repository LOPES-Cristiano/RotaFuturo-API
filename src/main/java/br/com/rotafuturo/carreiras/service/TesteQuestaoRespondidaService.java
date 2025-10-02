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
import br.com.rotafuturo.carreiras.dto.TesteResultadoDTO;
import br.com.rotafuturo.carreiras.dto.TesteSubareaResultadoDTO;
import br.com.rotafuturo.carreiras.model.AreaBean;
import br.com.rotafuturo.carreiras.model.AreaSubBean;
import br.com.rotafuturo.carreiras.model.TesteQuestaoBean;
import br.com.rotafuturo.carreiras.model.TesteQuestaoRespondidaBean;
import br.com.rotafuturo.carreiras.model.TesteQuestaoVinculoBean;
import br.com.rotafuturo.carreiras.model.UsuarioAreaBean;
import br.com.rotafuturo.carreiras.model.UsuarioBean;
import br.com.rotafuturo.carreiras.repository.AreaRepository;
import br.com.rotafuturo.carreiras.repository.AreaSubRepository;
import br.com.rotafuturo.carreiras.repository.TesteQuestaoRespondidaRepository;
import br.com.rotafuturo.carreiras.repository.TesteQuestaoVinculoRepository;
import br.com.rotafuturo.carreiras.repository.UsuarioAreaRepository;
import br.com.rotafuturo.carreiras.repository.UsuarioRepository;
@Service
public class TesteQuestaoRespondidaService {
    @Autowired
    private TesteQuestaoRespondidaRepository testeQuestaoRespondidaRepository;
    @Autowired
    private TesteQuestaoVinculoRepository testeQuestaoVinculoRepository;
    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private AreaSubRepository areaSubRepository;
    @Autowired
    private UsuarioAreaRepository usuarioAreaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    public List<TesteQuestaoRespondidaBean> findAll() {
        return testeQuestaoRespondidaRepository.findAll();
    }
    public TesteQuestaoRespondidaBean save(TesteQuestaoRespondidaBean testeQuestaoRespondida) {
        if (testeQuestaoRespondida.getUsuario() != null &&
            testeQuestaoRespondida.getTesqrResposta() != null) {
            Integer vinculoId = testeQuestaoRespondida.getTesteQuestaoVinculoId();
            if (vinculoId != null && vinculoId > 0) {
                var vinculoOptional = testeQuestaoVinculoRepository.findById(vinculoId);
                if (vinculoOptional.isPresent()) {
                    var vinculo = vinculoOptional.get();
                    testeQuestaoRespondida.setTesteQuestaoVinculo(vinculo);
                    if (testeQuestaoRespondida.getUsuario() != null) {
                        Integer usuarioId = testeQuestaoRespondida.getUsuario().getUsuId();
                        Optional<TesteQuestaoRespondidaBean> existingResponseOpt = 
                            testeQuestaoRespondidaRepository.findByTesteQuestaoVinculo_TesqvIdAndUsuario_UsuId(
                                vinculoId, usuarioId);
                        if (existingResponseOpt.isPresent()) {
                            TesteQuestaoRespondidaBean existingResponse = existingResponseOpt.get();
                            System.out.println("Atualizando resposta existente ID: " + existingResponse.getTesqrId() +
                                " de " + existingResponse.getTesqrResposta() + " para " + testeQuestaoRespondida.getTesqrResposta());
                            existingResponse.setTesqrResposta(testeQuestaoRespondida.getTesqrResposta());
                            existingResponse.setTesqrDatacadastro(LocalDate.now());
                            existingResponse.setTesqrHoracadastro(LocalTime.now());
                            return testeQuestaoRespondidaRepository.save(existingResponse);
                        }
                    }
                }
            }
        }
        if (testeQuestaoRespondida.getTesqrDatacadastro() == null) {
            testeQuestaoRespondida.setTesqrDatacadastro(LocalDate.now());
        }
        if (testeQuestaoRespondida.getTesqrHoracadastro() == null) {
            testeQuestaoRespondida.setTesqrHoracadastro(LocalTime.now());
        }
        System.out.println("Salvando nova resposta com valor: " + testeQuestaoRespondida.getTesqrResposta() + 
            ", usuário: " + (testeQuestaoRespondida.getUsuario() != null ? testeQuestaoRespondida.getUsuario().getUsuId() : "null") + 
            ", vínculo: " + (testeQuestaoRespondida.getTesteQuestaoVinculo() != null ? testeQuestaoRespondida.getTesteQuestaoVinculo().getTesqvId() : "null"));
        return testeQuestaoRespondidaRepository.save(testeQuestaoRespondida);
    }
    public void deleteById(Integer id) {
        testeQuestaoRespondidaRepository.deleteById(id);
    }
    @Transactional
    public List<TesteResultadoDTO> calcularResultadoPorArea(Integer testeId, Integer usuarioId) {
        System.out.println("Calculando resultados para teste ID: " + testeId + " e usuário ID: " + usuarioId);
        List<TesteQuestaoRespondidaBean> respostas = testeQuestaoRespondidaRepository
            .findMostRecentResponsesByTesteAndUsuario(testeId, usuarioId);
        System.out.println("Total de respostas mais recentes encontradas: " + respostas.size());
        for (TesteQuestaoRespondidaBean resposta : respostas) {
            System.out.println("Resposta ID: " + resposta.getTesqrId() + 
                ", Questão: " + (resposta.getTesteQuestaoVinculo() != null ? 
                    resposta.getTesteQuestaoVinculo().getTesqvId() : "null") + 
                ", Valor: " + resposta.getTesqrResposta() + 
                ", Data: " + resposta.getTesqrDatacadastro() + 
                ", Hora: " + resposta.getTesqrHoracadastro());
        }
        List<AreaBean> todasAreas = areaRepository.findAll();
        Map<Integer, Integer> pontuacaoPorArea = new HashMap<>();
        Map<Integer, String> areaDescricoes = new HashMap<>();
        Map<Integer, Integer> contagemQuestoesPorArea = new HashMap<>();
        for (AreaBean area : todasAreas) {
            pontuacaoPorArea.put(area.getAreaId(), 0);
            areaDescricoes.put(area.getAreaId(), area.getAreaDescricao());
            contagemQuestoesPorArea.put(area.getAreaId(), 0);
        }
        for (TesteQuestaoRespondidaBean resposta : respostas) {
            System.out.println("Processando resposta ID: " + resposta.getTesqrId() + 
                ", valor da resposta: " + resposta.getTesqrResposta());
            TesteQuestaoVinculoBean vinculo = resposta.getTesteQuestaoVinculo();
            if (vinculo != null) {
                TesteQuestaoBean questao = vinculo.getTesteQuestao();
                if (questao != null && questao.getArea() != null) {
                    Integer areaId = questao.getArea().getAreaId();
                    String areaDescricao = questao.getArea().getAreaDescricao();
                    System.out.println("  - Área ID: " + areaId + ", Descrição: " + areaDescricao);
                    areaDescricoes.put(areaId, areaDescricao);
                    int pontuacaoAtual = pontuacaoPorArea.getOrDefault(areaId, 0);
                    int novaPontuacao = pontuacaoAtual + resposta.getTesqrResposta();
                    System.out.println("  - Pontuação da área antes: " + pontuacaoAtual + 
                        ", resposta: " + resposta.getTesqrResposta() + 
                        ", nova pontuação: " + novaPontuacao);
                    pontuacaoPorArea.put(areaId, novaPontuacao);
                    int contagemAtual = contagemQuestoesPorArea.getOrDefault(areaId, 0);
                    contagemQuestoesPorArea.put(areaId, contagemAtual + 1);
                    System.out.println("  - Contagem de questões para área: " + (contagemAtual + 1));
                } else {
                    System.out.println("  - AVISO: Questão ou área é null");
                }
            } else {
                System.out.println("  - ERRO: Vínculo da questão é null");
            }
        }
        List<TesteResultadoDTO> resultados = new ArrayList<>();
        if (respostas.isEmpty()) {
            System.out.println("Nenhuma resposta encontrada para teste ID: " + testeId + " e usuário ID: " + usuarioId);
            java.util.Random random = new java.util.Random();
            for (AreaBean area : todasAreas) {
                int pontuacao = 50 + random.nextInt(51);
                resultados.add(new TesteResultadoDTO(
                    area.getAreaId(), 
                    area.getAreaDescricao(),
                    pontuacao));
                System.out.println("Área ID: " + area.getAreaId() + 
                    ", Descrição: " + area.getAreaDescricao() + 
                    ", Pontuação simulada: " + pontuacao + "%");
            }
            resultados.sort((a, b) -> b.getPontuacao().compareTo(a.getPontuacao()));
            if (!resultados.isEmpty()) {
                vincularUsuarioAreaMaisCompativel(usuarioId, resultados.get(0).getAreaId());
            }
            return resultados;
        }
        System.out.println("\nCálculo dos percentuais por área:");
        for (Map.Entry<Integer, Integer> entry : pontuacaoPorArea.entrySet()) {
            Integer areaId = entry.getKey();
            Integer pontuacaoTotal = entry.getValue();
            Integer quantidadeQuestoes = contagemQuestoesPorArea.get(areaId);
            int percentual;
            if (quantidadeQuestoes > 0) {
                int pontuacaoMaxima = quantidadeQuestoes * 5;
                percentual = (pontuacaoTotal * 100) / pontuacaoMaxima;
            } else {
                java.util.Random random = new java.util.Random();
                percentual = 50 + random.nextInt(51);
            }
            System.out.println("Área ID: " + areaId + 
                ", Descrição: " + areaDescricoes.get(areaId) + 
                ", Pontuação total: " + pontuacaoTotal + 
                ", Qtd questões: " + quantidadeQuestoes + 
                ", Percentual calculado: " + percentual + "%");
            resultados.add(new TesteResultadoDTO(
                areaId,
                areaDescricoes.get(areaId),
                percentual
            ));
        }
        resultados.sort((a, b) -> b.getPontuacao().compareTo(a.getPontuacao()));
        System.out.println("\nResultados ordenados por pontuação:");
        for (TesteResultadoDTO resultado : resultados) {
            System.out.println("Área ID: " + resultado.getAreaId() + 
                ", Descrição: " + resultado.getAreaDescricao() + 
                ", Percentual: " + resultado.getPontuacao() + "%");
        }
        if (!resultados.isEmpty()) {
            System.out.println("\nVinculando usuário " + usuarioId + " à área com maior pontuação: " + 
                resultados.get(0).getAreaId() + " - " + resultados.get(0).getAreaDescricao());
            vincularUsuarioAreaMaisCompativel(usuarioId, resultados.get(0).getAreaId());
        }
        return resultados;
    }
    @Transactional
    public void vincularUsuarioAreaMaisCompativel(Integer usuarioId, Integer areaId) {
        if (usuarioId == null || areaId == null) {
            return;
        }
        try {
            UsuarioBean usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            AreaBean area = areaRepository.findById(areaId)
                .orElseThrow(() -> new RuntimeException("Área não encontrada"));
            usuarioAreaRepository.findByUsuario_UsuId(usuarioId)
                .ifPresent(ua -> usuarioAreaRepository.delete(ua));
            UsuarioAreaBean usuarioArea = new UsuarioAreaBean();
            usuarioArea.setUsuario(usuario);
            usuarioArea.setArea(area);
            usuarioArea.setUsuaDatacadastro(LocalDate.now());
            usuarioArea.setUsuaHoracadastro(LocalTime.now());
            usuarioAreaRepository.save(usuarioArea);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao vincular usuário à área: " + e.getMessage(), e);
        }
    }
    public List<TesteSubareaResultadoDTO> calcularResultadoPorSubarea(Integer testeId, Integer usuarioId, Integer areaId) {
        System.out.println("Calculando resultados de subáreas para teste ID: " + testeId + ", usuário ID: " + usuarioId + ", área ID: " + areaId);
        List<TesteSubareaResultadoDTO> resultados = new ArrayList<>();
        try {
            List<AreaSubBean> subareas = areaSubRepository.findAll().stream()
                                         .filter(as -> as.getArea() != null && as.getArea().getAreaId().equals(areaId))
                                         .toList();
            System.out.println("Subáreas encontradas para área " + areaId + ": " + subareas.size());
            for (AreaSubBean subarea : subareas) {
                System.out.println("  - Subárea ID: " + subarea.getAreasId() + ", Descrição: " + subarea.getAreasDescricao());
            }
            if (subareas.isEmpty()) {
                System.out.println("Nenhuma subárea encontrada para a área " + areaId);
                return resultados;
            }
            Map<Integer, TesteSubareaResultadoDTO> pontuacaoPorSubarea = new HashMap<>();
            for (AreaSubBean subarea : subareas) {
                TesteSubareaResultadoDTO dto = new TesteSubareaResultadoDTO();
                dto.areasId = subarea.getAreasId();
                dto.areasDescricao = subarea.getAreasDescricao();
                dto.pontuacao = 0;
                pontuacaoPorSubarea.put(subarea.getAreasId(), dto);
            }
            List<TesteQuestaoVinculoBean> questoesDoTeste = testeQuestaoVinculoRepository.findByTeste_TesIdWithSubareaOfArea(testeId, areaId);
            System.out.println("Questões encontradas para o teste " + testeId + " com subáreas da área " + areaId + ": " + questoesDoTeste.size());
            for (TesteQuestaoVinculoBean vinculo : questoesDoTeste) {
                TesteQuestaoBean questao = vinculo.getTesteQuestao();
                System.out.println("Analisando questão do vínculo " + vinculo.getTesqvId());
                if (questao == null) {
                    System.out.println("  - Questão é nula para o vínculo " + vinculo.getTesqvId());
                    continue;
                }
                System.out.println("  - Questão ID: " + questao.getTesqId() + ", Descrição: " + questao.getTesqDescricao());
                System.out.println("  - Área associada à questão: " + (questao.getArea() != null ? questao.getArea().getAreaId() : "nula"));
                System.out.println("  - Subárea associada à questão: " + (questao.getAreaSub() != null ? questao.getAreaSub().getAreasId() : "nula"));
                Integer subareaId = questao.getAreaSub().getAreasId();
                System.out.println("  - Questão pertence à subárea " + subareaId + " da área " + areaId);
                Optional<TesteQuestaoRespondidaBean> resposta = testeQuestaoRespondidaRepository.findByUsuario_UsuIdAndTesteQuestaoVinculo_TesqvId(
                        usuarioId, vinculo.getTesqvId());
                if (resposta.isPresent()) {
                    Integer valorResposta = resposta.get().getTesqrResposta();
                    System.out.println("  - Usuário respondeu " + valorResposta + " para esta questão");
                    TesteSubareaResultadoDTO dto = pontuacaoPorSubarea.get(subareaId);
                    if (dto != null) {
                        dto.pontuacao += valorResposta;
                        System.out.println("  - Pontuação da subárea " + subareaId + " atualizada para " + dto.pontuacao);
                    } else {
                        System.out.println("  - Subárea " + subareaId + " não encontrada no mapa de pontuações");
                    }
                } else {
                    System.out.println("  - Usuário não respondeu esta questão");
                }
            }
            resultados.addAll(pontuacaoPorSubarea.values());
            resultados.sort((a, b) -> b.pontuacao.compareTo(a.pontuacao));
            System.out.println("Total de resultados: " + resultados.size());
            for (TesteSubareaResultadoDTO dto : resultados) {
                System.out.println("  - Subárea: " + dto.areasId + ", Descrição: " + dto.areasDescricao + ", Pontuação: " + dto.pontuacao);
            }
            if (!resultados.isEmpty()) {
                int maxPontos = resultados.stream().mapToInt(r -> r.pontuacao).max().orElse(0);
                if (maxPontos > 0) {
                    for (TesteSubareaResultadoDTO resultado : resultados) {
                        resultado.pontuacao = (resultado.pontuacao * 100) / maxPontos;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao calcular resultado por subárea: " + e.getMessage());
            System.err.println("Detalhes do erro: " + e);
        }
        return resultados;
    }
    @Transactional
    public void vincularUsuarioSubarea(Integer usuarioId, Integer areaSubId) {
        if (usuarioId == null || areaSubId == null) {
            return;
        }
        try {
            UsuarioBean usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            AreaSubBean areaSub = areaSubRepository.findById(areaSubId)
                .orElseThrow(() -> new RuntimeException("Subárea não encontrada"));
            AreaBean area = areaSub.getArea();
            java.util.Optional<UsuarioAreaBean> existente = usuarioAreaRepository.findByUsuario_UsuId(usuarioId);
            if (existente.isPresent() && existente.get().getAreaSub() != null) {
                usuarioAreaRepository.deleteByUsuario_UsuId(usuarioId);
            }
            UsuarioAreaBean usuarioArea = new UsuarioAreaBean();
            usuarioArea.setUsuario(usuario);
            usuarioArea.setAreaSub(areaSub);
            usuarioArea.setArea(area); 
            usuarioArea.setUsuaDatacadastro(LocalDate.now());
            usuarioArea.setUsuaHoracadastro(LocalTime.now());
            usuarioAreaRepository.save(usuarioArea);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao vincular usuário à subárea: " + e.getMessage(), e);
        }
    }
    @Transactional
    public void vincularUsuarioSubareaMaisCompativel(Integer usuarioId, Integer subareaId) {
        if (usuarioId == null || subareaId == null) {
            throw new RuntimeException("Usuário e subárea são obrigatórios");
        }
        try {
            areaSubRepository.findById(subareaId)
                .orElseThrow(() -> new RuntimeException("Subárea não encontrada"));
            vincularUsuarioSubarea(usuarioId, subareaId);
            System.out.println("Usuário " + usuarioId + " vinculado com sucesso à subárea " + subareaId);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao vincular usuário à subárea: " + e.getMessage(), e);
        }
    }
}
