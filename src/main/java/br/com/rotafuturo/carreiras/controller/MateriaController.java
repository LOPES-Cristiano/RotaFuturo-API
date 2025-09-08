package br.com.rotafuturo.carreiras.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import br.com.rotafuturo.carreiras.dto.MateriaDTO;
import br.com.rotafuturo.carreiras.model.MateriaBean;
import br.com.rotafuturo.carreiras.repository.MateriaRepository;

@RestController
@RequestMapping("/materia")
public class MateriaController {
	@Autowired
	private MateriaRepository materiaRepository;
	@Autowired
	private br.com.rotafuturo.carreiras.service.MateriaService materiaService;

	@GetMapping
	public List<br.com.rotafuturo.carreiras.dto.MateriaDTO> getAll() {
		return materiaRepository.findAll().stream().map(materiaService::toDTO).toList();
	}

	@GetMapping("/{id}")
	public ResponseEntity<br.com.rotafuturo.carreiras.dto.MateriaDTO> getById(@PathVariable Integer id) {
		Optional<MateriaBean> materia = materiaRepository.findById(id);
		return materia.map(m -> ResponseEntity.ok(materiaService.toDTO(m)))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<br.com.rotafuturo.carreiras.dto.MateriaDTO> create(
			@RequestBody br.com.rotafuturo.carreiras.dto.MateriaDTO materiaDTO) {
		MateriaBean materia = materiaService.fromDTO(materiaDTO);
		materia.setMatDatacadastro(java.time.LocalDate.now());
		materia.setMatHoracadastro(java.time.LocalTime.now());
		MateriaBean saved = materiaRepository.save(materia);
		return new ResponseEntity<>(materiaService.toDTO(saved), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<br.com.rotafuturo.carreiras.dto.MateriaDTO> update(@PathVariable Integer id,
			@RequestBody br.com.rotafuturo.carreiras.dto.MateriaDTO materiaDTO) {
		if (!materiaRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		MateriaBean materia = materiaService.fromDTO(materiaDTO);
		materia.setMatId(id);
		MateriaBean updated = materiaRepository.save(materia);
		return ResponseEntity.ok(materiaService.toDTO(updated));
	}

	@PatchMapping("/{id}/inativar")
	public ResponseEntity<br.com.rotafuturo.carreiras.dto.MateriaDTO> inativar(@PathVariable Integer id) {
		Optional<MateriaBean> materiaOpt = materiaRepository.findById(id);
		if (materiaOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		MateriaBean materia = materiaOpt.get();
		materia.setMatAtivo(false);
		MateriaBean atualizado = materiaRepository.save(materia);
		return ResponseEntity.ok(materiaService.toDTO(atualizado));
	}

	@GetMapping("/materia")
	public List<MateriaDTO> listarMaterias() {
		List<MateriaBean> materias = materiaRepository.findAll();
		return materias.stream().map(materiaService::toDTO).collect(Collectors.toList());
	}
}
