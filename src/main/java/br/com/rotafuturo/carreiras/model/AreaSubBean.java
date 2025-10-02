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
@Table(name = "AREASUB")
public class AreaSubBean {
	@jakarta.persistence.Column(name = "AREAS_ATIVO")
	private Boolean areasAtivo = true;
	public Boolean getAreasAtivo() {
		return areasAtivo;
	}
	public void setAreasAtivo(Boolean areasAtivo) {
		this.areasAtivo = areasAtivo;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@jakarta.persistence.Column(name = "AREAS_ID")
	private Integer areasId;
	@jakarta.persistence.Column(name = "AREAS_DATACADASTRO")
	private LocalDate areasDatacadastro;
	@jakarta.persistence.Column(name = "AREAS_HORACADASTRO")
	private LocalTime areasHoracadastro;
	@jakarta.persistence.Column(name = "AREAS_DESCRICAO")
	private String areasDescricao;
	@ManyToOne
	@JoinColumn(name = "AREA_ID")
	private AreaBean area;
	public Integer getAreasId() {
		return areasId;
	}
	public void setAreasId(Integer areasId) {
		this.areasId = areasId;
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
	public String getAreasDescricao() {
		return areasDescricao;
	}
	public void setAreasDescricao(String areasDescricao) {
		this.areasDescricao = areasDescricao;
	}
	public AreaBean getArea() {
		return area;
	}
	public void setArea(AreaBean area) {
		this.area = area;
	}
}
