package Veiculo.Entidade;

import java.util.Date;

public class VeiculoMultaDTO {    
    
    private Integer id;
    private Integer idVeiculo;    
    private String placa;
    private Integer numeroMulta;
    private Date data; 
    private Double valor;
    private String local;
    private String descricao;

    public VeiculoMultaDTO() { }

    public VeiculoMultaDTO(Integer id, Integer idVeiculo, String placa, Integer numeroMulta, Date data, Double valor, String local, String descricao) {
        this.id = id;
        this.idVeiculo = idVeiculo;
        this.placa = placa;
        this.numeroMulta = numeroMulta;
        this.data = data;
        this.valor = valor;
        this.local = local;
        this.descricao = descricao;
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

    public Integer getNumeroMulta() {
        return numeroMulta;
    }

    public void setNumeroMulta(Integer numeroMulta) {
        this.numeroMulta = numeroMulta;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }    
        
}
