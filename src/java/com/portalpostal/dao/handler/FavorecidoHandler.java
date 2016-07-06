package com.portalpostal.dao.handler;

import Entidade.Clientes;
import com.portalpostal.model.Colaborador;
import com.portalpostal.model.Favorecido;
import com.portalpostal.model.dd.TipoFavorecido;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class FavorecidoHandler extends GenericHandler implements ResultSetHandler<Favorecido> {
        
    public FavorecidoHandler() {
        super("favorecido");
    }
    
    public FavorecidoHandler(String table) {
        super(table);
    }

    public Favorecido handle(ResultSet result) throws SQLException {
        Favorecido favorecido = new Favorecido();
        favorecido.setIdFavorecido(getInt(result, "idFavorecido"));
        favorecido.setTipo(getTipo(result));
        favorecido.setColaborador(getColaborador(result));
//        favorecido.setFornecedor(getFornecedor(result));
        favorecido.setCliente(getCliente(result));
        favorecido.setNome(getString(result, "nome"));
        return favorecido;
    }
    
    private TipoFavorecido getTipo(ResultSet result) throws SQLException {
        Integer id = getInt(result, "tipo");
        if(id == null) return null;
        return TipoFavorecido.values()[getInt(result, "tipo")];
    }
    
    private Colaborador getColaborador(ResultSet result) throws SQLException {
        if(!existColumn(result, "colaborador.idColaborador")) return null;
        if(!existFKValue(result, "colaborador.idColaborador")) return null;
        return new ColaboradorHandler().handle(result);         
    }
    
    private Clientes getCliente(ResultSet result) throws SQLException {
        if(!existColumn(result, "clientes.codigo")) return null;
        if(!existFKValue(result, "clientes.codigo")) return null;
        return new ClienteHandler().handle(result);         
    }
    
}
