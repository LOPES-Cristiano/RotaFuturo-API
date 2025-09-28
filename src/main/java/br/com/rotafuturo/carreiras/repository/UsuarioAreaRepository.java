package br.com.rotafuturo.carreiras.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.rotafuturo.carreiras.model.UsuarioAreaBean;

@Repository
public interface UsuarioAreaRepository extends JpaRepository<UsuarioAreaBean, Integer> {
    // Buscar vinculações para um usuário específico
    java.util.Optional<UsuarioAreaBean> findByUsuario_UsuId(Integer usuId);
    
    // Excluir vinculações para um usuário específico
    void deleteByUsuario_UsuId(Integer usuId);
    
    // Buscar vinculação específica de usuário com uma subárea
    @Query("SELECT ua FROM UsuarioAreaBean ua WHERE ua.usuario.usuId = :usuarioId AND ua.areaSub.areasId = :subareaId")
    UsuarioAreaBean findByUsuarioIdAndAreaSubId(@Param("usuarioId") Integer usuarioId, @Param("subareaId") Integer subareaId);
    
    // Buscar vinculações de um usuário com subáreas
    @Query("SELECT ua FROM UsuarioAreaBean ua WHERE ua.usuario.usuId = :usuarioId AND ua.areaSub IS NOT NULL")
    java.util.List<UsuarioAreaBean> findSubareasByUsuarioId(@Param("usuarioId") Integer usuarioId);
}