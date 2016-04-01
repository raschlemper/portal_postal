package com.portalpostal.dao.handler;

import com.portalpostal.model.Banco;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class BancoHandler extends GenericHandler implements ResultSetHandler<Banco> {
        
    public BancoHandler() {
        super("banco");
    }
    
    public BancoHandler(String table) {
        super(table);
    }

    public Banco handle(ResultSet result) throws SQLException {
        Banco banco = new Banco();
        banco.setIdBanco(getInt(result, "idBanco"));
        banco.setNome(getString(result, "nome"));
        banco.setNumero(getInt(result, "numero"));
        banco.setWebsite(getString(result, "website"));
        return banco;
    }
    
}
