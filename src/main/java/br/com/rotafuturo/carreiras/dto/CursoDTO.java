package br.com.rotafuturo.carreiras.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class CursoDTO {
    private Integer curId;
    private String curDescricao;
    private Boolean curAtivo;
    private LocalDate curDatacadastro;
    private LocalTime curHoracadastro;
    // getters e setters
    public Integer getCurId() { return curId; }
    public void setCurId(Integer curId) { this.curId = curId; }
    public String getCurDescricao() { return curDescricao; }
    public void setCurDescricao(String curDescricao) { this.curDescricao = curDescricao; }
    public Boolean getCurAtivo() { return curAtivo; }
    public void setCurAtivo(Boolean curAtivo) { this.curAtivo = curAtivo; }
    public LocalDate getCurDatacadastro() { return curDatacadastro; }
    public void setCurDatacadastro(LocalDate curDatacadastro) { this.curDatacadastro = curDatacadastro; }
    public LocalTime getCurHoracadastro() { return curHoracadastro; }
    public void setCurHoracadastro(LocalTime curHoracadastro) { this.curHoracadastro = curHoracadastro; }
}
