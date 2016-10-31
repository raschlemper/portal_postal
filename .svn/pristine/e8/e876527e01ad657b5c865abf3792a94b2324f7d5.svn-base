
package Entidade;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ClienteSMTP {
    
private int id;
private int idCliente;
private String idDepartamento;   
private int tipo_servidor;     
private String envia_remetente;     
private int envia_destinatario;  
private String smtp;               
private int porta;              
private int is_secure;
private String tipo_seguranca;
private int porta_ssl; 
private String user;              
private String senha;     

    @Override
    public String toString() {
        return "ClienteSMTP{" + "id=" + id + ", idCliente=" + idCliente + ", idDepartamento=" + idDepartamento + ", tipo_servidor=" + tipo_servidor + ", envia_remetente=" + envia_remetente + ", envia_destinatario=" + envia_destinatario + ", smtp=" + smtp + ", porta=" + porta + ", is_secure=" + is_secure + ", tipo_seguranca=" + tipo_seguranca + ", porta_ssl=" + porta_ssl + ", user=" + user + ", senha=" + senha + '}';
    }

    public ClienteSMTP(int idCliente, String idDepartamento, int tipo_servidor, String envia_remetente, int envia_destinatario, String smtp, int porta, int is_secure, String tipo_seguranca, int porta_ssl, String user, String senha) {
        this.idCliente = idCliente;
        this.idDepartamento = idDepartamento;
        this.tipo_servidor = tipo_servidor;
        this.envia_remetente = envia_remetente;
        this.envia_destinatario = envia_destinatario;
        this.smtp = smtp;
        this.porta = porta;
        this.is_secure = is_secure;
        this.tipo_seguranca = tipo_seguranca;
        this.porta_ssl = porta_ssl;
        this.user = user;
        this.senha = senha;
    }



    public ClienteSMTP(int id, int idCliente, String idDepartamento, int tipo_servidor, String envia_remetente, int envia_destinatario, String smtp, int porta, int is_secure, String tipo_seguranca, int porta_ssl, String user, String senha) {
        this.id = id;
        this.idCliente = idCliente;
        this.idDepartamento = idDepartamento;
        this.tipo_servidor = tipo_servidor;
        this.envia_remetente = envia_remetente;
        this.envia_destinatario = envia_destinatario;
        this.smtp = smtp;
        this.porta = porta;
        this.is_secure = is_secure;
        this.tipo_seguranca = tipo_seguranca;
        this.porta_ssl = porta_ssl;
        this.user = user;
        this.senha = senha;
    }

    public ClienteSMTP(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.idCliente = rs.getInt("idCliente");
        this.idDepartamento = rs.getString("idDepartamento");
        this.tipo_servidor = rs.getInt("tipo_servidor");
        this.envia_remetente = rs.getString("envia_remetente");
        this.envia_destinatario = rs.getInt("envia_destinatario");
        this.smtp = rs.getString("smtp");
        this.porta = rs.getInt("porta");
        this.is_secure = rs.getInt("is_secure");
        this.tipo_seguranca = rs.getString("tipo_seguranca");
        this.porta_ssl = rs.getInt("porta_ssl");
        this.user = rs.getString("user");
        this.senha = rs.getString("senha");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(String idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public int getTipo_servidor() {
        return tipo_servidor;
    }

    public void setTipo_servidor(int tipo_servidor) {
        this.tipo_servidor = tipo_servidor;
    }

    public String getEnvia_remetente() {
        return envia_remetente;
    }

    public void setEnvia_remetente(int String) {
        this.envia_remetente = envia_remetente;
    }

    public int getEnvia_destinatario() {
        return envia_destinatario;
    }

    public void setEnvia_destinatario(int envia_destinatario) {
        this.envia_destinatario = envia_destinatario;
    }

    public String getSmtp() {
        return smtp;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    public int getPorta() {
        return porta;
    }

    public void setPorta(int porta) {
        this.porta = porta;
    }

    public int getIs_secure() {
        return is_secure;
    }

    public void setIs_secure(int is_secure) {
        this.is_secure = is_secure;
    }

    public String getTipo_seguranca() {
        return tipo_seguranca;
    }

    public void setTipo_seguranca(String tipo_seguranca) {
        this.tipo_seguranca = tipo_seguranca;
    }

    public int getPorta_ssl() {
        return porta_ssl;
    }

    public void setPorta_ssl(int porta_ssl) {
        this.porta_ssl = porta_ssl;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }



}
