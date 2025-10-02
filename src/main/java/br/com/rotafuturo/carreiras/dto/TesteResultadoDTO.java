package br.com.rotafuturo.carreiras.dto;
public class TesteResultadoDTO {
    private Integer areaId;
    private String areaDescricao;
    private Integer pontuacao;
    public TesteResultadoDTO() {
    }
    public TesteResultadoDTO(Integer areaId, String areaDescricao, Integer pontuacao) {
        this.areaId = areaId;
        this.areaDescricao = areaDescricao;
        this.pontuacao = pontuacao;
    }
    public Integer getAreaId() {
        return areaId;
    }
    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }
    public String getAreaDescricao() {
        return areaDescricao;
    }
    public void setAreaDescricao(String areaDescricao) {
        this.areaDescricao = areaDescricao;
    }
    public Integer getPontuacao() {
        return pontuacao;
    }
    public void setPontuacao(Integer pontuacao) {
        this.pontuacao = pontuacao;
    }
}