package br.com.rotafuturo.carreiras.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class PessoaDTO {
    private Integer pesId;
    private String pesNome;
    private String pesCpf;
    private Boolean pesAtivo;
    private LocalDate pesDatacadastro;
    private LocalTime pesHoracadastro;
    private Integer usuId;
    // getters e setters
    public Integer getPesId() { return pesId; }
    public void setPesId(Integer pesId) { this.pesId = pesId; }
    public String getPesNome() { return pesNome; }
    public void setPesNome(String pesNome) { this.pesNome = pesNome; }
    public String getPesCpf() { return pesCpf; }
    public void setPesCpf(String pesCpf) { this.pesCpf = pesCpf; }
    public Boolean getPesAtivo() { return pesAtivo; }
    public void setPesAtivo(Boolean pesAtivo) { this.pesAtivo = pesAtivo; }
    public LocalDate getPesDatacadastro() { return pesDatacadastro; }
    public void setPesDatacadastro(LocalDate pesDatacadastro) { this.pesDatacadastro = pesDatacadastro; }
    public LocalTime getPesHoracadastro() { return pesHoracadastro; }
    public void setPesHoracadastro(LocalTime pesHoracadastro) { this.pesHoracadastro = pesHoracadastro; }
    public Integer getUsuId() { return usuId; }
    public void setUsuId(Integer usuId) { this.usuId = usuId; }
}
