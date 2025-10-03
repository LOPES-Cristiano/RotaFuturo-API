package br.com.rotafuturo.carreiras.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rotafuturo.carreiras.model.ConteudoBean;

@Repository
public interface ConteudoRepository extends JpaRepository<ConteudoBean, Integer> {
    // Métodos personalizados podem ser adicionados aqui, se necessário
    boolean existsByConDescricao(String descricao);
}