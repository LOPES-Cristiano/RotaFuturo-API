package br.com.rotafuturo.carreiras.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class AreaDTO {
    private Integer areaId;
    private String areaDescricao;
    private Boolean areaAtivo;
    private LocalDate areaDatacadastro;
    private LocalTime areaHoracadastro;
    // getters e setters
    public Integer getAreaId() { return areaId; }
    public void setAreaId(Integer areaId) { this.areaId = areaId; }
    public String getAreaDescricao() { return areaDescricao; }
    public void setAreaDescricao(String areaDescricao) { this.areaDescricao = areaDescricao; }
    public Boolean getAreaAtivo() { return areaAtivo; }
    public void setAreaAtivo(Boolean areaAtivo) { this.areaAtivo = areaAtivo; }
    public LocalDate getAreaDatacadastro() { return areaDatacadastro; }
    public void setAreaDatacadastro(LocalDate areaDatacadastro) { this.areaDatacadastro = areaDatacadastro; }
    public LocalTime getAreaHoracadastro() { return areaHoracadastro; }
    public void setAreaHoracadastro(LocalTime areaHoracadastro) { this.areaHoracadastro = areaHoracadastro; }
}
