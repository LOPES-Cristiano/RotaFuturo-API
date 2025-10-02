package br.com.rotafuturo.carreiras.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.rotafuturo.carreiras.dto.AreaDTO;
import br.com.rotafuturo.carreiras.dto.AreaSubDTO;
import br.com.rotafuturo.carreiras.dto.QuestionarioDTO;
import br.com.rotafuturo.carreiras.dto.QuestionarioTipoDTO;
import br.com.rotafuturo.carreiras.model.QuestionarioBean;
import br.com.rotafuturo.carreiras.model.QuestionarioTipoBean;
import br.com.rotafuturo.carreiras.repository.AreaRepository;
import br.com.rotafuturo.carreiras.repository.AreaSubRepository;
@Service
public class QuestionarioService {
	@Autowired
	private AreaRepository areaRepository;
	@Autowired
	private AreaSubRepository areaSubRepository;
	public QuestionarioDTO toDTO(QuestionarioBean bean) {
		if (bean == null)
			return null;
		QuestionarioDTO dto = new QuestionarioDTO();
		dto.setQuesId(bean.getQuesId());
		dto.setQuesDescricao(bean.getQuesDescricao());
		dto.setQuesAtivo(true);
		dto.setQuesDatacadastro(bean.getQuesDatacadastro());
		dto.setQuesHoracadastro(bean.getQuesHoracadastro());
		dto.setQuesPeso(bean.getQuesPeso());
		if (bean.getQuestionarioTipo() != null) {
			QuestionarioTipoDTO tipoDTO = new QuestionarioTipoDTO();
			tipoDTO.setQuestId(bean.getQuestionarioTipo().getQuestId());
			tipoDTO.setQuestDescricao(bean.getQuestionarioTipo().getQuestDescricao());
			tipoDTO.setQuestAtivo(bean.getQuestionarioTipo().getQuestAtivo());
			dto.setQuestionarioTipo(tipoDTO);
		}
		if (bean.getArea() != null) {
			AreaDTO areaDTO = new AreaDTO();
			areaDTO.setAreaId(bean.getArea().getAreaId());
			areaDTO.setAreaDescricao(bean.getArea().getAreaDescricao());
			areaDTO.setAreaAtivo(bean.getArea().getAreaAtivo());
			areaDTO.setAreaDatacadastro(bean.getArea().getAreaDatacadastro());
			areaDTO.setAreaHoracadastro(bean.getArea().getAreaHoracadastro());
			dto.setArea(areaDTO);
		}
		if (bean.getAreaSub() != null) {
			AreaSubDTO areaSubDTO = new AreaSubDTO();
			areaSubDTO.setAreasId(bean.getAreaSub().getAreasId());
			areaSubDTO.setAreasDescricao(bean.getAreaSub().getAreasDescricao());
			areaSubDTO.setAreasAtivo(bean.getAreaSub().getAreasAtivo());
			areaSubDTO.setAreasDatacadastro(bean.getAreaSub().getAreasDatacadastro());
			areaSubDTO.setAreasHoracadastro(bean.getAreaSub().getAreasHoracadastro());
			areaSubDTO.setAreaId(bean.getAreaSub().getAreasId());
			dto.setAreaSub(areaSubDTO);
		}
		return dto;
	}
	public QuestionarioBean fromDTO(QuestionarioDTO dto) {
		if (dto == null)
			return null;
		QuestionarioBean bean = new QuestionarioBean();
		bean.setQuesId(dto.getQuesId());
		bean.setQuesDescricao(dto.getQuesDescricao());
		bean.setQuesAtivo(dto.getQuesAtivo());
		bean.setQuesDatacadastro(dto.getQuesDatacadastro());
		bean.setQuesHoracadastro(dto.getQuesHoracadastro());
		bean.setQuesPeso(dto.getQuesPeso());
		if (dto.getQuestionarioTipo() != null) {
			QuestionarioTipoBean tipoBean = new QuestionarioTipoBean();
			tipoBean.setQuestId(dto.getQuestionarioTipo().getQuestId());
			tipoBean.setQuestDescricao(dto.getQuestionarioTipo().getQuestDescricao());
			tipoBean.setQuestAtivo(dto.getQuestionarioTipo().getQuestAtivo());
			bean.setQuestionarioTipo(tipoBean);
		}
		if (dto.getArea() != null && dto.getArea().getAreaId() != null) {
			br.com.rotafuturo.carreiras.model.AreaBean areaBean = areaRepository.findById(dto.getArea().getAreaId())
					.orElse(null);
			bean.setArea(areaBean);
		}
		if (dto.getAreaSub() != null && dto.getAreaSub().getAreasId() != null) {
			br.com.rotafuturo.carreiras.model.AreaSubBean areaSubBean = areaSubRepository
					.findById(dto.getAreaSub().getAreasId()).orElse(null);
			bean.setAreaSub(areaSubBean);
		}
		return bean;
	}
}
