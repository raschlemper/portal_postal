/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidade;

import Util.FormataString;

/**
 *
 * @author Fernando
 */
public class ArquivoImportacao{
    
    private String nrLinha;
    private String nrObjeto;
    private String servico;
    private String cep;
    private String nome;
    private String empresa;
    private String cpf;
    private String endereco;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String email;
    private String celular;
    private String aosCuidados;
    private String notaFiscal;
    private String obs;
    private String conteudo;
    private String chave;
    private String peso;
    private String altura;
    private String largura;
    private String comprimento;
    private String ar;
    private String mp;
    private String vd;
    private String rg;
    private String rm;
    private String pr;
    private int codECT;
    private int idCliente;
    private int idDepartamento;
    private String departamento;
    private String contrato;
    private String cartaoPostagem;
    private String metodoInsercao;

    public ArquivoImportacao() {
    }    

    public String getMetodoInsercao() {
        return metodoInsercao;
    }

    public void setMetodoInsercao(String metodoInsercao) {
        this.metodoInsercao = metodoInsercao;
    }

    public int getCodECT() {
        return codECT;
    }

    public void setCodECT(int codECT) {
        this.codECT = codECT;
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

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public String getCartaoPostagem() {
        return cartaoPostagem;
    }

    public void setCartaoPostagem(String cartaoPostagem) {
        this.cartaoPostagem = cartaoPostagem;
    }

    public String getNrLinha() {
        return nrLinha;
    }

    public void setNrLinha(String nrLinha) {
        this.nrLinha = nrLinha;
    }

    public String getNrObjeto() {
        return nrObjeto;
    }

    public void setNrObjeto(String nrObjeto) {
        this.nrObjeto = nrObjeto;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = FormataString.formatarCep("#####-###", cep);
    }
    public void setCepSemMascara(String cep) {
        this.cep = cep;
    }

    public String getNome() {        
        return nome;
    }

    public void setNome(String nome) {
        this.nome = Util.FormataString.removeAccentsToUpper(nome);
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = Util.FormataString.removeAccentsToUpper(empresa);
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = Util.FormataString.removeAccentsToUpper(cpf);
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = Util.FormataString.removeAccentsToUpper(endereco);
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = Util.FormataString.removeAccentsToUpper(numero);
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = Util.FormataString.removeAccentsToUpper(complemento);
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = Util.FormataString.removeAccentsToUpper(bairro);
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = Util.FormataString.removeAccentsToUpper(cidade);
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = Util.FormataString.removeAccentsToUpper(uf);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = Util.FormataString.removeAccentsToUpper(email);
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = Util.FormataString.removeAccentsToUpper(celular);
    }

    public String getAosCuidados() {
        return aosCuidados;
    }

    public void setAosCuidados(String aosCuidados) {
        this.aosCuidados = Util.FormataString.removeAccentsToUpper(aosCuidados);
    }

    public String getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(String notaFiscal) {
        this.notaFiscal = Util.FormataString.removeAccentsToUpper(notaFiscal);
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = Util.FormataString.removeAccentsToUpper(obs);
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = Util.FormataString.removeAccentsToUpper(conteudo);
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getLargura() {
        return largura;
    }

    public void setLargura(String largura) {
        this.largura = largura;
    }

    public String getComprimento() {
        return comprimento;
    }

    public void setComprimento(String comprimento) {
        this.comprimento = comprimento;
    }

    public String getAr() {
        return ar;
    }

    public void setAr(String ar) {
        this.ar = ar;
    }

    public String getMp() {
        return mp;
    }

    public void setMp(String mp) {
        this.mp = mp;
    }

    public String getVd() {
        return vd;
    }

    public void setVd(String vd) {
        this.vd = vd;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getRm() {
        return rm;
    }

    public void setRm(String rm) {
        this.rm = rm;
    }

    public String getPr() {
        return pr;
    }

    public void setPr(String pr) {
        this.pr = pr;
    }

    
    
}
