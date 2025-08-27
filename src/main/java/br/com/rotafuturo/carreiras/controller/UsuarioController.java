package br.com.rotafuturo.carreiras.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.rotafuturo.carreiras.dto.usuario.UsuarioCriacaoDTO;
import br.com.rotafuturo.carreiras.model.UsuarioBean;
import br.com.rotafuturo.carreiras.service.UsuarioService;
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

    @GetMapping("/me")
    public ResponseEntity<UsuarioBean> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        String email = authentication.getName();
        Optional<UsuarioBean> usuario = usuarioService.buscarUsuarioPorEmail(email);
        
        return usuario.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioBean> buscarUsuarioPorId(@PathVariable Integer id) {
        Optional<UsuarioBean> usuario = usuarioService.buscarUsuarioPorId(id);
        return usuario.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/exists")
    public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestParam String email) {
        boolean exists = false;
    
        try {
            Optional<UsuarioBean> usuBean = usuarioService.buscarUsuarioPorEmail(email);
            exists = usuBean.isPresent();
        } catch (Exception e) {
            
            exists = false;
        }
    
        return ResponseEntity.ok(Map.of("exists", exists));
    }
}
