package br.com.rotafuturo.carreiras.service;

import br.com.rotafuturo.carreiras.model.ArquivoBean;
import br.com.rotafuturo.carreiras.repository.ArquivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArquivoService {
	@Autowired
	private ArquivoRepository arquivoRepository;

	public ArquivoBean salvar(ArquivoBean arquivo) {
		return arquivoRepository.save(arquivo);
	}
}
