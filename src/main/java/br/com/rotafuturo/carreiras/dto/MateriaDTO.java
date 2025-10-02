package br.com.rotafuturo.carreiras.dto;
import java.time.LocalDate;
import java.time.LocalTime;
public class MateriaDTO {
	private Integer matId;
	private String matDescricao;
	private Boolean matAtivo;
	private LocalDate matDatacadastro;
	private LocalTime matHoracadastro;
	private Integer area; 
	private Integer areaSub; 
	private String areaDescricao; 
	private String areaSubDescricao; 
	public Integer getMatId() {
		return matId;
	}
	public void setMatId(Integer matId) {
		this.matId = matId;
	}
	public String getMatDescricao() {
		return matDescricao;
	}
	public void setMatDescricao(String matDescricao) {
		this.matDescricao = matDescricao;
	}
	public Boolean getMatAtivo() {
		return matAtivo;
	}
	public void setMatAtivo(Boolean matAtivo) {
		this.matAtivo = matAtivo;
	}
	public LocalDate getMatDatacadastro() {
		return matDatacadastro;
	}
	public void setMatDatacadastro(LocalDate matDatacadastro) {
		this.matDatacadastro = matDatacadastro;
	}
	public LocalTime getMatHoracadastro() {
		return matHoracadastro;
	}
	public void setMatHoracadastro(LocalTime matHoracadastro) {
		this.matHoracadastro = matHoracadastro;
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
