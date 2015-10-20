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
 * @author SCC4
 */
public class ContrAmarracaoCep {

    public static void inserir(int idAmarracao, int cepInicial, int cepFinal, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "insert into amarracao_cep (idAmarracao, cepInicial, cepFinal) values(?,?,?)";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idAmarracao);
            valores.setInt(2, cepInicial);
            valores.setInt(3, cepFinal);
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
            String sql = "delete from amarracao_cep where idAmarracao = ?;";
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

    public static ArrayList<AmarracaoCep> consultaTodosByIdAmarracao(int idAmarracao, String nomeBD) {
        Connection con = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM amarracao_cep WHERE idAmarracao = ? ORDER BY cepInicial";
        try {
            PreparedStatement valores = con.prepareStatement(sql);
            valores.setInt(1, idAmarracao);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<AmarracaoCep> listaCob = new ArrayList<AmarracaoCep>();
            for (int i = 0; result.next(); i++) {
                int idCoberturaAmarracao = result.getInt("idCoberturaAmarracao");
                int cepInicial = result.getInt("cepInicial");
                int cepFinal = result.getInt("cepFinal");
                AmarracaoCep cob = new AmarracaoCep(idCoberturaAmarracao, idAmarracao, cepInicial, cepFinal);
                listaCob.add(cob);
            }
            return listaCob;
        } catch (SQLException e) {
            Logger.getLogger(ContrAmarracaoCep.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return null;
        } finally {
            Conexao.desconectar(con);
        }
    }

    public static ArrayList<AmarracaoCep> consultaTodos(String nomeBD) {
        Connection con = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM amarracao_cep";
        try {
            PreparedStatement valores = con.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<AmarracaoCep> listaCob = new ArrayList<AmarracaoCep>();
            for (int i = 0; result.next(); i++) {
                int idAmarracao = result.getInt("idAmarracao");
                int idCoberturaAmarracao = result.getInt("idCoberturaAmarracao");
                int cepInicial = result.getInt("cepInicial");
                int cepFinal = result.getInt("cepFinal");
                AmarracaoCep cob = new AmarracaoCep(idCoberturaAmarracao, idAmarracao, cepInicial, cepFinal);
                listaCob.add(cob);
            }
            return listaCob;
        } catch (SQLException e) {
            Logger.getLogger(ContrAmarracaoCep.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return null;
        } finally {
            Conexao.desconectar(con);
        }
    }

    public static boolean verificaExistenciaFaixaCep(int cepInicial, int cepFinal, int idAmarracao, String listIdAmarracao, String nomeBD){
        Connection con = Conexao.conectar(nomeBD);
        String sql = "SELECT idCoberturaAmarracao FROM amarracao_cep" +
                " WHERE ((cepInicial <= ? AND cepFinal >= ?) OR (cepInicial <= ? AND cepFinal >= ?) OR (cepInicial >= ? AND cepFinal <= ?)) AND idAmarracao IN ("+listIdAmarracao+")";
        if(idAmarracao > 0){
            sql+=" AND idAmarracao <> "+idAmarracao;
        }

        try {
            PreparedStatement valores = con.prepareStatement(sql);
            valores.setInt(1, cepInicial);
            valores.setInt(2, cepInicial);
            valores.setInt(3, cepFinal);
            valores.setInt(4, cepFinal);
            valores.setInt(5, cepInicial);
            valores.setInt(6, cepFinal);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            Logger.getLogger(ContrAmarracaoCep.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return false;
        } finally {
            Conexao.desconectar(con);
        }
    }

}
