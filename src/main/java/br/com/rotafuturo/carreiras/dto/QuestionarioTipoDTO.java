package br.com.rotafuturo.carreiras.dto;
public class QuestionarioTipoDTO {
	private Integer questId;
	private String questDescricao;
	private Boolean questAtivo;
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
	public Boolean getQuestAtivo() {
		return questAtivo;
	}
	public void setQuestAtivo(Boolean questAtivo) {
		this.questAtivo = questAtivo;
	}
}
