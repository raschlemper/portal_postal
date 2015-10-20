/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Emporium.Controle;

import Controle.ContrErroLog;
import Entidade.LogisticaReversa;
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
 * @author scc4
 */
public class ContrLogisticaReversa {
    
    public static int inserir(int idCliente, int range, int cod_ap, String numObjeto, String tipo_ap, String tipo_serv, int ar, float vd, String nome_des, String endereco_des, String numero_des, String complemento_des, String bairro_des, String cidade_des, String uf_des, String cep_des, String email_des, String nome_rem, String endereco_rem, String numero_rem, String complemento_rem, String bairro_rem, String cidade_rem, String uf_rem, String cep_rem, String email_rem, String ddd_rem, String celular_rem, String sms_rem, String userSolicitacao, int qtdObjeto, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "INSERT INTO logistica_reversa (idCliente, range_rev, cod_ap, numObjeto, tipo_ap, tipo_serv, ar, vd, nome_des, endereco_des, numero_des, complemento_des, bairro_des, cidade_des, uf_des, cep_des, email_des,"
                + " nome_rem, endereco_rem, numero_rem, complemento_rem, bairro_rem, cidade_rem, uf_rem, cep_rem, email_rem, ddd_rem, celular_rem, sms_rem, dataSolicitacao, userSolicitacao, cancelado, qtdObjeto)"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),?,-1,?);";
        try {
            PreparedStatement valores = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            valores.setInt(1, idCliente);
            valores.setInt(2, range);
            valores.setInt(3, cod_ap);
            valores.setString(4, numObjeto);
            valores.setString(5, tipo_ap);
            valores.setString(6, tipo_serv);
            valores.setInt(7, ar);
            valores.setFloat(8, vd);
            valores.setString(9, nome_des);
            valores.setString(10, endereco_des);
            valores.setString(11, numero_des);
            valores.setString(12, complemento_des);
            valores.setString(13, bairro_des);
            valores.setString(14, cidade_des);
            valores.setString(15, uf_des);
            valores.setString(16, cep_des);
            valores.setString(17, email_des);
            valores.setString(18, nome_rem);
            valores.setString(19, endereco_rem);
            valores.setString(20, numero_rem);
            valores.setString(21, complemento_rem);
            valores.setString(22, bairro_rem);
            valores.setString(23, cidade_rem);
            valores.setString(24, uf_rem);
            valores.setString(25, cep_rem);
            valores.setString(26, email_rem);
            valores.setString(27, ddd_rem);
            valores.setString(28, celular_rem);
            valores.setString(29, sms_rem);
            valores.setString(30, userSolicitacao);
            valores.setInt(31, qtdObjeto);
            valores.executeUpdate();
            
            int autoIncrementKey = 0;
            ResultSet rs = valores.getGeneratedKeys();
            if (rs.next()) {
                autoIncrementKey = rs.getInt(1);
            }
            valores.close();
            
            return autoIncrementKey;
        } catch (SQLException e) {
            Logger.getLogger(ContrLogisticaReversa.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return 0;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    
    public static boolean alterarCodigoAP(int codAP, int cancelado, int idRev, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE logistica_reversa SET cod_ap = ?, cancelado = ? WHERE id = ? ";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, codAP);
            valores.setInt(2, cancelado);
            valores.setInt(3, idRev);
            valores.executeUpdate();
            valores.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            //ContrErroLog.inserir("HOITO - contrContato", "SQLException", sql, e.toString());
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static boolean alterarSituacao(int idRev, String status, String descStatus, String obj, String dataStatus, String horaStatus, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE logistica_reversa SET status = ?, descricaoStatus = ?, dataStatus = ?, horaStatus = ?, numObjeto = ? WHERE id = ? ";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setString(1, status);
            valores.setString(2, descStatus);
            valores.setString(3, dataStatus);
            valores.setString(4, horaStatus);
            valores.setString(5, obj);
            valores.setInt(6, idRev);
            valores.executeUpdate();
            valores.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            //ContrErroLog.inserir("HOITO - contrContato", "SQLException", sql, e.toString());
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static boolean alterarCancelado(int cancelado, int idRev, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE logistica_reversa SET cancelado = ? WHERE id = ? ";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, cancelado);
            valores.setInt(2, idRev);
            valores.executeUpdate();
            valores.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    
    public static ArrayList<LogisticaReversa> consultaReversasByCliente(int idCliente, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM logistica_reversa WHERE idCliente = "+idCliente;
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<LogisticaReversa> lista = new ArrayList<LogisticaReversa>();
            while (result.next()) {
                lista.add(new LogisticaReversa(result));
            }
            
            return lista;
        } catch (Exception e) {
            System.out.println(e);
            //ContrErroLog.inserir("HOITO - contrCliente", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static ArrayList<String> consultaReversasPendByCliente(int idCliente, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT cod_ap FROM logistica_reversa WHERE idCliente = "+idCliente+" AND cancelado = 0";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<String> lista = new ArrayList<String>();
            while (result.next()) {
                lista.add(result.getString("cod_ap"));
            }
            
            return lista;
        } catch (Exception e) {
            System.out.println(e);
            //ContrErroLog.inserir("HOITO - contrCliente", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static LogisticaReversa consultaReversaById(int idRev, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM logistica_reversa WHERE id = "+ idRev;
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            LogisticaReversa rev = null;
            if (result.next()) {
                rev = new LogisticaReversa(result);
            }
            
            return rev;
        } catch (Exception e) {
            System.out.println(e);
            //ContrErroLog.inserir("HOITO - contrCliente", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
}
