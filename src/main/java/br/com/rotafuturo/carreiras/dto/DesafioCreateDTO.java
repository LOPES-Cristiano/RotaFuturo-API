package br.com.rotafuturo.carreiras.dto;

public class DesafioCreateDTO {
    private String desTitulo;
    private String desDescricao;
    private Integer nivelId;
    private Integer areaId;
    private Integer areaSubId;

    public String getDesTitulo() {
        return desTitulo;
    }

    public void setDesTitulo(String desTitulo) {
        this.desTitulo = desTitulo;
    }

    public String getDesDescricao() {
        return desDescricao;
    }

    public void setDesDescricao(String desDescricao) {
        this.desDescricao = desDescricao;
    }

    public Integer getNivelId() {
        return nivelId;
    }

    public void setNivelId(Integer nivelId) {
        this.nivelId = nivelId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getAreaSubId() {
        return areaSubId;
    }

    public void setAreaSubId(Integer areaSubId) {
        this.areaSubId = areaSubId;
    }
}
