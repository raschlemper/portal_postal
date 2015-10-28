package Veiculo.Entidade;

public class VeiculoManutencaoDTO {    
    
    private Integer id;
    private Integer idVeiculo;    
    private String placa;
    private String tipo;
    private Integer quilometragem;
    private Double valor;
    private String data;
    private String dataAgendamento;
    private String dataEntrega;
    private String descricao;

    public VeiculoManutencaoDTO() { }

    public VeiculoManutencaoDTO(Integer id, Integer idVeiculo, String placa, String tipo, Integer quilometragem, Double valor, String data, String dataAgendamento, String dataEntrega, String descricao) {
        this.id = id;
        this.idVeiculo = idVeiculo;
        this.placa = placa;
        this.tipo = tipo;
        this.quilometragem = quilometragem;
        this.valor = valor;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(String dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    public String getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(String dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    
}
