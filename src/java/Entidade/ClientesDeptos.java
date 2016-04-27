/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidade;

/**
 *
 * @author RICARDINHO
 */
public class ClientesDeptos {
    
    private int idDepartamento;
    private int idCliente;
    private String nomeDepartamento;
    private String cartaoPostagem;
    private int codReferencia;
    private int temEndereco;
    private String nomeEndereco;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;
    
    /*public ClientesDeptos(int idDepartamento, int idCliente, String nomeDepartamento, String cartaoPostagem) {
    this.idDepartamento = idDepartamento;
    this.idCliente = idCliente;
    this.nomeDepartamento = nomeDepartamento;
    this.cartaoPostagem = cartaoPostagem;
    }*/
    
    public ClientesDeptos(int idDepartamento, int idCliente, String nomeDepartamento, String cartaoPostagem, int codReferencia, int temEndereco, String nomeEndereco, String logradouro, String numero, String complemento, String bairro, String cidade, String uf, String cep) {    
        this.idDepartamento = idDepartamento;
        this.idCliente = idCliente;
        this.nomeDepartamento = nomeDepartamento;
        this.cartaoPostagem = cartaoPostagem;
        this.codReferencia = codReferencia;
        this.temEndereco = temEndereco;
        this.nomeEndereco = nomeEndereco;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.cep = cep;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public int getCodReferencia() {
        return codReferencia;
    }
    
    public void setCodReferencia(int codReferencia) {
        this.codReferencia = codReferencia;
    }

    public int getTemEndereco() {
        return temEndereco;
    }

    public void setTemEndereco(int temEndereco) {
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
    
    public String getCartaoPostagem() {
        return cartaoPostagem;
    }

    public void setCartaoPostagem(String cartaoPostagem) {
        this.cartaoPostagem = cartaoPostagem;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getNomeDepartamento() {
        return nomeDepartamento;
    }

    public void setNomeDepartamento(String nomeDepartamento) {
        this.nomeDepartamento = nomeDepartamento;
    }
    
}
