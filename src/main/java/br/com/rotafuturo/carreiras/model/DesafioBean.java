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
@Table(name = "DESAFIO")
public class DesafioBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DES_ID")
    private Integer desId;

    @Column(name = "DES_TITULO")
    private String desTitulo;

    @Column(name = "DES_DESCRICAO")
    private String desDescricao;

    @Column(name = "DES_DATACADASTRO")
    private LocalDate desDatacadastro;

    @Column(name = "DES_HORACADASTRO")
    private LocalTime desHoracadastro;

    @ManyToOne
    @JoinColumn(name = "NIV_ID")
    private NivelBean nivel;

    @ManyToOne
    @JoinColumn(name = "AREA_ID")
    private AreaBean area;

    @ManyToOne
    @JoinColumn(name = "AREAS_ID")
    private AreaSubBean areaSub;

    public Integer getDesId() {
        return desId;
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

    public void setDesId(Integer desId) {
        this.desId = desId;
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

    public NivelBean getNivel() {
        return nivel;
    }

    public void setNivel(NivelBean nivel) {
        this.nivel = nivel;
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