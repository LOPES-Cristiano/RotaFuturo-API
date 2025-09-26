package br.com.rotafuturo.carreiras.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafuturo.carreiras.model.TesteQuestaoVinculoBean;

public interface TesteQuestaoVinculoRepository extends JpaRepository<TesteQuestaoVinculoBean, Integer> {
	java.util.List<TesteQuestaoVinculoBean> findByTeste_TesId(Integer tesId);
}
