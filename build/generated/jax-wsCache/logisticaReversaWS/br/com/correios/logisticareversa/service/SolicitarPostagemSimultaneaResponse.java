
package br.com.correios.logisticareversa.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de solicitarPostagemSimultaneaResponse complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="solicitarPostagemSimultaneaResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="solicitarPostagemSimultanea" type="{http://service.logisticareversa.correios.com.br/}retornoPostagem" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "solicitarPostagemSimultaneaResponse", propOrder = {
    "solicitarPostagemSimultanea"
})
public class SolicitarPostagemSimultaneaResponse {

    protected RetornoPostagem solicitarPostagemSimultanea;

    /**
     * Obtém o valor da propriedade solicitarPostagemSimultanea.
     * 
     * @return
     *     possible object is
     *     {@link RetornoPostagem }
     *     
     */
    public RetornoPostagem getSolicitarPostagemSimultanea() {
        return solicitarPostagemSimultanea;
    }

    /**
     * Define o valor da propriedade solicitarPostagemSimultanea.
     * 
     * @param value
     *     allowed object is
     *     {@link RetornoPostagem }
     *     
     */
    public void setSolicitarPostagemSimultanea(RetornoPostagem value) {
        this.solicitarPostagemSimultanea = value;
    }

}
