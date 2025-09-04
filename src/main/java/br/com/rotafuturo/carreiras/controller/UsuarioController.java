package br.com.rotafuturo.carreiras.controller;

import java.util.Map;
import java.util.Optional;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PatchMapping;

import br.com.rotafuturo.carreiras.dto.usuario.UsuarioCriacaoDTO;
import br.com.rotafuturo.carreiras.model.UsuarioBean;
import br.com.rotafuturo.carreiras.dto.UsuarioDTO;
import br.com.rotafuturo.carreiras.service.UsuarioService;
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<UsuarioDTO> registrarUsuario(@RequestBody UsuarioCriacaoDTO usuarioCriacaoDTO) {
        UsuarioBean novoUsuario = usuarioService.criarUsuario(usuarioCriacaoDTO);
        UsuarioDTO dto = usuarioService.toDTO(novoUsuario);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioDTO> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String email = authentication.getName();
        Optional<UsuarioBean> usuario = usuarioService.buscarUsuarioPorEmail(email);
        return usuario.map(u -> ResponseEntity.ok(usuarioService.toDTO(u)))
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorId(@PathVariable Integer id) {
        Optional<UsuarioBean> usuario = usuarioService.buscarUsuarioPorId(id);
        return usuario.map(u -> ResponseEntity.ok(usuarioService.toDTO(u)))
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/exists")
    public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestParam String email) {
    
        boolean exists;
        try {
            Optional<UsuarioBean> usuBean = usuarioService.buscarUsuarioPorEmail(email);
            exists = usuBean.isPresent();
        } catch (Exception e) {
            exists = false;
        }
        return ResponseEntity.ok(Map.of("exists", exists));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        List<UsuarioBean> usuarios = usuarioService.listarTodos();
        List<UsuarioDTO> dtos = usuarios.stream().map(usuarioService::toDTO).toList();
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@PathVariable Integer id, @RequestBody UsuarioDTO usuarioDTO) {
        UsuarioBean atualizado = usuarioService.atualizarUsuario(id, usuarioService.fromDTO(usuarioDTO));
        return ResponseEntity.ok(usuarioService.toDTO(atualizado));
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<UsuarioBean> inativarUsuario(@PathVariable Integer id) {
        UsuarioBean inativado = usuarioService.inativarUsuario(id);
        return ResponseEntity.ok(inativado);
    }
}
