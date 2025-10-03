package br.com.rotafuturo.carreiras.security;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.rotafuturo.carreiras.model.UsuarioBean;
import br.com.rotafuturo.carreiras.repository.UsuarioRepository;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private final UsuarioRepository usuarioRepository;
	public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UsuarioBean usuario = usuarioRepository.findByUsuEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + username));
		List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
		return new org.springframework.security.core.userdetails.User(usuario.getUsuEmail(), usuario.getUsuSenha(),
				authorities);
	}
}
