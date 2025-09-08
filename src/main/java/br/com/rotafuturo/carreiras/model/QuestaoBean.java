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
@Table(name = "questao")
public class QuestaoBean {
	@jakarta.persistence.Column(name = "QUESTAO_CODIGO")
	private String questaoCodigo;

	@jakarta.persistence.Column(name = "QUESTAO_DESCRICAO")
	private String questaoDescricao;

	public String getQuestaoCodigo() {
		return questaoCodigo;
	}

	public void setQuestaoCodigo(String questaoCodigo) {
		this.questaoCodigo = questaoCodigo;
	}

	public String getQuestaoDescricao() {
		return questaoDescricao;
	}

	public void setQuestaoDescricao(String questaoDescricao) {
		this.questaoDescricao = questaoDescricao;
	}

	@jakarta.persistence.Column(name = "QUESTAO_ATIVO")
	private Boolean questaoAtivo = true;

	public Boolean getQuestaoAtivo() {
		return questaoAtivo;
	}

	public void setQuestaoAtivo(Boolean questaoAtivo) {
		this.questaoAtivo = questaoAtivo;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@jakarta.persistence.Column(name = "QUESTAO_ID")
	private Integer questaoId;

	@jakarta.persistence.Column(name = "QUESTAO_DATACADASTRO")
	private LocalDate questaoDatacadastro;

	@jakarta.persistence.Column(name = "QUESTAO_HORACADASTRO")
	private LocalTime questaoHoracadastro;

	@jakarta.persistence.Column(name = "QUESTAO_EXPERIENCIA")
	private Integer questaoExperiencia;

	@ManyToOne
	@JoinColumn(name = "QUESN_ID")
	private QuestaoNivelBean questaoNivel;

	@ManyToOne
	@JoinColumn(name = "QUET_ID")
	private QuestaoTipoBean questaoTipo;

	@ManyToOne
	@JoinColumn(name = "AREA_ID")
	private AreaBean area;

	@ManyToOne
	@JoinColumn(name = "AREAS_ID")
	private AreaSubBean areaSub;

	public Integer getQuestaoId() {
		return questaoId;
	}

	public void setQuestaoId(Integer questaoId) {
		this.questaoId = questaoId;
	}

	public LocalDate getQuestaoDatacadastro() {
		return questaoDatacadastro;
	}

	public void setQuestaoDatacadastro(LocalDate questaoDatacadastro) {
		this.questaoDatacadastro = questaoDatacadastro;
	}

	public LocalTime getQuestaoHoracadastro() {
		return questaoHoracadastro;
	}

	public void setQuestaoHoracadastro(LocalTime questaoHoracadastro) {
		this.questaoHoracadastro = questaoHoracadastro;
	}

	public Integer getQuestaoExperiencia() {
		return questaoExperiencia;
	}

	public void setQuestaoExperiencia(Integer questaoExperiencia) {
		this.questaoExperiencia = questaoExperiencia;
	}

	public QuestaoNivelBean getQuestaoNivel() {
		return questaoNivel;
	}

	public void setQuestaoNivel(QuestaoNivelBean questaoNivel) {
		this.questaoNivel = questaoNivel;
	}

	public QuestaoTipoBean getQuestaoTipo() {
		return questaoTipo;
	}

	public void setQuestaoTipo(QuestaoTipoBean questaoTipo) {
		this.questaoTipo = questaoTipo;
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
