package br.com.rotafuturo.carreiras.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rotafuturo.carreiras.dto.auth.LoginRequestDTO;
import br.com.rotafuturo.carreiras.dto.auth.LoginResponseDTO; 
import br.com.rotafuturo.carreiras.security.JwtTokenProvider;

/**
 * Controlador REST para o processo de autenticacao (login).
 */
@RestController
@RequestMapping("/usuario/auth")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    // Altere o tipo de retorno para LoginResponseDTO
    public ResponseEntity<LoginResponseDTO> authenticateUser(@RequestBody LoginRequestDTO loginRequest) {
        
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsuEmail(),
                loginRequest.getUsuSenha()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);

        // Retorne um novo objeto LoginResponseDTO encapsulando o token
        return ResponseEntity.ok(new LoginResponseDTO(jwt, "Login realizado com sucesso!"));
    }
}