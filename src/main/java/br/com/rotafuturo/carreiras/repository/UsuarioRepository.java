package br.com.rotafuturo.carreiras.repository;

import br.com.rotafuturo.carreiras.model.UsuarioBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface de repositorio para a entidade UsuarioBean, usada pelo Spring Data JPA.
 *
 * <p>O metodo `findByUsuEmail` e fundamental para a autenticacao, permitindo que
 * o Spring Security busque um usuario pelo email.</p>
 */
@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioBean, Integer> {

    /**
     * Busca um usuario pelo email.
     * O Spring Data JPA implementa este metodo automaticamente.
     * @param usuEmail O email do usuario.
     * @return Um Optional contendo o UsuarioBean, se encontrado.
     */
    Optional<UsuarioBean> findByUsuEmail(String usuEmail);
}
