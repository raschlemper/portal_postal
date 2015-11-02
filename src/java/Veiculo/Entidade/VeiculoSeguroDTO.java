package Veiculo.Entidade;

public class VeiculoSeguroDTO {    
    
    private Integer id;
    private Integer idVeiculo;   
    private String placa;
    private Integer numeroSeguro;
    private String assegurado;
    private Double valorFranquia;
    private String indenizacao;

    public VeiculoSeguroDTO() { }

    public VeiculoSeguroDTO(Integer id, Integer idVeiculo, String placa, Integer numeroSeguro, String assegurado, Double valorFranquia, String indenizacao) {
        this.id = id;
        this.idVeiculo = idVeiculo;
        this.placa = placa;
        this.numeroSeguro = numeroSeguro;
        this.assegurado = assegurado;
        this.valorFranquia = valorFranquia;
        this.indenizacao = indenizacao;
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

    public Integer getNumeroSeguro() {
        return numeroSeguro;
    }

    public void setNumeroSeguro(Integer numeroSeguro) {
        this.numeroSeguro = numeroSeguro;
    }

    public String getAssegurado() {
        return assegurado;
    }

    public void setAssegurado(String assegurado) {
        this.assegurado = assegurado;
    }

    public Double getValorFranquia() {
        return valorFranquia;
    }

    public void setValorFranquia(Double valorFranquia) {
        this.valorFranquia = valorFranquia;
    }

    public String getIndenizacao() {
        return indenizacao;
    }

    public void setIndenizacao(String indenizacao) {
        this.indenizacao = indenizacao;
    }
        
}
