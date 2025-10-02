package br.com.rotafuturo.carreiras.dto;
import java.time.LocalDate;
import java.time.LocalTime;
public class PessoaDTO {
	private Integer pesId;
	private String pesNome;
	private String pesApelido;
	private String pesCpf;
	private String pesTelefone1;
	private String pesImagemperfil;
	private String pesImagemCapaPerfil;
	private LocalDate pesDatanascimento;
	private Boolean pesAtivo;
	private LocalDate pesDatacadastro;
	private LocalTime pesHoracadastro;
	private Integer usuId;
	public String getPesImagemCapaPerfil() {
		return pesImagemCapaPerfil;
	}
	public void setPesImagemCapaPerfil(String pesImagemCapaPerfil) {
		this.pesImagemCapaPerfil = pesImagemCapaPerfil;
	}
	public LocalDate getPesDatanascimento() {
		return pesDatanascimento;
	}
	public void setPesDatanascimento(LocalDate pesDatanascimento) {
		this.pesDatanascimento = pesDatanascimento;
	}
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
	public String getPesCpf() {
		return pesCpf;
	}
	public void setPesCpf(String pesCpf) {
		this.pesCpf = pesCpf;
	}
	public String getPesTelefone1() {
		return pesTelefone1;
	}
	public void setPesTelefone1(String pesTelefone1) {
		this.pesTelefone1 = pesTelefone1;
	}
	public String getPesImagemperfil() {
		return pesImagemperfil;
	}
	public void setPesImagemperfil(String pesImagemperfil) {
		this.pesImagemperfil = pesImagemperfil;
	}
	public Boolean getPesAtivo() {
		return pesAtivo;
	}
	public void setPesAtivo(Boolean pesAtivo) {
		this.pesAtivo = pesAtivo;
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
	public Integer getUsuId() {
		return usuId;
	}
	public void setUsuId(Integer usuId) {
		this.usuId = usuId;
	}
}
