package com.portalpostal.dao;

import com.portalpostal.dao.builder.RowMapper;
import Util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GenericDAO {
    
    private Connection connection;

    public GenericDAO(String nomeBD) {        
        connection = Conexao.conectar(nomeBD);
    }
        
    protected List execute(String sql, Map<String,Object> params, RowMapper mapper) throws Exception {
        List lista = new ArrayList();
        try {
            sql = setParams(sql, params);
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet result = (ResultSet) ps.executeQuery();
            while(result.next()) { lista.add(mapper.mapRow(result, result.getRow())); }
        } catch (SQLException e) {
            throw new Exception(e);
        } finally {
            Conexao.desconectar(connection);
        }
        return lista;
    }
    
    private String setParams(String sql, Map<String,Object> params) {        
        for (String key : params.keySet()) {
            String named = ":".concat(key);
            sql = sql.replace(named, params.get(key).toString());
        }
        return sql;
    }
}
