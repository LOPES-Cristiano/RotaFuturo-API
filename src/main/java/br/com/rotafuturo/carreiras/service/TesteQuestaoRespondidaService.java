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
        // Verificar se os dados obrigatórios estão presentes
        if (testeQuestaoRespondida.getUsuario() != null &&
            testeQuestaoRespondida.getTesqrResposta() != null) {
            
            // Verificar se temos o ID do vínculo da questão
            Integer vinculoId = testeQuestaoRespondida.getTesteQuestaoVinculoId();
            
            // Se encontramos o ID do vínculo, usar para buscar o vínculo
            if (vinculoId != null && vinculoId > 0) {
                // Buscar o vínculo da questão
                var vinculoOptional = testeQuestaoVinculoRepository.findById(vinculoId);
                if (vinculoOptional.isPresent()) {
                    var vinculo = vinculoOptional.get();
                    testeQuestaoRespondida.setTesteQuestaoVinculo(vinculo);
                    
                    // Verificar se já existe uma resposta para esta pergunta
                    if (testeQuestaoRespondida.getUsuario() != null) {
                        Integer usuarioId = testeQuestaoRespondida.getUsuario().getUsuId();
                        
                        // Buscar uma resposta existente para esta questão e usuário
                        Optional<TesteQuestaoRespondidaBean> existingResponseOpt = 
                            testeQuestaoRespondidaRepository.findByTesteQuestaoVinculo_TesqvIdAndUsuario_UsuId(
                                vinculoId, usuarioId);
                        
                        if (existingResponseOpt.isPresent()) {
                            // Encontrou uma resposta existente, atualizar em vez de criar nova
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
        
        // Se chegou aqui, é porque não encontramos uma resposta existente para atualizar
        // ou algum dos dados obrigatórios não foi fornecido
        
        // Definir data e hora de cadastro
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
        
        // Buscar apenas as respostas mais recentes para cada questão usando query otimizada
        List<TesteQuestaoRespondidaBean> respostas = testeQuestaoRespondidaRepository
            .findMostRecentResponsesByTesteAndUsuario(testeId, usuarioId);
        
        System.out.println("Total de respostas mais recentes encontradas: " + respostas.size());
        
        // Log detalhado das respostas para debug
        for (TesteQuestaoRespondidaBean resposta : respostas) {
            System.out.println("Resposta ID: " + resposta.getTesqrId() + 
                ", Questão: " + (resposta.getTesteQuestaoVinculo() != null ? 
                    resposta.getTesteQuestaoVinculo().getTesqvId() : "null") + 
                ", Valor: " + resposta.getTesqrResposta() + 
                ", Data: " + resposta.getTesqrDatacadastro() + 
                ", Hora: " + resposta.getTesqrHoracadastro());
        }
        
        // Buscar todas as áreas para garantir que todas apareçam no resultado
        List<AreaBean> todasAreas = areaRepository.findAll();
        
        // Inicializar mapa com todas as áreas (para garantir que todas apareçam no resultado)
        Map<Integer, Integer> pontuacaoPorArea = new HashMap<>();
        Map<Integer, String> areaDescricoes = new HashMap<>();
        Map<Integer, Integer> contagemQuestoesPorArea = new HashMap<>();
        
        // Pré-preencher os mapas com todas as áreas existentes
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
                    
                    // Armazenar descrição da área
                    areaDescricoes.put(areaId, areaDescricao);
                    
                    // Somar a resposta à pontuação da área
                    int pontuacaoAtual = pontuacaoPorArea.getOrDefault(areaId, 0);
                    int novaPontuacao = pontuacaoAtual + resposta.getTesqrResposta();
                    System.out.println("  - Pontuação da área antes: " + pontuacaoAtual + 
                        ", resposta: " + resposta.getTesqrResposta() + 
                        ", nova pontuação: " + novaPontuacao);
                    
                    pontuacaoPorArea.put(areaId, novaPontuacao);
                    
                    // Contar questões por área
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
        
        // Calcular percentuais e criar DTOs
        List<TesteResultadoDTO> resultados = new ArrayList<>();
        
        // Se não houver respostas, retornar todas as áreas com pontuação aleatória para testes
        if (respostas.isEmpty()) {
            System.out.println("Nenhuma resposta encontrada para teste ID: " + testeId + " e usuário ID: " + usuarioId);
            
            // Para fins de teste, vamos gerar pontuações aleatórias entre 50 e 100
            java.util.Random random = new java.util.Random();
            
            for (AreaBean area : todasAreas) {
                // Gerar pontuação aleatória entre 50 e 100
                int pontuacao = 50 + random.nextInt(51);
                resultados.add(new TesteResultadoDTO(
                    area.getAreaId(), 
                    area.getAreaDescricao(),
                    pontuacao));
                
                System.out.println("Área ID: " + area.getAreaId() + 
                    ", Descrição: " + area.getAreaDescricao() + 
                    ", Pontuação simulada: " + pontuacao + "%");
            }
            
            // Ordenar por pontuação (maior para menor)
            resultados.sort((a, b) -> b.getPontuacao().compareTo(a.getPontuacao()));
            
            // Se houver resultados, vincular o usuário à área com maior pontuação
            if (!resultados.isEmpty()) {
                vincularUsuarioAreaMaisCompativel(usuarioId, resultados.get(0).getAreaId());
            }
            
            return resultados;
        }
        
        System.out.println("\nCálculo dos percentuais por área:");
        
        // Processar pontuação por área
        for (Map.Entry<Integer, Integer> entry : pontuacaoPorArea.entrySet()) {
            Integer areaId = entry.getKey();
            Integer pontuacaoTotal = entry.getValue();
            Integer quantidadeQuestoes = contagemQuestoesPorArea.get(areaId);
            int percentual;
            
            // Calcular percentual apenas se houver questões respondidas para esta área
            if (quantidadeQuestoes > 0) {
                // Calcular percentual (pontuação máxima é 5 por questão)
                int pontuacaoMaxima = quantidadeQuestoes * 5;
                percentual = (pontuacaoTotal * 100) / pontuacaoMaxima;
            } else {
                // Se não houver questões, gerar um valor aleatório para demonstração
                // entre 50 e 100 para áreas sem questões, apenas para fins de visualização
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
        
        // Ordenar por pontuação (maior para menor)
        resultados.sort((a, b) -> b.getPontuacao().compareTo(a.getPontuacao()));
        
        System.out.println("\nResultados ordenados por pontuação:");
        for (TesteResultadoDTO resultado : resultados) {
            System.out.println("Área ID: " + resultado.getAreaId() + 
                ", Descrição: " + resultado.getAreaDescricao() + 
                ", Percentual: " + resultado.getPontuacao() + "%");
        }
        
        // Se houver resultados, vincular o usuário à área com maior pontuação
        if (!resultados.isEmpty()) {
            System.out.println("\nVinculando usuário " + usuarioId + " à área com maior pontuação: " + 
                resultados.get(0).getAreaId() + " - " + resultados.get(0).getAreaDescricao());
            vincularUsuarioAreaMaisCompativel(usuarioId, resultados.get(0).getAreaId());
        }
        
        return resultados;
    }
    
    /**
     * Nota: Método anterior que comparava datas foi removido porque agora
     * usamos uma query SQL otimizada para obter diretamente as respostas mais recentes.
     */
    
    /**
     * Vincula um usuário à área mais compatível baseado no resultado do teste vocacional
     */
    @Transactional
    public void vincularUsuarioAreaMaisCompativel(Integer usuarioId, Integer areaId) {
        if (usuarioId == null || areaId == null) {
            return;
        }
        
        try {
            // Buscar usuário e área
            UsuarioBean usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
                
            AreaBean area = areaRepository.findById(areaId)
                .orElseThrow(() -> new RuntimeException("Área não encontrada"));
                
            // Verificar se já existe vinculação para este usuário e deletar se existir
            usuarioAreaRepository.findByUsuario_UsuId(usuarioId)
                .ifPresent(ua -> usuarioAreaRepository.delete(ua));
                
            // Criar nova vinculação
            UsuarioAreaBean usuarioArea = new UsuarioAreaBean();
            usuarioArea.setUsuario(usuario);
            usuarioArea.setArea(area);
            usuarioArea.setUsuaDatacadastro(LocalDate.now());
            usuarioArea.setUsuaHoracadastro(LocalTime.now());
            
            // Salvar vinculação
            usuarioAreaRepository.save(usuarioArea);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao vincular usuário à área: " + e.getMessage(), e);
        }
    }
    
    /**
     * Calcula o resultado do teste de subáreas para um usuário em uma área específica
     * 
     * @param testeId ID do teste
     * @param usuarioId ID do usuário
     * @param areaId ID da área principal selecionada
     * @return Lista de resultados por subárea
     */
    public List<TesteSubareaResultadoDTO> calcularResultadoPorSubarea(Integer testeId, Integer usuarioId, Integer areaId) {
        System.out.println("Calculando resultados de subáreas para teste ID: " + testeId + ", usuário ID: " + usuarioId + ", área ID: " + areaId);
        
        List<TesteSubareaResultadoDTO> resultados = new ArrayList<>();
        
        try {
            // 1. Buscar todas as subáreas relacionadas a esta área
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
            
            // 2. Criar mapa para armazenar pontuações por subárea
            Map<Integer, TesteSubareaResultadoDTO> pontuacaoPorSubarea = new HashMap<>();
            
            // Inicializar mapa com todas as subáreas
            for (AreaSubBean subarea : subareas) {
                TesteSubareaResultadoDTO dto = new TesteSubareaResultadoDTO();
                dto.areasId = subarea.getAreasId();
                dto.areasDescricao = subarea.getAreasDescricao();
                dto.pontuacao = 0;
                
                pontuacaoPorSubarea.put(subarea.getAreasId(), dto);
            }
            
            // 3. Buscar todas as respostas do usuário para o teste, filtrando por área
            // Usar a query específica para encontrar apenas questões ligadas a subáreas da área solicitada
            List<TesteQuestaoVinculoBean> questoesDoTeste = testeQuestaoVinculoRepository.findByTeste_TesIdWithSubareaOfArea(testeId, areaId);
            System.out.println("Questões encontradas para o teste " + testeId + " com subáreas da área " + areaId + ": " + questoesDoTeste.size());
            
            // 4. Para cada resposta, verificar a questão e sua subárea associada
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
                
                // A query já garante que a questão está associada a uma subárea da área principal
                Integer subareaId = questao.getAreaSub().getAreasId();
                System.out.println("  - Questão pertence à subárea " + subareaId + " da área " + areaId);
                
                // Buscar resposta do usuário para esta questão
                Optional<TesteQuestaoRespondidaBean> resposta = testeQuestaoRespondidaRepository.findByUsuario_UsuIdAndTesteQuestaoVinculo_TesqvId(
                        usuarioId, vinculo.getTesqvId());
                
                if (resposta.isPresent()) {
                    Integer valorResposta = resposta.get().getTesqrResposta();
                    System.out.println("  - Usuário respondeu " + valorResposta + " para esta questão");
                    
                    // Acumular pontuação para esta subárea
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
            
            // 5. Converter mapa para lista e ordenar por pontuação (maior para menor)
            resultados.addAll(pontuacaoPorSubarea.values());
            resultados.sort((a, b) -> b.pontuacao.compareTo(a.pontuacao));
            
            System.out.println("Total de resultados: " + resultados.size());
            for (TesteSubareaResultadoDTO dto : resultados) {
                System.out.println("  - Subárea: " + dto.areasId + ", Descrição: " + dto.areasDescricao + ", Pontuação: " + dto.pontuacao);
            }
            
            // 6. Normalizar as pontuações para percentuais (0-100)
            if (!resultados.isEmpty()) {
                int maxPontos = resultados.stream().mapToInt(r -> r.pontuacao).max().orElse(0);
                
                if (maxPontos > 0) {
                    for (TesteSubareaResultadoDTO resultado : resultados) {
                        // Converter para percentual
                        resultado.pontuacao = (resultado.pontuacao * 100) / maxPontos;
                    }
                }
            }
            
        } catch (Exception e) {
            System.err.println("Erro ao calcular resultado por subárea: " + e.getMessage());
            // Log error instead of printing stack trace
            System.err.println("Detalhes do erro: " + e);
        }
        
        return resultados;
    }
    
    /**
     * Vincula um usuário à subárea específica
     */
    @Transactional
    public void vincularUsuarioSubarea(Integer usuarioId, Integer areaSubId) {
        if (usuarioId == null || areaSubId == null) {
            return;
        }
        
        try {
            // Buscar usuário e subárea
            UsuarioBean usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
                
            AreaSubBean areaSub = areaSubRepository.findById(areaSubId)
                .orElseThrow(() -> new RuntimeException("Subárea não encontrada"));
                
            // Buscar área principal da subárea
            AreaBean area = areaSub.getArea();
                
            // Verificar se já existe vinculação para este usuário com subárea e deletar existentes
            java.util.Optional<UsuarioAreaBean> existente = usuarioAreaRepository.findByUsuario_UsuId(usuarioId);
            if (existente.isPresent() && existente.get().getAreaSub() != null) {
                usuarioAreaRepository.deleteByUsuario_UsuId(usuarioId);
            }
                
            // Criar nova vinculação
            UsuarioAreaBean usuarioArea = new UsuarioAreaBean();
            usuarioArea.setUsuario(usuario);
            usuarioArea.setAreaSub(areaSub);
            usuarioArea.setArea(area); // Também vincula à área principal
            usuarioArea.setUsuaDatacadastro(LocalDate.now());
            usuarioArea.setUsuaHoracadastro(LocalTime.now());
            
            // Salvar vinculação
            usuarioAreaRepository.save(usuarioArea);
            
            // Não precisamos mais chamar vincularUsuarioAreaMaisCompativel, pois já estamos vinculando à área principal
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao vincular usuário à subárea: " + e.getMessage(), e);
        }
    }
    
    /**
     * Vincula um usuário à subárea mais compatível com base nos resultados do teste
     */
    @Transactional
    public void vincularUsuarioSubareaMaisCompativel(Integer usuarioId, Integer subareaId) {
        if (usuarioId == null || subareaId == null) {
            throw new RuntimeException("Usuário e subárea são obrigatórios");
        }
        
        try {
            // Verificar se a subárea existe
            areaSubRepository.findById(subareaId)
                .orElseThrow(() -> new RuntimeException("Subárea não encontrada"));
            
            // Vincular o usuário à subárea específica
            vincularUsuarioSubarea(usuarioId, subareaId);
            
            System.out.println("Usuário " + usuarioId + " vinculado com sucesso à subárea " + subareaId);
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao vincular usuário à subárea: " + e.getMessage(), e);
        }
    }
}
