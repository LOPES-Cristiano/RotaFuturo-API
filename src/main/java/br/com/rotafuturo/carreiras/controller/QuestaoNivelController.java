package br.com.rotafuturo.carreiras.controller;

import br.com.rotafuturo.carreiras.model.QuestaoNivelBean;
import br.com.rotafuturo.carreiras.service.QuestaoNivelService;
import br.com.rotafuturo.carreiras.dto.QuestaoNivelDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/questao-nivel")
public class QuestaoNivelController {
	@Autowired
	private QuestaoNivelService service;

	@GetMapping
	public List<QuestaoNivelDTO> listAll() {
		return service.findAll().stream().map(this::toDTO).collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public QuestaoNivelDTO getById(@PathVariable Integer id) {
		return service.findById(id).map(this::toDTO).orElse(null);
	}

	@PostMapping
	public QuestaoNivelDTO create(@RequestBody QuestaoNivelDTO dto) {
		QuestaoNivelBean bean = fromDTO(dto);
		return toDTO(service.save(bean));
	}

	@PutMapping("/{id}")
	public QuestaoNivelDTO update(@PathVariable Integer id, @RequestBody QuestaoNivelDTO dto) {
		QuestaoNivelBean bean = fromDTO(dto);
		bean.setQuesnId(id);
		return toDTO(service.save(bean));
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		service.deleteById(id);
	}

	// Conversão para compatibilidade frontend
	private QuestaoNivelDTO toDTO(QuestaoNivelBean bean) {
		QuestaoNivelDTO dto = new QuestaoNivelDTO();
		dto.setQuesnId(bean.getQuesnId());
		dto.setQuesnDescricao(bean.getQuesnDescricao());
		dto.setQuesnAtivo(bean.getQuesnAtivo());
		return dto;
	}

	private QuestaoNivelBean fromDTO(QuestaoNivelDTO dto) {
		QuestaoNivelBean bean = new QuestaoNivelBean();
		bean.setQuesnId(dto.getQuesnId());
		bean.setQuesnDescricao(dto.getQuesnDescricao());
		bean.setQuesnAtivo(dto.getQuesnAtivo());
		return bean;
	}
}
