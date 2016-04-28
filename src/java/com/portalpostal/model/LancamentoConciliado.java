package com.portalpostal.model;

import com.portalpostal.model.dd.TipoLancamento;
import java.util.Date;

public class LancamentoConciliado {
    
    private Integer idLancamentoConciliado;
    private Conta conta;
    private PlanoConta planoConta;
    private TipoLancamento tipo;
    private Date competencia;
    private Date dataEmissao;
    private Date dataLancamento;
    private Double valor;

    public Integer getIdLancamentoConciliado() {
        return idLancamentoConciliado;
    }

    public void setIdLancamentoConciliado(Integer idLancamentoConciliado) {
        this.idLancamentoConciliado = idLancamentoConciliado;
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

    public TipoLancamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoLancamento tipo) {
        this.tipo = tipo;
    }

    public Date getCompetencia() {
        return competencia;
    }

    public void setCompetencia(Date competencia) {
        this.competencia = competencia;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Date getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(Date dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
    
    
}
