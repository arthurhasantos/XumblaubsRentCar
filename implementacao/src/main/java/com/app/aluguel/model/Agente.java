package com.app.aluguel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "agentes")
public class Agente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false)
    private String nome;
    
    @NotBlank(message = "CNPJ é obrigatório")
    @Pattern(regexp = "\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}", message = "CNPJ deve estar no formato 00.000.000/0000-00")
    @Column(unique = true, nullable = false)
    private String cnpj;
    
    @NotBlank(message = "Endereço é obrigatório")
    @Column(nullable = false)
    private String endereco;
    
    @Email(message = "Email deve ser válido")
    @Column(unique = true)
    private String email;
    
    @Pattern(regexp = "\\(\\d{2}\\)\\s\\d{4,5}-\\d{4}", message = "Telefone deve estar no formato (00) 00000-0000")
    private String telefone;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAgente tipo;
    
    @Column(length = 1000)
    private String observacoes;
    
    @OneToMany(mappedBy = "agenteAnalisador", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PedidoAluguel> pedidosAnalisados = new ArrayList<>();
    
    @OneToMany(mappedBy = "banco", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ContratoCredito> contratosCredito = new ArrayList<>();
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
    // Construtores
    public Agente() {}
    
    public Agente(String nome, String cnpj, String endereco, TipoAgente tipo) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.tipo = tipo;
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
    
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    
    public TipoAgente getTipo() { return tipo; }
    public void setTipo(TipoAgente tipo) { this.tipo = tipo; }
    
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
    
    public List<PedidoAluguel> getPedidosAnalisados() { return pedidosAnalisados; }
    public void setPedidosAnalisados(List<PedidoAluguel> pedidosAnalisados) { this.pedidosAnalisados = pedidosAnalisados; }
    
    public List<ContratoCredito> getContratosCredito() { return contratosCredito; }
    public void setContratosCredito(List<ContratoCredito> contratosCredito) { this.contratosCredito = contratosCredito; }
    
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    
    // Métodos auxiliares
    public boolean isBanco() {
        return tipo == TipoAgente.BANCO;
    }
    
    public boolean isEmpresa() {
        return tipo == TipoAgente.EMPRESA;
    }
}
