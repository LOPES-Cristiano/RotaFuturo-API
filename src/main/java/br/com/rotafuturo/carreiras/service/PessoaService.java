package br.com.rotafuturo.carreiras.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
		if (bean == null)
			return null;
		PessoaDTO dto = new PessoaDTO();
		dto.setPesId(bean.getPesId());
		dto.setPesNome(bean.getPesNome());
		dto.setPesApelido(bean.getPesApelido());
		dto.setPesTelefone1(bean.getPesTelefone1());
		dto.setPesImagemperfil(bean.getPesImagemperfil());
		dto.setPesImagemCapaPerfil(bean.getPesImagemCapaPerfil());
		dto.setPesDatanascimento(bean.getPesDatanascimento());
		dto.setPesAtivo(bean.getPesAtivo());
		dto.setPesDatacadastro(bean.getPesDatacadastro());
		dto.setPesHoracadastro(bean.getPesHoracadastro());
		if (bean.getUsuario() != null)
			dto.setUsuId(bean.getUsuario().getUsuId());
		return dto;
	}

	public PessoaBean fromDTO(PessoaDTO dto) {
		if (dto == null)
			return null;
		PessoaBean bean = new PessoaBean();
		bean.setPesId(dto.getPesId());
		bean.setPesNome(dto.getPesNome());
		bean.setPesApelido(dto.getPesApelido());
		bean.setPesTelefone1(dto.getPesTelefone1());
		bean.setPesImagemperfil(dto.getPesImagemperfil());
		bean.setPesImagemCapaPerfil(dto.getPesImagemCapaPerfil());
		bean.setPesDatanascimento(dto.getPesDatanascimento());
		bean.setPesAtivo(dto.getPesAtivo());
		bean.setPesDatacadastro(dto.getPesDatacadastro());
		bean.setPesHoracadastro(dto.getPesHoracadastro());
		return bean;
	}
	
	public List<PessoaDTO> listarTodasDTO() {
	    return pessoaRepository.findAll()
	        .stream()
	        .map(this::toDTO)
	        .collect(Collectors.toList());
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
