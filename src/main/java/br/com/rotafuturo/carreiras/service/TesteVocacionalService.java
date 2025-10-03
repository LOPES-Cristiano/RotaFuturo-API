package br.com.rotafuturo.carreiras.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rotafuturo.carreiras.model.AreaSubBean;
import br.com.rotafuturo.carreiras.model.TesteQuestaoBean;
import br.com.rotafuturo.carreiras.model.TesteQuestaoRespondidaBean;
import br.com.rotafuturo.carreiras.repository.AreaRepository;
import br.com.rotafuturo.carreiras.repository.AreaSubRepository;
import br.com.rotafuturo.carreiras.repository.TesteQuestaoRepository;
import br.com.rotafuturo.carreiras.repository.TesteQuestaoRespondidaRepository;
import br.com.rotafuturo.carreiras.repository.TesteRepository;

/**
 * Serviço responsável pelo cálculo de afinidades vocacionais
 * e recomendação de cursos baseado nas respostas do teste vocacional
 */
@Service
public class TesteVocacionalService {

    @Autowired
    private TesteQuestaoRespondidaRepository testeQuestaoRespondidaRepository;
    
    @Autowired
    private TesteQuestaoRepository testeQuestaoRepository;
    
    @Autowired
    private AreaRepository areaRepository;
    
    @Autowired
    private AreaSubRepository areaSubRepository;
    
    @Autowired
    private TesteRepository testeRepository;

    /**
     * Calcula a afinidade do usuário por área baseado nas respostas do teste
     */
    public Map<String, Double> calcularAfinidadePorArea(Integer usuarioId) {
        List<TesteQuestaoRespondidaBean> respostas = testeQuestaoRespondidaRepository.findByUsuario_UsuId(usuarioId);
        
        Map<String, List<Integer>> respostasPorArea = new HashMap<>();
        
        for (TesteQuestaoRespondidaBean resposta : respostas) {
            if (resposta.getTesteQuestaoVinculo() != null && 
                resposta.getTesteQuestaoVinculo().getTesteQuestao() != null &&
                resposta.getTesteQuestaoVinculo().getTesteQuestao().getArea() != null) {
                
                String areaDescricao = resposta.getTesteQuestaoVinculo().getTesteQuestao().getArea().getAreaDescricao();
                Integer valorResposta = resposta.getTesqrResposta();
                
                respostasPorArea.computeIfAbsent(areaDescricao, k -> new ArrayList<>()).add(valorResposta);
            }
        }
        
        Map<String, Double> afinidadePorArea = new HashMap<>();
        
        for (Map.Entry<String, List<Integer>> entry : respostasPorArea.entrySet()) {
            String area = entry.getKey();
            List<Integer> valores = entry.getValue();
            
            double media = valores.stream().mapToInt(Integer::intValue).average().orElse(0.0);
            double percentual = (media - 1) * 25; // Converte escala 1-5 para 0-100%
            
            afinidadePorArea.put(area, Math.max(0, Math.min(100, percentual)));
        }
        
        return afinidadePorArea;
    }

    /**
     * Calcula a afinidade do usuário por subárea (cursos) com peso diferenciado
     */
    public Map<String, Double> calcularAfinidadePorSubarea(Integer usuarioId) {
        List<TesteQuestaoRespondidaBean> respostas = testeQuestaoRespondidaRepository.findByUsuario_UsuId(usuarioId);
        
        Map<String, List<RespostaComPeso>> respostasPorSubarea = new HashMap<>();
        
        for (TesteQuestaoRespondidaBean resposta : respostas) {
            if (resposta.getTesteQuestaoVinculo() != null && 
                resposta.getTesteQuestaoVinculo().getTesteQuestao() != null) {
                
                TesteQuestaoBean questao = resposta.getTesteQuestaoVinculo().getTesteQuestao();
                Integer valorResposta = resposta.getTesqrResposta();
                
                // Se a questão tem subárea específica
                if (questao.getAreaSub() != null) {
                    String subareaDescricao = questao.getAreaSub().getAreasDescricao();
                    // Peso 2 para questões específicas da subárea
                    respostasPorSubarea.computeIfAbsent(subareaDescricao, k -> new ArrayList<>())
                                     .add(new RespostaComPeso(valorResposta, 2));
                }
                // Se a questão é da área geral
                else if (questao.getArea() != null) {
                    String areaDescricao = questao.getArea().getAreaDescricao();
                    
                    // Aplicar peso 1 para todas as subáreas desta área
                    List<AreaSubBean> subareasArea = areaSubRepository.findByArea_AreaId(questao.getArea().getAreaId());
                    for (AreaSubBean subarea : subareasArea) {
                        respostasPorSubarea.computeIfAbsent(subarea.getAreasDescricao(), k -> new ArrayList<>())
                                         .add(new RespostaComPeso(valorResposta, 1));
                    }
                }
            }
        }
        
        Map<String, Double> afinidadePorSubarea = new HashMap<>();
        
        for (Map.Entry<String, List<RespostaComPeso>> entry : respostasPorSubarea.entrySet()) {
            String subarea = entry.getKey();
            List<RespostaComPeso> respostasComPeso = entry.getValue();
            
            double somaRespostas = respostasComPeso.stream()
                .mapToDouble(r -> r.valor * r.peso)
                .sum();
            double somaPesos = respostasComPeso.stream()
                .mapToDouble(r -> r.peso)
                .sum();
            
            if (somaPesos > 0) {
                double mediaPonderada = somaRespostas / somaPesos;
                double percentual = (mediaPonderada - 1) * 25; // Converte escala 1-5 para 0-100%
                
                afinidadePorSubarea.put(subarea, Math.max(0, Math.min(100, percentual)));
            }
        }
        
        return afinidadePorSubarea;
    }

