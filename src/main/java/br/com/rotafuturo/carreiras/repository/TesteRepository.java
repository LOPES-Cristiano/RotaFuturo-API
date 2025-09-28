package br.com.rotafuturo.carreiras.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.rotafuturo.carreiras.model.TesteBean;

public interface TesteRepository extends JpaRepository<TesteBean, Integer> {
    
    /**
     * Encontra testes associados a uma área específica
     * @param areaId ID da área
     * @return Lista de testes associados à área
     */
    @Query("SELECT t FROM TesteBean t WHERE t.area.areaId = :areaId")
    List<TesteBean> findByAreaId(@Param("areaId") Integer areaId);
    
    /**
     * Encontra testes associados a uma subárea específica
     * @param areaSubId ID da subárea
     * @return Lista de testes associados à subárea
     */
    @Query("SELECT t FROM TesteBean t WHERE t.areaSub.areasId = :areaSubId")
    List<TesteBean> findByAreaSubId(@Param("areaSubId") Integer areaSubId);
    
    /**
     * Encontra testes de subárea (segundo teste)
     * Segundo a regra de negócio, o segundo teste sempre tem área associada
     * @return Lista de testes de subárea
     */
    @Query("SELECT t FROM TesteBean t WHERE t.area IS NOT NULL")
    List<TesteBean> findSubareaTests();
    
    /**
     * Encontra testes vocacionais (primeiro teste)
     * Segundo a regra de negócio, o primeiro teste nunca tem área associada
     * @return Lista de testes vocacionais
     */
    @Query("SELECT t FROM TesteBean t WHERE t.area IS NULL")
    List<TesteBean> findVocationalTests();
}
