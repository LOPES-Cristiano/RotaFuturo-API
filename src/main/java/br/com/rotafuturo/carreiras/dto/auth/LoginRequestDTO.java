package br.com.rotafuturo.carreiras.dto.auth;

import lombok.Data;

/**
 * DTO para encapsular os dados de login de uma requisicao.
 */
@Data
public class LoginRequestDTO {
    private String usuEmail;
    private String usuSenha;
}
