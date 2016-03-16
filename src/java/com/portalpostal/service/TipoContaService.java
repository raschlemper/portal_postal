package com.portalpostal.service;

import com.portalpostal.dao.TipoContaDAO;
import com.portalpostal.model.TipoConta;
import java.util.List;

public class TipoContaService {
    
    private final TipoContaDAO tipoContaDAO;

    public TipoContaService(String nomeBD) {
        tipoContaDAO = new TipoContaDAO(nomeBD);
    }
    
    public List<TipoConta> findAll() throws Exception {
        return tipoContaDAO.findAll();
    }  
    
    public TipoConta find(Integer idTipoConta) throws Exception {
        return tipoContaDAO.find(idTipoConta);
    } 
    
    public TipoConta save(TipoConta tipoConta) throws Exception {
        return tipoContaDAO.save(tipoConta);
    } 
    
    public TipoConta update(TipoConta tipoConta) throws Exception {
        return tipoContaDAO.update(tipoConta);
    } 
    
    public TipoConta delete(Integer idTipoConta) throws Exception {
        return tipoContaDAO.remove(idTipoConta);
    } 
    
}
