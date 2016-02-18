/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Coleta.Controle;

import Coleta.Entidade.Coleta;
import Coleta.Entidade.ColetaFixa;
import Controle.ContrErroLog;
import Util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author SCC4
 */
public class contrColetaFixa {

    public static void inserir(int idCliente, int idColetador, int idTipo, int fixo, String hora, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "insert into coleta_rotas (idCliente, idColetador, idTipo, fixo, hora) values(?,?,?,?,?)";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idCliente);
            valores.setInt(2, idColetador);
            valores.setInt(3, idTipo);
            valores.setInt(4, fixo);
            valores.setString(5, hora);
            valores.executeUpdate();
            valores.close();
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrColetaFixa", "SQLException", sql, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static void alterar(int idCliente, int idColetador, int idTipo, int fixo, String hora, int idRota, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "update coleta_rotas set idTipo=?, fixo=?, hora=? WHERE idColetaFixa = ?;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idTipo);
            valores.setInt(2, fixo);
            valores.setString(3, hora);
            valores.setInt(4, idRota);
            valores.executeUpdate();
            valores.close();
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrColetaFixa", "SQLException", sql, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static void alterarColetador(int idColetador, int idRota, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "update coleta_rotas set idColetador=? WHERE idColetaFixa=?;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idColetador);
            valores.setInt(2, idRota);
            valores.executeUpdate();
            valores.close();
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrColetaFixa", "SQLException", sql, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int excluir(int idColetaFixa, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "delete from coleta_rotas where idColetaFixa = ?;";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idColetaFixa);
            int i = pstmt.executeUpdate();
            return i;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrColetaFixa", "SQLException", sql, e.toString());
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int excluirByIdColetador(int idColetador, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "delete from coleta_rotas where idColetador = ?;";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idColetador);
            int i = pstmt.executeUpdate();
            return i;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrColetaFixa", "SQLException", sql, e.toString());
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static boolean verificaExistenciaColetaFixa(int idColetador, int idCliente, String hora, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT idColetaFixa FROM coleta_rotas WHERE idColetador = ? AND idCliente = ? AND hora = ?;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idColetador);
            valores.setInt(2, idCliente);
            valores.setString(3, hora);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrColetaFixa", "SQLException", sql, e.toString());
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int consultaColetadorEventualDoCliente(int idCliente, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT idColetador FROM coleta_rotas WHERE idCliente=? and fixo=0;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idCliente);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                return result.getInt("idColetador");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrColetaFixa", "SQLException", sql, e.toString());
            return 0;
        } finally {
            Conexao.desconectar(conn);
        }
    }
       public static int consultaTipoEscolhaColetaDoCliente(int idEmpresa) {
       Connection conn = Conexao.conectarGeral();
        String sql = "SELECT tipoEscolhaColeta FROM empresas WHERE idEmpresa= "+idEmpresa;
        try {
            PreparedStatement valores = conn.prepareStatement(sql);           
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                return result.getInt("tipoEscolhaColeta");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            ContrErroLog.inserir("consultaTipoEscolhaColetaDoCliente", "SQLException", sql, e.toString());
            return 0;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList consultaColetasFixasPeloColetador(int idColetador, String nomeBD) throws SQLException {
        Connection conn = Conexao.conectar(nomeBD);
        //String sql = "SELECT * FROM coleta_rotas WHERE idColetador=? AND idCliente IN (SELECT codigo FROM cliente) ORDER BY hora;";
        String sql = "SELECT * FROM coleta_rotas WHERE idColetador=? ORDER BY hora;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idColetador);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList listaStatus = new ArrayList();
            for (int i = 0; result.next(); i++) {
                int idColetaFixa = result.getInt("idColetaFixa");
                int idCliente = result.getInt("idCliente");
                int idTipo = result.getInt("idTipo");
                int fixo = result.getInt("fixo");
                String hora = result.getString("hora");
                ColetaFixa cf = new ColetaFixa(idColetaFixa, idCliente, idColetador, idTipo, fixo, hora);
                listaStatus.add(cf);
            }
            valores.close();
            return listaStatus;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrColetaFixa", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList consultaTodasColetasFixas(String nomeBD) throws SQLException {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM coleta_rotas WHERE fixo=1";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList listaStatus = new ArrayList();
            for (int i = 0; result.next(); i++) {
                int idColetador = result.getInt("idColetador");
                int idColetaFixa = result.getInt("idColetaFixa");
                int idCliente = result.getInt("idCliente");
                int idTipo = result.getInt("idTipo");
                int fixo = result.getInt("fixo");
                String hora = result.getString("hora");
                ColetaFixa cf = new ColetaFixa(idColetaFixa, idCliente, idColetador, idTipo, fixo, hora);
                listaStatus.add(cf);
            }
            valores.close();
            return listaStatus;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrColetaFixa", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    
}
