package br.com.rotafuturo.carreiras.dto;
public class QuestaoNivelDTO {
	private Integer quesnId;
	private String quesnDescricao;
	private Boolean quesnAtivo;
	public Integer getQuesnId() {
		return quesnId;
	}
	public void setQuesnId(Integer quesnId) {
		this.quesnId = quesnId;
	}
	public String getQuesnDescricao() {
		return quesnDescricao;
	}
	public void setQuesnDescricao(String quesnDescricao) {
		this.quesnDescricao = quesnDescricao;
	}
	public Boolean getQuesnAtivo() {
		return quesnAtivo;
	}
	public void setQuesnAtivo(Boolean quesnAtivo) {
		this.quesnAtivo = quesnAtivo;
	}
}
