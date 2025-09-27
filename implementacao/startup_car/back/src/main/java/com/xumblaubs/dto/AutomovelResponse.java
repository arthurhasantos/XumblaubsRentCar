package com.xumblaubs.dto;

import com.xumblaubs.entity.TipoProprietario;
import java.time.LocalDateTime;

public class AutomovelResponse {
    
    private Long id;
    private String matricula;
    private Integer ano;
    private String marca;
    private String modelo;
    private String placa;
    private TipoProprietario tipoProprietario;
    private Long proprietarioId;
    private String proprietarioNome;
    private String observacoes;
    private Boolean ativo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Construtores
    public AutomovelResponse() {}
    
    public AutomovelResponse(Long id, String matricula, Integer ano, String marca, 
                           String modelo, String placa, TipoProprietario tipoProprietario) {
        this.id = id;
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
}
