package com.xumblaubs.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bancos")
public class Banco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do banco é obrigatório")
    @Size(max = 100, message = "Nome do banco deve ter no máximo 100 caracteres")
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotBlank(message = "Código do banco é obrigatório")
    @Size(max = 10, message = "Código do banco deve ter no máximo 10 caracteres")
    @Column(name = "codigo", unique = true, nullable = false)
    private String codigo;

    @NotBlank(message = "CNPJ é obrigatório")
    @Pattern(regexp = "^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}$", 
             message = "CNPJ deve estar no formato XX.XXX.XXX/XXXX-XX")
    @Column(name = "cnpj", unique = true, nullable = false)
    private String cnpj;

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

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Relacionamento com contratos de crédito
    @OneToMany(mappedBy = "banco", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ContratoCredito> contratosCredito = new ArrayList<>();

    // Construtores
    public Banco() {}

    public Banco(String nome, String codigo, String cnpj) {
        this.nome = nome;
        this.codigo = codigo;
        this.cnpj = cnpj;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
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

    public List<ContratoCredito> getContratosCredito() {
        return contratosCredito;
    }

    public void setContratosCredito(List<ContratoCredito> contratosCredito) {
        this.contratosCredito = contratosCredito;
    }

    // Método para conceder crédito
    public ContratoCredito concederCredito(String numeroContrato, BigDecimal valorTotal, 
                                          Integer prazoMeses, Cliente cliente) {
        ContratoCredito contrato = new ContratoCredito();
        contrato.setNumero(numeroContrato);
        contrato.setValorTotal(valorTotal);
        contrato.setPrazoMeses(prazoMeses);
        contrato.setBanco(this);
        contrato.setCliente(cliente);
        // Taxa de juros será definida externamente
        contrato.setStatus(StatusContratoCredito.PENDENTE);
        contrato.calcularValorParcela();
        
        this.contratosCredito.add(contrato);
        return contrato;
    }

    @Override
    public String toString() {
        return "Banco{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", codigo='" + codigo + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", ativo=" + ativo +
                '}';
    }
}
