package Veiculo.Entidade;

import java.util.Date;

public class VeiculoSinistroDTO {    
    
    private Integer id;
    private Integer idVeiculo;   
    private String placa;
    private String tipo;
    private Integer boletimOcorrencia;
    private Date data;
    private String local;
    private String responsavel;
    private String descricao;
    
    public VeiculoSinistroDTO() { }

    public VeiculoSinistroDTO(Integer id, Integer idVeiculo, String placa, String tipo, Integer boletimOcorrencia, Date data, String local, String responsavel, 
            String descricao) {
        this.id = id;
        this.idVeiculo = idVeiculo;
        this.placa = placa;
        this.tipo = tipo;
        this.boletimOcorrencia = boletimOcorrencia;
        this.data = data;
        this.local = local;
        this.responsavel = responsavel;
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

    public Integer getBoletimOcorrencia() {
        return boletimOcorrencia;
    }

    public void setBoletimOcorrencia(Integer boletimOcorrencia) {
        this.boletimOcorrencia = boletimOcorrencia;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
}
