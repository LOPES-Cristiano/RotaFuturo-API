package br.com.rotafuturo.carreiras.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "area")
public class AreaBean {
	@jakarta.persistence.Column(name = "AREA_ATIVO")
	private Boolean areaAtivo = true;

	public Boolean getAreaAtivo() {
		return areaAtivo;
	}

	public void setAreaAtivo(Boolean areaAtivo) {
		this.areaAtivo = areaAtivo;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@jakarta.persistence.Column(name = "AREA_ID")
	private Integer areaId;

	@jakarta.persistence.Column(name = "AREA_DATACADASTRO")
	private LocalDate areaDatacadastro;

	@jakarta.persistence.Column(name = "AREA_HORACADASTRO")
	private LocalTime areaHoracadastro;

	@jakarta.persistence.Column(name = "AREA_DESCRICAO")
	private String areaDescricao;

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public LocalDate getAreaDatacadastro() {
		return areaDatacadastro;
	}

	public void setAreaDatacadastro(LocalDate areaDatacadastro) {
		this.areaDatacadastro = areaDatacadastro;
	}

	public LocalTime getAreaHoracadastro() {
		return areaHoracadastro;
	}

	public void setAreaHoracadastro(LocalTime areaHoracadastro) {
		this.areaHoracadastro = areaHoracadastro;
	}

	public String getAreaDescricao() {
		return areaDescricao;
	}

	public void setAreaDescricao(String areaDescricao) {
		this.areaDescricao = areaDescricao;
	}
}
