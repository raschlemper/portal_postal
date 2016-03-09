package Util;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class Sql2oConnexao {
    
    public static int contConn = 0;
    private static Connection instance;
    
    public static Connection getInstance(String nome, Class<?> clazz) {
        if(instance != null) { return instance; }
        return connect(nome, clazz);
    }
    
    private static Connection connect(String nome, Class<?> clazz) {   
        Connection connection = null;
        try {     
            Class.forName("com.mysql.jdbc.Driver"); 
            Sql2o sql2o = new Sql2o(TipoConexao.CLIENTE.url(nome), 
                                    TipoConexao.CLIENTE.username(), 
                                    TipoConexao.CLIENTE.password());
            connection = sql2o.open();
            contConn++;
            System.out.println("nova concetion " + contConn + " >>>>> " + clazz.getSimpleName());
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            return connection;              
        }
    }    
    
}
