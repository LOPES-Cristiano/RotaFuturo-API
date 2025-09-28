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
@Table(name = "CURSO")
public class CursoBean {
	@jakarta.persistence.Column(name = "CUR_ATIVO")
	private Boolean curAtivo = true;

	public Boolean getCurAtivo() {
		return curAtivo;
	}

	public void setCurAtivo(Boolean curAtivo) {
		this.curAtivo = curAtivo;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@jakarta.persistence.Column(name = "CUR_ID")
	private Integer curId;

	@jakarta.persistence.Column(name = "CUR_DATACADASTRO")
	private LocalDate curDatacadastro;

	@jakarta.persistence.Column(name = "CUR_HORACADASTRO")
	private LocalTime curHoracadastro;

	@jakarta.persistence.Column(name = "CUR_DESCRICAO")
	private String curDescricao;

	@ManyToOne
	@JoinColumn(name = "AREA_ID")
	private AreaBean area;

	@ManyToOne
	@JoinColumn(name = "AREAS_ID")
	private AreaSubBean areaSub;

	public Integer getCurId() {
		return curId;
	}

	public void setCurId(Integer curId) {
		this.curId = curId;
	}

	public LocalDate getCurDatacadastro() {
		return curDatacadastro;
	}

	public void setCurDatacadastro(LocalDate curDatacadastro) {
		this.curDatacadastro = curDatacadastro;
	}

	public LocalTime getCurHoracadastro() {
		return curHoracadastro;
	}

	public void setCurHoracadastro(LocalTime curHoracadastro) {
		this.curHoracadastro = curHoracadastro;
	}

	public String getCurDescricao() {
		return curDescricao;
	}

	public void setCurDescricao(String curDescricao) {
		this.curDescricao = curDescricao;
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
