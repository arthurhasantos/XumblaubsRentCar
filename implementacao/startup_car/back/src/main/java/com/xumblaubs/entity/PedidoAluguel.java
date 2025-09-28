package com.xumblaubs.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos_aluguel")
public class PedidoAluguel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    @NotNull(message = "Cliente é obrigatório")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "automovel_id", nullable = false)
    @NotNull(message = "Automóvel é obrigatório")
    private Automovel automovel;

    @Column(name = "data_inicio", nullable = false)
    @NotNull(message = "Data de início é obrigatória")
    private LocalDateTime dataInicio;

    @Column(name = "data_fim", nullable = false)
    @NotNull(message = "Data de fim é obrigatória")
    private LocalDateTime dataFim;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @NotNull(message = "Status é obrigatório")
    private StatusPedido status;

    @Column(name = "valor_total")
    @DecimalMin(value = "0.0", message = "Valor deve ser positivo")
    private Double valorTotal;

    @Column(name = "observacoes", length = 500)
    @Size(max = 500, message = "Observações não podem exceder 500 caracteres")
    private String observacoes;

    @Column(name = "motivo_rejeicao", length = 500)
    @Size(max = 500, message = "Motivo da rejeição não pode exceder 500 caracteres")
    private String motivoRejeicao;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Construtores
    public PedidoAluguel() {}

    public PedidoAluguel(Cliente cliente, Automovel automovel, LocalDateTime dataInicio, LocalDateTime dataFim) {
        this.cliente = cliente;
        this.automovel = automovel;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.status = StatusPedido.PENDENTE;
        this.ativo = true;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Automovel getAutomovel() {
        return automovel;
    }

    public void setAutomovel(Automovel automovel) {
        this.automovel = automovel;
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

    // Métodos de negócio
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

    public void aprovar() {
        this.status = StatusPedido.APROVADO;
        this.motivoRejeicao = null;
    }

    public void rejeitar(String motivo) {
        this.status = StatusPedido.REJEITADO;
        this.motivoRejeicao = motivo;
    }

    public void iniciar() {
        this.status = StatusPedido.EM_ANDAMENTO;
    }

    public void finalizar() {
        this.status = StatusPedido.FINALIZADO;
    }

    @Override
    public String toString() {
        return "PedidoAluguel{" +
                "id=" + id +
                ", cliente=" + (cliente != null ? cliente.getNome() : "null") +
                ", automovel=" + (automovel != null ? automovel.getMarca() + " " + automovel.getModelo() : "null") +
                ", dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                ", status=" + status +
                ", valorTotal=" + valorTotal +
                ", ativo=" + ativo +
                '}';
    }
}
