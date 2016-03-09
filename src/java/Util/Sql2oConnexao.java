package Util;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class Sql2oConnexao {
    
    public static Connection getConnection(String nameDB) { 
        try {     
            Class.forName("com.mysql.jdbc.Driver"); 
            Sql2o sql2o = new Sql2o(TipoConexao.CLIENTE.url(nameDB), 
                                    TipoConexao.CLIENTE.username(), 
                                    TipoConexao.CLIENTE.password());
            return sql2o.beginTransaction();
        } catch(Exception ex) {
            ex.printStackTrace();         
        }
        return null;
    }  
    
}
