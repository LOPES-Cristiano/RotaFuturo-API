package br.com.rotafuturo.carreiras.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.rotafuturo.carreiras.model.AreaSubBean;

public interface AreaSubRepository extends JpaRepository<AreaSubBean, Integer> {
    
    @Query("SELECT a FROM AreaSubBean a WHERE a.area.areaId = :areaId")
    List<AreaSubBean> findByArea_AreaId(@Param("areaId") Integer areaId);
}
