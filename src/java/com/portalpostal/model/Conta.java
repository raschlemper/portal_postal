package com.portalpostal.model;

import com.portalpostal.model.dd.TipoConta;
import com.portalpostal.model.dd.TipoStatusConta;
import java.util.Date;
import java.util.List;

public class Conta {
    
    private Integer idConta;
    private ContaCorrente contaCorrente;
    private CartaoCredito cartaoCredito;
    private String nome;
    private TipoConta tipo;
    private TipoStatusConta status;
    private Date dataAbertura;
    private Double valorSaldoAbertura;    
    private List<Lancamento> lancamentos;

    public Integer getIdConta() {
        return idConta;
    }

    public void setIdConta(Integer idConta) {
        this.idConta = idConta;
    }

    public ContaCorrente getContaCorrente() {
        return contaCorrente;
    }

    public void setContaCorrente(ContaCorrente contaCorrente) {
        this.contaCorrente = contaCorrente;
    }

    public CartaoCredito getCartaoCredito() {
        return cartaoCredito;
    }

    public void setCartaoCredito(CartaoCredito cartaoCredito) {
        this.cartaoCredito = cartaoCredito;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoConta getTipo() {
        return tipo;
    }

    public void setTipo(TipoConta tipo) {
        this.tipo = tipo;
    }

    public TipoStatusConta getStatus() {
        return status;
    }

    public void setStatus(TipoStatusConta status) {
        this.status = status;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public Double getValorSaldoAbertura() {
        return valorSaldoAbertura;
    }

    public void setValorSaldoAbertura(Double valorSaldoAbertura) {
        this.valorSaldoAbertura = valorSaldoAbertura;
    }

    public List<Lancamento> getLancamentos() {
        return lancamentos;
    }

    public void setLancamentos(List<Lancamento> lancamentos) {
        this.lancamentos = lancamentos;
    }

    
}