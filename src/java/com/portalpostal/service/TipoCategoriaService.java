package com.portalpostal.service;

import com.portalpostal.dao.TipoCategoriaDAO;
import com.portalpostal.model.TipoCategoria;
import java.util.List;

public class TipoCategoriaService {
    
    private final String nomeBD;
    
    private TipoCategoriaDAO tipoCategoriaDAO;

    public TipoCategoriaService(String nomeBD) {
        this.nomeBD = nomeBD;
    }

    public void init() {
        tipoCategoriaDAO = new TipoCategoriaDAO(nomeBD);
    }
    
    public List<TipoCategoria> findAll() throws Exception {
        init();
        return tipoCategoriaDAO.findAll();
    }  
    
    public TipoCategoria find(Integer idTipoCategoria) throws Exception {
        init();
        return tipoCategoriaDAO.find(idTipoCategoria);
    } 
    
    public TipoCategoria findByDescricao(String descricao) throws Exception {
        init();
        return tipoCategoriaDAO.findByDescricao(descricao);
    } 
    
    public TipoCategoria save(TipoCategoria tipoCategoria) throws Exception {
        init();
        validation(tipoCategoria);
        return tipoCategoriaDAO.save(tipoCategoria);
    } 
    
    public TipoCategoria update(TipoCategoria tipoCategoria) throws Exception {
        init();
        validation(tipoCategoria);
        return tipoCategoriaDAO.update(tipoCategoria);
    } 
    
    public TipoCategoria delete(Integer idTipoCategoria) throws Exception {
        init();
        return tipoCategoriaDAO.remove(idTipoCategoria);
    }   
    
    private void validation(TipoCategoria tipoCategoria) throws Exception {  
        if(existeTipoCategoria(tipoCategoria)) {
            throw new Exception("Esta Categoria já foi cadastrado!");
        } 
    }  
    
    private boolean existeTipoCategoria(TipoCategoria tipoCategoria) throws Exception {
        TipoCategoria tipoCategoriaDescricao = tipoCategoriaDAO.findByDescricao(tipoCategoria.getDescricao());
        if(tipoCategoriaDescricao == null) return false;
        if(tipoCategoriaDescricao.getIdTipoCategoria().equals(tipoCategoria.getIdTipoCategoria())) return false;
        return true;
    }
    
}
