/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidade;

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

    public ServicoECT(int codECT, String nomeServico, String grupoServico, int avista, int ativo, String nomeSimples, int idServicoECT) {
        this.codECT = codECT;
        this.nomeServico = nomeServico;
        this.grupoServico = grupoServico;
        this.avista = avista;
        this.ativo = ativo;
        this.nomeSimples = nomeSimples;
        this.idServicoECT = idServicoECT;
    }

    public ServicoECT(int codECT, int codECT_reversa, String nomeServico, String grupoServico, int avista, int ativo, String nomeSimples, int idServicoECT, String tipo_agencia) {
        this.codECT = codECT;
        this.codECT_reversa = codECT_reversa;
        this.nomeServico = nomeServico;
        this.grupoServico = grupoServico;
        this.avista = avista;
        this.ativo = ativo;
        this.nomeSimples = nomeSimples;
        this.idServicoECT = idServicoECT;
        this.tipo_agencia = tipo_agencia;
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
    
    
}
