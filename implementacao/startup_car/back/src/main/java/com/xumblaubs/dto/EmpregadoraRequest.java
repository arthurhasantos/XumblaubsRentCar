package com.xumblaubs.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class EmpregadoraRequest {

    @NotBlank(message = "Nome da empregadora é obrigatório")
    @Size(max = 100, message = "Nome da empregadora deve ter no máximo 100 caracteres")
    private String nome;


    @NotNull(message = "ID do cliente é obrigatório")
    private Long clienteId;

    @Size(max = 200, message = "Endereço deve ter no máximo 200 caracteres")
    private String endereco;

    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    private String telefone;

    @Email(message = "Email deve ter formato válido")
    @Size(max = 100, message = "Email deve ter no máximo 100 caracteres")
    private String email;

    @Size(max = 20, message = "CNPJ deve ter no máximo 20 caracteres")
    private String cnpj;

    @Size(max = 100, message = "Cargo deve ter no máximo 100 caracteres")
    private String cargo;

    private LocalDateTime dataAdmissao;

    // Construtores
    public EmpregadoraRequest() {}

    public EmpregadoraRequest(String nome, Long clienteId) {
        this.nome = nome;
        this.clienteId = clienteId;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
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

    @Override
    public String toString() {
        return "EmpregadoraRequest{" +
                "nome='" + nome + '\'' +
                ", clienteId=" + clienteId +
                ", endereco='" + endereco + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", cargo='" + cargo + '\'' +
                ", dataAdmissao=" + dataAdmissao +
                '}';
    }
}
