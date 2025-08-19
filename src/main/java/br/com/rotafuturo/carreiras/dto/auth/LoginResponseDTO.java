
package br.com.rotafuturo.carreiras.dto.auth;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor
public class LoginResponseDTO {
    private String token;
    private String message; 
}