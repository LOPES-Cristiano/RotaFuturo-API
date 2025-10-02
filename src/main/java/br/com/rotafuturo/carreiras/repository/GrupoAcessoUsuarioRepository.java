package br.com.rotafuturo.carreiras.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import br.com.rotafuturo.carreiras.model.GrupoAcessoUsuarioBean;
@Repository
public interface GrupoAcessoUsuarioRepository extends JpaRepository<GrupoAcessoUsuarioBean, Integer> {
    @Query("SELECT g FROM GrupoAcessoUsuarioBean g WHERE g.usuario.usuId = :usuId")
    List<GrupoAcessoUsuarioBean> findByUsuarioId(@Param("usuId") Integer usuId);
    @Query("SELECT CASE WHEN COUNT(g) > 0 THEN TRUE ELSE FALSE END FROM GrupoAcessoUsuarioBean g " +
           "WHERE g.usuario.usuId = :usuId AND g.grupoAcesso.gruaDescricao = :grupoDescricao")
    boolean usuarioPertenceAoGrupo(@Param("usuId") Integer usuId, @Param("grupoDescricao") String grupoDescricao);
}