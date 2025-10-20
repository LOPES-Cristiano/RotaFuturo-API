package br.com.rotafuturo.carreiras.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import br.com.rotafuturo.carreiras.annotation.RequiresAdmin;
import br.com.rotafuturo.carreiras.model.UsuarioBean;
import br.com.rotafuturo.carreiras.service.GrupoAcessoUsuarioService;
import br.com.rotafuturo.carreiras.service.UsuarioService;

/**
 * Aspect que intercepta métodos anotados com @RequiresAdmin e valida se o usuário
 * autenticado possui o grupo "Administrador" (case-insensitive)
 */
@Aspect
@Component
public class AdminSecurityAspect {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private GrupoAcessoUsuarioService grupoAcessoUsuarioService;

    @Before("@annotation(requiresAdmin)")
    public void verificarPermissaoAdmin(RequiresAdmin requiresAdmin) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, 
                "Usuário não autenticado"
            );
        }

        String email = authentication.getName();
        
        try {
            UsuarioBean usuario = usuarioService.buscarUsuarioPorEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, 
                    "Usuário não encontrado"
                ));

            // Verificação case-insensitive para "Administrador"
            // Aceita: Administrador, ADMINISTRADOR, administrador, etc
            boolean isAdmin = grupoAcessoUsuarioService
                .listarGruposDoUsuario(usuario.getUsuId())
                .stream()
                .anyMatch(grupo -> grupo.equalsIgnoreCase("Administrador"));

            if (!isAdmin) {
                throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, 
                    requiresAdmin.message()
                );
            }
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                "Erro ao verificar permissões: " + e.getMessage()
            );
        }
    }
}
