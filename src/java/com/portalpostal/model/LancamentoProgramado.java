package com.portalpostal.model;

import com.portalpostal.model.dd.TipoFrequencia;
import com.portalpostal.model.dd.TipoLancamento;
import com.portalpostal.model.dd.TipoSituacaoLancamento;
import com.portalpostal.model.dd.TipoSituacaoLancamentoProgramado;
import com.portalpostal.model.serializer.JsonDateSerializer;
import java.util.Date;
import org.codehaus.jackson.map.annotate.JsonSerialize;

public class LancamentoProgramado {
    
    private Integer idLancamentoProgramado;
    private Conta conta;
    private PlanoConta planoConta;
    private LancamentoParcelado lancamentoParcelado;
    private TipoLancamento tipo;
    private String favorecido;
    private String numero;
    private TipoDocumento documento;
    private TipoFormaPagamento formaPagamento;    
    private TipoFrequencia frequencia; 
    private Integer numeroParcela;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date dataEmissao;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date dataVencimento;
    private Double valor;
    private TipoSituacaoLancamentoProgramado situacao;
    private String historico;

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

    public LancamentoParcelado getLancamentoParcelado() {
        return lancamentoParcelado;
    }

    public void setLancamentoParcelado(LancamentoParcelado lancamentoParcelado) {
        this.lancamentoParcelado = lancamentoParcelado;
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

    public Integer getNumeroParcela() {
        return numeroParcela;
    }

    public void setNumeroParcela(Integer numeroParcela) {
        this.numeroParcela = numeroParcela;
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
    
    
    
}
