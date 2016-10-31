/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Entidade.AmarracaoCep;
import Util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RICARDINHO
 */
public class ContrAmarracaoServico {
    
    public static void inserir(int idAmarracao, String servico, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "insert into amarracao_servico (idAmarracao, servico) values(?,?)";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idAmarracao);
            valores.setString(2, servico);
            valores.executeUpdate();
            valores.close();
        } catch (SQLException e) {
            Logger.getLogger(ContrAmarracaoCep.class.getName()).log(Level.WARNING, e.getMessage(), e);
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int excluirByIdAmarracao(int idAmarracao, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        try {
            String sql = "delete from amarracao_servico where idAmarracao = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idAmarracao);
            int i = pstmt.executeUpdate();
            return i;
        } catch (SQLException e) {
            Logger.getLogger(ContrAmarracaoCep.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    
    public static ArrayList<String> consultaTodosServicosByIdAmarracao(int idAmarracao, String nomeBD) {
        Connection con = Conexao.conectar(nomeBD);
        String sql = "SELECT servico FROM amarracao_servico WHERE idAmarracao = ?";
        try {
            PreparedStatement valores = con.prepareStatement(sql);
            valores.setInt(1, idAmarracao);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<String> listaCob = new ArrayList<String>();
            for (int i = 0; result.next(); i++) {
                listaCob.add(result.getString("servico"));
            }
            return listaCob;
        } catch (SQLException e) {
            Logger.getLogger(ContrAmarracaoCep.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return null;
        } finally {
            Conexao.desconectar(con);
        }
    }
        
    public static String consultaIdAmarracaoByServico(int idAmar, String serv, String nomeBD) {
        Connection con = Conexao.conectar(nomeBD);
        String sql = "SELECT DISTINCT(idAmarracao) FROM amarracao_servico WHERE servico IN ("+serv+") AND idAmarracao <> "+idAmar;
        try {
            PreparedStatement valores = con.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            String res = "";
            for (int i = 0; result.next(); i++) {
                int idAmarracao = result.getInt("idAmarracao");
                if(i == 0){
                    res += idAmarracao;
                } else {
                    res += ", "+idAmarracao;
                }
            }
            return res;
        } catch (SQLException e) {
            Logger.getLogger(ContrAmarracaoCep.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return null;
        } finally {
            Conexao.desconectar(con);
        }
    }

}
