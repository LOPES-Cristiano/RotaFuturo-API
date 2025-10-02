package br.com.rotafuturo.carreiras.dto;
public class QuestaoTipoDTO {
	private Integer quetId;
	private String quetDescricao;
	private Boolean quetAtivo;
	public Integer getQuetId() {
		return quetId;
	}
	public void setQuetId(Integer quetId) {
		this.quetId = quetId;
	}
	public String getQuetDescricao() {
		return quetDescricao;
	}
	public void setQuetDescricao(String quetDescricao) {
		this.quetDescricao = quetDescricao;
	}
	public Boolean getQuetAtivo() {
		return quetAtivo;
	}
	public void setQuetAtivo(Boolean quetAtivo) {
		this.quetAtivo = quetAtivo;
	}
}
