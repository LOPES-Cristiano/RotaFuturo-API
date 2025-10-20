package br.com.rotafuturo.carreiras.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.rotafuturo.carreiras.model.DesafioBean;

@Repository
public interface DesafioRepository extends JpaRepository<DesafioBean, Integer> {
    // Métodos personalizados podem ser adicionados aqui, se necessário
    boolean existsByDesTitulo(String titulo);
    
    // Para importação
    Optional<DesafioBean> findByDesTitulo(String titulo);
    
    /**
     * Busca desafios por área do usuário.
     */
    @Query("SELECT d FROM DesafioBean d WHERE d.area.areaId IN :areaIds")
    List<DesafioBean> findByAreaIds(@Param("areaIds") List<Integer> areaIds);
    
    /**
     * Busca desafios por subárea do usuário.
     */
    @Query("SELECT d FROM DesafioBean d WHERE d.areaSub.areasId IN :areaSubIds")
    List<DesafioBean> findByAreaSubIds(@Param("areaSubIds") List<Integer> areaSubIds);
    
    /**
     * Busca desafios por área E subárea do usuário (correspondência exata).
     * Retorna apenas desafios onde AMBOS área e subárea correspondem aos vínculos do usuário.
     */
    @Query("SELECT DISTINCT d FROM DesafioBean d WHERE " +
           "(d.area.areaId IN :areaIds AND d.areaSub.areasId IN :areaSubIds)")
    List<DesafioBean> findByAreaAndAreaSubIds(
        @Param("areaIds") List<Integer> areaIds, 
        @Param("areaSubIds") List<Integer> areaSubIds
    );
    
    /**
     * Busca desafios por área OU subárea do usuário (quando não há ambos preenchidos).
     */
    @Query("SELECT DISTINCT d FROM DesafioBean d WHERE " +
           "d.area.areaId IN :areaIds OR d.areaSub.areasId IN :areaSubIds")
    List<DesafioBean> findByAreaOrAreaSubIds(
        @Param("areaIds") List<Integer> areaIds, 
        @Param("areaSubIds") List<Integer> areaSubIds
    );
    
    /**
     * Busca desafios por área específica.
     */
    List<DesafioBean> findByArea_AreaId(Integer areaId);
    
    /**
     * Busca desafios por área e subárea específicas.
     */
    List<DesafioBean> findByArea_AreaIdAndAreaSub_AreasId(Integer areaId, Integer areaSubId);
}