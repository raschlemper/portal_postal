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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author RICARDINHO
 */
public class ContrServicoECT {

    public static ArrayList<ServicoECT> consultaServicos(int avista, int atv, String tipo_agencia) throws SQLException {
        Connection conn = Conexao.conectarGeral();
        String sql = "SELECT * FROM servicos_ect";
        if (avista == 0) {
            sql += " WHERE ativo = " + atv + " AND tipo_agencia LIKE '%" + tipo_agencia + "%' AND (avista = 0 OR avista = 2)";
        } else if (avista == 1) {
            sql += " WHERE ativo = " + atv + " AND tipo_agencia LIKE '%" + tipo_agencia + "%' AND (avista = 1 OR avista = 2)";
        }
        sql += " ORDER BY nomeServico";

        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<ServicoECT> listaStatus = new ArrayList<ServicoECT>();
            while (result.next()) {
                int codECT = result.getInt("codECT");
                int codECT_reversa = result.getInt("codECT_reverso");
                int idServicoECT = result.getInt("idServicoECT");
                String nomeServico = result.getString("nomeServico");
                String nomeSimples = result.getString("nomeSimples");
                String grupoServico = result.getString("grupoServico");
                int vista = result.getInt("avista");
                int ativo = result.getInt("ativo");
                String tipo_agf = result.getString("tipo_agencia");

                ServicoECT sv = new ServicoECT(codECT, codECT_reversa, nomeServico, grupoServico, vista, ativo, nomeSimples, idServicoECT, tipo_agf);
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

    public static ArrayList<ServicoECT> consultaServicosByContrato(ArrayList<Integer> listaCodEctContrato) throws SQLException {
        Connection conn = Conexao.conectarGeral();
        
        String codigosECT = "0";
        for (Integer cod : listaCodEctContrato) {
            codigosECT += ","+cod;
        }
        
        String sql = "SELECT * FROM servicos_ect WHERE ativo = 1 AND tipo_agencia LIKE '%AGF%' " +
            " AND ((avista <> 0 AND grupoServico NOT IN (SELECT grupoServico FROM servicos_ect WHERE codECT IN ("+codigosECT+"))) " +
            " OR (codECT IN ("+codigosECT+")));";

        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<ServicoECT> listaStatus = new ArrayList<ServicoECT>();
            while (result.next()) {
                int codECT = result.getInt("codECT");
                int codECT_reversa = result.getInt("codECT_reverso");
                int idServicoECT = result.getInt("idServicoECT");
                String nomeServico = result.getString("nomeServico");
                String nomeSimples = result.getString("nomeSimples");
                String grupoServico = result.getString("grupoServico");
                int vista = result.getInt("avista");
                int ativo = result.getInt("ativo");
                String tipo_agf = result.getString("tipo_agencia");

                ServicoECT sv = new ServicoECT(codECT, codECT_reversa, nomeServico, grupoServico, vista, ativo, nomeSimples, idServicoECT, tipo_agf);
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

    public static ArrayList<ServicoECT> consultaServicosPorGrupo() throws SQLException {
        Connection conn = Conexao.conectarGeral();
        String sql = "SELECT grupoServico, nomeSimples FROM servicos_ect WHERE ativo = 1 GROUP BY grupoServico ORDER BY nomeServico";

        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<ServicoECT> listaStatus = new ArrayList<ServicoECT>();
            while (result.next()) {
                int codECT = 0;
                int idServicoECT = 0;
                String nomeServico = result.getString("nomeSimples");
                String grupoServico = result.getString("grupoServico");
                int vista = 0;
                int ativo = 1;

                ServicoECT sv = new ServicoECT(codECT, nomeServico, grupoServico, vista, ativo, nomeServico, idServicoECT);
                listaStatus.add(sv);
            }
            valores.close();
            return listaStatus;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrNivel", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

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

    public static ServicoECT consultaAvistaByGrupo(String grupo, String tipo) {
        Connection conn = Conexao.conectarGeral();
        //O SERVICO DE CARTA SIMPLES NO AVISTA TEM O MESMO CODIGO DA CARTA REG.
        if (grupo.equals("SIMPLES")) {
            grupo = "CARTA";
        }
        if (grupo.equals("ESEDEX")) {
            grupo = "SEDEX";
        }
        String sql = "SELECT * FROM servicos_ect WHERE grupoServico = '" + grupo + "' AND ativo = 1 AND (avista = 1 OR avista = 2) AND tipo LIKE '%" + tipo + "%' ;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ServicoECT sv = null;
            if (result.next()) {
                int codECT = result.getInt("codECT");
                int idServicoECT = result.getInt("idServicoECT");
                String nomeServico = result.getString("nomeServico");
                String nomeSimples = result.getString("nomeSimples");
                String grupoServico = result.getString("grupoServico");
                int vista = result.getInt("avista");
                int ativo = result.getInt("ativo");

                sv = new ServicoECT(codECT, nomeServico, grupoServico, vista, ativo, nomeSimples, idServicoECT);
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

    public static Map<String, ServicoECT> consultaMapServicosAvista() {
        Connection conn = Conexao.conectarGeral();
        //O SERVICO DE CARTA SIMPLES NO AVISTA TEM O MESMO CODIGO DA CARTA REG.
        String sql = "SELECT * FROM servicos_ect"
                + " WHERE ativo = 1 AND tipo_agencia LIKE '%AGF%' AND (avista = 1 OR avista = 2)"
                + " ORDER BY nomeServico";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            Map<String, ServicoECT> sv = new HashMap<String, ServicoECT>();
            while (result.next()) {
                int codECT = result.getInt("codECT");
                int idServicoECT = result.getInt("idServicoECT");
                String nomeServico = result.getString("nomeServico");
                String nomeSimples = result.getString("nomeSimples");
                String grupoServico = result.getString("grupoServico");
                int vista = result.getInt("avista");
                int ativo = result.getInt("ativo");                
                sv.put(grupoServico, new ServicoECT(codECT, nomeServico, grupoServico, vista, ativo, nomeSimples, idServicoECT));
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
