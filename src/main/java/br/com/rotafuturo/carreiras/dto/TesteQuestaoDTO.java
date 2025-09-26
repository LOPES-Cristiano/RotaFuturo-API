package br.com.rotafuturo.carreiras.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TesteQuestaoDTO {
    @JsonProperty("tesqId")
    private Integer tesqId;
    @JsonProperty("tesqDatacadastro")
    private LocalDate tesqDatacadastro;
    @JsonProperty("tesqHoracadastro")
    private LocalTime tesqHoracadastro;
    @JsonProperty("tesqDescricao")
    private String tesqDescricao;
    @JsonProperty("areaId")
    private Integer areaId;
    @JsonProperty("areaSubId")
    private Integer areaSubId;
    @JsonProperty("testeId")
    private Integer testeId;

    @Override
    public String toString() {
        return "TesteQuestaoDTO{" +
                "tesqId=" + tesqId +
                ", tesqDatacadastro=" + tesqDatacadastro +
                ", tesqHoracadastro=" + tesqHoracadastro +
                ", tesqDescricao='" + tesqDescricao + '\'' +
                ", areaId=" + areaId +
                ", areaSubId=" + areaSubId +
                ", testeId=" + testeId +
                '}';
    }

    public Integer getTesteId() {
        return testeId;
    }

    public void setTesteId(Integer testeId) {
        this.testeId = testeId;
    }

    public Integer getTesqId() {
        return tesqId;
    }

    public void setTesqId(Integer tesqId) {
        this.tesqId = tesqId;
    }

    public LocalDate getTesqDatacadastro() {
        return tesqDatacadastro;
    }

    public void setTesqDatacadastro(LocalDate tesqDatacadastro) {
        this.tesqDatacadastro = tesqDatacadastro;
    }

    public LocalTime getTesqHoracadastro() {
        return tesqHoracadastro;
    }

    public void setTesqHoracadastro(LocalTime tesqHoracadastro) {
        this.tesqHoracadastro = tesqHoracadastro;
    }

    public String getTesqDescricao() {
        return tesqDescricao;
    }

    public void setTesqDescricao(String tesqDescricao) {
        this.tesqDescricao = tesqDescricao;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getAreaSubId() {
        return areaSubId;
    }

    public void setAreaSubId(Integer areaSubId) {
        this.areaSubId = areaSubId;
    }
}
