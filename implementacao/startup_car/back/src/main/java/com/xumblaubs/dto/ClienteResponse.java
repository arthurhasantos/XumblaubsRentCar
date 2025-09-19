package com.xumblaubs.dto;

import com.xumblaubs.entity.Cliente;

import java.time.LocalDateTime;

public class ClienteResponse {
    
    private Long id;
    private String rg;
    private String cpf;
    private String nome;
    private String endereco;
    private String profissao;
    private Boolean ativo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Constructors
    public ClienteResponse() {}
    
    public ClienteResponse(Cliente cliente) {
        this.id = cliente.getId();
        this.rg = cliente.getRg();
        this.cpf = cliente.getCpf();
        this.nome = cliente.getNome();
        this.endereco = cliente.getEndereco();
        this.profissao = cliente.getProfissao();
        this.ativo = cliente.getAtivo();
        this.createdAt = cliente.getCreatedAt();
        this.updatedAt = cliente.getUpdatedAt();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getRg() {
        return rg;
    }
    
    public void setRg(String rg) {
        this.rg = rg;
    }
    
    public String getCpf() {
        return cpf;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getEndereco() {
        return endereco;
    }
    
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    public String getProfissao() {
        return profissao;
    }
    
    public void setProfissao(String profissao) {
        this.profissao = profissao;
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
