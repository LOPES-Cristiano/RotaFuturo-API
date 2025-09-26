package br.com.rotafuturo.carreiras.controller;

import br.com.rotafuturo.carreiras.model.TesteQuestaoRespondidaBean;
import br.com.rotafuturo.carreiras.service.TesteQuestaoRespondidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/testequestaorespondida")
public class TesteQuestaoRespondidaController {
    @Autowired
    private TesteQuestaoRespondidaService testeQuestaoRespondidaService;

    @GetMapping
    public List<TesteQuestaoRespondidaBean> getAll() {
        return testeQuestaoRespondidaService.findAll();
    }

    @PostMapping
    public TesteQuestaoRespondidaBean create(@RequestBody TesteQuestaoRespondidaBean testeQuestaoRespondida) {
        return testeQuestaoRespondidaService.save(testeQuestaoRespondida);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        testeQuestaoRespondidaService.deleteById(id);
    }
}
