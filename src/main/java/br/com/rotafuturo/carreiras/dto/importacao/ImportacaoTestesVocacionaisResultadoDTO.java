package br.com.rotafuturo.carreiras.dto.importacao;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO que representa o resultado da importação da Planilha 02 - Testes Vocacionais
 * Contém 3 abas: TESTE, TESTEQUESTAO, TESTEQUESTAOVINCULO
 */
public class ImportacaoTestesVocacionaisResultadoDTO {
    
    private ImportacaoResultadoDTO testes;
    private ImportacaoResultadoDTO testesQuestao;
    private ImportacaoResultadoDTO testesQuestaoVinculo;
    
    private boolean sucesso;
    private String mensagemGeral;
    private List<String> avisos = new ArrayList<>();
    
    public ImportacaoTestesVocacionaisResultadoDTO() {
    }

    // Getters e Setters
    
    public ImportacaoResultadoDTO getTestes() {
        return testes;
    }

    public void setTestes(ImportacaoResultadoDTO testes) {
        this.testes = testes;
    }

    public ImportacaoResultadoDTO getTestesQuestao() {
        return testesQuestao;
    }

    public void setTestesQuestao(ImportacaoResultadoDTO testesQuestao) {
        this.testesQuestao = testesQuestao;
    }

    public ImportacaoResultadoDTO getTestesQuestaoVinculo() {
        return testesQuestaoVinculo;
    }

    public void setTestesQuestaoVinculo(ImportacaoResultadoDTO testesQuestaoVinculo) {
        this.testesQuestaoVinculo = testesQuestaoVinculo;
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
        if (testes != null) total += testes.getInseridos();
        if (testesQuestao != null) total += testesQuestao.getInseridos();
        if (testesQuestaoVinculo != null) total += testesQuestaoVinculo.getInseridos();
        return total;
    }
    
    /**
     * Retorna o total de erros em todas as abas
     */
    public int getTotalErros() {
        int total = 0;
        if (testes != null) total += testes.getErros();
        if (testesQuestao != null) total += testesQuestao.getErros();
        if (testesQuestaoVinculo != null) total += testesQuestaoVinculo.getErros();
        return total;
    }
}
