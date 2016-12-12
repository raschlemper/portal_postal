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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 *
 * @author Viviane
 */
@Embeddable
public class PreVendaDestinatarioPK implements Serializable {
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDestinatario")
    private int idDestinatario;
    @Basic(optional = false)
    @Column(name = "idCliente")
    private int idCliente;

    public PreVendaDestinatarioPK() {
    }

    public PreVendaDestinatarioPK(int idDestinatario, int idCliente) {
        this.idDestinatario = idDestinatario;
        this.idCliente = idCliente;
    }

    public int getIdDestinatario() {
        return idDestinatario;
    }

    public void setIdDestinatario(int idDestinatario) {
        this.idDestinatario = idDestinatario;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idDestinatario;
        hash += (int) idCliente;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PreVendaDestinatarioPK)) {
            return false;
        }
        PreVendaDestinatarioPK other = (PreVendaDestinatarioPK) object;
        if (this.idDestinatario != other.idDestinatario) {
            return false;
        }
        if (this.idCliente != other.idCliente) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.portalpostal.entity.PreVendaDestinatarioPK[ idDestinatario=" + idDestinatario + ", idCliente=" + idCliente + " ]";
    }

}
