package com.portalpostal.model;

import com.portalpostal.model.dd.TipoEstadoCivil;
import com.portalpostal.model.dd.TipoSexo;
import com.portalpostal.model.dd.TipoStatusColaborador;
import java.util.Date;
import java.util.List;

public class Colaborador {
    
    private Integer idColaborador;
    private String nome;
    private TipoStatusColaborador status;
    private String cpf;
    private String rg;
    private TipoSexo sexo;
    private Date dataNascimento;
    private String telefone;
    private String celular;
    private String email;
    private String conjuge;
    private TipoEstadoCivil estadoCivil;
    private String naturalidade;
    private String nacionalidade;
    private Integer quantidadeDependente;
    private String nomePai;
    private String nomeMae;
    private String observacao;
    private Endereco endereco;
    private InformacaoProfissional informacaoProfissional;
    private InformacaoBancaria informacaoBancaria;
    private List<Lancamento> lancamentos;
    private List<LancamentoProgramado> lancamentosProgramados;

    public Integer getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoStatusColaborador getStatus() {
        return status;
    }

    public void setStatus(TipoStatusColaborador status) {
        this.status = status;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public TipoSexo getSexo() {
        return sexo;
    }

    public void setSexo(TipoSexo sexo) {
        this.sexo = sexo;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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

    public String getConjuge() {
        return conjuge;
    }

    public void setConjuge(String conjuge) {
        this.conjuge = conjuge;
    }

    public TipoEstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(TipoEstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public Integer getQuantidadeDependente() {
        return quantidadeDependente;
    }

    public void setQuantidadeDependente(Integer quantidadeDependente) {
        this.quantidadeDependente = quantidadeDependente;
    }

    public String getNomePai() {
        return nomePai;
    }

    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public InformacaoProfissional getInformacaoProfissional() {
        return informacaoProfissional;
    }

    public void setInformacaoProfissional(InformacaoProfissional informacaoProfissional) {
        this.informacaoProfissional = informacaoProfissional;
    }

    public InformacaoBancaria getInformacaoBancaria() {
        return informacaoBancaria;
    }

    public void setInformacaoBancaria(InformacaoBancaria informacaoBancaria) {
        this.informacaoBancaria = informacaoBancaria;
    }

    public List<Lancamento> getLancamentos() {
        return lancamentos;
    }

    public void setLancamentos(List<Lancamento> lancamentos) {
        this.lancamentos = lancamentos;
    }

    public List<LancamentoProgramado> getLancamentosProgramados() {
        return lancamentosProgramados;
    }

    public void setLancamentosProgramados(List<LancamentoProgramado> lancamentosProgramados) {
        this.lancamentosProgramados = lancamentosProgramados;
    }
    
}