    /**
     * Gera recomendações de cursos baseado nas afinidades calculadas
     */
    public List<RecomendacaoCurso> gerarRecomendacoesCursos(Integer usuarioId, int limite) {
        Map<String, Double> afinidades = calcularAfinidadePorSubarea(usuarioId);
        
        return afinidades.entrySet().stream()
            .filter(entry -> entry.getValue() >= 70.0) // Filtro de afinidade mínima
            .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
            .limit(limite)
            .map(entry -> {
                RecomendacaoCurso recomendacao = new RecomendacaoCurso();
                recomendacao.setCurso(entry.getKey());
                recomendacao.setPercentualAfinidade(entry.getValue());
                recomendacao.setNivelRecomendacao(getNivelRecomendacao(entry.getValue()));
                recomendacao.setMotivo(gerarMotivoRecomendacao(entry.getValue()));
                return recomendacao;
            })
            .collect(Collectors.toList());
    }

    /**
     * Gera relatório completo do teste vocacional para o usuário
     */
    public RelatorioTesteVocacional gerarRelatorioCompleto(Integer usuarioId) {
        RelatorioTesteVocacional relatorio = new RelatorioTesteVocacional();
        
        relatorio.setUsuarioId(usuarioId);
        relatorio.setDataRelatorio(java.time.LocalDateTime.now());
        relatorio.setAfinidadesPorArea(calcularAfinidadePorArea(usuarioId));
        relatorio.setAfinidadesPorSubarea(calcularAfinidadePorSubarea(usuarioId));
        relatorio.setRecomendacoesCursos(gerarRecomendacoesCursos(usuarioId, 5));
        relatorio.setTotalQuestoesRespondidas(contarQuestoesRespondidas(usuarioId));
        relatorio.setAreaMaiorAfinidade(getAreaMaiorAfinidade(relatorio.getAfinidadesPorArea()));
        
        return relatorio;
    }

    /**
     * Verifica se o usuário completou o teste vocacional
     */
    public boolean isTesteCompleto(Integer usuarioId) {
        int totalRespostas = testeQuestaoRespondidaRepository.findByUsuario_UsuId(usuarioId).size();
        // Considera completo se respondeu pelo menos 50 questões (geral + pelo menos 1 área específica)
        return totalRespostas >= 50;
    }

    /**
     * Obtém estatísticas gerais dos testes vocacionais
     */
    public EstatisticasTesteVocacional getEstatisticasGerais() {
        EstatisticasTesteVocacional stats = new EstatisticasTesteVocacional();
        
        // Implementar consultas para estatísticas
        // stats.setTotalUsuariosTestados(...);
        // stats.setAreaMaisEscolhida(...);
        // stats.setCursoMaisIndicado(...);
        
        return stats;
    }

    // Métodos auxiliares privados
    
    private String getNivelRecomendacao(double percentual) {
        if (percentual >= 90) return "EXCELENTE";
        if (percentual >= 80) return "MUITO_BOM";
        if (percentual >= 70) return "BOM";
        return "REGULAR";
    }
    
    private String gerarMotivoRecomendacao(double percentual) {
        if (percentual >= 90) {
            return "Suas respostas indicam uma afinidade excelente com esta área. " +
                   "Este curso se alinha perfeitamente com seus interesses e habilidades.";
        } else if (percentual >= 80) {
            return "Você demonstra muito interesse e aptidão para esta área. " +
                   "Este curso seria uma excelente escolha para seu futuro profissional.";
        } else if (percentual >= 70) {
            return "Suas respostas mostram boa afinidade com esta área. " +
                   "Vale a pena considerar este curso entre suas opções.";
        }
        return "Você mostra algum interesse por esta área, mas pode querer explorar outras opções também.";
    }
    
