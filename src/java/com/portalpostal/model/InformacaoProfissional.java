package com.portalpostal.model;

import java.util.Date;

public class InformacaoProfissional {
    
    private Integer idInformacaoProfissional;
    private Colaborador colaborador;
    private String cargoFuncao;
    private Double salario;
    private Date dataAdmissao;
    private Date dataDemissao;
    private String pisPasep;
    private String tituloEleitoral;
    private String certificadoReservista;
    private String ctps;
    private Date horarioEntrada;
    private Date horarioSaida;
    private Date intervaloDe;
    private Date intervaloAte;
    private String observacao;

    public Integer getIdInformacaoProfissional() {
        return idInformacaoProfissional;
    }

    public void setIdInformacaoProfissional(Integer idInformacaoProfissional) {
        this.idInformacaoProfissional = idInformacaoProfissional;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public String getCargoFuncao() {
        return cargoFuncao;
    }

    public void setCargoFuncao(String cargoFuncao) {
        this.cargoFuncao = cargoFuncao;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public Date getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(Date dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public Date getDataDemissao() {
        return dataDemissao;
    }

    public void setDataDemissao(Date dataDemissao) {
        this.dataDemissao = dataDemissao;
    }

    public String getPisPasep() {
        return pisPasep;
    }

    public void setPisPasep(String pisPasep) {
        this.pisPasep = pisPasep;
    }

    public String getTituloEleitoral() {
        return tituloEleitoral;
    }

    public void setTituloEleitoral(String tituloEleitoral) {
        this.tituloEleitoral = tituloEleitoral;
    }

    public String getCertificadoReservista() {
        return certificadoReservista;
    }

    public void setCertificadoReservista(String certificadoReservista) {
        this.certificadoReservista = certificadoReservista;
    }

    public String getCtps() {
        return ctps;
    }

    public void setCtps(String ctps) {
        this.ctps = ctps;
    }

    public Date getHorarioEntrada() {
        return horarioEntrada;
    }

    public void setHorarioEntrada(Date horarioEntrada) {
        this.horarioEntrada = horarioEntrada;
    }

    public Date getHorarioSaida() {
        return horarioSaida;
    }

    public void setHorarioSaida(Date horarioSaida) {
        this.horarioSaida = horarioSaida;
    }

    public Date getIntervaloDe() {
        return intervaloDe;
    }

    public void setIntervaloDe(Date intervaloDe) {
        this.intervaloDe = intervaloDe;
    }

    public Date getIntervaloAte() {
        return intervaloAte;
    }

    public void setIntervaloAte(Date intervaloAte) {
        this.intervaloAte = intervaloAte;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
    
}
