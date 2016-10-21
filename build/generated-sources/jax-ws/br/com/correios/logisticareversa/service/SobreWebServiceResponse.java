
package br.com.correios.logisticareversa.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de sobreWebServiceResponse complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="sobreWebServiceResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sobreWebService" type="{http://service.logisticareversa.correios.com.br/}retornoSobreWebService" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sobreWebServiceResponse", propOrder = {
    "sobreWebService"
})
public class SobreWebServiceResponse {

    protected RetornoSobreWebService sobreWebService;

    /**
     * Obtém o valor da propriedade sobreWebService.
     * 
     * @return
     *     possible object is
     *     {@link RetornoSobreWebService }
     *     
     */
    public RetornoSobreWebService getSobreWebService() {
        return sobreWebService;
    }

    /**
     * Define o valor da propriedade sobreWebService.
     * 
     * @param value
     *     allowed object is
     *     {@link RetornoSobreWebService }
     *     
     */
    public void setSobreWebService(RetornoSobreWebService value) {
        this.sobreWebService = value;
    }

}
