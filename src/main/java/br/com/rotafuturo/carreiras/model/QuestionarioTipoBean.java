package br.com.rotafuturo.carreiras.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "questionariotipo")
public class QuestionarioTipoBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "QUEST_ID")
	private Integer questId;

	@Column(name = "QUEST_DESCRICAO")
	private String questDescricao;

	@jakarta.persistence.Column(name = "QUEST_ATIVO")
	private Boolean questAtivo = true;

	public Boolean getQuestAtivo() {
		return questAtivo;
	}

	public void setQuestAtivo(Boolean questAtivo) {
		this.questAtivo = questAtivo;
	}

	public Integer getQuestId() {
		return questId;
	}

	public void setQuestId(Integer questId) {
		this.questId = questId;
	}

	public String getQuestDescricao() {
		return questDescricao;
	}

	public void setQuestDescricao(String questDescricao) {
		this.questDescricao = questDescricao;
	}
}
