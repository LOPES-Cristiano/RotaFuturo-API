package br.com.rotafuturo.carreiras.service;

import org.springframework.stereotype.Service;

import br.com.rotafuturo.carreiras.dto.AreaDTO;
import br.com.rotafuturo.carreiras.model.AreaBean;

@Service
public class AreaService {
	public AreaDTO toDTO(AreaBean bean) {
		if (bean == null)
			return null;
		AreaDTO dto = new AreaDTO();
		dto.setAreaId(bean.getAreaId());
		dto.setAreaDescricao(bean.getAreaDescricao());
		dto.setAreaAtivo(bean.getAreaAtivo());
		dto.setAreaDatacadastro(bean.getAreaDatacadastro());
		dto.setAreaHoracadastro(bean.getAreaHoracadastro());
		return dto;
	}

	public AreaBean fromDTO(AreaDTO dto) {
		if (dto == null)
			return null;
		AreaBean bean = new AreaBean();
		bean.setAreaId(dto.getAreaId());
		bean.setAreaDescricao(dto.getAreaDescricao());
		bean.setAreaAtivo(true);
		bean.setAreaDatacadastro(dto.getAreaDatacadastro());
		bean.setAreaHoracadastro(dto.getAreaHoracadastro());
		return bean;
	}
}
