package com.app.aluguel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "contratos_credito")
public class ContratoCredito {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Contrato é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contrato_id", nullable = false)
    private Contrato contrato;
    
    @NotNull(message = "Pedido é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private PedidoAluguel pedido;
    
    @NotNull(message = "Banco é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "banco_id", nullable = false)
    private Agente banco;
    
    @NotNull(message = "Valor do crédito é obrigatório")
    @Positive(message = "Valor do crédito deve ser positivo")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorCredito;
    
    @NotNull(message = "Taxa de juros é obrigatória")
    @Positive(message = "Taxa de juros deve ser positiva")
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal taxaJuros;
    
    @NotNull(message = "Número de parcelas é obrigatório")
    @Column(nullable = false)
    private Integer numeroParcelas;
    
    @NotNull(message = "Data de início é obrigatória")
    @Column(nullable = false)
    private LocalDate dataInicio;
    
    @Column(nullable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusContratoCredito status = StatusContratoCredito.ATIVO;
    
    @Column(length = 1000)
    private String observacoes;
    
    // Construtores
    public ContratoCredito() {}
    
    public ContratoCredito(Contrato contrato, PedidoAluguel pedido, Agente banco, BigDecimal valorCredito, BigDecimal taxaJuros, Integer numeroParcelas, LocalDate dataInicio) {
        this.contrato = contrato;
        this.pedido = pedido;
        this.banco = banco;
        this.valorCredito = valorCredito;
        this.taxaJuros = taxaJuros;
        this.numeroParcelas = numeroParcelas;
        this.dataInicio = dataInicio;
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Contrato getContrato() { return contrato; }
    public void setContrato(Contrato contrato) { this.contrato = contrato; }
    
    public PedidoAluguel getPedido() { return pedido; }
    public void setPedido(PedidoAluguel pedido) { this.pedido = pedido; }
    
    public Agente getBanco() { return banco; }
    public void setBanco(Agente banco) { this.banco = banco; }
    
    public BigDecimal getValorCredito() { return valorCredito; }
    public void setValorCredito(BigDecimal valorCredito) { this.valorCredito = valorCredito; }
    
    public BigDecimal getTaxaJuros() { return taxaJuros; }
    public void setTaxaJuros(BigDecimal taxaJuros) { this.taxaJuros = taxaJuros; }
    
    public Integer getNumeroParcelas() { return numeroParcelas; }
    public void setNumeroParcelas(Integer numeroParcelas) { this.numeroParcelas = numeroParcelas; }
    
    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }
    
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
    
    public StatusContratoCredito getStatus() { return status; }
    public void setStatus(StatusContratoCredito status) { this.status = status; }
    
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
    
    // Métodos auxiliares
    public boolean isAtivo() {
        return status == StatusContratoCredito.ATIVO;
    }
    
    public BigDecimal getValorParcela() {
        if (numeroParcelas == null || numeroParcelas <= 0) {
            return BigDecimal.ZERO;
        }
        return valorCredito.divide(BigDecimal.valueOf(numeroParcelas), 2, RoundingMode.HALF_UP);
    }
}
