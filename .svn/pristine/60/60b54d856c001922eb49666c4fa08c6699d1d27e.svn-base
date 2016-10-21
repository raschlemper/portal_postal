package com.portalpostal.service;

import com.portalpostal.dao.TipoDocumentoDAO;
import com.portalpostal.model.TipoDocumento;
import java.util.List;

public class TipoDocumentoService {
    
    private final TipoDocumentoDAO tipoDocumentoDAO;

    public TipoDocumentoService(String nomeBD) {
        tipoDocumentoDAO = new TipoDocumentoDAO(nomeBD);
    }
    
    public List<TipoDocumento> findAll() throws Exception {
        return tipoDocumentoDAO.findAll();
    }  
    
    public TipoDocumento find(Integer idTipoDocumento) throws Exception {
        return tipoDocumentoDAO.find(idTipoDocumento);
    } 
    
    public TipoDocumento findByDescricao(String descricao) throws Exception {
        return tipoDocumentoDAO.findByDescricao(descricao);
    } 
    
    public TipoDocumento save(TipoDocumento tipoDocumento) throws Exception {
        validation(tipoDocumento);
        return tipoDocumentoDAO.save(tipoDocumento);
    } 
    
    public TipoDocumento update(TipoDocumento tipoDocumento) throws Exception {
        validation(tipoDocumento);
        return tipoDocumentoDAO.update(tipoDocumento);
    } 
    
    public TipoDocumento delete(Integer idTipoDocumento) throws Exception {
        return tipoDocumentoDAO.remove(idTipoDocumento);
    }   
    
    private void validation(TipoDocumento tipoDocumento) throws Exception {  
        if(existeTipoDocumento(tipoDocumento)) {
            throw new Exception("Este TipoDocumento já foi cadastrado!");
        } 
    }  
    
    private boolean existeTipoDocumento(TipoDocumento tipoDocumento) throws Exception {
        TipoDocumento tipoDocumentoDescricao = tipoDocumentoDAO.findByDescricao(tipoDocumento.getDescricao());
        if(tipoDocumentoDescricao == null) return false;
        if(tipoDocumentoDescricao.getIdTipoDocumento().equals(tipoDocumento.getIdTipoDocumento())) return false;
        return true;
    }
    
}
