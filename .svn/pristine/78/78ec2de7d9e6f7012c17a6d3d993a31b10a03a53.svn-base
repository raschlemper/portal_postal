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
 * @author RICARDINHO
 */
public class ClienteLogEtiqueta {
    
    private int idLog;
    private int idCliente;
    private int idUsuario;
    private String nomeUsuario;
    private String faixaIni;
    private String faixaFim;
    private Timestamp dataHora;
    private int qtd;
    private String servico;
    private String nomeServico;
    private String cartaoPostagem;
    private String codAdiministrativo; 
    private String tipoGeracao; 
    private String tipoUso;
    private int avista;
    private int qtdUtilizada;

    public ClienteLogEtiqueta(int idLog, int idCliente, int idUsuario, String nomeUsuario, String faixaIni, String faixaFim, Timestamp dataHora, int qtd, String servico, String nomeServico) {
        this.idLog = idLog;
        this.idCliente = idCliente;
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
        this.faixaIni = faixaIni;
        this.faixaFim = faixaFim;
        this.dataHora = dataHora;
        this.qtd = qtd;
        this.servico = servico;
        this.nomeServico = nomeServico;
    }

    public ClienteLogEtiqueta(int idLog, int idCliente, int idUsuario, String nomeUsuario, String faixaIni, String faixaFim, Timestamp dataHora, int qtd, String servico, String nomeServico, String tipoGeracao, String tipoUso) {
        this.idLog = idLog;
        this.idCliente = idCliente;
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
        this.faixaIni = faixaIni;
        this.faixaFim = faixaFim;
        this.dataHora = dataHora;
        this.qtd = qtd;
        this.servico = servico;
        this.nomeServico = nomeServico;
        this.tipoGeracao = tipoGeracao;
        this.tipoUso = tipoUso;
    }

    public ClienteLogEtiqueta(ResultSet result, int qtdUtilizada) throws SQLException {
        this.idLog = result.getInt("id");
        this.idCliente = result.getInt("idCliente");
        this.idUsuario = result.getInt("idUsuario");
        this.nomeUsuario = result.getString("nomeUsuario");
        this.faixaIni = result.getString("faixaIni");
        this.faixaFim = result.getString("faixaFim");
        this.dataHora = result.getTimestamp("dataHora");
        this.qtd = result.getInt("qtd");
        this.servico = result.getString("servico");
        this.nomeServico = "";
        this.tipoGeracao = result.getString("tipoGeracao");
        this.tipoUso = result.getString("tipoUso");
        this.avista = result.getInt("avista");
        this.qtdUtilizada = qtdUtilizada;
    }

    public int getAvista() {
        return avista;
    }

    public void setAvista(int avista) {
        this.avista = avista;
    }

    public int getQtdUtilizada() {
        return qtdUtilizada;
    }

    public void setQtdUtilizada(int qtdUtilizada) {
        this.qtdUtilizada = qtdUtilizada;
    }

    public String getTipoGeracao() {
        return tipoGeracao;
    }

    public void setTipoGeracao(String tipoGeracao) {
        this.tipoGeracao = tipoGeracao;
    }

    public String getTipoUso() {
        return tipoUso;
    }

    public void setTipoUso(String tipoUso) {
        this.tipoUso = tipoUso;
    }
    

    public String getCartaoPostagem() {
        return cartaoPostagem;
    }

    public void setCartaoPostagem(String cartaoPostagem) {
        this.cartaoPostagem = cartaoPostagem;
    }

    public String getCodAdiministrativo() {
        return codAdiministrativo;
    }

    public void setCodAdiministrativo(String codAdiministrativo) {
        this.codAdiministrativo = codAdiministrativo;
    }

    public Timestamp getDataHora() {
        return dataHora;
    }

    public void setDataHora(Timestamp dataHora) {
        this.dataHora = dataHora;
    }

    public String getFaixaFim() {
        return faixaFim;
    }

    public void setFaixaFim(String faixaFim) {
        this.faixaFim = faixaFim;
    }

    public String getFaixaIni() {
        return faixaIni;
    }

    public void setFaixaIni(String faixaIni) {
        this.faixaIni = faixaIni;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdLog() {
        return idLog;
    }

    public void setIdLog(int idLog) {
        this.idLog = idLog;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeServico() {
        return nomeServico;
    }

    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }
    
}
