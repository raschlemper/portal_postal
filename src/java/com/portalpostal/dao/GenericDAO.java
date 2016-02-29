package com.portalpostal.dao;

import Util.Conexao;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class GenericDAO {
    
    protected Connection connection;

    public GenericDAO(String nomeBD) {    
        java.sql.Connection conn = Conexao.conectar(nomeBD); 
        conn.
    }
    
    private void getConnection() {    
        DataSource dataSource = new org.apache.tomcat.dbcp.dbcp.BasicDataSourcesicDataSource(connection);
        dataSource.
        Sql2o sql2o = new Sql2o(dataSource);
        connection = sql2o.open();        
    }
        
//    protected List executeQuery(String sql, Map<String,Object> params, RowMapper mapper) throws Exception {
//        try {
//            sql = setParams(sql, params);
//            PreparedStatement ps = connection.prepareStatement(sql);
//            ResultSet result = (ResultSet) ps.executeQuery();
//            List lista = new ArrayList();
//            while(result.next()) { lista.add(mapper.mapRow(result, result.getRow())); }
//            return lista;
//        } catch (SQLException e) {
//            throw new Exception(e);
//        } finally {
//            Conexao.desconectar(connection);
//        }
//    }
//        
//    protected Integer executeUpdate(String sql, Map<String,Object> params) throws Exception {
//        try {
//            sql = setParams(sql, params);
//            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//            ps.executeUpdate();
//            return getLastId(ps);
//        } catch (SQLException e) {
//            throw new Exception(e);
//        } finally {
//            Conexao.desconectar(connection);
//        }
//    }
//    
//    private static Integer getLastId(PreparedStatement ps) throws SQLException {
//        ResultSet rs = ps.getGeneratedKeys();
//        if(rs.next()) {
//            return rs.getInt(1);
//        }
//        return null;
//    }
//    
//    private String setParams(String sql, Map<String,Object> params) {        
//        for (String key : params.keySet()) {
//            String named = ":".concat(key);
//            Object value = params.get(key);
//            
//            
//            
//            if(value == null) { sql = sql.replace(named, null); }
//            else { sql = sql.replace(named, value.toString()); }
//        }
//        return sql;
//    }
}
