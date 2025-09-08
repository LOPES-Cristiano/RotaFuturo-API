package br.com.rotafuturo.carreiras.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rotafuturo.carreiras.model.QuestaoNivelBean;
import br.com.rotafuturo.carreiras.repository.QuestaoNivelRepository;

@Service
public class QuestaoNivelService {
	@Autowired
	private QuestaoNivelRepository repository;

	public List<QuestaoNivelBean> findAll() {
		return repository.findAll();
	}

	public Optional<QuestaoNivelBean> findById(Integer id) {
		return repository.findById(id);
	}

	public QuestaoNivelBean save(QuestaoNivelBean bean) {
		return repository.save(bean);
	}

	public void deleteById(Integer id) {
		repository.deleteById(id);
	}
}
