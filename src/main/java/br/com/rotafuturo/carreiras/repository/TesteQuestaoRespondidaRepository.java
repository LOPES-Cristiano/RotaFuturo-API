package br.com.rotafuturo.carreiras.repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import br.com.rotafuturo.carreiras.model.TesteQuestaoRespondidaBean;
public interface TesteQuestaoRespondidaRepository extends JpaRepository<TesteQuestaoRespondidaBean, Integer> {
    List<TesteQuestaoRespondidaBean> findByTesteQuestaoVinculo_Teste_TesIdAndUsuario_UsuId(Integer testeId, Integer usuarioId);
    @Query("SELECT t FROM TesteQuestaoRespondidaBean t WHERE t.testeQuestaoVinculo.tesqvId = :vinculoId AND t.usuario.usuId = :usuarioId")
    Optional<TesteQuestaoRespondidaBean> findByTesteQuestaoVinculo_TesqvIdAndUsuario_UsuId(
            @Param("vinculoId") Integer vinculoId, 
            @Param("usuarioId") Integer usuarioId);
    @Query("SELECT t FROM TesteQuestaoRespondidaBean t WHERE t.usuario.usuId = :usuarioId AND t.testeQuestaoVinculo.tesqvId = :vinculoId")
    Optional<TesteQuestaoRespondidaBean> findByUsuario_UsuIdAndTesteQuestaoVinculo_TesqvId(
            @Param("usuarioId") Integer usuarioId,
            @Param("vinculoId") Integer vinculoId);
    @Query(value = "SELECT t.* FROM TESTEQUESTAORESPONDIDA t " +
                   "INNER JOIN TESTEQUESTAOVINCULO v ON t.TESQV_ID = v.TESQV_ID " +
                   "INNER JOIN (SELECT t2.TESQV_ID, MAX(t2.TESQR_DATACADASTRO) as max_data, MAX(t2.TESQR_HORACADASTRO) as max_hora " +
                   "            FROM TESTEQUESTAORESPONDIDA t2 " +
                   "            INNER JOIN TESTEQUESTAOVINCULO v2 ON t2.TESQV_ID = v2.TESQV_ID " +
                   "            WHERE v2.TES_ID = :testeId AND t2.USU_ID = :usuarioId " +
                   "            GROUP BY t2.TESQV_ID) as latest " +
                   "ON t.TESQV_ID = latest.TESQV_ID " +
                   "AND t.TESQR_DATACADASTRO = latest.max_data " +
                   "AND t.TESQR_HORACADASTRO = latest.max_hora " +
                   "WHERE v.TES_ID = :testeId AND t.USU_ID = :usuarioId", nativeQuery = true)
    List<TesteQuestaoRespondidaBean> findMostRecentResponsesByTesteAndUsuario(
            @Param("testeId") Integer testeId, 
            @Param("usuarioId") Integer usuarioId);
}
