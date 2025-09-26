package com.app.aluguel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "empregos_rendas")
public class EmpregoRenda {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Nome da entidade empregadora é obrigatório")
    @Column(nullable = false)
    private String entidadeEmpregadora;
    
    @NotNull(message = "Renda é obrigatória")
    @Positive(message = "Renda deve ser positiva")
    @Column(nullable = false)
    private Double renda;
    
    @Column(length = 500)
    private String observacoes;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
    
    // Construtores
    public EmpregoRenda() {}
    
    public EmpregoRenda(String entidadeEmpregadora, Double renda) {
        this.entidadeEmpregadora = entidadeEmpregadora;
        this.renda = renda;
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getEntidadeEmpregadora() { return entidadeEmpregadora; }
    public void setEntidadeEmpregadora(String entidadeEmpregadora) { this.entidadeEmpregadora = entidadeEmpregadora; }
    
    public Double getRenda() { return renda; }
    public void setRenda(Double renda) { this.renda = renda; }
    
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
    
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
}
