/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidade;

import java.util.ArrayList;

/**
 *
 * @author Administrador
 */
public class Usuario {

    private int idUsuario;
    private int idEmpresa;
    private String nome;
    private String login;
    private String senha;
    private String email;
    private String cpf;
    private int ativo;
    private int idNivel;
    private int usaPortalPostal;
    private String acessosPortalPostal;
    private int usaConsolidador;
    private String acessosConsolidador;
    private ArrayList<Integer> listaAcessosPortalPostal;
    private ArrayList<Integer> listaAcessosConsolidador;

    public Usuario(int idUsuario, int idEmpresa, String nome, String login, String senha, String email, String cpf, int ativo, int idNivel, int usaPortalPostal, String acessosPortalPostal, int usaConsolidador, String acessosConsolidador) {
        this.idUsuario = idUsuario;
        this.idEmpresa = idEmpresa;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.email = email;
        this.cpf = cpf;
        this.ativo = ativo;
        this.idNivel = idNivel;
        this.usaPortalPostal = usaPortalPostal;
        this.acessosPortalPostal = acessosPortalPostal;
        this.usaConsolidador = usaConsolidador;
        this.acessosConsolidador = acessosConsolidador;
    }

    public Usuario(String nome, String cpf, int idEmpresa) {
        this.nome = nome;
        this.cpf = cpf;
        this.idEmpresa = idEmpresa;
    }

  

    
    
    public ArrayList<Integer> getListaAcessosPortalPostal() {
        listaAcessosPortalPostal = new ArrayList<Integer>();
        try{
            String[] aux = acessosPortalPostal.split(";");
            for (String ac : aux) {
                listaAcessosPortalPostal.add(Integer.parseInt(ac));
            }
        }catch(Exception e){    
            System.out.println(e);
        }
        return listaAcessosPortalPostal;
    }

    public void setListaAcessosPortalPostal(ArrayList<Integer> listaAcessosPortalPostal) {
        this.listaAcessosPortalPostal = listaAcessosPortalPostal;
    }

    public ArrayList<Integer> getListaAcessosConsolidador() {
        listaAcessosConsolidador = new ArrayList<Integer>();
        try{
            String[] aux = acessosConsolidador.split(";");
            for (String ac : aux) {
                listaAcessosConsolidador.add(Integer.parseInt(ac));
            }
        }catch(Exception e){            
        }
        return listaAcessosConsolidador;
    }

    public void setListaAcessosConsolidador(ArrayList<Integer> listaAcessosConsolidador) {
        this.listaAcessosConsolidador = listaAcessosConsolidador;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getAtivo() {
        return ativo;
    }

    public void setAtivo(int ativo) {
        this.ativo = ativo;
    }

    public int getUsaPortalPostal() {
        return usaPortalPostal;
    }

    public void setUsaPortalPostal(int usaPortalPostal) {
        this.usaPortalPostal = usaPortalPostal;
    }

    public String getAcessosPortalPostal() {
        return acessosPortalPostal;
    }

    public void setAcessosPortalPostal(String acessosPortalPostal) {
        this.acessosPortalPostal = acessosPortalPostal;
    }

    public int getUsaConsolidador() {
        return usaConsolidador;
    }

    public void setUsaConsolidador(int usaConsolidador) {
        this.usaConsolidador = usaConsolidador;
    }

    public String getAcessosConsolidador() {
        return acessosConsolidador;
    }

    public void setAcessosConsolidador(String acessosConsolidador) {
        this.acessosConsolidador = acessosConsolidador;
    }
    
    public int getIdNivel() {
        return idNivel;
    }

    public void setIdNivel(int idNivel) {
        this.idNivel = idNivel;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
}
