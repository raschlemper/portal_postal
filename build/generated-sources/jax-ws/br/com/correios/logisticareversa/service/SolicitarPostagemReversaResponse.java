
package br.com.correios.logisticareversa.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de solicitarPostagemReversaResponse complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="solicitarPostagemReversaResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="solicitarPostagemReversa" type="{http://service.logisticareversa.correios.com.br/}retornoPostagem" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "solicitarPostagemReversaResponse", propOrder = {
    "solicitarPostagemReversa"
})
public class SolicitarPostagemReversaResponse {

    protected RetornoPostagem solicitarPostagemReversa;

    /**
     * Obtém o valor da propriedade solicitarPostagemReversa.
     * 
     * @return
     *     possible object is
     *     {@link RetornoPostagem }
     *     
     */
    public RetornoPostagem getSolicitarPostagemReversa() {
        return solicitarPostagemReversa;
    }

    /**
     * Define o valor da propriedade solicitarPostagemReversa.
     * 
     * @param value
     *     allowed object is
     *     {@link RetornoPostagem }
     *     
     */
    public void setSolicitarPostagemReversa(RetornoPostagem value) {
        this.solicitarPostagemReversa = value;
    }

}
