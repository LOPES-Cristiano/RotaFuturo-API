package br.com.rotafuturo.carreiras.service;

import br.com.rotafuturo.carreiras.model.QuestionarioTipoBean;
import br.com.rotafuturo.carreiras.repository.QuestionarioTipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionarioTipoService {
	@Autowired
	private QuestionarioTipoRepository repository;

	public List<QuestionarioTipoBean> findAll() {
		return repository.findAll();
	}

	public Optional<QuestionarioTipoBean> findById(Integer id) {
		return repository.findById(id);
	}

	public QuestionarioTipoBean save(QuestionarioTipoBean bean) {
		return repository.save(bean);
	}

	public void deleteById(Integer id) {
		repository.deleteById(id);
	}
}
