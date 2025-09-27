package com.xumblaubs.dto;

import com.xumblaubs.entity.TipoProprietario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Min;

public class AutomovelRequest {
    
    @NotBlank(message = "Matrícula é obrigatória")
    private String matricula;
    
    @NotNull(message = "Ano é obrigatório")
    @Min(value = 1900, message = "Ano deve ser maior que 1900")
    private Integer ano;
    
    @NotBlank(message = "Marca é obrigatória")
    private String marca;
    
    @NotBlank(message = "Modelo é obrigatório")
    private String modelo;
    
    @NotBlank(message = "Placa é obrigatória")
    @Pattern(regexp = "^[A-Z]{3}[0-9]{4}$|^[A-Z]{3}[0-9][A-Z][0-9]{2}$", 
             message = "Placa deve estar no formato ABC1234 ou ABC1D23")
    private String placa;
    
    @NotNull(message = "Tipo de proprietário é obrigatório")
    private TipoProprietario tipoProprietario;
    
    private Long proprietarioId;
    
    private String proprietarioNome;
    
    private String observacoes;
    
    private Boolean ativo = true;
    
    // Construtores
    public AutomovelRequest() {}
    
    public AutomovelRequest(String matricula, Integer ano, String marca, String modelo, 
                           String placa, TipoProprietario tipoProprietario) {
        this.matricula = matricula;
        this.ano = ano;
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.tipoProprietario = tipoProprietario;
    }
    
    // Getters e Setters
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
}
