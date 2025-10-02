package br.com.rotafuturo.carreiras.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "GRUPOACESSOUSUARIO")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class GrupoAcessoUsuarioBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GAU_ID")
    private Integer gauId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GRUA_ID")
    private GrupoAcessoBean grupoAcesso;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USU_ID")
    private UsuarioBean usuario;
    public Integer getGauId() {
        return gauId;
    }
    public void setGauId(Integer gauId) {
        this.gauId = gauId;
    }
    public GrupoAcessoBean getGrupoAcesso() {
        return grupoAcesso;
    }
    public void setGrupoAcesso(GrupoAcessoBean grupoAcesso) {
        this.grupoAcesso = grupoAcesso;
    }
    public UsuarioBean getUsuario() {
        return usuario;
    }
    public void setUsuario(UsuarioBean usuario) {
        this.usuario = usuario;
    }
}