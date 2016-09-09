package Util;

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
            return String.format(URL, "189.4.65.227", 3306, nome);
        }
        protected String username() {
            return "dev";
        }
        protected String password() {
            return "1234@";
        }
    },
    
    PRODUCTION() {
        protected String url(String nome) {
            return String.format(URL, "localhost", 3306, nome);
        }
        protected String username() {
            return "smart_bd";
        }
        protected String password() {
            return "33m.SMRT";
        }
    };       
    
    private static String URL = "jdbc:mysql://%s:%d/%s?zeroDateTimeBehavior=convertToNull&autoReconnect=true";

    TipoConexao() { }
    
    protected abstract String url(String nome);
    protected abstract String username();
    protected abstract String password();
    
    
    
}
