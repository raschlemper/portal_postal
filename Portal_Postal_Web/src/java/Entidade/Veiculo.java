/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidade;

/**
 *
 * @author rafael
 */
public class Veiculo {
    
    private Integer id;
    private String marca;
    private String modelo; 
    private String placa;
    private Integer anoFabricacao;
    private Integer anoModelo;
    private String chassis;
    private String renavam;
    private Integer quilometragem;
    private String combustivel;
    private String status;
    private String situacao;
    
    public Veiculo() {}

    public Veiculo(Integer id, String marca, String modelo, String placa, Integer anoFabricacao, Integer anoModelo, String chassis, String renavam, Integer quilometragem, String combustivel, String status, String situacao) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.anoFabricacao = anoFabricacao;
        this.anoModelo = anoModelo;
        this.chassis = chassis;
        this.renavam = renavam;
        this.quilometragem = quilometragem;
        this.combustivel = combustivel;
        this.status = status;
        this.situacao = situacao;
    }   

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Integer getAnoFabricacao() {
        return anoFabricacao;
    }

    public void setAnoFabricacao(Integer anoFabricacao) {
        this.anoFabricacao = anoFabricacao;
    }

    public Integer getAnoModelo() {
        return anoModelo;
    }

    public void setAnoModelo(Integer anoModelo) {
        this.anoModelo = anoModelo;
    }

    public String getChassis() {
        return chassis;
    }

    public void setChassis(String chassis) {
        this.chassis = chassis;
    }

    public String getRenavam() {
        return renavam;
    }

    public void setRenavam(String renavam) {
        this.renavam = renavam;
    }

    public Integer getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(Integer quilometragem) {
        this.quilometragem = quilometragem;
    }

    public String getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(String combustivel) {
        this.combustivel = combustivel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
    
    
}
