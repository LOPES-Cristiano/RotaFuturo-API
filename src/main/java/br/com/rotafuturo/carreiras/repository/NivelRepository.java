package br.com.rotafuturo.carreiras.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rotafuturo.carreiras.model.NivelBean;

@Repository
public interface NivelRepository extends JpaRepository<NivelBean, Integer> {
    // Métodos personalizados podem ser adicionados aqui, se necessário
    boolean existsByNivDescricao(String descricao);
}