
package caixapostal.componentes;
import java.sql.Connection;

public class Conexao {

    public static Connection conn;

    public static Connection getConnection(String nomeDB) {
        if (conn != null) {
            return conn;
        }
        conn = Util.Conexao.conectar(nomeDB);
        return conn;
    }

}
