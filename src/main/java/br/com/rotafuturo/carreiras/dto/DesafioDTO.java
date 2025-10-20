package br.com.rotafuturo.carreiras.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class DesafioDTO {
    private Integer desId;
    private String desTitulo;
    private String desDescricao;
    private LocalDate desDatacadastro;
    private LocalTime desHoracadastro;
    private NivelDTO nivel;
    private AreaDTO area;
    private AreaSubDTO areaSub;

    public Integer getDesId() {
        return desId;
    }

    public void setDesId(Integer desId) {
        this.desId = desId;
    }

    public String getDesTitulo() {
        return desTitulo;
    }

    public void setDesTitulo(String desTitulo) {
        this.desTitulo = desTitulo;
    }

    public String getDesDescricao() {
        return desDescricao;
    }

    public void setDesDescricao(String desDescricao) {
        this.desDescricao = desDescricao;
    }

    public LocalDate getDesDatacadastro() {
        return desDatacadastro;
    }

    public void setDesDatacadastro(LocalDate desDatacadastro) {
        this.desDatacadastro = desDatacadastro;
    }

    public LocalTime getDesHoracadastro() {
        return desHoracadastro;
    }

    public void setDesHoracadastro(LocalTime desHoracadastro) {
        this.desHoracadastro = desHoracadastro;
    }

    public NivelDTO getNivel() {
        return nivel;
    }

    public void setNivel(NivelDTO nivel) {
        this.nivel = nivel;
    }

    public AreaDTO getArea() {
        return area;
    }

    public void setArea(AreaDTO area) {
        this.area = area;
    }

    public AreaSubDTO getAreaSub() {
        return areaSub;
    }

    public void setAreaSub(AreaSubDTO areaSub) {
        this.areaSub = areaSub;
    }
}
