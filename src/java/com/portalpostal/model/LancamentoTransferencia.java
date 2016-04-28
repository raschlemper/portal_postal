package com.portalpostal.model;

import java.util.Date;

public class LancamentoTransferencia {
    
    private Integer idLancamentoTransferencia;
    private Lancamento lancamentoOrigem;
    private Lancamento lancamentoDestino;
    private String numero;
    private Date competencia;
    private Date dataEmissao;
    private Date dataLancamento;
    private Double valor;
    private String historico;

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

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }
    
    
}
