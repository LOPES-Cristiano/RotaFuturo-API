
package br.com.rotafuturo.carreiras.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "QUESTAOALTERNATIVA")
public class QuestaoAlternativaBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@jakarta.persistence.Column(name = "QUESA_ID")
	private Integer quesaId;

	@jakarta.persistence.Column(name = "QUESA_DESCRICAO")
	private String quesaDescricao;

	@jakarta.persistence.Column(name = "QUESA_CORRETA")
	private Integer quesaCorreta;

	@ManyToOne
	@jakarta.persistence.JoinColumn(name = "QUESTAO_ID")
	private QuestaoBean questao;

	@jakarta.persistence.Column(name = "QUESA_ATIVO")
	private Boolean quesaAtivo = true;

	public Boolean getQuesaAtivo() {
		return quesaAtivo;
	}

	public void setQuesaAtivo(Boolean quesaAtivo) {
		this.quesaAtivo = quesaAtivo;
	}

	public Integer getQuesaId() {
		return quesaId;
	}

	public void setQuesaId(Integer quesaId) {
		this.quesaId = quesaId;
	}

	public String getQuesaDescricao() {
		return quesaDescricao;
	}

	public void setQuesaDescricao(String quesaDescricao) {
		this.quesaDescricao = quesaDescricao;
	}

	public Integer getQuesaCorreta() {
		return quesaCorreta;
	}

	public void setQuesaCorreta(Integer quesaCorreta) {
		this.quesaCorreta = quesaCorreta;
	}

	public QuestaoBean getQuestao() {
		return questao;
	}

	public void setQuestao(QuestaoBean questao) {
		this.questao = questao;
	}
}
