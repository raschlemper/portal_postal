
package br.com.portalpostal.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Viviane
 */
@Entity
@Table(name = "pre_venda")
@NamedQueries({
    @NamedQuery(name = "PreVenda.findAll", query = "SELECT p FROM PreVenda p")})
public class PreVenda implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PreVendaPK preVendaPK;
    @Column(name = "idCliente")
    private Integer idCliente;
    @Column(name = "idRemetente")
    private Integer idRemetente;
    @Column(name = "idDestinatario")
    private Integer idDestinatario;
    @Column(name = "idDepartamento")
    private Integer idDepartamento;
    @Column(name = "codECT")
    private Integer codECT;
    @Column(name = "nomeServico")
    private String nomeServico;
    @Column(name = "tipoEncomenda")
    private String tipoEncomenda;
    @Column(name = "contrato")
    private String contrato;
    @Column(name = "cartaoPostagem")
    private String cartaoPostagem;
    @Column(name = "departamento")
    private String departamento;
    @Column(name = "setor")
    private String setor;
    @Column(name = "aos_cuidados")
    private String aosCuidados;
    @Column(name = "observacoes")
    private String observacoes;
    @Column(name = "conteudo")
    private String conteudo;
    @Column(name = "notaFiscal")
    private String notaFiscal;
    @Column(name = "siglaAmarracao")
    private String siglaAmarracao;
    @Column(name = "peso")
    private Integer peso;
    @Column(name = "altura")
    private Integer altura;
    @Column(name = "largura")
    private Integer largura;
    @Column(name = "comprimento")
    private Integer comprimento;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor_declarado")
    private Float valorDeclarado;
    @Column(name = "valor_cobrar")
    private Float valorCobrar;
    @Column(name = "mao_propria")
    private Integer maoPropria;
    @Column(name = "aviso_recebimento")
    private Integer avisoRecebimento;
    @Column(name = "posta_restante")
    private Short postaRestante;
    @Column(name = "registro")
    private Integer registro;
    @Column(name = "registro_modico")
    private Short registroModico;
    @Column(name = "impresso")
    private Integer impresso;
    @Column(name = "impresso_ar")
    private Integer impressoAr;
    @Column(name = "consolidado")
    private Integer consolidado;
    @Column(name = "dataPreVenda")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPreVenda;
    @Column(name = "userPreVenda")
    private Integer userPreVenda;
    @Column(name = "nomePreVenda")
    private String nomePreVenda;
    @Column(name = "dataVenda")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataVenda;
    @Column(name = "userVenda")
    private Integer userVenda;
    @Column(name = "nomeVenda")
    private String nomeVenda;
    @Column(name = "dataImpressao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataImpressao;
    @Column(name = "userImpressao")
    private Integer userImpressao;
    @Column(name = "nomeImpressao")
    private String nomeImpressao;
    @Column(name = "dataConsolidado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataConsolidado;
    @Column(name = "userConsolidado")
    private Integer userConsolidado;
    @Column(name = "nomeConsolidado")
    private String nomeConsolidado;
    @Column(name = "inutilizada")
    private Integer inutilizada;
    @Column(name = "chave")
    private String chave;
    @Column(name = "responsavel")
    private String responsavel;
    @Column(name = "email_destinatario")
    private String emailDestinatario;
    @Column(name = "idOs")
    private Integer idOs;
    @Column(name = "metodo_insercao")
    private String metodoInsercao;
    @Column(name = "tipo_etiqueta")
    private String tipoEtiqueta;
    @Column(name = "id_plp")
    private Integer idPlp;
    @Column(name = "sigla_pais")
    private String siglaPais;
    @Column(name = "destino_postagem")
    private String destinoPostagem;
    @Column(name = "lote")
    private String lote;
    @Column(name = "isSync")
    private Boolean isSync;
    @Column(name = "arquivo_ar")
    private String arquivoAr;
    @Column(name = "serieNotaFiscal")
    private String serieNotaFiscal;

    @OneToOne(optional = true)
    @PrimaryKeyJoinColumns({
        @PrimaryKeyJoinColumn(name="idCliente", referencedColumnName="idCliente"),
        @PrimaryKeyJoinColumn(name="idDestinatario", referencedColumnName="idDestinatario")
    })
    private PreVendaDestinatario destinatario;

    
    @PrimaryKeyJoinColumns({
        @PrimaryKeyJoinColumn(name="idCliente", referencedColumnName="codigo")
    })
    @ManyToOne
    private Cliente cliente;
    

    public PreVenda() {
    }

    public PreVenda(PreVendaPK preVendaPK) {
        this.preVendaPK = preVendaPK;
    }

    public PreVenda(int id, String numObjeto) {
        this.preVendaPK = new PreVendaPK(id, numObjeto);
    }

    public PreVendaPK getPreVendaPK() {
        return preVendaPK;
    }

    public void setPreVendaPK(PreVendaPK preVendaPK) {
        this.preVendaPK = preVendaPK;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdRemetente() {
        return idRemetente;
    }

    public void setIdRemetente(Integer idRemetente) {
        this.idRemetente = idRemetente;
    }

    public Integer getIdDestinatario() {
        return idDestinatario;
    }

    public void setIdDestinatario(Integer idDestinatario) {
        this.idDestinatario = idDestinatario;
    }

    public Integer getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public Integer getCodECT() {
        return codECT;
    }

    public void setCodECT(Integer codECT) {
        this.codECT = codECT;
    }

    public String getNomeServico() {
        return nomeServico;
    }

    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
    }

    public String getTipoEncomenda() {
        return tipoEncomenda;
    }

    public void setTipoEncomenda(String tipoEncomenda) {
        this.tipoEncomenda = tipoEncomenda;
    }

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public String getCartaoPostagem() {
        return cartaoPostagem;
    }

    public void setCartaoPostagem(String cartaoPostagem) {
        this.cartaoPostagem = cartaoPostagem;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getAosCuidados() {
        return aosCuidados;
    }

    public void setAosCuidados(String aosCuidados) {
        this.aosCuidados = aosCuidados;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(String notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    public String getSiglaAmarracao() {
        return siglaAmarracao;
    }

    public void setSiglaAmarracao(String siglaAmarracao) {
        this.siglaAmarracao = siglaAmarracao;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public Integer getAltura() {
        return altura;
    }

    public void setAltura(Integer altura) {
        this.altura = altura;
    }

    public Integer getLargura() {
        return largura;
    }

    public void setLargura(Integer largura) {
        this.largura = largura;
    }

    public Integer getComprimento() {
        return comprimento;
    }

    public void setComprimento(Integer comprimento) {
        this.comprimento = comprimento;
    }

    public Float getValorDeclarado() {
        return valorDeclarado;
    }

    public void setValorDeclarado(Float valorDeclarado) {
        this.valorDeclarado = valorDeclarado;
    }

    public Float getValorCobrar() {
        return valorCobrar;
    }

    public void setValorCobrar(Float valorCobrar) {
        this.valorCobrar = valorCobrar;
    }

    public Integer getMaoPropria() {
        return maoPropria;
    }

    public void setMaoPropria(Integer maoPropria) {
        this.maoPropria = maoPropria;
    }

    public Integer getAvisoRecebimento() {
        return avisoRecebimento;
    }

    public void setAvisoRecebimento(Integer avisoRecebimento) {
        this.avisoRecebimento = avisoRecebimento;
    }

    public Short getPostaRestante() {
        return postaRestante;
    }

    public void setPostaRestante(Short postaRestante) {
        this.postaRestante = postaRestante;
    }

    public Integer getRegistro() {
        return registro;
    }

    public void setRegistro(Integer registro) {
        this.registro = registro;
    }

    public Short getRegistroModico() {
        return registroModico;
    }

    public void setRegistroModico(Short registroModico) {
        this.registroModico = registroModico;
    }

    public Integer getImpresso() {
        return impresso;
    }

    public void setImpresso(Integer impresso) {
        this.impresso = impresso;
    }

    public Integer getImpressoAr() {
        return impressoAr;
    }

    public void setImpressoAr(Integer impressoAr) {
        this.impressoAr = impressoAr;
    }

    public Integer getConsolidado() {
        return consolidado;
    }

    public void setConsolidado(Integer consolidado) {
        this.consolidado = consolidado;
    }

    public Date getDataPreVenda() {
        return dataPreVenda;
    }

    public void setDataPreVenda(Date dataPreVenda) {
        this.dataPreVenda = dataPreVenda;
    }

    public Integer getUserPreVenda() {
        return userPreVenda;
    }

    public void setUserPreVenda(Integer userPreVenda) {
        this.userPreVenda = userPreVenda;
    }

    public String getNomePreVenda() {
        return nomePreVenda;
    }

    public void setNomePreVenda(String nomePreVenda) {
        this.nomePreVenda = nomePreVenda;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Integer getUserVenda() {
        return userVenda;
    }

    public void setUserVenda(Integer userVenda) {
        this.userVenda = userVenda;
    }

    public String getNomeVenda() {
        return nomeVenda;
    }

    public void setNomeVenda(String nomeVenda) {
        this.nomeVenda = nomeVenda;
    }

    public Date getDataImpressao() {
        return dataImpressao;
    }

    public void setDataImpressao(Date dataImpressao) {
        this.dataImpressao = dataImpressao;
    }

    public Integer getUserImpressao() {
        return userImpressao;
    }

    public void setUserImpressao(Integer userImpressao) {
        this.userImpressao = userImpressao;
    }

    public String getNomeImpressao() {
        return nomeImpressao;
    }

    public void setNomeImpressao(String nomeImpressao) {
        this.nomeImpressao = nomeImpressao;
    }

    public Date getDataConsolidado() {
        return dataConsolidado;
    }

    public void setDataConsolidado(Date dataConsolidado) {
        this.dataConsolidado = dataConsolidado;
    }

    public Integer getUserConsolidado() {
        return userConsolidado;
    }

    public void setUserConsolidado(Integer userConsolidado) {
        this.userConsolidado = userConsolidado;
    }

    public String getNomeConsolidado() {
        return nomeConsolidado;
    }

    public void setNomeConsolidado(String nomeConsolidado) {
        this.nomeConsolidado = nomeConsolidado;
    }

    public Integer getInutilizada() {
        return inutilizada;
    }

    public void setInutilizada(Integer inutilizada) {
        this.inutilizada = inutilizada;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getEmailDestinatario() {
        return emailDestinatario;
    }

     public void setEmailDestinatario(String emailDestinatario) {
        this.emailDestinatario = emailDestinatario;
    }

    public Integer getIdOs() {
        return idOs;
    }

    public void setIdOs(Integer idOs) {
        this.idOs = idOs;
    }

    public String getMetodoInsercao() {
        return metodoInsercao;
    }

    public void setMetodoInsercao(String metodoInsercao) {
        this.metodoInsercao = metodoInsercao;
    }

    public String getTipoEtiqueta() {
        return tipoEtiqueta;
    }

    public void setTipoEtiqueta(String tipoEtiqueta) {
        this.tipoEtiqueta = tipoEtiqueta;
    }

    public Integer getIdPlp() {
        return idPlp;
    }

    public void setIdPlp(Integer idPlp) {
        this.idPlp = idPlp;
    }

    public String getSiglaPais() {
        return siglaPais;
    }

    public void setSiglaPais(String siglaPais) {
        this.siglaPais = siglaPais;
    }

    public String getDestinoPostagem() {
        return destinoPostagem;
    }

    public void setDestinoPostagem(String destinoPostagem) {
        this.destinoPostagem = destinoPostagem;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Boolean getIsSync() {
        return isSync;
    }

    public void setIsSync(Boolean isSync) {
        this.isSync = isSync;
    }

    public String getArquivoAr() {
        return arquivoAr;
    }

    public void setArquivoAr(String arquivoAr) {
        this.arquivoAr = arquivoAr;
    }

    public String getSerieNotaFiscal() {
        return serieNotaFiscal;
    }

    public void setSerieNotaFiscal(String serieNotaFiscal) {
        this.serieNotaFiscal = serieNotaFiscal;
    }

    public PreVendaDestinatario getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(PreVendaDestinatario destinatario) {
        this.destinatario = destinatario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        if(this.cliente!=null){
            setIdCliente(this.cliente.getCodigo());
            setContrato(this.cliente.getNumContrato());
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (preVendaPK != null ? preVendaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PreVenda)) {
            return false;
        }
        PreVenda other = (PreVenda) object;
        if ((this.preVendaPK == null && other.preVendaPK != null) || (this.preVendaPK != null && !this.preVendaPK.equals(other.preVendaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.portalpostal.entity.PreVenda[ preVendaPK=" + preVendaPK + " ]";
    }

}
