package br.com.rotafuturo.carreiras.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.rotafuturo.carreiras.model.PessoaBean;
import br.com.rotafuturo.carreiras.service.NivelXpService;

@RestController
@RequestMapping("/nivelxp")
public class NivelXpController {

    @Autowired
    private NivelXpService nivelXpService;

    /**
     * Adiciona XP ao usuário por uma questão respondida corretamente.
     * 
     * Exemplo: POST /nivelxp/adicionar?usuarioId=1&questaoId=123&acertou=true
     */
    @PostMapping("/adicionar")
    public ResponseEntity<?> adicionarXp(
            @RequestParam Integer usuarioId,
            @RequestParam Integer questaoId,
            @RequestParam boolean acertou) {
        try {
            PessoaBean pessoa = nivelXpService.adicionarXpPorQuestao(usuarioId, questaoId, acertou);
            
            if (pessoa == null) {
                return ResponseEntity.ok().body("Questão respondida incorretamente. Nenhum XP adicionado.");
            }
            
            return ResponseEntity.ok(pessoa);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

    /**
     * Obtém informações de XP e nível do usuário.
     * 
     * Exemplo: GET /nivelxp/usuario/1
     */
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> getInfoNivelXp(@PathVariable Integer usuarioId) {
        try {
            PessoaBean pessoa = nivelXpService.obterInfoNivelXp(usuarioId);
            
            if (pessoa == null) {
                return ResponseEntity.notFound().build();
            }
            
            long totalQuestoes = nivelXpService.getTotalQuestoesCorretas(usuarioId);
            int totalXp = nivelXpService.getTotalXp(usuarioId);
            
            // Retornar um objeto com informações consolidadas
            return ResponseEntity.ok(new InfoNivelXpResponse(
                pessoa.getPesId(),
                pessoa.getPesNome(),
                pessoa.getPesApelido(),
                pessoa.getPesNivel() != null ? pessoa.getPesNivel() : 1,
                pessoa.getPesXp() != null ? pessoa.getPesXp() : 0,
                totalQuestoes,
                totalXp
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

    /**
     * Retorna o total de questões corretas do usuário.
     * 
     * Exemplo: GET /nivelxp/usuario/1/questoes-corretas
     */
    @GetMapping("/usuario/{usuarioId}/questoes-corretas")
    public ResponseEntity<?> getTotalQuestoesCorretas(@PathVariable Integer usuarioId) {
        try {
            long total = nivelXpService.getTotalQuestoesCorretas(usuarioId);
            return ResponseEntity.ok().body("Total de questões corretas: " + total);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

    /**
     * Retorna o ranking global de usuários por XP.
     * 
     * Exemplo: GET /nivelxp/ranking?limite=10
     */
    @GetMapping("/ranking")
    public ResponseEntity<?> getRankingGlobal(@RequestParam(defaultValue = "10") int limite) {
        try {
            var ranking = nivelXpService.obterRankingGlobal(limite);
            return ResponseEntity.ok(ranking);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

    // Classe interna para resposta
    public static class InfoNivelXpResponse {
        public Integer pessoaId;
        public String nome;
        public String apelido;
        public Integer nivel;
        public Integer xpAtual;
        public long totalQuestoesCorretas;
        public int totalXpAcumulado;

        public InfoNivelXpResponse(Integer pessoaId, String nome, String apelido, Integer nivel, 
                                    Integer xpAtual, long totalQuestoesCorretas, int totalXpAcumulado) {
            this.pessoaId = pessoaId;
            this.nome = nome;
            this.apelido = apelido;
            this.nivel = nivel;
            this.xpAtual = xpAtual;
            this.totalQuestoesCorretas = totalQuestoesCorretas;
            this.totalXpAcumulado = totalXpAcumulado;
        }
    }
}
