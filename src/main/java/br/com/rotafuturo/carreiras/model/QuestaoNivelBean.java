package br.com.rotafuturo.carreiras.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "QUESTAONIVEL")
public class QuestaoNivelBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "QUESN_ID")
	private Integer quesnId;
	@Column(name = "QUESN_DESCRICAO")
	private String quesnDescricao;
	@jakarta.persistence.Column(name = "QUESN_ATIVO")
	private Boolean quesnAtivo = true;
	public Boolean getQuesnAtivo() {
		return quesnAtivo;
	}
	public void setQuesnAtivo(Boolean quesnAtivo) {
		this.quesnAtivo = quesnAtivo;
	}
	public Integer getQuesnId() {
		return quesnId;
	}
	public void setQuesnId(Integer quesnId) {
		this.quesnId = quesnId;
	}
	public String getQuesnDescricao() {
		return quesnDescricao;
	}
	public void setQuesnDescricao(String quesnDescricao) {
		this.quesnDescricao = quesnDescricao;
	}
}
