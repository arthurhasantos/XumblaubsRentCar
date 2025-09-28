package com.xumblaubs.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "RG é obrigatório")
    @Size(max = 20, message = "RG deve ter no máximo 20 caracteres")
    @Column(name = "rg", nullable = false)
    private String rg;
    
    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF deve estar no formato 000.000.000-00")
    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    @Column(name = "nome", nullable = false)
    private String nome;
    
    @NotBlank(message = "Endereço é obrigatório")
    @Size(max = 200, message = "Endereço deve ter no máximo 200 caracteres")
    @Column(name = "endereco", nullable = false)
    private String endereco;
    
    @NotBlank(message = "Profissão é obrigatória")
    @Size(max = 100, message = "Profissão deve ter no máximo 100 caracteres")
    @Column(name = "profissao", nullable = false)
    private String profissao;
    
    @NotBlank(message = "Email é obrigatório")
    @Size(max = 50, message = "Email deve ter no máximo 50 caracteres")
    @Email(message = "Email deve ter formato válido")
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, max = 120, message = "Senha deve ter entre 6 e 120 caracteres")
    @Column(name = "senha", nullable = false)
    private String senha;
    
    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relacionamento com empregadoras (máximo 3)
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Empregadora> empregadoras = new ArrayList<>();
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public Cliente() {}
    
    public Cliente(String rg, String cpf, String nome, String endereco, String profissao, String email, String senha) {
        this.rg = rg;
        this.cpf = cpf;
        this.nome = nome;
        this.endereco = endereco;
        this.profissao = profissao;
        this.email = email;
        this.senha = senha;
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

    public List<Empregadora> getEmpregadoras() {
        return empregadoras;
    }

    public void setEmpregadoras(List<Empregadora> empregadoras) {
        this.empregadoras = empregadoras;
    }

    // Método para adicionar empregadora (máximo 3)
    public void adicionarEmpregadora(Empregadora empregadora) {
        if (this.empregadoras.size() < 3) {
            empregadora.setCliente(this);
            this.empregadoras.add(empregadora);
        } else {
            throw new RuntimeException("Cliente já possui o máximo de 3 empregadoras");
        }
    }

    // Método para calcular rendimento total
    public Double getRendimentoTotal() {
        return empregadoras.stream()
                .filter(Empregadora::getAtivo)
                .mapToDouble(emp -> emp.getRendimento().doubleValue())
                .sum();
    }
}
