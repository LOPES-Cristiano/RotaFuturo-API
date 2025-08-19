package br.com.rotafuturo.carreiras.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.rotafuturo.carreiras.dto.usuario.UsuarioCriacaoDTO;
import br.com.rotafuturo.carreiras.model.UsuarioBean;
import br.com.rotafuturo.carreiras.repository.UsuarioRepository;
import br.com.rotafuturo.carreiras.util.AppUtils;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioBean criarUsuario(UsuarioCriacaoDTO criacaoDTO) {
        LocalDate usuDataCadastro = AppUtils.getCurrentDate();
        LocalTime usuHoraCadastro = AppUtils.getCurrentTime();

        if (usuarioRepository.findByUsuEmail(criacaoDTO.getUsuEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já cadastrado");
        }
        
        UsuarioBean novoUsuario = new UsuarioBean();
        novoUsuario.setUsuEmail(criacaoDTO.getUsuEmail());
        
        novoUsuario.setUsuSenha(passwordEncoder.encode(criacaoDTO.getUsuSenha()));
        novoUsuario.setUsuAtivo(true); 
        novoUsuario.setUsuDataCadastro(usuDataCadastro);
        novoUsuario.setUsuHoraCadastro(usuHoraCadastro);
        novoUsuario.setUsuTrocaSenha(false);
        novoUsuario.setUsuEmailValidado(false);

        return usuarioRepository.save(novoUsuario);
    }

    public Optional<UsuarioBean> buscarUsuarioPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    public Optional<UsuarioBean> buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByUsuEmail(email);
    }
}
