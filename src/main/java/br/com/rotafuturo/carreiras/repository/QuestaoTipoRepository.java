package br.com.rotafuturo.carreiras.repository;

import java.util.Optional;
import br.com.rotafuturo.carreiras.model.QuestaoTipoBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestaoTipoRepository extends JpaRepository<QuestaoTipoBean, Integer> {
    Optional<QuestaoTipoBean> findByQuetDescricao(String descricao);
}
