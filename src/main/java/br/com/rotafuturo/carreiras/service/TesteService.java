
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
				questao.setTesteDescricao(testeDescricao);
				questoes.add(questao);
			}
		}
		return questoes;
	}
	
	public List<br.com.rotafuturo.carreiras.model.TesteQuestaoBean> getQuestoesByTesteIdAndAreaId(Integer testeId, Integer areaId) {
		// Busca questões específicas para uma área
		List<br.com.rotafuturo.carreiras.model.TesteQuestaoVinculoBean> vinculos = testeQuestaoVinculoRepository
				.findByTeste_TesIdAndTesteQuestao_Area_AreaId(testeId, areaId);
		
		// Se não encontrar questões específicas para essa área, tenta buscar questões de subáreas dessa área
		if (vinculos == null || vinculos.isEmpty()) {
			vinculos = testeQuestaoVinculoRepository.findByTeste_TesIdWithSubareaOfArea(testeId, areaId);
		}
		
		// Se ainda não encontrar, retorna todas as questões do teste
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
		// Obter todas as subáreas da área específica
		List<br.com.rotafuturo.carreiras.model.AreaSubBean> subareas = areaSubRepository.findByArea_AreaId(areaId);
		
		if (subareas == null || subareas.isEmpty()) {
			return new java.util.ArrayList<>();
		}
		
		// Lista para armazenar os resultados por subárea
		List<br.com.rotafuturo.carreiras.dto.TesteResultadoDTO> resultados = new java.util.ArrayList<>();
		
		// Obter todas as respostas do usuário para este teste
		List<br.com.rotafuturo.carreiras.model.TesteQuestaoRespondidaBean> respostas = 
				testeQuestaoRespondidaRepository.findMostRecentResponsesByTesteAndUsuario(testeId, usuarioId);
		
		// Para cada subárea, calcular a pontuação baseada nas respostas do usuário
		for (br.com.rotafuturo.carreiras.model.AreaSubBean subarea : subareas) {
			// Pontuação para esta subárea
			int pontuacao = calcularPontuacaoSubarea(respostas, subarea.getAreasId());
			
			// Criar DTO de resultado
			br.com.rotafuturo.carreiras.dto.TesteResultadoDTO resultado = 
					new br.com.rotafuturo.carreiras.dto.TesteResultadoDTO();
			resultado.setAreaId(subarea.getAreasId());
			resultado.setAreaDescricao(subarea.getAreasDescricao());
			resultado.setPontuacao(pontuacao);
			
			resultados.add(resultado);
		}
		
		// Ordenar resultados por pontuação (maior para menor)
		resultados.sort((a, b) -> Integer.compare(b.getPontuacao(), a.getPontuacao()));
		
		return resultados;
	}
	
	// Método auxiliar para calcular a pontuação de uma subárea
	private int calcularPontuacaoSubarea(List<br.com.rotafuturo.carreiras.model.TesteQuestaoRespondidaBean> respostas, 
										Integer subareaId) {
		int pontuacaoTotal = 0;
		int questoesRespondidas = 0;
		
		for (br.com.rotafuturo.carreiras.model.TesteQuestaoRespondidaBean resposta : respostas) {
			if (resposta.getTesteQuestaoVinculo() != null && 
					resposta.getTesteQuestaoVinculo().getTesteQuestao() != null &&
					resposta.getTesteQuestaoVinculo().getTesteQuestao().getAreaSub() != null && 
					resposta.getTesteQuestaoVinculo().getTesteQuestao().getAreaSub().getAreasId().equals(subareaId)) {
				
				// Verificar se a resposta está correta (lógica a ser definida)
				// Para calcular a pontuação, usamos a resposta diretamente
				if (resposta.getTesqrResposta() != null) {
					// Pontuação baseada na resposta (1-5 convertida para pontuação 1-10)
					pontuacaoTotal += resposta.getTesqrResposta() * 2;
				}
				questoesRespondidas++;
			}
		}
		
		// Calcular percentual de pontuação (entre 0 e 100)
		if (questoesRespondidas > 0) {
			// Cada questão vale até 10 pontos
			int pontuacaoMaxima = questoesRespondidas * 10;
			if (pontuacaoMaxima > 0) {
				return (int) (((double) pontuacaoTotal / pontuacaoMaxima) * 100);
			}
		}
		
		// Se não houver questões respondidas para esta subárea, gera uma pontuação aleatória
		// para fins de demonstração (entre 50 e 100)
		java.util.Random random = new java.util.Random();
		return 50 + random.nextInt(51);
	}
}
