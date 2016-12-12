package br.com.portalpostal.importacao.modelo;

import java.math.BigDecimal;

public class DestinatarioModelo {

    private String nome;
    private String endereco;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String email;
    private String celular;
    private String codigoFinanceiro;
    private String numeroObjeto;
    private String peso;
    private String altura;
    private String largura;
    private String comprimento;
    private String nota;
    private String serieNota;
    private String valorDeclarado;
    private String servicosAdicionais;
    private String observacao;
    private String conteudoDoObjeto;
    private String quantidadeVolumes;
    private String chaveCliente;
    private String codigoCliente;
    private String departamento;
    private String idUsuario;
    private String nomeUsuario;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCodigoFinanceiro() {
        return codigoFinanceiro;
    }

    public void setCodigoFinanceiro(String codigoFinanceiro) {
        this.codigoFinanceiro = codigoFinanceiro;
    }

    public String getNumeroObjeto() {
        return numeroObjeto;
    }

    public void setNumeroObjeto(String numeroObjeto) {
        this.numeroObjeto = numeroObjeto;
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

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getSerieNota() {
        return serieNota;
    }

    public void setSerieNota(String serieNota) {
        this.serieNota = serieNota;
    }

    public String getValorDeclarado() {
        return valorDeclarado;
    }

    public void setValorDeclarado(String valorDeclarado) {
        this.valorDeclarado = valorDeclarado;
    }

    public String getServicosAdicionais() {
        return servicosAdicionais;
    }

    public void setServicosAdicionais(String servicosAdicionais) {
        this.servicosAdicionais = servicosAdicionais;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getConteudoDoObjeto() {
        return conteudoDoObjeto;
    }

    public void setConteudoDoObjeto(String conteudoDoObjeto) {
        this.conteudoDoObjeto = conteudoDoObjeto;
    }

    public String getQuantidadeVolumes() {
        return quantidadeVolumes;
    }

    public void setQuantidadeVolumes(String quantidadeVolumes) {
        this.quantidadeVolumes = quantidadeVolumes;
    }

    public Double getPesoConvertido() {
        return converteValor(this.peso);
    }

    public Double getAlturaConvertida() {
        return converteValor(this.altura);
    }

    public Double getLarguraConvertida() {
        return converteValor(this.largura);
    }

    public String getChaveCliente() {
        return chaveCliente;
    }

    public void setChaveCliente(String codigoCliente) {
        this.chaveCliente = codigoCliente;
    }

    public Double getValorDeclaradoConvertido() {
        return converteValor(this.valorDeclarado);
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    

    private Double converteValor(String valor) {
        BigDecimal decimal = new BigDecimal(Double.parseDouble(valor) / 100);
        decimal = decimal.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        return decimal.doubleValue();
    }

    

}
