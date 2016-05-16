/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Emporium.Controle;

import Controle.ContrErroLog;
import Entidade.Clientes;
import Entidade.Usuario;
import Util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Administrador
 */
public class ContrLoginEmporium {

    public static Clientes login(String login, String senha, String nomeBD) {
                
        Connection con = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM cliente AS c"
                + " JOIN cliente_usuarios AS u"
                + " ON u.codigo = c.codigo"
                + " WHERE u.login = ? AND u.senha = ?;";
        try {
            PreparedStatement valores = con.prepareStatement(sql);
            valores.setString(1, login);
            valores.setString(2, senha);
            
            ResultSet result = (ResultSet) valores.executeQuery();

            Clientes cli = null;
            if (result.next()) {
                cli = new Clientes(result);
            }

            return cli;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrLoginEmporium", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(con);
        }
    }

    public static ArrayList<Usuario> verificaOperadores(String login) {
        Connection con = Conexao.conectarGeral();

        String sql = "SELECT * FROM operador_master "
                + " WHERE login = ?;";
        try {
            PreparedStatement valores = con.prepareStatement(sql);
            valores.setString(1, login);
            
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<Usuario> ls = new ArrayList<>();

            while (result.next()) {
                String nomAgencia = result.getString("nomeAGF");
                String cnpj = result.getString("cnpj");
                int idEmpresa = result.getInt("idEmpresa");

                ls.add(new Usuario(nomAgencia, cnpj, idEmpresa));
            }

            return ls;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrLoginEmporium", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(con);
        }
    }

}
