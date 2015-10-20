/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Coleta.Controle;

import Coleta.Entidade.Coletador;
import Controle.ContrErroLog;
import Util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author SCC4
 */
public class contrColetador {

    public static int inserir(String nome, String telefone, int rota, String login, String senha, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "insert into coleta_coletador (nome, telefone, rota, login, senha, ativo) values(?,?,?,?,?,1)";
        try {
            PreparedStatement valores = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            valores.setString(1, nome);
            valores.setString(2, telefone);
            valores.setInt(3, rota);
            valores.setString(4, login);
            valores.setString(5, senha);
            valores.executeUpdate();

            int autoIncrementKey = 0;
            ResultSet rs = valores.getGeneratedKeys();
            if (rs.next()) {
                autoIncrementKey = rs.getInt(1);
            }

            valores.close();
            return autoIncrementKey;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrColetador", "SQLException", sql, e.toString());
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static void inserirGeral(int idColetador, int idEmpresa, String login, String senha) {
        Connection conn = Conexao.conectarGeral();
        String sql = "insert into logincoletador (idColetador, idEmpresa, login, senha, ativo) values(?,?,?,?,1)";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idColetador);
            valores.setInt(2, idEmpresa);
            valores.setString(3, login);
            valores.setString(4, senha);
            valores.executeUpdate();
            valores.close();
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrColetador", "SQLException", sql, e.toString());
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int alterar(String nome, String telefone, int rota, String login, String senha, int idColetador, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "update coleta_coletador set nome=?, telefone=?, rota=?, login=?, senha=? where idColetador = ?";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setString(1, nome);
            valores.setString(2, telefone);
            valores.setInt(3, rota);
            valores.setString(4, login);
            valores.setString(5, senha);
            valores.setInt(6, idColetador);
            int i = valores.executeUpdate();
            return i;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrColetador", "SQLException", sql, e.toString());
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int alterarGeral(String login, String senha, int idColetador, int idEmpresa, String nomeBD) {
        Connection conn = Conexao.conectarGeral();
        String sql = "update logincoletador set login=?, senha=? where idColetador = ? and idEmpresa = ?";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setString(1, login);
            valores.setString(2, senha);
            valores.setInt(3, idColetador);
            valores.setInt(4, idEmpresa);
            int i = valores.executeUpdate();
            return i;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrColetador", "SQLException", sql, e.toString());
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static boolean verificaExistenciaLoginColetador(int idColetador, int idEmpresa, String nomeBD) {
        Connection conn = Conexao.conectarGeral();
        String sql = "select idColetador from logincoletador where idColetador = ? and idEmpresa = ? and ativo = 1";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idColetador);
            valores.setInt(2, idEmpresa);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            ContrErroLog.inserir("HOITO - contrColetador", "SQLException", sql, ex.toString());
            return false;
        }
    }

    public static int excluirGeral(int idEmpresa, int idColetador) {
        Connection conn = Conexao.conectarGeral();
        String sql = "update logincoletador set ativo = 0 where idColetador = ? and idEmpresa = ?;";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idColetador);
            pstmt.setInt(2, idEmpresa);
            int i = pstmt.executeUpdate();
            return i;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrColetador", "SQLException", sql, e.toString());
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static int excluir(int idColetador, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "update coleta_coletador set ativo = 0 where idColetador = ?;";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idColetador);
            int i = pstmt.executeUpdate();
            return i;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrColetador", "SQLException", sql, e.toString());
            return -1;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList consultaTodosColetadores(String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM coleta_coletador WHERE ativo = 1";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList listaStatus = new ArrayList();
            for (int i = 0; result.next(); i++) {
                int idColetador = result.getInt("idColetador");
                String nome = result.getString("nome");
                String telefone = result.getString("telefone");
                int rota = result.getInt("rota");
                String login = result.getString("login");
                String senha = result.getString("senha");
                Coleta.Entidade.Coletador coletador = new Coleta.Entidade.Coletador(idColetador, nome, telefone, rota, login, senha);
                listaStatus.add(coletador);
            }
            valores.close();
            return listaStatus;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrColetador", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList<Integer> consultaIdColetadores(int idColetador, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "";
        if (idColetador == 0) {
            sql = "SELECT idColetador FROM coleta_coletador WHERE ativo = 1";
        } else {
            sql = "SELECT idColetador FROM coleta_coletador WHERE ativo = 1 AND idColetador=" + idColetador;
        }
        ArrayList<Integer> listaStatus = new ArrayList<Integer>();
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            for (int i = 0; result.next(); i++) {
                listaStatus.add(result.getInt("idColetador"));
            }
            valores.close();
            return listaStatus;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrColetador", "SQLException", sql, e.toString());
            return listaStatus;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static Coletador consultaColetadoresById(int idColetador, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM coleta_coletador WHERE idColetador=?";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idColetador);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                String nome = result.getString("nome");
                String telefone = result.getString("telefone");
                int rota = result.getInt("rota");
                String login = result.getString("login");
                String senha = result.getString("senha");
                return new Coleta.Entidade.Coletador(idColetador, nome, telefone, rota, login, senha);
            } else {
                return null;
            }
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrColetador", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static String consultaNomeColetadoresById(int idColetador, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT nome FROM coleta_coletador WHERE idColetador=?";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idColetador);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                return result.getString("nome");
            } else {
                return "";
            }
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrColetador", "SQLException", sql, e.toString());
            return "";
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static String consultaSenhaById(int idColetador, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT senha FROM coleta_coletador WHERE idColetador=?";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idColetador);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                return result.getString("senha");
            } else {
                return "";
            }
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrColetador", "SQLException", sql, e.toString());
            return "";
        } finally {
            Conexao.desconectar(conn);
        }
    }

    /*************************************/
    public static ArrayList <Coletador> consultaTodosColetadoresColeta(String data, int status, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM coleta_coletador WHERE ativo = 1 ORDER BY nome";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList <Coletador> listaC = new ArrayList();
            for (int i = 0; result.next(); i++) {
                int idColetador = result.getInt("idColetador");
                String nome = result.getString("nome");
                String telefone = result.getString("telefone");
                String login = result.getString("login");
                String senha = result.getString("senha");

                String sql2 = "SELECT COUNT(idColeta) AS qtd FROM coleta WHERE idColetador = "+idColetador+" AND DATE(dataHoraColeta) = '" + data + "'";
                if(status == 2){
                    sql2 += "AND (status = " + status+" OR status = 6)";
                } else if(status == 3){
                    sql2 += "AND (status = " + status+" OR status = 4)";
                } else if(status > 0){
                     sql2 += "AND status = " + status;
                }
                PreparedStatement valores2 = conn.prepareStatement(sql2);
                ResultSet result2 = (ResultSet) valores2.executeQuery();
                int rota = 0;
                if (result2.next()) {
                    rota = result2.getInt("qtd");
                }

                Coletador coletador = new Coletador(idColetador, nome, telefone, rota, login, senha);
                listaC.add(coletador);
            }
            valores.close();
            return listaC;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrColetador", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
}
