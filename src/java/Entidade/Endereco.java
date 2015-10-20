/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidade;

/**
 *
 * @author RICADINHO
 */
public class Endereco {

    private String nome;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;
    private String pais;
    private String siglaPais;
    private String siglaAltPais;

    public Endereco(String pais, String siglaPais, String siglaAltPais) {
        this.pais = pais;
        this.siglaPais = siglaPais;
        this.siglaAltPais = siglaAltPais;
    }

    public Endereco(String nome, String logradouro, String numero, String complemento, String bairro, String cidade, String uf, String cep) {
        this.nome = nome;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.cep = cep;
    }
    
    public Endereco(String bairro, String cidade, String logradouro, String uf, String cep) {
        this.bairro = bairro;
        this.cidade = cidade;
        this.logradouro = logradouro;
        this.uf = uf;
        this.cep = cep;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getSiglaPais() {
        return siglaPais;
    }

    public void setSiglaPais(String siglaPais) {
        this.siglaPais = siglaPais;
    }

    public String getSiglaAltPais() {
        return siglaAltPais;
    }

    public void setSiglaAltPais(String siglaAltPais) {
        this.siglaAltPais = siglaAltPais;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

}
