package br.com.rotafuturo.carreiras.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafuturo.carreiras.model.CursoBean;

public interface CursoRepository extends JpaRepository<CursoBean, Integer> {
}
