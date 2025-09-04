package br.com.rotafuturo.carreiras.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rotafuturo.carreiras.dto.PessoaDTO;
import br.com.rotafuturo.carreiras.model.PessoaBean;
import br.com.rotafuturo.carreiras.model.UsuarioBean;
import br.com.rotafuturo.carreiras.service.PessoaService;
import br.com.rotafuturo.carreiras.service.UsuarioService;

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
    public ResponseEntity<PessoaDTO> getMyPessoa() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<UsuarioBean> usuarioOpt = usuarioService.buscarUsuarioPorEmail(email);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<PessoaBean> pessoaOpt = pessoaService.getPessoaByUsuario(usuarioOpt.get());
        return pessoaOpt.map(p -> ResponseEntity.ok(pessoaService.toDTO(p))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PessoaDTO> createPessoa(@RequestBody PessoaDTO pessoaDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<UsuarioBean> usuarioOpt = usuarioService.buscarUsuarioPorEmail(email);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        PessoaBean pessoa = pessoaService.fromDTO(pessoaDTO);
        pessoa.setUsuario(usuarioOpt.get());
        PessoaBean novaPessoa = pessoaService.createPessoa(pessoa);
        return new ResponseEntity<>(pessoaService.toDTO(novaPessoa), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaDTO> updatePessoa(@PathVariable Integer id, @RequestBody PessoaDTO pessoaDTO) {
        Optional<PessoaBean> pessoaOpt = pessoaService.getPessoaById(id);
        if (pessoaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        PessoaBean pessoa = pessoaService.fromDTO(pessoaDTO);
        pessoa.setPesId(id);
        if (pessoa.getUsuario() == null) {
            pessoa.setUsuario(pessoaOpt.get().getUsuario());
        }
        PessoaBean pessoaAtualizada = pessoaService.updatePessoa(pessoa);
        return ResponseEntity.ok(pessoaService.toDTO(pessoaAtualizada));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> getPessoaById(@PathVariable Integer id) {
        Optional<PessoaBean> pessoaOpt = pessoaService.getPessoaById(id);
        return pessoaOpt.map(p -> ResponseEntity.ok(pessoaService.toDTO(p))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePessoa(@PathVariable Integer id) {
        pessoaService.deletePessoa(id);
        return ResponseEntity.noContent().build();
    }
}
