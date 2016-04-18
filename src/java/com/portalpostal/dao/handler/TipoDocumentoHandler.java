package com.portalpostal.dao.handler;

import com.portalpostal.model.TipoDocumento;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class TipoDocumentoHandler extends GenericHandler implements ResultSetHandler<TipoDocumento> {

    public TipoDocumentoHandler() {
        super("tipo_documento");
    }
    
    public TipoDocumentoHandler(String table) {
        super(table);
    }

    public TipoDocumento handle(ResultSet result) throws SQLException {
        TipoDocumento tipoDocumento = new TipoDocumento();
        tipoDocumento.setIdTipoDocumento(getInt(result, "idTipoDocumento"));
        tipoDocumento.setDescricao(getString(result, "descricao"));
        return tipoDocumento;
    }
    
}
