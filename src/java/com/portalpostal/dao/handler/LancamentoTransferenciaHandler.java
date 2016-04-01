package com.portalpostal.dao.handler;

import com.portalpostal.model.Conta;
import com.portalpostal.model.LancamentoTransferencia;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class LancamentoTransferenciaHandler extends GenericHandler implements ResultSetHandler<LancamentoTransferencia> {
        
    public LancamentoTransferenciaHandler() {
        super("lancamento_transferencia");
    }
    
    public LancamentoTransferenciaHandler(String table) {
        super(table);
    }

    public LancamentoTransferencia handle(ResultSet result) throws SQLException {
        LancamentoTransferencia lancamento = new LancamentoTransferencia();
        lancamento.setIdLancamentoTransferencia(getInt(result, "idLancamentoTranferencia"));
        lancamento.setContaOrigem(getContaOrigem(result));
        lancamento.setContaDestino(getContaDestino(result));
        lancamento.setData(getDate(result, "data"));
        lancamento.setValor(getDouble(result, "valor"));
        lancamento.setHistorico(getString(result, "historico"));
        return lancamento;
    }
    
    private Conta getContaOrigem(ResultSet result) throws SQLException {
        if(!existColumn(result, "contaOrigem.idConta")) return null;
        return new ContaHandler("contaOrigem").handle(result); 
    }
    
    private Conta getContaDestino(ResultSet result) throws SQLException {
        if(!existColumn(result, "contaDestino.idConta")) return null;
        return new ContaHandler("contaDestino").handle(result); 
    }
    
}
