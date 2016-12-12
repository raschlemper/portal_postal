/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.portalpostal.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

/**
 *
 * @author Viviane
 */
@Entity
@Table(name = "pre_venda_destinatario")
@NamedQueries({
    @NamedQuery(name = "PreVendaDestinatario.findAll", query = "SELECT p FROM PreVendaDestinatario p")})
public class PreVendaDestinatario implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PreVendaDestinatarioPK preVendaDestinatarioPK;
    @Column(name = "nome")
    private String nome;
    @Column(name = "nome_sa")
    private String nomeSa;
    @Column(name = "cpf_cnpj")
    private String cpfCnpj;
    @Column(name = "empresa")
    private String empresa;
    @Column(name = "cep")
    private String cep;
    @Column(name = "endereco")
    private String endereco;
    @Column(name = "numero")
    private String numero;
    @Column(name = "complemento")
    private String complemento;
    @Column(name = "bairro")
    private String bairro;
    @Column(name = "cidade")
    private String cidade;
    @Column(name = "uf")
    private String uf;
    @Column(name = "email")
    private String email;
    @Column(name = "celular")
    private String celular;
    @Column(name = "pais")
    private String pais;

    
    @OneToOne
    @PrimaryKeyJoinColumns({
        @PrimaryKeyJoinColumn(name="idCliente", referencedColumnName="idCliente"),
        @PrimaryKeyJoinColumn(name="idDestinatario", referencedColumnName="idDestinatario")
    })
    private PreVenda preVenda;

    public PreVendaDestinatario() {
    }

    public PreVendaDestinatario(PreVendaDestinatarioPK preVendaDestinatarioPK) {
        this.preVendaDestinatarioPK = preVendaDestinatarioPK;
    }

    public PreVendaDestinatario(int idDestinatario, int idCliente) {
        this.preVendaDestinatarioPK = new PreVendaDestinatarioPK(idDestinatario, idCliente);
    }

    public PreVendaDestinatarioPK getPreVendaDestinatarioPK() {
        return preVendaDestinatarioPK;
    }

    public void setPreVendaDestinatarioPK(PreVendaDestinatarioPK preVendaDestinatarioPK) {
        this.preVendaDestinatarioPK = preVendaDestinatarioPK;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeSa() {
        return nomeSa;
    }

    public void setNomeSa(String nomeSa) {
        this.nomeSa = nomeSa;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public PreVenda getPreVenda() {
        return preVenda;
    }

    public void setPreVenda(PreVenda preVenda) {
        this.preVenda = preVenda;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (preVendaDestinatarioPK != null ? preVendaDestinatarioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof PreVendaDestinatario)) {
            return false;
        }
        PreVendaDestinatario other = (PreVendaDestinatario) object;
        if ((this.preVendaDestinatarioPK == null && other.preVendaDestinatarioPK != null) || (this.preVendaDestinatarioPK != null && !this.preVendaDestinatarioPK.equals(other.preVendaDestinatarioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.portalpostal.entity.PreVendaDestinatario[ preVendaDestinatarioPK=" + preVendaDestinatarioPK + " ]";
    }

}
