package br.com.rotafuturo.carreiras.dto;

import java.util.List;

/**
 * DTO para envio de respostas de desafio
 */
public class DesafioRespostaDTO {
    
    private Integer desafioId;
    private Integer usuarioId;
    private List<RespostaQuestao> respostas;
    
    public static class RespostaQuestao {
        private Integer questaoId;
        private Integer alternativaId;
        
        public RespostaQuestao() {}
        
        public RespostaQuestao(Integer questaoId, Integer alternativaId) {
            this.questaoId = questaoId;
            this.alternativaId = alternativaId;
        }
        
        public Integer getQuestaoId() {
            return questaoId;
        }
        
        public void setQuestaoId(Integer questaoId) {
            this.questaoId = questaoId;
        }
        
        public Integer getAlternativaId() {
            return alternativaId;
        }
        
        public void setAlternativaId(Integer alternativaId) {
            this.alternativaId = alternativaId;
        }
    }
    
    public DesafioRespostaDTO() {}
    
    public DesafioRespostaDTO(Integer desafioId, Integer usuarioId, List<RespostaQuestao> respostas) {
        this.desafioId = desafioId;
        this.usuarioId = usuarioId;
        this.respostas = respostas;
    }
    
    public Integer getDesafioId() {
        return desafioId;
    }
    
    public void setDesafioId(Integer desafioId) {
        this.desafioId = desafioId;
    }
    
    public Integer getUsuarioId() {
        return usuarioId;
    }
    
    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
    
    public List<RespostaQuestao> getRespostas() {
        return respostas;
    }
    
    public void setRespostas(List<RespostaQuestao> respostas) {
        this.respostas = respostas;
    }
}
