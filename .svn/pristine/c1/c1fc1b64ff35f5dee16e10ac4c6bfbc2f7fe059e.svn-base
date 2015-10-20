/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Emporium.Controle;

import Controle.ContrErroLog;
import Entidade.Endereco;
import Entidade.TelegramaPostal;
import Util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Fernando
 */
public class ContrTelegramaPostal {
    
    
    public static void inserir(int idCliente, int idDepartamento, String departamento, int tipoEnvio, String dataAgendado, String adicionais, String envioCopia, String mensagem, Endereco endRem, Endereco endDest, String nomeUser, String emailCopia, String nomeBD){        
        Connection conn = Conexao.conectar(nomeBD);
        
        String sql = "INSERT INTO telegrama_postal " +
                    " (idCliente, idDepartamento, departamento, dataHora, dataHoraAgendado, tipoEnvio, adicionais, envioCopia, mensagem," +
                    " nomeRem, cepRem, enderecoRem, numeroRem, complementoRem, bairroRem, cidadeRem, ufRem," +
                    " nomeDes, cepDes, enderecoDes, numeroDes, complementoDes, bairroDes, cidadeDes, ufDes, userAgendado, emailCopia)" +
                    " VALUES (?, ?, ?, NOW(), '"+dataAgendado+"', ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idCliente);
            valores.setInt(2, idDepartamento);
            valores.setString(3, departamento);
            valores.setInt(4, tipoEnvio);
            valores.setString(5, adicionais);
            valores.setString(6, envioCopia);
            valores.setString(7, mensagem);           
            valores.setString(8, endRem.getNome());
            valores.setString(9, endRem.getCep());
            valores.setString(10, endRem.getLogradouro());
            valores.setString(11, endRem.getNumero());
            valores.setString(12, endRem.getComplemento());
            valores.setString(13, endRem.getBairro());
            valores.setString(14, endRem.getCidade());
            valores.setString(15, endRem.getUf());         
            valores.setString(16, endDest.getNome());
            valores.setString(17, endDest.getCep());
            valores.setString(18, endDest.getLogradouro());
            valores.setString(19, endDest.getNumero());
            valores.setString(20, endDest.getComplemento());
            valores.setString(21, endDest.getBairro());
            valores.setString(22, endDest.getCidade());
            valores.setString(23, endDest.getUf());    
            valores.setString(24, nomeUser);    
            valores.setString(25, emailCopia);    
            valores.execute();
            valores.close();
        } catch (SQLException e) {
            System.out.println(e);
            //JOptionPane.showMessageDialog(null, "Falha ao inserir - inserePLP no BD:\n- " + e);
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static void updateEnviado(int id, String nomeUser, String sro, String nomeBD){        
        Connection conn = Conexao.conectar(nomeBD);        
        String sql = "UPDATE telegrama_postal SET status = 1, userEnviado = ?, sro = ?, dataHoraEnviado = NOW() WHERE id = ? ";        
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setString(1, nomeUser);
            valores.setString(2, sro);
            valores.setInt(3, id);
            valores.execute();
            valores.close();
        } catch (SQLException e) {
            System.out.println(sql);
            //JOptionPane.showMessageDialog(null, "Falha ao inserir - inserePLP no BD:\n- " + e);
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static void delete(int id, String nomeBD){        
        Connection conn = Conexao.conectar(nomeBD);        
        String sql = "DELETE FROM telegrama_postal WHERE id = ? ";        
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, id);
            valores.execute();
            valores.close();
        } catch (SQLException e) {
            System.out.println(sql);
            //JOptionPane.showMessageDialog(null, "Falha ao inserir - inserePLP no BD:\n- " + e);
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static ArrayList<TelegramaPostal> consultaByData(int idCli, String dataHora, String dataHora2, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM telegrama_postal WHERE idCliente = " + idCli + " AND DATE(dataHora) BETWEEN '"+dataHora+"' AND  '"+dataHora2+"';";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<TelegramaPostal> lista = new ArrayList<TelegramaPostal>();
            while(result.next()) {
                lista.add(new TelegramaPostal(result));
            }
            valores.close();
            return lista;
        } catch (SQLException e) {
            System.out.println(e);
            ContrErroLog.inserir("PortalPostal - ContrImpressaoPLP", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static ArrayList<TelegramaPostal> consultaNaoEnviados(String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM telegrama_postal WHERE status = 0;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<TelegramaPostal> lista = new ArrayList<TelegramaPostal>();
            while(result.next()) {
                lista.add(new TelegramaPostal(result));
            }
            valores.close();
            return lista;
        } catch (SQLException e) {
            System.out.println(e);
            ContrErroLog.inserir("PortalPostal - ContrImpressaoPLP", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static TelegramaPostal consultaById(int id, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM telegrama_postal WHERE id = "+id+";";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            TelegramaPostal t = null;
            if(result.next()) {
                t = new TelegramaPostal(result);
            }
            valores.close();
            return t;
        } catch (SQLException e) {
            System.out.println(e);
            ContrErroLog.inserir("PortalPostal - ContrImpressaoPLP", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static ArrayList<TelegramaPostal> consultaEnviados(String dataHora, String dataHora2, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM telegrama_postal WHERE status = 1 AND DATE(dataHora) BETWEEN '"+dataHora+"' AND  '"+dataHora2+"' ;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<TelegramaPostal> lista = new ArrayList<TelegramaPostal>();
            while(result.next()) {
                lista.add(new TelegramaPostal(result));
            }
            valores.close();
            return lista;
        } catch (SQLException e) {
            System.out.println(e);
            ContrErroLog.inserir("PortalPostal - ContrImpressaoPLP", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
}
