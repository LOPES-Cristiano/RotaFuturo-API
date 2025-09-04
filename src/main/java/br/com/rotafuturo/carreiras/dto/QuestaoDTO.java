package br.com.rotafuturo.carreiras.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class QuestaoDTO {
    private Integer questaoExperiencia;
    private Integer questaoNivel;
    private br.com.rotafuturo.carreiras.dto.AreaDTO area;
    private br.com.rotafuturo.carreiras.dto.AreaSubDTO areaSub;
    private Integer questaoId;
    private String questaoCodigo;
    private String questaoDescricao;
    private Boolean questaoAtivo;
    private LocalDate questaoDatacadastro;
    private LocalTime questaoHoracadastro;
    // getters e setters
    public Integer getQuestaoExperiencia() { return questaoExperiencia; }
    public void setQuestaoExperiencia(Integer questaoExperiencia) { this.questaoExperiencia = questaoExperiencia; }
    public Integer getQuestaoNivel() { return questaoNivel; }
    public void setQuestaoNivel(Integer questaoNivel) { this.questaoNivel = questaoNivel; }
    public br.com.rotafuturo.carreiras.dto.AreaDTO getArea() { return area; }
    public void setArea(br.com.rotafuturo.carreiras.dto.AreaDTO area) { this.area = area; }
    public br.com.rotafuturo.carreiras.dto.AreaSubDTO getAreaSub() { return areaSub; }
    public void setAreaSub(br.com.rotafuturo.carreiras.dto.AreaSubDTO areaSub) { this.areaSub = areaSub; }
    public Integer getQuestaoId() { return questaoId; }
    public void setQuestaoId(Integer questaoId) { this.questaoId = questaoId; }
    public String getQuestaoCodigo() { return questaoCodigo; }
    public void setQuestaoCodigo(String questaoCodigo) { this.questaoCodigo = questaoCodigo; }
    public String getQuestaoDescricao() { return questaoDescricao; }
    public void setQuestaoDescricao(String questaoDescricao) { this.questaoDescricao = questaoDescricao; }
    public Boolean getQuestaoAtivo() { return questaoAtivo; }
    public void setQuestaoAtivo(Boolean questaoAtivo) { this.questaoAtivo = questaoAtivo; }
    public LocalDate getQuestaoDatacadastro() { return questaoDatacadastro; }
    public void setQuestaoDatacadastro(LocalDate questaoDatacadastro) { this.questaoDatacadastro = questaoDatacadastro; }
    public LocalTime getQuestaoHoracadastro() { return questaoHoracadastro; }
    public void setQuestaoHoracadastro(LocalTime questaoHoracadastro) { this.questaoHoracadastro = questaoHoracadastro; }
}
