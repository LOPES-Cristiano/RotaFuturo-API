package br.com.rotafuturo.carreiras.controller;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rotafuturo.carreiras.dto.QuestaoNivelDTO;
import br.com.rotafuturo.carreiras.model.QuestaoNivelBean;
import br.com.rotafuturo.carreiras.service.QuestaoNivelService;
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
