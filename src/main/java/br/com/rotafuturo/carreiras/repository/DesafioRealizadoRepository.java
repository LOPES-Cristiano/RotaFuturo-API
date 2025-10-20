package br.com.rotafuturo.carreiras.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.rotafuturo.carreiras.model.DesafioRealizadoBean;

@Repository
public interface DesafioRealizadoRepository extends JpaRepository<DesafioRealizadoBean, Integer> {

    /**
     * Busca todos os desafios realizados por um usuário
     */
    List<DesafioRealizadoBean> findByUsuario_UsuIdOrderByDesreDatacadastroDesc(Integer usuarioId);

    /**
     * Busca todas as realizações de um desafio específico
     */
    List<DesafioRealizadoBean> findByDesafio_DesIdOrderByDesrePontuacaoDesc(Integer desafioId);

    /**
     * Busca o histórico de um usuário em um desafio específico
     */
    List<DesafioRealizadoBean> findByUsuario_UsuIdAndDesafio_DesIdOrderByDesreDatacadastroDesc(
            Integer usuarioId, Integer desafioId);

    /**
     * Busca a melhor pontuação de um usuário em um desafio
     */
    @Query("SELECT dr FROM DesafioRealizadoBean dr " +
           "WHERE dr.usuario.usuId = :usuarioId " +
           "AND dr.desafio.desId = :desafioId " +
           "ORDER BY dr.desrePontuacao DESC")
    Optional<DesafioRealizadoBean> findMelhorPontuacao(
            @Param("usuarioId") Integer usuarioId, 
            @Param("desafioId") Integer desafioId);

    /**
     * Busca ranking geral de um desafio (top pontuações)
     */
    @Query("SELECT dr FROM DesafioRealizadoBean dr " +
           "WHERE dr.desafio.desId = :desafioId " +
           "ORDER BY dr.desrePontuacao DESC, dr.desreDatacadastro ASC")
    List<DesafioRealizadoBean> findRankingByDesafio(@Param("desafioId") Integer desafioId);

    /**
     * Verifica se usuário já realizou o desafio
     */
    boolean existsByUsuario_UsuIdAndDesafio_DesId(Integer usuarioId, Integer desafioId);

    /**
     * Conta quantos desafios um usuário completou
     */
    long countByUsuario_UsuId(Integer usuarioId);
}
