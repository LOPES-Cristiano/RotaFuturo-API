package br.com.rotafuturo.carreiras.dto;
import java.time.LocalDate;
import java.time.LocalTime;
public class QuestionarioDTO {
	private Integer quesId;
	private String quesDescricao;
	private Boolean quesAtivo;
	private Integer quesPeso;
	private LocalDate quesDatacadastro;
	private LocalTime quesHoracadastro;
	private QuestionarioTipoDTO questionarioTipo;
	private AreaDTO area;
	private AreaSubDTO areaSub;
	public QuestionarioTipoDTO getQuestionarioTipo() {
		return questionarioTipo;
	}
	public void setQuestionarioTipo(QuestionarioTipoDTO questionarioTipo) {
		this.questionarioTipo = questionarioTipo;
	}
	public Integer getQuesPeso() {
		return quesPeso;
	}
	public void setQuesPeso(Integer quesPeso) {
		this.quesPeso = quesPeso;
	}
	public AreaDTO getArea() {
		return area;
	}
	public void setArea(AreaDTO area) {
		this.area = area;
	}
	public AreaSubDTO getAreaSub() {
		return areaSub;
	}
	public void setAreaSub(AreaSubDTO areaSub) {
		this.areaSub = areaSub;
	}
	public Integer getQuesId() {
		return quesId;
	}
	public void setQuesId(Integer quesId) {
		this.quesId = quesId;
	}
	public String getQuesDescricao() {
		return quesDescricao;
	}
	public void setQuesDescricao(String quesDescricao) {
		this.quesDescricao = quesDescricao;
	}
	public Boolean getQuesAtivo() {
		return quesAtivo;
	}
	public void setQuesAtivo(Boolean quesAtivo) {
		this.quesAtivo = quesAtivo;
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
}
