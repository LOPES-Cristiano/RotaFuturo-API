package br.com.rotafuturo.carreiras.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.rotafuturo.carreiras.model.AreaBean;

public interface AreaRepository extends JpaRepository<AreaBean, Integer> {
    Optional<AreaBean> findByAreaDescricao(String descricao);
}
