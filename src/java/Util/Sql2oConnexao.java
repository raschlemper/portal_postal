package Util;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class Sql2oConnexao extends Conexao {
    
    public static Connection conect(String nome) {
        conectar(nome);
        Sql2o sql2o = new Sql2o(URL, USERNAME, PASSWORD);
        return sql2o.open();    
    }    
    
}
