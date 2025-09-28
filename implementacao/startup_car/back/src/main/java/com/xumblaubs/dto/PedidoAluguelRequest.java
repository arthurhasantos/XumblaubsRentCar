package com.xumblaubs.dto;

import jakarta.validation.constraints.*;
import com.xumblaubs.entity.StatusPedido;

import java.time.LocalDateTime;

public class PedidoAluguelRequest {

    @NotNull(message = "Cliente é obrigatório")
    private Long clienteId;

    @NotNull(message = "Automóvel é obrigatório")
    private Long automovelId;

    @NotNull(message = "Data de início é obrigatória")
    @Future(message = "Data de início deve ser no futuro")
    private LocalDateTime dataInicio;

    @NotNull(message = "Data de fim é obrigatória")
    @Future(message = "Data de fim deve ser no futuro")
    private LocalDateTime dataFim;

    private StatusPedido status;

    @DecimalMin(value = "0.0", message = "Valor deve ser positivo")
    private Double valorTotal;

    @Size(max = 500, message = "Observações não podem exceder 500 caracteres")
    private String observacoes;

    @Size(max = 500, message = "Motivo da rejeição não pode exceder 500 caracteres")
    private String motivoRejeicao;

    private Boolean ativo;

    // Construtores
    public PedidoAluguelRequest() {}

    public PedidoAluguelRequest(Long clienteId, Long automovelId, LocalDateTime dataInicio, LocalDateTime dataFim) {
        this.clienteId = clienteId;
        this.automovelId = automovelId;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.status = StatusPedido.PENDENTE;
        this.ativo = true;
    }

    // Getters e Setters
    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getAutomovelId() {
        return automovelId;
    }

    public void setAutomovelId(Long automovelId) {
        this.automovelId = automovelId;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getMotivoRejeicao() {
        return motivoRejeicao;
    }

    public void setMotivoRejeicao(String motivoRejeicao) {
        this.motivoRejeicao = motivoRejeicao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    // Métodos de validação
    public boolean isDataFimValida() {
        return dataFim != null && dataInicio != null && dataFim.isAfter(dataInicio);
    }

    public long getDiasAluguel() {
        if (dataInicio != null && dataFim != null) {
            return java.time.Duration.between(dataInicio, dataFim).toDays();
        }
        return 0;
    }

    @Override
    public String toString() {
        return "PedidoAluguelRequest{" +
                "clienteId=" + clienteId +
                ", automovelId=" + automovelId +
                ", dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                ", status=" + status +
                ", valorTotal=" + valorTotal +
                ", ativo=" + ativo +
                '}';
    }
}
