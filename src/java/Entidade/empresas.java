/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidade;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


/**
 *
 * @author Administrador
 */
public class empresas {

    private int idEmpresa;
    private String empresa;
    private String cnpj;
    private String endereco;
    private String telefone;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;
    private String email;
    private String fantasia;
    private String complemento;
    private String status;
    private Timestamp dataHora;
    private String codSto;
    private String ip_server;
    private String login_server;
    private String senha_server;
    private String versao_consolidador;
    private String login_ws_sigep;
    private String senha_ws_sigep;
    private String tipo_agencia;
    private String tipo_sistema;
    private int tipoEscolhaColeta;
    //private int crm;
    //private int desk;
    //private int chamada;
    //private int coleta;

    /*public empresas(int idEmpresa, String empresa, String endereco, String telefone, String bairro, String cidade, String uf, String cep, String email, String cnpj, String fantasia, String complemento, String status, int chamada, int coleta, String login_ws_sigep, String senha_ws_sigep, String tipo_agencia) {
        this.idEmpresa = idEmpresa;
        this.empresa = empresa;
        this.endereco = endereco;
        this.telefone = telefone;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.cep = cep;
        this.email = email;
        this.cnpj = cnpj;
        this.fantasia = fantasia;
        this.complemento = complemento;
        this.status = status;
        this.login_ws_sigep = login_ws_sigep;
        this.senha_ws_sigep = senha_ws_sigep;
        this.tipo_agencia = tipo_agencia;
    }   */ 
    
    public empresas(ResultSet result) throws SQLException {
        this.idEmpresa = result.getInt("idEmpresa");
        this.empresa = result.getString("empresa");
        this.endereco = result.getString("endereco");
        this.telefone = result.getString("telefone");
        this.bairro = result.getString("bairro");
        this.cidade = result.getString("cidade");
        this.uf = result.getString("uf");
        this.cep = result.getString("cep");
        this.email = result.getString("email");
        this.cnpj = result.getString("cnpj");
        this.fantasia = result.getString("fantasia");
        this.complemento = result.getString("complemento");
        this.status = result.getString("status");
        this.login_ws_sigep = result.getString("login_ws_sigep");
        this.senha_ws_sigep = result.getString("senha_ws_sigep");
        this.tipo_agencia = result.getString("tipo_agencia");
        this.tipo_sistema = result.getString("tipo_sistema");
        this.tipoEscolhaColeta = result.getInt("tipoEscolhaColeta");
    }

    public String getTipo_sistema() {
        return tipo_sistema;
    }

    public void setTipo_sistema(String tipo_sistema) {
        this.tipo_sistema = tipo_sistema;
    }

    public int getTipoEscolhaColeta() {
        return tipoEscolhaColeta;
    }

    public void setTipoEscolhaColeta(int tipoEscolhaColeta) {
        this.tipoEscolhaColeta = tipoEscolhaColeta;
    }
    
    public String getTipo_agencia() {
        return tipo_agencia;
    }

    public void setTipo_agencia(String tipo_agencia) {
        this.tipo_agencia = tipo_agencia;
    }

    public String getCodSto() {
        return codSto;
    }

    public void setCodSto(String codSto) {
        this.codSto = codSto;
    }

    public String getIp_server() {
        return ip_server;
    }

    public void setIp_server(String ip_server) {
        this.ip_server = ip_server;
    }

    public String getLogin_server() {
        return login_server;
    }

    public void setLogin_server(String login_server) {
        this.login_server = login_server;
    }

    public String getSenha_server() {
        return senha_server;
    }

    public void setSenha_server(String senha_server) {
        this.senha_server = senha_server;
    }

    public String getVersao_consolidador() {
        return versao_consolidador;
    }

    public void setVersao_consolidador(String versao_consolidador) {
        this.versao_consolidador = versao_consolidador;
    }

    public String getLogin_ws_sigep() {
        return login_ws_sigep;
    }

    public void setLogin_ws_sigep(String login_ws_sigep) {
        this.login_ws_sigep = login_ws_sigep;
    }

    public String getSenha_ws_sigep() {
        return senha_ws_sigep;
    }

    public void setSenha_ws_sigep(String senha_ws_sigep) {
        this.senha_ws_sigep = senha_ws_sigep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public Timestamp getDataHora() {
        return dataHora;
    }

    public void setDataHora(Timestamp dataHora) {
        this.dataHora = dataHora;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getFantasia() {
        return fantasia;
    }

    public void setFantasia(String fantasia) {
        this.fantasia = fantasia;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    /*public int getChamada() {
        return chamada;
    }

    public void setChamada(int chamada) {
        this.chamada = chamada;
    }

    public int getColeta() {
        return coleta;
    }

    public void setColeta(int coleta) {
        this.coleta = coleta;
    }

    public int getCrm() {
        return crm;
    }

    public void setCrm(int crm) {
        this.crm = crm;
    }

    public int getDesk() {
        return desk;
    }

    public void setDesk(int desk) {
        this.desk = desk;
    }*/

}