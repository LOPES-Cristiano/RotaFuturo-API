package br.com.rotafuturo.carreiras.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rotafuturo.carreiras.model.DesafioBean;
import br.com.rotafuturo.carreiras.service.DesafioService;

/**
 * Controller para gerenciar os endpoints relacionados aos desafios.
 */
@RestController
@RequestMapping("/api/desafios")
public class DesafioController {

    private static final Logger logger = LoggerFactory.getLogger(DesafioController.class);
    
    private final DesafioService desafioService;
    
    @Autowired
    public DesafioController(DesafioService desafioService) {
        this.desafioService = desafioService;
    }
    
    /**
     * Recupera todos os desafios.
     */
    @GetMapping
    public ResponseEntity<List<DesafioBean>> listarTodos() {
        logger.info("Requisição para listar todos os desafios");
        List<DesafioBean> desafios = desafioService.listarTodos();
        return ResponseEntity.ok(desafios);
    }
    
    /**
     * Recupera um desafio específico pelo ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DesafioBean> buscarPorId(@PathVariable Integer id) {
        logger.info("Requisição para buscar desafio com ID: {}", id);
        Optional<DesafioBean> desafioOpt = desafioService.buscarPorId(id);
        
        return desafioOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Cria um novo desafio.
     */
    @PostMapping
    public ResponseEntity<DesafioBean> criarDesafio(@RequestBody Map<String, Integer> request) {
        logger.info("Requisição para criar novo desafio");
        
        // Extrai o ID do nível do corpo da requisição
        Integer nivelId = request.get("nivelId");
        if (nivelId == null) {
            logger.error("ID do nível não fornecido");
            return ResponseEntity.badRequest().build();
        }
        
        try {
            DesafioBean novoDesafio = desafioService.criarDesafio(nivelId);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoDesafio);
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao criar desafio: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Atualiza um desafio existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DesafioBean> atualizarDesafio(
            @PathVariable Integer id,
            @RequestBody Map<String, Integer> request) {
        logger.info("Requisição para atualizar desafio com ID: {}", id);
        
        // Extrai o ID do nível do corpo da requisição
        Integer nivelId = request.get("nivelId");
        if (nivelId == null) {
            logger.error("ID do nível não fornecido");
            return ResponseEntity.badRequest().build();
        }
        
        try {
            Optional<DesafioBean> desafioAtualizado = desafioService.atualizarDesafio(id, nivelId);
            
            return desafioAtualizado.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao atualizar desafio: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Remove um desafio pelo ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerDesafio(@PathVariable Integer id) {
        logger.info("Requisição para remover desafio com ID: {}", id);
        
        boolean removido = desafioService.removerDesafio(id);
        
        if (removido) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}