/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidade;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author RICARDINHO
 */
public class ServicoECT {
    
    private int codECT;
    private int codECT_reversa;
    private String nomeServico;
    private String grupoServico;
    private int avista;
    private int ativo;
    private String nomeSimples;
    private int idServicoECT;
    private String tipo_agencia;
    private String tipo;
    private int faturar;
    private int tem_registro;
    private int tem_peso;
    private int tem_medida;
    private int tem_qtd;
    private int tem_destinatario;
    private float peso_max;
    private float peso_cubico_min;
    private float altura_min;
    private float largura_min;
    private float comprimento_min;
    private float maior_dimensao_max;
    private float soma_dimensoes_max;
    private String adicionais_permitidos;
    private int internacional;
    private int confere_abrangencia;    
    private int is_debito;    
    private int grupo_remuneracao;    
    private float porcent_remuneracao;    

    public ServicoECT(ResultSet result) throws SQLException {
        this.codECT = result.getInt("codECT");
        this.codECT_reversa = result.getInt("codECT_reverso");
        this.nomeServico = result.getString("nomeServico");
        this.grupoServico = result.getString("grupoServico");
        this.avista = result.getInt("avista");
        this.ativo = result.getInt("ativo");
        this.nomeSimples = result.getString("nomeSimples");
        this.idServicoECT = result.getInt("idServicoECT");
        this.tipo_agencia = result.getString("tipo_agencia");
        this.tipo = result.getString("tipo");
        this.faturar = result.getInt("faturar");
        this.tem_registro = result.getInt("tem_registro");
        this.tem_peso = result.getInt("tem_peso");
        this.tem_medida = result.getInt("tem_medida");
        this.tem_qtd = result.getInt("tem_qtd");
        this.tem_destinatario = result.getInt("tem_destinatario");
        this.peso_max = result.getFloat("peso_max");
        this.peso_cubico_min = result.getFloat("peso_cubico_min");
        this.altura_min = result.getFloat("altura_min");
        this.largura_min = result.getFloat("largura_min");
        this.comprimento_min = result.getFloat("comprimento_min");
        this.maior_dimensao_max = result.getFloat("maior_dimensao_max");
        this.soma_dimensoes_max = result.getFloat("soma_dimensoes_max");
        this.adicionais_permitidos = result.getString("adicionais_permitidos");
        this.internacional = result.getInt("internacional");
        this.confere_abrangencia = result.getInt("confere_abrangencia");
        this.is_debito = result.getInt("is_debito");
        this.grupo_remuneracao = result.getInt("grupo_remuneracao");
        this.porcent_remuneracao = result.getFloat("porcent_remuneracao");
    }    

    public int getIs_debito() {
        return is_debito;
    }

    public void setIs_debito(int is_debito) {
        this.is_debito = is_debito;
    }

    public int getGrupo_remuneracao() {
        return grupo_remuneracao;
    }

    public void setGrupo_remuneracao(int grupo_remuneracao) {
        this.grupo_remuneracao = grupo_remuneracao;
    }

    public float getPorcent_remuneracao() {
        return porcent_remuneracao;
    }

    public void setPorcent_remuneracao(float porcent_remuneracao) {
        this.porcent_remuneracao = porcent_remuneracao;
    }
    
    public int getCodECT_reversa() {
        return codECT_reversa;
    }

    public void setCodECT_reversa(int codECT_reversa) {
        this.codECT_reversa = codECT_reversa;
    }
    
    public String getTipo_agencia() {
        return tipo_agencia;
    }

    public void setTipo_agencia(String tipo_agencia) {
        this.tipo_agencia = tipo_agencia;
    }
    
    public int getIdServicoECT() {
        return idServicoECT;
    }

    public void setIdServicoECT(int idServicoECT) {
        this.idServicoECT = idServicoECT;
    }

    public String getNomeSimples() {
        return nomeSimples;
    }

    public void setNomeSimples(String nomeSimples) {
        this.nomeSimples = nomeSimples;
    }

    public int getAtivo() {
        return ativo;
    }

    public void setAtivo(int ativo) {
        this.ativo = ativo;
    }

    public int getAvista() {
        return avista;
    }

    public void setAvista(int avista) {
        this.avista = avista;
    }

    public int getCodECT() {
        return codECT;
    }

    public void setCodECT(int codECT) {
        this.codECT = codECT;
    }

    public String getGrupoServico() {
        return grupoServico;
    }

    public void setGrupoServico(String grupoServico) {
        this.grupoServico = grupoServico;
    }

    public String getNomeServico() {
        return nomeServico;
    }

    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getFaturar() {
        return faturar;
    }

    public void setFaturar(int faturar) {
        this.faturar = faturar;
    }

    public int getTem_registro() {
        return tem_registro;
    }

    public void setTem_registro(int tem_registro) {
        this.tem_registro = tem_registro;
    }

    public int getTem_peso() {
        return tem_peso;
    }

    public void setTem_peso(int tem_peso) {
        this.tem_peso = tem_peso;
    }

    public int getTem_medida() {
        return tem_medida;
    }

    public void setTem_medida(int tem_medida) {
        this.tem_medida = tem_medida;
    }

    public int getTem_qtd() {
        return tem_qtd;
    }

    public void setTem_qtd(int tem_qtd) {
        this.tem_qtd = tem_qtd;
    }

    public int getTem_destinatario() {
        return tem_destinatario;
    }

    public void setTem_destinatario(int tem_destinatario) {
        this.tem_destinatario = tem_destinatario;
    }

    public float getPeso_max() {
        return peso_max;
    }

    public void setPeso_max(float peso_max) {
        this.peso_max = peso_max;
    }

    public float getPeso_cubico_min() {
        return peso_cubico_min;
    }

    public void setPeso_cubico_min(float peso_cubico_min) {
        this.peso_cubico_min = peso_cubico_min;
    }

    public float getAltura_min() {
        return altura_min;
    }

    public void setAltura_min(float altura_min) {
        this.altura_min = altura_min;
    }

    public float getLargura_min() {
        return largura_min;
    }

    public void setLargura_min(float largura_min) {
        this.largura_min = largura_min;
    }

    public float getComprimento_min() {
        return comprimento_min;
    }

    public void setComprimento_min(float comprimento_min) {
        this.comprimento_min = comprimento_min;
    }

    public float getMaior_dimensao_max() {
        return maior_dimensao_max;
    }

    public void setMaior_dimensao_max(float maior_dimensao_max) {
        this.maior_dimensao_max = maior_dimensao_max;
    }

    public float getSoma_dimensoes_max() {
        return soma_dimensoes_max;
    }

    public void setSoma_dimensoes_max(float soma_dimensoes_max) {
        this.soma_dimensoes_max = soma_dimensoes_max;
    }

    public String getAdicionais_permitidos() {
        return adicionais_permitidos;
    }

    public void setAdicionais_permitidos(String adicionais_permitidos) {
        this.adicionais_permitidos = adicionais_permitidos;
    }

    public int getInternacional() {
        return internacional;
    }

    public void setInternacional(int internacional) {
        this.internacional = internacional;
    }

    public int getConfere_abrangencia() {
        return confere_abrangencia;
    }

    public void setConfere_abrangencia(int confere_abrangencia) {
        this.confere_abrangencia = confere_abrangencia;
    }
    
    
    
}
