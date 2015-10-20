/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidade;

import java.sql.Timestamp;

/**
 *
 * @author Correios
 */
public class OrdemServico {
    
    private int idOs;
    private int idCliente;
    private int idUsuario;
    private String nomeUsuario;
    private Timestamp dataOs;
    private int qtdObjetos;

    public OrdemServico(int idOs, int idCliente, int idUsuario, String nomeUsuario, Timestamp dataOs, int qtdObjetos) {
        this.idOs = idOs;
        this.idCliente = idCliente;
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
        this.dataOs = dataOs;
        this.qtdObjetos = qtdObjetos;
    }

    public int getIdOs() {
        return idOs;
    }

    public void setIdOs(int idOs) {
        this.idOs = idOs;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public Timestamp getDataOs() {
        return dataOs;
    }

    public void setDataOs(Timestamp dataOs) {
        this.dataOs = dataOs;
    }

    public int getQtdObjetos() {
        return qtdObjetos;
    }

    public void setQtdObjetos(int qtdObjetos) {
        this.qtdObjetos = qtdObjetos;
    }
    
    
}
