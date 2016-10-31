/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Entidade.EDI;
import Entidade.SRO;
import Util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Fernando
 */
public class ContrlEDI {

    public static void inserir(int idCliente, int codigo, String descricao, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "REPLACE INTO edi (idCliente, codigo, descricao) values(?,?,?) ;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idCliente);
            valores.setInt(2, codigo);
            valores.setString(3, descricao);
            valores.executeUpdate();
            valores.close();
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - ContrlEDI", "SQLException", sql, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static void insert_sro_edi(int idCliente, int codigoEDI, int idSRO, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "INSERT INTO sro_edi (idCliente, idSRO, codigo_edi) values(?,?,?) ;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idCliente);
            valores.setInt(2, idSRO);
            valores.setInt(3, codigoEDI);
            valores.executeUpdate();
            valores.close();
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - ContrlEDI", "SQLException", sql, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static void delete_sro_edi(int idCliente, int codigoEDI, int idSRO, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "DELETE FROM sro_edi WHERE idCliente = ? AND idSRO = ? AND codigo_edi = ? ;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idCliente);
            valores.setInt(2, idSRO);
            valores.setInt(3, codigoEDI);
            valores.executeUpdate();
            valores.close();
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - ContrlEDI", "SQLException", sql, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static void deletar(int idCliente, int codigo, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE edi SET ativo = 0 WHERE idCliente = ? AND codigo = ? ;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idCliente);
            valores.setInt(2, codigo);
            valores.executeUpdate();
            valores.close();
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - ContrlEDI", "SQLException", sql, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList<EDI> consultaEDIByCliente(int idCliente, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM edi WHERE idCliente = ? AND ativo = 1 ORDER BY codigo";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idCliente);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<EDI> lista = new ArrayList<EDI>();
            while (result.next()) {
                int idCli = result.getInt("idCliente");
                int codigo = result.getInt("codigo");
                String descricao = result.getString("descricao");
                EDI edi = new EDI(idCli, codigo, descricao);
                lista.add(edi);
            }
            valores.close();
            return lista;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - consultaEDIByCliente", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList<SRO> consultaSRO(String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);

        String sql = "SELECT * FROM sro";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<SRO> lista = new ArrayList<SRO>();
            while (result.next()) {
                int idCli = result.getInt("idSRO");
                int codigo = result.getInt("status");
                String tipo = result.getString("tipo");
                String descricao = result.getString("descricao");
                int agrupamento = result.getInt("agrupamento");

                SRO s = new SRO(idCli, codigo, tipo, descricao, agrupamento);
                lista.add(s);
            }
            valores.close();
            return lista;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - consultaSRO", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int consultaSelected(int idSRO, int idCliente, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM sro AS s LEFT JOIN sro_edi AS se ON s.idSRO = se.idSRO WHERE se.idCliente = ? AND se.idSRO = ?;";

        int flag = -1;
        try {
            PreparedStatement valores = conn.prepareStatement(sql);

            valores.setInt(1, idCliente);
            valores.setInt(2, idSRO);
            //System.out.println(valores.toString());
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                flag = result.getInt("codigo_edi");
            }
            valores.close();

            return flag;

        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - consultaSRO", "SQLException", sql, e.toString());
            return flag;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static Map<String, Integer> consultaSROxEDI(int idCliente, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM sro AS s " +
            " LEFT JOIN sro_edi AS se ON s.idSRO = se.idSRO " +
            " WHERE se.idCliente = ?;";
        try {
            Map<String, Integer> mapOcor = new HashMap<>();
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idCliente);
            ResultSet result = (ResultSet) valores.executeQuery();
            while (result.next()) {
                int idSro = result.getInt("idSRO");
                int idEdi = result.getInt("codigo_edi");                
                String tipoSro = result.getString("tipo");                
                String[] tps = new String[]{tipoSro};
                if(tipoSro.contains(" ")){
                    tps = tipoSro.split(" ");
                }
                
                for (String tp : tps) {
                    mapOcor.put(idSro+";"+tp, idEdi);
                }
                
            }
            valores.close();

            return mapOcor;

        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - consultaSRO", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    public static Map<Integer, String> consultaOcorEDI(int idCliente, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM edi WHERE idCliente = ?;";
        try {
            Map<Integer, String> mapOcor = new HashMap<>();
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idCliente);
            ResultSet result = (ResultSet) valores.executeQuery();
            while (result.next()) {
                int idEdi = result.getInt("codigo");
                String desc = result.getString("descricao");
                mapOcor.put(idEdi, desc);
            }
            valores.close();

            return mapOcor;

        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - consultaSRO", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
}
