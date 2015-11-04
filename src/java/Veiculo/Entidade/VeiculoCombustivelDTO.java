package Veiculo.Entidade;

public class VeiculoCombustivelDTO {    
    
    private Integer id;
    private Integer idVeiculo;    
    private String placa;
    private String tipo;
    private Integer quantidade;
    private Double valor;
    private String data; 
    private Integer media;
    private Double valorTotal;
    private Integer quilometragemInicial;
    private Integer quilometragemFinal;
    private Integer quilometragemPercorrida;

    public VeiculoCombustivelDTO() { }

    public VeiculoCombustivelDTO(Integer id, Integer idVeiculo, String placa, String tipo, Integer quantidade, Double valor, String data, Integer media, Double valorTotal, Integer quilometragemInicial, Integer quilometragemFinal, Integer quilometragemPercorrida) {
        this.id = id;
        this.idVeiculo = idVeiculo;
        this.placa = placa;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.valor = valor;
        this.data = data;
        this.media = media;
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

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getMedia() {
        return media;
    }

    public void setMedia(Integer media) {
        this.media = media;
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
