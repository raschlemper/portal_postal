/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Entidade.ClientesUsuario;
import Entidade.SenhaCliente;
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
public class contrSenhaCliente {

    public static boolean inserir(int idCliente, String login, String senha, int nivel, String acessos, String departamentos, String servicos, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "INSERT INTO cliente_usuarios (codigo, login, senha, nivel, acessos, departamentos, servicos) VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idCliente);
            valores.setString(2, login);
            valores.setString(3, senha);
            valores.setInt(4, nivel);
            valores.setString(5, acessos);
            valores.setString(6, departamentos);
            valores.setString(7, servicos);
            valores.executeUpdate();
            valores.close();
            return true;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrSenhaCliente", "SQLException", sql, e.toString());
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static boolean alterar(int id, String login, String senha, int nivel, String acessos, String departamentos, String servicos, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE cliente_usuarios SET senha=?, login=?, nivel=?, acessos=?, departamentos=?, servicos=? WHERE id = ?";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setString(1, senha);
            valores.setString(2, login);
            valores.setInt(3, nivel);
            valores.setString(4, acessos);
            valores.setString(5, departamentos);
            valores.setString(6, servicos);
            valores.setInt(7, id);
            valores.executeUpdate();
            valores.close();
            return true;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrSenhaCliente", "SQLException", sql, e.toString());
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static boolean adicionarNovoDepartamento(int idCliente, int idDepto, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE cliente_usuarios SET departamentos = CONCAT(departamentos,?) WHERE codigo = ?";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setString(1, ";" + idDepto);
            valores.setInt(2, idCliente);
            //System.out.println(valores.toString());
            valores.executeUpdate();
            valores.close();
            return true;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrSenhaCliente", "SQLException", sql, e.toString());
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static boolean alterarPriemiroAcesso(String login, String senha, int idCliente,  String nomeBD, String novaSenha) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "UPDATE cliente_usuarios SET isFirst = 1, senha = '"+novaSenha+"' WHERE codigo = "+idCliente+" AND senha = '"+senha+"' AND login = '"+login+"' ;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
          
            valores.executeUpdate();
            valores.close();
            return true;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrSenhaCliente", "SQLException", sql, e.toString());
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    

    public static boolean excluir2(int idCliente, String login, String senha, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "delete from cliente_usuarios WHERE codigo=? AND login=? AND senha=?";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idCliente);
            valores.setString(2, login);
            valores.setString(3, senha);
            valores.executeUpdate();
            valores.close();
            return true;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrSenhaCliente", "SQLException", sql, e.toString());
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static ArrayList<SenhaCliente> consultaTodasSenhaCliente(int idCliente, String nomeBD) {
        Connection con = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM cliente_usuarios WHERE codigo = ?";
        try {
            PreparedStatement valores = con.prepareStatement(sql);
            valores.setInt(1, idCliente);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<SenhaCliente> listaLogin = new ArrayList<SenhaCliente>();
            for (int i = 0; result.next(); i++) {
                int id = result.getInt("id");
                int cod = result.getInt("codigo");
                String senha = result.getString("senha");
                String login = result.getString("login");
                int nivel = result.getInt("nivel");
                String acessos = result.getString("acessos");
                String departamentos = result.getString("departamentos");
                String servicos = result.getString("servicos");
                SenhaCliente sc = new Entidade.SenhaCliente(id, cod, senha, login, nivel, acessos, departamentos, servicos);
                listaLogin.add(sc);
            }
            return listaLogin;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrSenhaCliente", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(con);
        }
    }

    public static SenhaCliente consultaSenhaClienteById(int id, String nomeBD) {
        Connection con = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM cliente_usuarios WHERE id = ?";
        try {
            PreparedStatement valores = con.prepareStatement(sql);
            valores.setInt(1, id);
            ResultSet result = (ResultSet) valores.executeQuery();
            SenhaCliente sc = null;
            for (int i = 0; result.next(); i++) {
                int cod = result.getInt("codigo");
                String senha = result.getString("senha");
                String login = result.getString("login");
                int nivel = result.getInt("nivel");
                String acessos = result.getString("acessos");
                String departamentos = result.getString("departamentos");
                String servicos = result.getString("servicos");
                sc = new Entidade.SenhaCliente(id, cod, senha, login, nivel, acessos, departamentos, servicos);                
            }
            return sc;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrSenhaCliente", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(con);
        }
    }

    public static int nivelUsuarioEmp(String login, String senha, String nomeBD) {
        Connection con = Conexao.conectar(nomeBD);
        String sql = "SELECT cliente_usuarios.nivel FROM cliente JOIN cliente_usuarios ON cliente_usuarios.codigo = cliente.codigo WHERE cliente_usuarios.login = ? AND cliente_usuarios.senha = ?;";
        try {
            PreparedStatement valores = con.prepareStatement(sql);
            valores.setString(1, login);
            valores.setString(2, senha);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                int nivel = result.getInt("cliente_usuarios.nivel");
                return nivel;
            } else {
                return 0;
            }
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrSenhaCliente", "SQLException", sql, e.toString());
            return 0;
        } finally {
            Conexao.desconectar(con);
        }
    }

    public static ClientesUsuario usuarioEmp(String login, String senha, String nomeBD) {
        Connection con = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM cliente_usuarios WHERE login = ? AND senha = ?;";
       
        try {
            PreparedStatement valores = con.prepareStatement(sql);
            valores.setString(1, login);
            valores.setString(2, senha);
            ResultSet result = (ResultSet) valores.executeQuery();

            ClientesUsuario us = null;
            if (result.next()) {
                int id = result.getInt("id");
                int idCliente = result.getInt("codigo");
                int nivel = result.getInt("nivel");
                String login1 = result.getString("login");
                String ac = result.getString("acessos");
                int isFirst = result.getInt("isFirst");
                ArrayList<Integer> acessos = new ArrayList<Integer>();
                if (ac != null && ac.contains(";")) {
                    String aux[] = ac.split(";");
                    for (int i = 0; i < aux.length; i++) {
                        int j = Integer.parseInt(aux[i]);
                        acessos.add(j);
                    }
                } else if (ac != null && !ac.trim().equals("")) {
                    acessos.add(Integer.parseInt(ac));
                }
                String dp = result.getString("departamentos");
                ArrayList<Integer> departamentos = new ArrayList<Integer>();
                if (dp != null && dp.contains(";")) {
                    String aux[] = dp.split(";");
                    for (int i = 0; i < aux.length; i++) {
                        int j = Integer.parseInt(aux[i]);
                        departamentos.add(j);
                    }
                } else if (dp != null && !dp.trim().equals("")) {
                    departamentos.add(Integer.parseInt(dp));
                }
                String sv = result.getString("servicos");
                ArrayList<Integer> servicos = new ArrayList<Integer>();
                if (sv != null && sv.contains(";")) {
                    String aux[] = sv.split(";");
                    for (int i = 0; i < aux.length; i++) {
                        int j = Integer.parseInt(aux[i]);
                        servicos.add(j);
                    }
                } else if (sv != null && !sv.trim().equals("")) {
                    servicos.add(Integer.parseInt(sv));
                }

                us = new ClientesUsuario(id, idCliente, login1, nivel, acessos, departamentos, servicos, isFirst);
            }
            valores.close();
            return us;

        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrSenhaCliente", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(con);
        }
    }
}
