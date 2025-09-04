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

import br.com.rotafuturo.carreiras.model.AreaBean;
import br.com.rotafuturo.carreiras.repository.AreaRepository;

@RestController
@RequestMapping("/area")
public class AreaController {
    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private br.com.rotafuturo.carreiras.service.AreaService areaService;

    @GetMapping
    public List<br.com.rotafuturo.carreiras.dto.AreaDTO> getAll() {
        return areaRepository.findAll().stream().map(areaService::toDTO).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<br.com.rotafuturo.carreiras.dto.AreaDTO> getById(@PathVariable Integer id) {
        Optional<AreaBean> area = areaRepository.findById(id);
        return area.map(a -> ResponseEntity.ok(areaService.toDTO(a))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<br.com.rotafuturo.carreiras.dto.AreaDTO> create(@RequestBody br.com.rotafuturo.carreiras.dto.AreaDTO areaDTO) {
        AreaBean area = areaService.fromDTO(areaDTO);
        area.setAreaDatacadastro(java.time.LocalDate.now());
        area.setAreaHoracadastro(java.time.LocalTime.now());
        AreaBean saved = areaRepository.save(area);
        return new ResponseEntity<>(areaService.toDTO(saved), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<br.com.rotafuturo.carreiras.dto.AreaDTO> update(@PathVariable Integer id, @RequestBody br.com.rotafuturo.carreiras.dto.AreaDTO areaDTO) {
        if (!areaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        AreaBean area = areaService.fromDTO(areaDTO);
        area.setAreaId(id);
        AreaBean updated = areaRepository.save(area);
        return ResponseEntity.ok(areaService.toDTO(updated));
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<br.com.rotafuturo.carreiras.dto.AreaDTO> inativar(@PathVariable Integer id) {
        Optional<AreaBean> areaOpt = areaRepository.findById(id);
        if (areaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        AreaBean area = areaOpt.get();
        area.setAreaAtivo(false);
        AreaBean atualizado = areaRepository.save(area);
        return ResponseEntity.ok(areaService.toDTO(atualizado));
    }
    
}
