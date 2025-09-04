package br.com.rotafuturo.carreiras.service;

import org.springframework.stereotype.Service;

import br.com.rotafuturo.carreiras.dto.AreaSubDTO;
import br.com.rotafuturo.carreiras.model.AreaSubBean;

@Service
public class AreaSubService {
    public AreaSubDTO toDTO(AreaSubBean bean) {
        if (bean == null) return null;
        AreaSubDTO dto = new AreaSubDTO();
        dto.setAreasId(bean.getAreasId());
        dto.setAreasDescricao(bean.getAreasDescricao());
        dto.setAreasAtivo(bean.getAreasAtivo());
        dto.setAreasDatacadastro(bean.getAreasDatacadastro());
        dto.setAreasHoracadastro(bean.getAreasHoracadastro());
        dto.setAreaId(bean.getArea() != null ? bean.getArea().getAreaId() : null);
        return dto;
    }
    public AreaSubBean fromDTO(AreaSubDTO dto) {
        if (dto == null) return null;
        AreaSubBean bean = new AreaSubBean();
        bean.setAreasId(dto.getAreasId());
        bean.setAreasDescricao(dto.getAreasDescricao());
        bean.setAreasAtivo(dto.getAreasAtivo());
        bean.setAreasDatacadastro(dto.getAreasDatacadastro());
        bean.setAreasHoracadastro(dto.getAreasHoracadastro());
        // AreaBean reference should be set in the controller/service using areaId
        return bean;
    }
}
