package com.portalpostal.model;

import java.util.List;

public class CartaoCredito {
    
    private Integer idCartaoCredito;
    private String nome;
    private String bandeira;
    private Integer diaFechamento;
    private Integer diaVencimento;
    private Double valorLimiteCredito;
    private ContaCorrente contaCorrente;
    private List<Conta> contas;

    public Integer getIdCartaoCredito() {
        return idCartaoCredito;
    }

    public void setIdCartaoCredito(Integer idCartaoCredito) {
        this.idCartaoCredito = idCartaoCredito;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getBandeira() {
        return bandeira;
    }

    public void setBandeira(String bandeira) {
        this.bandeira = bandeira;
    }
    
    public Integer getDiaFechamento() {
        return diaFechamento;
    }

    public void setDiaFechamento(Integer diaFechamento) {
        this.diaFechamento = diaFechamento;
    }

    public Integer getDiaVencimento() {
        return diaVencimento;
    }

    public void setDiaVencimento(Integer diaVencimento) {
        this.diaVencimento = diaVencimento;
    }

    public Double getValorLimiteCredito() {
        return valorLimiteCredito;
    }

    public void setValorLimiteCredito(Double valorLimiteCredito) {
        this.valorLimiteCredito = valorLimiteCredito;
    }

    public ContaCorrente getContaCorrente() {
        return contaCorrente;
    }

    public void setContaCorrente(ContaCorrente contaCorrente) {
        this.contaCorrente = contaCorrente;
    }   

    public List<Conta> getContas() {
        return contas;
    }

    public void setContas(List<Conta> contas) {
        this.contas = contas;
    }
    
}
