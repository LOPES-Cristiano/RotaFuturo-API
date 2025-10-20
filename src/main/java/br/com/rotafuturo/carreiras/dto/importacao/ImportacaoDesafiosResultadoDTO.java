package br.com.rotafuturo.carreiras.dto.importacao;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO que representa o resultado da importação da Planilha 04 - Desafios
 * Contém 2 abas: DESAFIO, DESAFIOQUESTIONARIO
 */
public class ImportacaoDesafiosResultadoDTO {
    
    private ImportacaoResultadoDTO desafios;
    private ImportacaoResultadoDTO desafiosQuestionario;
    
    private boolean sucesso;
    private String mensagemGeral;
    private List<String> avisos = new ArrayList<>();
    
    public ImportacaoDesafiosResultadoDTO() {
    }

    // Getters e Setters
    
    public ImportacaoResultadoDTO getDesafios() {
        return desafios;
    }

    public void setDesafios(ImportacaoResultadoDTO desafios) {
        this.desafios = desafios;
    }

    public ImportacaoResultadoDTO getDesafiosQuestionario() {
        return desafiosQuestionario;
    }

    public void setDesafiosQuestionario(ImportacaoResultadoDTO desafiosQuestionario) {
        this.desafiosQuestionario = desafiosQuestionario;
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
        if (desafios != null) total += desafios.getInseridos();
        if (desafiosQuestionario != null) total += desafiosQuestionario.getInseridos();
        return total;
    }
    
    /**
     * Retorna o total de erros em todas as abas
     */
    public int getTotalErros() {
        int total = 0;
        if (desafios != null) total += desafios.getErros();
        if (desafiosQuestionario != null) total += desafiosQuestionario.getErros();
        return total;
    }
}
