package com.portalpostal.model;

import com.portalpostal.model.dd.TipoStatusConta;
import java.util.Date;

public class Conta {
    
    private Integer idConta;
    private TipoConta tipo;
    private Banco banco;
    private Integer agencia;
    private Integer contaCorrente;
    private Integer carteira;
    private Double valorLimiteCredito;
    private Date dataVencimentoCredito;
    private TipoStatusConta status;
    private Date dataAbertura;
    private Double valorSaldoAbertura;

    public Integer getIdConta() {
        return idConta;
    }

    public void setIdConta(Integer idConta) {
        this.idConta = idConta;
    }

    public TipoConta getTipo() {
        return tipo;
    }

    public void setTipo(TipoConta tipo) {
        this.tipo = tipo;
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

    public Double getValorLimiteCredito() {
        return valorLimiteCredito;
    }

    public void setValorLimiteCredito(Double valorLimiteCredito) {
        this.valorLimiteCredito = valorLimiteCredito;
    }
    
    public Date getDataVencimentoCredito() {
        return dataVencimentoCredito;
    }

    public void setDataVencimentoCredito(Date dataVencimentoCredito) {
        this.dataVencimentoCredito = dataVencimentoCredito;
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
    
}
