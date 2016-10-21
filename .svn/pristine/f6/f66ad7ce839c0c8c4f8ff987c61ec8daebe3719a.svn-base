package com.portalpostal.dao;

import com.portalpostal.dao.handler.TipoDocumentoHandler;
import com.portalpostal.model.TipoDocumento;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TipoDocumentoDAO extends GenericDAO { 
    
    private final TipoDocumentoHandler tipoDocumentoHandler;

    public TipoDocumentoDAO(String nameDB) { 
        super(nameDB, TipoDocumentoDAO.class);
        tipoDocumentoHandler = new TipoDocumentoHandler();
    } 

    public List<TipoDocumento> findAll() throws Exception {
        String sql = "SELECT * FROM tipo_documento "
                   + "ORDER BY tipo_documento.idTipoDocumento";        
        return findAll(sql, null, tipoDocumentoHandler);
    }

    public TipoDocumento find(Integer idTipoDocumento) throws Exception {
        String sql = "SELECT * FROM tipo_documento "
                   + "WHERE tipo_documento.idTipoDocumento = :idTipoDocumento";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idTipoDocumento", idTipoDocumento);
        return (TipoDocumento) find(sql, params, tipoDocumentoHandler);
    }

    public TipoDocumento findByDescricao(String descricao) throws Exception {
        String sql = "SELECT * FROM tipo_documento "
                   + "WHERE tipo_documento.descricao = :descricao";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("descricao", descricao);
        return (TipoDocumento) find(sql, params, tipoDocumentoHandler);
    }

    public TipoDocumento save(TipoDocumento tipoDocumento) throws Exception {  
        String sql = "INSERT INTO tipo_documento (descricao) "
                   + "VALUES(:descricao)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("descricao", tipoDocumento.getDescricao());
        Integer idTipoDocumento = save(sql, params, tipoDocumentoHandler);
        return find(idTipoDocumento);
    }

    public TipoDocumento update(TipoDocumento tipoDocumento) throws Exception {
        String sql = "UPDATE tipo_documento "
                   + "SET descricao = :descricao "
                   + "WHERE idTipoDocumento = :idTipoDocumento ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idTipoDocumento", tipoDocumento.getIdTipoDocumento());
        params.put("descricao", tipoDocumento.getDescricao());
        update(sql, params, tipoDocumentoHandler);
        return tipoDocumento;  
    }

    public TipoDocumento remove(Integer idTipoDocumento) throws Exception { 
        String sql = "DELETE FROM tipo_documento WHERE idTipoDocumento = :idTipoDocumento ";
        TipoDocumento tipoDocumento = find(idTipoDocumento);
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idTipoDocumento", idTipoDocumento);
        remove(sql, params, tipoDocumentoHandler);
        return tipoDocumento;
    }
}
