package br.com.rotafuturo.carreiras.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.rotafuturo.carreiras.model.GrupoAcessoBean;
import br.com.rotafuturo.carreiras.service.GrupoAcessoService;
@RestController
@RequestMapping("/grupo-acesso")
public class GrupoAcessoController {
    @Autowired
    private GrupoAcessoService grupoAcessoService;
    @GetMapping
    public ResponseEntity<List<GrupoAcessoBean>> listarTodos() {
        List<GrupoAcessoBean> grupos = grupoAcessoService.listarTodos();
        return ResponseEntity.ok(grupos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<GrupoAcessoBean> buscarPorId(@PathVariable Integer id) {
        GrupoAcessoBean grupo = grupoAcessoService.buscarPorId(id);
        return ResponseEntity.ok(grupo);
    }
    @PostMapping
    public ResponseEntity<GrupoAcessoBean> criar(@RequestBody GrupoAcessoBean grupoAcesso) {
        GrupoAcessoBean novoGrupo = grupoAcessoService.salvar(grupoAcesso);
        return ResponseEntity.ok(novoGrupo);
    }
    @PutMapping("/{id}")
    public ResponseEntity<GrupoAcessoBean> atualizar(@PathVariable Integer id, @RequestBody GrupoAcessoBean grupoAcesso) {
        grupoAcesso.setGruaId(id);
        GrupoAcessoBean grupoAtualizado = grupoAcessoService.salvar(grupoAcesso);
        return ResponseEntity.ok(grupoAtualizado);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        grupoAcessoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}