
package br.com.correios.scol.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de solicitarPostagemReversa complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="solicitarPostagemReversa">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="usuario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="senha" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codAdministrativo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="contrato" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="codigo_servico" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="cartao" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="destinatario" type="{http://webservice.scol.correios.com.br/}pessoaTO" minOccurs="0"/>
 *         &lt;element name="coletas_solicitadas" type="{http://webservice.scol.correios.com.br/}coletaReversaTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "solicitarPostagemReversa", propOrder = {
    "usuario",
    "senha",
    "codAdministrativo",
    "contrato",
    "codigoServico",
    "cartao",
    "destinatario",
    "coletasSolicitadas"
})
public class SolicitarPostagemReversa {

    protected String usuario;
    protected String senha;
    protected Integer codAdministrativo;
    protected Long contrato;
    @XmlElement(name = "codigo_servico")
    protected Integer codigoServico;
    protected Long cartao;
    protected PessoaTO destinatario;
    @XmlElement(name = "coletas_solicitadas")
    protected List<ColetaReversaTO> coletasSolicitadas;

    /**
     * Obtém o valor da propriedade usuario.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Define o valor da propriedade usuario.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsuario(String value) {
        this.usuario = value;
    }

    /**
     * Obtém o valor da propriedade senha.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Define o valor da propriedade senha.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSenha(String value) {
        this.senha = value;
    }

    /**
     * Obtém o valor da propriedade codAdministrativo.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodAdministrativo() {
        return codAdministrativo;
    }

    /**
     * Define o valor da propriedade codAdministrativo.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodAdministrativo(Integer value) {
        this.codAdministrativo = value;
    }

    /**
     * Obtém o valor da propriedade contrato.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getContrato() {
        return contrato;
    }

    /**
     * Define o valor da propriedade contrato.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setContrato(Long value) {
        this.contrato = value;
    }

    /**
     * Obtém o valor da propriedade codigoServico.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodigoServico() {
        return codigoServico;
    }

    /**
     * Define o valor da propriedade codigoServico.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodigoServico(Integer value) {
        this.codigoServico = value;
    }

    /**
     * Obtém o valor da propriedade cartao.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCartao() {
        return cartao;
    }

    /**
     * Define o valor da propriedade cartao.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCartao(Long value) {
        this.cartao = value;
    }

    /**
     * Obtém o valor da propriedade destinatario.
     * 
     * @return
     *     possible object is
     *     {@link PessoaTO }
     *     
     */
    public PessoaTO getDestinatario() {
        return destinatario;
    }

    /**
     * Define o valor da propriedade destinatario.
     * 
     * @param value
     *     allowed object is
     *     {@link PessoaTO }
     *     
     */
    public void setDestinatario(PessoaTO value) {
        this.destinatario = value;
    }

    /**
     * Gets the value of the coletasSolicitadas property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the coletasSolicitadas property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getColetasSolicitadas().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ColetaReversaTO }
     * 
     * 
     */
    public List<ColetaReversaTO> getColetasSolicitadas() {
        if (coletasSolicitadas == null) {
            coletasSolicitadas = new ArrayList<ColetaReversaTO>();
        }
        return this.coletasSolicitadas;
    }

}
