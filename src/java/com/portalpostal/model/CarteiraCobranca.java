package com.portalpostal.model;

import java.util.List;

public class CarteiraCobranca {

    private Integer idCarteiraCobranca;
    private ContaCorrente contaCorrente;
    private String nome;
    private Integer codigoBeneficiario;
    private Integer codigoBeneficiarioDv;
    private Integer codigoConvenio;
    private Integer codigoCarteira;
    private Boolean aceite;
    private Boolean baixa;
    private String especieDocumento;
    private String localPagamento;
    private String instrucao01;
    private String instrucao02;
    private String instrucao03;
    private String instrucao04;
    private String instrucao05;
    private String beneficiarioNome;
    private String beneficiarioDocumento;
    private String beneficiarioLogradouro;
    private String beneficiarioBairro;
    private String beneficiarioCidade;
    private String beneficiarioUf;
    private String beneficiarioCep;
    private List<Conta> contas;

    public Integer getIdCarteiraCobranca() {
        return idCarteiraCobranca;
    }

    public void setIdCarteiraCobranca(Integer idCarteiraCobranca) {
        this.idCarteiraCobranca = idCarteiraCobranca;
    }

    public ContaCorrente getContaCorrente() {
        return contaCorrente;
    }

    public void setContaCorrente(ContaCorrente contaCorrente) {
        this.contaCorrente = contaCorrente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCodigoBeneficiario() {
        return codigoBeneficiario;
    }

    public void setCodigoBeneficiario(Integer codigoBeneficiario) {
        this.codigoBeneficiario = codigoBeneficiario;
    }

    public Integer getCodigoBeneficiarioDv() {
        return codigoBeneficiarioDv;
    }

    public void setCodigoBeneficiarioDv(Integer codigoBeneficiarioDv) {
        this.codigoBeneficiarioDv = codigoBeneficiarioDv;
    }

    public Integer getCodigoConvenio() {
        return codigoConvenio;
    }

    public void setCodigoConvenio(Integer codigoConvenio) {
        this.codigoConvenio = codigoConvenio;
    }

    public Integer getCodigoCarteira() {
        return codigoCarteira;
    }

    public void setCodigoCarteira(Integer codigoCarteira) {
        this.codigoCarteira = codigoCarteira;
    }

    public Boolean getAceite() {
        return aceite;
    }

    public void setAceite(Boolean aceite) {
        this.aceite = aceite;
    }

    public Boolean getBaixa() {
        return baixa;
    }

    public void setBaixa(Boolean baixa) {
        this.baixa = baixa;
    }

    public String getEspecieDocumento() {
        return especieDocumento;
    }

    public void setEspecieDocumento(String especieDocumento) {
        this.especieDocumento = especieDocumento;
    }

    public String getLocalPagamento() {
        return localPagamento;
    }

    public void setLocalPagamento(String localPagamento) {
        this.localPagamento = localPagamento;
    }

    public String getInstrucao01() {
        return instrucao01;
    }

    public void setInstrucao01(String instrucao01) {
        this.instrucao01 = instrucao01;
    }

    public String getInstrucao02() {
        return instrucao02;
    }

    public void setInstrucao02(String instrucao02) {
        this.instrucao02 = instrucao02;
    }

    public String getInstrucao03() {
        return instrucao03;
    }

    public void setInstrucao03(String instrucao03) {
        this.instrucao03 = instrucao03;
    }

    public String getInstrucao04() {
        return instrucao04;
    }

    public void setInstrucao04(String instrucao04) {
        this.instrucao04 = instrucao04;
    }

    public String getInstrucao05() {
        return instrucao05;
    }

    public void setInstrucao05(String instrucao05) {
        this.instrucao05 = instrucao05;
    }

    public String getBeneficiarioNome() {
        return beneficiarioNome;
    }

    public void setBeneficiarioNome(String beneficiarioNome) {
        this.beneficiarioNome = beneficiarioNome;
    }

    public String getBeneficiarioDocumento() {
        return beneficiarioDocumento;
    }

    public void setBeneficiarioDocumento(String beneficiarioDocumento) {
        this.beneficiarioDocumento = beneficiarioDocumento;
    }

    public String getBeneficiarioLogradouro() {
        return beneficiarioLogradouro;
    }

    public void setBeneficiarioLogradouro(String beneficiarioLogradouro) {
        this.beneficiarioLogradouro = beneficiarioLogradouro;
    }

    public String getBeneficiarioBairro() {
        return beneficiarioBairro;
    }

    public void setBeneficiarioBairro(String beneficiarioBairro) {
        this.beneficiarioBairro = beneficiarioBairro;
    }

    public String getBeneficiarioCidade() {
        return beneficiarioCidade;
    }

    public void setBeneficiarioCidade(String beneficiarioCidade) {
        this.beneficiarioCidade = beneficiarioCidade;
    }

    public String getBeneficiarioUf() {
        return beneficiarioUf;
    }

    public void setBeneficiarioUf(String beneficiarioUf) {
        this.beneficiarioUf = beneficiarioUf;
    }

    public String getBeneficiarioCep() {
        return beneficiarioCep;
    }

    public void setBeneficiarioCep(String beneficiarioCep) {
        this.beneficiarioCep = beneficiarioCep;
    }

    public List<Conta> getContas() {
        return contas;
    }

    public void setContas(List<Conta> contas) {
        this.contas = contas;
    }    
    
}
