package br.com.rotafuturo.carreiras.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "GRUPOACESSO")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class GrupoAcessoBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GRUA_ID")
    private Integer gruaId;
    @Column(name = "GRUA_DESCRICAO", length = 100, nullable = false)
    private String gruaDescricao;
    public Integer getGruaId() {
        return gruaId;
    }
    public void setGruaId(Integer gruaId) {
        this.gruaId = gruaId;
    }
    public String getGruaDescricao() {
        return gruaDescricao;
    }
    public void setGruaDescricao(String gruaDescricao) {
        this.gruaDescricao = gruaDescricao;
    }
}