package br.com.rotafuturo.carreiras.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafuturo.carreiras.model.QuestaoBean;
public interface QuestaoRepository extends JpaRepository<QuestaoBean, Integer> {
    // Para importação - usa questaoCodigo (nome correto do campo no Bean)
    Optional<QuestaoBean> findByQuestaoCodigo(String codigo);
}
