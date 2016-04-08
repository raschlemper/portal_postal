package com.portalpostal.model;

public class LancamentoTransferencia {
    
    private Integer idLancamentoTransferencia;
    private Lancamento lancamentoOrigem;
    private Lancamento lancamentoDestino;

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
    
    
}
