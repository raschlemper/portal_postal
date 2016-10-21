/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidade;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 *
 * @author scc4
 */
public class LogisticaReversa {
    
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private int id;
    private int idCliente;
    private int range;
    private int cod_ap;
    private int qtdObjeto;
    private String numObjeto;
    private String tipo_ap;
    private String tipo_serv;
    private int ar;
    private float vd;
    private String nome_des;
    private String endereco_des;
    private String numero_des;
    private String complemento_des;
    private String bairro_des;
    private String cidade_des;
    private String uf_des;
    private String cep_des;
    private String email_des;
    private String nome_rem;
    private String endereco_rem;
    private String numero_rem;
    private String complemento_rem;
    private String bairro_rem;
    private String cidade_rem;
    private String uf_rem;
    private String cep_rem;
    private String email_rem;
    private String ddd_rem;
    private String celular_rem;
    private String sms_rem;
    private String dataSolicitacao;
    private String userSolicitacao;
    private int cancelado;
    private String status;
    private String descricaoStatus;
    private String dataStatus;
    private String horaStatus;

    public LogisticaReversa(ResultSet result) throws SQLException {
        this.id = result.getInt("id");
        this.idCliente = result.getInt("idCliente");
        this.range = result.getInt("range_rev");
        this.cod_ap = result.getInt("cod_ap");
        this.numObjeto = result.getString("numObjeto");
        this.tipo_ap = result.getString("tipo_ap");
        this.tipo_serv = result.getString("tipo_serv");
        this.ar = result.getInt("ar");
        this.vd = result.getFloat("vd");
        this.nome_des = result.getString("nome_des");
        this.endereco_des = result.getString("endereco_des");
        this.numero_des = result.getString("numero_des");
        this.complemento_des = result.getString("complemento_des");
        this.bairro_des = result.getString("bairro_des");
        this.cidade_des = result.getString("cidade_des");
        this.uf_des = result.getString("uf_des");
        this.cep_des = result.getString("cep_des");
        this.email_des = result.getString("email_des");
        this.nome_rem = result.getString("nome_rem");
        this.endereco_rem = result.getString("endereco_rem");
        this.numero_rem = result.getString("numero_rem");
        this.complemento_rem = result.getString("complemento_rem");
        this.bairro_rem = result.getString("bairro_rem");
        this.cidade_rem = result.getString("cidade_rem");
        this.uf_rem = result.getString("uf_rem");
        this.cep_rem = result.getString("cep_rem");
        this.email_rem = result.getString("email_rem");
        this.ddd_rem = result.getString("ddd_rem");
        this.celular_rem = result.getString("celular_rem");
        this.sms_rem = result.getString("sms_rem");
        this.dataSolicitacao = sdf.format(result.getTimestamp("dataSolicitacao"));
        this.userSolicitacao = result.getString("userSolicitacao");
        this.cancelado = result.getInt("cancelado");
        this.status = result.getString("status");
        this.descricaoStatus = result.getString("descricaoStatus");
        this.dataStatus = result.getString("dataStatus");
        this.horaStatus = result.getString("horaStatus");
        this.qtdObjeto = result.getInt("qtdObjeto");
    }

    public int getQtdObjeto() {
        return qtdObjeto;
    }

    public void setQtdObjeto(int qtdObjeto) {
        this.qtdObjeto = qtdObjeto;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescricaoStatus() {
        return descricaoStatus;
    }

    public void setDescricaoStatus(String descricaoStatus) {
        this.descricaoStatus = descricaoStatus;
    }

    public String getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus;
    }

    public String getHoraStatus() {
        return horaStatus;
    }

