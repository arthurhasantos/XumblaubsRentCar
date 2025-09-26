package com.app.aluguel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "pedidos_aluguel")
public class PedidoAluguel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Cliente é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
    
    @NotNull(message = "Automóvel é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "automovel_id", nullable = false)
    private Automovel automovel;
    
    @NotNull(message = "Data de início é obrigatória")
    @Future(message = "Data de início deve ser futura")
    @Column(nullable = false)
    private LocalDate dataInicio;
    
    @NotNull(message = "Data de fim é obrigatória")
    @Future(message = "Data de fim deve ser futura")
    @Column(nullable = false)
    private LocalDate dataFim;
    
    @NotNull(message = "Valor total é obrigatório")
    @Positive(message = "Valor total deve ser positivo")
    @Column(nullable = false)
    private Double valorTotal;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPedido status = StatusPedido.PENDENTE;
    
    @Column(length = 1000)
    private String observacoes;
    
    @Column(nullable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();
    
    private LocalDateTime dataAnalise;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agente_analisador_id")
    private Agente agenteAnalisador;
    
    @Column(length = 1000)
    private String parecerFinanceiro;
    
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ContratoCredito> contratosCredito = new ArrayList<>();
    
    // Construtores
    public PedidoAluguel() {}
    
    public PedidoAluguel(Cliente cliente, Automovel automovel, LocalDate dataInicio, LocalDate dataFim, Double valorTotal) {
        this.cliente = cliente;
        this.automovel = automovel;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.valorTotal = valorTotal;
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    
    public Automovel getAutomovel() { return automovel; }
    public void setAutomovel(Automovel automovel) { this.automovel = automovel; }
    
    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }
    
    public LocalDate getDataFim() { return dataFim; }
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }
    
    public Double getValorTotal() { return valorTotal; }
    public void setValorTotal(Double valorTotal) { this.valorTotal = valorTotal; }
    
    public StatusPedido getStatus() { return status; }
    public void setStatus(StatusPedido status) { this.status = status; }
    
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
    
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
    
    public LocalDateTime getDataAnalise() { return dataAnalise; }
    public void setDataAnalise(LocalDateTime dataAnalise) { this.dataAnalise = dataAnalise; }
    
    public Agente getAgenteAnalisador() { return agenteAnalisador; }
    public void setAgenteAnalisador(Agente agenteAnalisador) { this.agenteAnalisador = agenteAnalisador; }
    
    public String getParecerFinanceiro() { return parecerFinanceiro; }
    public void setParecerFinanceiro(String parecerFinanceiro) { this.parecerFinanceiro = parecerFinanceiro; }
    
    public List<ContratoCredito> getContratosCredito() { return contratosCredito; }
    public void setContratosCredito(List<ContratoCredito> contratosCredito) { this.contratosCredito = contratosCredito; }
    
    // Métodos auxiliares
    public boolean isPendente() {
        return status == StatusPedido.PENDENTE;
    }
    
    public boolean isAprovado() {
        return status == StatusPedido.APROVADO;
    }
    
    public boolean isRejeitado() {
        return status == StatusPedido.REJEITADO;
    }
    
    public void aprovar(Agente agente, String parecer) {
        this.status = StatusPedido.APROVADO;
        this.agenteAnalisador = agente;
        this.parecerFinanceiro = parecer;
        this.dataAnalise = LocalDateTime.now();
    }
    
    public void rejeitar(Agente agente, String parecer) {
        this.status = StatusPedido.REJEITADO;
        this.agenteAnalisador = agente;
        this.parecerFinanceiro = parecer;
        this.dataAnalise = LocalDateTime.now();
    }
}
