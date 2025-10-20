package br.com.rotafuturo.carreiras.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rotafuturo.carreiras.dto.UsuarioAreaDTO;
import br.com.rotafuturo.carreiras.service.UsuarioAreaService;
import br.com.rotafuturo.carreiras.service.UsuarioService;

/**
 * Controller para gerenciar os vínculos de usuários com áreas e subáreas.
 */
@RestController
@RequestMapping("/api/usuario-area")
public class UsuarioAreaController {

    private final UsuarioAreaService usuarioAreaService;
    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioAreaController(UsuarioAreaService usuarioAreaService, UsuarioService usuarioService) {
        this.usuarioAreaService = usuarioAreaService;
        this.usuarioService = usuarioService;
    }

    /**
     * Lista as áreas e subáreas do usuário autenticado.
     */
    @GetMapping("/minhas-areas")
    public ResponseEntity<List<UsuarioAreaDTO>> listarMinhasAreas() {
        // Obtém o usuário autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        
        var usuario = usuarioService.buscarUsuarioPorEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        List<UsuarioAreaDTO> areas = usuarioAreaService.listarAreasDoUsuario(usuario.getUsuId());
        
        return ResponseEntity.ok(areas);
    }
    
    /**
     * Vincula uma área e opcionalmente uma subárea ao usuário autenticado.
     */
    @PostMapping("/vincular")
    public ResponseEntity<UsuarioAreaDTO> vincularArea(@RequestBody VincularAreaRequest request) {
        // Obtém o usuário autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        
        var usuario = usuarioService.buscarUsuarioPorEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        UsuarioAreaDTO vinculo = usuarioAreaService.vincularArea(
                usuario.getUsuId(), 
                request.getAreaId(), 
                request.getAreaSubId()
        );
        
        return ResponseEntity.ok(vinculo);
    }
    
    /**
     * Desvincula uma área específica do usuário autenticado.
     */
    @DeleteMapping("/{usuareaId}")
    public ResponseEntity<Void> desvincularArea(@PathVariable Integer usuareaId) {
        usuarioAreaService.desvincularArea(usuareaId);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Desvincula todas as áreas do usuário autenticado.
     */
    @DeleteMapping("/todas")
    public ResponseEntity<Void> desvincularTodasAreas() {
        // Obtém o usuário autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        
        var usuario = usuarioService.buscarUsuarioPorEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        usuarioAreaService.desvincularTodasAreas(usuario.getUsuId());
        
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Classe interna para requisição de vinculação de área.
     */
    public static class VincularAreaRequest {
        private Integer areaId;
        private Integer areaSubId;
        
        public Integer getAreaId() {
            return areaId;
        }
        
        public void setAreaId(Integer areaId) {
            this.areaId = areaId;
        }
        
        public Integer getAreaSubId() {
            return areaSubId;
        }
        
        public void setAreaSubId(Integer areaSubId) {
            this.areaSubId = areaSubId;
        }
    }
}
