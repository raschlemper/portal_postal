/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Coleta.Entidade;

/**
 *
 * @author SCC4
 */
public class Coletador {
    
    private int idColetador;
    private String nome;
    private String telefone;
    private int rota;
    private String login;
    private String senha;

    public Coletador(int idColetador, String nome, String telefone, int rota, String login, String senha) {
        this.idColetador = idColetador;
        this.nome = nome;
        this.telefone = telefone;
        this.rota = rota;
        this.login = login;
        this.senha = senha;
    }

    public int getIdColetador() {
        return idColetador;
    }

    public void setIdColetador(int idColetador) {
        this.idColetador = idColetador;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getRota() {
        return rota;
    }

    public void setRota(int rota) {
        this.rota = rota;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

}
