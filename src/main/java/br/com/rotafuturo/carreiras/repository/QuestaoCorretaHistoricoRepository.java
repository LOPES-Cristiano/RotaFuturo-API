package br.com.rotafuturo.carreiras.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.rotafuturo.carreiras.model.QuestaoCorretaHistoricoBean;

@Repository
public interface QuestaoCorretaHistoricoRepository extends JpaRepository<QuestaoCorretaHistoricoBean, Integer> {
    
    /**
     * Conta quantas questões corretas o usuário já respondeu.
     */
    @Query("SELECT COUNT(q) FROM QuestaoCorretaHistoricoBean q WHERE q.usuario.usuId = :usuarioId")
    long contarQuestoesCorretasPorUsuario(@Param("usuarioId") Integer usuarioId);
    
    /**
     * Verifica se o usuário já respondeu uma questão específica corretamente.
     */
    @Query("SELECT COUNT(q) > 0 FROM QuestaoCorretaHistoricoBean q WHERE q.usuario.usuId = :usuarioId AND q.questao.questaoId = :questaoId")
    boolean jaRespondeuQuestaoCorreta(@Param("usuarioId") Integer usuarioId, @Param("questaoId") Integer questaoId);
    
    /**
     * Soma total de XP ganho pelo usuário.
     */
    @Query("SELECT COALESCE(SUM(q.qchXpGanho), 0) FROM QuestaoCorretaHistoricoBean q WHERE q.usuario.usuId = :usuarioId")
    int somarXpTotalPorUsuario(@Param("usuarioId") Integer usuarioId);
}
