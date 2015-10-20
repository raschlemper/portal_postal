
package br.com.correios.scol.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de objetoPostalTO complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="objetoPostalTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="numero_etiqueta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="controle_objeto_cliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ultimo_status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descricao_status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="data_ultima_atualizacao" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hora_ultima_atualizacao" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "objetoPostalTO", propOrder = {
    "numeroEtiqueta",
    "controleObjetoCliente",
    "ultimoStatus",
    "descricaoStatus",
    "dataUltimaAtualizacao",
    "horaUltimaAtualizacao"
})
public class ObjetoPostalTO {

    @XmlElement(name = "numero_etiqueta")
    protected String numeroEtiqueta;
    @XmlElement(name = "controle_objeto_cliente")
    protected String controleObjetoCliente;
    @XmlElement(name = "ultimo_status")
    protected String ultimoStatus;
    @XmlElement(name = "descricao_status")
    protected String descricaoStatus;
    @XmlElement(name = "data_ultima_atualizacao")
    protected String dataUltimaAtualizacao;
    @XmlElement(name = "hora_ultima_atualizacao")
    protected String horaUltimaAtualizacao;

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
     * Obtém o valor da propriedade controleObjetoCliente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getControleObjetoCliente() {
        return controleObjetoCliente;
    }

    /**
     * Define o valor da propriedade controleObjetoCliente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setControleObjetoCliente(String value) {
        this.controleObjetoCliente = value;
    }

    /**
     * Obtém o valor da propriedade ultimoStatus.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUltimoStatus() {
        return ultimoStatus;
    }

    /**
     * Define o valor da propriedade ultimoStatus.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUltimoStatus(String value) {
        this.ultimoStatus = value;
    }

    /**
     * Obtém o valor da propriedade descricaoStatus.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescricaoStatus() {
        return descricaoStatus;
    }

    /**
     * Define o valor da propriedade descricaoStatus.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescricaoStatus(String value) {
        this.descricaoStatus = value;
    }

    /**
     * Obtém o valor da propriedade dataUltimaAtualizacao.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataUltimaAtualizacao() {
        return dataUltimaAtualizacao;
    }

    /**
     * Define o valor da propriedade dataUltimaAtualizacao.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataUltimaAtualizacao(String value) {
        this.dataUltimaAtualizacao = value;
    }

    /**
     * Obtém o valor da propriedade horaUltimaAtualizacao.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHoraUltimaAtualizacao() {
        return horaUltimaAtualizacao;
    }

    /**
     * Define o valor da propriedade horaUltimaAtualizacao.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHoraUltimaAtualizacao(String value) {
        this.horaUltimaAtualizacao = value;
    }

}
