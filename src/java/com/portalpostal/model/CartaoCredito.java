package com.portalpostal.model;

import java.util.List;

public class CartaoCredito {
    
    private Integer idCartaoCredito;
    private String nome;
    private String nomeTitular;
    private String numero;
    private String numeroFinal;
    private String codigoSeguranca;
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

    public String getNomeTitular() {
        return nomeTitular;
    }

    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        if(numero != null) {
            String fim = numero.substring(numero.length() - 4); 
            setNumeroFinal(fim);
        }
        this.numero = numero;
    }

    public String getNumeroFinal() {
        String numero = getNumero();
        if(numero != null) {
            String fim = getNumero().substring(getNumero().length() - 4); 
            setNumeroFinal(fim);
        }
        return numeroFinal;
    }

    public void setNumeroFinal(String numeroFinal) {
        this.numeroFinal = numeroFinal;
    }

    public String getCodigoSeguranca() {
        return codigoSeguranca;
    }

    public void setCodigoSeguranca(String codigoSeguranca) {
        this.codigoSeguranca = codigoSeguranca;
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
