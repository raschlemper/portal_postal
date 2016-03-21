package com.portalpostal.dao.handler;

import com.portalpostal.model.LancamentoTransferencia;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class LancamentoTransferenciaHandler implements ResultSetHandler<LancamentoTransferencia> {
    
    private final ContaHandler contaOrigemHandler;
    private final ContaHandler contaDestinoHandler;
    private String table = "lancamento";
    
    public LancamentoTransferenciaHandler() {
        contaOrigemHandler = new ContaHandler();        
        contaDestinoHandler = new ContaHandler();
    }
    
    public LancamentoTransferenciaHandler(String table) {
        contaOrigemHandler = new ContaHandler("contaOrigem");
        contaDestinoHandler = new ContaHandler("contaDestino");
        this.table = table;
    }

    public LancamentoTransferencia handle(ResultSet result) throws SQLException {
        LancamentoTransferencia lancamento = new LancamentoTransferencia();
        lancamento.setIdLancamentoTransferencia(result.getInt(table + ".idLancamentoTranferencia"));
        lancamento.setContaOrigem(contaOrigemHandler.handle(result));
        lancamento.setContaDestino(contaDestinoHandler.handle(result));
        lancamento.setData(result.getDate(table + ".data"));
        lancamento.setValor(result.getDouble(table + ".valor"));
        lancamento.setHistorico(result.getString(table + ".historico"));
        return lancamento;
    }
    
}
