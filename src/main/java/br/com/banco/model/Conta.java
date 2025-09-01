package br.com.banco.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "conta")
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titular", nullable = false)
    private String titular;

    @Column(name = "saldo", nullable = false)
    private BigDecimal saldo = BigDecimal.ZERO;

    // Versão para lock otimista
    @Version
    @Column(name = "versao")
    private Long versao;

    public Conta() {}

    public Conta(String titular) {
        this.titular = titular;
        this.saldo = BigDecimal.ZERO;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitular() { return titular; }
    public void setTitular(String titular) { this.titular = titular; }

    public BigDecimal getSaldo() { return saldo; }
    public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }

    public Long getVersao() { return versao; }
    public void setVersao(Long versao) { this.versao = versao; }
}
