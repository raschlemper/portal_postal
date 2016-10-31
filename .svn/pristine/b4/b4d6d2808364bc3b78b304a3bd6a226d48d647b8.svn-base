package com.portalpostal.converter;

import com.portalpostal.model.Lancamento;
import com.portalpostal.model.LancamentoProgramado;
import com.portalpostal.model.dd.TipoModeloLancamento;
import com.portalpostal.model.dd.TipoSituacaoLancamento;
import java.util.Date;

public class LancamentoConverter {
    
    public static Lancamento converter(LancamentoProgramado lancamentoProgramado) {
        if(lancamentoProgramado == null) return null;
        Lancamento lancamento = new Lancamento();
        lancamento.setConta(lancamentoProgramado.getConta());
        lancamento.setPlanoConta(lancamentoProgramado.getPlanoConta());
        lancamento.setLancamentoProgramado(lancamentoProgramado);
        lancamento.setTipo(lancamentoProgramado.getTipo());
        lancamento.setFavorecido(lancamentoProgramado.getFavorecido());
        lancamento.setNumero(lancamentoProgramado.getNumero());
        lancamento.setDataCompetencia(lancamentoProgramado.getDataCompetencia());
        lancamento.setDataEmissao(lancamentoProgramado.getDataEmissao());
        lancamento.setDataVencimento(lancamentoProgramado.getDataVencimento());
        lancamento.setDataLancamento(new Date());
        lancamento.setDataCompensacao(null);
        lancamento.setValor(lancamentoProgramado.getValor());
        lancamento.setSituacao(TipoSituacaoLancamento.NORMAL);
        lancamento.setModelo(TipoModeloLancamento.PROGRAMADO);
        lancamento.setHistorico(lancamentoProgramado.getHistorico());
        return lancamento;
    }
}
