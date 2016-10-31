
package br.com.correios.logisticareversa.service;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de coletasSolicitadas complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="coletasSolicitadas">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="numero_pedido" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="controle_cliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="historico" type="{http://service.logisticareversa.correios.com.br/}historicoColeta" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="objeto" type="{http://service.logisticareversa.correios.com.br/}objetoPostal" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "coletasSolicitadas", propOrder = {
    "numeroPedido",
    "controleCliente",
    "historico",
    "objeto"
})
public class ColetasSolicitadas {

    @XmlElement(name = "numero_pedido")
    protected Long numeroPedido;
    @XmlElement(name = "controle_cliente")
    protected String controleCliente;
    @XmlElement(nillable = true)
    protected List<HistoricoColeta> historico;
    @XmlElement(nillable = true)
    protected List<ObjetoPostal> objeto;

    /**
     * Obtém o valor da propriedade numeroPedido.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNumeroPedido() {
        return numeroPedido;
    }

    /**
     * Define o valor da propriedade numeroPedido.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNumeroPedido(Long value) {
        this.numeroPedido = value;
    }

    /**
     * Obtém o valor da propriedade controleCliente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getControleCliente() {
        return controleCliente;
    }

    /**
     * Define o valor da propriedade controleCliente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setControleCliente(String value) {
        this.controleCliente = value;
    }

    /**
     * Gets the value of the historico property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the historico property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHistorico().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HistoricoColeta }
     * 
     * 
     */
    public List<HistoricoColeta> getHistorico() {
        if (historico == null) {
            historico = new ArrayList<HistoricoColeta>();
        }
        return this.historico;
    }

    /**
     * Gets the value of the objeto property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the objeto property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getObjeto().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ObjetoPostal }
     * 
     * 
     */
    public List<ObjetoPostal> getObjeto() {
        if (objeto == null) {
            objeto = new ArrayList<ObjetoPostal>();
        }
        return this.objeto;
    }

}
