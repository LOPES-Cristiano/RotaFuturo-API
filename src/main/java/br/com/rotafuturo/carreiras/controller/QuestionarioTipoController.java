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

import br.com.rotafuturo.carreiras.dto.QuestionarioTipoDTO;
import br.com.rotafuturo.carreiras.model.QuestionarioTipoBean;
import br.com.rotafuturo.carreiras.service.QuestionarioTipoService;
@RestController
@RequestMapping("/questionario-tipo")
public class QuestionarioTipoController {
	@Autowired
	private QuestionarioTipoService service;
	@GetMapping
	public List<QuestionarioTipoDTO> listAll() {
		return service.findAll().stream().map(this::toDTO).collect(Collectors.toList());
	}
	@GetMapping("/{id}")
	public QuestionarioTipoDTO getById(@PathVariable Integer id) {
		return service.findById(id).map(this::toDTO).orElse(null);
	}
	@PostMapping
	public QuestionarioTipoDTO create(@RequestBody QuestionarioTipoDTO dto) {
		QuestionarioTipoBean bean = fromDTO(dto);
		return toDTO(service.save(bean));
	}
	@PutMapping("/{id}")
	public QuestionarioTipoDTO update(@PathVariable Integer id, @RequestBody QuestionarioTipoDTO dto) {
		QuestionarioTipoBean bean = fromDTO(dto);
		bean.setQuestId(id);
		return toDTO(service.save(bean));
	}
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		service.deleteById(id);
	}
	private QuestionarioTipoDTO toDTO(QuestionarioTipoBean bean) {
		QuestionarioTipoDTO dto = new QuestionarioTipoDTO();
		dto.setQuestId(bean.getQuestId());
		dto.setQuestDescricao(bean.getQuestDescricao());
		dto.setQuestAtivo(bean.getQuestAtivo());
		return dto;
	}
	private QuestionarioTipoBean fromDTO(QuestionarioTipoDTO dto) {
		QuestionarioTipoBean bean = new QuestionarioTipoBean();
		bean.setQuestId(dto.getQuestId());
		bean.setQuestDescricao(dto.getQuestDescricao());
		bean.setQuestAtivo(dto.getQuestAtivo());
		return bean;
	}
}
