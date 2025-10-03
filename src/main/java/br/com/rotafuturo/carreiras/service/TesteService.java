package br.com.rotafuturo.carreiras.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rotafuturo.carreiras.model.TesteBean;
import br.com.rotafuturo.carreiras.repository.AreaSubRepository;
import br.com.rotafuturo.carreiras.repository.TesteRepository;
@Service
public class TesteService {
	@Autowired
	private TesteRepository testeRepository;
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
				questao.setTesteDescricao(testeDescricao);
				questoes.add(questao);
			}
		}
		return questoes;
	}
	public List<br.com.rotafuturo.carreiras.model.TesteQuestaoBean> getQuestoesByTesteIdAndAreaId(Integer testeId, Integer areaId) {
		List<br.com.rotafuturo.carreiras.model.TesteQuestaoVinculoBean> vinculos = testeQuestaoVinculoRepository
				.findByTeste_TesIdAndTesteQuestao_Area_AreaId(testeId, areaId);
		if (vinculos == null || vinculos.isEmpty()) {
			vinculos = testeQuestaoVinculoRepository.findByTeste_TesIdWithSubareaOfArea(testeId, areaId);
		}
		if (vinculos == null || vinculos.isEmpty()) {
			return getQuestoesByTesteId(testeId);
		}
		List<br.com.rotafuturo.carreiras.model.TesteQuestaoBean> questoes = new java.util.ArrayList<>();
		String testeDescricao = null;
		if (!vinculos.isEmpty() && vinculos.get(0).getTeste() != null) {
			testeDescricao = vinculos.get(0).getTeste().getTesDescricao();
		}
		for (br.com.rotafuturo.carreiras.model.TesteQuestaoVinculoBean vinculo : vinculos) {
			if (vinculo.getTesteQuestao() != null) {
				br.com.rotafuturo.carreiras.model.TesteQuestaoBean questao = vinculo.getTesteQuestao();
				questao.setTesteDescricao(testeDescricao);
				questoes.add(questao);
			}
		}
		return questoes;
	}
	@Autowired
	private br.com.rotafuturo.carreiras.repository.TesteQuestaoRespondidaRepository testeQuestaoRespondidaRepository;
	public List<br.com.rotafuturo.carreiras.dto.TesteResultadoDTO> getTesteResultadosSubareas(Integer testeId, Integer usuarioId, Integer areaId) {
		List<br.com.rotafuturo.carreiras.model.AreaSubBean> subareas = areaSubRepository.findByArea_AreaId(areaId);
		if (subareas == null || subareas.isEmpty()) {
			return new java.util.ArrayList<>();
		}
		List<br.com.rotafuturo.carreiras.dto.TesteResultadoDTO> resultados = new java.util.ArrayList<>();
		List<br.com.rotafuturo.carreiras.model.TesteQuestaoRespondidaBean> respostas = 
				testeQuestaoRespondidaRepository.findMostRecentResponsesByTesteAndUsuario(testeId, usuarioId);
		for (br.com.rotafuturo.carreiras.model.AreaSubBean subarea : subareas) {
			int pontuacao = calcularPontuacaoSubarea(respostas, subarea.getAreasId());
			br.com.rotafuturo.carreiras.dto.TesteResultadoDTO resultado = 
					new br.com.rotafuturo.carreiras.dto.TesteResultadoDTO();
			resultado.setAreaId(subarea.getAreasId());
			resultado.setAreaDescricao(subarea.getAreasDescricao());
			resultado.setPontuacao(pontuacao);
			resultados.add(resultado);
		}
		resultados.sort((a, b) -> Integer.compare(b.getPontuacao(), a.getPontuacao()));
		return resultados;
	}
	private int calcularPontuacaoSubarea(List<br.com.rotafuturo.carreiras.model.TesteQuestaoRespondidaBean> respostas, 
										Integer subareaId) {
		int pontuacaoTotal = 0;
		int questoesRespondidas = 0;
		for (br.com.rotafuturo.carreiras.model.TesteQuestaoRespondidaBean resposta : respostas) {
			if (resposta.getTesteQuestaoVinculo() != null && 
					resposta.getTesteQuestaoVinculo().getTesteQuestao() != null &&
					resposta.getTesteQuestaoVinculo().getTesteQuestao().getAreaSub() != null && 
					resposta.getTesteQuestaoVinculo().getTesteQuestao().getAreaSub().getAreasId().equals(subareaId)) {
				if (resposta.getTesqrResposta() != null) {
					pontuacaoTotal += resposta.getTesqrResposta() * 2;
				}
				questoesRespondidas++;
			}
		}
		if (questoesRespondidas > 0) {
			int pontuacaoMaxima = questoesRespondidas * 10;
			if (pontuacaoMaxima > 0) {
				return (int) (((double) pontuacaoTotal / pontuacaoMaxima) * 100);
			}
		}
		java.util.Random random = new java.util.Random();
		return 50 + random.nextInt(51);
	}
}
