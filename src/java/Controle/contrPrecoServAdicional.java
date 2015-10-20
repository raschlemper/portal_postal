/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Entidade.PrecoServAdicional;
import Util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author SCC4
 */
public class contrPrecoServAdicional {

    public static PrecoServAdicional consultaSigla(String sigla, String data, String tipo, String nomeBD) throws SQLException {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM servicos_adicionais"
                + " WHERE data < '" + data + "' AND sigla = '" + sigla + "' AND tipo = '" + tipo + "'"
                + " ORDER BY data DESC LIMIT 1;";

        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();

            if (result.next()) {
                int id = result.getInt("id");
                String sigla2 = result.getString("sigla");
                String desc = result.getString("desc");
                float valor = result.getFloat("valor");
                Date data2 = result.getDate("data");
                String tipo2 = result.getString("tipo");

                return new PrecoServAdicional(id, sigla2, desc, valor, data2, tipo2);
            } else {
                return null;
            }
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrPrecoServAdicional", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
}
