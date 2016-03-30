package com.portalpostal.model;

public class ContaCorrente {
    
    private Integer idContaCorrente;
    private String nome;
    private Banco banco;
    private Integer agencia;
    private Integer agenciaDv;
    private Integer contaCorrente;
    private Integer contaCorrenteDv;
//    private Carteira carteira;
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
    
}
