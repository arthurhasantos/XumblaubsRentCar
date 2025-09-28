package com.xumblaubs.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class ContratoCreditoRequest {

    @NotBlank(message = "Número do contrato é obrigatório")
    @Size(max = 50, message = "Número do contrato deve ter no máximo 50 caracteres")
    private String numero;

    @NotNull(message = "Valor total é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor total deve ser maior que zero")
    private BigDecimal valorTotal;

    @NotNull(message = "Prazo em meses é obrigatório")
    @Min(value = 1, message = "Prazo deve ser pelo menos 1 mês")
    @Max(value = 360, message = "Prazo não pode exceder 360 meses")
    private Integer prazoMeses;

    @NotNull(message = "ID do banco é obrigatório")
    private Long bancoId;

    @NotNull(message = "ID do cliente é obrigatório")
    private Long clienteId;

    @DecimalMin(value = "0.0", message = "Taxa de juros não pode ser negativa")
    @DecimalMax(value = "1.0", message = "Taxa de juros não pode exceder 100%")
    private BigDecimal taxaJuros;

    @Size(max = 500, message = "Observações não podem exceder 500 caracteres")
    private String observacoes;

    // Construtores
    public ContratoCreditoRequest() {}

    public ContratoCreditoRequest(String numero, BigDecimal valorTotal, Integer prazoMeses, 
                                 Long bancoId, Long clienteId) {
        this.numero = numero;
        this.valorTotal = valorTotal;
        this.prazoMeses = prazoMeses;
        this.bancoId = bancoId;
        this.clienteId = clienteId;
    }

    // Getters e Setters
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

    public Long getBancoId() {
        return bancoId;
    }

    public void setBancoId(Long bancoId) {
        this.bancoId = bancoId;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public BigDecimal getTaxaJuros() {
        return taxaJuros;
    }

    public void setTaxaJuros(BigDecimal taxaJuros) {
        this.taxaJuros = taxaJuros;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    @Override
    public String toString() {
        return "ContratoCreditoRequest{" +
                "numero='" + numero + '\'' +
                ", valorTotal=" + valorTotal +
                ", prazoMeses=" + prazoMeses +
                ", bancoId=" + bancoId +
                ", clienteId=" + clienteId +
                ", taxaJuros=" + taxaJuros +
                '}';
    }
}
