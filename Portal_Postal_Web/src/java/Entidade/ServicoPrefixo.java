/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidade;

/**
 *
 * @author scc4
 */
public class ServicoPrefixo {
    private String grupoServico;
    private String codigosECT;
    private int avista;
    private String prefixo;
    private int status;

    public ServicoPrefixo(String grupoServico, String codigosECT, int avista, String prefixo, int status) {
        this.grupoServico = grupoServico;
        this.codigosECT = codigosECT;
        this.avista = avista;
        this.prefixo = prefixo;
        this.status = status;
    }

    public String getGrupoServico() {
        return grupoServico;
    }

    public void setGrupoServico(String grupoServico) {
        this.grupoServico = grupoServico;
    }

    public String getCodigosECT() {
        return codigosECT;
    }

    public void setCodigosECT(String codigosECT) {
        this.codigosECT = codigosECT;
    }

    public int getAvista() {
        return avista;
    }

    public void setAvista(int avista) {
        this.avista = avista;
    }

    public String getPrefixo() {
        return prefixo;
    }

    public void setPrefixo(String prefixo) {
        this.prefixo = prefixo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    
    
}
