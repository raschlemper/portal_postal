/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Entidade.ServicoECT;
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
 * @author RICARDINHO
 */
public class ContrServicoECT {

    //OK
    public static ArrayList<ServicoECT> consultaServicos(int avista, int atv, String tipo_agencia) throws SQLException {
        Connection conn = Conexao.conectarGeral();
        String sql = "SELECT * FROM servicos_ect";
        if (avista == 0) {
            sql += " WHERE ativo = " + atv + " AND tipo = 'SERVICO' AND tipo_agencia LIKE '%" + tipo_agencia + "%' AND faturar = 1";
        } else if (avista == 1) {
            sql += " WHERE ativo = " + atv + " AND tipo = 'SERVICO' AND tipo_agencia LIKE '%" + tipo_agencia + "%' AND avista >= 1";
        }
        sql += " ORDER BY nomeServico";

        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<ServicoECT> listaStatus = new ArrayList<ServicoECT>();
            while (result.next()) {
                ServicoECT sv = new ServicoECT(result);
                listaStatus.add(sv);
            }
            valores.close();
            return listaStatus;
        } catch (SQLException e) {
            System.out.println(e);
            ContrErroLog.inserir("HOITO - contrNivel", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    //OK
    public static ArrayList<ServicoECT> consultaServicosReversa() throws SQLException {
        Connection conn = Conexao.conectarGeral();
        String sql = "SELECT * FROM servicos_ect"
                + " WHERE ativo = 1 AND codECT_reverso > 0 " 
                + " ORDER BY nomeServico";

        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<ServicoECT> listaStatus = new ArrayList<ServicoECT>();
            while (result.next()) {
                ServicoECT sv = new ServicoECT(result);
                listaStatus.add(sv);
            }
            valores.close();
            return listaStatus;
        } catch (SQLException e) {
            System.out.println(e);
            ContrErroLog.inserir("HOITO - contrNivel", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    //OK
    public static ArrayList<ServicoECT> consultaServicosSigepWEB() throws SQLException {
        Connection conn = Conexao.conectarGeral();
        String sql = "SELECT * FROM servicos_ect"
                + " WHERE ativo = 1 AND idServicoECT > 0 " 
                + " ORDER BY nomeServico";

        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<ServicoECT> listaStatus = new ArrayList<ServicoECT>();
            while (result.next()) {
                ServicoECT sv = new ServicoECT(result);
                listaStatus.add(sv);
            }
            valores.close();
            return listaStatus;
        } catch (SQLException e) {
            System.out.println(e);
            ContrErroLog.inserir("HOITO - contrNivel", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    //OK
    public static ArrayList<ServicoECT> consultaServicosByContrato(ArrayList<Integer> listaCodEctContrato) throws SQLException {
        Connection conn = Conexao.conectarGeral();
        
        String codigosECT = "0";
        for (Integer cod : listaCodEctContrato) {
            codigosECT += ","+cod;
        }
        
        String sql = "SELECT * FROM servicos_ect WHERE ativo = 1 AND tipo = 'SERVICO' " +
            " AND ((avista >= 1 AND grupoServico NOT IN (SELECT grupoServico FROM servicos_ect WHERE codECT IN ("+codigosECT+"))) " +
            " OR (codECT IN ("+codigosECT+")));";

        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<ServicoECT> listaStatus = new ArrayList<ServicoECT>();
            while (result.next()) {
                ServicoECT sv = new ServicoECT(result);
                listaStatus.add(sv);
            }
            valores.close();
            return listaStatus;
        } catch (SQLException e) {
            System.out.println(e);
            ContrErroLog.inserir("HOITO - contrNivel", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    //OK
    public static String consultaGrupoServicoByCodECT(int codECT) {
        Connection conn = Conexao.conectarGeral();
        String sql = "SELECT grupoServico FROM servicos_ect WHERE codECT = " + codECT;
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            String nome = "";
            if (result.next()) {
                nome = result.getString("grupoServico");
            }
            valores.close();
            return nome;
        } catch (SQLException e) {
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    //OK
    public static ArrayList<ServicoECT> consultaServicosPorGrupo() throws SQLException {
        Connection conn = Conexao.conectarGeral();
        String sql = "SELECT * FROM servicos_ect WHERE ativo = 1 AND tipo = 'SERVICO' GROUP BY grupoServico ORDER BY nomeServico";

        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<ServicoECT> listaStatus = new ArrayList<ServicoECT>();
            while (result.next()) {
                ServicoECT sv = new ServicoECT(result);
                listaStatus.add(sv);
            }
            valores.close();
            return listaStatus;
        } catch (SQLException e) {
            System.out.println(e);
            //ContrErroLog.inserir("HOITO - contrNivel", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    //OK
    public static String consultaNomeServicoById(int idServ) throws SQLException {
        Connection conn = Conexao.conectarGeral();
        String sql = "SELECT nomeServico FROM servicos_ect WHERE codECT = " + idServ;
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            String nome = "";
            if (result.next()) {
                nome = result.getString("nomeServico");
            }
            valores.close();
            return nome;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrNivel", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    //OK
    public static ServicoECT consultaAvistaByGrupo(String grupo) {
        Connection conn = Conexao.conectarGeral();
        //O SERVICO DE CARTA SIMPLES NO AVISTA TEM O MESMO CODIGO DA CARTA REG.
        if (grupo.equals("SIMPLES")) {
            grupo = "CARTA";
        }
        if (grupo.equals("ESEDEX")) {
            grupo = "SEDEX";
        }
        String sql = "SELECT * FROM servicos_ect WHERE grupoServico = '" + grupo + "' AND ativo = 1 AND avista >= 1 AND tipo = 'SERVICO' ;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ServicoECT sv = null;
            if (result.next()) {
                sv = new ServicoECT(result);
            }
            valores.close();
            return sv;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrNivel", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    //OK
    public static Map<String, ServicoECT> consultaMapServicosAvista() {
        Connection conn = Conexao.conectarGeral();
        //O SERVICO DE CARTA SIMPLES NO AVISTA TEM O MESMO CODIGO DA CARTA REG.
        String sql = "SELECT * FROM servicos_ect"
                + " WHERE ativo = 1 AND tipo = 'SERVICO' AND avista >= 1 "
                + " ORDER BY nomeServico";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            Map<String, ServicoECT> sv = new HashMap<String, ServicoECT>();
            while (result.next()) {
                String grupoServico = result.getString("grupoServico");
                sv.put(grupoServico, new ServicoECT(result));
            }
            valores.close();
            return sv;
        } catch (SQLException e) {
            System.out.println(e);
            ContrErroLog.inserir("HOITO - contrNivel", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    //OK
    public static Map<Integer, ServicoECT> consultaMapServicosDeContrato() {
        Connection conn = Conexao.conectarGeral();
        //O SERVICO DE CARTA SIMPLES NO AVISTA TEM O MESMO CODIGO DA CARTA REG.
        String sql = "SELECT * FROM servicos_ect"
                + "  WHERE ativo = 1 AND tipo = 'SERVICO' AND tipo_agencia LIKE '%CTR%' AND faturar = 1 "
                + " ORDER BY nomeServico";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            Map<Integer, ServicoECT> sv = new HashMap<Integer, ServicoECT>();
            while (result.next()) {
                int codECT = result.getInt("codECT");
                sv.put(codECT, new ServicoECT(result));
            }
            valores.close();
            return sv;
        } catch (SQLException e) {
            System.out.println(e);
            ContrErroLog.inserir("HOITO - contrNivel", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

}
