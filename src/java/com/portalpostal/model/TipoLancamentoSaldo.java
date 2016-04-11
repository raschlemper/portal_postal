package com.portalpostal.model;

import com.portalpostal.model.dd.TipoLancamento;

public class TipoLancamentoSaldo {
    
    private TipoLancamento tipo;
    private Integer ano;
    private Integer mes;
    private Double valor;

    public TipoLancamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoLancamento tipo) {
        this.tipo = tipo;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }   
    
}
