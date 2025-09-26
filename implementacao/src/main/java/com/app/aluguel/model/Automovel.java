package com.app.aluguel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "automoveis")
public class Automovel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Matrícula é obrigatória")
    @Column(unique = true, nullable = false)
    private String matricula;
    
    @NotNull(message = "Ano é obrigatório")
    @PastOrPresent(message = "Ano deve ser atual ou anterior")
    @Column(nullable = false)
    private Integer ano;
    
    @NotBlank(message = "Marca é obrigatória")
    @Column(nullable = false)
    private String marca;
    
    @NotBlank(message = "Modelo é obrigatório")
    @Column(nullable = false)
    private String modelo;
    
    @NotBlank(message = "Placa é obrigatória")
    @Column(unique = true, nullable = false)
    private String placa;
    
    @NotNull(message = "Valor de aluguel é obrigatório")
    @Positive(message = "Valor de aluguel deve ser positivo")
    @Column(nullable = false)
    private Double valorAluguel;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoContrato tipoContrato;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAutomovel status = StatusAutomovel.DISPONIVEL;
    
    @Column(length = 1000)
    private String observacoes;
    
    @OneToMany(mappedBy = "automovel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PedidoAluguel> pedidos = new ArrayList<>();
    
    @OneToMany(mappedBy = "automovel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Contrato> contratos = new ArrayList<>();
    
    // Construtores
    public Automovel() {}
    
    public Automovel(String matricula, Integer ano, String marca, String modelo, String placa, Double valorAluguel, TipoContrato tipoContrato) {
        this.matricula = matricula;
        this.ano = ano;
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.valorAluguel = valorAluguel;
        this.tipoContrato = tipoContrato;
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    
    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }
    
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    
    public Double getValorAluguel() { return valorAluguel; }
    public void setValorAluguel(Double valorAluguel) { this.valorAluguel = valorAluguel; }
    
    public TipoContrato getTipoContrato() { return tipoContrato; }
    public void setTipoContrato(TipoContrato tipoContrato) { this.tipoContrato = tipoContrato; }
    
    public StatusAutomovel getStatus() { return status; }
    public void setStatus(StatusAutomovel status) { this.status = status; }
    
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
    
    public List<PedidoAluguel> getPedidos() { return pedidos; }
    public void setPedidos(List<PedidoAluguel> pedidos) { this.pedidos = pedidos; }
    
    public List<Contrato> getContratos() { return contratos; }
    public void setContratos(List<Contrato> contratos) { this.contratos = contratos; }
    
    // Métodos auxiliares
    public String getDescricaoCompleta() {
        return String.format("%s %s %d - %s", marca, modelo, ano, placa);
    }
    
    public boolean isDisponivel() {
        return status == StatusAutomovel.DISPONIVEL;
    }
}
