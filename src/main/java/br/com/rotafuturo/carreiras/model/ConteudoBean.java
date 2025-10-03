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
@Table(name = "CONTEUDO")
public class ConteudoBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CON_ID")
    private Integer conId;

    @Column(name = "CON_DATACADASTRO")
    private LocalDate conDatacadastro;

    @Column(name = "CON_HORACADASTRO")
    private LocalTime conHoracadastro;

    @Column(name = "CON_DESCRICAO", length = 500, nullable = false)
    private String conDescricao;

    @Column(name = "CON_LINKREFERENCIA", length = 1000)
    private String conLinkReferencia;

    @ManyToOne
    @JoinColumn(name = "NIV_ID")
    private NivelBean nivel;

    @ManyToOne
    @JoinColumn(name = "AREA_ID")
    private AreaBean area;

    @ManyToOne
    @JoinColumn(name = "AREAS_ID")
    private AreaSubBean areaSub;

    public Integer getConId() {
        return conId;
    }

    public void setConId(Integer conId) {
        this.conId = conId;
    }

    public LocalDate getConDatacadastro() {
        return conDatacadastro;
    }

    public void setConDatacadastro(LocalDate conDatacadastro) {
        this.conDatacadastro = conDatacadastro;
    }

    public LocalTime getConHoracadastro() {
        return conHoracadastro;
    }

    public void setConHoracadastro(LocalTime conHoracadastro) {
        this.conHoracadastro = conHoracadastro;
    }

    public String getConDescricao() {
        return conDescricao;
    }

    public void setConDescricao(String conDescricao) {
        this.conDescricao = conDescricao;
    }

    public String getConLinkReferencia() {
        return conLinkReferencia;
    }

    public void setConLinkReferencia(String conLinkReferencia) {
        this.conLinkReferencia = conLinkReferencia;
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