package br.com.rotafuturo.carreiras.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rotafuturo.carreiras.model.TesteQuestaoRespondidaBean;
import br.com.rotafuturo.carreiras.repository.TesteQuestaoRespondidaRepository;

@Service
public class TesteQuestaoRespondidaService {
    @Autowired
    private TesteQuestaoRespondidaRepository testeQuestaoRespondidaRepository;

    public List<TesteQuestaoRespondidaBean> findAll() {
        return testeQuestaoRespondidaRepository.findAll();
    }

    public TesteQuestaoRespondidaBean save(TesteQuestaoRespondidaBean testeQuestaoRespondida) {
        return testeQuestaoRespondidaRepository.save(testeQuestaoRespondida);
    }

    public void deleteById(Integer id) {
        testeQuestaoRespondidaRepository.deleteById(id);
    }
}
