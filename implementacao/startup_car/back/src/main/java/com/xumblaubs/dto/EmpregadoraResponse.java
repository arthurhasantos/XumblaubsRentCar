package com.xumblaubs.dto;

import com.xumblaubs.entity.Empregadora;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EmpregadoraResponse {

    private Long id;
    private String nome;
    private BigDecimal rendimento;
    private Long clienteId;
    private String clienteNome;
    private String endereco;
    private String telefone;
    private String email;
    private String senha;
    private String cnpj;
    private String cargo;
    private LocalDateTime dataAdmissao;
    private Boolean ativo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Construtor
    public EmpregadoraResponse() {}

    public EmpregadoraResponse(Empregadora empregadora) {
        this.id = empregadora.getId();
        this.nome = empregadora.getNome();
        this.rendimento = empregadora.getRendimento();
        this.clienteId = empregadora.getCliente() != null ? empregadora.getCliente().getId() : null;
        this.clienteNome = empregadora.getCliente() != null ? empregadora.getCliente().getNome() : null;
        this.endereco = empregadora.getEndereco();
        this.telefone = empregadora.getTelefone();
        this.email = empregadora.getEmail();
        this.senha = empregadora.getSenha();
        this.cnpj = empregadora.getCnpj();
        this.cargo = empregadora.getCargo();
        this.dataAdmissao = empregadora.getDataAdmissao();
        this.ativo = empregadora.getAtivo();
        this.createdAt = empregadora.getCreatedAt();
        this.updatedAt = empregadora.getUpdatedAt();
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getRendimento() {
        return rendimento;
    }

    public void setRendimento(BigDecimal rendimento) {
        this.rendimento = rendimento;
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public LocalDateTime getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(LocalDateTime dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
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
        return "EmpregadoraResponse{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", rendimento=" + rendimento +
                ", clienteNome='" + clienteNome + '\'' +
                ", ativo=" + ativo +
                '}';
    }
}
