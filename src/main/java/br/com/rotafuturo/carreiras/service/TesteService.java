
package br.com.rotafuturo.carreiras.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rotafuturo.carreiras.model.TesteBean;
import br.com.rotafuturo.carreiras.repository.AreaRepository;
import br.com.rotafuturo.carreiras.repository.AreaSubRepository;
import br.com.rotafuturo.carreiras.repository.TesteRepository;

@Service
public class TesteService {
	@Autowired
	private TesteRepository testeRepository;
	@Autowired
	private AreaRepository areaRepository;
	@Autowired
	private AreaSubRepository areaSubRepository;

	public List<TesteBean> findAll() {
		return testeRepository.findAll();
	}

	public TesteBean findById(Integer id) {
		return testeRepository.findById(id).orElse(null);
	}

	public TesteBean save(TesteBean teste) {
		if (teste.getTesDatacadastro() == null) {
			teste.setTesDatacadastro(java.time.LocalDate.now());
		}
		if (teste.getTesHoracadastro() == null) {
			teste.setTesHoracadastro(java.time.LocalTime.now());
		}
		return testeRepository.save(teste);
	}

	public void deleteById(Integer id) {
		testeRepository.deleteById(id);
	}

	@Autowired
	private br.com.rotafuturo.carreiras.repository.TesteQuestaoVinculoRepository testeQuestaoVinculoRepository;

	public List<br.com.rotafuturo.carreiras.model.TesteQuestaoBean> getQuestoesByTesteId(Integer testeId) {
		List<br.com.rotafuturo.carreiras.model.TesteQuestaoVinculoBean> vinculos = testeQuestaoVinculoRepository.findByTeste_TesId(testeId);
		List<br.com.rotafuturo.carreiras.model.TesteQuestaoBean> questoes = new java.util.ArrayList<>();
		String testeDescricao = null;
		if (!vinculos.isEmpty() && vinculos.get(0).getTeste() != null) {
			testeDescricao = vinculos.get(0).getTeste().getTesDescricao();
		}
		for (br.com.rotafuturo.carreiras.model.TesteQuestaoVinculoBean vinculo : vinculos) {
			if (vinculo.getTesteQuestao() != null) {
				br.com.rotafuturo.carreiras.model.TesteQuestaoBean questao = vinculo.getTesteQuestao();
				try {
					java.lang.reflect.Field f = questao.getClass().getDeclaredField("testeDescricao");
					f.setAccessible(true);
					f.set(questao, testeDescricao);
				} catch (Exception e) {
					// Ignora se o campo não existir
				}
				questoes.add(questao);
			}
		}
		return questoes;
	}
}
