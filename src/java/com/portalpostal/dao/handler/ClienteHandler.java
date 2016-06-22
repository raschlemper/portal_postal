package com.portalpostal.dao.handler;

import Entidade.Clientes;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class ClienteHandler extends GenericHandler implements ResultSetHandler<Clientes> {
        
    public ClienteHandler() {
        super("clientes");
    }
    
    public ClienteHandler(String table) {
        super(table);
    }

    public Clientes handle(ResultSet result) throws SQLException {
        Clientes cliente = new Clientes(result);
        return cliente;
    }
    
}
