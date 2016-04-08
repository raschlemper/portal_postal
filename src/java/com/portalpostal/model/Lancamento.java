package com.portalpostal.model;

import com.portalpostal.model.dd.TipoSituacao;
import java.util.Date;

public class Lancamento {
    
    private Integer idLancamento;
    private Conta conta;
    private PlanoConta planoConta;
    private String favorecido;
    private String numero;
    private Date data;
    private Double valor;
    private TipoSituacao situacao;
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

    public String getFavorecido() {
        return favorecido;
    }

    public void setFavorecido(String favorecido) {
        this.favorecido = favorecido;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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

    public TipoSituacao getSituacao() {
        return situacao;
    }

    public void setSituacao(TipoSituacao situacao) {
        this.situacao = situacao;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    
}
