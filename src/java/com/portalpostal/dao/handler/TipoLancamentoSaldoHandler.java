package com.portalpostal.dao.handler;

import com.portalpostal.model.TipoLancamentoSaldo;
import com.portalpostal.model.dd.TipoLancamento;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class TipoLancamentoSaldoHandler extends GenericHandler implements ResultSetHandler<TipoLancamentoSaldo> {
            
    public TipoLancamentoSaldoHandler() { 
        super(null);
    }
    
    public TipoLancamentoSaldoHandler(String table) {
        super(table);
    }

    @Override
    public TipoLancamentoSaldo handle(ResultSet result) throws SQLException {
        TipoLancamentoSaldo lancamentoSaldo = new TipoLancamentoSaldo();
        lancamentoSaldo.setTipo(TipoLancamento.values()[getInt(result, "tipo")]);
        lancamentoSaldo.setAno(getInt(result, "ano"));
        lancamentoSaldo.setMes(getInt(result, "mes"));
        lancamentoSaldo.setValor(getDouble(result, "valor"));
        return lancamentoSaldo;    
    }
    
}
