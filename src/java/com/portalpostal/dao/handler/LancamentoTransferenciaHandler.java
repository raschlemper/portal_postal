package com.portalpostal.dao.handler;

import com.portalpostal.model.Lancamento;
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
        lancamento.setLancamentoOrigem(getLancamentoOrigem(result));
        lancamento.setLancamentoDestino(getLancamentoDestino(result));
        return lancamento;
    }
    
    private Lancamento getLancamentoOrigem(ResultSet result) throws SQLException {
        if(!existColumn(result, "lancamentoOrigem.idLancamento")) return null;
        return new LancamentoHandler("lancamentoOrigem").handle(result); 
    }
    
    private Lancamento getLancamentoDestino(ResultSet result) throws SQLException {
        if(!existColumn(result, "lancamentoDestino.idLancamento")) return null;
        return new LancamentoHandler("lancamentoDestino").handle(result); 
    }
    
}
