package br.com.rotafuturo.carreiras.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.rotafuturo.carreiras.model.GrupoAcessoBean;
@Repository
public interface GrupoAcessoRepository extends JpaRepository<GrupoAcessoBean, Integer> {
    GrupoAcessoBean findByGruaDescricao(String descricao);
}