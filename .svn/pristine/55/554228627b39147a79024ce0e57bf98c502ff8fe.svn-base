package com.portalpostal.dao.handler;

import com.portalpostal.model.Saldo;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class SaldoHandler extends GenericHandler implements ResultSetHandler<Saldo> {
            
    public SaldoHandler() { 
        super(null);
    }
    
    public SaldoHandler(String table) {
        super(table);
    }

    @Override
    public Saldo handle(ResultSet result) throws SQLException {
        Saldo saldo = new Saldo();
        saldo.setId(getInt(result, "id"));
        saldo.setData(getDate(result, "data"));
        saldo.setValor(getDouble(result, "valor"));
        return saldo;    
    }
    
}
