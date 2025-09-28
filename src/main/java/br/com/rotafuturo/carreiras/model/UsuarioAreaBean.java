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
@Table(name = "USUARIOAREA")
public class UsuarioAreaBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USUA_ID")
    private Integer usuaId;

    @Column(name = "USUA_DATACADASTRO", nullable = false)
    private LocalDate usuaDatacadastro;

    @Column(name = "USUA_HORACADASTRO", nullable = false)
    private LocalTime usuaHoracadastro;

    @ManyToOne
    @JoinColumn(name = "USU_ID")
    private UsuarioBean usuario;

    @ManyToOne
    @JoinColumn(name = "AREA_ID")
    private AreaBean area;

    @ManyToOne
    @JoinColumn(name = "AREAS_ID")
    private AreaSubBean areaSub;

    // Getters and Setters
    public Integer getUsuaId() {
        return usuaId;
    }

    public void setUsuaId(Integer usuaId) {
        this.usuaId = usuaId;
    }

    public LocalDate getUsuaDatacadastro() {
        return usuaDatacadastro;
    }

    public void setUsuaDatacadastro(LocalDate usuaDatacadastro) {
        this.usuaDatacadastro = usuaDatacadastro;
    }

    public LocalTime getUsuaHoracadastro() {
        return usuaHoracadastro;
    }

    public void setUsuaHoracadastro(LocalTime usuaHoracadastro) {
        this.usuaHoracadastro = usuaHoracadastro;
    }

    public UsuarioBean getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioBean usuario) {
        this.usuario = usuario;
    }

    public AreaBean getArea() {
        return area;
    }

    public void setArea(AreaBean area) {
        this.area = area;
    }

    public AreaSubBean getAreaSub() {
        return areaSub;
    }

    public void setAreaSub(AreaSubBean areaSub) {
        this.areaSub = areaSub;
    }
}