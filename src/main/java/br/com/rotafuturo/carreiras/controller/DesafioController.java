package br.com.rotafuturo.carreiras.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rotafuturo.carreiras.dto.DesafioCreateDTO;
import br.com.rotafuturo.carreiras.dto.DesafioDTO;
import br.com.rotafuturo.carreiras.dto.DesafioUpdateDTO;
import br.com.rotafuturo.carreiras.dto.QuestionarioComQuestoesDTO;
import br.com.rotafuturo.carreiras.model.UsuarioBean;
import br.com.rotafuturo.carreiras.service.DesafioService;
import br.com.rotafuturo.carreiras.service.UsuarioService;

/**
 * Controller para gerenciar os endpoints relacionados aos desafios.
 */
@RestController
@RequestMapping("/api/desafios")
public class DesafioController {

    private static final Logger logger = LoggerFactory.getLogger(DesafioController.class);
    
    // Cache para prevenir submissões duplicadas (usuarioId_desafioId -> timestamp)
    private static final Map<String, Long> submissionCache = new ConcurrentHashMap<>();
    private static final long SUBMISSION_COOLDOWN_MS = 5000; // 5 segundos entre submissões
    
    private final DesafioService desafioService;
    private final UsuarioService usuarioService;
    
    @Autowired
    public DesafioController(DesafioService desafioService, UsuarioService usuarioService) {
        this.desafioService = desafioService;
        this.usuarioService = usuarioService;
    }
    
    /**
     * Recupera todos os desafios.
     */
    @GetMapping
    public ResponseEntity<List<DesafioDTO>> listarTodos() {
        logger.info("Requisição para listar todos os desafios");
        List<DesafioDTO> desafios = desafioService.listarTodosDTO();
        return ResponseEntity.ok(desafios);
    }
    
    /**
     * Recupera desafios filtrados pelas áreas do usuário autenticado.
     */
    @GetMapping("/usuario")
    public ResponseEntity<List<DesafioDTO>> listarDesafiosDoUsuario() {
        logger.info("Requisição para listar desafios do usuário autenticado");
        
        // Obtém o usuário autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            logger.error("Usuário não autenticado");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        String email = authentication.getName();
        Optional<UsuarioBean> usuarioOpt = usuarioService.buscarUsuarioPorEmail(email);
        
        if (!usuarioOpt.isPresent()) {
            logger.error("Usuário não encontrado: {}", email);
            return ResponseEntity.notFound().build();
        }
        
        Integer usuarioId = usuarioOpt.get().getUsuId();
        List<DesafioDTO> desafios = desafioService.listarDesafiosPorUsuario(usuarioId);
        
        return ResponseEntity.ok(desafios);
    }
    
