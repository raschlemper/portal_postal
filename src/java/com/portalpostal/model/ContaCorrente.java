package com.portalpostal.model;

import java.util.List;

public class ContaCorrente {
    
    private Integer idContaCorrente;
    private String nome;
    private Banco banco;
    private Integer agencia;
    private Integer agenciaDv;
    private Integer contaCorrente;
    private Integer contaCorrenteDv;
    private Boolean poupanca;
    private List<CartaoCredito> cartaoCreditos;
    private List<CarteiraCobranca> carteiraCobrancas;
    private List<Conta> contas;

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

    public Integer getAgenciaDv() {
        return agenciaDv;
    }

    public void setAgenciaDv(Integer agenciaDv) {
        this.agenciaDv = agenciaDv;
    }

    public Integer getContaCorrente() {
        return contaCorrente;
    }

    public void setContaCorrente(Integer contaCorrente) {
        this.contaCorrente = contaCorrente;
    }

    public Integer getContaCorrenteDv() {
        return contaCorrenteDv;
    }

    public void setContaCorrenteDv(Integer contaCorrenteDv) {
        this.contaCorrenteDv = contaCorrenteDv;
    }
    
    public Boolean getPoupanca() {
        return poupanca;
    }

    public void setPoupanca(Boolean poupanca) {
        this.poupanca = poupanca;
    }

    public List<CartaoCredito> getCartaoCreditos() {
        return cartaoCreditos;
    }

    public void setCartaoCreditos(List<CartaoCredito> cartaoCreditos) {
        this.cartaoCreditos = cartaoCreditos;
    }

    public List<CarteiraCobranca> getCarteiraCobrancas() {
        return carteiraCobrancas;
    }

    public void setCarteiraCobrancas(List<CarteiraCobranca> carteiraCobrancas) {
        this.carteiraCobrancas = carteiraCobrancas;
    }

    public List<Conta> getContas() {
        return contas;
    }

    public void setContas(List<Conta> contas) {
        this.contas = contas;
    }
    
}
