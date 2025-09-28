package br.com.rotafuturo.carreiras.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.rotafuturo.carreiras.model.TesteQuestaoVinculoBean;

public interface TesteQuestaoVinculoRepository extends JpaRepository<TesteQuestaoVinculoBean, Integer> {
	java.util.List<TesteQuestaoVinculoBean> findByTeste_TesId(Integer tesId);
	
	/**
	 * Encontra vínculos pela ID da questão
	 */
	java.util.Optional<TesteQuestaoVinculoBean> findByTesteQuestao_TesqId(Integer tesqId);
	
	/**
	 * Encontra vínculos por teste ID onde questões estão associadas a uma área específica
	 */
	@Query("SELECT tv FROM TesteQuestaoVinculoBean tv JOIN tv.testeQuestao tq WHERE tv.teste.tesId = :testeId AND tq.area.areaId = :areaId")
	java.util.List<TesteQuestaoVinculoBean> findByTeste_TesIdAndTesteQuestao_Area_AreaId(
	        @Param("testeId") Integer testeId, 
	        @Param("areaId") Integer areaId);
	        
	/**
	 * Encontra vínculos por teste ID onde questões estão associadas a subáreas de uma área específica
	 */
	@Query("SELECT tv FROM TesteQuestaoVinculoBean tv JOIN tv.testeQuestao tq JOIN tq.areaSub sa WHERE tv.teste.tesId = :testeId AND sa.area.areaId = :areaId")
	java.util.List<TesteQuestaoVinculoBean> findByTeste_TesIdWithSubareaOfArea(
	        @Param("testeId") Integer testeId, 
	        @Param("areaId") Integer areaId);
}
