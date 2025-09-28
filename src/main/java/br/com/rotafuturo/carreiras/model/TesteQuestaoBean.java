package br.com.rotafuturo.carreiras.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "TESTEQUESTAO")
public class TesteQuestaoBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TESQ_ID")
	private Integer tesqId;

	@Column(name = "TESQ_DATACADASTRO", nullable = false)
	private LocalDate tesqDatacadastro;

	@Column(name = "TESQ_HORACADASTRO", nullable = false)
	private LocalTime tesqHoracadastro;

	@Column(name = "TESQ_DESCRICAO", nullable = false, length = 500)
	private String tesqDescricao;

	@ManyToOne
	@JoinColumn(name = "AREA_ID")
	private AreaBean area;

	@ManyToOne
	@JoinColumn(name = "AREAS_ID")
	private AreaSubBean areaSub;

	// getters e setters
	public Integer getTesqId() {
		return tesqId;
	}

	public void setTesqId(Integer tesqId) {
		this.tesqId = tesqId;
	}

	public LocalDate getTesqDatacadastro() {
		return tesqDatacadastro;
	}

	public void setTesqDatacadastro(LocalDate tesqDatacadastro) {
		this.tesqDatacadastro = tesqDatacadastro;
	}

	public LocalTime getTesqHoracadastro() {
		return tesqHoracadastro;
	}

	public void setTesqHoracadastro(LocalTime tesqHoracadastro) {
		this.tesqHoracadastro = tesqHoracadastro;
	}

	public String getTesqDescricao() {
		return tesqDescricao;
	}

	public void setTesqDescricao(String tesqDescricao) {
		this.tesqDescricao = tesqDescricao;
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

	@Transient
	private String testeDescricao;

	public String getTesteDescricao() {
		return testeDescricao;
	}

	public void setTesteDescricao(String testeDescricao) {
		this.testeDescricao = testeDescricao;
	}
}
