package com.portalpostal.model;

import com.portalpostal.model.dd.TipoFrequencia;
import com.portalpostal.model.dd.TipoLancamento;
import com.portalpostal.model.dd.TipoSituacaoLancamentoProgramado;
import java.util.Date;
import java.util.List;

public class LancamentoProgramado {
    
    private Integer idLancamentoProgramado;
    private Conta conta;
    private PlanoConta planoConta;
    private CentroCusto centroCusto;
    private TipoLancamento tipo;
    private String favorecido;
    private String numero;
    private TipoDocumento documento;
    private TipoFormaPagamento formaPagamento;    
    private TipoFrequencia frequencia; 
    private Integer quantidadeParcela;
    private Integer numeroParcela;
    private Date dataCompetencia;
    private Date dataEmissao;
    private Date dataVencimento;
    private Double valor;
    private TipoSituacaoLancamentoProgramado situacao;
    private String historico;
    private String observacao;
    private String usuario;
    private List<Lancamento> lancamentos;
    private List<LancamentoProgramadoRateio> rateios;

    public Integer getIdLancamentoProgramado() {
        return idLancamentoProgramado;
    }

    public void setIdLancamentoProgramado(Integer idLancamentoProgramado) {
        this.idLancamentoProgramado = idLancamentoProgramado;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public PlanoConta getPlanoConta() {
        return planoConta;
    }

    public void setPlanoConta(PlanoConta planoConta) {
        this.planoConta = planoConta;
    }

    public CentroCusto getCentroCusto() {
        return centroCusto;
    }

    public void setCentroCusto(CentroCusto centroCusto) {
        this.centroCusto = centroCusto;
    }

    public TipoLancamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoLancamento tipo) {
        this.tipo = tipo;
    }

    public String getFavorecido() {
        return favorecido;
    }

    public void setFavorecido(String favorecido) {
        this.favorecido = favorecido;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public TipoDocumento getDocumento() {
        return documento;
    }

    public void setDocumento(TipoDocumento documento) {
        this.documento = documento;
    }

    public TipoFormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(TipoFormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public TipoFrequencia getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(TipoFrequencia frequencia) {
        this.frequencia = frequencia;
    }

    public Integer getQuantidadeParcela() {
        return quantidadeParcela;
    }

    public void setQuantidadeParcela(Integer quantidadeParcela) {
        this.quantidadeParcela = quantidadeParcela;
    }

    public Integer getNumeroParcela() {
        return numeroParcela;
    }

    public void setNumeroParcela(Integer numeroParcela) {
        this.numeroParcela = numeroParcela;
    }

    public Date getDataCompetencia() {
        return dataCompetencia;
    }

    public void setDataCompetencia(Date dataCompetencia) {
        this.dataCompetencia = dataCompetencia;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public TipoSituacaoLancamentoProgramado getSituacao() {
        return situacao;
    }

    public void setSituacao(TipoSituacaoLancamentoProgramado situacao) {
        this.situacao = situacao;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public List<Lancamento> getLancamentos() {
        return lancamentos;
    }

    public void setLancamentos(List<Lancamento> lancamentos) {
        this.lancamentos = lancamentos;
    }

    public List<LancamentoProgramadoRateio> getRateios() {
        return rateios;
    }

    public void setRateios(List<LancamentoProgramadoRateio> rateios) {
        this.rateios = rateios;
    }
    
}
