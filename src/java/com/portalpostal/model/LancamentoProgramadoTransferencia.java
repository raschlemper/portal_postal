package com.portalpostal.model;

import com.portalpostal.model.dd.TipoFrequencia;
import java.util.Date;

public class LancamentoProgramadoTransferencia {
    
    private Integer idLancamentoProgramadoTransferencia;
    private LancamentoProgramado lancamentoProgramadoOrigem;
    private LancamentoProgramado lancamentoProgramadoDestino;
    private String numero;
    private TipoDocumento documento;
    private TipoFormaPagamento formaPagamento;  
    private TipoFrequencia frequencia; 
    private Date dataCompetencia;
    private Date dataEmissao;
    private Date dataVencimento;
    private Double valor;
    private String historico;
    private String observacao;
    private String usuario;

    public Integer getIdLancamentoProgramadoTransferencia() {
        return idLancamentoProgramadoTransferencia;
    }

    public void setIdLancamentoProgramadoTransferencia(Integer idLancamentoProgramadoTransferencia) {
        this.idLancamentoProgramadoTransferencia = idLancamentoProgramadoTransferencia;
    }    

    public LancamentoProgramado getLancamentoProgramadoOrigem() {
        return lancamentoProgramadoOrigem;
    }

    public void setLancamentoProgramadoOrigem(LancamentoProgramado lancamentoProgramadoOrigem) {
        this.lancamentoProgramadoOrigem = lancamentoProgramadoOrigem;
    }

    public LancamentoProgramado getLancamentoProgramadoDestino() {
        return lancamentoProgramadoDestino;
    }

    public void setLancamentoProgramadoDestino(LancamentoProgramado lancamentoProgramadoDestino) {
        this.lancamentoProgramadoDestino = lancamentoProgramadoDestino;
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
    
}
