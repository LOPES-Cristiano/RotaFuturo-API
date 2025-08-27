package br.com.rotafuturo.carreiras.repository;

import br.com.rotafuturo.carreiras.model.ArquivoBean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArquivoRepository extends JpaRepository<ArquivoBean, Integer> {
}
