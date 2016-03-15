package com.portalpostal.dao.handler;

import com.portalpostal.model.Banco;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class BancoHandler implements ResultSetHandler<Banco> {

    public Banco handle(ResultSet result) throws SQLException {
        Banco banco = new Banco();
        banco.setIdBanco(result.getInt("banco.idBanco"));
        banco.setNome(result.getString("banco.nome"));
        banco.setNumero(result.getInt("banco.numero"));
        banco.setWebsite(result.getString("banco.website"));
        return banco;
    }
    
}
