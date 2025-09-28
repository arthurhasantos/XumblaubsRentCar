package com.xumblaubs.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "contratos_credito")
public class ContratoCredito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Número do contrato é obrigatório")
    @Size(max = 50, message = "Número do contrato deve ter no máximo 50 caracteres")
    @Column(name = "numero", unique = true, nullable = false)
    private String numero;

    @NotNull(message = "Valor total é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor total deve ser maior que zero")
    @Column(name = "valor_total", nullable = false, precision = 15, scale = 2)
    private BigDecimal valorTotal;

    @NotNull(message = "Prazo em meses é obrigatório")
    @Min(value = 1, message = "Prazo deve ser pelo menos 1 mês")
    @Max(value = 360, message = "Prazo não pode exceder 360 meses")
    @Column(name = "prazo_meses", nullable = false)
    private Integer prazoMeses;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "banco_id", nullable = false)
    @NotNull(message = "Banco é obrigatório")
    private Banco banco;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    @NotNull(message = "Cliente é obrigatório")
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @NotNull(message = "Status é obrigatório")
    private StatusContratoCredito status;

    @Column(name = "taxa_juros", precision = 5, scale = 4)
    @DecimalMin(value = "0.0", message = "Taxa de juros não pode ser negativa")
    @DecimalMax(value = "1.0", message = "Taxa de juros não pode exceder 100%")
    private BigDecimal taxaJuros;

    @Column(name = "valor_parcela", precision = 15, scale = 2)
    private BigDecimal valorParcela;

    @Column(name = "observacoes", length = 500)
    @Size(max = 500, message = "Observações não podem exceder 500 caracteres")
    private String observacoes;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Construtores
    public ContratoCredito() {}

    public ContratoCredito(String numero, BigDecimal valorTotal, Integer prazoMeses, 
                          Banco banco, Cliente cliente) {
        this.numero = numero;
        this.valorTotal = valorTotal;
        this.prazoMeses = prazoMeses;
        this.banco = banco;
        this.cliente = cliente;
        this.status = StatusContratoCredito.ATIVO;
        this.ativo = true;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Integer getPrazoMeses() {
        return prazoMeses;
    }

    public void setPrazoMeses(Integer prazoMeses) {
        this.prazoMeses = prazoMeses;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public StatusContratoCredito getStatus() {
        return status;
    }

    public void setStatus(StatusContratoCredito status) {
        this.status = status;
    }

    public BigDecimal getTaxaJuros() {
        return taxaJuros;
    }

    public void setTaxaJuros(BigDecimal taxaJuros) {
        this.taxaJuros = taxaJuros;
    }

    public BigDecimal getValorParcela() {
        return valorParcela;
    }

    public void setValorParcela(BigDecimal valorParcela) {
        this.valorParcela = valorParcela;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Método para emitir o contrato
    public void emitir() {
        this.status = StatusContratoCredito.ATIVO;
        this.ativo = true;
    }

    // Método para calcular valor da parcela
    public void calcularValorParcela() {
        if (this.taxaJuros != null && this.valorTotal != null && this.prazoMeses != null) {
            // Cálculo simples de parcela (sem juros compostos)
            BigDecimal valorComJuros = this.valorTotal.multiply(
                BigDecimal.ONE.add(this.taxaJuros)
            );
            this.valorParcela = valorComJuros.divide(
                BigDecimal.valueOf(this.prazoMeses), 
                2, 
                java.math.RoundingMode.HALF_UP
            );
        }
    }

    @Override
    public String toString() {
        return "ContratoCredito{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", valorTotal=" + valorTotal +
                ", prazoMeses=" + prazoMeses +
                ", banco=" + (banco != null ? banco.getNome() : "null") +
                ", cliente=" + (cliente != null ? cliente.getNome() : "null") +
                ", status=" + status +
                ", ativo=" + ativo +
                '}';
    }
}
