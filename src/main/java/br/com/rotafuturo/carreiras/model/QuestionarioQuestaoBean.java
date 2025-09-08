package br.com.rotafuturo.carreiras.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "questionarioquestao")
public class QuestionarioQuestaoBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@jakarta.persistence.Column(name = "QUESTQ_ID")
	private Integer questqId;

	@jakarta.persistence.Column(name = "QUESTQ_DATACADASTRO")
	private java.time.LocalDate questqDatacadastro;

	@jakarta.persistence.Column(name = "QUESTQ_HORACADASTRO")
	private java.time.LocalTime questqHoracadastro;

	@ManyToOne
	@jakarta.persistence.JoinColumn(name = "QUES_ID")
	private QuestionarioBean questionario;

	@ManyToOne
	@jakarta.persistence.JoinColumn(name = "QUESTAO_ID")
	private QuestaoBean questao;

	@jakarta.persistence.Column(name = "QUESTQ_ATIVO")
	private Boolean questqAtivo = true;

	public Boolean getQuestqAtivo() {
		return questqAtivo;
	}

	public void setQuestqAtivo(Boolean questqAtivo) {
		this.questqAtivo = questqAtivo;
	}

	public Integer getQuestqId() {
		return questqId;
	}

	public void setQuestqId(Integer questqId) {
		this.questqId = questqId;
	}

	public java.time.LocalDate getQuestqDatacadastro() {
		return questqDatacadastro;
	}

	public void setQuestqDatacadastro(java.time.LocalDate questqDatacadastro) {
		this.questqDatacadastro = questqDatacadastro;
	}

	public java.time.LocalTime getQuestqHoracadastro() {
		return questqHoracadastro;
	}

	public void setQuestqHoracadastro(java.time.LocalTime questqHoracadastro) {
		this.questqHoracadastro = questqHoracadastro;
	}

	public QuestionarioBean getQuestionario() {
		return questionario;
	}

	public void setQuestionario(QuestionarioBean questionario) {
		this.questionario = questionario;
	}

	public QuestaoBean getQuestao() {
		return questao;
	}

	public void setQuestao(QuestaoBean questao) {
		this.questao = questao;
	}
}
