package br.com.rotafuturo.carreiras.dto.importacao;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO que representa o resultado da importação da Planilha 03 - Questionários e Questões
 * Contém 4 abas: QUESTIONARIO, QUESTAO, QUESTAOALTERNATIVA, QUESTIONARIOQUESTAO
 */
public class ImportacaoQuestionariosQuestoesResultadoDTO {
    
    private ImportacaoResultadoDTO questionarios;
    private ImportacaoResultadoDTO questoes;
    private ImportacaoResultadoDTO questoesAlternativa;
    private ImportacaoResultadoDTO questionariosQuestao;
    
    private boolean sucesso;
    private String mensagemGeral;
    private List<String> avisos = new ArrayList<>();
    
    public ImportacaoQuestionariosQuestoesResultadoDTO() {
    }

    // Getters e Setters
    
    public ImportacaoResultadoDTO getQuestionarios() {
        return questionarios;
    }

    public void setQuestionarios(ImportacaoResultadoDTO questionarios) {
        this.questionarios = questionarios;
    }

    public ImportacaoResultadoDTO getQuestoes() {
        return questoes;
    }

    public void setQuestoes(ImportacaoResultadoDTO questoes) {
        this.questoes = questoes;
    }

    public ImportacaoResultadoDTO getQuestoesAlternativa() {
        return questoesAlternativa;
    }

    public void setQuestoesAlternativa(ImportacaoResultadoDTO questoesAlternativa) {
        this.questoesAlternativa = questoesAlternativa;
    }

    public ImportacaoResultadoDTO getQuestionariosQuestao() {
        return questionariosQuestao;
    }

    public void setQuestionariosQuestao(ImportacaoResultadoDTO questionariosQuestao) {
        this.questionariosQuestao = questionariosQuestao;
    }

    public boolean isSucesso() {
        return sucesso;
    }

    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }

    public String getMensagemGeral() {
        return mensagemGeral;
    }

    public void setMensagemGeral(String mensagemGeral) {
        this.mensagemGeral = mensagemGeral;
    }

    public List<String> getAvisos() {
        return avisos;
    }

    public void setAvisos(List<String> avisos) {
        this.avisos = avisos;
    }
    
    public void adicionarAviso(String aviso) {
        this.avisos.add(aviso);
    }
    
    /**
     * Retorna o total de registros inseridos em todas as abas
     */
    public int getTotalInseridos() {
        int total = 0;
        if (questionarios != null) total += questionarios.getInseridos();
        if (questoes != null) total += questoes.getInseridos();
        if (questoesAlternativa != null) total += questoesAlternativa.getInseridos();
        if (questionariosQuestao != null) total += questionariosQuestao.getInseridos();
        return total;
    }
    
    /**
     * Retorna o total de erros em todas as abas
     */
    public int getTotalErros() {
        int total = 0;
        if (questionarios != null) total += questionarios.getErros();
        if (questoes != null) total += questoes.getErros();
        if (questoesAlternativa != null) total += questoesAlternativa.getErros();
        if (questionariosQuestao != null) total += questionariosQuestao.getErros();
        return total;
    }
}
