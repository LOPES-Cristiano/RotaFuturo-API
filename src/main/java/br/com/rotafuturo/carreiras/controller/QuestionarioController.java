package br.com.rotafuturo.carreiras.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rotafuturo.carreiras.dto.QuestionarioDTO;
import br.com.rotafuturo.carreiras.model.QuestionarioBean;
import br.com.rotafuturo.carreiras.repository.QuestionarioRepository;

@RestController
@RequestMapping("/questionario")
public class QuestionarioController {
	@Autowired
	private QuestionarioRepository questionarioRepository;
	@Autowired
	private br.com.rotafuturo.carreiras.service.QuestionarioService questionarioService;

	@GetMapping
	public List<QuestionarioDTO> getAll() {
		return questionarioRepository.findAll().stream().map(questionarioService::toDTO).toList();
	}

	@GetMapping("/{id}")
	public ResponseEntity<QuestionarioDTO> getById(@PathVariable Integer id) {
		Optional<QuestionarioBean> questionario = questionarioRepository.findById(id);
		return questionario.map(q -> ResponseEntity.ok(questionarioService.toDTO(q)))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	   @PostMapping
	   public ResponseEntity<QuestionarioDTO> create(
			   @RequestBody QuestionarioDTO questionarioDTO) {
		   QuestionarioBean questionario = questionarioService.fromDTO(questionarioDTO);
		   questionario.setQuesDatacadastro(java.time.LocalDate.now());
		   questionario.setQuesHoracadastro(java.time.LocalTime.now());
		   questionario.setQuesAtivo(true); // Sempre criar como ativo
		   QuestionarioBean saved = questionarioRepository.save(questionario);
		   return new ResponseEntity<>(questionarioService.toDTO(saved), HttpStatus.CREATED);
	   }

	   @PutMapping("/{id}")
	   public ResponseEntity<QuestionarioDTO> update(@PathVariable Integer id,
			   @RequestBody QuestionarioDTO questionarioDTO) {
		   Optional<QuestionarioBean> originalOpt = questionarioRepository.findById(id);
		   if (originalOpt.isEmpty()) {
			   return ResponseEntity.notFound().build();
		   }
		   QuestionarioBean original = originalOpt.get();
		   QuestionarioBean questionario = questionarioService.fromDTO(questionarioDTO);
		   questionario.setQuesId(id);
		   // Mantém data/hora originais se não vierem no DTO
		   if (questionario.getQuesDatacadastro() == null) {
			   questionario.setQuesDatacadastro(original.getQuesDatacadastro());
		   }
		   if (questionario.getQuesHoracadastro() == null) {
			   questionario.setQuesHoracadastro(original.getQuesHoracadastro());
		   }
		   QuestionarioBean updated = questionarioRepository.save(questionario);
		   return ResponseEntity.ok(questionarioService.toDTO(updated));
	   }

	@PatchMapping("/{id}/inativar")
	public ResponseEntity<QuestionarioDTO> inativar(@PathVariable Integer id) {
		Optional<QuestionarioBean> questionarioOpt = questionarioRepository.findById(id);
		if (questionarioOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
			QuestionarioBean questionario = questionarioOpt.get();
			questionario.setQuesAtivo(false);
			QuestionarioBean atualizado = questionarioRepository.save(questionario);
			return ResponseEntity.ok(questionarioService.toDTO(atualizado));
	}
}
