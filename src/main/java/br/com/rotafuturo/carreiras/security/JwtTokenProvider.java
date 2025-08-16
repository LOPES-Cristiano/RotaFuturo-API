package br.com.rotafuturo.carreiras.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

/**
 * Classe utilitaria para gerar, validar e extrair informacoes de tokens JWT.
 */
@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    // Quando formos subir para produção, devemos usar uma variável vindo do .env
    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    private final long JWT_EXPIRATION_IN_MS = 86400000; // 1 dia

    /**
     * Gera um token JWT para um usuario autenticado.
     * @param authentication O objeto de autenticacao do usuario.
     * @return A string do token JWT.
     */
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION_IN_MS);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Extrai o nome de usuario (subject) de um token JWT.
     * @param token O token a ser validado.
     * @return O nome de usuario.
     */
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Valida um token JWT.
     * @param token O token a ser validado.
     * @return true se o token for valido, false caso contrario.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            logger.error("Assinatura JWT invalida: {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            logger.error("Token JWT invalido: {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            logger.error("Token JWT expirado: {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            logger.error("Token JWT nao suportado: {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            logger.error("O token JWT esta vazio: {}", ex.getMessage());
        }
        return false;
    }
}
