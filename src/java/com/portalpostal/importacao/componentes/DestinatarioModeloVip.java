package com.portalpostal.importacao.componentes;

import java.math.BigDecimal;

public class DestinatarioModeloVip {

    private String nome;
    private String endereco;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String telefone;
    private String codigoFinanceiro;
    private String numeroObjeto;
    private String peso;
    private String altura;
    private String largura;
    private String comprimento;
    private String cubico;
    private String nota;
    private String serieNota;
    private String valorDeclarado;
    private String valorCobrar;
    private String servicosAdicionais;
    private String contrato;
    private String administrativo;
    private String cartao;
    private String observacao;
    private String conteudoDoObjeto;
    private String codigoVolume;
    private String quantidadeVolumes;
    private String codigoClienteVisual;
    private String email;
    private String celular;

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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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

    public String getCubico() {
        return cubico;
    }

    public void setCubico(String cubico) {
        this.cubico = cubico;
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

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public String getAdministrativo() {
        return administrativo;
    }

    public void setAdministrativo(String administrativo) {
        this.administrativo = administrativo;
    }

    public String getCartao() {
        return cartao;
    }

    public void setCartao(String cartao) {
        this.cartao = cartao;
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

    public String getCodigoVolume() {
        return codigoVolume;
    }

    public void setCodigoVolume(String codigoVolume) {
        this.codigoVolume = codigoVolume;
    }

    public String getQuantidadeVolumes() {
        return quantidadeVolumes;
    }

    public void setQuantidadeVolumes(String quantidadeVolumes) {
        this.quantidadeVolumes = quantidadeVolumes;
    }

    public String getCodigoClienteVisual() {
        return codigoClienteVisual;
    }

    public void setCodigoClienteVisual(String codigoClienteVisual) {
        this.codigoClienteVisual = codigoClienteVisual;
    }

    public String getValorCobrar() {
        return valorCobrar;
    }

    public void setValorCobrar(String valorCobrar) {
        this.valorCobrar = valorCobrar;
    }

    public Double getPesoConvertido(){
        return converteValor(this.peso);
    }

    public Double getAlturaConvertida(){
        return converteValor(this.altura);
    }

    public Double getLarguraConvertida(){
        return converteValor(this.largura);
    }

    public Double getValorDeclaradoConvertido(){
        return converteValor(this.valorDeclarado);
    }


     private Double converteValor(String valor) {
         BigDecimal decimal = new BigDecimal(Double.parseDouble(valor)/100);
         decimal = decimal.setScale(2,BigDecimal.ROUND_HALF_EVEN);
         return decimal.doubleValue();
    }



   
}
