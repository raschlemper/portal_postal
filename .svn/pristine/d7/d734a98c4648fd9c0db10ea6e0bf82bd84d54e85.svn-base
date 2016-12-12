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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Viviane
 */
@Entity
@Table(name = "cliente_destinatario")
@NamedQueries({
    @NamedQuery(name = "ClienteDestinatario.findAll", query = "SELECT c FROM ClienteDestinatario c")})
public class ClienteDestinatario implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ClienteDestinatarioPK clienteDestinatarioPK;
    @Column(name = "idDepartamento")
    private Integer idDepartamento;
    @Column(name = "nome")
    private String nome;
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
    @Column(name = "pais")
    private String pais;
    @Column(name = "email")
    private String email;
    @Column(name = "celular")
    private String celular;
    @Lob
    @Column(name = "tags")
    private String tags;
    @Column(name = "chaveCliente")
    private String chaveCliente;
    @Column(name = "metodoInsert")
    private String metodoInsert;

    public ClienteDestinatario() {
    }

    public ClienteDestinatario(ClienteDestinatarioPK clienteDestinatarioPK) {
        this.clienteDestinatarioPK = clienteDestinatarioPK;
    }

    public ClienteDestinatario(int idDestinatario, int idCliente) {
        this.clienteDestinatarioPK = new ClienteDestinatarioPK(idDestinatario, idCliente);
    }

    public ClienteDestinatarioPK getClienteDestinatarioPK() {
        return clienteDestinatarioPK;
    }

    public void setClienteDestinatarioPK(ClienteDestinatarioPK clienteDestinatarioPK) {
        this.clienteDestinatarioPK = clienteDestinatarioPK;
    }

    public Integer getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getChaveCliente() {
        return chaveCliente;
    }

    public void setChaveCliente(String chaveCliente) {
        this.chaveCliente = chaveCliente;
    }

    public String getMetodoInsert() {
        return metodoInsert;
    }

    public void setMetodoInsert(String metodoInsert) {
        this.metodoInsert = metodoInsert;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clienteDestinatarioPK != null ? clienteDestinatarioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClienteDestinatario)) {
            return false;
        }
        ClienteDestinatario other = (ClienteDestinatario) object;
        if ((this.clienteDestinatarioPK == null && other.clienteDestinatarioPK != null) || (this.clienteDestinatarioPK != null && !this.clienteDestinatarioPK.equals(other.clienteDestinatarioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.portalpostal.entity.ClienteDestinatario[ clienteDestinatarioPK=" + clienteDestinatarioPK + " ]";
    }

}
