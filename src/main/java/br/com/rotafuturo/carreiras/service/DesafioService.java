package br.com.rotafuturo.carreiras.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rotafuturo.carreiras.model.DesafioBean;
import br.com.rotafuturo.carreiras.model.NivelBean;
import br.com.rotafuturo.carreiras.repository.DesafioRepository;
import br.com.rotafuturo.carreiras.repository.NivelRepository;

/**
 * Serviço para gerenciar os desafios da aplicação.
 */
@Service
public class DesafioService {

    private static final Logger logger = LoggerFactory.getLogger(DesafioService.class);
    
    private final DesafioRepository desafioRepository;
    private final NivelRepository nivelRepository;
    
    @Autowired
    public DesafioService(DesafioRepository desafioRepository, NivelRepository nivelRepository) {
        this.desafioRepository = desafioRepository;
        this.nivelRepository = nivelRepository;
    }
    
    /**
     * Recupera todos os desafios cadastrados.
     */
    public List<DesafioBean> listarTodos() {
        logger.info("Listando todos os desafios");
        return desafioRepository.findAll();
    }
    
    /**
     * Recupera um desafio específico pelo ID.
     */
    public Optional<DesafioBean> buscarPorId(Integer id) {
        logger.info("Buscando desafio com ID: {}", id);
        return desafioRepository.findById(id);
    }
    
    /**
     * Cria um novo desafio associado a um nível específico.
     */
    @Transactional
    public DesafioBean criarDesafio(Integer nivelId) {
        logger.info("Criando novo desafio para nível ID: {}", nivelId);
        
        // Busca o nível pelo ID
        Optional<NivelBean> nivelOpt = nivelRepository.findById(nivelId);
        if (!nivelOpt.isPresent()) {
            logger.error("Nível com ID {} não encontrado", nivelId);
            throw new IllegalArgumentException("Nível não encontrado");
        }
        
        // Cria um novo desafio
        DesafioBean desafio = new DesafioBean();
        desafio.setDesDatacadastro(LocalDate.now());
        desafio.setDesHoracadastro(LocalTime.now());
        desafio.setNivel(nivelOpt.get());
        
        // Salva o desafio
        return desafioRepository.save(desafio);
    }
    
    /**
     * Atualiza um desafio existente.
     */
    @Transactional
    public Optional<DesafioBean> atualizarDesafio(Integer desafioId, Integer nivelId) {
        logger.info("Atualizando desafio ID: {} para nível ID: {}", desafioId, nivelId);
        
        // Busca o desafio existente
        Optional<DesafioBean> desafioOpt = desafioRepository.findById(desafioId);
        if (!desafioOpt.isPresent()) {
            logger.error("Desafio com ID {} não encontrado", desafioId);
            return Optional.empty();
        }
        
        // Busca o novo nível
        Optional<NivelBean> nivelOpt = nivelRepository.findById(nivelId);
        if (!nivelOpt.isPresent()) {
            logger.error("Nível com ID {} não encontrado", nivelId);
            throw new IllegalArgumentException("Nível não encontrado");
        }
        
        // Atualiza o desafio
        DesafioBean desafio = desafioOpt.get();
        desafio.setNivel(nivelOpt.get());
        
        // Salva as alterações
        DesafioBean desafioAtualizado = desafioRepository.save(desafio);
        return Optional.of(desafioAtualizado);
    }
    
    /**
     * Remove um desafio pelo ID.
     */
    @Transactional
    public boolean removerDesafio(Integer desafioId) {
        logger.info("Removendo desafio ID: {}", desafioId);
        
        // Verifica se o desafio existe
        if (!desafioRepository.existsById(desafioId)) {
            logger.error("Desafio com ID {} não encontrado para remoção", desafioId);
            return false;
        }
        
        // Remove o desafio
        desafioRepository.deleteById(desafioId);
        return true;
    }
}