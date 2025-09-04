package br.com.rotafuturo.carreiras.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafuturo.carreiras.model.QuestaoBean;

public interface QuestaoRepository extends JpaRepository<QuestaoBean, Integer> {
}
