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
            String url = "jdbc:mysql://192.168.0.7:3306/pp_" + nome + "?zeroDateTimeBehavior=convertToNull&autoReconnect=true";
            Connection con = DriverManager.getConnection(url, "root", "123456");
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

            String url = "jdbc:mysql://192.168.0.7:3306/portalpostal?zeroDateTimeBehavior=convertToNull&autoReconnect=true";
            Connection con = DriverManager.getConnection(url, "root", "123456");
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
            String url = "jdbc:mysql://192.168.0.7:3306/_dne?zeroDateTimeBehavior=convertToNull&autoReconnect=true";
            //Connection con = DriverManager.getConnection(url,"smart_bd","33m.SMRT");
            Connection con = DriverManager.getConnection(url, "root", "123456");

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
            String url = "jdbc:mysql://192.168.0.7:3306/scc4?zeroDateTimeBehavior=convertToNull&autoReconnect=true";
            //Connection con = DriverManager.getConnection(url,"smart_bd","33m.SMRT");
            Connection con = DriverManager.getConnection(url, "root", "123456");

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

    public static Connection conectarBoxCubo() {
        try {
            //Conexão Banco Local
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://192.168.0.7:3306/boxcubo?zeroDateTimeBehavior=convertToNull&autoReconnect=true";
            Connection con = DriverManager.getConnection(url, "smart_bd", "33m.SMRT");
            // Connection con = DriverManager.getConnection(url, "root", "123456");

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
