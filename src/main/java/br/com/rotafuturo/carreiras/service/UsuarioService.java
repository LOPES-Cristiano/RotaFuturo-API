
package br.com.rotafuturo.carreiras.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
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
	public br.com.rotafuturo.carreiras.dto.UsuarioDTO toDTO(UsuarioBean bean) {
		if (bean == null)
			return null;
		br.com.rotafuturo.carreiras.dto.UsuarioDTO dto = new br.com.rotafuturo.carreiras.dto.UsuarioDTO();
		dto.setUsuId(bean.getUsuId());
		dto.setUsuEmail(bean.getUsuEmail());
		dto.setUsuAtivo(bean.getUsuAtivo());
		dto.setUsuDatacadastro(bean.getUsuDataCadastro());
		dto.setUsuHoracadastro(bean.getUsuHoraCadastro());
		// Adicione outros campos conforme necessário
		return dto;
	}

	public UsuarioBean fromDTO(br.com.rotafuturo.carreiras.dto.UsuarioDTO dto) {
		if (dto == null)
			return null;
		UsuarioBean bean = new UsuarioBean();
		bean.setUsuId(dto.getUsuId());
		bean.setUsuEmail(dto.getUsuEmail());
		bean.setUsuAtivo(dto.getUsuAtivo());
		bean.setUsuDataCadastro(dto.getUsuDatacadastro());
		bean.setUsuHoraCadastro(dto.getUsuHoracadastro());
		// Adicione outros campos conforme necessário
		return bean;
	}

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

	public List<UsuarioBean> listarTodos() {
		return usuarioRepository.findAll();
	}

	public UsuarioBean atualizarUsuario(Integer id, UsuarioBean usuario) {
		Optional<UsuarioBean> existenteOpt = usuarioRepository.findById(id);
		if (existenteOpt.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
		}
		UsuarioBean existente = existenteOpt.get();
		// Atualize os campos necessários
		existente.setUsuEmail(usuario.getUsuEmail());
		if (usuario.getUsuSenha() != null && !usuario.getUsuSenha().isEmpty()) {
			existente.setUsuSenha(passwordEncoder.encode(usuario.getUsuSenha()));
		}
		existente.setUsuAtivo(usuario.getUsuAtivo());
		existente.setUsuEmailValidado(usuario.getUsuEmailValidado());
		existente.setUsuTrocaSenha(usuario.getUsuTrocaSenha());
		// Adicione outros campos se necessário
		return usuarioRepository.save(existente);
	}

	public UsuarioBean inativarUsuario(Integer id) {
		Optional<UsuarioBean> existenteOpt = usuarioRepository.findById(id);
		if (existenteOpt.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
		}
		UsuarioBean existente = existenteOpt.get();
		existente.setUsuAtivo(false);
		return usuarioRepository.save(existente);
	}

	public void alterarSenhaComHistorico(Integer id, String novaSenha) {
		Optional<UsuarioBean> existenteOpt = usuarioRepository.findById(id);
		if (existenteOpt.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
		}
		UsuarioBean usuario = existenteOpt.get();
		String senhaAtual = usuario.getUsuSenha();
		String senhaAntiga1 = usuario.getUsuSenhaAntiga1();
		usuario.setUsuSenhaAntiga2(senhaAntiga1);
		usuario.setUsuSenhaAntiga1(senhaAtual);
		usuario.setUsuSenha(passwordEncoder.encode(novaSenha));
		usuarioRepository.save(usuario);
	}
}
