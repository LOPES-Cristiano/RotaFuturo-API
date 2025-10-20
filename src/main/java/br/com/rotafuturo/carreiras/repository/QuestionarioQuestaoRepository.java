package br.com.rotafuturo.carreiras.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafuturo.carreiras.model.QuestionarioQuestaoBean;

public interface QuestionarioQuestaoRepository extends JpaRepository<QuestionarioQuestaoBean, Integer> {
	
	/**
	 * Busca todas as questões ativas de um questionário.
	 */
	List<QuestionarioQuestaoBean> findByQuestionario_QuesIdAndQuestqAtivoTrue(Integer quesId);
}
