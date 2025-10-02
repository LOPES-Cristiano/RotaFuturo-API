package br.com.rotafuturo.carreiras.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "TESTEQUESTAOVINCULO")
public class TesteQuestaoVinculoBean {
    @Override
    public String toString() {
        return "TesteQuestaoVinculoBean{" +
                "tesqvId=" + tesqvId +
                ", teste=" + (teste != null ? teste.getTesId() : null) +
                ", testeQuestao=" + (testeQuestao != null ? testeQuestao.getTesqId() : null) +
                '}';
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TESQV_ID")
    private Integer tesqvId;
    @ManyToOne
    @JoinColumn(name = "TES_ID")
    private TesteBean teste;
    @ManyToOne
    @JoinColumn(name = "TESQ_ID")
    private TesteQuestaoBean testeQuestao;
    @Column(name = "TES_ID", insertable = false, updatable = false)
    private Integer testeId;
    public Integer getTesqvId() { return tesqvId; }
    public void setTesqvId(Integer tesqvId) { this.tesqvId = tesqvId; }
    public TesteBean getTeste() { return teste; }
    public void setTeste(TesteBean teste) { this.teste = teste; }
    public TesteQuestaoBean getTesteQuestao() { return testeQuestao; }
    public void setTesteQuestao(TesteQuestaoBean testeQuestao) { this.testeQuestao = testeQuestao; }
    public Integer getTesteId() {
        return testeId;
    }
    public void setTesteId(Integer testeId) {
        this.testeId = testeId;
    }
}