    /**
     * Recupera um desafio específico pelo ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DesafioDTO> buscarPorId(@PathVariable Integer id) {
        logger.info("Requisição para buscar desafio com ID: {}", id);
        Optional<DesafioDTO> desafioOpt = desafioService.buscarPorIdDTO(id);
        
        return desafioOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Cria um novo desafio.
     */
    @PostMapping
    public ResponseEntity<DesafioDTO> criarDesafio(@RequestBody DesafioCreateDTO createDTO) {
        logger.info("Requisição para criar novo desafio: {}", createDTO.getDesTitulo());
        
        try {
            DesafioDTO novoDesafio = desafioService.criarDesafioDTO(createDTO);
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
    public ResponseEntity<DesafioDTO> atualizarDesafio(
            @PathVariable Integer id,
            @RequestBody DesafioUpdateDTO updateDTO) {
        logger.info("Requisição para atualizar desafio com ID: {}", id);
        
        try {
            Optional<DesafioDTO> desafioAtualizado = desafioService.atualizarDesafioDTO(id, updateDTO);
            
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
    
    /**
     * Busca o questionário vinculado ao desafio com todas as suas questões.
     */
    @GetMapping("/{id}/questionario")
    public ResponseEntity<QuestionarioComQuestoesDTO> buscarQuestionarioDoDesafio(@PathVariable Integer id) {
        logger.info("Requisição para buscar questionário do desafio ID: {}", id);
        
        Optional<QuestionarioComQuestoesDTO> questionario = desafioService.buscarQuestionarioDoDesafio(id);
        
        return questionario.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Salva as respostas do usuário para um desafio e retorna a pontuação obtida.
     */
    @PostMapping("/{id}/respostas")
    public ResponseEntity<br.com.rotafuturo.carreiras.dto.DesafioRealizadoDTO> salvarRespostas(
            @PathVariable Integer id,
            @RequestBody br.com.rotafuturo.carreiras.dto.DesafioRespostaDTO respostaDTO) {
        logger.info("Requisição para salvar respostas do desafio ID: {} do usuário ID: {}", 
                id, respostaDTO.getUsuarioId());
        
        try {
            // Valida que o ID do desafio no path corresponde ao DTO
            if (!id.equals(respostaDTO.getDesafioId())) {
                logger.error("ID do desafio no path ({}) não corresponde ao DTO ({})", 
                        id, respostaDTO.getDesafioId());
                return ResponseEntity.badRequest().build();
            }
            
            // PROTEÇÃO CONTRA SUBMISSÕES DUPLICADAS
            String cacheKey = respostaDTO.getUsuarioId() + "_" + id;
            Long lastSubmission = submissionCache.get(cacheKey);
            long now = System.currentTimeMillis();
            
            if (lastSubmission != null && (now - lastSubmission) < SUBMISSION_COOLDOWN_MS) {
                logger.warn("⚠️ SUBMISSÃO DUPLICADA BLOQUEADA - Usuário {} tentando enviar desafio {} " +
                           "novamente dentro de {} ms (cooldown: {} ms)", 
                           respostaDTO.getUsuarioId(), id, (now - lastSubmission), SUBMISSION_COOLDOWN_MS);
                return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body(null); // 429 Too Many Requests
            }
            
            // Marcar timestamp desta submissão
            submissionCache.put(cacheKey, now);
            
            // Limpar entradas antigas do cache (mais de 10 segundos)
            submissionCache.entrySet().removeIf(entry -> 
                (now - entry.getValue()) > 10000);
            
            br.com.rotafuturo.carreiras.dto.DesafioRealizadoDTO resultado = desafioService.salvarRespostas(respostaDTO);
            logger.info("✅ Respostas salvas com sucesso para usuário {} no desafio {}", 
                    respostaDTO.getUsuarioId(), id);
            return ResponseEntity.ok(resultado);
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao salvar respostas: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Erro inesperado ao salvar respostas", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Busca o histórico de desafios realizados por um usuário.
     */
    @GetMapping("/usuario/{usuarioId}/historico")
    public ResponseEntity<List<br.com.rotafuturo.carreiras.dto.DesafioRealizadoDTO>> buscarHistoricoUsuario(
            @PathVariable Integer usuarioId) {
        logger.info("Requisição para buscar histórico do usuário ID: {}", usuarioId);
        
        List<br.com.rotafuturo.carreiras.dto.DesafioRealizadoDTO> historico = 
                desafioService.buscarHistoricoUsuario(usuarioId);
        
        return ResponseEntity.ok(historico);
    }
    
    /**
     * Busca o ranking de um desafio (top pontuações).
     */
    @GetMapping("/{id}/ranking")
    public ResponseEntity<List<br.com.rotafuturo.carreiras.dto.DesafioRealizadoDTO>> buscarRankingDesafio(
            @PathVariable Integer id) {
        logger.info("Requisição para buscar ranking do desafio ID: {}", id);
        
        List<br.com.rotafuturo.carreiras.dto.DesafioRealizadoDTO> ranking = 
                desafioService.buscarRankingDesafio(id);
        
        return ResponseEntity.ok(ranking);
    }
    
    /**
     * Busca estatísticas de desafios de um usuário.
     */
    @GetMapping("/usuario/{usuarioId}/estatisticas")
    public ResponseEntity<br.com.rotafuturo.carreiras.dto.EstatisticasUsuarioDTO> buscarEstatisticasUsuario(
            @PathVariable Integer usuarioId) {
        logger.info("Requisição para buscar estatísticas do usuário ID: {}", usuarioId);
        
        br.com.rotafuturo.carreiras.dto.EstatisticasUsuarioDTO estatisticas = 
                desafioService.buscarEstatisticasUsuario(usuarioId);
        
        return ResponseEntity.ok(estatisticas);
    }
    
    /**
     * Verifica se usuário já realizou um desafio e retorna melhor pontuação.
     */
    @GetMapping("/{desafioId}/usuario/{usuarioId}/melhor-pontuacao")
    public ResponseEntity<br.com.rotafuturo.carreiras.dto.DesafioRealizadoDTO> buscarMelhorPontuacao(
            @PathVariable Integer desafioId,
            @PathVariable Integer usuarioId) {
        logger.info("Requisição para verificar se usuário {} realizou desafio {}", usuarioId, desafioId);
        
        Optional<br.com.rotafuturo.carreiras.dto.DesafioRealizadoDTO> melhorPontuacao = 
                desafioService.buscarMelhorPontuacao(usuarioId, desafioId);
        
        return melhorPontuacao.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Busca o ranking global de usuários por desafios completados (100% acerto).
     */
    @GetMapping("/ranking-global")
    public ResponseEntity<List<br.com.rotafuturo.carreiras.dto.RankingUsuarioDTO>> buscarRankingGlobal() {
        logger.info("Requisição para buscar ranking global (desafios perfeitos)");
        
        List<br.com.rotafuturo.carreiras.dto.RankingUsuarioDTO> ranking = 
                desafioService.buscarRankingGlobal(10); // Top 10
        
        return ResponseEntity.ok(ranking);
    }
    
    /**
     * Busca o ranking de usuários por total de tentativas.
     */
    @GetMapping("/ranking-tentativas")
    public ResponseEntity<List<br.com.rotafuturo.carreiras.dto.RankingUsuarioDTO>> buscarRankingPorTentativas() {
        logger.info("Requisição para buscar ranking por tentativas");
        
        List<br.com.rotafuturo.carreiras.dto.RankingUsuarioDTO> ranking = 
                desafioService.buscarRankingPorTentativas(10); // Top 10
        
        return ResponseEntity.ok(ranking);
    }
    
    /**
     * Busca a posição do usuário no ranking global.
     */
    @GetMapping("/usuario/{usuarioId}/posicao-ranking")
    public ResponseEntity<br.com.rotafuturo.carreiras.dto.RankingUsuarioDTO> buscarPosicaoRanking(
            @PathVariable Integer usuarioId) {
        logger.info("Requisição para buscar posição do usuário {} no ranking", usuarioId);
        
        br.com.rotafuturo.carreiras.dto.RankingUsuarioDTO posicao = 
                desafioService.buscarPosicaoUsuarioNoRanking(usuarioId);
        
        if (posicao == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(posicao);
    }
    
    /**
     * Busca o progresso do usuário na carreira.
     */
    @GetMapping("/usuario/{usuarioId}/progresso")
    public ResponseEntity<List<br.com.rotafuturo.carreiras.dto.ProgressoCarreiraDTO>> buscarProgressoCarreira(
            @PathVariable Integer usuarioId) {
        logger.info("Requisição para buscar progresso na carreira do usuário {}", usuarioId);
        
        List<br.com.rotafuturo.carreiras.dto.ProgressoCarreiraDTO> progresso = 
                desafioService.buscarProgressoCarreira(usuarioId);
        
        return ResponseEntity.ok(progresso);
    }
}