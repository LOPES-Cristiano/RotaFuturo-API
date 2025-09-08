package br.com.rotafuturo.carreiras.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.rotafuturo.carreiras.model.AreaBean;
import br.com.rotafuturo.carreiras.model.AreaSubBean;
import br.com.rotafuturo.carreiras.repository.AreaRepository;
import br.com.rotafuturo.carreiras.repository.AreaSubRepository;

import br.com.rotafuturo.carreiras.dto.MateriaDTO;
import br.com.rotafuturo.carreiras.model.MateriaBean;

@Service
public class MateriaService {
	@Autowired
	private AreaRepository areaRepository;
	@Autowired
	private AreaSubRepository areaSubRepository;

	public MateriaDTO toDTO(MateriaBean bean) {
		if (bean == null)
			return null;
		MateriaDTO dto = new MateriaDTO();
		dto.setMatId(bean.getMatId());
		dto.setMatDescricao(bean.getMatDescricao());
		dto.setMatAtivo(bean.getMatAtivo());
		dto.setMatDatacadastro(bean.getMatDatacadastro());
		dto.setMatHoracadastro(bean.getMatHoracadastro());
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

	public MateriaBean fromDTO(MateriaDTO dto) {
		if (dto == null)
			return null;
		MateriaBean bean = new MateriaBean();
		bean.setMatId(dto.getMatId());
		bean.setMatDescricao(dto.getMatDescricao());
		bean.setMatAtivo(dto.getMatAtivo());
		bean.setMatDatacadastro(dto.getMatDatacadastro());
		bean.setMatHoracadastro(dto.getMatHoracadastro());
		// Busca e vincula a área pelo ID
		if (dto.getArea() != null) {
			AreaBean area = areaRepository.findById(dto.getArea()).orElse(null);
			bean.setArea(area);
		} else {
			bean.setArea(null);
		}
		// Busca e vincula a subárea pelo ID
		if (dto.getAreaSub() != null) {
			AreaSubBean areaSub = areaSubRepository.findById(dto.getAreaSub()).orElse(null);
			bean.setAreaSub(areaSub);
		} else {
			bean.setAreaSub(null);
		}
		return bean;
	}
}
