package br.com.rotafuturo.carreiras.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class MateriaDTO {
    private Integer matId;
    private String matDescricao;
    private Boolean matAtivo;
    private LocalDate matDatacadastro;
    private LocalTime matHoracadastro;
    // getters e setters
    public Integer getMatId() { return matId; }
    public void setMatId(Integer matId) { this.matId = matId; }
    public String getMatDescricao() { return matDescricao; }
    public void setMatDescricao(String matDescricao) { this.matDescricao = matDescricao; }
    public Boolean getMatAtivo() { return matAtivo; }
    public void setMatAtivo(Boolean matAtivo) { this.matAtivo = matAtivo; }
    public LocalDate getMatDatacadastro() { return matDatacadastro; }
    public void setMatDatacadastro(LocalDate matDatacadastro) { this.matDatacadastro = matDatacadastro; }
    public LocalTime getMatHoracadastro() { return matHoracadastro; }
    public void setMatHoracadastro(LocalTime matHoracadastro) { this.matHoracadastro = matHoracadastro; }
}
