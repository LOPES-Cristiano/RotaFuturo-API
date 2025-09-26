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
@Table(name = "TESTE")
public class TesteBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TES_ID")
    private Integer tesId;

    @Column(name = "TES_DATACADASTRO", nullable = false)
    private LocalDate tesDatacadastro;

    @Column(name = "TES_HORACADASTRO", nullable = false)
    private LocalTime tesHoracadastro;

    @Column(name = "TES_DESCRICAO", nullable = false, length = 500)
    private String tesDescricao;

    @ManyToOne
    @JoinColumn(name = "AREA_ID")
    private AreaBean area;

    @ManyToOne
    @JoinColumn(name = "AREAS_ID")
    private AreaSubBean areaSub;

    public Integer getTesId() {
        return tesId;
    }

    public void setTesId(Integer tesId) {
        this.tesId = tesId;
    }

    public LocalDate getTesDatacadastro() {
        return tesDatacadastro;
    }

    public void setTesDatacadastro(LocalDate tesDatacadastro) {
        this.tesDatacadastro = tesDatacadastro;
    }

    public LocalTime getTesHoracadastro() {
        return tesHoracadastro;
    }

    public void setTesHoracadastro(LocalTime tesHoracadastro) {
        this.tesHoracadastro = tesHoracadastro;
    }

    public String getTesDescricao() {
        return tesDescricao;
    }

    public void setTesDescricao(String tesDescricao) {
        this.tesDescricao = tesDescricao;
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
