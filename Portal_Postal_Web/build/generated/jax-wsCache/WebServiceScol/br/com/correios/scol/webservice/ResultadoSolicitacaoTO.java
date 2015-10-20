
package br.com.correios.scol.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de resultadoSolicitacaoTO complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="resultadoSolicitacaoTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tipo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_cliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numero_coleta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numero_etiqueta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id_obj" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="status_objeto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="prazo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="data_solicitacao" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hora_solicitacao" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codigo_erro" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="descricao_erro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resultadoSolicitacaoTO", propOrder = {
    "tipo",
    "idCliente",
    "numeroColeta",
    "numeroEtiqueta",
    "idObj",
    "statusObjeto",
    "prazo",
    "dataSolicitacao",
    "horaSolicitacao",
    "codigoErro",
    "descricaoErro"
})
public class ResultadoSolicitacaoTO {

    protected String tipo;
    @XmlElement(name = "id_cliente")
    protected String idCliente;
    @XmlElement(name = "numero_coleta")
    protected String numeroColeta;
    @XmlElement(name = "numero_etiqueta")
    protected String numeroEtiqueta;
    @XmlElement(name = "id_obj")
    protected String idObj;
    @XmlElement(name = "status_objeto")
    protected String statusObjeto;
    protected String prazo;
    @XmlElement(name = "data_solicitacao")
    protected String dataSolicitacao;
    @XmlElement(name = "hora_solicitacao")
    protected String horaSolicitacao;
    @XmlElement(name = "codigo_erro")
    protected long codigoErro;
    @XmlElement(name = "descricao_erro")
    protected String descricaoErro;

    /**
     * Obtém o valor da propriedade tipo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Define o valor da propriedade tipo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipo(String value) {
        this.tipo = value;
    }

    /**
     * Obtém o valor da propriedade idCliente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdCliente() {
        return idCliente;
    }

    /**
     * Define o valor da propriedade idCliente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdCliente(String value) {
        this.idCliente = value;
    }

    /**
     * Obtém o valor da propriedade numeroColeta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroColeta() {
        return numeroColeta;
    }

    /**
     * Define o valor da propriedade numeroColeta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroColeta(String value) {
        this.numeroColeta = value;
    }

    /**
     * Obtém o valor da propriedade numeroEtiqueta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroEtiqueta() {
        return numeroEtiqueta;
    }

    /**
     * Define o valor da propriedade numeroEtiqueta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroEtiqueta(String value) {
        this.numeroEtiqueta = value;
    }

    /**
     * Obtém o valor da propriedade idObj.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdObj() {
        return idObj;
    }

    /**
     * Define o valor da propriedade idObj.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdObj(String value) {
        this.idObj = value;
    }

    /**
     * Obtém o valor da propriedade statusObjeto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusObjeto() {
        return statusObjeto;
    }

    /**
     * Define o valor da propriedade statusObjeto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusObjeto(String value) {
        this.statusObjeto = value;
    }

    /**
     * Obtém o valor da propriedade prazo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrazo() {
        return prazo;
    }

    /**
     * Define o valor da propriedade prazo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrazo(String value) {
        this.prazo = value;
    }

    /**
     * Obtém o valor da propriedade dataSolicitacao.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataSolicitacao() {
        return dataSolicitacao;
    }

    /**
     * Define o valor da propriedade dataSolicitacao.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataSolicitacao(String value) {
        this.dataSolicitacao = value;
    }

    /**
     * Obtém o valor da propriedade horaSolicitacao.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHoraSolicitacao() {
        return horaSolicitacao;
    }

    /**
     * Define o valor da propriedade horaSolicitacao.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHoraSolicitacao(String value) {
        this.horaSolicitacao = value;
    }

    /**
     * Obtém o valor da propriedade codigoErro.
     * 
     */
    public long getCodigoErro() {
        return codigoErro;
    }

    /**
     * Define o valor da propriedade codigoErro.
     * 
     */
    public void setCodigoErro(long value) {
        this.codigoErro = value;
    }

    /**
     * Obtém o valor da propriedade descricaoErro.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescricaoErro() {
        return descricaoErro;
    }

    /**
     * Define o valor da propriedade descricaoErro.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescricaoErro(String value) {
        this.descricaoErro = value;
    }

}
