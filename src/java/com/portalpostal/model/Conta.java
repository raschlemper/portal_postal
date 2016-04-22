package com.portalpostal.model;

import com.portalpostal.model.dd.TipoConta;
import com.portalpostal.model.dd.TipoStatusConta;
import com.portalpostal.model.serializer.JsonDateSerializer;
import java.util.Date;
import java.util.List;
import org.codehaus.jackson.map.annotate.JsonSerialize;

public class Conta {
    
    private Integer idConta;
    private ContaCorrente contaCorrente;
    private CartaoCredito cartaoCredito;
    private String nome;
    private TipoConta tipo;
    private TipoStatusConta status;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date dataAbertura;
    private Double valorSaldoAbertura;   
    private Double saldo; 
    private List<Lancamento> lancamentos;
    private List<LancamentoProgramado> lancamentosProgramados;

    public Integer getIdConta() {
        return idConta;
    }

    public void setIdConta(Integer idConta) {
        this.idConta = idConta;
    }

    public ContaCorrente getContaCorrente() {
        return contaCorrente;
    }

    public void setContaCorrente(ContaCorrente contaCorrente) {
        this.contaCorrente = contaCorrente;
    }

    public CartaoCredito getCartaoCredito() {
        return cartaoCredito;
    }

    public void setCartaoCredito(CartaoCredito cartaoCredito) {
        this.cartaoCredito = cartaoCredito;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoConta getTipo() {
        return tipo;
    }

    public void setTipo(TipoConta tipo) {
        this.tipo = tipo;
    }

    public TipoStatusConta getStatus() {
        return status;
    }

    public void setStatus(TipoStatusConta status) {
        this.status = status;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public Double getValorSaldoAbertura() {
        return valorSaldoAbertura;
    }

    public void setValorSaldoAbertura(Double valorSaldoAbertura) {
        this.valorSaldoAbertura = valorSaldoAbertura;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
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