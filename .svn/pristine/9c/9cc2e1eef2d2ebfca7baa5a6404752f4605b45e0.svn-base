/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controle;

import Entidade.Movimentacao;
import Util.Conexao;
import Util.ServicosECT;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Fernando
 */
public class ContrDashboard {
    
    public static Map<String, Movimentacao> consultaFaturamentoPorServico(String dataIni, String dataFim, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT " +
                "   codigoEct, SUM(quantidade) AS qtd, SUM(valorServico) AS vlr " +
                " FROM " +
                "   movimentacao " +
                " WHERE " +
                "   dataPostagem BETWEEN '"+dataIni+"' AND '"+dataFim+"' " +
                "   AND codigoEct <> '' " +
                "   AND LENGTH(codigoEct) <= 5	" +
                " GROUP BY " +
                "   codigoEct;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            Map<String, Movimentacao> mapMov = new HashMap<String, Movimentacao>();
            while (result.next()) {
                Date dataPostagem = null;
                String idCliente = "";
                String descServ = "";
                String codigoECT = result.getString("codigoEct");
                float quantidade = result.getFloat("qtd");
                float valorServ = result.getInt("vlr");
                
                ServicosECT s = new ServicosECT();
                String tipoServ = s.getTipoServicoByCodECT(codigoECT);
                if(mapMov.containsKey(tipoServ)){
                    Movimentacao mov = mapMov.get(tipoServ);
                    mov.setValorServico(mov.getValorServico()+valorServ);
                    mov.setQuantidade(mov.getQuantidade()+quantidade);                    
                    mapMov.put(tipoServ, mov);
                }else{                    
                    Movimentacao mov = new Movimentacao(dataPostagem, descServ, valorServ, quantidade, idCliente, codigoECT);
                    mapMov.put(tipoServ, mov);                    
                }
            }
            valores.close();
            return mapMov;
        } catch (SQLException e) {
            ContrErroLog.inserir("PortalPostal - ContrDashboard", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static ArrayList<Movimentacao> consultaClientesInativos(String dataIni, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM " +
                "     (SELECT codCliente, nomeFantasia, MAX(dataPostagem) AS dataMax " +
                "     FROM movimentacao " +
 "     LEFT JOIN cliente ON codCliente = codigo "
                +                "     WHERE nomeFantasia IS NOT NULL AND ativo = 1 " +
                "     GROUP BY codCliente) AS res " +
                " WHERE res.dataMax < '"+dataIni+"' " +
                " ORDER BY dataMax;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<Movimentacao> listaMov = new ArrayList<Movimentacao>();
            while (result.next()) {
                Date dataPostagem = result.getDate("dataMax");
                String idCliente = result.getString("codCliente");
                String descServ = result.getString("nomeFantasia");
                String codigoECT = "";
                float quantidade = 0;
                float valorServ = 0;
                Movimentacao mov = new Movimentacao(dataPostagem, descServ, valorServ, quantidade, idCliente, codigoECT);
                listaMov.add(mov);
            }
            valores.close();
            return listaMov;
        } catch (SQLException e) {
            ContrErroLog.inserir("PortalPostal - ContrDashboard", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static ArrayList<Movimentacao> consultaEtiquetasPorClientes(String dataIni, String dataFim, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT idCliente, nomeFantasia, COUNT(*) AS qtd " +
                " FROM pre_venda " +
                " LEFT JOIN cliente ON idCliente = codigo " +
                " WHERE nomeFantasia IS NOT NULL " +
                " AND impresso = 1 " +
                " AND dataImpressao IS NOT NULL " +
                " AND DATE(dataImpressao) BETWEEN '"+dataIni+"' AND '"+dataFim+"' " +
                " GROUP BY idCliente " +
                " ORDER BY qtd DESC";        
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<Movimentacao> listaMov = new ArrayList<Movimentacao>();
            while (result.next()) {
                Date dataPostagem = null;
                String idCliente = result.getString("idCliente");
                String descServ = result.getString("nomeFantasia");
                String codigoECT = "";
                float quantidade = result.getFloat("qtd");
                float valorServ = 0;
                Movimentacao mov = new Movimentacao(dataPostagem, descServ, valorServ, quantidade, idCliente, codigoECT);
                listaMov.add(mov);
            }
            valores.close();
            return listaMov;
        } catch (SQLException e) {
            ContrErroLog.inserir("PortalPostal - ContrDashboard", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static ArrayList<Movimentacao> consultaFaturaPorClientes(String dataIni, String dataFim, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT codCliente, SUM(valorServico) AS vlr, SUM(quantidade) AS qtd, nomeFantasia " +
                    " FROM movimentacao " +
                    " LEFT JOIN cliente ON codCliente = codigo " +
                    " WHERE nomeFantasia IS NOT NULL " +
                    " AND dataPostagem IS NOT NULL " +
                    " AND dataPostagem BETWEEN '"+dataIni+"' AND '"+dataFim+"' " +
                    " GROUP BY codCliente " +
                    " ORDER BY vlr DESC;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<Movimentacao> listaMov = new ArrayList<Movimentacao>();
            while (result.next()) {
                Date dataPostagem = null;
                String idCliente = result.getString("codCliente");
                String descServ = result.getString("nomeFantasia");
                String codigoECT = "";
                float quantidade = result.getFloat("qtd");
                float valorServ = result.getFloat("vlr");
                Movimentacao mov = new Movimentacao(dataPostagem, descServ, valorServ, quantidade, idCliente, codigoECT);
                listaMov.add(mov);
            }
            valores.close();
            return listaMov;
        } catch (SQLException e) {
            ContrErroLog.inserir("PortalPostal - ContrDashboard", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static ArrayList<Movimentacao> consultaFaturaPorData(String dataIni, String dataFim, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT dataPostagem, SUM(valorServico) AS vlr, SUM(quantidade) AS qtd " +
                " FROM movimentacao " +
                " WHERE dataPostagem IS NOT NULL " +
                " AND dataPostagem BETWEEN '"+dataIni+"' AND '"+dataFim+"' " +
                " GROUP BY dataPostagem " +
                " ORDER BY dataPostagem;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<Movimentacao> listaMov = new ArrayList<Movimentacao>();
            while (result.next()) {
                Date dataPostagem = result.getDate("dataPostagem");
                String idCliente = "";
                String descServ = "";
                String codigoECT = "";
                float quantidade = result.getFloat("qtd");
                float valorServ = result.getFloat("vlr");
                Movimentacao mov = new Movimentacao(dataPostagem, descServ, valorServ, quantidade, idCliente, codigoECT);
                listaMov.add(mov);
            }
            valores.close();
            return listaMov;
        } catch (SQLException e) {
            ContrErroLog.inserir("PortalPostal - ContrDashboard", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static ArrayList<Movimentacao> consultaFaturaPorMes(String dataIni, String dataFim, String nomeBD) {
        Connection conn = (Connection) Conexao.conectar(nomeBD);
        String sql = "SELECT MONTH(dataPostagem) AS mes, SUM(valorServico) AS vlr, SUM(quantidade) AS qtd " +
                " FROM movimentacao " +
                " WHERE dataPostagem IS NOT NULL " +
                " AND dataPostagem BETWEEN '"+dataIni+"' AND '"+dataFim+"' " +
                " GROUP BY mes " +
                " ORDER BY mes;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<Movimentacao> listaMov = new ArrayList<Movimentacao>();
            while (result.next()) {
                Date dataPostagem = null;
                String idCliente = "";
                String descServ = result.getString("mes");
                String codigoECT = "";
                float quantidade = result.getFloat("qtd");
                float valorServ = result.getFloat("vlr");
                Movimentacao mov = new Movimentacao(dataPostagem, descServ, valorServ, quantidade, idCliente, codigoECT);
                listaMov.add(mov);
            }
            valores.close();
            return listaMov;
        } catch (SQLException e) {
            ContrErroLog.inserir("PortalPostal - ContrDashboard", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    /*
    public static void main(String[] args) {
        System.out.println(new Date() +" - inicio");
        consultaFaturamentoPorServico("'2015-07-03'","'2015-03-01'","pp_06895434000183");
        System.out.println(new Date());
        consultaFaturaPorData("'2015-07-03'","'2015-03-01'","pp_06895434000183");
        System.out.println(new Date());
        consultaFaturaPorMes("'2015-07-03'","'2015-03-01'","pp_06895434000183");
        System.out.println(new Date());
        consultaFaturaPorClientes("'2015-07-03'","'2015-03-01'","pp_06895434000183");
        System.out.println(new Date());
        consultaEtiquetasPorClientes("'2015-07-03'","'2015-03-01'","pp_06895434000183");
        System.out.println(new Date());
        consultaClientesInativos("'2015-07-03'","pp_06895434000183");
        System.out.println(new Date() +" - fim!");
    }*/
}