    private int contarQuestoesRespondidas(Integer usuarioId) {
        return testeQuestaoRespondidaRepository.findByUsuario_UsuId(usuarioId).size();
    }
    
    private String getAreaMaiorAfinidade(Map<String, Double> afinidades) {
        return afinidades.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("Não identificada");
    }

    // Classes auxiliares
    
    private static class RespostaComPeso {
        int valor;
        int peso;
        
        RespostaComPeso(int valor, int peso) {
            this.valor = valor;
            this.peso = peso;
        }
    }

    // Classes para DTOs de resposta
    
    public static class RecomendacaoCurso {
        private String curso;
        private Double percentualAfinidade;
        private String nivelRecomendacao;
        private String motivo;
        
        // Getters e Setters
        public String getCurso() { return curso; }
        public void setCurso(String curso) { this.curso = curso; }
        
        public Double getPercentualAfinidade() { return percentualAfinidade; }
        public void setPercentualAfinidade(Double percentualAfinidade) { this.percentualAfinidade = percentualAfinidade; }
        
        public String getNivelRecomendacao() { return nivelRecomendacao; }
        public void setNivelRecomendacao(String nivelRecomendacao) { this.nivelRecomendacao = nivelRecomendacao; }
        
        public String getMotivo() { return motivo; }
        public void setMotivo(String motivo) { this.motivo = motivo; }
    }

    public static class RelatorioTesteVocacional {
        private Integer usuarioId;
        private java.time.LocalDateTime dataRelatorio;
        private Map<String, Double> afinidadesPorArea;
        private Map<String, Double> afinidadesPorSubarea;
        private List<RecomendacaoCurso> recomendacoesCursos;
        private int totalQuestoesRespondidas;
        private String areaMaiorAfinidade;
        
        // Getters e Setters
        public Integer getUsuarioId() { return usuarioId; }
        public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }
        
        public java.time.LocalDateTime getDataRelatorio() { return dataRelatorio; }
        public void setDataRelatorio(java.time.LocalDateTime dataRelatorio) { this.dataRelatorio = dataRelatorio; }
        
        public Map<String, Double> getAfinidadesPorArea() { return afinidadesPorArea; }
        public void setAfinidadesPorArea(Map<String, Double> afinidadesPorArea) { this.afinidadesPorArea = afinidadesPorArea; }
        
        public Map<String, Double> getAfinidadesPorSubarea() { return afinidadesPorSubarea; }
        public void setAfinidadesPorSubarea(Map<String, Double> afinidadesPorSubarea) { this.afinidadesPorSubarea = afinidadesPorSubarea; }
        
        public List<RecomendacaoCurso> getRecomendacoesCursos() { return recomendacoesCursos; }
        public void setRecomendacoesCursos(List<RecomendacaoCurso> recomendacoesCursos) { this.recomendacoesCursos = recomendacoesCursos; }
        
        public int getTotalQuestoesRespondidas() { return totalQuestoesRespondidas; }
        public void setTotalQuestoesRespondidas(int totalQuestoesRespondidas) { this.totalQuestoesRespondidas = totalQuestoesRespondidas; }
        
        public String getAreaMaiorAfinidade() { return areaMaiorAfinidade; }
        public void setAreaMaiorAfinidade(String areaMaiorAfinidade) { this.areaMaiorAfinidade = areaMaiorAfinidade; }
    }

    public static class EstatisticasTesteVocacional {
        private long totalUsuariosTestados;
        private String areaMaisEscolhida;
        private String cursoMaisIndicado;
        private double mediaTempoTeste;
        
        // Getters e Setters
        public long getTotalUsuariosTestados() { return totalUsuariosTestados; }
        public void setTotalUsuariosTestados(long totalUsuariosTestados) { this.totalUsuariosTestados = totalUsuariosTestados; }
        
        public String getAreaMaisEscolhida() { return areaMaisEscolhida; }
        public void setAreaMaisEscolhida(String areaMaisEscolhida) { this.areaMaisEscolhida = areaMaisEscolhida; }
        
        public String getCursoMaisIndicado() { return cursoMaisIndicado; }
        public void setCursoMaisIndicado(String cursoMaisIndicado) { this.cursoMaisIndicado = cursoMaisIndicado; }
        
        public double getMediaTempoTeste() { return mediaTempoTeste; }
        public void setMediaTempoTeste(double mediaTempoTeste) { this.mediaTempoTeste = mediaTempoTeste; }
    }
}