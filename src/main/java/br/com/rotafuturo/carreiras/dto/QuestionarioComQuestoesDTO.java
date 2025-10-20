package br.com.rotafuturo.carreiras.dto;

import java.util.List;

/**
 * DTO para representar um questionário completo com suas questões.
 */
public class QuestionarioComQuestoesDTO {
    
    private Integer quesId;
    private String quesDescricao;
    private Integer quesPeso;
    private List<QuestaoDTO> questoes;
    
    public Integer getQuesId() {
        return quesId;
    }
    
    public void setQuesId(Integer quesId) {
        this.quesId = quesId;
    }
    
    public String getQuesDescricao() {
        return quesDescricao;
    }
    
    public void setQuesDescricao(String quesDescricao) {
        this.quesDescricao = quesDescricao;
    }
    
    public Integer getQuesPeso() {
        return quesPeso;
    }
    
    public void setQuesPeso(Integer quesPeso) {
        this.quesPeso = quesPeso;
    }
    
    public List<QuestaoDTO> getQuestoes() {
        return questoes;
    }
    
    public void setQuestoes(List<QuestaoDTO> questoes) {
        this.questoes = questoes;
    }
    
    /**
     * DTO interno para representar uma alternativa de questão.
     */
    public static class AlternativaDTO {
        private Integer quesaId;
        private String quesaDescricao;
        private Boolean quesaCorreta;
        
        public Integer getQuesaId() {
            return quesaId;
        }
        
        public void setQuesaId(Integer quesaId) {
            this.quesaId = quesaId;
        }
        
        public String getQuesaDescricao() {
            return quesaDescricao;
        }
        
        public void setQuesaDescricao(String quesaDescricao) {
            this.quesaDescricao = quesaDescricao;
        }
        
        public Boolean getQuesaCorreta() {
            return quesaCorreta;
        }
        
        public void setQuesaCorreta(Boolean quesaCorreta) {
            this.quesaCorreta = quesaCorreta;
        }
    }
    
    /**
     * DTO interno para representar uma questão.
     */
    public static class QuestaoDTO {
        private Integer questaoId;
        private String questaoCodigo;
        private String questaoDescricao;
        private String questaoTipo; // Tipo da questão (múltipla escolha, dissertativa, etc)
        private Integer questaoExperiencia;
        private List<AlternativaDTO> alternativas;
        
        public Integer getQuestaoId() {
            return questaoId;
        }
        
        public void setQuestaoId(Integer questaoId) {
            this.questaoId = questaoId;
        }
        
        public String getQuestaoCodigo() {
            return questaoCodigo;
        }
        
        public void setQuestaoCodigo(String questaoCodigo) {
            this.questaoCodigo = questaoCodigo;
        }
        
        public String getQuestaoDescricao() {
            return questaoDescricao;
        }
        
        public void setQuestaoDescricao(String questaoDescricao) {
            this.questaoDescricao = questaoDescricao;
        }
        
        public String getQuestaoTipo() {
            return questaoTipo;
        }
        
        public void setQuestaoTipo(String questaoTipo) {
            this.questaoTipo = questaoTipo;
        }
        
        public Integer getQuestaoExperiencia() {
            return questaoExperiencia;
        }
        
        public void setQuestaoExperiencia(Integer questaoExperiencia) {
            this.questaoExperiencia = questaoExperiencia;
        }
        
        public List<AlternativaDTO> getAlternativas() {
            return alternativas;
        }
        
        public void setAlternativas(List<AlternativaDTO> alternativas) {
            this.alternativas = alternativas;
        }
    }
}
