package com.portalpostal.dao.handler;

import com.portalpostal.model.Lancamento;
import com.portalpostal.model.LancamentoAnexo;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class LancamentoAnexoHandler extends GenericHandler implements ResultSetHandler<LancamentoAnexo> {
        
    public LancamentoAnexoHandler() {
        super("lancamento_anexo");
    }
    
    public LancamentoAnexoHandler(String table) {
        super(table);
    }

    public LancamentoAnexo handle(ResultSet result) throws SQLException {
        LancamentoAnexo lancamentoAnexo = new LancamentoAnexo();
        lancamentoAnexo.setIdLancamentoAnexo(getInt(result, "idLancamentoAnexo"));
        lancamentoAnexo.setLancamento(getLancamento(result));
        lancamentoAnexo.setNome(getString(result, "nome"));
        lancamentoAnexo.setAnexo(getBinaryStream(result, "anexo"));
        lancamentoAnexo.setUsuario(getString(result, "usuario"));
        return lancamentoAnexo;
    }
    
    private Lancamento getLancamento(ResultSet result) throws SQLException {
        if(!existColumn(result, "lancamento.idLancamento")) return null;
        if(!existFKValue(result, "lancamento.idLancamento")) return null;
        return new LancamentoHandler().handle(result); 
    }
    
}
