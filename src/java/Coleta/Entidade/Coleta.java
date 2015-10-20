/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Coleta.Entidade;

import java.sql.Timestamp;

/**
 *
 * @author SCC4
 */
public class Coleta {
    
    private int idColeta;
    private int idCliente;
    private int idUsuario;
    private int idColetador;
    private int idContato;
    private int idTipo;
    private int status;
    private Timestamp dataHoraSolicitacao;
    private Timestamp dataHoraColeta;
    private Timestamp dataHoraBaixa;
    private String obs;
    private String nomeCliente;
    private String nomeFantasia;
    private String tipoColeta;
    private String nomeColetador;
    private int statusEntrega;
    private int tipoSolicitacao;
    private Timestamp dataHoraAguardando;
    private String nomeStatus;

    public Coleta(int idColeta, int idCliente, int idUsuario, int idColetador, int idContato, int idTipo, int status, Timestamp dataHoraSolicitacao, Timestamp dataHoraColeta, Timestamp dataHoraBaixa, String obs, int statusEntrega, int tipoSolicitacao, Timestamp dataHoraAguardando) {
        this.idColeta = idColeta;
        this.idCliente = idCliente;
        this.idUsuario = idUsuario;
        this.idColetador = idColetador;
        this.idContato = idContato;
        this.idTipo = idTipo;
        this.status = status;
        this.dataHoraSolicitacao = dataHoraSolicitacao;
        this.dataHoraColeta = dataHoraColeta;
        this.dataHoraBaixa = dataHoraBaixa;
        this.obs = obs;
        this.statusEntrega = statusEntrega;
        this.tipoSolicitacao = tipoSolicitacao;
        this.dataHoraAguardando = dataHoraAguardando;
    }

    public Coleta(int idColeta, int idCliente, Timestamp dataHoraColeta, Timestamp dataHoraBaixa, String obs, String nomeCliente, String nomeFantasia, String tipoColeta, String nomeColetador, Timestamp dataHoraSolicitacao, int statusEntrega, int tipoSolicitacao, int idColetador, Timestamp dataHoraAguardando, String nomeStatus) {
        this.idColeta = idColeta;
        this.idCliente = idCliente;
        this.dataHoraColeta = dataHoraColeta;
        this.dataHoraBaixa = dataHoraBaixa;
        this.obs = obs;
        this.nomeCliente = nomeCliente;
        this.nomeFantasia = nomeFantasia;
        this.tipoColeta = tipoColeta;
        this.nomeColetador = nomeColetador;
        this.dataHoraSolicitacao = dataHoraSolicitacao;
        this.statusEntrega = statusEntrega;
        this.tipoSolicitacao = tipoSolicitacao;
        this.idColetador = idColetador;
        this.dataHoraAguardando = dataHoraAguardando;
        this.nomeStatus = nomeStatus;
    }

    public String getNomeStatus() {
        return nomeStatus;
    }

    public void setNomeStatus(String nomeStatus) {
        this.nomeStatus = nomeStatus;
    }

    public Timestamp getDataHoraAguardando() {
        return dataHoraAguardando;
    }

    public void setDataHoraAguardando(Timestamp dataHoraAguardando) {
        this.dataHoraAguardando = dataHoraAguardando;
    }

    public int getStatusEntrega() {
        return statusEntrega;
    }

    public void setStatusEntrega(int statusEntrega) {
        this.statusEntrega = statusEntrega;
    }

    public int getTipoSolicitacao() {
        return tipoSolicitacao;
    }

    public void setTipoSolicitacao(int tipoSolicitacao) {
        this.tipoSolicitacao = tipoSolicitacao;
    }

    public Timestamp getDataHoraBaixa() {
        return dataHoraBaixa;
    }

    public void setDataHoraBaixa(Timestamp dataHoraBaixa) {
        this.dataHoraBaixa = dataHoraBaixa;
    }

    public Timestamp getDataHoraColeta() {
        return dataHoraColeta;
    }

    public void setDataHoraColeta(Timestamp dataHoraColeta) {
        this.dataHoraColeta = dataHoraColeta;
    }

    public Timestamp getDataHoraSolicitacao() {
        return dataHoraSolicitacao;
    }

    public void setDataHoraSolicitacao(Timestamp dataHoraSolicitacao) {
        this.dataHoraSolicitacao = dataHoraSolicitacao;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdColeta() {
        return idColeta;
    }

    public void setIdColeta(int idColeta) {
        this.idColeta = idColeta;
    }

    public int getIdColetador() {
        return idColetador;
    }

    public void setIdColetador(int idColetador) {
        this.idColetador = idColetador;
    }

    public int getIdContato() {
        return idContato;
    }

    public void setIdContato(int idContato) {
        this.idContato = idContato;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getNomeColetador() {
        return nomeColetador;
    }

    public void setNomeColetador(String nomeColetador) {
        this.nomeColetador = nomeColetador;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getTipoColeta() {
        return tipoColeta;
    }

    public void setTipoColeta(String tipoColeta) {
        this.tipoColeta = tipoColeta;
    }

}
