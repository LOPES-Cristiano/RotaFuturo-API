package br.com.rotafuturo.carreiras.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class UsuarioBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "usu_id")
	private Integer usuId;

	@Column(name = "usu_email", unique = true, length = 200)
	private String usuEmail;

	@Column(name = "usu_senha", length = 250)
	private String usuSenha;

	@Column(name = "usu_datacadastro")
	private LocalDate usuDataCadastro;

	@Column(name = "usu_horacadastro")
	private LocalTime usuHoraCadastro;

	@Column(name = "usu_emailvalidado")
	private Boolean usuEmailValidado;

	@Column(name = "usu_ativo")
	private Boolean usuAtivo;

	@Column(name = "usu_trocasenha")
	private Boolean usuTrocaSenha;

	@Column(name = "usu_senhaantiga1", length = 250)
	private String usuSenhaAntiga1;

	@Column(name = "usu_senhaantiga2", length = 250)
	private String usuSenhaAntiga2;

	public Integer getUsuId() {
		return usuId;
	}

	public void setUsuId(Integer usuId) {
		this.usuId = usuId;
	}

	public String getUsuEmail() {
		return usuEmail;
	}

	public void setUsuEmail(String usuEmail) {
		this.usuEmail = usuEmail;
	}

	public String getUsuSenha() {
		return usuSenha;
	}

	public void setUsuSenha(String usuSenha) {
		this.usuSenha = usuSenha;
	}

	public LocalDate getUsuDataCadastro() {
		return usuDataCadastro;
	}

	public void setUsuDataCadastro(LocalDate usuDataCadastro) {
		this.usuDataCadastro = usuDataCadastro;
	}

	public LocalTime getUsuHoraCadastro() {
		return usuHoraCadastro;
	}

	public void setUsuHoraCadastro(LocalTime usuHoraCadastro) {
		this.usuHoraCadastro = usuHoraCadastro;
	}

	public Boolean getUsuEmailValidado() {
		return usuEmailValidado;
	}

	public void setUsuEmailValidado(Boolean usuEmailValidado) {
		this.usuEmailValidado = usuEmailValidado;
	}

	public Boolean getUsuAtivo() {
		return usuAtivo;
	}

	public void setUsuAtivo(Boolean usuAtivo) {
		this.usuAtivo = usuAtivo;
	}

	public Boolean getUsuTrocaSenha() {
		return usuTrocaSenha;
	}

	public void setUsuTrocaSenha(Boolean usuTrocaSenha) {
		this.usuTrocaSenha = usuTrocaSenha;
	}

	public String getUsuSenhaAntiga1() {
		return usuSenhaAntiga1;
	}

	public void setUsuSenhaAntiga1(String usuSenhaAntiga1) {
		this.usuSenhaAntiga1 = usuSenhaAntiga1;
	}

	public String getUsuSenhaAntiga2() {
		return usuSenhaAntiga2;
	}

	public void setUsuSenhaAntiga2(String usuSenhaAntiga2) {
		this.usuSenhaAntiga2 = usuSenhaAntiga2;
	}
}
