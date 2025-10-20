package br.com.rotafuturo.carreiras.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rotafuturo.carreiras.dto.AreaDTO;
import br.com.rotafuturo.carreiras.dto.AreaSubDTO;
import br.com.rotafuturo.carreiras.dto.DesafioCreateDTO;
import br.com.rotafuturo.carreiras.dto.DesafioDTO;
import br.com.rotafuturo.carreiras.dto.DesafioUpdateDTO;
import br.com.rotafuturo.carreiras.dto.NivelDTO;
import br.com.rotafuturo.carreiras.dto.QuestionarioComQuestoesDTO;
import br.com.rotafuturo.carreiras.model.AreaBean;
import br.com.rotafuturo.carreiras.model.AreaSubBean;
import br.com.rotafuturo.carreiras.model.DesafioBean;
import br.com.rotafuturo.carreiras.model.NivelBean;
import br.com.rotafuturo.carreiras.model.UsuarioAreaBean;
import br.com.rotafuturo.carreiras.repository.AreaRepository;
import br.com.rotafuturo.carreiras.repository.AreaSubRepository;
import br.com.rotafuturo.carreiras.repository.DesafioRepository;
import br.com.rotafuturo.carreiras.repository.NivelRepository;
import br.com.rotafuturo.carreiras.repository.UsuarioAreaRepository;

/**
 * Serviço para gerenciar os desafios da aplicação.
 */
@Service
public class DesafioService {

    private static final Logger logger = LoggerFactory.getLogger(DesafioService.class);
    
    private final DesafioRepository desafioRepository;
    private final NivelRepository nivelRepository;
    private final AreaRepository areaRepository;
    private final AreaSubRepository areaSubRepository;
    private final UsuarioAreaRepository usuarioAreaRepository;
    private final br.com.rotafuturo.carreiras.repository.DesafioQuestionarioRepository desafioQuestionarioRepository;
    private final br.com.rotafuturo.carreiras.repository.QuestionarioQuestaoRepository questionarioQuestaoRepository;
    private final br.com.rotafuturo.carreiras.repository.QuestaoRespondidaRepository questaoRespondidaRepository;
    private final br.com.rotafuturo.carreiras.repository.QuestaoRepository questaoRepository;
    private final br.com.rotafuturo.carreiras.repository.UsuarioRepository usuarioRepository;
    private final br.com.rotafuturo.carreiras.repository.QuestaoAlternativaRepository questaoAlternativaRepository;
    private final br.com.rotafuturo.carreiras.repository.DesafioRealizadoRepository desafioRealizadoRepository;
    private final br.com.rotafuturo.carreiras.repository.PessoaRepository pessoaRepository;
    private final NivelXpService nivelXpService;
    
