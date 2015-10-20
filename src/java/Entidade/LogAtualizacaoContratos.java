/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidade;

import java.sql.Timestamp;

/**
 *
 * @author Ricardo
 */
public class LogAtualizacaoContratos {

    private int id;
    private Timestamp dataHora;
    private String msgSucesso;
    private String msgFalha;
    private String codSucesso;
    private String codFalha;
    private int idUsuario;
    private String nomeUsuario;

    public LogAtualizacaoContratos(int id, Timestamp dataHora, String msgSucesso, String msgFalha, String codSucesso, String codFalha, int idUsuario, String nomeUsuario) {
        this.id = id;
        this.dataHora = dataHora;
        this.msgSucesso = msgSucesso;
        this.msgFalha = msgFalha;
        this.codSucesso = codSucesso;
        this.codFalha = codFalha;
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDataHora() {
        return dataHora;
    }

    public void setDataHora(Timestamp dataHora) {
        this.dataHora = dataHora;
    }

    public String getMsgSucesso() {
        return msgSucesso;
    }

    public void setMsgSucesso(String msgSucesso) {
        this.msgSucesso = msgSucesso;
    }

    public String getMsgFalha() {
        return msgFalha;
    }

    public void setMsgFalha(String msgFalha) {
        this.msgFalha = msgFalha;
    }

    public String getCodSucesso() {
        return codSucesso;
    }

    public void setCodSucesso(String codSucesso) {
        this.codSucesso = codSucesso;
    }

    public String getCodFalha() {
        return codFalha;
    }

    public void setCodFalha(String codFalha) {
        this.codFalha = codFalha;
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

}
