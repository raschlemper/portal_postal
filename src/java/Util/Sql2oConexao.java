package Util;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class Sql2oConexao {
    
    private static String DRIVER = "com.mysql.jdbc.Driver";
    private static TipoConexao CONEXAO = TipoConexao.TEST;
    
    public static Connection getConnection(String nameDB) { 
        try {     
            Class.forName(DRIVER);
            //System.out.println("sql2o - " + CONEXAO.url("pp_" + nameDB) + " " + CONEXAO.username() + " " + CONEXAO.password());
            Sql2o sql2o = new Sql2o(CONEXAO.url("pp_" + nameDB), CONEXAO.username(), CONEXAO.password());
            return sql2o.open();
        } catch(Exception ex) {
            ex.printStackTrace();         
        }
        return null;
    }  
    
    public static Connection getConnectionGeral() { 
        try {     
            Class.forName(DRIVER); 
            Sql2o sql2o = new Sql2o(CONEXAO.url("portalpostal"), CONEXAO.username(), CONEXAO.password());
            return sql2o.open();
        } catch(Exception ex) {
            ex.printStackTrace();         
        }
        return null;
    }  
    
    public static Connection getConnectionCep() { 
        try {     
            Class.forName(DRIVER); 
            Sql2o sql2o = new Sql2o(CONEXAO.url("_dne"), CONEXAO.username(), CONEXAO.password());
            return sql2o.open();
        } catch(Exception ex) {
            ex.printStackTrace();         
        }
        return null;
    }    
    
    public static Connection getConnectionScc4() { 
        try {     
            Class.forName(DRIVER); 
            Sql2o sql2o = new Sql2o(CONEXAO.url("scc4"), CONEXAO.username(), CONEXAO.password());
            return sql2o.open();
        } catch(Exception ex) {
            ex.printStackTrace();         
        }
        return null;
    }  
    
    public static Connection getConnectionBoxCubo() { 
        try {     
            Class.forName(DRIVER); 
            Sql2o sql2o = new Sql2o(CONEXAO.url("boxcubo"), CONEXAO.username(), CONEXAO.password());
            return sql2o.open();
        } catch(Exception ex) {
            ex.printStackTrace();         
        }
        return null;
    }  
    
}
