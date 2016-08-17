package com.portalpostal.model;

import java.util.Date;

public class LancamentoTransferencia {
    
    private Integer idLancamentoTransferencia;
    private Lancamento lancamentoOrigem;
    private Lancamento lancamentoDestino;
    private String numero;
    private Date dataEmissao;
    private Date dataCompetenciaOrigem;
    private Date dataCompetenciaDestino;
    private Date dataLancamentoOrigem;
    private Date dataLancamentoDestino;
    private Double valor;
    private String historico;
    private String observacao;
    private String usuario;

    public Integer getIdLancamentoTransferencia() {
        return idLancamentoTransferencia;
    }

    public void setIdLancamentoTransferencia(Integer idLancamentoTransferencia) {
        this.idLancamentoTransferencia = idLancamentoTransferencia;
    }    

    public Lancamento getLancamentoOrigem() {
        return lancamentoOrigem;
    }

    public void setLancamentoOrigem(Lancamento lancamentoOrigem) {
        this.lancamentoOrigem = lancamentoOrigem;
    }

    public Lancamento getLancamentoDestino() {
        return lancamentoDestino;
    }

    public void setLancamentoDestino(Lancamento lancamentoDestino) {
        this.lancamentoDestino = lancamentoDestino;
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

    public Date getDataCompetenciaOrigem() {
        return dataCompetenciaOrigem;
    }

    public void setDataCompetenciaOrigem(Date dataCompetenciaOrigem) {
        this.dataCompetenciaOrigem = dataCompetenciaOrigem;
    }

    public Date getDataCompetenciaDestino() {
        return dataCompetenciaDestino;
    }

    public void setDataCompetenciaDestino(Date dataCompetenciaDestino) {
        this.dataCompetenciaDestino = dataCompetenciaDestino;
    }

    public Date getDataLancamentoOrigem() {
        return dataLancamentoOrigem;
    }

    public void setDataLancamentoOrigem(Date dataLancamentoOrigem) {
        this.dataLancamentoOrigem = dataLancamentoOrigem;
    }

    public Date getDataLancamentoDestino() {
        return dataLancamentoDestino;
    }

    public void setDataLancamentoDestino(Date dataLancamentoDestino) {
        this.dataLancamentoDestino = dataLancamentoDestino;
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

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }   

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
}
