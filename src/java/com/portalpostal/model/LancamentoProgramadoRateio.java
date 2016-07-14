package com.portalpostal.model;

import java.util.Objects;

public class LancamentoProgramadoRateio {
    
    private Integer idLancamentoProgramadoRateio;
    private PlanoConta planoConta;
    private CentroCusto centroCusto;
    private LancamentoProgramado lancamentoProgramado;
    private Double valor;
    private String observacao;

    public Integer getIdLancamentoProgramadoRateio() {
        return idLancamentoProgramadoRateio;
    }

    public void setIdLancamentoProgramadoRateio(Integer idLancamentoProgramadoRateio) {
        this.idLancamentoProgramadoRateio = idLancamentoProgramadoRateio;
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
        hash = 23 * hash + Objects.hashCode(this.idLancamentoProgramadoRateio);
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
        final LancamentoProgramadoRateio other = (LancamentoProgramadoRateio) obj;
        if (!Objects.equals(this.idLancamentoProgramadoRateio, other.idLancamentoProgramadoRateio)) {
            return false;
        }
        return true;
    }
    
    
}
