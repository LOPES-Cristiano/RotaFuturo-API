package br.com.rotafuturo.carreiras.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "arquivotipo")
public class ArquivoTipoBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@jakarta.persistence.Column(name = "ARQT_ID")
	private Integer arqtId;
	@jakarta.persistence.Column(name = "ARQT_ATIVO")
	private Boolean arqtAtivo = true;

	public Boolean getArqtAtivo() {
		return arqtAtivo;
	}

	public void setArqtAtivo(Boolean arqtAtivo) {
		this.arqtAtivo = arqtAtivo;
	}

	@jakarta.persistence.Column(name = "ARQT_DESCRICAO")
	private String arqtDescricao;

	public Integer getArqtId() {
		return arqtId;
	}

	public void setArqtId(Integer arqtId) {
		this.arqtId = arqtId;
	}

	public String getArqtDescricao() {
		return arqtDescricao;
	}

	public void setArqtDescricao(String arqtDescricao) {
		this.arqtDescricao = arqtDescricao;
	}
}
