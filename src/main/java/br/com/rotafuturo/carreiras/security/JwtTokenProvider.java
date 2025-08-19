package br.com.rotafuturo.carreiras.security;

import java.util.Date;

import javax.crypto.SecretKey; // Continua sendo javax.crypto.SecretKey

import org.slf4j.Logger; // Continue usando Keys
import org.slf4j.LoggerFactory; // Para o algoritmo de assinatura
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component; // <-- Importe para ler propriedades

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException; // <-- Importe para decodificar Base64

/**
 * Classe utilitaria para gerar, validar e extrair informacoes de tokens JWT.
 */
@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    // Remove a inicialização direta aqui
    // private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    // injeta o valor da propriedade jwt.secret do application.properties
    @Value("${app.jwtSecret}")
    private String jwtSecretString; // String da chave

    private SecretKey key; // Agora será inicializada no construtor

    private final long JWT_EXPIRATION_IN_MS = 86400000; // 1 dia

    // Construtor para inicializar a SecretKey a partir da string
    public JwtTokenProvider(@Value("${app.jwtSecret}") String jwtSecretString) {
        // Decodifica a string Base64 da chave e a usa para criar a SecretKey
        // Isso garante que a mesma chave seja usada a cada vez que o componente for carregado
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretString));
    }

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION_IN_MS);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(this.key, SignatureAlgorithm.HS512) // Usa a chave persistente
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.key) // Usa a chave persistente
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token); // Usa a chave persistente
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