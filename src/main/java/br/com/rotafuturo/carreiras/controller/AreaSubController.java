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

import br.com.rotafuturo.carreiras.model.AreaSubBean;
import br.com.rotafuturo.carreiras.repository.AreaSubRepository;
import br.com.rotafuturo.carreiras.repository.AreaRepository;

@RestController
@RequestMapping("/areasub")
public class AreaSubController {
	@GetMapping(params = "search")
	public List<br.com.rotafuturo.carreiras.dto.AreaSubDTO> search(
			@org.springframework.web.bind.annotation.RequestParam("search") String search) {
		return areaSubRepository.findAll().stream()
				.filter(a -> a.getAreasDescricao() != null
						&& a.getAreasDescricao().toLowerCase().contains(search.toLowerCase()))
				.map(areaSubService::toDTO).toList();
	}

	@Autowired
	private AreaSubRepository areaSubRepository;
	@Autowired
	private br.com.rotafuturo.carreiras.service.AreaSubService areaSubService;
	@Autowired
	private AreaRepository areaRepository;

	@GetMapping
	public List<br.com.rotafuturo.carreiras.dto.AreaSubDTO> getAll() {
		return areaSubRepository.findAll().stream().map(areaSubService::toDTO).toList();
	}

	@GetMapping("/{id}")
	public ResponseEntity<br.com.rotafuturo.carreiras.dto.AreaSubDTO> getById(@PathVariable Integer id) {
		Optional<AreaSubBean> areaSub = areaSubRepository.findById(id);
		return areaSub.map(a -> ResponseEntity.ok(areaSubService.toDTO(a)))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<br.com.rotafuturo.carreiras.dto.AreaSubDTO> create(
			@RequestBody br.com.rotafuturo.carreiras.dto.AreaSubDTO areaSubDTO) {
		AreaSubBean areaSub = areaSubService.fromDTO(areaSubDTO);
		areaSub.setAreasDatacadastro(java.time.LocalDate.now());
		areaSub.setAreasHoracadastro(java.time.LocalTime.now());
		if (areaSubDTO.getAreaId() != null) {
			areaRepository.findById(areaSubDTO.getAreaId()).ifPresent(areaSub::setArea);
		}
		AreaSubBean saved = areaSubRepository.save(areaSub);
		return new ResponseEntity<>(areaSubService.toDTO(saved), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<br.com.rotafuturo.carreiras.dto.AreaSubDTO> update(@PathVariable Integer id,
			@RequestBody br.com.rotafuturo.carreiras.dto.AreaSubDTO areaSubDTO) {
		java.util.Optional<AreaSubBean> existingOpt = areaSubRepository.findById(id);
		if (existingOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		AreaSubBean existing = existingOpt.get();
		// Atualiza apenas os campos editáveis
		existing.setAreasDescricao(areaSubDTO.getAreasDescricao());
		if (areaSubDTO.getAreaId() != null) {
			areaRepository.findById(areaSubDTO.getAreaId()).ifPresent(existing::setArea);
		}
		if (areaSubDTO.getAreasAtivo() != null) {
			existing.setAreasAtivo(areaSubDTO.getAreasAtivo());
		}
		AreaSubBean updated = areaSubRepository.save(existing);
		return ResponseEntity.ok(areaSubService.toDTO(updated));
	}

	@PatchMapping("/{id}/inativar")
	public ResponseEntity<br.com.rotafuturo.carreiras.dto.AreaSubDTO> inativar(@PathVariable Integer id) {
		Optional<AreaSubBean> areaSubOpt = areaSubRepository.findById(id);
		if (areaSubOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		AreaSubBean areaSub = areaSubOpt.get();
		areaSub.setAreasAtivo(false);
		AreaSubBean atualizado = areaSubRepository.save(areaSub);
		return ResponseEntity.ok(areaSubService.toDTO(atualizado));
	}
}
