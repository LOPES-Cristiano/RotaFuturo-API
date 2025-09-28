
package br.com.rotafuturo.carreiras.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pessoa")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class PessoaBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PES_ID")
	private Integer pesId;
	@Column(name = "PES_ATIVO")
	private Boolean pesAtivo = true;

	public Boolean getPesAtivo() {
		return pesAtivo;
	}

	public void setPesAtivo(Boolean pesAtivo) {
		this.pesAtivo = pesAtivo;
	}

	@Column(name = "PES_NOME")
	private String pesNome;

	@Column(name = "PES_APELIDO")
	private String pesApelido;

	@Column(name = "PES_DATACADASTRO")
	private LocalDate pesDatacadastro;

	@Column(name = "PES_HORACADASTRO")
	private LocalTime pesHoracadastro;

	@Column(name = "PES_DATANASCIMENTO")
	private LocalDate pesDatanascimento;

	@Column(name = "PES_TELEFONE1")
	private String pesTelefone1;

	@Column(name = "PES_TELEFONE2")
	private String pesTelefone2;

	@Column(name = "PES_IMAGEMPERFIL")
	private String pesImagemperfil;

	@Column(name = "PES_IMAGEMCAPAPERFIL")
	private String pesImagemCapaPerfil;

	public String getPesImagemCapaPerfil() {
		return pesImagemCapaPerfil;
	}

	public void setPesImagemCapaPerfil(String pesImagemCapaPerfil) {
		this.pesImagemCapaPerfil = pesImagemCapaPerfil;
	}

	@Column(name = "PES_IMAGEMCURRICULO")
	private String pesImagemcurriculo;

	@Column(name = "PES_NIVEL")
	private Integer pesNivel;

	@Column(name = "PES_XP")
	private Integer pesXp;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USU_ID")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private UsuarioBean usuario;
	
	public Integer getPesId() {
		return pesId;
	}

	public void setPesId(Integer pesId) {
		this.pesId = pesId;
	}

	public String getPesNome() {
		return pesNome;
	}

	public void setPesNome(String pesNome) {
		this.pesNome = pesNome;
	}

	public String getPesApelido() {
		return pesApelido;
	}

	public void setPesApelido(String pesApelido) {
		this.pesApelido = pesApelido;
	}

	public LocalDate getPesDatacadastro() {
		return pesDatacadastro;
	}

	public void setPesDatacadastro(LocalDate pesDatacadastro) {
		this.pesDatacadastro = pesDatacadastro;
	}

	public LocalTime getPesHoracadastro() {
		return pesHoracadastro;
	}

	public void setPesHoracadastro(LocalTime pesHoracadastro) {
		this.pesHoracadastro = pesHoracadastro;
	}

	public LocalDate getPesDatanascimento() {
		return pesDatanascimento;
	}

	public void setPesDatanascimento(LocalDate pesDatanascimento) {
		this.pesDatanascimento = pesDatanascimento;
	}

	public String getPesTelefone1() {
		return pesTelefone1;
	}

	public void setPesTelefone1(String pesTelefone1) {
		this.pesTelefone1 = pesTelefone1;
	}

	public String getPesTelefone2() {
		return pesTelefone2;
	}

	public void setPesTelefone2(String pesTelefone2) {
		this.pesTelefone2 = pesTelefone2;
	}

	public String getPesImagemperfil() {
		return pesImagemperfil;
	}

	public void setPesImagemperfil(String pesImagemperfil) {
		this.pesImagemperfil = pesImagemperfil;
	}

	public String getPesImagemcurriculo() {
		return pesImagemcurriculo;
	}

	public void setPesImagemcurriculo(String pesImagemcurriculo) {
		this.pesImagemcurriculo = pesImagemcurriculo;
	}

	public Integer getPesNivel() {
		return pesNivel;
	}

	public void setPesNivel(Integer pesNivel) {
		this.pesNivel = pesNivel;
	}

	public Integer getPesXp() {
		return pesXp;
	}

	public void setPesXp(Integer pesXp) {
		this.pesXp = pesXp;
	}

	public UsuarioBean getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioBean usuario) {
		this.usuario = usuario;
	}
	
	@Override
	public String toString() {
		return "PessoaBean{" + "pesId=" + pesId + ", pesNome='" + pesNome + '\'' + ", pesApelido='" + pesApelido + '\''
				+ ", pesTelefone1='" + pesTelefone1 + '\'' + ", pesImagemperfil='"
				+ (pesImagemperfil != null
						? pesImagemperfil.substring(0, Math.min(30, pesImagemperfil.length())) + "..."
						: null)
				+ '\'' + ", pesAtivo=" + pesAtivo + ", pesDatacadastro=" + pesDatacadastro + ", pesHoracadastro="
				+ pesHoracadastro + ", usuario=" + (usuario != null ? usuario.getUsuId() : null) + '}';
	}
}
