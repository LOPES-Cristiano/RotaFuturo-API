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
@Table(name = "MATERIA")
public class MateriaBean {
	@jakarta.persistence.Column(name = "MAT_ATIVO")
	private Boolean matAtivo = true;
	public Boolean getMatAtivo() {
		return matAtivo;
	}
	public void setMatAtivo(Boolean matAtivo) {
		this.matAtivo = matAtivo;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@jakarta.persistence.Column(name = "MAT_ID")
	private Integer matId;
	@jakarta.persistence.Column(name = "MAT_DATACADASTRO")
	private LocalDate matDatacadastro;
	@jakarta.persistence.Column(name = "MAT_HORACADASTRO")
	private LocalTime matHoracadastro;
	@jakarta.persistence.Column(name = "MAT_DESCRICAO")
	private String matDescricao;
	@ManyToOne
	@JoinColumn(name = "AREA_ID")
	private AreaBean area;
	@ManyToOne
	@JoinColumn(name = "AREAS_ID")
	private AreaSubBean areaSub;
	public Integer getMatId() {
		return matId;
	}
	public void setMatId(Integer matId) {
		this.matId = matId;
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
	public String getMatDescricao() {
		return matDescricao;
	}
	public void setMatDescricao(String matDescricao) {
		this.matDescricao = matDescricao;
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
