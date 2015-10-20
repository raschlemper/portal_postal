
package br.com.correios.scol.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de retornoFaixaNumericaTO complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="retornoFaixaNumericaTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="data" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hora" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cod_erro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="msg_erro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="faixa_inicial" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="faixa_final" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "retornoFaixaNumericaTO", propOrder = {
    "data",
    "hora",
    "codErro",
    "msgErro",
    "faixaInicial",
    "faixaFinal"
})
public class RetornoFaixaNumericaTO {

    protected String data;
    protected String hora;
    @XmlElement(name = "cod_erro")
    protected String codErro;
    @XmlElement(name = "msg_erro")
    protected String msgErro;
    @XmlElement(name = "faixa_inicial")
    protected Integer faixaInicial;
    @XmlElement(name = "faixa_final")
    protected Integer faixaFinal;

    /**
     * Obtém o valor da propriedade data.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getData() {
        return data;
    }

    /**
     * Define o valor da propriedade data.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setData(String value) {
        this.data = value;
    }

    /**
     * Obtém o valor da propriedade hora.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHora() {
        return hora;
    }

    /**
     * Define o valor da propriedade hora.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHora(String value) {
        this.hora = value;
    }

    /**
     * Obtém o valor da propriedade codErro.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodErro() {
        return codErro;
    }

    /**
     * Define o valor da propriedade codErro.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodErro(String value) {
        this.codErro = value;
    }

    /**
     * Obtém o valor da propriedade msgErro.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMsgErro() {
        return msgErro;
    }

    /**
     * Define o valor da propriedade msgErro.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMsgErro(String value) {
        this.msgErro = value;
    }

    /**
     * Obtém o valor da propriedade faixaInicial.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFaixaInicial() {
        return faixaInicial;
    }

    /**
     * Define o valor da propriedade faixaInicial.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFaixaInicial(Integer value) {
        this.faixaInicial = value;
    }

    /**
     * Obtém o valor da propriedade faixaFinal.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFaixaFinal() {
        return faixaFinal;
    }

    /**
     * Define o valor da propriedade faixaFinal.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFaixaFinal(Integer value) {
        this.faixaFinal = value;
    }

}
