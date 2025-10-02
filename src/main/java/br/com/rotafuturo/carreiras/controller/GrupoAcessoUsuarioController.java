package br.com.rotafuturo.carreiras.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.rotafuturo.carreiras.model.GrupoAcessoUsuarioBean;
import br.com.rotafuturo.carreiras.service.GrupoAcessoUsuarioService;
@RestController
@RequestMapping("/grupo-acesso-usuario")
public class GrupoAcessoUsuarioController {
    @Autowired
    private GrupoAcessoUsuarioService grupoAcessoUsuarioService;
    @GetMapping("/usuario/{usuId}")
    public ResponseEntity<List<GrupoAcessoUsuarioBean>> listarPorUsuario(@PathVariable Integer usuId) {
        List<GrupoAcessoUsuarioBean> grupos = grupoAcessoUsuarioService.listarPorUsuario(usuId);
        return ResponseEntity.ok(grupos);
    }
    @GetMapping("/usuario/{usuId}/grupos")
    public ResponseEntity<List<String>> listarGruposDoUsuario(@PathVariable Integer usuId) {
        List<String> grupos = grupoAcessoUsuarioService.listarGruposDoUsuario(usuId);
        return ResponseEntity.ok(grupos);
    }
    @GetMapping("/verificar/{usuId}/{grupoDescricao}")
    public ResponseEntity<Boolean> verificarUsuarioNoGrupo(
            @PathVariable Integer usuId, 
            @PathVariable String grupoDescricao) {
        boolean pertence = grupoAcessoUsuarioService.usuarioPertenceAoGrupo(usuId, grupoDescricao);
        return ResponseEntity.ok(pertence);
    }
    @PostMapping("/associar/{usuId}/{gruaId}")
    public ResponseEntity<GrupoAcessoUsuarioBean> associarUsuarioAGrupo(
            @PathVariable Integer usuId, 
            @PathVariable Integer gruaId) {
        GrupoAcessoUsuarioBean associacao = grupoAcessoUsuarioService.associarUsuarioAGrupo(usuId, gruaId);
        return ResponseEntity.ok(associacao);
    }
    @DeleteMapping("/remover/{usuId}/{gruaId}")
    public ResponseEntity<Void> removerUsuarioDoGrupo(
            @PathVariable Integer usuId, 
            @PathVariable Integer gruaId) {
        grupoAcessoUsuarioService.removerUsuarioDoGrupo(usuId, gruaId);
        return ResponseEntity.noContent().build();
    }
}