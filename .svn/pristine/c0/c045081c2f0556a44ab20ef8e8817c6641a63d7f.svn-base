package com.portalpostal.dao;

import Entidade.Clientes;
import com.portalpostal.dao.handler.ClienteHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClienteDAO extends GenericDAO { 
    
    private final ClienteHandler clienteHandler;

    public ClienteDAO(String nameDB) { 
        super(nameDB, ClienteDAO.class);
        clienteHandler = new ClienteHandler();
    } 

    public List<Clientes> findAll() throws Exception {
        String sql = "SELECT * FROM clientes ORDER BY codigo";        
        return findAll(sql, null, clienteHandler);
    }

    public Clientes find(Integer idCliente) throws Exception {
        String sql = "SELECT * FROM clientes "
                   + "WHERE clientes.codigo = :idCliente";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idCliente", idCliente);
        return (Clientes) find(sql, params, clienteHandler);
    }
    
}
