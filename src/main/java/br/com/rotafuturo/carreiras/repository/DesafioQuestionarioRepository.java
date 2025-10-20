package br.com.rotafuturo.carreiras.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rotafuturo.carreiras.model.DesafioQuestionarioBean;

public interface DesafioQuestionarioRepository extends JpaRepository<DesafioQuestionarioBean, Integer> {
	
	/**
	 * Busca todos os vínculos de questionários para um desafio específico.
	 */
	List<DesafioQuestionarioBean> findByDesafio_DesId(Integer desId);
}
