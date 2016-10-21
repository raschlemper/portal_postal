
package br.com.correios.logisticareversa.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de objetoPostal complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="objetoPostal">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="numero_etiqueta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="controle_objeto_cliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ultimo_status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descricao_status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="data_ultima_atualizacao" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hora_ultima_atualizacao" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="peso_cubico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="peso_real" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="valor_postagem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "objetoPostal", propOrder = {
    "numeroEtiqueta",
    "controleObjetoCliente",
    "ultimoStatus",
    "descricaoStatus",
    "dataUltimaAtualizacao",
    "horaUltimaAtualizacao",
    "pesoCubico",
    "pesoReal",
    "valorPostagem"
})
public class ObjetoPostal {

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
    @XmlElement(name = "peso_cubico")
    protected String pesoCubico;
    @XmlElement(name = "peso_real")
    protected String pesoReal;
    @XmlElement(name = "valor_postagem")
    protected String valorPostagem;

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

    /**
     * Obtém o valor da propriedade pesoCubico.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPesoCubico() {
        return pesoCubico;
    }

    /**
     * Define o valor da propriedade pesoCubico.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPesoCubico(String value) {
        this.pesoCubico = value;
    }

    /**
     * Obtém o valor da propriedade pesoReal.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPesoReal() {
        return pesoReal;
    }

    /**
     * Define o valor da propriedade pesoReal.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPesoReal(String value) {
        this.pesoReal = value;
    }

    /**
     * Obtém o valor da propriedade valorPostagem.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValorPostagem() {
        return valorPostagem;
    }

    /**
     * Define o valor da propriedade valorPostagem.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValorPostagem(String value) {
        this.valorPostagem = value;
    }

}
