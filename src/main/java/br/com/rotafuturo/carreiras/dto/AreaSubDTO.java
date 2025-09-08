package br.com.rotafuturo.carreiras.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class AreaSubDTO {
	private Integer areasId;
	private String areasDescricao;
	private Boolean areasAtivo;
	private LocalDate areasDatacadastro;
	private LocalTime areasHoracadastro;
	private Integer areaId;

	// getters e setters
	public Integer getAreasId() {
		return areasId;
	}

	public void setAreasId(Integer areasId) {
		this.areasId = areasId;
	}

	public String getAreasDescricao() {
		return areasDescricao;
	}

	public void setAreasDescricao(String areasDescricao) {
		this.areasDescricao = areasDescricao;
	}

	public Boolean getAreasAtivo() {
		return areasAtivo;
	}

	public void setAreasAtivo(Boolean areasAtivo) {
		this.areasAtivo = areasAtivo;
	}

	public LocalDate getAreasDatacadastro() {
		return areasDatacadastro;
	}

	public void setAreasDatacadastro(LocalDate areasDatacadastro) {
		this.areasDatacadastro = areasDatacadastro;
	}

	public LocalTime getAreasHoracadastro() {
		return areasHoracadastro;
	}

	public void setAreasHoracadastro(LocalTime areasHoracadastro) {
		this.areasHoracadastro = areasHoracadastro;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
}
