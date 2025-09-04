package br.com.rotafuturo.carreiras.service;

import org.springframework.stereotype.Service;

import br.com.rotafuturo.carreiras.dto.CursoDTO;
import br.com.rotafuturo.carreiras.model.CursoBean;

@Service
public class CursoService {
    public CursoDTO toDTO(CursoBean bean) {
        if (bean == null) return null;
        CursoDTO dto = new CursoDTO();
        dto.setCurId(bean.getCurId());
        dto.setCurDescricao(bean.getCurDescricao());
        dto.setCurAtivo(bean.getCurAtivo());
        dto.setCurDatacadastro(bean.getCurDatacadastro());
        dto.setCurHoracadastro(bean.getCurHoracadastro());
        return dto;
    }
    public CursoBean fromDTO(CursoDTO dto) {
        if (dto == null) return null;
        CursoBean bean = new CursoBean();
        bean.setCurId(dto.getCurId());
        bean.setCurDescricao(dto.getCurDescricao());
        bean.setCurAtivo(dto.getCurAtivo());
        bean.setCurDatacadastro(dto.getCurDatacadastro());
        bean.setCurHoracadastro(dto.getCurHoracadastro());
        return bean;
    }
}
