package br.com.rotafuturo.carreiras.repository;

import br.com.rotafuturo.carreiras.model.QuestaoNivelBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestaoNivelRepository extends JpaRepository<QuestaoNivelBean, Integer> {
}
