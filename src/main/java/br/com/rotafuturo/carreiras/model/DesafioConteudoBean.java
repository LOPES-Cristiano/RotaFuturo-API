package br.com.rotafuturo.carreiras.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "DESAFIOCONTEUDO")
public class DesafioConteudoBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DESCON_ID")
    private Integer desconId;

    @ManyToOne
    @JoinColumn(name = "DES_ID")
    private DesafioBean desafio;

    @ManyToOne
    @JoinColumn(name = "CON_ID")
    private ConteudoBean conteudo;

    @ManyToOne
    @JoinColumn(name = "NIV_ID")
    private NivelBean nivel;

    @ManyToOne
    @JoinColumn(name = "AREA_ID")
    private AreaBean area;

    @ManyToOne
    @JoinColumn(name = "AREAS_ID")
    private AreaSubBean areaSub;

    public Integer getDesconId() {
        return desconId;
    }

    public void setDesconId(Integer desconId) {
        this.desconId = desconId;
    }

    public DesafioBean getDesafio() {
        return desafio;
    }

    public void setDesafio(DesafioBean desafio) {
        this.desafio = desafio;
    }

    public ConteudoBean getConteudo() {
        return conteudo;
    }

    public void setConteudo(ConteudoBean conteudo) {
        this.conteudo = conteudo;
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