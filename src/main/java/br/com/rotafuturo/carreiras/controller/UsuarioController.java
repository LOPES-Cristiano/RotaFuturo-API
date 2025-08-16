package br.com.rotafuturo.carreiras.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rotafuturo.carreiras.dto.usuario.UsuarioCriacaoDTO;
import br.com.rotafuturo.carreiras.model.UsuarioBean;
import br.com.rotafuturo.carreiras.service.UsuarioService;

/**
 * Controlador REST para gerenciar a entidade Usuario.
 * Contem os endpoints publicos de registro e um endpoint protegido para teste.
 */
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<UsuarioBean> registrarUsuario(@RequestBody UsuarioCriacaoDTO usuarioCriacaoDTO) {
       
        UsuarioBean novoUsuario = usuarioService.criarUsuario(usuarioCriacaoDTO);
        return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioBean> buscarUsuarioPorId(@PathVariable Integer id) {
        Optional<UsuarioBean> usuario = usuarioService.buscarUsuarioPorId(id);
        return usuario.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
