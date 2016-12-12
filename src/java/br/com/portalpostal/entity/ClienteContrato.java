/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.portalpostal.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Viviane
 */
@Entity
@Table(name = "cliente_contrato")
@NamedQueries({
    @NamedQuery(name = "ClienteContrato.findAll", query = "SELECT c FROM ClienteContrato c"),
    @NamedQuery(name = "ClienteContrato.findByIdCliente", query = "SELECT c FROM ClienteContrato c WHERE c.clienteContratoPK.idCliente = :idCliente"),
    @NamedQuery(name = "ClienteContrato.findByCodECT", query = "SELECT c FROM ClienteContrato c WHERE c.codECT = :codECT"),
    @NamedQuery(name = "ClienteContrato.findByGrupoServico", query = "SELECT c FROM ClienteContrato c WHERE c.clienteContratoPK.idCliente = :idCliente AND c.clienteContratoPK.grupoServico = :grupoServico")})
public class ClienteContrato implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ClienteContratoPK clienteContratoPK;
    @Basic(optional = false)
    @Column(name = "codECT")
    private int codECT;

    @MapsId("idCliente")
    @JoinColumn(name="idCliente",referencedColumnName = "codigo")
    @ManyToOne(targetEntity = Cliente.class)
    public Cliente cliente;

    public ClienteContrato() {
    }

    public ClienteContrato(ClienteContratoPK clienteContratoPK) {
        this.clienteContratoPK = clienteContratoPK;
    }

    public ClienteContrato(ClienteContratoPK clienteContratoPK, int codECT) {
        this.clienteContratoPK = clienteContratoPK;
        this.codECT = codECT;
    }

    public ClienteContrato(int idCliente, String grupoServico) {
        this.clienteContratoPK = new ClienteContratoPK(idCliente, grupoServico);
    }

    public ClienteContratoPK getClienteContratoPK() {
        return clienteContratoPK;
    }

    public void setClienteContratoPK(ClienteContratoPK clienteContratoPK) {
        this.clienteContratoPK = clienteContratoPK;
    }

    public int getCodECT() {
        return codECT;
    }

    public void setCodECT(int codECT) {
        this.codECT = codECT;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clienteContratoPK != null ? clienteContratoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClienteContrato)) {
            return false;
        }
        ClienteContrato other = (ClienteContrato) object;
        if ((this.clienteContratoPK == null && other.clienteContratoPK != null) || (this.clienteContratoPK != null && !this.clienteContratoPK.equals(other.clienteContratoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.portalpostal.entity.ClienteContrato[ clienteContratoPK=" + clienteContratoPK + " ]";
    }

}
