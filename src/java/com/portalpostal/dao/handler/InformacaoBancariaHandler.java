package com.portalpostal.dao.handler;

import com.portalpostal.model.Banco;
import com.portalpostal.model.Colaborador;
import com.portalpostal.model.InformacaoBancaria;
import com.portalpostal.model.dd.TipoConta;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class InformacaoBancariaHandler extends GenericHandler implements ResultSetHandler<InformacaoBancaria> {
        
    public InformacaoBancariaHandler() {
        super("informacao_bancaria");
    }
    
    public InformacaoBancariaHandler(String table) {
        super(table);
    }

    public InformacaoBancaria handle(ResultSet result) throws SQLException {
        InformacaoBancaria informacaoBancaria = new InformacaoBancaria();
        informacaoBancaria.setIdInformacaoBancaria(getInt(result, "idInformacaoBancaria"));
//        informacaoBancaria.setColaborador(getColaborador(result));  
        informacaoBancaria.setBanco(getBanco(result));
        informacaoBancaria.setTipoConta(TipoConta.values()[getInt(result, "tipoConta")]);      
        informacaoBancaria.setAgencia(getInt(result, "agencia"));
        informacaoBancaria.setAgenciaDv(getInt(result, "agencia_dv"));
        informacaoBancaria.setContaCorrente(getInt(result, "contaCorrente"));
        informacaoBancaria.setContaCorrenteDv(getInt(result, "contaCorrente_dv"));
        return informacaoBancaria;
    }
    
    private Colaborador getColaborador(ResultSet result) throws SQLException {
        if(!existColumn(result, "colaborador.idColaborador")) return null;
        return new ColaboradorHandler().handle(result); 
    }
    
    private Banco getBanco(ResultSet result) throws SQLException {
        if(!existColumn(result, "banco.idBanco")) return null;
        return new BancoHandler().handle(result); 
    }
    
}
