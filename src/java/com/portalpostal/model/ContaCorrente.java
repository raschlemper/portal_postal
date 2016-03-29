package com.portalpostal.model;

public class ContaCorrente {
    
    private Integer idContaCorrente;
    private String nome;
    private Banco banco;
    private Integer agencia;
    private Integer contaCorrente;
    private Integer carteira;
    private Boolean poupanca;

    public Integer getIdContaCorrente() {
        return idContaCorrente;
    }

    public void setIdContaCorrente(Integer idContaCorrente) {
        this.idContaCorrente = idContaCorrente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public Integer getAgencia() {
        return agencia;
    }

    public void setAgencia(Integer agencia) {
        this.agencia = agencia;
    }

    public Integer getContaCorrente() {
        return contaCorrente;
    }

    public void setContaCorrente(Integer contaCorrente) {
        this.contaCorrente = contaCorrente;
    }

    public Integer getCarteira() {
        return carteira;
    }

    public void setCarteira(Integer carteira) {
        this.carteira = carteira;
    }

    public Boolean getPoupanca() {
        return poupanca;
    }

    public void setPoupanca(Boolean poupanca) {
        this.poupanca = poupanca;
    }
    
}
