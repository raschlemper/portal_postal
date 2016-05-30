package com.portalpostal.model;

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
    
    
}
