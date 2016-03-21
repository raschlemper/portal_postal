package com.portalpostal.model;

public class CartaoCredito {
    
    private Integer idCartaoCredito;
    private ContaCorrente contaCorrente;
    private String bandeira;
    private Integer diaFechamento;
    private Integer diaVencimento;
    private Double valorLimiteCredito;

    public Integer getIdCartaoCredito() {
        return idCartaoCredito;
    }

    public void setIdCartaoCredito(Integer idCartaoCredito) {
        this.idCartaoCredito = idCartaoCredito;
    }

    public ContaCorrente getContaCorrente() {
        return contaCorrente;
    }

    public void setContaCorrente(ContaCorrente contaCorrente) {
        this.contaCorrente = contaCorrente;
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
    
    
}