    public void setHoraStatus(String horaStatus) {
        this.horaStatus = horaStatus;
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

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getCod_ap() {
        return cod_ap;
    }

    public void setCod_ap(int cod_ap) {
        this.cod_ap = cod_ap;
    }

    public String getNumObjeto() {
        return numObjeto;
    }

    public void setNumObjeto(String numObjeto) {
        this.numObjeto = numObjeto;
    }

    public String getTipo_ap() {
        return tipo_ap;
    }

    public void setTipo_ap(String tipo_ap) {
        this.tipo_ap = tipo_ap;
    }

    public String getTipo_serv() {
        return tipo_serv;
    }

    public void setTipo_serv(String tipo_serv) {
        this.tipo_serv = tipo_serv;
    }

    public int getAr() {
        return ar;
    }

    public void setAr(int ar) {
        this.ar = ar;
    }

    public float getVd() {
        return vd;
    }

    public void setVd(float vd) {
        this.vd = vd;
    }

    public String getNome_des() {
        return nome_des;
    }

    public void setNome_des(String nome_des) {
        this.nome_des = nome_des;
    }

    public String getEndereco_des() {
        return endereco_des;
    }

    public void setEndereco_des(String endereco_des) {
        this.endereco_des = endereco_des;
    }

    public String getNumero_des() {
        return numero_des;
    }

    public void setNumero_des(String numero_des) {
        this.numero_des = numero_des;
    }

    public String getComplemento_des() {
        return complemento_des;
    }

    public void setComplemento_des(String complemento_des) {
        this.complemento_des = complemento_des;
    }

    public String getBairro_des() {
        return bairro_des;
    }

    public void setBairro_des(String bairro_des) {
        this.bairro_des = bairro_des;
    }

    public String getCidade_des() {
        return cidade_des;
    }

    public void setCidade_des(String cidade_des) {
        this.cidade_des = cidade_des;
    }

    public String getUf_des() {
        return uf_des;
    }

    public void setUf_des(String uf_des) {
        this.uf_des = uf_des;
    }

    public String getCep_des() {
        return cep_des;
    }

    public void setCep_des(String cep_des) {
        this.cep_des = cep_des;
    }

    public String getEmail_des() {
        return email_des;
    }

    public void setEmail_des(String email_des) {
        this.email_des = email_des;
    }

    public String getNome_rem() {
        return nome_rem;
    }

    public void setNome_rem(String nome_rem) {
        this.nome_rem = nome_rem;
    }

    public String getEndereco_rem() {
        return endereco_rem;
    }

    public void setEndereco_rem(String endereco_rem) {
        this.endereco_rem = endereco_rem;
    }

    public String getNumero_rem() {
        return numero_rem;
    }

    public void setNumero_rem(String numero_rem) {
        this.numero_rem = numero_rem;
    }

    public String getComplemento_rem() {
        return complemento_rem;
    }

    public void setComplemento_rem(String complemento_rem) {
        this.complemento_rem = complemento_rem;
    }

    public String getBairro_rem() {
        return bairro_rem;
    }

    public void setBairro_rem(String bairro_rem) {
        this.bairro_rem = bairro_rem;
    }

    public String getCidade_rem() {
        return cidade_rem;
    }

    public void setCidade_rem(String cidade_rem) {
        this.cidade_rem = cidade_rem;
    }

    public String getUf_rem() {
        return uf_rem;
    }

    public void setUf_rem(String uf_rem) {
        this.uf_rem = uf_rem;
    }

    public String getCep_rem() {
        return cep_rem;
    }

    public void setCep_rem(String cep_rem) {
        this.cep_rem = cep_rem;
    }

    public String getEmail_rem() {
        return email_rem;
    }

    public void setEmail_rem(String email_rem) {
        this.email_rem = email_rem;
    }

    public String getDdd_rem() {
        return ddd_rem;
    }

    public void setDdd_rem(String ddd_rem) {
        this.ddd_rem = ddd_rem;
    }

    public String getCelular_rem() {
        return celular_rem;
    }

    public void setCelular_rem(String celular_rem) {
        this.celular_rem = celular_rem;
    }

    public String getSms_rem() {
        return sms_rem;
    }

    public void setSms_rem(String sms_rem) {
        this.sms_rem = sms_rem;
    }

    public String getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(String dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public String getUserSolicitacao() {
        return userSolicitacao;
    }

    public void setUserSolicitacao(String userSolicitacao) {
        this.userSolicitacao = userSolicitacao;
    }

    public int getCancelado() {
        return cancelado;
    }

    public void setCancelado(int cancelado) {
        this.cancelado = cancelado;
    }
    
}
