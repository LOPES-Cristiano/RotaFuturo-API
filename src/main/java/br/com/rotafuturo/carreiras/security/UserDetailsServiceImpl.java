package br.com.rotafuturo.carreiras.security;

import br.com.rotafuturo.carreiras.model.UsuarioBean;
import br.com.rotafuturo.carreiras.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

/**
 * Implementacao do UserDetailsService do Spring Security.
 * Carrega os detalhes do usuario do banco de dados para autenticacao.
 * 
 * Aqui é para validar o usuário no login, ele procura se tem algum usuário com esse email.
 * Se encontrar, retorna os detalhes do usuário, caso contrário, lança uma exceção.
 */
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

        return new org.springframework.security.core.userdetails.User(
                usuario.getUsuEmail(),
                usuario.getUsuSenha(),
                emptyList()
        );
    }
}
