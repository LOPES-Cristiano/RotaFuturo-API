package br.com.rotafuturo.carreiras.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class UsuarioDTO {
	private Integer usuId;
	private String usuNome;
	private String usuEmail;
	private Boolean usuAtivo;
	private Boolean usuEmailValidado;
	private Boolean usuTrocaSenha;
	private LocalDate usuDatacadastro;
	private LocalTime usuHoracadastro;

	// getters e setters
	public Integer getUsuId() {
		return usuId;
	}

	public void setUsuId(Integer usuId) {
		this.usuId = usuId;
	}

	public String getUsuNome() {
		return usuNome;
	}

	public void setUsuNome(String usuNome) {
		this.usuNome = usuNome;
	}

	public String getUsuEmail() {
		return usuEmail;
	}

	public void setUsuEmail(String usuEmail) {
		this.usuEmail = usuEmail;
	}

	public Boolean getUsuAtivo() {
		return usuAtivo;
	}

	public void setUsuAtivo(Boolean usuAtivo) {
		this.usuAtivo = usuAtivo;
	}

	public Boolean getUsuEmailValidado() {
		return usuEmailValidado;
	}

	public void setUsuEmailValidado(Boolean usuEmailValidado) {
		this.usuEmailValidado = usuEmailValidado;
	}

	public Boolean getUsuTrocaSenha() {
		return usuTrocaSenha;
	}

	public void setUsuTrocaSenha(Boolean usuTrocaSenha) {
		this.usuTrocaSenha = usuTrocaSenha;
	}

	public LocalDate getUsuDatacadastro() {
		return usuDatacadastro;
	}

	public void setUsuDatacadastro(LocalDate usuDatacadastro) {
		this.usuDatacadastro = usuDatacadastro;
	}

	public LocalTime getUsuHoracadastro() {
		return usuHoracadastro;
	}

	public void setUsuHoracadastro(LocalTime usuHoracadastro) {
		this.usuHoracadastro = usuHoracadastro;
	}
}
