package br.com.rotafuturo.carreiras.repository;

import br.com.rotafuturo.carreiras.model.QuestaoAlternativaBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestaoAlternativaRepository extends JpaRepository<QuestaoAlternativaBean, Integer> {
}
