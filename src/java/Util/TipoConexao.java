package Util;

public enum TipoConexao {
    
    CLIENTE() {
        protected String url(String nome) {
            return "jdbc:mysql://localhost:3306/pp_" + nome +"?zeroDateTimeBehavior=convertToNull&autoReconnect=true";
        }
        protected String username() {
            return "root";
        }
        protected String password() {
            return "123456";
        }
    };
    

    TipoConexao() { }
    
    protected abstract String url(String nome);
    protected abstract String username();
    protected abstract String password();
    
    
    
}
