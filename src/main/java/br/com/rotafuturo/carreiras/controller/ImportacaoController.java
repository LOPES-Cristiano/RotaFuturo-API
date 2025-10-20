package br.com.rotafuturo.carreiras.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.rotafuturo.carreiras.annotation.RequiresAdmin;
import br.com.rotafuturo.carreiras.dto.importacao.ImportacaoDesafiosResultadoDTO;
import br.com.rotafuturo.carreiras.dto.importacao.ImportacaoEstruturaBaseResultadoDTO;
import br.com.rotafuturo.carreiras.dto.importacao.ImportacaoQuestionariosQuestoesResultadoDTO;
import br.com.rotafuturo.carreiras.dto.importacao.ImportacaoResultadoDTO;
import br.com.rotafuturo.carreiras.dto.importacao.ImportacaoTestesVocacionaisResultadoDTO;
import br.com.rotafuturo.carreiras.service.ImportacaoDesafiosService;
import br.com.rotafuturo.carreiras.service.ImportacaoEstruturaBaseService;
import br.com.rotafuturo.carreiras.service.ImportacaoQuestionariosQuestoesService;
import br.com.rotafuturo.carreiras.service.ImportacaoTestesVocacionaisService;

@RestController
@RequestMapping("/api/importacao")
@CrossOrigin(origins = "*")
public class ImportacaoController {

    @Autowired
    private ImportacaoEstruturaBaseService importacaoEstruturaBaseService;

    @Autowired
    private ImportacaoTestesVocacionaisService importacaoTestesVocacionaisService;

    @Autowired
    private ImportacaoQuestionariosQuestoesService importacaoQuestionariosQuestoesService;

    @Autowired
    private ImportacaoDesafiosService importacaoDesafiosService;

