package br.com.rotafuturo.carreiras.service;

import org.springframework.stereotype.Service;

import br.com.rotafuturo.carreiras.dto.QuestionarioDTO;
import br.com.rotafuturo.carreiras.model.QuestionarioBean;

@Service
public class QuestionarioService {
    public QuestionarioDTO toDTO(QuestionarioBean bean) {
        if (bean == null) return null;
        QuestionarioDTO dto = new QuestionarioDTO();
        dto.setQuesId(bean.getQuesId());
        dto.setQuesDescricao(bean.getQuesDescricao());
        dto.setQuesAtivo(bean.getQuesAtivo());
        dto.setQuesDatacadastro(bean.getQuesDatacadastro());
        dto.setQuesHoracadastro(bean.getQuesHoracadastro());
        return dto;
    }
    public QuestionarioBean fromDTO(QuestionarioDTO dto) {
        if (dto == null) return null;
        QuestionarioBean bean = new QuestionarioBean();
        bean.setQuesId(dto.getQuesId());
        bean.setQuesDescricao(dto.getQuesDescricao());
        bean.setQuesAtivo(dto.getQuesAtivo());
        bean.setQuesDatacadastro(dto.getQuesDatacadastro());
        bean.setQuesHoracadastro(dto.getQuesHoracadastro());
        return bean;
    }
}
