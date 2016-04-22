package com.portalpostal.model;

import com.portalpostal.model.dd.TipoFrequencia;
import com.portalpostal.model.serializer.JsonDateSerializer;
import java.util.Date;
import org.codehaus.jackson.map.annotate.JsonSerialize;

public class LancamentoTransferenciaProgramado {
    
    private Integer idLancamentoTransferenciaProgramado;
    private LancamentoProgramado lancamentoProgramadoOrigem;
    private LancamentoProgramado lancamentoProgramadoDestino;
    private String numero;
    private TipoDocumento documento;
    private TipoFormaPagamento formaPagamento;  
    private TipoFrequencia frequencia; 
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date dataEmissao;
    private Double valor;
    private String historico;

    public Integer getIdLancamentoTransferenciaProgramado() {
        return idLancamentoTransferenciaProgramado;
    }

    public void setIdLancamentoTransferenciaProgramado(Integer idLancamentoTransferenciaProgramado) {
        this.idLancamentoTransferenciaProgramado = idLancamentoTransferenciaProgramado;
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

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
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
    
}
