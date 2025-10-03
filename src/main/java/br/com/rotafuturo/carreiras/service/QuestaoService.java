package br.com.rotafuturo.carreiras.service;
import org.springframework.stereotype.Service;

import br.com.rotafuturo.carreiras.dto.QuestaoDTO;
import br.com.rotafuturo.carreiras.model.QuestaoBean;
@Service
public class QuestaoService {
	@org.springframework.beans.factory.annotation.Autowired
	private br.com.rotafuturo.carreiras.repository.AreaRepository areaRepository;
	@org.springframework.beans.factory.annotation.Autowired
	private br.com.rotafuturo.carreiras.repository.AreaSubRepository areaSubRepository;
	@org.springframework.beans.factory.annotation.Autowired
	private br.com.rotafuturo.carreiras.repository.QuestaoTipoRepository questaoTipoRepository;
	public QuestaoDTO toDTO(QuestaoBean bean) {
		if (bean == null)
			return null;
		QuestaoDTO dto = new QuestaoDTO();
		dto.setQuestaoId(bean.getQuestaoId());
		dto.setQuestaoCodigo(bean.getQuestaoCodigo());
		dto.setQuestaoDescricao(bean.getQuestaoDescricao());
		dto.setQuestaoAtivo(bean.getQuestaoAtivo());
		dto.setQuestaoDatacadastro(bean.getQuestaoDatacadastro());
		dto.setQuestaoHoracadastro(bean.getQuestaoHoracadastro());
		dto.setQuestaoExperiencia(bean.getQuestaoExperiencia());
	dto.setQuestaoNivel(bean.getNivel() != null ? bean.getNivel().getNivId() : null);
	dto.setQuestaoNivelDescricao(bean.getNivel() != null ? bean.getNivel().getNivDescricao() : null);
	dto.setQuestaoTipo(bean.getQuestaoTipo() != null ? bean.getQuestaoTipo().getQuetId() : null);
	dto.setQuestaoTipoDescricao(bean.getQuestaoTipo() != null ? bean.getQuestaoTipo().getQuetDescricao() : null);
		if (bean.getArea() != null) {
			br.com.rotafuturo.carreiras.dto.AreaDTO areaDTO = new br.com.rotafuturo.carreiras.dto.AreaDTO();
			areaDTO.setAreaId(bean.getArea().getAreaId());
			areaDTO.setAreaDescricao(bean.getArea().getAreaDescricao());
			dto.setArea(areaDTO);
		}
		if (bean.getAreaSub() != null) {
			br.com.rotafuturo.carreiras.dto.AreaSubDTO areaSubDTO = new br.com.rotafuturo.carreiras.dto.AreaSubDTO();
			areaSubDTO.setAreasId(bean.getAreaSub().getAreasId());
			areaSubDTO.setAreasDescricao(bean.getAreaSub().getAreasDescricao());
			dto.setAreaSub(areaSubDTO);
		}
		return dto;
	}
	public QuestaoBean fromDTO(QuestaoDTO dto) {
		if (dto == null)
			return null;
		QuestaoBean bean = new QuestaoBean();
		bean.setQuestaoId(dto.getQuestaoId());
		bean.setQuestaoCodigo(dto.getQuestaoCodigo());
		bean.setQuestaoDescricao(dto.getQuestaoDescricao());
		bean.setQuestaoAtivo(dto.getQuestaoAtivo());
		bean.setQuestaoDatacadastro(dto.getQuestaoDatacadastro());
		bean.setQuestaoHoracadastro(dto.getQuestaoHoracadastro());
		bean.setQuestaoExperiencia(dto.getQuestaoExperiencia());
		if (dto.getQuestaoNivel() != null) {
			br.com.rotafuturo.carreiras.model.NivelBean nivel = new br.com.rotafuturo.carreiras.model.NivelBean();
			nivel.setNivId(dto.getQuestaoNivel());
			bean.setNivel(nivel);
		}
		if (dto.getQuestaoTipo() != null) {
			br.com.rotafuturo.carreiras.model.QuestaoTipoBean tipo = questaoTipoRepository.findById(dto.getQuestaoTipo()).orElse(null);
			bean.setQuestaoTipo(tipo);
		}
		if (dto.getArea() != null && dto.getArea().getAreaId() != null) {
			bean.setArea(areaRepository.findById(dto.getArea().getAreaId()).orElse(null));
		}
		if (dto.getAreaSub() != null && dto.getAreaSub().getAreasId() != null) {
			bean.setAreaSub(areaSubRepository.findById(dto.getAreaSub().getAreasId()).orElse(null));
		}
		return bean;
	}
}
