package br.com.rotafuturo.carreiras.dto;

import java.math.BigDecimal;

/**
 * DTO para retornar estatísticas de desafios do usuário.
 */
public class EstatisticasUsuarioDTO {

    private Integer usuarioId;
    private Long totalDesafiosCompletados; // Apenas desafios com 100% de acerto
    private Long totalTentativas; // Todas as tentativas (incluindo não perfeitas)
    private BigDecimal mediaPontuacao;
    private BigDecimal melhorPontuacao;
    private BigDecimal percentualEficiencia; // (desafiosPerfeitos / totalTentativas) * 100

    // Getters e Setters

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
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
