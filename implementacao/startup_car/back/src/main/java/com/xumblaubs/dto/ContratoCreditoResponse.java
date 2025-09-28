package com.xumblaubs.dto;

import com.xumblaubs.entity.ContratoCredito;
import com.xumblaubs.entity.StatusContratoCredito;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ContratoCreditoResponse {

    private Long id;
    private String numero;
    private BigDecimal valorTotal;
    private Integer prazoMeses;
    private Long bancoId;
    private String bancoNome;
    private Long clienteId;
    private String clienteNome;
    private StatusContratoCredito status;
    private BigDecimal taxaJuros;
    private BigDecimal valorParcela;
    private String observacoes;
    private Boolean ativo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Construtor
    public ContratoCreditoResponse() {}

    public ContratoCreditoResponse(ContratoCredito contrato) {
        this.id = contrato.getId();
        this.numero = contrato.getNumero();
        this.valorTotal = contrato.getValorTotal();
        this.prazoMeses = contrato.getPrazoMeses();
        this.bancoId = contrato.getBanco() != null ? contrato.getBanco().getId() : null;
        this.bancoNome = contrato.getBanco() != null ? contrato.getBanco().getNome() : null;
        this.clienteId = contrato.getCliente() != null ? contrato.getCliente().getId() : null;
        this.clienteNome = contrato.getCliente() != null ? contrato.getCliente().getNome() : null;
        this.status = contrato.getStatus();
        this.taxaJuros = contrato.getTaxaJuros();
        this.valorParcela = contrato.getValorParcela();
        this.observacoes = contrato.getObservacoes();
        this.ativo = contrato.getAtivo();
        this.createdAt = contrato.getCreatedAt();
        this.updatedAt = contrato.getUpdatedAt();
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

    public Long getBancoId() {
        return bancoId;
    }

    public void setBancoId(Long bancoId) {
        this.bancoId = bancoId;
    }

    public String getBancoNome() {
        return bancoNome;
    }

    public void setBancoNome(String bancoNome) {
        this.bancoNome = bancoNome;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
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

    @Override
    public String toString() {
        return "ContratoCreditoResponse{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", valorTotal=" + valorTotal +
                ", prazoMeses=" + prazoMeses +
                ", bancoNome='" + bancoNome + '\'' +
                ", clienteNome='" + clienteNome + '\'' +
                ", status=" + status +
                ", ativo=" + ativo +
                '}';
    }
}
