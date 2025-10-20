package br.com.rotafuturo.carreiras.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rotafuturo.carreiras.model.QuestaoAlternativaBean;
@Repository
public interface QuestaoAlternativaRepository extends JpaRepository<QuestaoAlternativaBean, Integer> {
    
    /**
     * Busca alternativas ativas de uma questão específica.
     */
    List<QuestaoAlternativaBean> findByQuestao_QuestaoIdAndQuesaAtivoTrue(Integer questaoId);
}
