package br.com.rotafuturo.carreiras.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "DESAFIOQUESTIONARIO")
public class DesafioQuestionarioBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DESQ_ID")
    private Integer desqId;

    @ManyToOne
    @JoinColumn(name = "DES_ID")
    private DesafioBean desafio;

    @ManyToOne
    @JoinColumn(name = "QUES_ID")
    private QuestionarioBean questionario;

    public Integer getDesqId() {
        return desqId;
    }

    public void setDesqId(Integer desqId) {
        this.desqId = desqId;
    }

    public DesafioBean getDesafio() {
        return desafio;
    }

    public void setDesafio(DesafioBean desafio) {
        this.desafio = desafio;
    }

    public QuestionarioBean getQuestionario() {
        return questionario;
    }

    public void setQuestionario(QuestionarioBean questionario) {
        this.questionario = questionario;
    }
}