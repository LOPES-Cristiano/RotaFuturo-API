package br.com.rotafuturo.carreiras.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rotafuturo.carreiras.model.QuestaoBean;
import br.com.rotafuturo.carreiras.repository.QuestaoRepository;

@RestController
@RequestMapping("/questao")
public class QuestaoController {
    @Autowired
    private QuestaoRepository questaoRepository;
    @Autowired
    private br.com.rotafuturo.carreiras.service.QuestaoService questaoService;

    @GetMapping
    public List<br.com.rotafuturo.carreiras.dto.QuestaoDTO> getAll() {
        return questaoRepository.findAll().stream().map(questaoService::toDTO).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<br.com.rotafuturo.carreiras.dto.QuestaoDTO> getById(@PathVariable Integer id) {
        return questaoRepository.findById(id)
            .map(q -> ResponseEntity.ok(questaoService.toDTO(q)))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<br.com.rotafuturo.carreiras.dto.QuestaoDTO> create(@RequestBody br.com.rotafuturo.carreiras.dto.QuestaoDTO questaoDTO) {
    QuestaoBean questao = questaoService.fromDTO(questaoDTO);
    questao.setQuestaoDatacadastro(java.time.LocalDate.now());
    questao.setQuestaoHoracadastro(java.time.LocalTime.now());
    // Garante que a questão será criada como ativa
    questao.setQuestaoAtivo(true);
    // Os campos questaoCodigo e questaoDescricao já são setados via fromDTO
    QuestaoBean saved = questaoRepository.save(questao);
    return new ResponseEntity<>(questaoService.toDTO(saved), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<br.com.rotafuturo.carreiras.dto.QuestaoDTO> update(@PathVariable Integer id, @RequestBody br.com.rotafuturo.carreiras.dto.QuestaoDTO questaoDTO) {
        if (!questaoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        QuestaoBean questao = questaoService.fromDTO(questaoDTO);
        questao.setQuestaoId(id);
        // Os campos questaoCodigo e questaoDescricao já são setados via fromDTO
        QuestaoBean updated = questaoRepository.save(questao);
        return ResponseEntity.ok(questaoService.toDTO(updated));
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<br.com.rotafuturo.carreiras.dto.QuestaoDTO> inativar(@PathVariable Integer id) {
        java.util.Optional<QuestaoBean> questaoOpt = questaoRepository.findById(id);
        if (questaoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        QuestaoBean questao = questaoOpt.get();
        questao.setQuestaoAtivo(false);
        QuestaoBean atualizado = questaoRepository.save(questao);
        return ResponseEntity.ok(questaoService.toDTO(atualizado));
    }
}
