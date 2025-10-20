package br.com.rotafuturo.carreiras.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rotafuturo.carreiras.model.TesteBean;
import br.com.rotafuturo.carreiras.service.TesteFluxoService;
import br.com.rotafuturo.carreiras.service.TesteFluxoService.StatusFluxoTeste;

@RestController
@RequestMapping("/teste")
public class TesteFluxoController {

    @Autowired
    private TesteFluxoService testeFluxoService;

    /**
     * Retorna o teste vocacional (ENEM).
     * 
     * GET /teste/vocacional
     */
    @GetMapping("/vocacional")
    public ResponseEntity<?> getTesteVocacional() {
        try {
            TesteBean teste = testeFluxoService.buscarTesteVocacional();
            return ResponseEntity.ok(teste);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

    /**
     * Retorna o teste de afinidade para uma área específica.
     * 
     * GET /teste/afinidade/{areaId}
     */
    @GetMapping("/afinidade/{areaId}")
    public ResponseEntity<?> getTesteAfinidade(@PathVariable Integer areaId) {
        try {
            TesteBean teste = testeFluxoService.buscarTesteAfinidadePorArea(areaId);
            return ResponseEntity.ok(teste);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

    /**
     * Calcula e salva a área do usuário baseado nas respostas do teste vocacional.
     * 
     * POST /teste/vocacional/resultado
     * Body: { "usuarioId": 1, "testeId": 1 }
     */
    @PostMapping("/vocacional/resultado")
    public ResponseEntity<?> calcularResultadoVocacional(@RequestBody ResultadoTesteRequest request) {
        try {
            // Calcular área
            Integer areaId = testeFluxoService.calcularAreaTesteVocacional(
                request.getUsuarioId(), 
                request.getTesteId()
            );
            
            // Salvar área do usuário
            testeFluxoService.salvarAreaUsuario(request.getUsuarioId(), areaId);
            
            ResultadoTesteResponse response = new ResultadoTesteResponse();
            response.setUsuarioId(request.getUsuarioId());
            response.setAreaId(areaId);
            response.setMensagem("Área definida com sucesso");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

    /**
     * Calcula e salva a subárea (curso) do usuário baseado nas respostas do teste de afinidade.
     * 
     * POST /teste/afinidade/resultado
     * Body: { "usuarioId": 1, "testeId": 2, "areaId": 1 }
     */
    @PostMapping("/afinidade/resultado")
    public ResponseEntity<?> calcularResultadoAfinidade(@RequestBody ResultadoTesteRequest request) {
        try {
            if (request.getAreaId() == null) {
                return ResponseEntity.badRequest().body("areaId é obrigatório");
            }
            
            // Calcular subárea
            Integer subareaId = testeFluxoService.calcularSubareaTesteAfinidade(
                request.getUsuarioId(), 
                request.getTesteId(),
                request.getAreaId()
            );
            
            // Salvar subárea do usuário
            testeFluxoService.salvarSubareaUsuario(request.getUsuarioId(), subareaId);
            
            ResultadoTesteResponse response = new ResultadoTesteResponse();
            response.setUsuarioId(request.getUsuarioId());
            response.setAreaId(request.getAreaId());
            response.setSubareaId(subareaId);
            response.setMensagem("Subárea definida com sucesso");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

    /**
     * Retorna o status do fluxo de testes do usuário.
     * 
     * GET /teste/status/{usuarioId}
     */
    @GetMapping("/status/{usuarioId}")
    public ResponseEntity<?> getStatusFluxo(@PathVariable Integer usuarioId) {
        try {
            StatusFluxoTeste status = testeFluxoService.getStatusFluxo(usuarioId);
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

    // DTOs
    
    public static class ResultadoTesteRequest {
        private Integer usuarioId;
        private Integer testeId;
        private Integer areaId; // Apenas para teste de afinidade

        public Integer getUsuarioId() {
            return usuarioId;
        }

        public void setUsuarioId(Integer usuarioId) {
            this.usuarioId = usuarioId;
        }

        public Integer getTesteId() {
            return testeId;
        }

        public void setTesteId(Integer testeId) {
            this.testeId = testeId;
        }

        public Integer getAreaId() {
            return areaId;
        }

        public void setAreaId(Integer areaId) {
            this.areaId = areaId;
        }
    }

    public static class ResultadoTesteResponse {
        private Integer usuarioId;
        private Integer areaId;
        private Integer subareaId;
        private String mensagem;

        public Integer getUsuarioId() {
            return usuarioId;
        }

        public void setUsuarioId(Integer usuarioId) {
            this.usuarioId = usuarioId;
        }

        public Integer getAreaId() {
            return areaId;
        }

        public void setAreaId(Integer areaId) {
            this.areaId = areaId;
        }

        public Integer getSubareaId() {
            return subareaId;
        }

        public void setSubareaId(Integer subareaId) {
            this.subareaId = subareaId;
        }

        public String getMensagem() {
            return mensagem;
        }

        public void setMensagem(String mensagem) {
            this.mensagem = mensagem;
        }
    }
}
