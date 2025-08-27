package br.com.rotafuturo.carreiras.service;

import br.com.rotafuturo.carreiras.model.PessoaBean;
import br.com.rotafuturo.carreiras.model.UsuarioBean;
import br.com.rotafuturo.carreiras.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Service
public class PessoaService {
    private final PessoaRepository pessoaRepository;

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public Optional<PessoaBean> getPessoaByUsuario(UsuarioBean usuario) {
        return pessoaRepository.findByUsuario(usuario);
    }

    public PessoaBean createPessoa(PessoaBean pessoa) {
        return pessoaRepository.save(pessoa);
    }

    private static final Logger logger = LoggerFactory.getLogger(PessoaService.class);

    public PessoaBean updatePessoa(PessoaBean pessoa) {
        logger.debug("Valor recebido para pesImagemperfil: {}", pessoa.getPesImagemperfil());
        return pessoaRepository.save(pessoa);
    }

    public Optional<PessoaBean> getPessoaById(Integer id) {
        return pessoaRepository.findById(id);
    }

    public void deletePessoa(Integer id) {
        pessoaRepository.deleteById(id);
    }
}
