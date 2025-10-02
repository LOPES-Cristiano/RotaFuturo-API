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
import br.com.rotafuturo.carreiras.dto.TesteQuestaoDTO;
import br.com.rotafuturo.carreiras.model.AreaBean;
import br.com.rotafuturo.carreiras.model.AreaSubBean;
import br.com.rotafuturo.carreiras.model.TesteBean;
import br.com.rotafuturo.carreiras.model.TesteQuestaoBean;
import br.com.rotafuturo.carreiras.model.TesteQuestaoVinculoBean;
import br.com.rotafuturo.carreiras.repository.AreaRepository;
import br.com.rotafuturo.carreiras.repository.AreaSubRepository;
import br.com.rotafuturo.carreiras.repository.TesteQuestaoVinculoRepository;
import br.com.rotafuturo.carreiras.service.TesteQuestaoService;
@RestController
@RequestMapping("/testequestao")
public class TesteQuestaoController {
	@Autowired
	private TesteQuestaoService testeQuestaoService;
	@Autowired
	private AreaRepository areaRepository;
	@Autowired
	private AreaSubRepository areaSubRepository;
	@Autowired
	private TesteQuestaoVinculoRepository testeQuestaoVinculoRepository;
	@Autowired
	private br.com.rotafuturo.carreiras.repository.TesteRepository testeRepository;
	@GetMapping
	public List<TesteQuestaoBean> getAll() {
		return testeQuestaoService.findAll();
	}
	@PostMapping
	public TesteQuestaoBean create(@RequestBody TesteQuestaoDTO dto) {
		TesteQuestaoBean testeQuestao = new TesteQuestaoBean();
		testeQuestao.setTesqDatacadastro(java.time.LocalDate.now());
		testeQuestao.setTesqHoracadastro(java.time.LocalTime.now());
		testeQuestao.setTesqDescricao(dto.getTesqDescricao());
		if (dto.getAreaId() != null) {
			AreaBean area = areaRepository.findById(dto.getAreaId()).orElse(null);
			testeQuestao.setArea(area);
		}
		if (dto.getAreaSubId() != null) {
			AreaSubBean areaSub = areaSubRepository.findById(dto.getAreaSubId()).orElse(null);
			testeQuestao.setAreaSub(areaSub);
		}
		TesteQuestaoBean savedTesteQuestao = testeQuestaoService.save(testeQuestao);
		if (savedTesteQuestao.getTesqId() != null) {
			savedTesteQuestao = testeQuestaoService.findById(savedTesteQuestao.getTesqId());
		}
		if (dto.getTesteId() != null && savedTesteQuestao.getTesqId() != null) {
			TesteBean testeBean = testeRepository.findById(dto.getTesteId()).orElse(null);
			if (testeBean != null && savedTesteQuestao != null) {
				TesteQuestaoVinculoBean vinculo = new TesteQuestaoVinculoBean();
				vinculo.setTeste(testeBean);
				vinculo.setTesteQuestao(savedTesteQuestao);
				System.out.println("[DEBUG] Vinculo a ser salvo: " + vinculo);
				if (vinculo.getTeste() != null && vinculo.getTesteQuestao() != null) {
					testeQuestaoVinculoRepository.save(vinculo);
					return savedTesteQuestao;
				} else {
					System.out.println("[ERRO] Tentativa de salvar vinculo com campos nulos: " + vinculo);
					return savedTesteQuestao;
				}
			} else {
				System.out.println("[ERRO] TesteBean ou TesteQuestaoBean nulo ao criar vinculo");
				return savedTesteQuestao;
			}
		}
		return savedTesteQuestao;
	}
	@PutMapping("/{id}")
	public TesteQuestaoBean update(@PathVariable Integer id, @RequestBody TesteQuestaoBean dto) {
		TesteQuestaoBean questao = testeQuestaoService.findById(id);
		if (questao == null)
			throw new RuntimeException("Questão não encontrada");
		questao.setTesqDescricao(dto.getTesqDescricao());
		questao.setArea(dto.getArea());
		questao.setAreaSub(dto.getAreaSub());
		if (questao.getTesqDatacadastro() == null) {
			questao.setTesqDatacadastro(java.time.LocalDate.now());
		}
		if (questao.getTesqHoracadastro() == null) {
			questao.setTesqHoracadastro(java.time.LocalTime.now());
		}
		return testeQuestaoService.save(questao);
	}
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		testeQuestaoService.deleteById(id);
	}
}
