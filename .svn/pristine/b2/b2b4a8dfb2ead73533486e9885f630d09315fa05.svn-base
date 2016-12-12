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
public class PreVendaPK implements Serializable {
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Basic(optional = false)
    @Column(name = "numObjeto")
    private String numObjeto;

    public PreVendaPK() {
    }

    public PreVendaPK(int id, String numObjeto) {
        this.id = id;
        this.numObjeto = numObjeto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumObjeto() {
        return numObjeto;
    }

    public void setNumObjeto(String numObjeto) {
        this.numObjeto = numObjeto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        hash += (numObjeto != null ? numObjeto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PreVendaPK)) {
            return false;
        }
        PreVendaPK other = (PreVendaPK) object;
        if (this.id != other.id) {
            return false;
        }
        if ((this.numObjeto == null && other.numObjeto != null) || (this.numObjeto != null && !this.numObjeto.equals(other.numObjeto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.portalpostal.entity.PreVendaPK[ id=" + id + ", numObjeto=" + numObjeto + " ]";
    }

}
