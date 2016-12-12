/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.portalpostal.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Viviane
 */
@Entity
@Table(name = "servicos_ect")
@NamedQueries({
    @NamedQuery(name = "ServicosEct.findAll", query = "SELECT s FROM ServicosEct s"),
    @NamedQuery(name = "ServicosEct.findByCodECT", query = "SELECT s FROM ServicosEct s WHERE s.codECT = :codECT"),
    @NamedQuery(name = "ServicosEct.findServicos", query = "SELECT s FROM ServicosEct s WHERE s.ativo=1")})
   
public class ServicosEct implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codECT")
    private Integer codECT;
    @Column(name = "codECT_reverso")
    private Integer codECTreverso;
    @Column(name = "idServicoECT")
    private Integer idServicoECT;
    @Column(name = "nomeServico")
    private String nomeServico;
    @Column(name = "nomeSimples")
    private String nomeSimples;
    @Column(name = "grupoServico")
    private String grupoServico;
    @Column(name = "avista")
    private Integer avista;
    @Column(name = "faturar")
    private Integer faturar;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "ativo")
    private Integer ativo;
    @Column(name = "tipo_agencia")
    private String tipoAgencia;
    @Column(name = "tem_registro")
    private Integer temRegistro;
    @Column(name = "tem_peso")
    private Integer temPeso;
    @Column(name = "tem_medida")
    private Integer temMedida;
    @Column(name = "tem_qtd")
    private Integer temQtd;
    @Column(name = "tem_destinatario")
    private Integer temDestinatario;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "peso_max")
    private Float pesoMax;
    @Column(name = "peso_cubico_min")
    private Float pesoCubicoMin;
    @Column(name = "altura_min")
    private Float alturaMin;
    @Column(name = "largura_min")
    private Float larguraMin;
    @Column(name = "comprimento_min")
    private Float comprimentoMin;
    @Column(name = "maior_dimensao_max")
    private Float maiorDimensaoMax;
    @Column(name = "soma_dimensoes_max")
    private Float somaDimensoesMax;
    @Column(name = "adicionais_permitidos")
    private String adicionaisPermitidos;
    @Column(name = "internacional")
    private Integer internacional;
    @Column(name = "confere_abrangencia")
    private Integer confereAbrangencia;
    @Column(name = "is_debito")
    private Integer isDebito;
    @Column(name = "grupo_remuneracao")
    private Integer grupoRemuneracao;
    @Column(name = "porcent_remuneracao")
    private Float porcentRemuneracao;

    public ServicosEct() {
    }

    public ServicosEct(Integer codECT) {
        this.codECT = codECT;
    }

    public Integer getCodECT() {
        return codECT;
    }

    public void setCodECT(Integer codECT) {
        this.codECT = codECT;
    }

    public Integer getCodECTreverso() {
        return codECTreverso;
    }

    public void setCodECTreverso(Integer codECTreverso) {
        this.codECTreverso = codECTreverso;
    }

    public Integer getIdServicoECT() {
        return idServicoECT;
    }

    public void setIdServicoECT(Integer idServicoECT) {
        this.idServicoECT = idServicoECT;
    }

    public String getNomeServico() {
        return nomeServico;
    }

    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
    }

    public String getNomeSimples() {
        return nomeSimples;
    }

    public void setNomeSimples(String nomeSimples) {
        this.nomeSimples = nomeSimples;
    }

    public String getGrupoServico() {
        return grupoServico;
    }

    public void setGrupoServico(String grupoServico) {
        this.grupoServico = grupoServico;
    }

    public Integer getAvista() {
        return avista;
    }

    public void setAvista(Integer avista) {
        this.avista = avista;
    }

    public Integer getFaturar() {
        return faturar;
    }

    public void setFaturar(Integer faturar) {
        this.faturar = faturar;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getAtivo() {
        return ativo;
    }

    public void setAtivo(Integer ativo) {
        this.ativo = ativo;
    }

    public String getTipoAgencia() {
        return tipoAgencia;
    }

    public void setTipoAgencia(String tipoAgencia) {
        this.tipoAgencia = tipoAgencia;
    }

    public Integer getTemRegistro() {
        return temRegistro;
    }

    public void setTemRegistro(Integer temRegistro) {
        this.temRegistro = temRegistro;
    }

    public Integer getTemPeso() {
        return temPeso;
    }

    public void setTemPeso(Integer temPeso) {
        this.temPeso = temPeso;
    }

    public Integer getTemMedida() {
        return temMedida;
    }

    public void setTemMedida(Integer temMedida) {
        this.temMedida = temMedida;
    }

    public Integer getTemQtd() {
        return temQtd;
    }

    public void setTemQtd(Integer temQtd) {
        this.temQtd = temQtd;
    }

    public Integer getTemDestinatario() {
        return temDestinatario;
    }

    public void setTemDestinatario(Integer temDestinatario) {
        this.temDestinatario = temDestinatario;
    }

    public Float getPesoMax() {
        return pesoMax;
    }

    public void setPesoMax(Float pesoMax) {
        this.pesoMax = pesoMax;
    }

    public Float getPesoCubicoMin() {
        return pesoCubicoMin;
    }

    public void setPesoCubicoMin(Float pesoCubicoMin) {
        this.pesoCubicoMin = pesoCubicoMin;
    }

    public Float getAlturaMin() {
        return alturaMin;
    }

    public void setAlturaMin(Float alturaMin) {
        this.alturaMin = alturaMin;
    }

    public Float getLarguraMin() {
        return larguraMin;
    }

    public void setLarguraMin(Float larguraMin) {
        this.larguraMin = larguraMin;
    }

    public Float getComprimentoMin() {
        return comprimentoMin;
    }

    public void setComprimentoMin(Float comprimentoMin) {
        this.comprimentoMin = comprimentoMin;
    }

    public Float getMaiorDimensaoMax() {
        return maiorDimensaoMax;
    }

    public void setMaiorDimensaoMax(Float maiorDimensaoMax) {
        this.maiorDimensaoMax = maiorDimensaoMax;
    }

    public Float getSomaDimensoesMax() {
        return somaDimensoesMax;
    }

    public void setSomaDimensoesMax(Float somaDimensoesMax) {
        this.somaDimensoesMax = somaDimensoesMax;
    }

    public String getAdicionaisPermitidos() {
        return adicionaisPermitidos;
    }

    public void setAdicionaisPermitidos(String adicionaisPermitidos) {
        this.adicionaisPermitidos = adicionaisPermitidos;
    }

    public Integer getInternacional() {
        return internacional;
    }

    public void setInternacional(Integer internacional) {
        this.internacional = internacional;
    }

    public Integer getConfereAbrangencia() {
        return confereAbrangencia;
    }

    public void setConfereAbrangencia(Integer confereAbrangencia) {
        this.confereAbrangencia = confereAbrangencia;
    }

    public Integer getIsDebito() {
        return isDebito;
    }

    public void setIsDebito(Integer isDebito) {
        this.isDebito = isDebito;
    }

    public Integer getGrupoRemuneracao() {
        return grupoRemuneracao;
    }

    public void setGrupoRemuneracao(Integer grupoRemuneracao) {
        this.grupoRemuneracao = grupoRemuneracao;
    }

    public Float getPorcentRemuneracao() {
        return porcentRemuneracao;
    }

    public void setPorcentRemuneracao(Float porcentRemuneracao) {
        this.porcentRemuneracao = porcentRemuneracao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codECT != null ? codECT.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ServicosEct)) {
            return false;
        }
        ServicosEct other = (ServicosEct) object;
        if ((this.codECT == null && other.codECT != null) || (this.codECT != null && !this.codECT.equals(other.codECT))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.portalpostal.entity.ServicosEct[ codECT=" + codECT + " ]";
    }

}
