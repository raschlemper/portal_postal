package Controle;

import Entidade.ClienteSMTP;
import Util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ContrClienteSMTP {

    public static void insereClienteSMTP(ClienteSMTP clSMTP, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "INSERT INTO cliente_smtp (idCliente, idDepartamento, tipo_servidor, envia_remetente, envia_destinatario, smtp, porta, is_secure, tipo_seguranca, porta_ssl,  user,  senha) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        System.out.println(sql);
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            
            valores.setInt(1, clSMTP.getIdCliente());
            valores.setString(2, clSMTP.getIdDepartamento());
            valores.setInt(3, clSMTP.getTipo_servidor());
            valores.setString(4, clSMTP.getEnvia_remetente());
            valores.setInt(5, clSMTP.getEnvia_destinatario());
            valores.setString(6, clSMTP.getSmtp());
            valores.setInt(7, clSMTP.getPorta());
            valores.setInt(8, clSMTP.getIs_secure());
            valores.setString(9, clSMTP.getTipo_seguranca());
            valores.setInt(10, clSMTP.getPorta_ssl());
            valores.setString(11, clSMTP.getUser());
            valores.setString(12, clSMTP.getSenha());
           
            valores.executeUpdate();
            valores.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            ContrErroLog.inserir("insereClienteSMTP", "SQLException", sql, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList<ClienteSMTP> consultaCadastroSMTP(int idCliente, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT *FROM cliente_smtp WHERE idCliente = " + idCliente + ";";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<ClienteSMTP> lista = new ArrayList<ClienteSMTP>();
            while (result.next()) {
                ClienteSMTP ls = new ClienteSMTP(result);
                lista.add(ls);
            }
            valores.close();
            return lista;
        } catch (SQLException e) {
            ContrErroLog.inserir("consultaCadastroSMTP", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static void excluirClienteSMTP(int id, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "DELETE FROM cliente_smtp WHERE id = ? ";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, id);
            valores.executeUpdate();
            valores.close();
        } catch (SQLException e) {
            ContrErroLog.inserir("excluirClienteSMTP", "SQLException", sql, e.toString());
            
        } finally {
            Conexao.desconectar(conn);
        }
    }

}
