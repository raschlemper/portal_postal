/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexao {

    private static final int contConn = 0;
    private static final String PROP_PATH = "/opt/tomcat/webapps/application_config/database.properties";
    //private static final String PROP_PATH = "C:\\Users\\Ricardinho\\Desktop\\database.properties";
    //private static String HOST = "5.189.190.196";
   // private static String HOST = "192.168.0.100";
    //private static String PORT = "3306";
    //private static String USER = "smart_bd";
    //private static String PASS = "33m.SMRT";
    private static String HOST = "localhost";
    private static String PORT = "3306";
    private static String USER = "root";
    private static String PASS = "123456";

    public static Connection conectar(String nome) {
        try {
            //loadConnectionProperties();
            //System.out.println(HOST+" "+PORT+" "+USER+" "+PASS);
            //Conexão Servidor Scc4.com.br
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://" + HOST + ":" + PORT + "/pp_" + nome + "?zeroDateTimeBehavior=convertToNull&autoReconnect=true";
            //String url = "jdbc:mysql://scc4.com.br:3306/pp_" + nome + "?zeroDateTimeBehavior=convertToNull&autoReconnect=true";
            Connection con = DriverManager.getConnection(url, USER, PASS);
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
            String url = "jdbc:mysql://" + HOST + ":" + PORT + "/portalpostal?zeroDateTimeBehavior=convertToNull&autoReconnect=true";
            //String url = "jdbc:mysql://scc4.com.br:3306/portalpostal?zeroDateTimeBehavior=convertToNull&autoReconnect=true";
            Connection con = DriverManager.getConnection(url, USER, PASS);
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
            String url = "jdbc:mysql://" + HOST + ":" + PORT + "/_dne?zeroDateTimeBehavior=convertToNull&autoReconnect=true";
            Connection con = DriverManager.getConnection(url, USER, PASS);
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
            String url = "jdbc:mysql://" + HOST + ":" + PORT + "/scc4?zeroDateTimeBehavior=convertToNull&autoReconnect=true";
            //Connection con = DriverManager.getConnection(url,"smart_bd","33m.SMRT");
            Connection con = DriverManager.getConnection(url, USER, PASS);

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
            String url = "jdbc:mysql://" + HOST + ":" + PORT + "/boxcubo?zeroDateTimeBehavior=convertToNull&autoReconnect=true";
            //Connection con = DriverManager.getConnection(url, "smart_bd", "33m.SMRT");
            Connection con = DriverManager.getConnection(url, USER, PASS);

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

    public static void loadConnectionProperties() {
        Properties props = new Properties();
        InputStream is = null;

        // First try loading from the current directory
        try {
            File f = new File(PROP_PATH);
            is = new FileInputStream(f);
        } catch (Exception e) {
            System.out.println(e);
            is = null;
        }

        try {
            if (is != null) {

                // Try loading properties from the file (if found)
                props.load(is);

                //HOST = props.getProperty("DatabaseHost", "mysql-01.scc4.com.br");
                HOST = props.getProperty("DatabaseHost", "localhost");
                PORT = props.getProperty("DatabasePort", "3306");
                USER = props.getProperty("DatabaseUser", "smart_bd");
                PASS = props.getProperty("DatabasePass", "33m.SMRT");

            }
        } catch (Exception e) {
            System.out.println(e);
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
