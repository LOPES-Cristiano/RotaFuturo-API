package br.com.rotafuturo.carreiras.dto;

/**
 * DTO para representar o progresso do usuário na carreira.
 */
public class ProgressoCarreiraDTO {

    private Integer areaId;
    private String areaDescricao;
    private Integer areaSubId;
    private String areaSubDescricao;
    private Long totalDesafios;
    private Long desafiosCompletados;
    private Double percentualConcluido;

    // Getters e Setters

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

    public Integer getAreaSubId() {
        return areaSubId;
    }

    public void setAreaSubId(Integer areaSubId) {
        this.areaSubId = areaSubId;
    }

    public String getAreaSubDescricao() {
        return areaSubDescricao;
    }

    public void setAreaSubDescricao(String areaSubDescricao) {
        this.areaSubDescricao = areaSubDescricao;
    }

    public Long getTotalDesafios() {
        return totalDesafios;
    }

    public void setTotalDesafios(Long totalDesafios) {
        this.totalDesafios = totalDesafios;
    }

    public Long getDesafiosCompletados() {
        return desafiosCompletados;
    }

    public void setDesafiosCompletados(Long desafiosCompletados) {
        this.desafiosCompletados = desafiosCompletados;
    }

    public Double getPercentualConcluido() {
        return percentualConcluido;
    }

    public void setPercentualConcluido(Double percentualConcluido) {
        this.percentualConcluido = percentualConcluido;
    }
}
