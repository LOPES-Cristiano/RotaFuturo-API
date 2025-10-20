package br.com.rotafuturo.carreiras.model;

import java.math.BigDecimal;
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
@Table(name = "DESAFIOREALIZADO")
public class DesafioRealizadoBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DESRE_ID")
    private Integer desreId;

    @ManyToOne
    @JoinColumn(name = "DES_ID", nullable = false)
    private DesafioBean desafio;

    @ManyToOne
    @JoinColumn(name = "USU_ID", nullable = false)
    private UsuarioBean usuario;

    @Column(name = "DESRE_PONTUACAO", precision = 5, scale = 2)
    private BigDecimal desrePontuacao;

    @Column(name = "DESRE_ACERTOS", nullable = false)
    private Integer desreAcertos;

    @Column(name = "DESRE_TOTAL_QUESTOES", nullable = false)
    private Integer desreTotalQuestoes;

    @Column(name = "DESRE_DATACADASTRO", nullable = false)
    private LocalDate desreDatacadastro;

    @Column(name = "DESRE_HORACADASTRO", nullable = false)
    private LocalTime desreHoracadastro;

    // Getters e Setters

    public Integer getDesreId() {
        return desreId;
    }

    public void setDesreId(Integer desreId) {
        this.desreId = desreId;
    }

    public DesafioBean getDesafio() {
        return desafio;
    }

    public void setDesafio(DesafioBean desafio) {
        this.desafio = desafio;
    }

    public UsuarioBean getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioBean usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getDesrePontuacao() {
        return desrePontuacao;
    }

    public void setDesrePontuacao(BigDecimal desrePontuacao) {
        this.desrePontuacao = desrePontuacao;
    }

    public Integer getDesreAcertos() {
        return desreAcertos;
    }

    public void setDesreAcertos(Integer desreAcertos) {
        this.desreAcertos = desreAcertos;
    }

    public Integer getDesreTotalQuestoes() {
        return desreTotalQuestoes;
    }

    public void setDesreTotalQuestoes(Integer desreTotalQuestoes) {
        this.desreTotalQuestoes = desreTotalQuestoes;
    }

    public LocalDate getDesreDatacadastro() {
        return desreDatacadastro;
    }

    public void setDesreDatacadastro(LocalDate desreDatacadastro) {
        this.desreDatacadastro = desreDatacadastro;
    }

    public LocalTime getDesreHoracadastro() {
        return desreHoracadastro;
    }

    public void setDesreHoracadastro(LocalTime desreHoracadastro) {
        this.desreHoracadastro = desreHoracadastro;
    }
}
