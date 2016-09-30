
package Controle;

import Util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContrLogAcoes {
    

    public static int inserir(String nome_bd, String localAcao, String tipoAcao, String acao, int idUsuario, String nomeUsuario) {
        Connection conn = Conexao.conectar(nome_bd);
        String sql = "INSERT INTO log_acoes (idUsuario, nomeUsuario, dataHora, local_acao, tipo_acao, acao_efetuada) VALUES(?,?,NOW(),?,?,?);";
        try {
            PreparedStatement valores = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);;
            valores.setInt(1, idUsuario);
            valores.setString(2, nomeUsuario);
            valores.setString(3, localAcao);
            valores.setString(4, tipoAcao);
            valores.setString(5, acao);

            valores.executeUpdate();
            int autoIncrementKey = 0;
            ResultSet rs = valores.getGeneratedKeys();
            if (rs.next()) {
                autoIncrementKey = rs.getInt(1);
            }
            valores.close();
            return autoIncrementKey;
        } catch (SQLException e) {
            System.out.println("Erro na insercao dos dados do log de acao: \n" + e.getMessage());
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }


}
