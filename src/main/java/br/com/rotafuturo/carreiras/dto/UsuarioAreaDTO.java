package br.com.rotafuturo.carreiras.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO para representar os vínculos do usuário com áreas e subáreas.
 */
public class UsuarioAreaDTO {
    private Integer usuareaId;
    private Integer usuarioId;
    private AreaDTO area;
    private AreaSubDTO areaSub;
    private LocalDate usuareaDatacadastro;
    private LocalTime usuareaHoracadastro;

    // Constructors
    public UsuarioAreaDTO() {
    }

    public UsuarioAreaDTO(Integer usuareaId, Integer usuarioId, AreaDTO area, AreaSubDTO areaSub,
            LocalDate usuareaDatacadastro, LocalTime usuareaHoracadastro) {
        this.usuareaId = usuareaId;
        this.usuarioId = usuarioId;
        this.area = area;
        this.areaSub = areaSub;
        this.usuareaDatacadastro = usuareaDatacadastro;
        this.usuareaHoracadastro = usuareaHoracadastro;
    }

    // Getters and Setters
    public Integer getUsuareaId() {
        return usuareaId;
    }

    public void setUsuareaId(Integer usuareaId) {
        this.usuareaId = usuareaId;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
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

    public LocalDate getUsuareaDatacadastro() {
        return usuareaDatacadastro;
    }

    public void setUsuareaDatacadastro(LocalDate usuareaDatacadastro) {
        this.usuareaDatacadastro = usuareaDatacadastro;
    }

    public LocalTime getUsuareaHoracadastro() {
        return usuareaHoracadastro;
    }

    public void setUsuareaHoracadastro(LocalTime usuareaHoracadastro) {
        this.usuareaHoracadastro = usuareaHoracadastro;
    }
}
