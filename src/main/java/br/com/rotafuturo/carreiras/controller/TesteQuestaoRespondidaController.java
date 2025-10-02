package br.com.rotafuturo.carreiras.controller;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.rotafuturo.carreiras.dto.TesteQuestaoRespondidaDTO;
import br.com.rotafuturo.carreiras.dto.TesteResultadoDTO;
import br.com.rotafuturo.carreiras.dto.TesteSubareaResultadoDTO;
import br.com.rotafuturo.carreiras.model.TesteQuestaoRespondidaBean;
import br.com.rotafuturo.carreiras.repository.TesteQuestaoVinculoRepository;
import br.com.rotafuturo.carreiras.repository.UsuarioRepository;
import br.com.rotafuturo.carreiras.service.TesteQuestaoRespondidaService;
@RestController
@RequestMapping("/testequestaorespondida")
public class TesteQuestaoRespondidaController {
    @Autowired
    private TesteQuestaoRespondidaService testeQuestaoRespondidaService;
    @Autowired
    private TesteQuestaoVinculoRepository testeQuestaoVinculoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @GetMapping
    public List<TesteQuestaoRespondidaBean> getAll() {
        return testeQuestaoRespondidaService.findAll();
    }
    @PostMapping
    public TesteQuestaoRespondidaBean create(@RequestBody TesteQuestaoRespondidaDTO dto) {
        TesteQuestaoRespondidaBean bean = new TesteQuestaoRespondidaBean();
        bean.setTesqrResposta(dto.tesqrResposta);
        bean.setTesteQuestaoVinculoId(dto.testeQuestaoVinculoId);
        if (dto.testeQuestaoVinculoId != null) {
            testeQuestaoVinculoRepository.findById(dto.testeQuestaoVinculoId)
                .ifPresent(bean::setTesteQuestaoVinculo);
        }
        if (dto.usuarioId != null) {
            usuarioRepository.findById(dto.usuarioId)
                .ifPresent(bean::setUsuario);
        }
        bean.setTesqrDatacadastro(LocalDate.now());
        bean.setTesqrHoracadastro(LocalTime.now());
        return testeQuestaoRespondidaService.save(bean);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        testeQuestaoRespondidaService.deleteById(id);
    }
    @GetMapping("/resultado/{testeId}/{usuarioId}")
    public List<TesteResultadoDTO> getResultadoByTesteAndUsuario(
            @PathVariable Integer testeId,
            @PathVariable Integer usuarioId) {
        System.out.println("Solicitando resultado para teste ID: " + testeId + " e usuário ID: " + usuarioId);
        return testeQuestaoRespondidaService.calcularResultadoPorArea(testeId, usuarioId);
    }
    @PostMapping("/vincular-area/{usuarioId}/{areaId}")
    public ResponseEntity<?> vincularUsuarioArea(
            @PathVariable Integer usuarioId,
            @PathVariable Integer areaId) {
        try {
            testeQuestaoRespondidaService.vincularUsuarioAreaMaisCompativel(usuarioId, areaId);
            return ResponseEntity.ok().body(Map.of("message", "Usuário vinculado à área com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    @GetMapping("/resultado-subarea/{testeId}/{usuarioId}/{areaId}")
    public List<TesteSubareaResultadoDTO> getResultadoSubareasByTesteAndUsuario(
            @PathVariable Integer testeId,
            @PathVariable Integer usuarioId,
            @PathVariable Integer areaId) {
        System.out.println("Solicitando resultado de subáreas para teste ID: " + testeId + 
                           ", usuário ID: " + usuarioId + " e área ID: " + areaId);
        return testeQuestaoRespondidaService.calcularResultadoPorSubarea(testeId, usuarioId, areaId);
    }
    @PostMapping("/vincular-subarea/{usuarioId}/{subareaId}")
    public ResponseEntity<?> vincularUsuarioSubarea(
            @PathVariable Integer usuarioId,
            @PathVariable Integer subareaId) {
        try {
            testeQuestaoRespondidaService.vincularUsuarioSubareaMaisCompativel(usuarioId, subareaId);
            return ResponseEntity.ok().body(Map.of("message", "Usuário vinculado à subárea com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
