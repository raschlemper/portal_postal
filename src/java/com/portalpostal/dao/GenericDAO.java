package com.portalpostal.dao;

import Util.Conexao;
import java.sql.Connection;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class GenericDAO {
    
    // pesquisar jdbcTemplate without xml
        
    protected static NamedParameterJdbcTemplate getNametTemplate(String nomeBD) throws Exception {
//        Connection connection = Conexao.conectar(nomeBD);
//        DriverManagerDataSource ds = new DriverManagerDataSource(connection.getMetaData().getURL());
        return new NamedParameterJdbcTemplate(getConnection(nomeBD));
    }

//    protected static JdbcTemplate getJDBCTemplate(String nomeBD) throws Exception{
//        Connection connection = Conexao.conectar(nomeBD);
//        DriverManagerDataSource ds = new DriverManagerDataSource(connection.getMetaData().getURL());
//        return new JdbcTemplate(ds);
//    }    
    
    private static DataSource getConnection(String nomeBD) {
        DriverManagerDataSource ds = new DriverManagerDataSource(
                "com.mysql.jdbc.Driver", 
                "jdbc:mysql://localhost:3306/pp_" + nomeBD +"?zeroDateTimeBehavior=convertToNull&autoReconnect=true",
                "root", "123456");
        return ds;
    }
}
