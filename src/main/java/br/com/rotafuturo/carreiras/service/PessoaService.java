package br.com.rotafuturo.carreiras.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.rotafuturo.carreiras.dto.PessoaDTO;
import br.com.rotafuturo.carreiras.model.PessoaBean;
import br.com.rotafuturo.carreiras.model.UsuarioBean;
import br.com.rotafuturo.carreiras.repository.PessoaRepository;

@Service
public class PessoaService {
    public PessoaDTO toDTO(PessoaBean bean) {
        if (bean == null) return null;
        PessoaDTO dto = new PessoaDTO();
        dto.setPesId(bean.getPesId());
        dto.setPesNome(bean.getPesNome());
        dto.setPesCpf(bean.getPesTelefone1()); 
        dto.setPesAtivo(bean.getPesAtivo());
        dto.setPesDatacadastro(bean.getPesDatacadastro());
        dto.setPesHoracadastro(bean.getPesHoracadastro());
        if (bean.getUsuario() != null) dto.setUsuId(bean.getUsuario().getUsuId());
        return dto;
    }

    public PessoaBean fromDTO(br.com.rotafuturo.carreiras.dto.PessoaDTO dto) {
        if (dto == null) return null;
        PessoaBean bean = new PessoaBean();
        bean.setPesId(dto.getPesId());
        bean.setPesNome(dto.getPesNome());
        bean.setPesTelefone1(dto.getPesCpf()); 
        bean.setPesAtivo(dto.getPesAtivo());
        bean.setPesDatacadastro(dto.getPesDatacadastro());
        bean.setPesHoracadastro(dto.getPesHoracadastro());
        return bean;
    }
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
