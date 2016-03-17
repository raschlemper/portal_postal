package com.portalpostal.model;

import com.portalpostal.model.dd.TipoPlanoConta;
import java.util.Date;

public class Lancamento {
    
    private Integer idLancamento;
    private Conta conta;
    private PlanoConta planoConta;
    private TipoPlanoConta tipo;
    private Date data;
    private Double valor;
    private String historico;

    public Integer getIdLancamento() {
        return idLancamento;
    }

    public void setIdLancamento(Integer idLancamento) {
        this.idLancamento = idLancamento;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public PlanoConta getPlanoConta() {
        return planoConta;
    }

    public void setPlanoConta(PlanoConta planoConta) {
        this.planoConta = planoConta;
    }

    public TipoPlanoConta getTipo() {
        return tipo;
    }

    public void setTipo(TipoPlanoConta tipo) {
        this.tipo = tipo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }
    
}
