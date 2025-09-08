package br.com.rotafuturo.carreiras.controller;

import br.com.rotafuturo.carreiras.model.QuestaoTipoBean;
import br.com.rotafuturo.carreiras.service.QuestaoTipoService;
import br.com.rotafuturo.carreiras.dto.QuestaoTipoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/questao-tipo")
public class QuestaoTipoController {
	@Autowired
	private QuestaoTipoService service;

	@GetMapping
	public List<QuestaoTipoDTO> listAll() {
		return service.findAll().stream().map(this::toDTO).collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public QuestaoTipoDTO getById(@PathVariable Integer id) {
		return service.findById(id).map(this::toDTO).orElse(null);
	}

	@PostMapping
	public QuestaoTipoDTO create(@RequestBody QuestaoTipoDTO dto) {
		QuestaoTipoBean bean = fromDTO(dto);
		return toDTO(service.save(bean));
	}

	@PutMapping("/{id}")
	public QuestaoTipoDTO update(@PathVariable Integer id, @RequestBody QuestaoTipoDTO dto) {
		QuestaoTipoBean bean = fromDTO(dto);
		bean.setQuetId(id);
		return toDTO(service.save(bean));
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		service.deleteById(id);
	}

	// Conversão para compatibilidade frontend
	private QuestaoTipoDTO toDTO(QuestaoTipoBean bean) {
		QuestaoTipoDTO dto = new QuestaoTipoDTO();
		dto.setQuetId(bean.getQuetId());
		dto.setQuetDescricao(bean.getQuetDescricao());
		dto.setQuetAtivo(bean.getQuetAtivo());
		return dto;
	}

	private QuestaoTipoBean fromDTO(QuestaoTipoDTO dto) {
		QuestaoTipoBean bean = new QuestaoTipoBean();
		bean.setQuetId(dto.getQuetId());
		bean.setQuetDescricao(dto.getQuetDescricao());
		bean.setQuetAtivo(dto.getQuetAtivo());
		return bean;
	}
}
