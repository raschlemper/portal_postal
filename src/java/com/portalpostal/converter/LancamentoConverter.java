package com.portalpostal.converter;

import com.portalpostal.model.Lancamento;
import com.portalpostal.model.LancamentoProgramado;

public class LancamentoConverter {
    
    public static Lancamento converter(LancamentoProgramado lancamentoProgramado) {
        if(lancamentoProgramado == null) return null;
        Lancamento lancamento = new Lancamento();
        lancamento.setConta(lancamentoProgramado.getConta());
        lancamento.setPlanoConta(lancamentoProgramado.getPlanoConta());
        lancamento.setTipo(lancamentoProgramado.getTipo());
        lancamento.setFavorecido(lancamentoProgramado.getFavorecido());
        lancamento.setNumero(lancamentoProgramado.getNumero() + "-" + lancamentoProgramado.getNumeroParcela());
        lancamento.setData(lancamentoProgramado.getData());
        lancamento.setValor(lancamentoProgramado.getValor());
        lancamento.setSituacao(lancamentoProgramado.getSituacao());
        lancamento.setHistorico(lancamentoProgramado.getHistorico());
        return lancamento;
    }
}
