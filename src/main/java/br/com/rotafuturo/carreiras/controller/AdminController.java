package br.com.rotafuturo.carreiras.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
@RestController
@RequestMapping("/home/admin")
public class AdminController {
    @GetMapping
    public ResponseEntity<String> acessarAreaAdmin(HttpServletRequest request) {
        return ResponseEntity.ok("Acesso permitido à área administrativa");
    }
}