package com.portalpostal.model;

import com.portalpostal.model.dd.TipoStatusConta;
import java.util.Date;

public class Conta {
    
    private Integer idConta;
    private ContaCorrente contaCorrente;
    private TipoStatusConta status;
    private Date dataAbertura;
    private Double valorSaldoAbertura;

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