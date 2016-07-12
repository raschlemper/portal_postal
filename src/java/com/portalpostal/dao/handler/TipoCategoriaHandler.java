package com.portalpostal.dao.handler;

import com.portalpostal.model.TipoCategoria;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class TipoCategoriaHandler extends GenericHandler implements ResultSetHandler<TipoCategoria> {

    public TipoCategoriaHandler() {
        super("tipo_categoria");
    }
    
    public TipoCategoriaHandler(String table) {
        super(table);
    }

    public TipoCategoria handle(ResultSet result) throws SQLException {
        TipoCategoria tipoCategoria = new TipoCategoria();
        tipoCategoria.setIdTipoCategoria(getInt(result, "idTipoCategoria"));
        tipoCategoria.setDescricao(getString(result, "descricao"));
        return tipoCategoria;
    }
    
}
