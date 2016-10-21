
package br.com.correios.logisticareversa.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de revalidarPrazoAutorizacaoPostagemResponse complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="revalidarPrazoAutorizacaoPostagemResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="revalidarPrazoAutorizacaoPostagem" type="{http://service.logisticareversa.correios.com.br/}retornoRevalidarPrazo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "revalidarPrazoAutorizacaoPostagemResponse", propOrder = {
    "revalidarPrazoAutorizacaoPostagem"
})
public class RevalidarPrazoAutorizacaoPostagemResponse {

    protected RetornoRevalidarPrazo revalidarPrazoAutorizacaoPostagem;

    /**
     * Obtém o valor da propriedade revalidarPrazoAutorizacaoPostagem.
     * 
     * @return
     *     possible object is
     *     {@link RetornoRevalidarPrazo }
     *     
     */
    public RetornoRevalidarPrazo getRevalidarPrazoAutorizacaoPostagem() {
        return revalidarPrazoAutorizacaoPostagem;
    }

    /**
     * Define o valor da propriedade revalidarPrazoAutorizacaoPostagem.
     * 
     * @param value
     *     allowed object is
     *     {@link RetornoRevalidarPrazo }
     *     
     */
    public void setRevalidarPrazoAutorizacaoPostagem(RetornoRevalidarPrazo value) {
        this.revalidarPrazoAutorizacaoPostagem = value;
    }

}
