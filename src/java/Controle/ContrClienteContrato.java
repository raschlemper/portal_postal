/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Util.Conexao;
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
 * @author RICARDINHO
 */
public class ContrClienteContrato {
    
    
    public static void insereContrato(int idCliente, int codECT, String grupo, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "INSERT INTO cliente_contrato (idCliente, codECT, grupoServico) VALUES(?,?,?)";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idCliente);
            valores.setInt(2, codECT);
            valores.setString(3, grupo);
            valores.executeUpdate();
            valores.close();
        } catch (SQLException e) {
            e.printStackTrace();
            //ContrErroLog.inserir("HOITO - contrContato", "SQLException", sql, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static boolean limparContrato(int idCliente, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "DELETE FROM cliente_contrato WHERE idCliente = ? ";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idCliente);
            valores.executeUpdate();
            valores.close();
            return true;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrContato", "SQLException", sql, e.toString());
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static boolean limparContratoByServ(int idCliente, String servicos, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "DELETE FROM cliente_contrato WHERE idCliente = ? AND grupoServico IN ("+servicos+") ";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idCliente);
            valores.executeUpdate();
            valores.close();
            return true;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrContato", "SQLException", sql, e.toString());
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static boolean alterarTemContrato( int temContrato, String nomeContrato, String numContrato, int anoContrato, String ufContrato, int idCliente, String cartaoPostagem, String codAdm, Date dtVigencia, int codDir, int statusCartao, String nomeCliSara, String loginCorreio, String senhaCorreio, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE cliente"
                + " SET temContrato = ?, nomeContrato = ?, numContrato = ?, anoContrato = ?, ufContrato = ?, cartaoPostagem = ?, codAdministrativo = ?, dtVigenciaFimContrato = ?,"
                + " codDiretoria = ?, statusCartaoPostagem = ?, nomeClienteSara = ?, login_correio = ?, senha_correio = ?, erro_atualizacao = 0, dataHoraAtualizacao = NOW()"
                + " WHERE codigo = ? ";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, temContrato);
            valores.setString(2, nomeContrato);
            valores.setString(3, numContrato);
            valores.setInt(4, anoContrato);
            valores.setString(5, ufContrato);
            valores.setString(6, cartaoPostagem);
            valores.setString(7, codAdm);
            valores.setDate(8, new java.sql.Date(dtVigencia.getTime()));
            valores.setInt(9, codDir);
            valores.setInt(10, statusCartao);
            valores.setString(11, nomeCliSara);
            valores.setString(12, loginCorreio);
            valores.setString(13, senhaCorreio);
            valores.setInt(14, idCliente);
            valores.executeUpdate();
            valores.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            ContrErroLog.inserir("HOITO - contrContato", "SQLException", sql, e.toString());
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    
    public static boolean alterarLoginSigep(int idCliente, String login_sigep, String senha_sigep, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE cliente SET login_sigep = ?, senha_sigep = ? WHERE codigo = ? ";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setString(1, login_sigep);
            valores.setString(2, senha_sigep);
            valores.setInt(3, idCliente);
            valores.executeUpdate();
            valores.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            ContrErroLog.inserir("HOITO - contrContato", "SQLException", sql, e.toString());
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static boolean alterarStatusCartao(int idCliente, int statusCartao, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE cliente SET statusCartaoPostagem = ?, erro_atualizacao = 0, dataHoraAtualizacao = NOW() WHERE codigo = ? ";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, statusCartao);
            valores.setInt(2, idCliente);
            valores.executeUpdate();
            valores.close();
            return true;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrContato", "SQLException", sql, e.toString());
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static boolean alterarErroAtualizacao(int idCliente, int erro, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE cliente SET erro_atualizacao = ? WHERE codigo = ? ";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, erro);
            valores.setInt(2, idCliente);
            valores.executeUpdate();
            valores.close();
            return true;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrContato", "SQLException", sql, e.toString());
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static ArrayList<Integer> consultaContratoCliente(int idCliente, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT codECT FROM cliente_contrato WHERE idCliente = " + idCliente + " AND (SELECT temContrato FROM cliente WHERE codigo = " + idCliente + ") = 1";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<Integer> listaStatus = new ArrayList<Integer>();
            while (result.next()) {
                int codECT = result.getInt("codECT");
                listaStatus.add(codECT);
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
        
    public static ArrayList<Integer> consultaContratoClienteGroupByServico(int idCliente, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT codECT FROM cliente_contrato WHERE idCliente = " + idCliente + " GROUP BY grupoServico";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<Integer> listaStatus = new ArrayList<Integer>();
            for (int i = 0; result.next(); i++) {
                int codECT = result.getInt("codECT");
                listaStatus.add(codECT);
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
    
    public static int consultaContratoClienteGrupoServ(int idCliente, String grupoServ, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT codECT FROM cliente_contrato WHERE idCliente = " + idCliente + " AND grupoServico = '" + grupoServ + "' ;";
        String sql2 = "SELECT codigo FROM cliente WHERE codigo = "+idCliente+" AND (statusCartaoPostagem = 0 OR  dtVigenciaFimContrato < DATE(NOW()) OR temContrato = 0)";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            int codECT = 0;
            for (int i = 0; result.next(); i++) {
                codECT = result.getInt("codECT");
            }
            
            valores = conn.prepareStatement(sql2);
            result = (ResultSet) valores.executeQuery();
            if(result.next()){
                codECT = 0;
            }
            valores.close();
            return codECT;
        } catch (SQLException e) {
            System.out.println(e);
            //ContrErroLog.inserir("HOITO - contrNivel", "SQLException", sql, e.toString());
            return 0;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static Map<String, Integer> consultaMapContratoClienteByIdCliente(int idCliente, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT codECT, grupoServico FROM cliente_contrato WHERE idCliente = " + idCliente + " ;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            Map<String, Integer> mapList = new HashMap<String, Integer>();
            while (result.next()) {
                int codECT = result.getInt("codECT");
                String grupoServico = result.getString("grupoServico");
                mapList.put(grupoServico, codECT);
            }
            valores.close();
            return mapList;
        } catch (SQLException e) {
            System.out.println(e);
            //ContrErroLog.inserir("HOITO - contrNivel", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static boolean consultaStatusContrato(int idCliente, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql2 = "SELECT codigo FROM cliente WHERE codigo = "+idCliente+" AND (statusCartaoPostagem = 0 OR  dtVigenciaFimContrato < DATE(NOW())) AND temContrato = 1;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql2);
            ResultSet result = (ResultSet) valores.executeQuery();
            boolean flag = true;
            if(result.next()){
                flag = false;
            }
            valores.close();
            return flag;
        } catch (SQLException e) {
            System.out.println(e);
            //ContrErroLog.inserir("HOITO - contrNivel", "SQLException", sql, e.toString());
            return true;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    /*
    ***************************************************************************    
    */
    
    
    public static ArrayList<Integer> consultaOutrosServicosCliente(int idCliente, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT codECT FROM cliente_outros_servicos WHERE idCliente = " + idCliente + ";";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<Integer> listaStatus = new ArrayList<Integer>();
            while (result.next()) {
                int codECT = result.getInt("codECT");
                listaStatus.add(codECT);
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
    public static void insereOutrosServicos(int idCliente, int codECT, String grupo, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "INSERT INTO cliente_outros_servicos (idCliente, codECT, grupoServico) VALUES(?,?,?)";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idCliente);
            valores.setInt(2, codECT);
            valores.setString(3, grupo);
            valores.executeUpdate();
            valores.close();
        } catch (SQLException e) {
            e.printStackTrace();
            //ContrErroLog.inserir("HOITO - contrContato", "SQLException", sql, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static boolean limparOutrosServicos(int idCliente, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "DELETE FROM cliente_outros_servicos WHERE idCliente = ? ";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idCliente);
            valores.executeUpdate();
            valores.close();
            return true;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrContato", "SQLException", sql, e.toString());
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    
}
