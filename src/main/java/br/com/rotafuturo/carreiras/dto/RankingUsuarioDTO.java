package br.com.rotafuturo.carreiras.dto;

import java.math.BigDecimal;

/**
 * DTO para representar um usuário no ranking global.
 */
public class RankingUsuarioDTO {

    private Integer usuarioId;
    private String usuarioNome;
    private Long totalDesafiosCompletados; // Apenas desafios com 100% de acerto
    private Long totalTentativas; // Todas as tentativas (incluindo não perfeitas)
    private BigDecimal mediaPontuacao;
    private BigDecimal melhorPontuacao;
    private BigDecimal percentualEficiencia; // (desafiosPerfeitos / totalTentativas) * 100
    private Integer posicao;

    // Getters e Setters

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public void setUsuarioNome(String usuarioNome) {
        this.usuarioNome = usuarioNome;
    }

    public Long getTotalDesafiosCompletados() {
        return totalDesafiosCompletados;
    }

    public void setTotalDesafiosCompletados(Long totalDesafiosCompletados) {
        this.totalDesafiosCompletados = totalDesafiosCompletados;
    }

    public BigDecimal getMediaPontuacao() {
        return mediaPontuacao;
    }

    public void setMediaPontuacao(BigDecimal mediaPontuacao) {
        this.mediaPontuacao = mediaPontuacao;
    }

    public BigDecimal getMelhorPontuacao() {
        return melhorPontuacao;
    }

    public void setMelhorPontuacao(BigDecimal melhorPontuacao) {
        this.melhorPontuacao = melhorPontuacao;
    }

    public Integer getPosicao() {
        return posicao;
    }

    public void setPosicao(Integer posicao) {
        this.posicao = posicao;
    }

    public Long getTotalTentativas() {
        return totalTentativas;
    }

    public void setTotalTentativas(Long totalTentativas) {
        this.totalTentativas = totalTentativas;
    }

    public BigDecimal getPercentualEficiencia() {
        return percentualEficiencia;
    }

    public void setPercentualEficiencia(BigDecimal percentualEficiencia) {
        this.percentualEficiencia = percentualEficiencia;
    }
}
