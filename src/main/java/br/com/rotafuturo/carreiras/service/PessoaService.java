package br.com.rotafuturo.carreiras.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rotafuturo.carreiras.model.PessoaBean;
import br.com.rotafuturo.carreiras.model.UsuarioBean;
import br.com.rotafuturo.carreiras.repository.PessoaRepository;

@Service
public class PessoaService {
    public br.com.rotafuturo.carreiras.dto.PessoaDTO toDTO(PessoaBean bean) {
        if (bean == null) return null;
        br.com.rotafuturo.carreiras.dto.PessoaDTO dto = new br.com.rotafuturo.carreiras.dto.PessoaDTO();
        dto.setPesId(bean.getPesId());
        dto.setPesNome(bean.getPesNome());
        dto.setPesCpf(bean.getPesTelefone1()); // Ajuste conforme o campo correto
        dto.setPesAtivo(bean.getPesAtivo());
        dto.setPesDatacadastro(bean.getPesDatacadastro());
        dto.setPesHoracadastro(bean.getPesHoracadastro());
        if (bean.getUsuario() != null) dto.setUsuId(bean.getUsuario().getUsuId());
        // Adicione outros campos conforme necessário
        return dto;
    }

    public PessoaBean fromDTO(br.com.rotafuturo.carreiras.dto.PessoaDTO dto) {
        if (dto == null) return null;
        PessoaBean bean = new PessoaBean();
        bean.setPesId(dto.getPesId());
        bean.setPesNome(dto.getPesNome());
        bean.setPesTelefone1(dto.getPesCpf()); // Ajuste conforme o campo correto
        bean.setPesAtivo(dto.getPesAtivo());
        bean.setPesDatacadastro(dto.getPesDatacadastro());
        bean.setPesHoracadastro(dto.getPesHoracadastro());
        // Adicione outros campos conforme necessário
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
