package Util;

public enum TipoConexao {
    
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
    
    PRODUTION() {
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
