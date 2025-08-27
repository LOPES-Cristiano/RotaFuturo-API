package br.com.rotafuturo.carreiras.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "arquivo")
public class ArquivoBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer arqId;
    private LocalDate arqDatacadastro;
    private LocalTime arqHoracadastro;
    private Integer arqTamanho;
    private String arqDescricao;
    private String arqExtensao;
    private String arqEndereco;

    @ManyToOne
    @JoinColumn(name = "arqt_id")
    private ArquivoTipoBean arquivoTipo;

    @ManyToOne
    @JoinColumn(name = "usu_id")
    private UsuarioBean usuario;

    
    public Integer getArqId() { return arqId; }
    public void setArqId(Integer arqId) { this.arqId = arqId; }
    public LocalDate getArqDatacadastro() { return arqDatacadastro; }
    public void setArqDatacadastro(LocalDate arqDatacadastro) { this.arqDatacadastro = arqDatacadastro; }
    public LocalTime getArqHoracadastro() { return arqHoracadastro; }
    public void setArqHoracadastro(LocalTime arqHoracadastro) { this.arqHoracadastro = arqHoracadastro; }
    public Integer getArqTamanho() { return arqTamanho; }
    public void setArqTamanho(Integer arqTamanho) { this.arqTamanho = arqTamanho; }
    public String getArqDescricao() { return arqDescricao; }
    public void setArqDescricao(String arqDescricao) { this.arqDescricao = arqDescricao; }
    public String getArqExtensao() { return arqExtensao; }
    public void setArqExtensao(String arqExtensao) { this.arqExtensao = arqExtensao; }
    public String getArqEndereco() { return arqEndereco; }
    public void setArqEndereco(String arqEndereco) { this.arqEndereco = arqEndereco; }
    public ArquivoTipoBean getArquivoTipo() { return arquivoTipo; }
    public void setArquivoTipo(ArquivoTipoBean arquivoTipo) { this.arquivoTipo = arquivoTipo; }
    public UsuarioBean getUsuario() { return usuario; }
    public void setUsuario(UsuarioBean usuario) { this.usuario = usuario; }
}
