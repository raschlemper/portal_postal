package com.portalpostal.model;

import java.util.Objects;

public class LancamentoRateio {
    
    private Integer idLancamentoRateio;
    private PlanoConta planoConta;
    private CentroCusto centroCusto;
    private Lancamento lancamento;
    private Double valor;
    private String observacao;

    public Integer getIdLancamentoRateio() {
        return idLancamentoRateio;
    }

    public void setIdLancamentoRateio(Integer idLancamentoRateio) {
        this.idLancamentoRateio = idLancamentoRateio;
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

    public Lancamento getLancamento() {
        return lancamento;
    }

    public void setLancamento(Lancamento lancamento) {
        this.lancamento = lancamento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }   

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.idLancamentoRateio);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LancamentoRateio other = (LancamentoRateio) obj;
        if (!Objects.equals(this.idLancamentoRateio, other.idLancamentoRateio)) {
            return false;
        }
        return true;
    }
    
    
}
