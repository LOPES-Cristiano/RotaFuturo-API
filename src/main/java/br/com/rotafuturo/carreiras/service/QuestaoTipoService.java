package br.com.rotafuturo.carreiras.service;

import br.com.rotafuturo.carreiras.model.QuestaoTipoBean;
import br.com.rotafuturo.carreiras.repository.QuestaoTipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestaoTipoService {
	@Autowired
	private QuestaoTipoRepository repository;

	public List<QuestaoTipoBean> findAll() {
		return repository.findAll();
	}

	public Optional<QuestaoTipoBean> findById(Integer id) {
		return repository.findById(id);
	}

	public QuestaoTipoBean save(QuestaoTipoBean bean) {
		return repository.save(bean);
	}

	public void deleteById(Integer id) {
		repository.deleteById(id);
	}
}
