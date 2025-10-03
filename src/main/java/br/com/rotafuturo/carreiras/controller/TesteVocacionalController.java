package br.com.rotafuturo.carreiras.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.rotafuturo.carreiras.service.TesteVocacionalService;
import br.com.rotafuturo.carreiras.service.TesteVocacionalService.EstatisticasTesteVocacional;
import br.com.rotafuturo.carreiras.service.TesteVocacionalService.RecomendacaoCurso;
import br.com.rotafuturo.carreiras.service.TesteVocacionalService.RelatorioTesteVocacional;

/**
 * Controller responsável pelos endpoints do sistema de teste vocacional
 */
@RestController
@RequestMapping("/api/teste-vocacional")
@CrossOrigin(origins = "*")
public class TesteVocacionalController {

    @Autowired
    private TesteVocacionalService testeVocacionalService;

    /**
     * Calcula e retorna as afinidades do usuário por área
     */
    @GetMapping("/afinidades/areas/{usuarioId}")
    public ResponseEntity<Map<String, Double>> getAfinidadesPorArea(@PathVariable Integer usuarioId) {
        try {
            Map<String, Double> afinidades = testeVocacionalService.calcularAfinidadePorArea(usuarioId);
            return ResponseEntity.ok(afinidades);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Calcula e retorna as afinidades do usuário por subárea (cursos)
     */
    @GetMapping("/afinidades/subareas/{usuarioId}")
    public ResponseEntity<Map<String, Double>> getAfinidadesPorSubarea(@PathVariable Integer usuarioId) {
        try {
            Map<String, Double> afinidades = testeVocacionalService.calcularAfinidadePorSubarea(usuarioId);
            return ResponseEntity.ok(afinidades);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Gera recomendações de cursos para o usuário
     */
    @GetMapping("/recomendacoes/{usuarioId}")
    public ResponseEntity<List<RecomendacaoCurso>> getRecomendacoesCursos(
            @PathVariable Integer usuarioId,
            @RequestParam(defaultValue = "5") int limite) {
        try {
            List<RecomendacaoCurso> recomendacoes = testeVocacionalService.gerarRecomendacoesCursos(usuarioId, limite);
            return ResponseEntity.ok(recomendacoes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Gera relatório completo do teste vocacional
     */
    @GetMapping("/relatorio/{usuarioId}")
    public ResponseEntity<RelatorioTesteVocacional> getRelatorioCompleto(@PathVariable Integer usuarioId) {
        try {
            RelatorioTesteVocacional relatorio = testeVocacionalService.gerarRelatorioCompleto(usuarioId);
            return ResponseEntity.ok(relatorio);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Verifica se o usuário completou o teste vocacional
     */
    @GetMapping("/status/{usuarioId}")
    public ResponseEntity<StatusTesteResponse> getStatusTeste(@PathVariable Integer usuarioId) {
        try {
            boolean completo = testeVocacionalService.isTesteCompleto(usuarioId);
            StatusTesteResponse response = new StatusTesteResponse();
            response.setUsuarioId(usuarioId);
            response.setTesteCompleto(completo);
            response.setMensagem(completo ? 
                "Teste vocacional completo! Você pode visualizar seus resultados." : 
                "Complete mais questões para obter um resultado mais preciso.");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Retorna estatísticas gerais dos testes vocacionais (para administradores)
     */
    @GetMapping("/estatisticas")
    public ResponseEntity<EstatisticasTesteVocacional> getEstatisticasGerais() {
        try {
            EstatisticasTesteVocacional stats = testeVocacionalService.getEstatisticasGerais();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Endpoint para resetar/reiniciar o teste de um usuário (para desenvolvimento)
     */
    @DeleteMapping("/reset/{usuarioId}")
    public ResponseEntity<String> resetarTeste(@PathVariable Integer usuarioId) {
        try {
            // Implementar lógica para resetar teste se necessário
            return ResponseEntity.ok("Funcionalidade de reset em desenvolvimento");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Endpoint para obter sugestões de próximos passos baseado no resultado
     */
    @GetMapping("/proximos-passos/{usuarioId}")
    public ResponseEntity<ProximosPassosResponse> getProximosPassos(@PathVariable Integer usuarioId) {
        try {
            ProximosPassosResponse response = new ProximosPassosResponse();
            
            // Verificar se o teste está completo
            boolean testeCompleto = testeVocacionalService.isTesteCompleto(usuarioId);
            
            if (!testeCompleto) {
                response.setSugestoes(List.of(
                    "Complete o teste vocacional respondendo mais questões",
                    "Responda pelo menos as questões gerais e de uma área específica",
                    "Quanto mais questões você responder, mais preciso será o resultado"
                ));
            } else {
                // Obter recomendações e gerar sugestões baseadas nelas
                List<RecomendacaoCurso> recomendacoes = testeVocacionalService.gerarRecomendacoesCursos(usuarioId, 3);
                
                if (!recomendacoes.isEmpty()) {
                    response.setSugestoes(List.of(
                        "Pesquise mais sobre os cursos recomendados: " + 
                            recomendacoes.stream().map(RecomendacaoCurso::getCurso).reduce((a, b) -> a + ", " + b).orElse(""),
                        "Visite universidades que oferecem esses cursos",
                        "Converse com profissionais das áreas indicadas",
                        "Considere fazer cursos preparatórios ou técnicos relacionados",
                        "Explore oportunidades de estágio ou trabalho voluntário nessas áreas"
                    ));
                } else {
                    response.setSugestoes(List.of(
                        "Considere refazer o teste com mais atenção às questões",
                        "Explore diferentes áreas através de cursos online",
                        "Procure orientação vocacional presencial",
                        "Participe de feiras de profissões e universidades"
                    ));
                }
            }
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Classes para DTOs de resposta

    public static class StatusTesteResponse {
        private Integer usuarioId;
        private boolean testeCompleto;
        private String mensagem;

        // Getters e Setters
        public Integer getUsuarioId() { return usuarioId; }
        public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }

        public boolean isTesteCompleto() { return testeCompleto; }
        public void setTesteCompleto(boolean testeCompleto) { this.testeCompleto = testeCompleto; }

        public String getMensagem() { return mensagem; }
        public void setMensagem(String mensagem) { this.mensagem = mensagem; }
    }

    public static class ProximosPassosResponse {
        private List<String> sugestoes;

        // Getters e Setters
        public List<String> getSugestoes() { return sugestoes; }
        public void setSugestoes(List<String> sugestoes) { this.sugestoes = sugestoes; }
    }
}