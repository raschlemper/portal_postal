package com.portalpostal.model;

public class ContaCartaoCredito {
    
    private Integer idContaCartaoCredito;
    private Conta conta;
    private Integer diaFechamento;
    private Integer diaVencimento;
    private Conta contaPagamento;

    public Integer getIdContaCartaoCredito() {
        return idContaCartaoCredito;
    }

    public void setIdContaCartaoCredito(Integer idContaCartaoCredito) {
        this.idContaCartaoCredito = idContaCartaoCredito;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
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

    public Conta getContaPagamento() {
        return contaPagamento;
    }

    public void setContaPagamento(Conta contaPagamento) {
        this.contaPagamento = contaPagamento;
    }
    
}
