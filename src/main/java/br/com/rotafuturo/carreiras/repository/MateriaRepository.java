package br.com.rotafuturo.carreiras.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafuturo.carreiras.model.MateriaBean;

public interface MateriaRepository extends JpaRepository<MateriaBean, Integer> {
}
