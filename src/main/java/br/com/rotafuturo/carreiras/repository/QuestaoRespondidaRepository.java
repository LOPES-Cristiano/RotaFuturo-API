package br.com.rotafuturo.carreiras.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rotafuturo.carreiras.model.QuestaoRespondidaBean;

/**
 * Repository para gerenciar questões respondidas
 */
@Repository
public interface QuestaoRespondidaRepository extends JpaRepository<QuestaoRespondidaBean, Integer> {
    
    /**
     * Busca respostas de um usuário específico
     */
    List<QuestaoRespondidaBean> findByUsuario_UsuId(Integer usuarioId);
    
    /**
     * Busca respostas de um usuário para uma questão específica
     */
    List<QuestaoRespondidaBean> findByUsuario_UsuIdAndQuestao_QuestaoId(Integer usuarioId, Integer questaoId);
}
