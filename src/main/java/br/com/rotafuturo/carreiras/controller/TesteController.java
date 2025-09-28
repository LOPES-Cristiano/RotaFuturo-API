package br.com.rotafuturo.carreiras.controller;

import java.util.List;

import  org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rotafuturo.carreiras.dto.TesteDTO;
import br.com.rotafuturo.carreiras.model.AreaBean;
import br.com.rotafuturo.carreiras.model.AreaSubBean;
import br.com.rotafuturo.carreiras.model.TesteBean;
import br.com.rotafuturo.carreiras.repository.AreaRepository;
import br.com.rotafuturo.carreiras.repository.AreaSubRepository;
import br.com.rotafuturo.carreiras.service.TesteService;

@RestController
@RequestMapping("/teste")
public class TesteController {
    @Autowired
    private TesteService testeService;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private AreaSubRepository areaSubRepository;
    @GetMapping
    public List<TesteBean> getAll() {
        return testeService.findAll();
    }

    // Endpoint para buscar todas as questões de um teste
    // Vinculo handling is now managed automatically in TesteQuestaoController.

    @PostMapping
    public TesteBean create(@RequestBody TesteDTO dto) {
        TesteBean teste = new TesteBean();
        // Nunca setar tesId no create
        teste.setTesDatacadastro(dto.tesDatacadastro);
        teste.setTesHoracadastro(dto.tesHoracadastro);
    // ...existing code...
        if (dto.areaSubId != null) {
            AreaSubBean areaSub = areaSubRepository.findById(dto.areaSubId).orElse(null);
            teste.setAreaSub(areaSub);
        }
        return testeService.save(teste);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        testeService.deleteById(id);
    }

     @PutMapping("/{id}")
    public TesteBean update(@PathVariable Integer id, @RequestBody TesteDTO dto) {
        TesteBean teste = testeService.findById(id);
        if (teste == null) throw new RuntimeException("Teste não encontrado");
        teste.setTesDescricao(dto.tesDescricao);
        if (dto.areaId != null) {
            AreaBean area = areaRepository.findById(dto.areaId).orElse(null);
            teste.setArea(area);
        }
        if (dto.areaSubId != null) {
            AreaSubBean areaSub = areaSubRepository.findById(dto.areaSubId).orElse(null);
            teste.setAreaSub(areaSub);
        }
        return testeService.save(teste);
    }

        @GetMapping("/{id}/questoes")
    public List<br.com.rotafuturo.carreiras.model.TesteQuestaoBean> getQuestoesByTeste(@PathVariable Integer id) {
        // Retorna todas as questões vinculadas ao teste
        return testeService.getQuestoesByTesteId(id);
    }
    
    @GetMapping("/{id}/questoes/area/{areaId}")
    public List<br.com.rotafuturo.carreiras.model.TesteQuestaoBean> getQuestoesByTesteAndArea(
            @PathVariable Integer id, @PathVariable Integer areaId) {
        // Retorna questões vinculadas ao teste e à área específica
        return testeService.getQuestoesByTesteIdAndAreaId(id, areaId);
    }
    
    @GetMapping("/{id}/resultado/{usuarioId}/area/{areaId}")
    public List<br.com.rotafuturo.carreiras.dto.TesteResultadoDTO> getTesteResultadosSubareas(
            @PathVariable Integer id, 
            @PathVariable Integer usuarioId,
            @PathVariable Integer areaId) {
        // Retorna os resultados do teste de subárea para o usuário específico na área específica
        return testeService.getTesteResultadosSubareas(id, usuarioId, areaId);
    }
    
}
