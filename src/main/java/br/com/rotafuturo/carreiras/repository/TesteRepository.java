package br.com.rotafuturo.carreiras.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafuturo.carreiras.model.TesteBean;

public interface TesteRepository extends JpaRepository<TesteBean, Integer> {
}
