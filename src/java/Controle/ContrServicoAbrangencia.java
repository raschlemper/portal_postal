/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Entidade.AmarracaoCep;
import Entidade.ServicoAbrangencia;
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
public class ContrServicoAbrangencia {

    public static void inserir(String servico, int cepInicial, int cepFinal, int suspenso, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "insert into servicos_abrangencia (servico, cep_inicial, cep_final, faixa_suspensa) values(?,?,?,?)";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setString(1, servico);
            valores.setInt(2, cepInicial);
            valores.setInt(3, cepFinal);
            valores.setInt(4, suspenso);
            valores.executeUpdate();
            valores.close();
        } catch (SQLException e) {
            Logger.getLogger(ContrAmarracaoCep.class.getName()).log(Level.WARNING, e.getMessage(), e);
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int excluirByServico(String servico, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        try {
            String sql = "delete from servicos_abrangencia where servico = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, servico);
            int i = pstmt.executeUpdate();
            return i;
        } catch (SQLException e) {
            Logger.getLogger(ContrAmarracaoCep.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int suspenderServico(String servico, int suspenso, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        try {
            String sql = "UPDATE servicos_abrangencia SET servico_suspenso = ? WHERE servico = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, suspenso);
            pstmt.setString(2, servico);
            int i = pstmt.executeUpdate();
            return i;
        } catch (SQLException e) {
            Logger.getLogger(ContrAmarracaoCep.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static int suspenderFaixa(int cepIni, int cepFim, String servico, int suspenso, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        try {
            String sql = "UPDATE servicos_abrangencia SET faixa_suspensa = ? WHERE servico = ? AND cep_inicial = ? AND cep_final ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, suspenso);
            pstmt.setString(2, servico);
            pstmt.setInt(3, cepIni);
            pstmt.setInt(4, cepFim);
            int i = pstmt.executeUpdate();
            return i;
        } catch (SQLException e) {
            Logger.getLogger(ContrAmarracaoCep.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList<ServicoAbrangencia> consultaTodosByServico(String servico, String nomeBD) {
        Connection con = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM servicos_abrangencia WHERE servico = ? ORDER BY cep_inicial";
        try {
            PreparedStatement valores = con.prepareStatement(sql);
            valores.setString(1, servico);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<ServicoAbrangencia> listaCob = new ArrayList<ServicoAbrangencia>();
            for (int i = 0; result.next(); i++) {
                int cepInicial = result.getInt("cep_inicial");
                int cepFinal = result.getInt("cep_final");
                int faixa_suspensa = result.getInt("faixa_suspensa");
                int servico_suspenso = result.getInt("servico_suspenso");
                ServicoAbrangencia cob = new ServicoAbrangencia(servico, cepInicial, cepFinal, faixa_suspensa, servico_suspenso);
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

    public static boolean verificaByCepServico(int cep, String servico, String nomeBD) {
        Connection con = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM servicos_abrangencia WHERE servico = ? AND faixa_suspensa = 0 AND servico_suspenso = 0 AND cep_inicial <= " + cep + " AND cep_final >= " + cep;
        try {
            PreparedStatement valores = con.prepareStatement(sql);
            valores.setString(1, servico);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            Logger.getLogger(ContrAmarracaoCep.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return false;
        } finally {
            Conexao.desconectar(con);
        }
    }
    public static String verificaMDPBxCep(int cep,String nomeBD) {
        Connection con = Conexao.conectar(nomeBD);
        String sql = "SELECT servico FROM servicos_abrangencia WHERE servico IN ('MDPB_L','MDPB_E','MDPB_N') AND faixa_suspensa = 0 AND servico_suspenso = 0 AND cep_inicial <= " + cep + " AND cep_final >= " + cep;
       String ret ="erro";
        try {
            PreparedStatement valores = con.prepareStatement(sql);            
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
               ret = result.getString("servico");                
            }
            if(ret.trim().equals("")){
                ret ="erro";
            }
            
            return ret;
            
        } catch (SQLException e) {
            Logger.getLogger(ContrAmarracaoCep.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return ret;
        } finally {
            Conexao.desconectar(con);
        }
    }
}
