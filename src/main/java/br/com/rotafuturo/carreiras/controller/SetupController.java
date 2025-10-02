package br.com.rotafuturo.carreiras.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.rotafuturo.carreiras.model.GrupoAcessoBean;
import br.com.rotafuturo.carreiras.service.GrupoAcessoService;
import br.com.rotafuturo.carreiras.service.GrupoAcessoUsuarioService;
@RestController
@RequestMapping("/setup")
public class SetupController {
    @Autowired
    private GrupoAcessoService grupoAcessoService;
    @Autowired
    private GrupoAcessoUsuarioService grupoAcessoUsuarioService;
    @PostMapping("/init-grupos")
    public ResponseEntity<String> inicializarGrupos() {
        GrupoAcessoBean grupoAdmin = grupoAcessoService.buscarPorDescricao("ADMINISTRADOR");
        if (grupoAdmin == null) {
            grupoAdmin = new GrupoAcessoBean();
            grupoAdmin.setGruaDescricao("ADMINISTRADOR");
            grupoAcessoService.salvar(grupoAdmin);
            return ResponseEntity.ok("Grupo ADMINISTRADOR criado com sucesso!");
        }
        return ResponseEntity.ok("Grupo ADMINISTRADOR já existe!");
    }
    @PostMapping("/associar-admin")
    public ResponseEntity<String> associarAdmin(Integer usuarioId) {
        if (usuarioId == null) {
            return ResponseEntity.badRequest().body("ID do usuário não fornecido");
        }
        try {
            GrupoAcessoBean grupoAdmin = grupoAcessoService.buscarPorDescricao("ADMINISTRADOR");
            if (grupoAdmin == null) {
                return ResponseEntity.badRequest().body("Grupo ADMINISTRADOR não encontrado. Execute o setup inicial primeiro.");
            }
            grupoAcessoUsuarioService.associarUsuarioAGrupo(usuarioId, grupoAdmin.getGruaId());
            return ResponseEntity.ok("Usuário associado ao grupo ADMINISTRADOR com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao associar usuário: " + e.getMessage());
        }
    }
}