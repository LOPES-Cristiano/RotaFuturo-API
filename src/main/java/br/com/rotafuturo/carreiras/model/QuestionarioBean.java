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
@Table(name = "questionario")
public class QuestionarioBean {
	@jakarta.persistence.Column(name = "QUES_ATIVO")
	private Boolean quesAtivo = true;

	public Boolean getQuesAtivo() {
		return quesAtivo;
	}

	public void setQuesAtivo(Boolean quesAtivo) {
		this.quesAtivo = quesAtivo;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@jakarta.persistence.Column(name = "QUES_ID")
	private Integer quesId;

	@jakarta.persistence.Column(name = "QUES_DATACADASTRO")
	private LocalDate quesDatacadastro;

	@jakarta.persistence.Column(name = "QUES_HORACADASTRO")
	private LocalTime quesHoracadastro;

	@jakarta.persistence.Column(name = "QUES_DESCRICAO")
	private String quesDescricao;

	@jakarta.persistence.Column(name = "QUES_PESO")
	private Integer quesPeso;

	@ManyToOne
	@JoinColumn(name = "QUEST_ID")
	private QuestionarioTipoBean questionarioTipo;

	@ManyToOne
	@JoinColumn(name = "AREA_ID")
	private AreaBean area;

	@ManyToOne
	@JoinColumn(name = "AREAS_ID")
	private AreaSubBean areaSub;

	public Integer getQuesId() {
		return quesId;
	}

	public void setQuesId(Integer quesId) {
		this.quesId = quesId;
	}

	public LocalDate getQuesDatacadastro() {
		return quesDatacadastro;
	}

	public void setQuesDatacadastro(LocalDate quesDatacadastro) {
		this.quesDatacadastro = quesDatacadastro;
	}

	public LocalTime getQuesHoracadastro() {
		return quesHoracadastro;
	}

	public void setQuesHoracadastro(LocalTime quesHoracadastro) {
		this.quesHoracadastro = quesHoracadastro;
	}

	public String getQuesDescricao() {
		return quesDescricao;
	}

	public void setQuesDescricao(String quesDescricao) {
		this.quesDescricao = quesDescricao;
	}

	public Integer getQuesPeso() {
		return quesPeso;
	}

	public void setQuesPeso(Integer quesPeso) {
		this.quesPeso = quesPeso;
	}

	public QuestionarioTipoBean getQuestionarioTipo() {
		return questionarioTipo;
	}

	public void setQuestionarioTipo(QuestionarioTipoBean questionarioTipo) {
		this.questionarioTipo = questionarioTipo;
	}

	public AreaBean getArea() {
		return area;
	}

	public void setArea(AreaBean area) {
		this.area = area;
	}

	public AreaSubBean getAreaSub() {
		return areaSub;
	}

	public void setAreaSub(AreaSubBean areaSub) {
		this.areaSub = areaSub;
	}
}
