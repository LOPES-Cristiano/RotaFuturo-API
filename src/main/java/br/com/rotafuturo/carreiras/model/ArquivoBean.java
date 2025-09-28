package br.com.rotafuturo.carreiras.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ARQUIVO")
public class ArquivoBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@jakarta.persistence.Column(name = "ARQ_ID")
	private Integer arqId;
	@jakarta.persistence.Column(name = "ARQ_ATIVO")
	private Boolean arqAtivo = true;
public Boolean getArqAtivo() {
	return arqAtivo;
}
public void setArqAtivo(Boolean arqAtivo) {
	this.arqAtivo = arqAtivo;
}

	@jakarta.persistence.Column(name = "ARQ_DATACADASTRO")
	private LocalDate arqDatacadastro;

	@jakarta.persistence.Column(name = "ARQ_HORACADASTRO")
	private LocalTime arqHoracadastro;

	@jakarta.persistence.Column(name = "ARQ_TAMANHO")
	private Integer arqTamanho;

	@jakarta.persistence.Column(name = "ARQ_DESCRICAO")
	private String arqDescricao;

	@jakarta.persistence.Column(name = "ARQ_EXTENSAO")
	private String arqExtensao;

	@jakarta.persistence.Column(name = "ARQ_ENDERECO")
	private String arqEndereco;

	@ManyToOne
	@jakarta.persistence.JoinColumn(name = "ARQT_ID")
	private ArquivoTipoBean arquivoTipo;

	@ManyToOne
	@jakarta.persistence.JoinColumn(name = "USU_ID")
	private UsuarioBean usuario;

	public Integer getArqId() {
		return arqId;
	}

	public void setArqId(Integer arqId) {
		this.arqId = arqId;
	}

	public LocalDate getArqDatacadastro() {
		return arqDatacadastro;
	}

	public void setArqDatacadastro(LocalDate arqDatacadastro) {
		this.arqDatacadastro = arqDatacadastro;
	}

	public LocalTime getArqHoracadastro() {
		return arqHoracadastro;
	}

	public void setArqHoracadastro(LocalTime arqHoracadastro) {
		this.arqHoracadastro = arqHoracadastro;
	}

	public Integer getArqTamanho() {
		return arqTamanho;
	}

	public void setArqTamanho(Integer arqTamanho) {
		this.arqTamanho = arqTamanho;
	}

	public String getArqDescricao() {
		return arqDescricao;
	}

	public void setArqDescricao(String arqDescricao) {
		this.arqDescricao = arqDescricao;
	}

	public String getArqExtensao() {
		return arqExtensao;
	}

	public void setArqExtensao(String arqExtensao) {
		this.arqExtensao = arqExtensao;
	}

	public String getArqEndereco() {
		return arqEndereco;
	}

	public void setArqEndereco(String arqEndereco) {
		this.arqEndereco = arqEndereco;
	}

	public ArquivoTipoBean getArquivoTipo() {
		return arquivoTipo;
	}

	public void setArquivoTipo(ArquivoTipoBean arquivoTipo) {
		this.arquivoTipo = arquivoTipo;
	}

	public UsuarioBean getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioBean usuario) {
		this.usuario = usuario;
	}
}
