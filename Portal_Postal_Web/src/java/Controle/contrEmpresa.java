/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import java.sql.Connection;
import Util.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Administrador
 */
public class contrEmpresa {

    public static String cnpjEmpresa(int idEmpresa) {
        Connection con = Conexao.conectarGeral();
        String sql = "SELECT cnpj FROM empresas WHERE idEmpresa = ?";
        try {
            PreparedStatement valores = con.prepareStatement(sql);
            valores.setInt(1, idEmpresa);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                String empresa = result.getString("cnpj");
                return empresa;
            } else {
                return null;
            }
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrEmpresa", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(con);
        }
    }

    public static String nomeEmpresaByNomeBD(String nomeBD) {
        Connection con = Conexao.conectarGeral();
        String sql = "SELECT fantasia FROM empresas WHERE cnpj = ?";
        try {
            PreparedStatement valores = con.prepareStatement(sql);
            valores.setString(1, nomeBD);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                String empresa = result.getString("fantasia");
                return empresa;
            } else {
                return "";
            }
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrEmpresa", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(con);
        }
    }

    public static Entidade.empresas consultaEmpresa(int idEmpresa) {
        Connection con = Conexao.conectarGeral();
        String sql = "SELECT * FROM empresas WHERE idEmpresa = ?";
        try {
            PreparedStatement valores = con.prepareStatement(sql);
            valores.setInt(1, idEmpresa);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                String cnpj = result.getString("cnpj");
                String nomeEmpresa = result.getString("empresa");
                String endereco = result.getString("endereco");
                String telefone = result.getString("telefone");
                String bairro = result.getString("bairro");
                String cidade = result.getString("cidade");
                String uf = result.getString("uf");
                String cep = result.getString("cep");
                String email = result.getString("email");
                String fantasia = result.getString("fantasia");
                String complemento = result.getString("complemento");
                String status = result.getString("status");
                int chamada = result.getInt("chamada");
                int coleta = result.getInt("coleta");
                String login_ws = result.getString("login_ws_sigep");
                String senha_ws = result.getString("senha_ws_sigep");
                String tipo_agencia = result.getString("tipo_agencia");
                Entidade.empresas empresa = new Entidade.empresas(idEmpresa, nomeEmpresa, endereco, telefone, bairro, cidade, uf, cep, email, cnpj, fantasia, complemento, status, chamada, coleta, login_ws, senha_ws, tipo_agencia);
                return empresa;
            } else {
                return null;
            }
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrEmpresa", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(con);
        }
    }
}
