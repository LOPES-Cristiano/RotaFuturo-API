package br.com.rotafuturo.carreiras.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "QUESTAORESPONDIDA")
public class QuestaoRespondidaBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QUESR_ID")
    private Integer quesrId;

    @Column(name = "QUESR_DATACADASTRO")
    private LocalDate quesrDatacadastro;

    @Column(name = "QUESR_HORACADASTRO")
    private LocalTime quesrHoracadastro;

    @ManyToOne
    @JoinColumn(name = "QUESTAO_ID")
    private QuestaoBean questao;

    @ManyToOne
    @JoinColumn(name = "USU_ID")
    private UsuarioBean usuario;

    @ManyToOne
    @JoinColumn(name = "QUESA_ID")
    private QuestaoAlternativaBean alternativa;

    public Integer getQuesrId() {
        return quesrId;
    }

    public void setQuesrId(Integer quesrId) {
        this.quesrId = quesrId;
    }

    public LocalDate getQuesrDatacadastro() {
        return quesrDatacadastro;
    }

    public void setQuesrDatacadastro(LocalDate quesrDatacadastro) {
        this.quesrDatacadastro = quesrDatacadastro;
    }

    public LocalTime getQuesrHoracadastro() {
        return quesrHoracadastro;
    }

    public void setQuesrHoracadastro(LocalTime quesrHoracadastro) {
        this.quesrHoracadastro = quesrHoracadastro;
    }

    public QuestaoBean getQuestao() {
        return questao;
    }

    public void setQuestao(QuestaoBean questao) {
        this.questao = questao;
    }

    public UsuarioBean getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioBean usuario) {
        this.usuario = usuario;
    }

    public QuestaoAlternativaBean getAlternativa() {
        return alternativa;
    }

    public void setAlternativa(QuestaoAlternativaBean alternativa) {
        this.alternativa = alternativa;
    }
}