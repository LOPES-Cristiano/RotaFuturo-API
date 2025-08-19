package br.com.rotafuturo.carreiras.config; // Verifique se o pacote corresponde à sua estrutura

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // Indica que esta é uma classe de configuração Spring
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) { 
        registry.addMapping("/**") 
                .allowedOrigins("http://localhost:3000") 
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") 
                .allowedHeaders("*") 
                .allowCredentials(true); 
    }
}