package br.com.rotafuturo.carreiras.dto.importacao;

import java.util.ArrayList;
import java.util.List;

public class ImportacaoResultadoDTO {
    
    private String planilha;
    private String aba;
    private Integer totalLinhas;
    private Integer inseridos;
    private Integer atualizados;
    private Integer erros;
    private List<ErroImportacaoDTO> detalhesErros;
    private String mensagem;
    private boolean sucesso;
    
    public ImportacaoResultadoDTO() {
        this.detalhesErros = new ArrayList<>();
        this.inseridos = 0;
        this.atualizados = 0;
        this.erros = 0;
        this.totalLinhas = 0;
        this.sucesso = true;
    }
    
    public ImportacaoResultadoDTO(String planilha, String aba) {
        this();
        this.planilha = planilha;
        this.aba = aba;
    }

    // Getters e Setters
    public String getPlanilha() {
        return planilha;
    }

    public void setPlanilha(String planilha) {
        this.planilha = planilha;
    }

    public String getAba() {
        return aba;
    }

    public void setAba(String aba) {
        this.aba = aba;
    }

    public Integer getTotalLinhas() {
        return totalLinhas;
    }

    public void setTotalLinhas(Integer totalLinhas) {
        this.totalLinhas = totalLinhas;
    }

    public Integer getInseridos() {
        return inseridos;
    }

    public void setInseridos(Integer inseridos) {
        this.inseridos = inseridos;
    }

    public Integer getAtualizados() {
        return atualizados;
    }

    public void setAtualizados(Integer atualizados) {
        this.atualizados = atualizados;
    }

    public Integer getErros() {
        return erros;
    }

    public void setErros(Integer erros) {
        this.erros = erros;
    }

    public List<ErroImportacaoDTO> getDetalhesErros() {
        return detalhesErros;
    }

    public void setDetalhesErros(List<ErroImportacaoDTO> detalhesErros) {
        this.detalhesErros = detalhesErros;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public boolean isSucesso() {
        return sucesso;
    }

    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }

    public void adicionarErro(int linha, String erro) {
        ErroImportacaoDTO erroDTO = new ErroImportacaoDTO();
        erroDTO.setLinha(linha);
        erroDTO.setErro(erro);
        this.detalhesErros.add(erroDTO);
        if (this.erros == null) this.erros = 0;
        this.erros++;
    }
    
    public void incrementarInseridos() {
        if (this.inseridos == null) this.inseridos = 0;
        this.inseridos++;
    }
    
    public void incrementarAtualizados() {
        if (this.atualizados == null) this.atualizados = 0;
        this.atualizados++;
    }
}
