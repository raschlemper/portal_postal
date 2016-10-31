
package br.com.correios.logisticareversa.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de validarPostagemReversaResponse complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="validarPostagemReversaResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="validarPostagemReversa" type="{http://service.logisticareversa.correios.com.br/}retornoValidacao" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validarPostagemReversaResponse", propOrder = {
    "validarPostagemReversa"
})
public class ValidarPostagemReversaResponse {

    protected RetornoValidacao validarPostagemReversa;

    /**
     * Obtém o valor da propriedade validarPostagemReversa.
     * 
     * @return
     *     possible object is
     *     {@link RetornoValidacao }
     *     
     */
    public RetornoValidacao getValidarPostagemReversa() {
        return validarPostagemReversa;
    }

    /**
     * Define o valor da propriedade validarPostagemReversa.
     * 
     * @param value
     *     allowed object is
     *     {@link RetornoValidacao }
     *     
     */
    public void setValidarPostagemReversa(RetornoValidacao value) {
        this.validarPostagemReversa = value;
    }

}
