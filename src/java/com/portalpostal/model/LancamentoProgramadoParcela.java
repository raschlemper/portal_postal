package com.portalpostal.model;

import java.util.Date;

public class LancamentoProgramadoParcela {
    
    private Integer idLancamentoProgramadoParcela;
    private PlanoConta planoConta;
    private CentroCusto centroCusto;
    private LancamentoProgramado lancamentoProgramado;
    private Integer numero;
    private Date dataVencimento;
    private Double valor;

    public Integer getIdLancamentoProgramadoParcela() {
        return idLancamentoProgramadoParcela;
    }

    public void setIdLancamentoProgramadoParcela(Integer idLancamentoProgramadoParcela) {
        this.idLancamentoProgramadoParcela = idLancamentoProgramadoParcela;
    }

    public PlanoConta getPlanoConta() {
        return planoConta;
    }

    public void setPlanoConta(PlanoConta planoConta) {
        this.planoConta = planoConta;
    }

    public CentroCusto getCentroCusto() {
        return centroCusto;
    }

    public void setCentroCusto(CentroCusto centroCusto) {
        this.centroCusto = centroCusto;
    }

    public LancamentoProgramado getLancamentoProgramado() {
        return lancamentoProgramado;
    }

    public void setLancamentoProgramado(LancamentoProgramado lancamentoProgramado) {
        this.lancamentoProgramado = lancamentoProgramado;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }    
    
}
