package com.xumblaubs.dto;

import com.xumblaubs.entity.PedidoAluguel;
import com.xumblaubs.entity.StatusPedido;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PedidoAluguelResponse {

    private Long id;
    private Long clienteId;
    private String clienteNome;
    private String clienteCpf;
    private Long automovelId;
    private String automovelMarca;
    private String automovelModelo;
    private String automovelPlaca;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private StatusPedido status;
    private String statusDescricao;
    private Double valorTotal;
    private String observacoes;
    private String motivoRejeicao;
    private Boolean ativo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private long diasAluguel;

    // Construtores
    public PedidoAluguelResponse() {}

    public PedidoAluguelResponse(PedidoAluguel pedido) {
        this.id = pedido.getId();
        this.clienteId = pedido.getCliente().getId();
        this.clienteNome = pedido.getCliente().getNome();
        this.clienteCpf = pedido.getCliente().getCpf();
        this.automovelId = pedido.getAutomovel().getId();
        this.automovelMarca = pedido.getAutomovel().getMarca();
        this.automovelModelo = pedido.getAutomovel().getModelo();
        this.automovelPlaca = pedido.getAutomovel().getPlaca();
        this.dataInicio = pedido.getDataInicio();
        this.dataFim = pedido.getDataFim();
        this.status = pedido.getStatus();
        this.statusDescricao = pedido.getStatus().getDescricao();
        this.valorTotal = pedido.getValorTotal();
        this.observacoes = pedido.getObservacoes();
        this.motivoRejeicao = pedido.getMotivoRejeicao();
        this.ativo = pedido.getAtivo();
        this.createdAt = pedido.getCreatedAt();
        this.updatedAt = pedido.getUpdatedAt();
        this.diasAluguel = pedido.getDataFim() != null && pedido.getDataInicio() != null 
            ? java.time.Duration.between(pedido.getDataInicio(), pedido.getDataFim()).toDays() 
            : 0;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getClienteCpf() {
        return clienteCpf;
    }

    public void setClienteCpf(String clienteCpf) {
        this.clienteCpf = clienteCpf;
    }

    public Long getAutomovelId() {
        return automovelId;
    }

    public void setAutomovelId(Long automovelId) {
        this.automovelId = automovelId;
    }

    public String getAutomovelMarca() {
        return automovelMarca;
    }

    public void setAutomovelMarca(String automovelMarca) {
        this.automovelMarca = automovelMarca;
    }

    public String getAutomovelModelo() {
        return automovelModelo;
    }

    public void setAutomovelModelo(String automovelModelo) {
        this.automovelModelo = automovelModelo;
    }

    public String getAutomovelPlaca() {
        return automovelPlaca;
    }

    public void setAutomovelPlaca(String automovelPlaca) {
        this.automovelPlaca = automovelPlaca;
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
        this.statusDescricao = status != null ? status.getDescricao() : null;
    }

    public String getStatusDescricao() {
        return statusDescricao;
    }

    public void setStatusDescricao(String statusDescricao) {
        this.statusDescricao = statusDescricao;
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

    public long getDiasAluguel() {
        return diasAluguel;
    }

    public void setDiasAluguel(long diasAluguel) {
        this.diasAluguel = diasAluguel;
    }

    // Métodos auxiliares
    public String getDataInicioFormatada() {
        return dataInicio != null ? dataInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : null;
    }

    public String getDataFimFormatada() {
        return dataFim != null ? dataFim.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : null;
    }

    public String getCreatedAtFormatada() {
        return createdAt != null ? createdAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : null;
    }

    public String getUpdatedAtFormatada() {
        return updatedAt != null ? updatedAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : null;
    }

    public String getValorTotalFormatado() {
        return valorTotal != null ? String.format("R$ %.2f", valorTotal) : "Não definido";
    }

    public String getAutomovelCompleto() {
        return automovelMarca + " " + automovelModelo + " (" + automovelPlaca + ")";
    }

    public String getClienteCompleto() {
        return clienteNome + " (" + clienteCpf + ")";
    }

    public boolean isPendente() {
        return StatusPedido.PENDENTE.equals(this.status);
    }

    public boolean isAprovado() {
        return StatusPedido.APROVADO.equals(this.status);
    }

    public boolean isRejeitado() {
        return StatusPedido.REJEITADO.equals(this.status);
    }

    public boolean isEmAndamento() {
        return StatusPedido.EM_ANDAMENTO.equals(this.status);
    }

    public boolean isFinalizado() {
        return StatusPedido.FINALIZADO.equals(this.status);
    }

    @Override
    public String toString() {
        return "PedidoAluguelResponse{" +
                "id=" + id +
                ", clienteNome='" + clienteNome + '\'' +
                ", automovelCompleto='" + getAutomovelCompleto() + '\'' +
                ", dataInicio=" + getDataInicioFormatada() +
                ", dataFim=" + getDataFimFormatada() +
                ", status=" + statusDescricao +
                ", valorTotal=" + getValorTotalFormatado() +
                ", diasAluguel=" + diasAluguel +
                '}';
    }
}
