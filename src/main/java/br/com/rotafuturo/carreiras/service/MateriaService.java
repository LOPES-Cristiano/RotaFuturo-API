package br.com.rotafuturo.carreiras.service;

import org.springframework.stereotype.Service;

import br.com.rotafuturo.carreiras.dto.MateriaDTO;
import br.com.rotafuturo.carreiras.model.MateriaBean;

@Service
public class MateriaService {
    public MateriaDTO toDTO(MateriaBean bean) {
        if (bean == null) return null;
        MateriaDTO dto = new MateriaDTO();
        dto.setMatId(bean.getMatId());
        dto.setMatDescricao(bean.getMatDescricao());
        dto.setMatAtivo(bean.getMatAtivo());
        dto.setMatDatacadastro(bean.getMatDatacadastro());
        dto.setMatHoracadastro(bean.getMatHoracadastro());
        return dto;
    }
    public MateriaBean fromDTO(MateriaDTO dto) {
        if (dto == null) return null;
        MateriaBean bean = new MateriaBean();
        bean.setMatId(dto.getMatId());
        bean.setMatDescricao(dto.getMatDescricao());
        bean.setMatAtivo(dto.getMatAtivo());
        bean.setMatDatacadastro(dto.getMatDatacadastro());
        bean.setMatHoracadastro(dto.getMatHoracadastro());
        return bean;
    }
}
