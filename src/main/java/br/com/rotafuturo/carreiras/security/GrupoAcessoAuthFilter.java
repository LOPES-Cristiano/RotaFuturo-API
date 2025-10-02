package br.com.rotafuturo.carreiras.security;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class GrupoAcessoAuthFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(GrupoAcessoAuthFilter.class);
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String path = request.getRequestURI();
            if (path.startsWith("/home/admin")) {
                String token = getTokenFromRequest(request);
                if (token != null && jwtTokenProvider.validateToken(token)) {
                    boolean isAdmin = jwtTokenProvider.usuarioPertenceAoGrupo(token, "ADMINISTRADOR");
                    if (!isAdmin) {
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        response.getWriter().write("Acesso negado. É necessário ser um administrador.");
                        return;
                    }
                } else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Autenticação necessária para acessar este recurso.");
                    return;
                }
            }
        } catch (Exception ex) {
            logger.error("Não foi possível validar o acesso ao grupo", ex);
        }
        filterChain.doFilter(request, response);
    }
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}