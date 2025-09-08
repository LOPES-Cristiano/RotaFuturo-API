package br.com.rotafuturo.carreiras.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class CursoDTO {
	private Integer curId;
	private String curDescricao;
	private Boolean curAtivo;
	private LocalDate curDatacadastro;
	private LocalTime curHoracadastro;
	private Integer area; // ID da área
	private Integer areaSub; // ID da subárea
	private String areaDescricao; // descrição da área
	private String areaSubDescricao; // descrição da subárea

	// getters e setters
	public Integer getCurId() {
		return curId;
	}

	public void setCurId(Integer curId) {
		this.curId = curId;
	}

	public String getCurDescricao() {
		return curDescricao;
	}

	public void setCurDescricao(String curDescricao) {
		this.curDescricao = curDescricao;
	}

	public Boolean getCurAtivo() {
		return curAtivo;
	}

	public void setCurAtivo(Boolean curAtivo) {
		this.curAtivo = curAtivo;
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

	public Integer getArea() {
		return area;
	}

	public void setArea(Integer area) {
		this.area = area;
	}

	public Integer getAreaSub() {
		return areaSub;
	}

	public void setAreaSub(Integer areaSub) {
		this.areaSub = areaSub;
	}

	public String getAreaDescricao() {
		return areaDescricao;
	}

	public void setAreaDescricao(String areaDescricao) {
		this.areaDescricao = areaDescricao;
	}

	public String getAreaSubDescricao() {
		return areaSubDescricao;
	}

	public void setAreaSubDescricao(String areaSubDescricao) {
		this.areaSubDescricao = areaSubDescricao;
	}
}
