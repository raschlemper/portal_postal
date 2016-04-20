package com.portalpostal.model;

public class LancamentoTransferenciaProgramado {
    
    private Integer idLancamentoTransferenciaProgramado;
    private LancamentoProgramado lancamentoProgramadoOrigem;
    private LancamentoProgramado lancamentoProgramadoDestino;

    public Integer getIdLancamentoTransferenciaProgramado() {
        return idLancamentoTransferenciaProgramado;
    }

    public void setIdLancamentoTransferenciaProgramado(Integer idLancamentoTransferenciaProgramado) {
        this.idLancamentoTransferenciaProgramado = idLancamentoTransferenciaProgramado;
    }

    public LancamentoProgramado getLancamentoProgramadoOrigem() {
        return lancamentoProgramadoOrigem;
    }

    public void setLancamentoProgramadoOrigem(LancamentoProgramado lancamentoProgramadoOrigem) {
        this.lancamentoProgramadoOrigem = lancamentoProgramadoOrigem;
    }

    public LancamentoProgramado getLancamentoProgramadoDestino() {
        return lancamentoProgramadoDestino;
    }

    public void setLancamentoProgramadoDestino(LancamentoProgramado lancamentoProgramadoDestino) {
        this.lancamentoProgramadoDestino = lancamentoProgramadoDestino;
    }    
    
}
