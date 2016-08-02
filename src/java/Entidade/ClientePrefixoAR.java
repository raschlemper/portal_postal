/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidade;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Ricardinho
 */
public class ClientePrefixoAR {
    
    private int idCliente;
    private String grupoServico;
    private String prefixo;

    public ClientePrefixoAR(ResultSet r) throws SQLException {
        this.idCliente = r.getInt("idCliente");
        this.grupoServico = r.getString("grupoServico");
        this.prefixo = r.getString("prefixo");
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getGrupoServico() {
        return grupoServico;
    }

    public void setGrupoServico(String grupoServico) {
        this.grupoServico = grupoServico;
    }

    public String getPrefixo() {
        return prefixo;
    }

    public void setPrefixo(String prefixo) {
        this.prefixo = prefixo;
    }
    
    
}
