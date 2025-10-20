package br.com.rotafuturo.carreiras.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafuturo.carreiras.model.TesteQuestaoBean;
public interface TesteQuestaoRepository extends JpaRepository<TesteQuestaoBean, Integer> {
    // Para importação
    Optional<TesteQuestaoBean> findByTesqDescricao(String descricao);
}
