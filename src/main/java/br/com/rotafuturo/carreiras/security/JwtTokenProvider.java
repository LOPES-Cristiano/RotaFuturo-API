package br.com.rotafuturo.carreiras.security;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.SecretKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import br.com.rotafuturo.carreiras.repository.UsuarioRepository;
import br.com.rotafuturo.carreiras.service.GrupoAcessoUsuarioService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
@Component
public class JwtTokenProvider {
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
	@Value("${app.jwtSecret}")
	private String jwtSecretString;
	private SecretKey key;
	private final long JWT_EXPIRATION_IN_MS = 86400000; 
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private GrupoAcessoUsuarioService grupoAcessoUsuarioService;
	public JwtTokenProvider(@Value("${app.jwtSecret}") String jwtSecretString) {
		this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretString));
	}
	public String generateToken(Authentication authentication) {
		String username = authentication.getName();
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION_IN_MS);
		Map<String, Object> claims = new HashMap<>();
		usuarioRepository.findByUsuEmail(username).ifPresent(usuario -> {
			List<String> grupos = grupoAcessoUsuarioService.listarGruposDoUsuario(usuario.getUsuId());
			claims.put("grupos", grupos);
		});
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(username)
				.setIssuedAt(now)
				.setExpiration(expiryDate)
				.signWith(this.key, SignatureAlgorithm.HS512)
				.compact();
	}
	public String getUsernameFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token).getBody().getSubject();
	}
	@SuppressWarnings("unchecked")
	public List<String> getGruposFromToken(String token) {
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(this.key)
				.build()
				.parseClaimsJws(token)
				.getBody();
		return claims.get("grupos", List.class);
	}
	public boolean usuarioPertenceAoGrupo(String token, String grupoDescricao) {
		try {
			List<String> grupos = getGruposFromToken(token);
			return grupos != null && grupos.contains(grupoDescricao);
		} catch (Exception e) {
			return false;
		}
	}
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token);
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