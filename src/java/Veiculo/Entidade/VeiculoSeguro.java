package Veiculo.Entidade;

public class VeiculoSeguro {
    
    private Integer id;
    private Veiculo veiculo;
    private Integer numeroSeguro;
    private String assegurado;
    private Double valorFranquia;
    private String indenizacao;
    
    public VeiculoSeguro() {}

    public VeiculoSeguro(Integer id) {
        this.id = id;
    }

    public VeiculoSeguro(Integer id, Veiculo veiculo, Integer numeroSeguro, String assegurado, Double valorFranquia, String indenizacao) {
        this.id = id;
        this.veiculo = veiculo;
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

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
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
