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
public class ClienteDepartamentoPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "idCliente")
    private int idCliente;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDepartamento")
    private int idDepartamento;

    public ClienteDepartamentoPK() {
    }

    public ClienteDepartamentoPK(int idCliente, int idDepartamento) {
        this.idCliente = idCliente;
        this.idDepartamento = idDepartamento;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idCliente;
        hash += (int) idDepartamento;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClienteDepartamentoPK)) {
            return false;
        }
        ClienteDepartamentoPK other = (ClienteDepartamentoPK) object;
        if (this.idCliente != other.idCliente) {
            return false;
        }
        if (this.idDepartamento != other.idDepartamento) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.portalpostal.entity.ClienteDepartamentoPK[ idCliente=" + idCliente + ", idDepartamento=" + idDepartamento + " ]";
    }

}
