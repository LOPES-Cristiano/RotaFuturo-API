package br.com.rotafuturo.carreiras.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.rotafuturo.carreiras.dto.CursoDTO;
import br.com.rotafuturo.carreiras.model.AreaBean;
import br.com.rotafuturo.carreiras.model.AreaSubBean;
import br.com.rotafuturo.carreiras.model.CursoBean;
import br.com.rotafuturo.carreiras.repository.AreaRepository;
import br.com.rotafuturo.carreiras.repository.AreaSubRepository;
@Service
public class CursoService {
	@Autowired
	private AreaRepository areaRepository;
	@Autowired
	private AreaSubRepository areaSubRepository;
	public CursoDTO toDTO(CursoBean bean) {
		if (bean == null)
			return null;
		CursoDTO dto = new CursoDTO();
		dto.setCurId(bean.getCurId());
		dto.setCurDescricao(bean.getCurDescricao());
		dto.setCurAtivo(bean.getCurAtivo());
		dto.setCurDatacadastro(bean.getCurDatacadastro());
		dto.setCurHoracadastro(bean.getCurHoracadastro());
		if (bean.getArea() != null) {
			dto.setArea(bean.getArea().getAreaId());
			dto.setAreaDescricao(bean.getArea().getAreaDescricao());
		}
		if (bean.getAreaSub() != null) {
			dto.setAreaSub(bean.getAreaSub().getAreasId());
			dto.setAreaSubDescricao(bean.getAreaSub().getAreasDescricao());
		}
		return dto;
	}
	public CursoBean fromDTO(CursoDTO dto) {
		if (dto == null)
			return null;
		CursoBean bean = new CursoBean();
		bean.setCurId(dto.getCurId());
		bean.setCurDescricao(dto.getCurDescricao());
		bean.setCurAtivo(dto.getCurAtivo());
		bean.setCurDatacadastro(dto.getCurDatacadastro());
		bean.setCurHoracadastro(dto.getCurHoracadastro());
		if (dto.getArea() != null) {
			AreaBean area = areaRepository.findById(dto.getArea()).orElse(null);
			bean.setArea(area);
		}
		if (dto.getAreaSub() != null) {
			AreaSubBean areaSub = areaSubRepository.findById(dto.getAreaSub()).orElse(null);
			bean.setAreaSub(areaSub);
		}
		return bean;
	}
}
