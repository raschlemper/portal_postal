package com.portalpostal.dao.handler;

import com.portalpostal.model.Banco;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class BancoHandler implements ResultSetHandler<Banco> {
    
    private String table = "banco";
    
    public BancoHandler() {}
    
    public BancoHandler(String table) {
        this.table = table;      
    }

    public Banco handle(ResultSet result) throws SQLException {
        Banco banco = new Banco();
        banco.setIdBanco(result.getInt(table + ".idBanco"));
        banco.setNome(result.getString(table + ".nome"));
        banco.setNumero(result.getInt(table + ".numero"));
        banco.setWebsite(result.getString(table + ".website"));
        return banco;
    }
    
}
