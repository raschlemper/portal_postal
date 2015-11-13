/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    
    public static int contConn = 0;
    
    public static Connection conectar(String nome) {
        try {            
            //Conexão Servidor Scc4.com.br
            Class.forName("com.mysql.jdbc.Driver");
            
            //String url = "jdbc:mysql://localhost:3306/pp_06895434000183?zeroDateTimeBehavior=convertToNull&autoReconnect=true";
            String url = "jdbc:mysql://localhost:3306/pp_" + nome +"?zeroDateTimeBehavior=convertToNull&autoReconnect=true";
            Connection con = DriverManager.getConnection(url, "root", "123456");
            
            //String url = "jdbc:mysql://80.86.94.18:3306/pp_" + nome +"?zeroDateTimeBehavior=convertToNull&autoReconnect=true";
            //Connection con = DriverManager.getConnection(url,"smart_bd","33m.SMRT");
            
            //contConn++;
            //System.out.println("+ Conexoes Ativas = " + contConn);
            return (con);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Connection conectarGeral() {
        try {            
            //Conexão Banco Local
            Class.forName("com.mysql.jdbc.Driver");
            
            String url = "jdbc:mysql://localhost:3306/portalpostal?zeroDateTimeBehavior=convertToNull&autoReconnect=true";
            Connection con = DriverManager.getConnection(url, "root", "123456");
            
            //String url = "jdbc:mysql://80.86.94.18:3306/portalpostal?zeroDateTimeBehavior=convertToNull&autoReconnect=true";
            //Connection con = DriverManager.getConnection(url,"smart_bd","33m.SMRT");
            
            //contConn++;
            //System.out.println("G Conexoes Ativas = " + contConn);
            return (con);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Connection conectarCep() {
        try {            
            //Conexão Banco Local
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://209.126.111.14:3306/cep2011?zeroDateTimeBehavior=convertToNull&autoReconnect=true";
            Connection con = DriverManager.getConnection(url,"smart_bd","33m.SMRT");
            //Connection con = DriverManager.getConnection(url, "root", "1s2c3c4");
            
            
            //contConn++;
            //System.out.println("C Conexoes Ativas = " + contConn);
            return (con);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Connection conectarSCC4() {
        try {
            //Conexão Banco Local
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://209.126.111.14:3306/scc4?zeroDateTimeBehavior=convertToNull&autoReconnect=true";
            Connection con = DriverManager.getConnection(url,"smart_bd","33m.SMRT");
            //Connection con = DriverManager.getConnection(url, "root", "1s2c3c4");
            
            //contConn++;
            //System.out.println("S Conexoes Ativas = " + contConn);
            return (con);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void desconectar(Connection conexao) {
        try {
            conexao.close();
            //contConn--;
            //System.out.println("D Conexoes Ativas = " + contConn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
