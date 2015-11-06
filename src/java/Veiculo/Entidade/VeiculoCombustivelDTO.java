package Veiculo.Entidade;

import java.text.DecimalFormat;

public class VeiculoCombustivelDTO {    
    
    private Integer id;
    private Integer idVeiculo;    
    private String placa;
    private String tipo;
    private Integer quantidade;
    private Double valorUnitario;
    private String data; 
    private Double valorTotal;
    private Integer quilometragemInicial;
    private Integer quilometragemFinal;
    private Integer quilometragemPercorrida;
    
    private DecimalFormat decimalFormat = new DecimalFormat("#.##");


    public VeiculoCombustivelDTO() { }

    public VeiculoCombustivelDTO(Integer id, Integer idVeiculo, String placa, String tipo, Integer quantidade, Double valorUnitario, String data, 
            Double valorTotal, Integer quilometragemInicial, Integer quilometragemFinal, Integer quilometragemPercorrida) {
        this.id = id;
        this.idVeiculo = idVeiculo;
        this.placa = placa;
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

    public Integer getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(Integer idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getValorTotal() {
        return decimalFormat.format(valorTotal);
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
