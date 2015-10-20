/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidade;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author Rico
 */
public class HistoricoImport {

    private Date dataInicio;
    private Date dataFim;
    private Timestamp dataImportacao;
    private int tamanho;
    private int qtdCliente;
    private int idUsuario;
    private int qtdExcluido;

    public HistoricoImport(Date dataInicio, Date dataFim, Timestamp dataImportacao, int tamanho, int qtdCliente, int idUsuario, int qtdExcluido) {
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.dataImportacao = dataImportacao;
        this.tamanho = tamanho;
        this.qtdCliente = qtdCliente;
        this.idUsuario = idUsuario;
        this.qtdExcluido = qtdExcluido;
    }

    public HistoricoImport(Timestamp dataImportacao, int qtdCliente, int idUsuario, int qtdExcluido) {
        this.dataImportacao = dataImportacao;
        this.qtdCliente = qtdCliente;
        this.idUsuario = idUsuario;
        this.qtdExcluido = qtdExcluido;
    }

    public int getQtdExcluido() {
        return qtdExcluido;
    }

    public void setQtdExcluido(int qtdExcluido) {
        this.qtdExcluido = qtdExcluido;
    }


    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getQtdCliente() {
        return qtdCliente;
    }

    public void setQtdCliente(int qtdCliente) {
        this.qtdCliente = qtdCliente;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public Timestamp getDataImportacao() {
        return dataImportacao;
    }

    public void setDataImportacao(Timestamp dataImportacao) {
        this.dataImportacao = dataImportacao;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }
}
