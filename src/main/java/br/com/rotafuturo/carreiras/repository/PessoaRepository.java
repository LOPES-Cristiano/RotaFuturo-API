package br.com.rotafuturo.carreiras.repository;

import br.com.rotafuturo.carreiras.model.PessoaBean;
import br.com.rotafuturo.carreiras.model.UsuarioBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaBean, Integer> {
	Optional<PessoaBean> findByUsuario(UsuarioBean usuario);
}
