package com.portalpostal.converter;

import com.portalpostal.model.Lancamento;
import com.portalpostal.model.LancamentoProgramado;
import com.portalpostal.model.dd.TipoModeloLancamento;

public class LancamentoConverter {
    
    public static Lancamento converter(LancamentoProgramado lancamentoProgramado) {
        if(lancamentoProgramado == null) return null;
        Lancamento lancamento = new Lancamento();
        lancamento.setConta(lancamentoProgramado.getConta());
        lancamento.setPlanoConta(lancamentoProgramado.getPlanoConta());
        lancamento.setLancamentoProgramado(lancamentoProgramado);
        lancamento.setTipo(lancamentoProgramado.getTipo());
        lancamento.setFavorecido(lancamentoProgramado.getFavorecido());
        lancamento.setNumero(lancamentoProgramado.getNumero() + "-" + lancamentoProgramado.getNumeroParcela());
        lancamento.setDataEmissao(lancamentoProgramado.getDataEmissao());
        lancamento.setDataVencimento(lancamentoProgramado.getDataVencimento());
        lancamento.setDataPagamento(lancamentoProgramado.getDataPagamento());
        lancamento.setDataCompensacao(null);
        lancamento.setValor(lancamentoProgramado.getValor());
        lancamento.setSituacao(lancamentoProgramado.getSituacao());
        lancamento.setModelo(TipoModeloLancamento.PROGRAMADO);
        lancamento.setHistorico(lancamentoProgramado.getHistorico());
        return lancamento;
    }
}
