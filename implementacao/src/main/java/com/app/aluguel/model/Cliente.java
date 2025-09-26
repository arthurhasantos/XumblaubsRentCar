package com.app.aluguel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "clientes")
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false)
    private String nome;
    
    @NotBlank(message = "RG é obrigatório")
    @Column(unique = true, nullable = false)
    private String rg;
    
    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF deve estar no formato 000.000.000-00")
    @Column(unique = true, nullable = false)
    private String cpf;
    
    @NotBlank(message = "Endereço é obrigatório")
    @Column(nullable = false)
    private String endereco;
    
    @NotBlank(message = "Profissão é obrigatória")
    @Column(nullable = false)
    private String profissao;
    
    @Email(message = "Email deve ser válido")
    @Column(unique = true)
    private String email;
    
    @Pattern(regexp = "\\(\\d{2}\\)\\s\\d{4,5}-\\d{4}", message = "Telefone deve estar no formato (00) 00000-0000")
    private String telefone;
    
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EmpregoRenda> empregosRendas = new ArrayList<>();
    
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PedidoAluguel> pedidos = new ArrayList<>();
    
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Contrato> contratos = new ArrayList<>();
    
    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Usuario usuario;
    
    // Construtores
    public Cliente() {}
    
    public Cliente(String nome, String rg, String cpf, String endereco, String profissao) {
        this.nome = nome;
        this.rg = rg;
        this.cpf = cpf;
        this.endereco = endereco;
        this.profissao = profissao;
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getRg() { return rg; }
    public void setRg(String rg) { this.rg = rg; }
    
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    
    public String getProfissao() { return profissao; }
    public void setProfissao(String profissao) { this.profissao = profissao; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    
    public List<EmpregoRenda> getEmpregosRendas() { return empregosRendas; }
    public void setEmpregosRendas(List<EmpregoRenda> empregosRendas) { this.empregosRendas = empregosRendas; }
    
    public List<PedidoAluguel> getPedidos() { return pedidos; }
    public void setPedidos(List<PedidoAluguel> pedidos) { this.pedidos = pedidos; }
    
    public List<Contrato> getContratos() { return contratos; }
    public void setContratos(List<Contrato> contratos) { this.contratos = contratos; }
    
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    
    // Métodos auxiliares
    public void adicionarEmpregoRenda(EmpregoRenda empregoRenda) {
        if (empregosRendas.size() >= 3) {
            throw new IllegalStateException("Máximo de 3 empregos/rendas permitido");
        }
        empregosRendas.add(empregoRenda);
        empregoRenda.setCliente(this);
    }
    
    public double getRendaTotal() {
        return empregosRendas.stream()
                .mapToDouble(EmpregoRenda::getRenda)
                .sum();
    }
}
