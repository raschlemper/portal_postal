/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Entidade.empresas;
import Util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public static boolean consultaoSemHrVerao(int idEmpresa) {
        Connection con = Conexao.conectarGeral();
        String sql = "SELECT sem_hr_verao FROM empresas WHERE idEmpresa = ? AND sem_hr_verao = 1";
        try {
            PreparedStatement valores = con.prepareStatement(sql);
            valores.setInt(1, idEmpresa);
            ResultSet result = (ResultSet) valores.executeQuery();
            return result.next();
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrEmpresa", "SQLException", sql, e.toString());
            return false;
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

    public static ArrayList<empresas> listaAGF() {
        Connection con = Conexao.conectarGeral();
        String sql = "SELECT * FROM empresas ORDER BY empresa;";       
        ArrayList<empresas> lsEmp = new ArrayList<empresas>();
        try {            
            PreparedStatement valores = con.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            while (result.next()) { 
                
                lsEmp.add(new Entidade.empresas(result));               
            }
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrEmpresa.listaAGF", "SQLException", sql, e.toString());
            System.out.println(e);
        } finally {
            Conexao.desconectar(con);
        }
         return lsEmp;
    }

    public static empresas consultaEmpresa(int idEmpresa) {
        Connection con = Conexao.conectarGeral();
        String sql = "SELECT * FROM empresas WHERE idEmpresa = ?";
        try {
            PreparedStatement valores = con.prepareStatement(sql);
            valores.setInt(1, idEmpresa);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {                
                return new Entidade.empresas(result);
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
    public static empresas consultaEmpresaCnpj(String cnpj) {
        Connection con = Conexao.conectarGeral();
        String sql = "SELECT * FROM empresas WHERE cnpj = ?";
        try {
            PreparedStatement valores = con.prepareStatement(sql);
            valores.setString(1, cnpj);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {                
                return new Entidade.empresas(result);
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
