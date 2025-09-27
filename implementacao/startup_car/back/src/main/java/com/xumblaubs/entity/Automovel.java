package com.xumblaubs.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Min;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

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
    @Min(value = 1900, message = "Ano deve ser maior que 1900")
    @Column(nullable = false)
    private Integer ano;
    
    @NotBlank(message = "Marca é obrigatória")
    @Column(nullable = false)
    private String marca;
    
    @NotBlank(message = "Modelo é obrigatório")
    @Column(nullable = false)
    private String modelo;
    
    @NotBlank(message = "Placa é obrigatória")
    @Pattern(regexp = "^[A-Z]{3}[0-9]{4}$|^[A-Z]{3}[0-9][A-Z][0-9]{2}$", 
             message = "Placa deve estar no formato ABC1234 ou ABC1D23")
    @Column(unique = true, nullable = false)
    private String placa;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoProprietario tipoProprietario;
    
    @Column(name = "proprietario_id")
    private Long proprietarioId;
    
    @Column(name = "proprietario_nome")
    private String proprietarioNome;
    
    @Column(columnDefinition = "TEXT")
    private String observacoes;
    
    @Column(nullable = false)
    private Boolean ativo = true;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Construtores
    public Automovel() {}
    
    public Automovel(String matricula, Integer ano, String marca, String modelo, 
                    String placa, TipoProprietario tipoProprietario) {
        this.matricula = matricula;
        this.ano = ano;
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.tipoProprietario = tipoProprietario;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getMatricula() {
        return matricula;
    }
    
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    
    public Integer getAno() {
        return ano;
    }
    
    public void setAno(Integer ano) {
        this.ano = ano;
    }
    
    public String getMarca() {
        return marca;
    }
    
    public void setMarca(String marca) {
        this.marca = marca;
    }
    
    public String getModelo() {
        return modelo;
    }
    
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    
    public String getPlaca() {
        return placa;
    }
    
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    
    public TipoProprietario getTipoProprietario() {
        return tipoProprietario;
    }
    
    public void setTipoProprietario(TipoProprietario tipoProprietario) {
        this.tipoProprietario = tipoProprietario;
    }
    
    public Long getProprietarioId() {
        return proprietarioId;
    }
    
    public void setProprietarioId(Long proprietarioId) {
        this.proprietarioId = proprietarioId;
    }
    
    public String getProprietarioNome() {
        return proprietarioNome;
    }
    
    public void setProprietarioNome(String proprietarioNome) {
        this.proprietarioNome = proprietarioNome;
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
        return "Automovel{" +
                "id=" + id +
                ", matricula='" + matricula + '\'' +
                ", ano=" + ano +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", placa='" + placa + '\'' +
                ", tipoProprietario=" + tipoProprietario +
                ", ativo=" + ativo +
                '}';
    }
}
