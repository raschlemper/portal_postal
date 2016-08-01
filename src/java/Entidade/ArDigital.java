/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidade;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author Ricardinho
 */
public class ArDigital {
    
    private int idCliente;
    private String siglaAR;
    private Date dataArquivo;
    private int sequenciaArquivo;
    private String nomeArquivo;
    private String nomeCliente;
    private int codClienteECT;
    private static final String[] ARRAY_SEQ = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

    public ArDigital(ResultSet r) throws SQLException {
        this.idCliente = r.getInt("idCliente");
        this.siglaAR = r.getString("siglaAR");
        this.dataArquivo = r.getDate("dataArquivo");
        this.sequenciaArquivo = r.getInt("sequenciaArquivo");
        this.nomeArquivo = r.getString("nomeArquivo");
        this.nomeCliente = r.getString("nomeCliente");
        this.codClienteECT = r.getInt("codClienteECT");
    }

    public int getCodClienteECT() {
        return codClienteECT;
    }

    public void setCodClienteECT(int codClienteECT) {
        this.codClienteECT = codClienteECT;
    }
    
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getSiglaAR() {
        return siglaAR;
    }

    public void setSiglaAR(String siglaAR) {
        this.siglaAR = siglaAR;
    }

    public Date getDataArquivo() {
        return dataArquivo;
    }

    public void setDataArquivo(Date dataArquivo) {
        this.dataArquivo = dataArquivo;
    }

    public int getSequenciaArquivo() {
        return sequenciaArquivo;
    }

    public void setSequenciaArquivo(int sequenciaArquivo) {
        this.sequenciaArquivo = sequenciaArquivo;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }
    
    public static String getSequenciaByNrSequencia(int seq){
        return ARRAY_SEQ[seq-1];
    }
    
    
}
