/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Veiculo.Entidade;

import java.util.Date;

/**
 *
 * @author rafael
 */
public class VeiculoManutencao {
    
    private Integer id;
    private Veiculo veiculo;
    private String tipo;
    private Integer quilometragem;
    private Double valor;
    private Date data;
    private Date dataAgendamento;
    private Date dataEntrega;
    private String descricao;
    
    public VeiculoManutencao() {}

    public VeiculoManutencao(Integer id, Veiculo veiculo, String tipo, Integer quilometragem, 
            Double valor, Date data, Date dataAgendamento, Date dataEntrega, String descricao) {
        this.id = id;
        this.veiculo = veiculo;
        this.tipo = tipo;
        this.valor = valor;
        this.quilometragem = quilometragem;
        this.data = data;
        this.dataAgendamento = dataAgendamento;
        this.dataEntrega = dataEntrega;
        this.descricao = descricao;        
    }  

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(Integer quilometragem) {
        this.quilometragem = quilometragem;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(Date dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    public Date getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(Date dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
}
