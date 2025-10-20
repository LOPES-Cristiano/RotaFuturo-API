package br.com.rotafuturo.carreiras.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rotafuturo.carreiras.model.PessoaBean;
import br.com.rotafuturo.carreiras.model.QuestaoBean;
import br.com.rotafuturo.carreiras.model.QuestaoCorretaHistoricoBean;
import br.com.rotafuturo.carreiras.model.UsuarioBean;
import br.com.rotafuturo.carreiras.repository.PessoaRepository;
import br.com.rotafuturo.carreiras.repository.QuestaoCorretaHistoricoRepository;
import br.com.rotafuturo.carreiras.repository.QuestaoRepository;
import br.com.rotafuturo.carreiras.repository.UsuarioRepository;

/**
 * Serviço responsável por gerenciar XP e níveis dos usuários.
 * 
 * Regras:
 * - Cada questão correta adiciona XP baseado no campo questao_experiencia
 * - O nível aumenta a cada 5 questões corretas
 */
@Service
public class NivelXpService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private QuestaoRepository questaoRepository;

    @Autowired
    private QuestaoCorretaHistoricoRepository questaoCorretaHistoricoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private static final int QUESTOES_POR_NIVEL = 5;

    /**
     * Adiciona XP ao usuário quando ele responde uma questão de teste vocacional corretamente.
     * Versão para TESTEQUESTAO (teste vocacional).
     * 
     * @param usuarioId ID do usuário
     * @param testeQuestaoId ID da questão do teste vocacional respondida
     * @param acertou Se o usuário acertou a questão
     * @return A pessoa atualizada
     */
    @Transactional
    public PessoaBean adicionarXpPorTesteQuestao(Integer usuarioId, Integer testeQuestaoId, boolean acertou) {
        if (!acertou) {
            return null; // Não adiciona XP se errou
        }

        // Buscar pessoa pelo usuário
        Optional<PessoaBean> pessoaOpt = pessoaRepository.findByUsuario_UsuId(usuarioId);
        if (pessoaOpt.isEmpty()) {
            throw new RuntimeException("Pessoa não encontrada para usuário ID: " + usuarioId);
        }

        // Buscar usuário
        Optional<UsuarioBean> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado ID: " + usuarioId);
        }

        PessoaBean pessoa = pessoaOpt.get();
        UsuarioBean usuario = usuarioOpt.get();

        // XP padrão para questões de teste vocacional (10 XP)
        Integer xpQuestao = 10;

        // Adicionar XP
        Integer xpAtual = pessoa.getPesXp() != null ? pessoa.getPesXp() : 0;
        Integer novoXp = xpAtual + xpQuestao;
        pessoa.setPesXp(novoXp);

        // Registrar no histórico (sem vínculo com QUESTAO, apenas contagem)
        QuestaoCorretaHistoricoBean historico = new QuestaoCorretaHistoricoBean();
        historico.setUsuario(usuario);
        historico.setQuestao(null); // Teste vocacional não usa tabela QUESTAO
        historico.setQchXpGanho(xpQuestao);
        historico.setQchDatacadastro(LocalDate.now());
        historico.setQchHoracadastro(LocalTime.now());
        questaoCorretaHistoricoRepository.save(historico);

        System.out.println("XP adicionado (Teste Vocacional): " + xpQuestao + " | XP total: " + novoXp + " | TesteQuestão: " + testeQuestaoId);

        // Salvar pessoa com novo XP
        pessoa = pessoaRepository.save(pessoa);

        // Verificar se deve subir de nível
        long totalQuestoesCorretas = questaoCorretaHistoricoRepository.contarQuestoesCorretasPorUsuario(usuarioId);
        int nivelCalculado = calcularNivelPorQuestoesCorretas((int) totalQuestoesCorretas);
        
        Integer nivelAtual = pessoa.getPesNivel() != null ? pessoa.getPesNivel() : 1;
        if (nivelCalculado > nivelAtual) {
            pessoa.setPesNivel(nivelCalculado);
            pessoa = pessoaRepository.save(pessoa);
            System.out.println("🎉 NÍVEL UP! Novo nível: " + nivelCalculado + " (Questões corretas: " + totalQuestoesCorretas + ")");
        }

        return pessoa;
    }

    /**
     * Adiciona XP ao usuário quando ele responde uma questão corretamente.
     * Também atualiza o nível se necessário.
     * Registra no histórico para evitar XP duplicado.
     * 
     * @param usuarioId ID do usuário
     * @param questaoId ID da questão respondida
     * @param acertou Se o usuário acertou a questão
     * @return A pessoa atualizada
     */
    @Transactional
    public PessoaBean adicionarXpPorQuestao(Integer usuarioId, Integer questaoId, boolean acertou) {
        if (!acertou) {
            return null; // Não adiciona XP se errou
        }

        // Verificar se já respondeu essa questão corretamente (evita XP duplicado)
        if (questaoCorretaHistoricoRepository.jaRespondeuQuestaoCorreta(usuarioId, questaoId)) {
            System.out.println("Usuário " + usuarioId + " já respondeu a questão " + questaoId + " corretamente. XP não adicionado.");
            return pessoaRepository.findByUsuario_UsuId(usuarioId).orElse(null);
        }

        // Buscar pessoa pelo usuário
        Optional<PessoaBean> pessoaOpt = pessoaRepository.findByUsuario_UsuId(usuarioId);
        if (pessoaOpt.isEmpty()) {
            throw new RuntimeException("Pessoa não encontrada para usuário ID: " + usuarioId);
        }

        // Buscar questão para pegar o XP
        Optional<QuestaoBean> questaoOpt = questaoRepository.findById(questaoId);
        if (questaoOpt.isEmpty()) {
            throw new RuntimeException("Questão não encontrada ID: " + questaoId);
        }

        // Buscar usuário
        Optional<UsuarioBean> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado ID: " + usuarioId);
        }

        PessoaBean pessoa = pessoaOpt.get();
        QuestaoBean questao = questaoOpt.get();
        UsuarioBean usuario = usuarioOpt.get();

        // Pegar XP da questão (campo questao_experiencia)
        Integer xpQuestao = questao.getQuestaoExperiencia();
        if (xpQuestao == null || xpQuestao <= 0) {
            xpQuestao = 10; // XP padrão caso não esteja definido
        }

        // Adicionar XP
        Integer xpAtual = pessoa.getPesXp() != null ? pessoa.getPesXp() : 0;
        Integer novoXp = xpAtual + xpQuestao;
        pessoa.setPesXp(novoXp);

        // Registrar no histórico
        QuestaoCorretaHistoricoBean historico = new QuestaoCorretaHistoricoBean();
        historico.setUsuario(usuario);
        historico.setQuestao(questao);
        historico.setQchXpGanho(xpQuestao);
        historico.setQchDatacadastro(LocalDate.now());
        historico.setQchHoracadastro(LocalTime.now());
        questaoCorretaHistoricoRepository.save(historico);

        System.out.println("XP adicionado: " + xpQuestao + " | XP total: " + novoXp + " | Questão: " + questaoId);

        // Salvar pessoa com novo XP
        pessoa = pessoaRepository.save(pessoa);

        // Verificar se deve subir de nível
        long totalQuestoesCorretas = questaoCorretaHistoricoRepository.contarQuestoesCorretasPorUsuario(usuarioId);
        int nivelCalculado = calcularNivelPorQuestoesCorretas((int) totalQuestoesCorretas);
        
        Integer nivelAtual = pessoa.getPesNivel() != null ? pessoa.getPesNivel() : 1;
        if (nivelCalculado > nivelAtual) {
            pessoa.setPesNivel(nivelCalculado);
            pessoa = pessoaRepository.save(pessoa);
            System.out.println("🎉 NÍVEL UP! Novo nível: " + nivelCalculado + " (Questões corretas: " + totalQuestoesCorretas + ")");
        }

        return pessoa;
    }

    /**
     * Calcula o nível baseado no total de questões corretas acumuladas.
     * A cada 5 questões corretas = +1 nível (começando do nível 1).
     * 
     * @param totalQuestoesCorretas Total de questões corretas
     * @return O nível calculado
     */
    public int calcularNivelPorQuestoesCorretas(int totalQuestoesCorretas) {
        return 1 + (totalQuestoesCorretas / QUESTOES_POR_NIVEL);
    }

    /**
     * Obtém informações de XP e nível de um usuário.
     * 
     * @param usuarioId ID do usuário
     * @return A pessoa com informações de XP e nível
     */
    public PessoaBean obterInfoNivelXp(Integer usuarioId) {
        Optional<PessoaBean> pessoaOpt = pessoaRepository.findByUsuario_UsuId(usuarioId);
        return pessoaOpt.orElse(null);
    }

    /**
     * Retorna o total de questões corretas do usuário.
     * 
     * @param usuarioId ID do usuário
     * @return Total de questões corretas
     */
    public long getTotalQuestoesCorretas(Integer usuarioId) {
        return questaoCorretaHistoricoRepository.contarQuestoesCorretasPorUsuario(usuarioId);
    }

    /**
     * Retorna o total de XP acumulado do usuário.
     * 
     * @param usuarioId ID do usuário
     * @return Total de XP
     */
    public int getTotalXp(Integer usuarioId) {
        return questaoCorretaHistoricoRepository.somarXpTotalPorUsuario(usuarioId);
    }

    /**
     * Retorna o ranking global de usuários ordenado por XP.
     * 
     * @param limite Número máximo de usuários no ranking
     * @return Lista de usuários ordenada por XP (maior para menor)
     */
    public java.util.List<RankingUsuarioXpDTO> obterRankingGlobal(int limite) {
        // Buscar todas as pessoas com XP > 0
        java.util.List<PessoaBean> todasPessoas = pessoaRepository.findAll();
        
        return todasPessoas.stream()
            .filter(p -> p.getPesXp() != null && p.getPesXp() > 0)
            .sorted((p1, p2) -> {
                // Ordenar por XP (maior primeiro)
                int compareXp = Integer.compare(
                    p2.getPesXp() != null ? p2.getPesXp() : 0,
                    p1.getPesXp() != null ? p1.getPesXp() : 0
                );
                if (compareXp != 0) return compareXp;
                
                // Em caso de empate, ordenar por nível
                return Integer.compare(
                    p2.getPesNivel() != null ? p2.getPesNivel() : 1,
                    p1.getPesNivel() != null ? p1.getPesNivel() : 1
                );
            })
            .limit(limite)
            .map(pessoa -> {
                long questoesCorretas = questaoCorretaHistoricoRepository
                    .contarQuestoesCorretasPorUsuario(pessoa.getUsuario().getUsuId());
                
                return new RankingUsuarioXpDTO(
                    pessoa.getPesId(),
                    pessoa.getUsuario().getUsuId(),
                    pessoa.getPesNome(),
                    pessoa.getPesApelido(),
                    pessoa.getPesNivel() != null ? pessoa.getPesNivel() : 1,
                    pessoa.getPesXp() != null ? pessoa.getPesXp() : 0,
                    questoesCorretas,
                    pessoa.getPesImagemperfil()
                );
            })
            .collect(java.util.stream.Collectors.toList());
    }

    // DTO para ranking
    public static class RankingUsuarioXpDTO {
        public Integer pessoaId;
        public Integer usuarioId;
        public String nome;
        public String apelido;
        public Integer nivel;
        public Integer xp;
        public long questoesCorretas;
        public String imagemPerfil;

        public RankingUsuarioXpDTO(Integer pessoaId, Integer usuarioId, String nome, String apelido, 
                                    Integer nivel, Integer xp, long questoesCorretas, 
                                    String imagemPerfil) {
            this.pessoaId = pessoaId;
            this.usuarioId = usuarioId;
            this.nome = nome;
            this.apelido = apelido;
            this.nivel = nivel;
            this.xp = xp;
            this.questoesCorretas = questoesCorretas;
            this.imagemPerfil = imagemPerfil;
        }
    }
}
