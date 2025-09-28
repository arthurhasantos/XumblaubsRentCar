package com.xumblaubs.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class BancoRequest {

    @NotBlank(message = "Nome do banco é obrigatório")
    @Size(max = 100, message = "Nome do banco deve ter no máximo 100 caracteres")
    private String nome;

    @NotBlank(message = "Código do banco é obrigatório")
    @Size(max = 10, message = "Código do banco deve ter no máximo 10 caracteres")
    private String codigo;

    @NotBlank(message = "CNPJ é obrigatório")
    @Pattern(regexp = "^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}$", 
             message = "CNPJ deve estar no formato XX.XXX.XXX/XXXX-XX")
    private String cnpj;

    @Size(max = 200, message = "Endereço deve ter no máximo 200 caracteres")
    private String endereco;

    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    private String telefone;

    @Email(message = "Email deve ter formato válido")
    @Size(max = 100, message = "Email deve ter no máximo 100 caracteres")
    private String email;

    @DecimalMin(value = "0.0", message = "Taxa de juros não pode ser negativa")
    @DecimalMax(value = "1.0", message = "Taxa de juros não pode exceder 100%")
    private BigDecimal taxaJurosPadrao;

    @DecimalMin(value = "0.0", message = "Limite de crédito não pode ser negativo")
    private BigDecimal limiteCreditoMaximo;

    // Construtores
    public BancoRequest() {}

    public BancoRequest(String nome, String codigo, String cnpj) {
        this.nome = nome;
        this.codigo = codigo;
        this.cnpj = cnpj;
    }

    // Getters e Setters
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

    public BigDecimal getTaxaJurosPadrao() {
        return taxaJurosPadrao;
    }

    public void setTaxaJurosPadrao(BigDecimal taxaJurosPadrao) {
        this.taxaJurosPadrao = taxaJurosPadrao;
    }

    public BigDecimal getLimiteCreditoMaximo() {
        return limiteCreditoMaximo;
    }

    public void setLimiteCreditoMaximo(BigDecimal limiteCreditoMaximo) {
        this.limiteCreditoMaximo = limiteCreditoMaximo;
    }

    @Override
    public String toString() {
        return "BancoRequest{" +
                "nome='" + nome + '\'' +
                ", codigo='" + codigo + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", endereco='" + endereco + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", taxaJurosPadrao=" + taxaJurosPadrao +
                ", limiteCreditoMaximo=" + limiteCreditoMaximo +
                '}';
    }
}
