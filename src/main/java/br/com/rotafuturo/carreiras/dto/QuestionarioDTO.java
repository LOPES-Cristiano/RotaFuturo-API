package br.com.rotafuturo.carreiras.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class QuestionarioDTO {
    private Integer quesId;
    private String quesDescricao;
    private Boolean quesAtivo;
    private LocalDate quesDatacadastro;
    private LocalTime quesHoracadastro;
    // getters e setters
    public Integer getQuesId() { return quesId; }
    public void setQuesId(Integer quesId) { this.quesId = quesId; }
    public String getQuesDescricao() { return quesDescricao; }
    public void setQuesDescricao(String quesDescricao) { this.quesDescricao = quesDescricao; }
    public Boolean getQuesAtivo() { return quesAtivo; }
    public void setQuesAtivo(Boolean quesAtivo) { this.quesAtivo = quesAtivo; }
    public LocalDate getQuesDatacadastro() { return quesDatacadastro; }
    public void setQuesDatacadastro(LocalDate quesDatacadastro) { this.quesDatacadastro = quesDatacadastro; }
    public LocalTime getQuesHoracadastro() { return quesHoracadastro; }
    public void setQuesHoracadastro(LocalTime quesHoracadastro) { this.quesHoracadastro = quesHoracadastro; }
}
