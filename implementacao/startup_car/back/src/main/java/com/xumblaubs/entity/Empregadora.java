package com.xumblaubs.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "empregadoras")
public class Empregadora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome da empregadora é obrigatório")
    @Size(max = 100, message = "Nome da empregadora deve ter no máximo 100 caracteres")
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull(message = "Rendimento é obrigatório")
    @DecimalMin(value = "0.01", message = "Rendimento deve ser maior que zero")
    @Column(name = "rendimento", nullable = false, precision = 15, scale = 2)
    private BigDecimal rendimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    @NotNull(message = "Cliente é obrigatório")
    private Cliente cliente;

    @Size(max = 200, message = "Endereço deve ter no máximo 200 caracteres")
    @Column(name = "endereco")
    private String endereco;

    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    @Column(name = "telefone")
    private String telefone;

    @Email(message = "Email deve ter formato válido")
    @Size(max = 100, message = "Email deve ter no máximo 100 caracteres")
    @Column(name = "email")
    private String email;

    @Size(max = 20, message = "CNPJ deve ter no máximo 20 caracteres")
    @Column(name = "cnpj")
    private String cnpj;

    @Size(max = 100, message = "Cargo deve ter no máximo 100 caracteres")
    @Column(name = "cargo")
    private String cargo;

    @Column(name = "data_admissao")
    private LocalDateTime dataAdmissao;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Construtores
    public Empregadora() {}

    public Empregadora(String nome, BigDecimal rendimento, Cliente cliente) {
        this.nome = nome;
        this.rendimento = rendimento;
        this.cliente = cliente;
        this.ativo = true;
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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
        return "Empregadora{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", rendimento=" + rendimento +
                ", cliente=" + (cliente != null ? cliente.getNome() : "null") +
                ", ativo=" + ativo +
                '}';
    }
}
