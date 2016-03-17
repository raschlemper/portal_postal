package com.portalpostal.model;

import java.util.Date;

public class LancamentoTransferencia {
    
    private Integer idLancamentoTransferencia;
    private Conta contaOrigem;
    private Conta contaDestino;
    private Date data;
    private Double valor;
    private String historico;

    public Integer getIdLancamentoTransferencia() {
        return idLancamentoTransferencia;
    }

    public void setIdLancamentoTransferencia(Integer idLancamentoTransferencia) {
        this.idLancamentoTransferencia = idLancamentoTransferencia;
    }

    public Conta getContaOrigem() {
        return contaOrigem;
    }

    public void setContaOrigem(Conta contaOrigem) {
        this.contaOrigem = contaOrigem;
    }

    public Conta getContaDestino() {
        return contaDestino;
    }

    public void setContaDestino(Conta contaDestino) {
        this.contaDestino = contaDestino;
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

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }
    
}
