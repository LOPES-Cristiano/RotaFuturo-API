package br.com.rotafuturo.carreiras.model;
import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "TESTEQUESTAORESPONDIDA")
public class TesteQuestaoRespondidaBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TESQR_ID")
    private Integer tesqrId;
    @Column(name = "TESQR_DATACADASTRO", nullable = false)
    private LocalDate tesqrDatacadastro;
    @Column(name = "TESQR_HORACADASTRO", nullable = false)
    private LocalTime tesqrHoracadastro;
    @ManyToOne
    @JoinColumn(name = "TESQV_ID")
    private TesteQuestaoVinculoBean testeQuestaoVinculo;
    @ManyToOne
    @JoinColumn(name = "USU_ID")
    private UsuarioBean usuario;
    @Column(name = "TESQR_RESPOSTA")
    private Integer tesqrResposta;
    private transient Integer testeQuestaoVinculoId;
    public Integer getTesqrId() {
        return tesqrId;
    }
    public void setTesqrId(Integer tesqrId) {
        this.tesqrId = tesqrId;
    }
    public LocalDate getTesqrDatacadastro() {
        return tesqrDatacadastro;
    }
    public void setTesqrDatacadastro(LocalDate tesqrDatacadastro) {
        this.tesqrDatacadastro = tesqrDatacadastro;
    }
    public LocalTime getTesqrHoracadastro() {
        return tesqrHoracadastro;
    }
    public void setTesqrHoracadastro(LocalTime tesqrHoracadastro) {
        this.tesqrHoracadastro = tesqrHoracadastro;
    }
    public TesteQuestaoVinculoBean getTesteQuestaoVinculo() {
        return testeQuestaoVinculo;
    }
    public void setTesteQuestaoVinculo(TesteQuestaoVinculoBean testeQuestaoVinculo) {
        this.testeQuestaoVinculo = testeQuestaoVinculo;
    }
    public UsuarioBean getUsuario() {
        return usuario;
    }
    public void setUsuario(UsuarioBean usuario) {
        this.usuario = usuario;
    }
    public Integer getTesqrResposta() {
        return tesqrResposta;
    }
    public void setTesqrResposta(Integer tesqrResposta) {
        this.tesqrResposta = tesqrResposta;
    }
    public Integer getTesteQuestaoVinculoId() {
        return testeQuestaoVinculoId;
    }
    public void setTesteQuestaoVinculoId(Integer testeQuestaoVinculoId) {
        this.testeQuestaoVinculoId = testeQuestaoVinculoId;
    }
}
