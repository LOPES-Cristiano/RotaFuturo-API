package br.com.rotafuturo.carreiras.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "NIVEL")
public class NivelBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NIV_ID")
    private Integer nivId;

    @Column(name = "NIV_DESCRICAO")
    private String nivDescricao;

    public Integer getNivId() {
        return nivId;
    }

    public void setNivId(Integer nivId) {
        this.nivId = nivId;
    }

    public String getNivDescricao() {
        return nivDescricao;
    }

    public void setNivDescricao(String nivDescricao) {
        this.nivDescricao = nivDescricao;
    }
}