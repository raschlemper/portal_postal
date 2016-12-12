/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.portalpostal.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Viviane
 */
@Embeddable
public class ClienteContratoPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "idCliente")
    private int idCliente;
    @Basic(optional = false)
    @Column(name = "grupoServico")
    private String grupoServico;

    public ClienteContratoPK() {
    }

    public ClienteContratoPK(int idCliente, String grupoServico) {
        this.idCliente = idCliente;
        this.grupoServico = grupoServico;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getGrupoServico() {
        return grupoServico;
    }

    public void setGrupoServico(String grupoServico) {
        this.grupoServico = grupoServico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idCliente;
        hash += (grupoServico != null ? grupoServico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClienteContratoPK)) {
            return false;
        }
        ClienteContratoPK other = (ClienteContratoPK) object;
        if (this.idCliente != other.idCliente) {
            return false;
        }
        if ((this.grupoServico == null && other.grupoServico != null) || (this.grupoServico != null && !this.grupoServico.equals(other.grupoServico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.portalpostal.entity.ClienteContratoPK[ idCliente=" + idCliente + ", grupoServico=" + grupoServico + " ]";
    }

}
