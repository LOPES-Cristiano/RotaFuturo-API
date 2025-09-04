package br.com.rotafuturo.carreiras.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafuturo.carreiras.model.QuestionarioBean;

public interface QuestionarioRepository extends JpaRepository<QuestionarioBean, Integer> {
}
