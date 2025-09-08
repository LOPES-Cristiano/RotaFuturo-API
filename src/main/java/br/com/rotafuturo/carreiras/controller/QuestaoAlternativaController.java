package br.com.rotafuturo.carreiras.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rotafuturo.carreiras.model.QuestaoAlternativaBean;
import br.com.rotafuturo.carreiras.repository.QuestaoAlternativaRepository;

@RestController
@RequestMapping("/questao-alternativa")
public class QuestaoAlternativaController {
    @Autowired
    private QuestaoAlternativaRepository repository;

    @GetMapping
    public List<QuestaoAlternativaBean> listAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public QuestaoAlternativaBean getById(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @PostMapping
    public QuestaoAlternativaBean create(@RequestBody QuestaoAlternativaBean alternativa) {
        return repository.save(alternativa);
    }

    @PutMapping("/{id}")
    public QuestaoAlternativaBean update(@PathVariable Integer id, @RequestBody QuestaoAlternativaBean alternativa) {
        alternativa.setQuesaId(id);
        return repository.save(alternativa);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
