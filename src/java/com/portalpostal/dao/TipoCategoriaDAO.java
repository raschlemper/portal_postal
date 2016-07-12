package com.portalpostal.dao;

import com.portalpostal.dao.handler.TipoCategoriaHandler;
import com.portalpostal.model.TipoCategoria;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TipoCategoriaDAO extends GenericDAO { 
    
    private final TipoCategoriaHandler tipoCategoriaHandler;

    public TipoCategoriaDAO(String nameDB) { 
        super(nameDB, TipoCategoriaDAO.class);
        tipoCategoriaHandler = new TipoCategoriaHandler();
    } 

    public List<TipoCategoria> findAll() throws Exception {
        String sql = "SELECT * FROM tipo_categoria "
                   + "ORDER BY tipo_categoria.idTipoCategoria";        
        return findAll(sql, null, tipoCategoriaHandler);
    }

    public TipoCategoria find(Integer idTipoCategoria) throws Exception {
        String sql = "SELECT * FROM tipo_categoria "
                   + "WHERE tipo_categoria.idTipoCategoria = :idTipoCategoria";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idTipoCategoria", idTipoCategoria);
        return (TipoCategoria) find(sql, params, tipoCategoriaHandler);
    }

    public TipoCategoria findByDescricao(String descricao) throws Exception {
        String sql = "SELECT * FROM tipo_categoria "
                   + "WHERE tipo_categoria.descricao = :descricao";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("descricao", descricao);
        return (TipoCategoria) find(sql, params, tipoCategoriaHandler);
    }

    public TipoCategoria save(TipoCategoria tipoCategoria) throws Exception {  
        String sql = "INSERT INTO tipo_categoria (descricao) "
                   + "VALUES(:descricao)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("descricao", tipoCategoria.getDescricao());
        Integer idTipoCategoria = save(sql, params, tipoCategoriaHandler);
        return find(idTipoCategoria);
    }

    public TipoCategoria update(TipoCategoria tipoCategoria) throws Exception {
        String sql = "UPDATE tipo_categoria "
                   + "SET descricao = :descricao "
                   + "WHERE idTipoCategoria = :idTipoCategoria ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idTipoCategoria", tipoCategoria.getIdTipoCategoria());
        params.put("descricao", tipoCategoria.getDescricao());
        update(sql, params, tipoCategoriaHandler);
        return tipoCategoria;  
    }

    public TipoCategoria remove(Integer idTipoCategoria) throws Exception { 
        String sql = "DELETE FROM tipo_categoria WHERE idTipoCategoria = :idTipoCategoria ";
        TipoCategoria tipoCategoria = find(idTipoCategoria);
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idTipoCategoria", idTipoCategoria);
        remove(sql, params, tipoCategoriaHandler);
        return tipoCategoria;
    }
}
