package com.portalpostal.model;

import com.portalpostal.model.dd.TipoLancamento;
import com.portalpostal.model.dd.TipoModeloLancamento;
import com.portalpostal.model.dd.TipoSituacao;
import java.util.Date;

public class Lancamento {
    
    private Integer idLancamento;
    private Conta conta;
    private PlanoConta planoConta;
    private LancamentoProgramado lancamentoProgramado;
    private TipoLancamento tipo;
    private String favorecido;
    private String numero;
    private Date dataEmissao;
    private Date dataVencimento;
    private Date dataPagamento;
    private Date dataCompensacao;
    private Double valor;
    private TipoSituacao situacao;
    private TipoModeloLancamento modelo;
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

    public LancamentoProgramado getLancamentoProgramado() {
        return lancamentoProgramado;
    }

    public void setLancamentoProgramado(LancamentoProgramado lancamentoProgramado) {
        this.lancamentoProgramado = lancamentoProgramado;
    }

    public TipoLancamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoLancamento tipo) {
        this.tipo = tipo;
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

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Date getDataCompensacao() {
        return dataCompensacao;
    }

    public void setDataCompensacao(Date dataCompensacao) {
        this.dataCompensacao = dataCompensacao;
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

    public TipoModeloLancamento getModelo() {
        return modelo;
    }

    public void setModelo(TipoModeloLancamento modelo) {
        this.modelo = modelo;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    
}
