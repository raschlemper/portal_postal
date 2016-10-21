
package br.com.correios.logisticareversa.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de validarPostagemSimultaneaResponse complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="validarPostagemSimultaneaResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="validarPostagemSimultanea" type="{http://service.logisticareversa.correios.com.br/}retornoValidacao" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validarPostagemSimultaneaResponse", propOrder = {
    "validarPostagemSimultanea"
})
public class ValidarPostagemSimultaneaResponse {

    protected RetornoValidacao validarPostagemSimultanea;

    /**
     * Obtém o valor da propriedade validarPostagemSimultanea.
     * 
     * @return
     *     possible object is
     *     {@link RetornoValidacao }
     *     
     */
    public RetornoValidacao getValidarPostagemSimultanea() {
        return validarPostagemSimultanea;
    }

    /**
     * Define o valor da propriedade validarPostagemSimultanea.
     * 
     * @param value
     *     allowed object is
     *     {@link RetornoValidacao }
     *     
     */
    public void setValidarPostagemSimultanea(RetornoValidacao value) {
        this.validarPostagemSimultanea = value;
    }

}
