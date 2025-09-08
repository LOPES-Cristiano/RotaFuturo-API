package br.com.rotafuturo.carreiras.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rotafuturo.carreiras.dto.PessoaDTO;
import br.com.rotafuturo.carreiras.model.PessoaBean;
import br.com.rotafuturo.carreiras.model.UsuarioBean;
import br.com.rotafuturo.carreiras.service.PessoaService;
import br.com.rotafuturo.carreiras.service.UsuarioService;
import br.com.rotafuturo.carreiras.storage.FileCryptoUtil;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {
	private final PessoaService pessoaService;
	private final UsuarioService usuarioService;

	@Autowired
	public PessoaController(PessoaService pessoaService, UsuarioService usuarioService) {
		this.pessoaService = pessoaService;
		this.usuarioService = usuarioService;
	}

	@GetMapping("/me")
	public ResponseEntity<PessoaDTO> getMyPessoa() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		Optional<UsuarioBean> usuarioOpt = usuarioService.buscarUsuarioPorEmail(email);
		if (usuarioOpt.isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		Optional<PessoaBean> pessoaOpt = pessoaService.getPessoaByUsuario(usuarioOpt.get());
		return pessoaOpt.map(p -> ResponseEntity.ok(pessoaService.toDTO(p)))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<PessoaDTO> createPessoa(@RequestBody PessoaDTO pessoaDTO) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		Optional<UsuarioBean> usuarioOpt = usuarioService.buscarUsuarioPorEmail(email);
		if (usuarioOpt.isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		PessoaBean pessoa = pessoaService.fromDTO(pessoaDTO);
		pessoa.setUsuario(usuarioOpt.get());
		pessoa.setPesDatacadastro(java.time.LocalDate.now());
		pessoa.setPesHoracadastro(java.time.LocalTime.now());
		pessoa.setPesAtivo(true);

		if (pessoaDTO.getPesImagemperfil() != null && !pessoaDTO.getPesImagemperfil().isEmpty()) {
			try {
				Integer usuId = usuarioOpt.get().getUsuId();
				Path userDir = Paths.get("storage", String.valueOf(usuId));
				if (!Files.exists(userDir)) {
					Files.createDirectories(userDir);
				}
				String fileName = java.util.UUID.randomUUID() + "_perfil.jpg";
				Path filePath = userDir.resolve(fileName);
				byte[] imageBytes = Base64.getDecoder().decode(pessoaDTO.getPesImagemperfil());
				FileCryptoUtil.encryptAndSave(filePath, imageBytes);
				pessoa.setPesImagemperfil(filePath.toString().replace("\\", "/"));
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}
		}
		if (pessoaDTO.getPesImagemCapaPerfil() != null && !pessoaDTO.getPesImagemCapaPerfil().isEmpty()) {
			try {
				Integer usuId = usuarioOpt.get().getUsuId();
				Path userDir = Paths.get("storage", String.valueOf(usuId));
				if (!Files.exists(userDir)) {
					Files.createDirectories(userDir);
				}
				String fileName = java.util.UUID.randomUUID() + "_capaperfil.jpg";
				Path filePath = userDir.resolve(fileName);
				byte[] imageBytes = Base64.getDecoder().decode(pessoaDTO.getPesImagemCapaPerfil());
				FileCryptoUtil.encryptAndSave(filePath, imageBytes);
				pessoa.setPesImagemCapaPerfil(filePath.toString().replace("\\", "/"));
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}
		}

		PessoaBean novaPessoa = pessoaService.createPessoa(pessoa);
		return new ResponseEntity<>(pessoaService.toDTO(novaPessoa), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<PessoaDTO> updatePessoa(@PathVariable Integer id, @RequestBody PessoaDTO pessoaDTO) {
		Optional<PessoaBean> pessoaOpt = pessoaService.getPessoaById(id);

		if (pessoaOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		PessoaBean pessoa = pessoaService.fromDTO(pessoaDTO);

		pessoa.setPesId(id);

		if (pessoa.getUsuario() == null) {
			pessoa.setUsuario(pessoaOpt.get().getUsuario());
		}

		pessoa.setPesDatacadastro(pessoaOpt.get().getPesDatacadastro());
		pessoa.setPesHoracadastro(pessoaOpt.get().getPesHoracadastro());

		if (pessoa.getPesAtivo() == null) {
			pessoa.setPesAtivo(true);
		}
		if (pessoaDTO.getPesImagemperfil() != null && !pessoaDTO.getPesImagemperfil().isEmpty()
				&& !pessoaDTO.getPesImagemperfil().startsWith("storage/")) {
			try {
				Integer usuId = pessoa.getUsuario().getUsuId();
				Path userDir = Paths.get("storage", String.valueOf(usuId));
				if (!Files.exists(userDir)) {
					Files.createDirectories(userDir);
				}
				String fileName = java.util.UUID.randomUUID() + "_perfil.jpg";
				Path filePath = userDir.resolve(fileName);
				byte[] imageBytes = Base64.getDecoder().decode(pessoaDTO.getPesImagemperfil());
				FileCryptoUtil.encryptAndSave(filePath, imageBytes);
				pessoa.setPesImagemperfil(filePath.toString().replace("\\", "/"));
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}
		} else if (pessoaDTO.getPesImagemperfil() == null || pessoaDTO.getPesImagemperfil().isEmpty()) {
			pessoa.setPesImagemperfil(pessoaOpt.get().getPesImagemperfil());
		}
		if (pessoaDTO.getPesImagemCapaPerfil() != null && !pessoaDTO.getPesImagemCapaPerfil().isEmpty()
				&& !pessoaDTO.getPesImagemCapaPerfil().startsWith("storage/")) {
			try {
				Integer usuId = pessoa.getUsuario().getUsuId();
				Path userDir = Paths.get("storage", String.valueOf(usuId));
				if (!Files.exists(userDir)) {
					Files.createDirectories(userDir);
				}
				String fileName = java.util.UUID.randomUUID() + "_capaperfil.jpg";
				Path filePath = userDir.resolve(fileName);
				byte[] imageBytes = Base64.getDecoder().decode(pessoaDTO.getPesImagemCapaPerfil());
				FileCryptoUtil.encryptAndSave(filePath, imageBytes);
				pessoa.setPesImagemCapaPerfil(filePath.toString().replace("\\", "/"));
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}
		} else if (pessoaDTO.getPesImagemCapaPerfil() == null || pessoaDTO.getPesImagemCapaPerfil().isEmpty()) {
			pessoa.setPesImagemCapaPerfil(pessoaOpt.get().getPesImagemCapaPerfil());
		}
		PessoaBean pessoaAtualizada = pessoaService.updatePessoa(pessoa);
		return ResponseEntity.ok(pessoaService.toDTO(pessoaAtualizada));
	}

	@GetMapping("/{id}")
	public ResponseEntity<PessoaDTO> getPessoaById(@PathVariable Integer id) {
		Optional<PessoaBean> pessoaOpt = pessoaService.getPessoaById(id);
		return pessoaOpt.map(p -> ResponseEntity.ok(pessoaService.toDTO(p)))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePessoa(@PathVariable Integer id) {
		pessoaService.deletePessoa(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public List<PessoaDTO> listarTodasPessoas() {
		return pessoaService.listarTodasDTO();
	}
}
