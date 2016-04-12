package com.portalpostal.model;

import com.portalpostal.model.dd.TipoLancamento;
import java.util.Date;

public class LancamentoSaldo {
    
    private Integer conta;
    private Integer planoConta;
    private TipoLancamento tipo;
    private Date data;
    private Double valor;

    public Integer getConta() {
        return conta;
    }

    public void setConta(Integer conta) {
        this.conta = conta;
    }

    public Integer getPlanoConta() {
        return planoConta;
    }

    public void setPlanoConta(Integer planoConta) {
        this.planoConta = planoConta;
    }

    public TipoLancamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoLancamento tipo) {
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

    
}
