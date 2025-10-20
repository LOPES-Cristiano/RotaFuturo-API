package br.com.rotafuturo.carreiras.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO para retornar informações sobre um desafio realizado.
 */
public class DesafioRealizadoDTO {

    private Integer desreId;
    private Integer desafioId;
    private String desafioTitulo;
    private Integer usuarioId;
    private BigDecimal desrePontuacao;
    private Integer desreAcertos;
    private Integer desreTotalQuestoes;
    private LocalDate desreDatacadastro;
    private LocalTime desreHoracadastro;

    // Getters e Setters

    public Integer getDesreId() {
        return desreId;
    }

    public void setDesreId(Integer desreId) {
        this.desreId = desreId;
    }

    public Integer getDesafioId() {
        return desafioId;
    }

    public void setDesafioId(Integer desafioId) {
        this.desafioId = desafioId;
    }

    public String getDesafioTitulo() {
        return desafioTitulo;
    }

    public void setDesafioTitulo(String desafioTitulo) {
        this.desafioTitulo = desafioTitulo;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
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
