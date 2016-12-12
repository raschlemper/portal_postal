
package br.com.portalpostal.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name = "cliente_departamentos")
@NamedQueries({
    @NamedQuery(name = "ClienteDepartamentos.findAll", query = "SELECT c FROM ClienteDepartamentos c"),
    @NamedQuery(name = "ClienteDepartamentos.findByIdCliente", query = "SELECT c FROM ClienteDepartamentos c WHERE c.clienteDepartamentosPK.idCliente = :idCliente"),
    @NamedQuery(name = "ClienteDepartamentos.findByIdDepartamento", query = "SELECT c FROM ClienteDepartamentos c WHERE c.clienteDepartamentosPK.idDepartamento = :idDepartamento"),
    @NamedQuery(name = "ClienteDepartamentos.findByNomeDepartamento", query = "SELECT c FROM ClienteDepartamentos c WHERE c.nomeDepartamento = :nomeDepartamento")})

public class ClienteDepartamentos implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ClienteDepartamentosPK clienteDepartamentosPK;
    @Column(name = "nomeDepartamento")
    private String nomeDepartamento;
    @Column(name = "cartaoPostagem")
    private String cartaoPostagem;
    @Column(name = "ativo")
    private Integer ativo;
    @Column(name = "codReferencia")
    private String codReferencia;
    @Column(name = "temEndereco")
    private Integer temEndereco;
    @Column(name = "nomeEndereco")
    private String nomeEndereco;
    @Column(name = "logradouro")
    private String logradouro;
    @Column(name = "numero")
    private String numero;
    @Column(name = "complemento")
    private String complemento;
    @Column(name = "bairro")
    private String bairro;
    @Column(name = "cidade")
    private String cidade;
    @Column(name = "uf")
    private String uf;
    @Column(name = "cep")
    private String cep;

    @MapsId("idCliente")
    @JoinColumn(name="idCliente",referencedColumnName = "codigo")
    @ManyToOne(targetEntity = Cliente.class)
    private Cliente cliente;

    public ClienteDepartamentos() {
    }

    public ClienteDepartamentos(ClienteDepartamentosPK clienteDepartamentosPK) {
        this.clienteDepartamentosPK = clienteDepartamentosPK;
    }

    public ClienteDepartamentos(int idCliente, int idDepartamento) {
        this.clienteDepartamentosPK = new ClienteDepartamentosPK(idCliente, idDepartamento);
    }

    public ClienteDepartamentosPK getClienteDepartamentosPK() {
        return clienteDepartamentosPK;
    }

    public void setClienteDepartamentosPK(ClienteDepartamentosPK clienteDepartamentosPK) {
        this.clienteDepartamentosPK = clienteDepartamentosPK;
    }

    public String getNomeDepartamento() {
        return nomeDepartamento;
    }

    public void setNomeDepartamento(String nomeDepartamento) {
        this.nomeDepartamento = nomeDepartamento;
    }

    public String getCartaoPostagem() {
        return cartaoPostagem;
    }

    public void setCartaoPostagem(String cartaoPostagem) {
        this.cartaoPostagem = cartaoPostagem;
    }

    public Integer getAtivo() {
        return ativo;
    }

    public void setAtivo(Integer ativo) {
        this.ativo = ativo;
    }

    public String getCodReferencia() {
        return codReferencia;
    }

    public void setCodReferencia(String codReferencia) {
        this.codReferencia = codReferencia;
    }

    public Integer getTemEndereco() {
        return temEndereco;
    }

    public void setTemEndereco(Integer temEndereco) {
        this.temEndereco = temEndereco;
    }

    public String getNomeEndereco() {
        return nomeEndereco;
    }

    public void setNomeEndereco(String nomeEndereco) {
        this.nomeEndereco = nomeEndereco;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clienteDepartamentosPK != null ? clienteDepartamentosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClienteDepartamentos)) {
            return false;
        }
        ClienteDepartamentos other = (ClienteDepartamentos) object;
        if ((this.clienteDepartamentosPK == null && other.clienteDepartamentosPK != null) || (this.clienteDepartamentosPK != null && !this.clienteDepartamentosPK.equals(other.clienteDepartamentosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.portalpostal.entity.ClienteDepartamentos[ clienteDepartamentosPK=" + clienteDepartamentosPK + " ]";
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

  

}
