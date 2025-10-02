package br.com.rotafuturo.carreiras.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "QUESTAOTIPO")
public class QuestaoTipoBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@jakarta.persistence.Column(name = "QUET_ID")
	private Integer quetId;
	@jakarta.persistence.Column(name = "QUET_DESCRICAO")
	private String quetDescricao;
	@jakarta.persistence.Column(name = "QUET_ATIVO")
	private Boolean quetAtivo = true;
	public Integer getQuetId() {
		return quetId;
	}
	public void setQuetId(Integer quetId) {
		this.quetId = quetId;
	}
	public String getQuetDescricao() {
		return quetDescricao;
	}
	public void setQuetDescricao(String quetDescricao) {
		this.quetDescricao = quetDescricao;
	}
	public Boolean getQuetAtivo() {
		return quetAtivo;
	}
	public void setQuetAtivo(Boolean quetAtivo) {
		this.quetAtivo = quetAtivo;
	}
}
