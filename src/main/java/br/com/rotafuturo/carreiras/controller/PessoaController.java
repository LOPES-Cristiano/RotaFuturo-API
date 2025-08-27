package br.com.rotafuturo.carreiras.controller;

import br.com.rotafuturo.carreiras.model.PessoaBean;
import br.com.rotafuturo.carreiras.model.UsuarioBean;
import br.com.rotafuturo.carreiras.service.PessoaService;
import br.com.rotafuturo.carreiras.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {
    private final PessoaService pessoaService;
    private final UsuarioService usuarioService;

    @Autowired
    public PessoaController(PessoaService pessoaService, UsuarioService usuarioService) {
        this.pessoaService = pessoaService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/me")
    public ResponseEntity<PessoaBean> getMyPessoa() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<UsuarioBean> usuarioOpt = usuarioService.buscarUsuarioPorEmail(email);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<PessoaBean> pessoaOpt = pessoaService.getPessoaByUsuario(usuarioOpt.get());
        return pessoaOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PessoaBean> createPessoa(@RequestBody PessoaBean pessoa) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<UsuarioBean> usuarioOpt = usuarioService.buscarUsuarioPorEmail(email);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Integer usuarioId = usuarioOpt.get().getUsuId();
        Optional<UsuarioBean> usuarioGerenciadoOpt = usuarioService.buscarUsuarioPorId(usuarioId);
        if (usuarioGerenciadoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UsuarioBean usuario = usuarioGerenciadoOpt.get();
        System.out.println("[DEBUG] Vinculando pessoa ao usuario com ID: " + usuario.getUsuId());
        pessoa.setUsuario(usuario);
        PessoaBean novaPessoa = pessoaService.createPessoa(pessoa);
        System.out.println("[DEBUG] Pessoa criada: " + novaPessoa.getPesId() + " | Usuario vinculado: " + (novaPessoa.getUsuario() != null ? novaPessoa.getUsuario().getUsuId() : "null"));
        return new ResponseEntity<>(novaPessoa, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaBean> updatePessoa(@PathVariable Integer id, @RequestBody PessoaBean pessoa) {
    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PessoaController.class);
    logger.debug("Payload recebido no updatePessoa: {}", pessoa);
        Optional<PessoaBean> pessoaOpt = pessoaService.getPessoaById(id);
        if (pessoaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        pessoa.setPesId(id);
        
        if (pessoa.getUsuario() == null) {
            pessoa.setUsuario(pessoaOpt.get().getUsuario());
        }
        PessoaBean pessoaAtualizada = pessoaService.updatePessoa(pessoa);
        return ResponseEntity.ok(pessoaAtualizada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaBean> getPessoaById(@PathVariable Integer id) {
        Optional<PessoaBean> pessoaOpt = pessoaService.getPessoaById(id);
        return pessoaOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePessoa(@PathVariable Integer id) {
        pessoaService.deletePessoa(id);
        return ResponseEntity.noContent().build();
    }
}
