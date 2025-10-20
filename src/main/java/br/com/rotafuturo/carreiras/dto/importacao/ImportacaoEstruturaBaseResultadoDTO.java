package br.com.rotafuturo.carreiras.dto.importacao;

import java.util.ArrayList;
import java.util.List;

public class ImportacaoEstruturaBaseResultadoDTO {
    
    private ImportacaoResultadoDTO areas;
    private ImportacaoResultadoDTO areasSub;
    private ImportacaoResultadoDTO niveis;
    private ImportacaoResultadoDTO questionarioTipos;
    private ImportacaoResultadoDTO questaoTipos;
    
    private boolean sucesso;
    private String mensagemGeral;
    private List<String> avisos;
    
    public ImportacaoEstruturaBaseResultadoDTO() {
        this.avisos = new ArrayList<>();
    }

    public ImportacaoResultadoDTO getAreas() {
        return areas;
    }

    public void setAreas(ImportacaoResultadoDTO areas) {
        this.areas = areas;
    }

    public ImportacaoResultadoDTO getAreasSub() {
        return areasSub;
    }

    public void setAreasSub(ImportacaoResultadoDTO areasSub) {
        this.areasSub = areasSub;
    }

    public ImportacaoResultadoDTO getNiveis() {
        return niveis;
    }

    public void setNiveis(ImportacaoResultadoDTO niveis) {
        this.niveis = niveis;
    }

    public ImportacaoResultadoDTO getQuestionarioTipos() {
        return questionarioTipos;
    }

    public void setQuestionarioTipos(ImportacaoResultadoDTO questionarioTipos) {
        this.questionarioTipos = questionarioTipos;
    }

    public ImportacaoResultadoDTO getQuestaoTipos() {
        return questaoTipos;
    }

    public void setQuestaoTipos(ImportacaoResultadoDTO questaoTipos) {
        this.questaoTipos = questaoTipos;
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
    
    public int getTotalInseridos() {
        int total = 0;
        if (areas != null) total += areas.getInseridos();
        if (areasSub != null) total += areasSub.getInseridos();
        if (niveis != null) total += niveis.getInseridos();
        if (questionarioTipos != null) total += questionarioTipos.getInseridos();
        if (questaoTipos != null) total += questaoTipos.getInseridos();
        return total;
    }
    
    public int getTotalErros() {
        int total = 0;
        if (areas != null) total += areas.getErros();
        if (areasSub != null) total += areasSub.getErros();
        if (niveis != null) total += niveis.getErros();
        if (questionarioTipos != null) total += questionarioTipos.getErros();
        if (questaoTipos != null) total += questaoTipos.getErros();
        return total;
    }
}
