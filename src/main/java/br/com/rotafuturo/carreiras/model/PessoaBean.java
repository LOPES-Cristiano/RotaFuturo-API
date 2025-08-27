
package br.com.rotafuturo.carreiras.model;
import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pessoa")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PessoaBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pesId;

    private String pesNome;
    private String pesApelido;
    private LocalDate pesDatacadastro;
    private LocalTime pesHoracadastro;
    private LocalDate pesDatanascimento;
    private String pesTelefone1;
    private String pesTelefone2;
    private String pesImagemperfil;
    private String pesImagemcurriculo;
    private Integer pesNivel;
    private Integer pesXp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usu_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private UsuarioBean usuario;

    public Integer getPesId() { return pesId; }
    public void setPesId(Integer pesId) { this.pesId = pesId; }

    public String getPesNome() { return pesNome; }
    public void setPesNome(String pesNome) { this.pesNome = pesNome; }

    public String getPesApelido() { return pesApelido; }
    public void setPesApelido(String pesApelido) { this.pesApelido = pesApelido; }

    public LocalDate getPesDatacadastro() { return pesDatacadastro; }
    public void setPesDatacadastro(LocalDate pesDatacadastro) { this.pesDatacadastro = pesDatacadastro; }

    public LocalTime getPesHoracadastro() { return pesHoracadastro; }
    public void setPesHoracadastro(LocalTime pesHoracadastro) { this.pesHoracadastro = pesHoracadastro; }

    public LocalDate getPesDatanascimento() { return pesDatanascimento; }
    public void setPesDatanascimento(LocalDate pesDatanascimento) { this.pesDatanascimento = pesDatanascimento; }

    public String getPesTelefone1() { return pesTelefone1; }
    public void setPesTelefone1(String pesTelefone1) { this.pesTelefone1 = pesTelefone1; }

    public String getPesTelefone2() { return pesTelefone2; }
    public void setPesTelefone2(String pesTelefone2) { this.pesTelefone2 = pesTelefone2; }

    public String getPesImagemperfil() { return pesImagemperfil; }
    public void setPesImagemperfil(String pesImagemperfil) { this.pesImagemperfil = pesImagemperfil; }

    public String getPesImagemcurriculo() { return pesImagemcurriculo; }
    public void setPesImagemcurriculo(String pesImagemcurriculo) { this.pesImagemcurriculo = pesImagemcurriculo; }

    public Integer getPesNivel() { return pesNivel; }
    public void setPesNivel(Integer pesNivel) { this.pesNivel = pesNivel; }

    public Integer getPesXp() { return pesXp; }
    public void setPesXp(Integer pesXp) { this.pesXp = pesXp; }

    public UsuarioBean getUsuario() { return usuario; }
    public void setUsuario(UsuarioBean usuario) { this.usuario = usuario; }
}
