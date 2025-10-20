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

/**
 * Armazena o histórico de questões corretas respondidas pelo usuário.
 * Usado para calcular o nível (a cada 5 questões corretas = +1 nível).
 */
@Entity
@Table(name = "QUESTAOCORRETAHISTORICO")
public class QuestaoCorretaHistoricoBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QCH_ID")
    private Integer qchId;

    @ManyToOne
    @JoinColumn(name = "USU_ID", nullable = false)
    private UsuarioBean usuario;

    @ManyToOne
    @JoinColumn(name = "QUESTAO_ID")
    private QuestaoBean questao;

    @Column(name = "QCH_XP_GANHO")
    private Integer qchXpGanho;

    @Column(name = "QCH_DATACADASTRO", nullable = false)
    private LocalDate qchDatacadastro;

    @Column(name = "QCH_HORACADASTRO", nullable = false)
    private LocalTime qchHoracadastro;

    public Integer getQchId() {
        return qchId;
    }

    public void setQchId(Integer qchId) {
        this.qchId = qchId;
    }

    public UsuarioBean getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioBean usuario) {
        this.usuario = usuario;
    }

    public QuestaoBean getQuestao() {
        return questao;
    }

    public void setQuestao(QuestaoBean questao) {
        this.questao = questao;
    }

    public Integer getQchXpGanho() {
        return qchXpGanho;
    }

    public void setQchXpGanho(Integer qchXpGanho) {
        this.qchXpGanho = qchXpGanho;
    }

    public LocalDate getQchDatacadastro() {
        return qchDatacadastro;
    }

    public void setQchDatacadastro(LocalDate qchDatacadastro) {
        this.qchDatacadastro = qchDatacadastro;
    }

    public LocalTime getQchHoracadastro() {
        return qchHoracadastro;
    }

    public void setQchHoracadastro(LocalTime qchHoracadastro) {
        this.qchHoracadastro = qchHoracadastro;
    }
}
