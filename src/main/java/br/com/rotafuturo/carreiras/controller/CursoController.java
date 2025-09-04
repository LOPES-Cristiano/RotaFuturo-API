package br.com.rotafuturo.carreiras.controller;

import java.util.List;
import java.util.Optional;

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

import br.com.rotafuturo.carreiras.model.CursoBean;
import br.com.rotafuturo.carreiras.repository.CursoRepository;

@RestController
@RequestMapping("/curso")
public class CursoController {
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private br.com.rotafuturo.carreiras.service.CursoService cursoService;

    @GetMapping
    public List<br.com.rotafuturo.carreiras.dto.CursoDTO> getAll() {
        return cursoRepository.findAll().stream().map(cursoService::toDTO).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<br.com.rotafuturo.carreiras.dto.CursoDTO> getById(@PathVariable Integer id) {
        Optional<CursoBean> curso = cursoRepository.findById(id);
        return curso.map(c -> ResponseEntity.ok(cursoService.toDTO(c))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<br.com.rotafuturo.carreiras.dto.CursoDTO> create(@RequestBody br.com.rotafuturo.carreiras.dto.CursoDTO cursoDTO) {
        CursoBean curso = cursoService.fromDTO(cursoDTO);
        curso.setCurDatacadastro(java.time.LocalDate.now());
        curso.setCurHoracadastro(java.time.LocalTime.now());
        CursoBean saved = cursoRepository.save(curso);
        return new ResponseEntity<>(cursoService.toDTO(saved), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<br.com.rotafuturo.carreiras.dto.CursoDTO> update(@PathVariable Integer id, @RequestBody br.com.rotafuturo.carreiras.dto.CursoDTO cursoDTO) {
        if (!cursoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        CursoBean curso = cursoService.fromDTO(cursoDTO);
        curso.setCurId(id);
        CursoBean updated = cursoRepository.save(curso);
        return ResponseEntity.ok(cursoService.toDTO(updated));
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<br.com.rotafuturo.carreiras.dto.CursoDTO> inativar(@PathVariable Integer id) {
        Optional<CursoBean> cursoOpt = cursoRepository.findById(id);
        if (cursoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        CursoBean curso = cursoOpt.get();
        curso.setCurAtivo(false);
        CursoBean atualizado = cursoRepository.save(curso);
        return ResponseEntity.ok(cursoService.toDTO(atualizado));
    }
}
