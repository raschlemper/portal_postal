package Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public enum TipoConexao {
    
    DEVELOPMENT_SCC4() {
        protected String url(String nome) {
            return String.format(URL, "localhost", 3306, nome);
        }
        protected String username() {
            return "root";
        }
        protected String password() {
            return "1s2c3c4";
        }
    },
    
    DEVELOPMENT() {
        protected String url(String nome) {
            return String.format(URL, "localhost", 3306, nome);
        }
        protected String username() {
            return "root";
        }
        protected String password() {
            return "123456";
        }
    },
    
    TEST() {
        protected String url(String nome) {
            return String.format(URL, "localhost", 3306, nome);
        }
        protected String username() {
            return "root";
        }
        protected String password() {
            return "123456";
        }
    },
    
    HOMOLOGATION() {
        protected String url(String nome) {
            return String.format(URL, "localhost", 3306, nome);
        }
        protected String username() {
            return "smart_bd";
        }
        protected String password() {
            return "33m.SMRT";
        }
    },
    
    PRODUCTION() {
        protected String url(String nome) {
            //return String.format(URL, "mysql-01.scc4.com.br", 3306, nome);
            return String.format(URL, "5.189.190.196", 3306, nome);
        }

        protected String username() {
            return "smart_bd";
        }

        protected String password() {
            return "33m.SMRT";
        }
        /*protected String url(String nome) {
            return String.format(URL, loadConnectionProperties("DatabaseHost"), 3306, nome);
        }
        protected String username() {
            return loadConnectionProperties("DatabaseUser");
        }
        protected String password() {
            return loadConnectionProperties("DatabasePass");
        }*/
    };       
    
    private static String URL = "jdbc:mysql://%s:%d/%s?zeroDateTimeBehavior=convertToNull&autoReconnect=true";

    TipoConexao() { }
    
    protected abstract String url(String nome);
    protected abstract String username();
    protected abstract String password();
    
    private static String PROP_PATH = "/opt/tomcat/webapps/application_config/database.properties";
    //private static final String PROP_PATH = "C:\\Users\\Ricardinho\\Desktop\\database.properties";

    private static String loadConnectionProperties(String parametro) {
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

                return props.getProperty(parametro, "localhost");
                //return props.getProperty("DatabaseHost", "localhost");
                //PORT = props.getProperty("DatabasePort", "3306");
                //USER  = props.getProperty("DatabaseUser", "root");
                //PASS  = props.getProperty("DatabasePass", "1s2c3c4");

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    
}