    /**
     * Importa a estrutura base do sistema a partir de um arquivo Excel.
     * Requer permissão de Administrador (case-insensitive).
     */
    @PostMapping("/estrutura-base")
    @RequiresAdmin(message = "Apenas administradores podem importar a estrutura base")
    public ResponseEntity<ImportacaoEstruturaBaseResultadoDTO> importarEstruturaBase(
            @RequestParam("arquivo") MultipartFile arquivo) {

        try {
            // Validações básicas
            if (arquivo.isEmpty()) {
                ImportacaoEstruturaBaseResultadoDTO erro = new ImportacaoEstruturaBaseResultadoDTO();
                erro.setSucesso(false);
                erro.setMensagemGeral("Arquivo não pode estar vazio");
                return ResponseEntity.badRequest().body(erro);
            }

            String nomeArquivo = arquivo.getOriginalFilename();
            if (nomeArquivo == null || !nomeArquivo.endsWith(".xlsx")) {
                ImportacaoEstruturaBaseResultadoDTO erro = new ImportacaoEstruturaBaseResultadoDTO();
                erro.setSucesso(false);
                erro.setMensagemGeral("Apenas arquivos .xlsx são permitidos");
                return ResponseEntity.badRequest().body(erro);
            }

            // Processa importação
            ImportacaoEstruturaBaseResultadoDTO resultado = importacaoEstruturaBaseService
                    .importarEstruturaBase(arquivo);

            if (resultado.isSucesso()) {
                return ResponseEntity.ok(resultado);
            } else {
                return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(resultado);
            }

        } catch (Exception e) {
            ImportacaoEstruturaBaseResultadoDTO erro = new ImportacaoEstruturaBaseResultadoDTO();
            erro.setSucesso(false);
            erro.setMensagemGeral("Erro ao processar arquivo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
        }
    }

    @GetMapping("/estrutura-base/template")
    public ResponseEntity<String> downloadTemplate() {
        return ResponseEntity.ok(
                "Para baixar o template, acesse: /api/importacao/estrutura-base/template/download");
    }

    /**
     * Importa testes vocacionais a partir de um arquivo Excel.
     * Requer permissão de Administrador (case-insensitive).
     */
    @PostMapping("/testes-vocacionais")
    @RequiresAdmin(message = "Apenas administradores podem importar testes vocacionais")
    public ResponseEntity<ImportacaoTestesVocacionaisResultadoDTO> importarTestesVocacionais(
            @RequestParam("arquivo") MultipartFile arquivo) {

        try {
            if (arquivo.isEmpty()) {
                ImportacaoTestesVocacionaisResultadoDTO erro = new ImportacaoTestesVocacionaisResultadoDTO();
                erro.setTestes(criarResultadoErro("02-testes-vocacionais", "Arquivo não pode estar vazio"));
                return ResponseEntity.badRequest().body(erro);
            }

            String nomeArquivo = arquivo.getOriginalFilename();
            if (nomeArquivo == null || !nomeArquivo.endsWith(".xlsx")) {
                ImportacaoTestesVocacionaisResultadoDTO erro = new ImportacaoTestesVocacionaisResultadoDTO();
                erro.setTestes(criarResultadoErro("02-testes-vocacionais", "Apenas arquivos .xlsx são permitidos"));
                return ResponseEntity.badRequest().body(erro);
            }

            ImportacaoTestesVocacionaisResultadoDTO resultado = importacaoTestesVocacionaisService
                    .importarTestesVocacionais(arquivo);

            if (resultado.getTotalErros() == 0) {
                return ResponseEntity.ok(resultado);
            } else {
                return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(resultado);
            }

        } catch (Exception e) {
            ImportacaoTestesVocacionaisResultadoDTO erro = new ImportacaoTestesVocacionaisResultadoDTO();
            erro.setTestes(criarResultadoErro("02-testes-vocacionais", "Erro ao processar arquivo: " + e.getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
        }
    }

    /**
     * Importa questionários e questões a partir de um arquivo Excel.
     * Requer permissão de Administrador (case-insensitive).
     */
    @PostMapping("/questionarios-questoes")
    @RequiresAdmin(message = "Apenas administradores podem importar questionários e questões")
    public ResponseEntity<ImportacaoQuestionariosQuestoesResultadoDTO> importarQuestionariosQuestoes(
            @RequestParam("arquivo") MultipartFile arquivo) {

        try {
            if (arquivo.isEmpty()) {
                ImportacaoQuestionariosQuestoesResultadoDTO erro = new ImportacaoQuestionariosQuestoesResultadoDTO();
                erro.setQuestionarios(criarResultadoErro("03-questionarios-questoes", "Arquivo não pode estar vazio"));
                return ResponseEntity.badRequest().body(erro);
            }

            String nomeArquivo = arquivo.getOriginalFilename();
            if (nomeArquivo == null || !nomeArquivo.endsWith(".xlsx")) {
                ImportacaoQuestionariosQuestoesResultadoDTO erro = new ImportacaoQuestionariosQuestoesResultadoDTO();
                erro.setQuestionarios(criarResultadoErro("03-questionarios-questoes", "Apenas arquivos .xlsx são permitidos"));
                return ResponseEntity.badRequest().body(erro);
            }

            ImportacaoQuestionariosQuestoesResultadoDTO resultado = importacaoQuestionariosQuestoesService
                    .importarQuestionariosQuestoes(arquivo);

            if (resultado.getTotalErros() == 0) {
                return ResponseEntity.ok(resultado);
            } else {
                return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(resultado);
            }

        } catch (Exception e) {
            ImportacaoQuestionariosQuestoesResultadoDTO erro = new ImportacaoQuestionariosQuestoesResultadoDTO();
            erro.setQuestionarios(criarResultadoErro("03-questionarios-questoes", "Erro ao processar arquivo: " + e.getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
        }
    }

    /**
     * Importa desafios a partir de um arquivo Excel.
     * Requer permissão de Administrador (case-insensitive).
     */
    @PostMapping("/desafios")
    @RequiresAdmin(message = "Apenas administradores podem importar desafios")
    public ResponseEntity<ImportacaoDesafiosResultadoDTO> importarDesafios(
            @RequestParam("arquivo") MultipartFile arquivo) {

        try {
            if (arquivo.isEmpty()) {
                ImportacaoDesafiosResultadoDTO erro = new ImportacaoDesafiosResultadoDTO();
                erro.setDesafios(criarResultadoErro("04-desafios", "Arquivo não pode estar vazio"));
                return ResponseEntity.badRequest().body(erro);
            }

            String nomeArquivo = arquivo.getOriginalFilename();
            if (nomeArquivo == null || !nomeArquivo.endsWith(".xlsx")) {
                ImportacaoDesafiosResultadoDTO erro = new ImportacaoDesafiosResultadoDTO();
                erro.setDesafios(criarResultadoErro("04-desafios", "Apenas arquivos .xlsx são permitidos"));
                return ResponseEntity.badRequest().body(erro);
            }

            ImportacaoDesafiosResultadoDTO resultado = importacaoDesafiosService.importarDesafios(arquivo);

            if (resultado.getTotalErros() == 0) {
                return ResponseEntity.ok(resultado);
            } else {
                return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(resultado);
            }

        } catch (Exception e) {
            ImportacaoDesafiosResultadoDTO erro = new ImportacaoDesafiosResultadoDTO();
            erro.setDesafios(criarResultadoErro("04-desafios", "Erro ao processar arquivo: " + e.getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
        }
    }

    /**
     * Helper para criar mensagens de erro padronizadas
     */
    private ImportacaoResultadoDTO criarResultadoErro(String planilha, String mensagem) {
        ImportacaoResultadoDTO erro = new ImportacaoResultadoDTO();
        erro.setPlanilha(planilha);
        erro.setSucesso(false);
        erro.setMensagem(mensagem);
        return erro;
    }
}
