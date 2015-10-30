package Veiculo.Entidade;

import java.util.Date;

public class VeiculoSinistro {
    
    private Integer id;
    private Veiculo veiculo;
    private String tipo;
    private Integer boletimOcorrencia;
    private Date data;
    private String local;
    private String responsavel;
    private String descricao;
    
    public VeiculoSinistro() {}

    public VeiculoSinistro(Integer id) {
        this.id = id;
    }

    public VeiculoSinistro(Integer id, Veiculo veiculo, String tipo, Integer boletimOcorrencia, Date data, String local, String responsavel, String descricao) {
        this.id = id;
        this.veiculo = veiculo;
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
