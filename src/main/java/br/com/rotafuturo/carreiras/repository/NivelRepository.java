package br.com.rotafuturo.carreiras.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.rotafuturo.carreiras.model.NivelBean;

@Repository
public interface NivelRepository extends JpaRepository<NivelBean, Integer> {
    // Métodos personalizados podem ser adicionados aqui, se necessário
    boolean existsByNivDescricao(String descricao);
    
    java.util.Optional<NivelBean> findByNivDescricao(String descricao);
    
    @Modifying
    @Query(value = "INSERT IGNORE INTO NIVEL (NIV_ID, NIV_DESCRICAO) VALUES (:id, :descricao)", nativeQuery = true)
    void insertIfNotExists(@Param("id") Integer id, @Param("descricao") String descricao);
}