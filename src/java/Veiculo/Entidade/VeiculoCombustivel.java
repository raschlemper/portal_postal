package Veiculo.Entidade;

import java.util.Date;

public class VeiculoCombustivel {
    
    private Integer id;
    private Veiculo veiculo;
    private String tipo;
    private Integer quantidade;
    private Double valorUnitario;
    private Date data; 
    private Double valorTotal;
    private Integer quilometragemInicial;
    private Integer quilometragemFinal;
    private Integer quilometragemPercorrida;
    
    public VeiculoCombustivel() {}

    public VeiculoCombustivel(Integer id) {
        this.id = id;
    }

    public VeiculoCombustivel(Integer id, Veiculo veiculo, String tipo, Integer quantidade, Double valorUnitario, Date data, Double valorTotal, 
            Integer quilometragemInicial, Integer quilometragemFinal, Integer quilometragemPercorrida) {
        this.id = id;
        this.veiculo = veiculo;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.data = data;
        this.valorTotal = valorTotal;
        this.quilometragemInicial = quilometragemInicial;
        this.quilometragemFinal = quilometragemFinal;
        this.quilometragemPercorrida = quilometragemPercorrida;
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

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Integer getQuilometragemInicial() {
        return quilometragemInicial;
    }

    public void setQuilometragemInicial(Integer quilometragemInicial) {
        this.quilometragemInicial = quilometragemInicial;
    }

    public Integer getQuilometragemFinal() {
        return quilometragemFinal;
    }

    public void setQuilometragemFinal(Integer quilometragemFinal) {
        this.quilometragemFinal = quilometragemFinal;
    }

    public Integer getQuilometragemPercorrida() {
        return quilometragemPercorrida;
    }

    public void setQuilometragemPercorrida(Integer quilometragemPercorrida) {
        this.quilometragemPercorrida = quilometragemPercorrida;
    }
    
}
