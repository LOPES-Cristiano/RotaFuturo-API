package br.com.rotafuturo.carreiras.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rotafuturo.carreiras.model.DesafioBean;

@Repository
public interface DesafioRepository extends JpaRepository<DesafioBean, Integer> {
    // Métodos personalizados podem ser adicionados aqui, se necessário
    boolean existsByDesTitulo(String titulo);
}