package br.com.rotafuturo.carreiras.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafuturo.carreiras.model.QuestionarioTipoBean;

public interface QuestionarioTipoRepository extends JpaRepository<QuestionarioTipoBean, Integer> {
}
