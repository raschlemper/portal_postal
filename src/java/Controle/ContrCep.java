/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Entidade.Endereco;
import Util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author RICARDINHO
 */
public class ContrCep {
    
    public static int inserir(String cep, String uf, String cidade, String bairro, String logradouro) {
        Connection conn = Conexao.conectarCep();
        String sql = "INSERT INTO enderecos (id, cep, uf, cidade, bairro, logradouro, logradouro_abreviatura) values(?,?,?,?,?,?,?)";
        try {
            PreparedStatement valores = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            valores.setString(1, cep);
            valores.setString(2, cep);
            valores.setString(3, uf);
            valores.setString(4, cidade);
            valores.setString(5, bairro);
            valores.setString(6, logradouro);
            valores.setString(7, logradouro);
            valores.executeUpdate();
            
            int autoIncrementKey = 0;
            ResultSet rs = valores.getGeneratedKeys();
            if (rs.next()) {
                autoIncrementKey = rs.getInt(1);
            }
            valores.close();
            return autoIncrementKey;
        } catch (SQLException e) {
            return 0;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
    public static Endereco pesquisaCep(String cep) {
        Connection con = Conexao.conectarCep();
        try {
            Endereco end = new Endereco("", "", "CEP inexistente", "", cep);
            
            String sql = "SELECT * FROM enderecos WHERE cep = '"+cep+"'";
            PreparedStatement valores = con.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
                String cep1 = Util.FormataString.formataCep(cep);
                String uf = result.getString("uf");
                String cidade = result.getString("cidade");
                String bairro = result.getString("bairro");
                if(bairro == null){
                    bairro = "";
                }
                String logradouro = result.getString("logradouro");     
                if(logradouro == null){
                    logradouro = "";
                }
                end= new Endereco(bairro, cidade, logradouro, uf, cep1);
            }
            return end;
        } catch (SQLException e) {
            ContrErroLog.inserir("HOITO - contrLogin", "SQLException", "pesquisaCep", e.toString());
            return null;
        } finally {
            Conexao.desconectar(con);
        }
    }
    
    
    public static ArrayList<Endereco> pesquisaPaises(String servico) {
        Connection con = Conexao.conectarCep();
        try {            
            String sql = "SELECT * FROM ect_pais WHERE servicos_disponiveis LIKE '%"+servico+"%' ORDER BY pai_no_portugues;";
            PreparedStatement valores = con.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList<Endereco> lista = new ArrayList<Endereco>();
            while (result.next()) {
                String sigla = result.getString("pai_sg");
                String siglaAlt = result.getString("pai_sg_alternativa");
                String pais = result.getString("pai_no_portugues");                
                lista.add(new Endereco(pais, sigla, siglaAlt));
            }
            return lista;
        } catch (SQLException e) {
            ContrErroLog.inserir("PortalPostal - contrCep", "SQLException", "pesquisaPaises - ", e.toString());
            return null;
        } finally {
            Conexao.desconectar(con);
        }
    }
}