    @Autowired
    public DesafioService(
            DesafioRepository desafioRepository, 
            NivelRepository nivelRepository,
            AreaRepository areaRepository,
            AreaSubRepository areaSubRepository,
            UsuarioAreaRepository usuarioAreaRepository,
            br.com.rotafuturo.carreiras.repository.DesafioQuestionarioRepository desafioQuestionarioRepository,
            br.com.rotafuturo.carreiras.repository.QuestionarioQuestaoRepository questionarioQuestaoRepository,
            br.com.rotafuturo.carreiras.repository.QuestaoRespondidaRepository questaoRespondidaRepository,
            br.com.rotafuturo.carreiras.repository.QuestaoRepository questaoRepository,
            br.com.rotafuturo.carreiras.repository.UsuarioRepository usuarioRepository,
            br.com.rotafuturo.carreiras.repository.QuestaoAlternativaRepository questaoAlternativaRepository,
            br.com.rotafuturo.carreiras.repository.DesafioRealizadoRepository desafioRealizadoRepository,
            br.com.rotafuturo.carreiras.repository.PessoaRepository pessoaRepository,
            NivelXpService nivelXpService) {
        this.desafioRepository = desafioRepository;
        this.nivelRepository = nivelRepository;
        this.areaRepository = areaRepository;
        this.areaSubRepository = areaSubRepository;
        this.usuarioAreaRepository = usuarioAreaRepository;
        this.desafioQuestionarioRepository = desafioQuestionarioRepository;
        this.questionarioQuestaoRepository = questionarioQuestaoRepository;
        this.questaoRespondidaRepository = questaoRespondidaRepository;
        this.questaoRepository = questaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.questaoAlternativaRepository = questaoAlternativaRepository;
        this.desafioRealizadoRepository = desafioRealizadoRepository;
        this.pessoaRepository = pessoaRepository;
        this.nivelXpService = nivelXpService;
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
    
    /**
     * Lista todos os desafios como DTOs.
     */
    public List<DesafioDTO> listarTodosDTO() {
        logger.info("Listando todos os desafios como DTOs");
        return desafioRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Busca um desafio por ID e retorna como DTO.
     */
    public Optional<DesafioDTO> buscarPorIdDTO(Integer id) {
        logger.info("Buscando desafio por ID como DTO: {}", id);
        return desafioRepository.findById(id)
                .map(this::convertToDTO);
    }
    
    /**
     * Cria um novo desafio a partir de um DTO.
     */
    @Transactional
    public DesafioDTO criarDesafioDTO(DesafioCreateDTO createDTO) {
        logger.info("Criando novo desafio: {}", createDTO.getDesTitulo());
        
        DesafioBean desafio = new DesafioBean();
        desafio.setDesTitulo(createDTO.getDesTitulo());
        desafio.setDesDescricao(createDTO.getDesDescricao());
        desafio.setDesDatacadastro(LocalDate.now());
        desafio.setDesHoracadastro(LocalTime.now());
        
        // Associa nível
        if (createDTO.getNivelId() != null) {
            NivelBean nivel = nivelRepository.findById(createDTO.getNivelId())
                    .orElseThrow(() -> new IllegalArgumentException("Nível não encontrado"));
            desafio.setNivel(nivel);
        }
        
        // Associa área
        if (createDTO.getAreaId() != null) {
            AreaBean area = areaRepository.findById(createDTO.getAreaId())
                    .orElseThrow(() -> new IllegalArgumentException("Área não encontrada"));
            desafio.setArea(area);
        }
        
        // Associa subárea
        if (createDTO.getAreaSubId() != null) {
            AreaSubBean areaSub = areaSubRepository.findById(createDTO.getAreaSubId())
                    .orElseThrow(() -> new IllegalArgumentException("Subárea não encontrada"));
            desafio.setAreaSub(areaSub);
        }
        
        DesafioBean saved = desafioRepository.save(desafio);
        return convertToDTO(saved);
    }
    
    /**
     * Atualiza um desafio existente.
     */
    @Transactional
    public Optional<DesafioDTO> atualizarDesafioDTO(Integer id, DesafioUpdateDTO updateDTO) {
        logger.info("Atualizando desafio ID: {}", id);
        
        Optional<DesafioBean> desafioOpt = desafioRepository.findById(id);
        if (!desafioOpt.isPresent()) {
            logger.error("Desafio com ID {} não encontrado", id);
            return Optional.empty();
        }
        
        DesafioBean desafio = desafioOpt.get();
        
        if (updateDTO.getDesTitulo() != null) {
            desafio.setDesTitulo(updateDTO.getDesTitulo());
        }
        if (updateDTO.getDesDescricao() != null) {
            desafio.setDesDescricao(updateDTO.getDesDescricao());
        }
        
        // Atualiza nível
        if (updateDTO.getNivelId() != null) {
            NivelBean nivel = nivelRepository.findById(updateDTO.getNivelId())
                    .orElseThrow(() -> new IllegalArgumentException("Nível não encontrado"));
            desafio.setNivel(nivel);
        }
        
        // Atualiza área
        if (updateDTO.getAreaId() != null) {
            AreaBean area = areaRepository.findById(updateDTO.getAreaId())
                    .orElseThrow(() -> new IllegalArgumentException("Área não encontrada"));
            desafio.setArea(area);
        }
        
        // Atualiza subárea
        if (updateDTO.getAreaSubId() != null) {
            AreaSubBean areaSub = areaSubRepository.findById(updateDTO.getAreaSubId())
                    .orElseThrow(() -> new IllegalArgumentException("Subárea não encontrada"));
            desafio.setAreaSub(areaSub);
        }
        
        DesafioBean updated = desafioRepository.save(desafio);
        return Optional.of(convertToDTO(updated));
    }
    
    /**
     * Lista desafios filtrados pelas áreas e subáreas exatas do usuário.
     * Usa correspondência precisa com AREA_ID e AREAS_ID da tabela USUARIOAREA.
     */
    public List<DesafioDTO> listarDesafiosPorUsuario(Integer usuarioId) {
        logger.info("Listando desafios para usuário ID: {}", usuarioId);
        
        // Busca as áreas/subáreas vinculadas ao usuário
        List<UsuarioAreaBean> usuarioAreas = usuarioAreaRepository.findSubareasByUsuarioId(usuarioId);
        
        // Se usuário não tem áreas vinculadas, busca área principal
        if (usuarioAreas.isEmpty()) {
            java.util.Optional<UsuarioAreaBean> areaOpt = usuarioAreaRepository.findByUsuario_UsuId(usuarioId);
            if (areaOpt.isPresent()) {
                usuarioAreas = List.of(areaOpt.get());
            }
        }
        
        // Se ainda não tem áreas, retorna lista vazia
        if (usuarioAreas.isEmpty()) {
            logger.warn("Usuário {} não tem áreas vinculadas", usuarioId);
            return List.of();
        }
        
        // Coleta IDs EXATOS de áreas e subáreas da tabela USUARIOAREA
        List<Integer> areaIds = usuarioAreas.stream()
                .map(ua -> ua.getArea() != null ? ua.getArea().getAreaId() : null)
                .filter(id -> id != null)
                .distinct()
                .collect(Collectors.toList());
        
        List<Integer> areaSubIds = usuarioAreas.stream()
                .map(ua -> ua.getAreaSub() != null ? ua.getAreaSub().getAreasId() : null)
                .filter(id -> id != null)
                .distinct()
                .collect(Collectors.toList());
        
        logger.info("IDs de áreas do usuário: {}", areaIds);
        logger.info("IDs de subáreas do usuário: {}", areaSubIds);
        
        // Busca desafios com correspondência EXATA (AND)
        List<DesafioBean> desafios;
        if (!areaIds.isEmpty() && !areaSubIds.isEmpty()) {
            // Usa correspondência exata: área AND subárea
            desafios = desafioRepository.findByAreaAndAreaSubIds(areaIds, areaSubIds);
            logger.info("Buscando com filtro AND (área E subárea)");
        } else if (!areaIds.isEmpty()) {
            desafios = desafioRepository.findByAreaIds(areaIds);
            logger.info("Buscando apenas por área");
        } else {
            desafios = desafioRepository.findByAreaSubIds(areaSubIds);
            logger.info("Buscando apenas por subárea");
        }
        
        logger.info("Encontrados {} desafios para o usuário", desafios.size());
        
        return desafios.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Converte DesafioBean para DesafioDTO.
     */
    private DesafioDTO convertToDTO(DesafioBean bean) {
        DesafioDTO dto = new DesafioDTO();
        dto.setDesId(bean.getDesId());
        dto.setDesTitulo(bean.getDesTitulo());
        dto.setDesDescricao(bean.getDesDescricao());
        dto.setDesDatacadastro(bean.getDesDatacadastro());
        dto.setDesHoracadastro(bean.getDesHoracadastro());
        
        if (bean.getNivel() != null) {
            NivelDTO nivelDTO = new NivelDTO();
            nivelDTO.setNivId(bean.getNivel().getNivId());
            nivelDTO.setNivDescricao(bean.getNivel().getNivDescricao());
            dto.setNivel(nivelDTO);
        }
        
        if (bean.getArea() != null) {
            AreaDTO areaDTO = new AreaDTO();
            areaDTO.setAreaId(bean.getArea().getAreaId());
            areaDTO.setAreaDescricao(bean.getArea().getAreaDescricao());
            dto.setArea(areaDTO);
        }
        
        if (bean.getAreaSub() != null) {
            AreaSubDTO areaSubDTO = new AreaSubDTO();
            areaSubDTO.setAreasId(bean.getAreaSub().getAreasId());
            areaSubDTO.setAreasDescricao(bean.getAreaSub().getAreasDescricao());
            dto.setAreaSub(areaSubDTO);
        }
        
        return dto;
    }
    
    /**
     * Busca o questionário vinculado ao desafio com todas as suas questões.
     */
    public Optional<QuestionarioComQuestoesDTO> buscarQuestionarioDoDesafio(Integer desafioId) {
        logger.info("Buscando questionário para desafio ID: {}", desafioId);
        
        // Busca o vínculo desafio-questionário
        List<br.com.rotafuturo.carreiras.model.DesafioQuestionarioBean> vinculos = 
                desafioQuestionarioRepository.findByDesafio_DesId(desafioId);
        
        if (vinculos.isEmpty()) {
            logger.warn("Nenhum questionário encontrado para desafio ID: {}", desafioId);
            return Optional.empty();
        }
        
        // Pega o primeiro questionário (assumindo 1 questionário por desafio)
        br.com.rotafuturo.carreiras.model.QuestionarioBean questionario = vinculos.get(0).getQuestionario();
        
        // Busca as questões do questionário
        List<br.com.rotafuturo.carreiras.model.QuestionarioQuestaoBean> questoesBean = 
                questionarioQuestaoRepository.findByQuestionario_QuesIdAndQuestqAtivoTrue(questionario.getQuesId());
        
        // Converte para DTO
        QuestionarioComQuestoesDTO dto = new QuestionarioComQuestoesDTO();
        dto.setQuesId(questionario.getQuesId());
        dto.setQuesDescricao(questionario.getQuesDescricao());
        dto.setQuesPeso(questionario.getQuesPeso());
        
        List<QuestionarioComQuestoesDTO.QuestaoDTO> questoesDTO = questoesBean.stream()
                .filter(qq -> qq.getQuestao() != null && qq.getQuestao().getQuestaoAtivo())
                .map(qq -> {
                    br.com.rotafuturo.carreiras.model.QuestaoBean questao = qq.getQuestao();
                    QuestionarioComQuestoesDTO.QuestaoDTO qDTO = new QuestionarioComQuestoesDTO.QuestaoDTO();
                    qDTO.setQuestaoId(questao.getQuestaoId());
                    qDTO.setQuestaoCodigo(questao.getQuestaoCodigo());
                    qDTO.setQuestaoDescricao(questao.getQuestaoDescricao());
                    qDTO.setQuestaoExperiencia(questao.getQuestaoExperiencia());
                    
                    if (questao.getQuestaoTipo() != null) {
                        qDTO.setQuestaoTipo(questao.getQuestaoTipo().getQuetDescricao());
                    }
                    
                    // Busca as alternativas da questão
                    List<br.com.rotafuturo.carreiras.model.QuestaoAlternativaBean> alternativasBean = 
                            questaoAlternativaRepository.findByQuestao_QuestaoIdAndQuesaAtivoTrue(questao.getQuestaoId());
                    
                    List<QuestionarioComQuestoesDTO.AlternativaDTO> alternativasDTO = alternativasBean.stream()
                            .map(alt -> {
                                QuestionarioComQuestoesDTO.AlternativaDTO aDTO = new QuestionarioComQuestoesDTO.AlternativaDTO();
                                aDTO.setQuesaId(alt.getQuesaId());
                                aDTO.setQuesaDescricao(alt.getQuesaDescricao());
                                // Não envia se é correta para o frontend (para não dar spoiler)
                                aDTO.setQuesaCorreta(false);
                                return aDTO;
                            })
                            .collect(Collectors.toList());
                    
                    qDTO.setAlternativas(alternativasDTO);
                    
                    return qDTO;
                })
                .collect(Collectors.toList());
        
        dto.setQuestoes(questoesDTO);
        
        logger.info("Questionário encontrado com {} questões", questoesDTO.size());
        
        return Optional.of(dto);
    }
    
    /**
     * Salva as respostas do usuário para um desafio e registra a pontuação.
     * Retorna um DTO com os dados da realização do desafio.
     */
    @Transactional
    public br.com.rotafuturo.carreiras.dto.DesafioRealizadoDTO salvarRespostas(
            br.com.rotafuturo.carreiras.dto.DesafioRespostaDTO respostaDTO) {
        logger.info("Salvando respostas do desafio ID: {} para usuário ID: {}", 
                respostaDTO.getDesafioId(), respostaDTO.getUsuarioId());
        
        // Busca o usuário
        br.com.rotafuturo.carreiras.model.UsuarioBean usuario = usuarioRepository.findById(respostaDTO.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        
        // Busca o desafio
        DesafioBean desafio = desafioRepository.findById(respostaDTO.getDesafioId())
                .orElseThrow(() -> new IllegalArgumentException("Desafio não encontrado"));
        
        int acertos = 0;
        int totalQuestoes = respostaDTO.getRespostas().size();
        
        // Processa cada resposta e conta acertos
        for (br.com.rotafuturo.carreiras.dto.DesafioRespostaDTO.RespostaQuestao resposta : respostaDTO.getRespostas()) {
            // Busca a questão
            br.com.rotafuturo.carreiras.model.QuestaoBean questao = questaoRepository.findById(resposta.getQuestaoId())
                    .orElseThrow(() -> new IllegalArgumentException("Questão não encontrada: " + resposta.getQuestaoId()));
            
            // Busca a alternativa
            br.com.rotafuturo.carreiras.model.QuestaoAlternativaBean alternativa = 
                    questaoAlternativaRepository.findById(resposta.getAlternativaId())
                    .orElseThrow(() -> new IllegalArgumentException("Alternativa não encontrada: " + resposta.getAlternativaId()));
            
            // Conta acerto se a alternativa está correta
            boolean acertou = alternativa.getQuesaCorreta() != null && alternativa.getQuesaCorreta() == 1;
            if (acertou) {
                acertos++;
                
                // 🎯 SISTEMA DE XP - Adiciona XP por questão acertada
                try {
                    nivelXpService.adicionarXpPorQuestao(
                        respostaDTO.getUsuarioId(), 
                        questao.getQuestaoId(), 
                        true
                    );
                    logger.info("✅ XP adicionado para usuário {} na questão {}", 
                        respostaDTO.getUsuarioId(), questao.getQuestaoId());
                } catch (Exception e) {
                    logger.warn("⚠️ Erro ao adicionar XP para questão {}: {}", 
                        questao.getQuestaoId(), e.getMessage());
                }
            }
            
            // Cria o registro de resposta
            br.com.rotafuturo.carreiras.model.QuestaoRespondidaBean questaoRespondida = 
                    new br.com.rotafuturo.carreiras.model.QuestaoRespondidaBean();
            questaoRespondida.setQuesrDatacadastro(LocalDate.now());
            questaoRespondida.setQuesrHoracadastro(LocalTime.now());
            questaoRespondida.setQuestao(questao);
            questaoRespondida.setUsuario(usuario);
            questaoRespondida.setAlternativa(alternativa);
            
            // Salva a resposta
            questaoRespondidaRepository.save(questaoRespondida);
        }
        
        // Calcula a pontuação (0.00 a 100.00)
        java.math.BigDecimal pontuacao = java.math.BigDecimal.valueOf(acertos)
                .divide(java.math.BigDecimal.valueOf(totalQuestoes), 2, java.math.RoundingMode.HALF_UP)
                .multiply(java.math.BigDecimal.valueOf(100));
        
        // Cria o registro do desafio realizado
        br.com.rotafuturo.carreiras.model.DesafioRealizadoBean desafioRealizado = 
                new br.com.rotafuturo.carreiras.model.DesafioRealizadoBean();
        desafioRealizado.setDesafio(desafio);
        desafioRealizado.setUsuario(usuario);
        desafioRealizado.setDesreAcertos(acertos);
        desafioRealizado.setDesreTotalQuestoes(totalQuestoes);
        desafioRealizado.setDesrePontuacao(pontuacao);
        desafioRealizado.setDesreDatacadastro(LocalDate.now());
        desafioRealizado.setDesreHoracadastro(LocalTime.now());
        
        // Salva o desafio realizado
        br.com.rotafuturo.carreiras.model.DesafioRealizadoBean saved = desafioRealizadoRepository.save(desafioRealizado);
        
        // 🎁 BÔNUS DE XP - Se acertou TODAS as questões, ganha bônus aleatório entre 100-500 XP
        if (acertos == totalQuestoes && totalQuestoes > 0) {
            try {
                // Gera bônus aleatório entre 100 e 500 XP
                java.util.Random random = new java.util.Random();
                int bonusXp = 100 + random.nextInt(401); // 100 + [0-400] = [100-500]
                
                // Adiciona bônus diretamente à pessoa
                br.com.rotafuturo.carreiras.model.PessoaBean pessoa = 
                    pessoaRepository.findByUsuario_UsuId(respostaDTO.getUsuarioId())
                        .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));
                
                Integer xpAtual = pessoa.getPesXp() != null ? pessoa.getPesXp() : 0;
                pessoa.setPesXp(xpAtual + bonusXp);
                
                // Recalcula o nível baseado no total de questões corretas
                Long totalCorretasLong = nivelXpService.getTotalQuestoesCorretas(respostaDTO.getUsuarioId());
                Integer novoNivel = nivelXpService.calcularNivelPorQuestoesCorretas(totalCorretasLong.intValue());
                pessoa.setPesNivel(novoNivel);
                
                pessoaRepository.save(pessoa);
                
                logger.info("🎁 DESAFIO PERFEITO! Usuário {} ganhou {} XP de bônus! Total XP: {}, Nível: {}", 
                    respostaDTO.getUsuarioId(), bonusXp, pessoa.getPesXp(), novoNivel);
            } catch (Exception e) {
                logger.warn("⚠️ Erro ao adicionar bônus de XP por desafio completo: {}", e.getMessage());
            }
        }
        
        logger.info("Desafio realizado salvo com sucesso. Acertos: {}/{}, Pontuação: {}", 
                acertos, totalQuestoes, pontuacao);
        
        // Converte para DTO
        br.com.rotafuturo.carreiras.dto.DesafioRealizadoDTO dto = 
                new br.com.rotafuturo.carreiras.dto.DesafioRealizadoDTO();
        dto.setDesreId(saved.getDesreId());
        dto.setDesafioId(desafio.getDesId());
        dto.setDesafioTitulo(desafio.getDesTitulo());
        dto.setUsuarioId(usuario.getUsuId());
        dto.setDesreAcertos(acertos);
        dto.setDesreTotalQuestoes(totalQuestoes);
        dto.setDesrePontuacao(pontuacao);
        dto.setDesreDatacadastro(saved.getDesreDatacadastro());
        dto.setDesreHoracadastro(saved.getDesreHoracadastro());
        
        return dto;
    }
    
    /**
     * Busca o histórico de desafios realizados por um usuário.
     */
    public List<br.com.rotafuturo.carreiras.dto.DesafioRealizadoDTO> buscarHistoricoUsuario(Integer usuarioId) {
        logger.info("Buscando histórico de desafios do usuário ID: {}", usuarioId);
        
        List<br.com.rotafuturo.carreiras.model.DesafioRealizadoBean> historico = 
                desafioRealizadoRepository.findByUsuario_UsuIdOrderByDesreDatacadastroDesc(usuarioId);
        
        return historico.stream()
                .map(this::convertDesafioRealizadoToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Busca o ranking de um desafio específico (top pontuações).
     */
    public List<br.com.rotafuturo.carreiras.dto.DesafioRealizadoDTO> buscarRankingDesafio(Integer desafioId) {
        logger.info("Buscando ranking do desafio ID: {}", desafioId);
        
        List<br.com.rotafuturo.carreiras.model.DesafioRealizadoBean> ranking = 
                desafioRealizadoRepository.findRankingByDesafio(desafioId);
        
        return ranking.stream()
                .map(this::convertDesafioRealizadoToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Verifica se usuário já realizou um desafio e retorna a melhor pontuação.
     */
    public Optional<br.com.rotafuturo.carreiras.dto.DesafioRealizadoDTO> buscarMelhorPontuacao(
            Integer usuarioId, Integer desafioId) {
        logger.info("Buscando melhor pontuação do usuário {} no desafio {}", usuarioId, desafioId);
        
        return desafioRealizadoRepository.findMelhorPontuacao(usuarioId, desafioId)
                .map(this::convertDesafioRealizadoToDTO);
    }
    
    /**
     * Retorna estatísticas do usuário: total completados (100%), tentativas, média de pontuação, melhor pontuação, eficiência.
     */
    public br.com.rotafuturo.carreiras.dto.EstatisticasUsuarioDTO buscarEstatisticasUsuario(Integer usuarioId) {
        logger.info("Buscando estatísticas do usuário ID: {}", usuarioId);
        
        List<br.com.rotafuturo.carreiras.model.DesafioRealizadoBean> desafiosRealizados = 
                desafioRealizadoRepository.findByUsuario_UsuIdOrderByDesreDatacadastroDesc(usuarioId);
        
        // Total de tentativas (todas as execuções, incluindo não perfeitas)
        long totalTentativas = desafiosRealizados.size();
        
        // Desafios únicos com 100% de acerto (apenas os perfeitos)
        long totalCompletados = desafiosRealizados.stream()
                .filter(dr -> dr.getDesrePontuacao().compareTo(java.math.BigDecimal.valueOf(100)) == 0)
                .map(dr -> dr.getDesafio().getDesId())
                .distinct()
                .count();
        
        java.math.BigDecimal mediaPontuacao = java.math.BigDecimal.ZERO;
        java.math.BigDecimal melhorPontuacao = java.math.BigDecimal.ZERO;
        java.math.BigDecimal percentualEficiencia = java.math.BigDecimal.ZERO;
        
        if (totalTentativas > 0) {
            // Calcula média de pontuação (de todas as tentativas)
            java.math.BigDecimal soma = desafiosRealizados.stream()
                    .map(br.com.rotafuturo.carreiras.model.DesafioRealizadoBean::getDesrePontuacao)
                    .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
            
            mediaPontuacao = soma.divide(
                    java.math.BigDecimal.valueOf(totalTentativas), 
                    2, 
                    java.math.RoundingMode.HALF_UP);
            
            // Encontra melhor pontuação
            melhorPontuacao = desafiosRealizados.stream()
                    .map(br.com.rotafuturo.carreiras.model.DesafioRealizadoBean::getDesrePontuacao)
                    .max(java.math.BigDecimal::compareTo)
                    .orElse(java.math.BigDecimal.ZERO);
            
            // Calcula percentual de eficiência: (desafios perfeitos / total tentativas) * 100
            percentualEficiencia = java.math.BigDecimal.valueOf(totalCompletados)
                    .multiply(java.math.BigDecimal.valueOf(100))
                    .divide(java.math.BigDecimal.valueOf(totalTentativas), 2, java.math.RoundingMode.HALF_UP);
        }
        
        br.com.rotafuturo.carreiras.dto.EstatisticasUsuarioDTO estatisticas = 
                new br.com.rotafuturo.carreiras.dto.EstatisticasUsuarioDTO();
        estatisticas.setUsuarioId(usuarioId);
        estatisticas.setTotalDesafiosCompletados(totalCompletados);
        estatisticas.setTotalTentativas(totalTentativas);
        estatisticas.setMediaPontuacao(mediaPontuacao);
        estatisticas.setMelhorPontuacao(melhorPontuacao);
        estatisticas.setPercentualEficiencia(percentualEficiencia);
        
        return estatisticas;
    }
    
    /**
     * Retorna o ranking global de usuários ordenado por desafios completados (100% acerto).
     * Considera apenas desafios com pontuação perfeita.
     */
    public List<br.com.rotafuturo.carreiras.dto.RankingUsuarioDTO> buscarRankingGlobal(int limite) {
        logger.info("Buscando ranking global (desafios perfeitos) com limite de {} usuários", limite);
        
        // Busca todos os usuários
        List<br.com.rotafuturo.carreiras.model.UsuarioBean> usuarios = usuarioRepository.findAll();
        
        List<br.com.rotafuturo.carreiras.dto.RankingUsuarioDTO> ranking = new java.util.ArrayList<>();
        
        for (br.com.rotafuturo.carreiras.model.UsuarioBean usuario : usuarios) {
            br.com.rotafuturo.carreiras.dto.EstatisticasUsuarioDTO estatisticas = 
                    buscarEstatisticasUsuario(usuario.getUsuId());
            
            // Só inclui usuários que completaram pelo menos 1 desafio com 100%
            if (estatisticas.getTotalDesafiosCompletados() > 0) {
                br.com.rotafuturo.carreiras.dto.RankingUsuarioDTO rankingDTO = 
                        new br.com.rotafuturo.carreiras.dto.RankingUsuarioDTO();
                rankingDTO.setUsuarioId(usuario.getUsuId());
                
                // Busca o nome do usuário via Pessoa
                Optional<br.com.rotafuturo.carreiras.model.PessoaBean> pessoaOpt = 
                        pessoaRepository.findByUsuario_UsuId(usuario.getUsuId());
                rankingDTO.setUsuarioNome(
                        pessoaOpt.map(br.com.rotafuturo.carreiras.model.PessoaBean::getPesNome)
                                .orElse("Usuário #" + usuario.getUsuId()));
                
                rankingDTO.setTotalDesafiosCompletados(estatisticas.getTotalDesafiosCompletados());
                rankingDTO.setTotalTentativas(estatisticas.getTotalTentativas());
                rankingDTO.setMediaPontuacao(estatisticas.getMediaPontuacao());
                rankingDTO.setMelhorPontuacao(estatisticas.getMelhorPontuacao());
                rankingDTO.setPercentualEficiencia(estatisticas.getPercentualEficiencia());
                
                ranking.add(rankingDTO);
            }
        }
        
        // Ordena por total de desafios completados (100%), depois por percentual de eficiência
        ranking.sort((a, b) -> {
            int compareCompletados = b.getTotalDesafiosCompletados().compareTo(a.getTotalDesafiosCompletados());
            if (compareCompletados != 0) return compareCompletados;
            return b.getPercentualEficiencia().compareTo(a.getPercentualEficiencia());
        });
        
        // Define as posições
        for (int i = 0; i < ranking.size(); i++) {
            ranking.get(i).setPosicao(i + 1);
        }
        
        // Retorna apenas o limite solicitado
        return ranking.stream().limit(limite).collect(Collectors.toList());
    }
    
    /**
     * Retorna o ranking de usuários ordenado por total de tentativas.
     * Considera todas as tentativas, não apenas as perfeitas.
     */
    public List<br.com.rotafuturo.carreiras.dto.RankingUsuarioDTO> buscarRankingPorTentativas(int limite) {
        logger.info("Buscando ranking por tentativas com limite de {} usuários", limite);
        
        // Busca todos os usuários
        List<br.com.rotafuturo.carreiras.model.UsuarioBean> usuarios = usuarioRepository.findAll();
        
        List<br.com.rotafuturo.carreiras.dto.RankingUsuarioDTO> ranking = new java.util.ArrayList<>();
        
        for (br.com.rotafuturo.carreiras.model.UsuarioBean usuario : usuarios) {
            br.com.rotafuturo.carreiras.dto.EstatisticasUsuarioDTO estatisticas = 
                    buscarEstatisticasUsuario(usuario.getUsuId());
            
            // Só inclui usuários que fizeram pelo menos 1 tentativa
            if (estatisticas.getTotalTentativas() > 0) {
                br.com.rotafuturo.carreiras.dto.RankingUsuarioDTO rankingDTO = 
                        new br.com.rotafuturo.carreiras.dto.RankingUsuarioDTO();
                rankingDTO.setUsuarioId(usuario.getUsuId());
                
                // Busca o nome do usuário via Pessoa
                Optional<br.com.rotafuturo.carreiras.model.PessoaBean> pessoaOpt = 
                        pessoaRepository.findByUsuario_UsuId(usuario.getUsuId());
                rankingDTO.setUsuarioNome(
                        pessoaOpt.map(br.com.rotafuturo.carreiras.model.PessoaBean::getPesNome)
                                .orElse("Usuário #" + usuario.getUsuId()));
                
                rankingDTO.setTotalDesafiosCompletados(estatisticas.getTotalDesafiosCompletados());
                rankingDTO.setTotalTentativas(estatisticas.getTotalTentativas());
                rankingDTO.setMediaPontuacao(estatisticas.getMediaPontuacao());
                rankingDTO.setMelhorPontuacao(estatisticas.getMelhorPontuacao());
                rankingDTO.setPercentualEficiencia(estatisticas.getPercentualEficiencia());
                
                ranking.add(rankingDTO);
            }
        }
        
        // Ordena por total de tentativas (maior primeiro), depois por média de pontuação
        ranking.sort((a, b) -> {
            int compareTentativas = b.getTotalTentativas().compareTo(a.getTotalTentativas());
            if (compareTentativas != 0) return compareTentativas;
            return b.getMediaPontuacao().compareTo(a.getMediaPontuacao());
        });
        
        // Define as posições
        for (int i = 0; i < ranking.size(); i++) {
            ranking.get(i).setPosicao(i + 1);
        }
        
        // Retorna apenas o limite solicitado
        return ranking.stream().limit(limite).collect(Collectors.toList());
    }
    
    /**
     * Busca a posição do usuário no ranking global e retorna contexto (usuários próximos).
     */
    public br.com.rotafuturo.carreiras.dto.RankingUsuarioDTO buscarPosicaoUsuarioNoRanking(Integer usuarioId) {
        logger.info("Buscando posição do usuário {} no ranking", usuarioId);
        
        List<br.com.rotafuturo.carreiras.dto.RankingUsuarioDTO> rankingCompleto = 
                buscarRankingGlobal(Integer.MAX_VALUE);
        
        return rankingCompleto.stream()
                .filter(r -> r.getUsuarioId().equals(usuarioId))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Retorna o progresso do usuário na carreira (áreas escolhidas).
     */
    public List<br.com.rotafuturo.carreiras.dto.ProgressoCarreiraDTO> buscarProgressoCarreira(Integer usuarioId) {
        logger.info("Buscando progresso na carreira do usuário {}", usuarioId);
        
        // Busca as áreas do usuário
        List<UsuarioAreaBean> usuarioAreas = usuarioAreaRepository.findSubareasByUsuarioId(usuarioId);
        
        if (usuarioAreas.isEmpty()) {
            Optional<UsuarioAreaBean> areaOpt = usuarioAreaRepository.findByUsuario_UsuId(usuarioId);
            if (areaOpt.isPresent()) {
                usuarioAreas = List.of(areaOpt.get());
            }
        }
        
        List<br.com.rotafuturo.carreiras.dto.ProgressoCarreiraDTO> progresso = new java.util.ArrayList<>();
        
        for (UsuarioAreaBean usuarioArea : usuarioAreas) {
            AreaBean area = usuarioArea.getArea();
            AreaSubBean areaSub = usuarioArea.getAreaSub();
            
            if (area == null) continue;
            
            br.com.rotafuturo.carreiras.dto.ProgressoCarreiraDTO progressoDTO = 
                    new br.com.rotafuturo.carreiras.dto.ProgressoCarreiraDTO();
            
            progressoDTO.setAreaId(area.getAreaId());
            progressoDTO.setAreaDescricao(area.getAreaDescricao());
            
            if (areaSub != null) {
                progressoDTO.setAreaSubId(areaSub.getAreasId());
                progressoDTO.setAreaSubDescricao(areaSub.getAreasDescricao());
            }
            
            // Conta total de desafios disponíveis na área/subárea
            List<DesafioBean> desafiosDisponiveis;
            if (areaSub != null) {
                desafiosDisponiveis = desafioRepository.findByArea_AreaIdAndAreaSub_AreasId(
                        area.getAreaId(), areaSub.getAreasId());
            } else {
                desafiosDisponiveis = desafioRepository.findByArea_AreaId(area.getAreaId());
            }
            
            progressoDTO.setTotalDesafios((long) desafiosDisponiveis.size());
            
            // Conta desafios completados pelo usuário nessa área
            List<br.com.rotafuturo.carreiras.model.DesafioRealizadoBean> realizados = 
                    desafioRealizadoRepository.findByUsuario_UsuIdOrderByDesreDatacadastroDesc(usuarioId);
            
            long completados = realizados.stream()
                    .map(dr -> dr.getDesafio().getDesId())
                    .distinct()
                    .filter(desafioId -> desafiosDisponiveis.stream()
                            .anyMatch(d -> d.getDesId().equals(desafioId)))
                    .count();
            
            progressoDTO.setDesafiosCompletados(completados);
            
            // Calcula percentual
            double percentual = desafiosDisponiveis.isEmpty() ? 0.0 : 
                    (completados * 100.0) / desafiosDisponiveis.size();
            progressoDTO.setPercentualConcluido(percentual);
            
            progresso.add(progressoDTO);
        }
        
        return progresso;
    }
    
    /**
     * Converte DesafioRealizadoBean para DTO.
     */
    private br.com.rotafuturo.carreiras.dto.DesafioRealizadoDTO convertDesafioRealizadoToDTO(
            br.com.rotafuturo.carreiras.model.DesafioRealizadoBean bean) {
        br.com.rotafuturo.carreiras.dto.DesafioRealizadoDTO dto = 
                new br.com.rotafuturo.carreiras.dto.DesafioRealizadoDTO();
        dto.setDesreId(bean.getDesreId());
        dto.setDesafioId(bean.getDesafio().getDesId());
        dto.setDesafioTitulo(bean.getDesafio().getDesTitulo());
        dto.setUsuarioId(bean.getUsuario().getUsuId());
        dto.setDesreAcertos(bean.getDesreAcertos());
        dto.setDesreTotalQuestoes(bean.getDesreTotalQuestoes());
        dto.setDesrePontuacao(bean.getDesrePontuacao());
        dto.setDesreDatacadastro(bean.getDesreDatacadastro());
        dto.setDesreHoracadastro(bean.getDesreHoracadastro());
        return dto;
    }
}