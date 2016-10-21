/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidade;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 * @author Fernando
 */
public class TelegramaPostal {
    
    private int id;
    private String sro;
    private int idCliente;
    private int idDepartamento;
    private String departamento;
    private Timestamp dataHora;
    private Timestamp dataHoraAgendado;
    private String userAgendado;
    private Timestamp dataHoraEnviado;
    private String userEnviado;
    private int status;
    private int tipoEnvio;
    private String adicionais;
    private String envioCopia;
    private String emailCopia;
    private String mensagem;
    private Endereco enderecoRem;
    private Endereco enderecoDes;
    private float valor;

    public TelegramaPostal(ResultSet result) throws SQLException {
        this.id = result.getInt("id");
        this.sro = result.getString("sro");
        this.idCliente = result.getInt("idCliente");
        this.idDepartamento = result.getInt("idDepartamento");
        this.departamento = result.getString("departamento");
        this.dataHora = result.getTimestamp("dataHora");
        this.dataHoraAgendado = result.getTimestamp("dataHoraAgendado");
        this.userAgendado = result.getString("userAgendado");
        this.dataHoraEnviado = result.getTimestamp("dataHoraEnviado");
        this.userEnviado = result.getString("userEnviado");
        this.status = result.getInt("status");
        this.tipoEnvio = result.getInt("tipoEnvio");
        this.adicionais = result.getString("adicionais");
        this.envioCopia = result.getString("envioCopia");
        this.emailCopia = result.getString("emailCopia");
        this.mensagem = result.getString("mensagem");
        this.valor = result.getFloat("valor");
        this.enderecoRem = new Endereco(result.getString("nomeRem"), result.getString("enderecoRem"), result.getString("numeroRem"), result.getString("complementoRem"), result.getString("bairroRem"), result.getString("cidadeRem"), result.getString("ufRem"), result.getString("cepRem"));
        this.enderecoDes = new Endereco(result.getString("nomeDes"), result.getString("enderecoDes"), result.getString("numeroDes"), result.getString("complementoDes"), result.getString("bairroDes"), result.getString("cidadeDes"), result.getString("ufDes"), result.getString("cepDes"));
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getSro() {
        return sro;
    }

    public void setSro(String sro) {
        this.sro = sro;
    }

    public String getEmailCopia() {
        return emailCopia;
    }

    public void setEmailCopia(String emailCopia) {
        this.emailCopia = emailCopia;
    }    

    public String getUserAgendado() {
        return userAgendado;
    }

    public void setUserAgendado(String userAgendado) {
        this.userAgendado = userAgendado;
    }

    public String getUserEnviado() {
        return userEnviado;
    }

    public void setUserEnviado(String userEnviado) {
        this.userEnviado = userEnviado;
    }

    public int getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public Timestamp getDataHoraEnviado() {
        return dataHoraEnviado;
    }

    public void setDataHoraEnviado(Timestamp dataHoraEnviado) {
        this.dataHoraEnviado = dataHoraEnviado;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public Timestamp getDataHora() {
        return dataHora;
    }

    public void setDataHora(Timestamp dataHora) {
        this.dataHora = dataHora;
    }

    public Timestamp getDataHoraAgendado() {
        return dataHoraAgendado;
    }

    public void setDataHoraAgendado(Timestamp dataHoraAgendado) {
        this.dataHoraAgendado = dataHoraAgendado;
    }

    public int getTipoEnvio() {
        return tipoEnvio;
    }

    public void setTipoEnvio(int tipoEnvio) {
        this.tipoEnvio = tipoEnvio;
    }

    public String getAdicionais() {
        return adicionais;
    }

    public void setAdicionais(String adicionais) {
        this.adicionais = adicionais;
    }

    public String getEnvioCopia() {
        return envioCopia;
    }

    public void setEnvioCopia(String envioCopia) {
        this.envioCopia = envioCopia;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Endereco getEnderecoRem() {
        return enderecoRem;
    }

    public void setEnderecoRem(Endereco enderecoRem) {
        this.enderecoRem = enderecoRem;
    }

    public Endereco getEnderecoDes() {
        return enderecoDes;
    }

    public void setEnderecoDes(Endereco enderecoDes) {
        this.enderecoDes = enderecoDes;
    }
    
}
