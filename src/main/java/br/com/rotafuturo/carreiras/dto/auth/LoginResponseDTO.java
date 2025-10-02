package br.com.rotafuturo.carreiras.dto.auth;
public class LoginResponseDTO {
	private String token;
	private String message;
	public LoginResponseDTO() {
	}
	public LoginResponseDTO(String token, String message) {
		this.token = token;
		this.message = message;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}