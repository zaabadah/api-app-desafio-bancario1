package br.com.banco.dto;

import java.math.BigDecimal;

public class TransferenciaDTO {
    private Long idDestino;
    private BigDecimal valor;

    public Long getIdDestino() { return idDestino; }
    public void setIdDestino(Long idDestino) { this.idDestino = idDestino; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
}
