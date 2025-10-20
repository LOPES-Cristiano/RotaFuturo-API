package br.com.rotafuturo.carreiras.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.rotafuturo.carreiras.model.QuestionarioTipoBean;

public interface QuestionarioTipoRepository extends JpaRepository<QuestionarioTipoBean, Integer> {
    Optional<QuestionarioTipoBean> findByQuestDescricao(String descricao);
}
