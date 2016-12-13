

package br.com.portalpostal.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "coleta")
@NamedQueries({
    @NamedQuery(name = "Coleta.findAll", query = "SELECT c FROM Coleta c")})
public class Coleta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idColeta")
    private Integer idColeta;
    @Basic(optional = false)
    @Column(name = "idCliente")
    private int idCliente;
    @Basic(optional = false)
    @Column(name = "idUsuario")
    private int idUsuario;
    @Basic(optional = false)
    @Column(name = "idColetador")
    private int idColetador;
    @Basic(optional = false)
    @Column(name = "idContato")
    private int idContato;
    @Basic(optional = false)
    @Column(name = "idTipo")
    private int idTipo;
    @Basic(optional = false)
    @Column(name = "status")
    private int status;
    @Basic(optional = false)
    @Column(name = "dataHoraSolicitacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraSolicitacao;
    @Basic(optional = false)
    @Column(name = "dataHoraColeta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraColeta;
    @Column(name = "dataHoraBaixa")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraBaixa;
    @Column(name = "dataHoraAguardando")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraAguardando;
    @Column(name = "obs")
    private String obs;
    @Column(name = "tipoSolicitacao")
    private Integer tipoSolicitacao;
    @Column(name = "statusEntrega")
    private Integer statusEntrega;
    @Column(name = "idDepartamento")
    private Integer idDepartamento;

    public Coleta() {
    }

    public Coleta(Integer idColeta) {
        this.idColeta = idColeta;
    }

    public Coleta(Integer idColeta, int idCliente, int idUsuario, int idColetador, int idContato, int idTipo, int status, Date dataHoraSolicitacao, Date dataHoraColeta) {
        this.idColeta = idColeta;
        this.idCliente = idCliente;
        this.idUsuario = idUsuario;
        this.idColetador = idColetador;
        this.idContato = idContato;
        this.idTipo = idTipo;
        this.status = status;
        this.dataHoraSolicitacao = dataHoraSolicitacao;
        this.dataHoraColeta = dataHoraColeta;
    }

    public Integer getIdColeta() {
        return idColeta;
    }

    public void setIdColeta(Integer idColeta) {
        this.idColeta = idColeta;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdColetador() {
        return idColetador;
    }

    public void setIdColetador(int idColetador) {
        this.idColetador = idColetador;
    }

    public int getIdContato() {
        return idContato;
    }

    public void setIdContato(int idContato) {
        this.idContato = idContato;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getDataHoraSolicitacao() {
        return dataHoraSolicitacao;
    }

    public void setDataHoraSolicitacao(Date dataHoraSolicitacao) {
        this.dataHoraSolicitacao = dataHoraSolicitacao;
    }

    public Date getDataHoraColeta() {
        return dataHoraColeta;
    }

    public void setDataHoraColeta(Date dataHoraColeta) {
        this.dataHoraColeta = dataHoraColeta;
    }

    public Date getDataHoraBaixa() {
        return dataHoraBaixa;
    }

    public void setDataHoraBaixa(Date dataHoraBaixa) {
        this.dataHoraBaixa = dataHoraBaixa;
    }

    public Date getDataHoraAguardando() {
        return dataHoraAguardando;
    }

    public void setDataHoraAguardando(Date dataHoraAguardando) {
        this.dataHoraAguardando = dataHoraAguardando;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Integer getTipoSolicitacao() {
        return tipoSolicitacao;
    }

    public void setTipoSolicitacao(Integer tipoSolicitacao) {
        this.tipoSolicitacao = tipoSolicitacao;
    }

    public Integer getStatusEntrega() {
        return statusEntrega;
    }

    public void setStatusEntrega(Integer statusEntrega) {
        this.statusEntrega = statusEntrega;
    }

    public Integer getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idColeta != null ? idColeta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Coleta)) {
            return false;
        }
        Coleta other = (Coleta) object;
        if ((this.idColeta == null && other.idColeta != null) || (this.idColeta != null && !this.idColeta.equals(other.idColeta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.portalpostal.entity.Coleta[ idColeta=" + idColeta + " ]";
    }

}
