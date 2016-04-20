package com.portalpostal.dao.handler;

import com.portalpostal.model.LancamentoProgramado;
import com.portalpostal.model.LancamentoTransferenciaProgramado;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class LancamentoTransferenciaProgramadoHandler extends GenericHandler implements ResultSetHandler<LancamentoTransferenciaProgramado> {
        
    public LancamentoTransferenciaProgramadoHandler() {
        super("lancamento_transferencia");
    }
    
    public LancamentoTransferenciaProgramadoHandler(String table) {
        super(table);
    }

    public LancamentoTransferenciaProgramado handle(ResultSet result) throws SQLException {
        LancamentoTransferenciaProgramado lancamento = new LancamentoTransferenciaProgramado();
        lancamento.setIdLancamentoTransferenciaProgramado(getInt(result, "idLancamentoTransferenciaProgramado"));
        lancamento.setLancamentoProgramadoOrigem(getLancamentoProgramadoOrigem(result));
        lancamento.setLancamentoProgramadoDestino(getLancamentoProgramadoDestino(result));
        return lancamento;
    }
    
    private LancamentoProgramado getLancamentoProgramadoOrigem(ResultSet result) throws SQLException {
        if(!existColumn(result, "lancamentoProgramadoOrigem.idLancamentoProgramado")) return null;
        return new LancamentoProgramadoHandler("lancamentoProgramadoOrigem").handle(result); 
    }
    
    private LancamentoProgramado getLancamentoProgramadoDestino(ResultSet result) throws SQLException {
        if(!existColumn(result, "lancamentoProgramadoDestino.idLancamentoProgramado")) return null;
        return new LancamentoProgramadoHandler("lancamentoProgramadoDestino").handle(result); 
    }
    
}
