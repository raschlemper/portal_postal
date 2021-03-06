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
public class DadosPLP {

    private String sro;
    private int idPLP;
    private int idCliente;
    private int idDepartamento;
    private int idVendaLocal;
    private Timestamp dataHoraImportacao;
    private int status;
    private String contrato;
    private String cartaoPostagem;
    private String codigoAdministrativo;
    private int codECT;
    private String servico;
    private int ar;
    private int mp;
    private int pr;
    private int rg;
    private int rm;
    private float vd;
    private String notaFiscal;
    private Endereco endRemetente;
    private Endereco endDestinatario;
    private int isSincronizado;
    private int peso;
    private int altura;
    private int largura;
    private int comprimento;

    /*public DadosPLP(String sro, int idPLP, int idCliente, int idDepartamento, int idVendaLocal, Timestamp dataHoraImportacao, int status, String contrato, String cartaoPostagem, String codigoAdministrativo, int codECT, String servico, int ar, int mp, float vd, String notaFiscal, Endereco endRemetente, Endereco endDestinatario) {
        this.sro = sro;
        this.idPLP = idPLP;
        this.idCliente = idCliente;
        this.idDepartamento = idDepartamento;
        this.idVendaLocal = idVendaLocal;
        this.dataHoraImportacao = dataHoraImportacao;
        this.status = status;
        this.contrato = contrato;
        this.cartaoPostagem = cartaoPostagem;
        this.codigoAdministrativo = codigoAdministrativo;
        this.codECT = codECT;
        this.servico = servico;
        this.ar = ar;
        this.mp = mp;
        this.vd = vd;
        this.notaFiscal = notaFiscal;
        this.endRemetente = endRemetente;
        this.endDestinatario = endDestinatario;
    }*/

    public DadosPLP(ResultSet result) throws SQLException {
        
        this.sro = result.getString("sro");
        this.idPLP = result.getInt("idPLP");
        this.idCliente = result.getInt("idCliente");
        this.idDepartamento = result.getInt("idDepartamento");
        this.idVendaLocal = result.getInt("idVendaLocal");
        this.status = result.getInt("status");
        this.dataHoraImportacao = result.getTimestamp("dataHoraImportacao");
        
        this.contrato = result.getString("contrato");
        this.cartaoPostagem = result.getString("cartaoPostagem");
        this.codigoAdministrativo = result.getString("codAdministrativo");
        this.codECT = result.getInt("codECT");
        this.servico = result.getString("servico");
        
        this.ar = result.getInt("ar");
        this.mp = result.getInt("mp");
        this.pr = result.getInt("pr");
        this.rg = result.getInt("rg");
        this.rm = result.getInt("rm");
        this.vd = result.getFloat("vd");

        this.isSincronizado = result.getInt("isSincronizado");
        this.peso = result.getInt("peso");
        this.altura = result.getInt("altura");
        this.largura = result.getInt("largura");
        this.comprimento = result.getInt("comprimento");
        
        this.notaFiscal = result.getString("notaFiscal");
        
        Endereco endRem = new Endereco("", "", "", "", "", "", "", "");
        endRem.setNome(result.getString("nomeRemetente"));
        endRem.setCep(result.getString("cepRemetente"));
        endRem.setLogradouro(result.getString("enderecoRemetente"));
        endRem.setNumero(result.getString("numeroRemetente"));
        endRem.setComplemento(result.getString("complementoRemetente"));
        endRem.setBairro(result.getString("bairroRemetente"));
        endRem.setCidade(result.getString("cidadeRemetente"));
        endRem.setUf(result.getString("ufRemetente"));
        this.endRemetente = endRem;
        
        Endereco endDest = new Endereco("", "", "", "", "", "", "", "");
        endDest.setNome(result.getString("nomeDestinatario"));
        endDest.setCep(result.getString("cepDestinatario"));
        endDest.setLogradouro(result.getString("enderecoDestinatario"));
        endDest.setNumero(result.getString("numeroDestinatario"));
        endDest.setComplemento(result.getString("complementoDestinatario"));
        endDest.setBairro(result.getString("bairroDestinatario"));
        endDest.setCidade(result.getString("cidadeDestinatario"));
        endDest.setUf(result.getString("ufDestinatario"));
        this.endDestinatario = endDest;
        
    }

    public int getIsSincronizado() {
        return isSincronizado;
    }

    public void setIsSincronizado(int isSincronizado) {
        this.isSincronizado = isSincronizado;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getLargura() {
        return largura;
    }

    public void setLargura(int largura) {
        this.largura = largura;
    }

    public int getComprimento() {
        return comprimento;
    }

    public void setComprimento(int comprimento) {
        this.comprimento = comprimento;
    }

    public int getPr() {
        return pr;
    }

    public void setPr(int pr) {
        this.pr = pr;
    }

    public int getRg() {
        return rg;
    }

    public void setRg(int rg) {
        this.rg = rg;
    }

    public int getRm() {
        return rm;
    }

    public void setRm(int rm) {
        this.rm = rm;
    }

    public int getIdVendaLocal() {
        return idVendaLocal;
    }

    public void setIdVendaLocal(int idVendaLocal) {
        this.idVendaLocal = idVendaLocal;
    }

    public Timestamp getDataHoraImportacao() {
        return dataHoraImportacao;
    }

    public void setDataHoraImportacao(Timestamp dataHoraImportacao) {
        this.dataHoraImportacao = dataHoraImportacao;
    }

    public String getSro() {
        return sro;
    }

    public void setSro(String sro) {
        this.sro = sro;
    }

    public int getIdPLP() {
        return idPLP;
    }

    public void setIdPLP(int idPLP) {
        this.idPLP = idPLP;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public String getCartaoPostagem() {
        return cartaoPostagem;
    }

    public void setCartaoPostagem(String cartaoPostagem) {
        this.cartaoPostagem = cartaoPostagem;
    }

    public String getCodigoAdministrativo() {
        return codigoAdministrativo;
    }

    public void setCodigoAdministrativo(String codigoAdministrativo) {
        this.codigoAdministrativo = codigoAdministrativo;
    }

    public int getCodECT() {
        return codECT;
    }

    public void setCodECT(int codECT) {
        this.codECT = codECT;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public int getAr() {
        return ar;
    }

    public void setAr(int ar) {
        this.ar = ar;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public float getVd() {
        return vd;
    }

    public void setVd(float vd) {
        this.vd = vd;
    }

    public String getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(String notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    public Endereco getEndRemetente() {
        return endRemetente;
    }

    public void setEndRemetente(Endereco endRemetente) {
        this.endRemetente = endRemetente;
    }

    public Endereco getEndDestinatario() {
        return endDestinatario;
    }

    public void setEndDestinatario(Endereco endDestinatario) {
        this.endDestinatario = endDestinatario;
    }
    

}
