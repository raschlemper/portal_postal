package Vendedor.Controle;

import Controle.ContrErroLog;
import Entidade.Clientes;
import Util.Conexao;
import Vendedor.Entidade.Vendedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class contrVendedores {
    
     public static void inserir(String nome, String referencia, int idClientes, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "INSERT INTO vendedores (nomeVendedor, referencia, idClientes, data_cadastro, ativo) values(?,?,?,NOW(),1);";
        
         System.out.println("foi 2");
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setString(1, nome);
            valores.setString(2, referencia);
            valores.setInt(3, idClientes);
            valores.executeUpdate();

            valores.close();
        } catch (SQLException e) {
            System.out.println("contrVendedores.inserir SQLException: "+e);
            ContrErroLog.inserir("contrVendedores", "SQLException", sql, e.toString());
        
        } finally {
            Conexao.desconectar(conn);
        }
     }
     public static void inserirClientesLista(int idVendedor, int idClientes, String percent, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "INSERT INTO vendedor_cliente (idVendedor, idCliente, percentual) values(?,?,?);";
        
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idVendedor);
            valores.setInt(2, idClientes);
            valores.setString(3, percent);
            valores.executeUpdate();

            valores.close();
        } catch (SQLException e) {
            System.out.println("contrVendedores.inserirClientesLista SQLException: "+e);
            ContrErroLog.inserir("contrVendedores", "SQLException", sql, e.toString());
        
        } finally {
            Conexao.desconectar(conn);
        }
     }
     public static void deletarListaCliente(int idVendedor, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "DELETE FROM vendedor_cliente WHERE idVendedor = "+idVendedor+";";
        
        try {
            PreparedStatement valores = conn.prepareStatement(sql);            
            valores.executeUpdate();
            valores.close();
        } catch (SQLException e) {
            System.out.println("contrVendedores.deletarListaCliente SQLException: "+e);
            ContrErroLog.inserir("contrVendedores", "SQLException", sql, e.toString());
        
        } finally {
            Conexao.desconectar(conn);
        }
     }
    
    
        public static ArrayList consultaTodosVendedores(String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT * FROM vendedores WHERE ativo = 1";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList listaVendedores = new ArrayList();
            for (int i = 0; result.next(); i++) {
                int idVendedor = result.getInt("idVendedor");
                String nome = result.getString("nomeVendedor");
                String referencia = result.getString("referencia");
                int idListaClientes = result.getInt("idClientes");
                Vendedor vend = new Vendedor(idVendedor, nome, referencia, idListaClientes);
                listaVendedores.add(vend);
            }
            valores.close();
            return listaVendedores;
        } catch (SQLException e) {
            ContrErroLog.inserir("contrVendedores", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
        
        public static String consultaNomeVendedoreById(int idVendedor, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT nomeVendedor, referencia FROM vendedores WHERE idVendedor = "+idVendedor+";";
          String res ="";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
           ResultSet result = (ResultSet) valores.executeQuery();
            if (result.next()) {
              
                res += result.getString("nomeVendedor");
                res += " - "+result.getString("referencia");
            } 
                return res;
            
        } catch (SQLException e) {
            ContrErroLog.inserir("contrVendedores", "SQLException", sql, e.toString());
            return "";
        } finally {
            Conexao.desconectar(conn);
        }
    }
        
           public static ArrayList consultaClientesVendedor(int idVendedor, String nomeBD) throws SQLException {
        Connection conn = Conexao.conectar(nomeBD);
        //String sql = "SELECT * FROM coleta_rotas WHERE idColetador=? AND idCliente IN (SELECT codigo FROM cliente) ORDER BY hora;";
        String sql = "SELECT nome, percentual, idCliente FROM vendedor_cliente LEFT JOIN cliente ON codigo = idCliente WHERE idVendedor=?;";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.setInt(1, idVendedor);
            ResultSet result = (ResultSet) valores.executeQuery();
            ArrayList listaClientes = new ArrayList();
            for (int i = 0; result.next(); i++) {
                String nome = result.getString("nome");
                int idCliente = result.getInt("idCliente");
                String perc = result.getString("percentual");
                float percent = 0;
                if(perc != null || !perc.equals("")){
                   percent = Float.parseFloat(result.getString("percentual"));
                }
                Clientes cf = new Clientes(idCliente, nome, percent);
                listaClientes.add(cf);
            }
            valores.close();
            return listaClientes;
        } catch (SQLException e) {
            ContrErroLog.inserir("contrVendedores", "SQLException", sql, e.toString());
            return null;
        } finally {
            Conexao.desconectar(conn);
        }
    }
    
}
