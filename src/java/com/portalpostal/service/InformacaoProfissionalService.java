package com.portalpostal.service;

import com.portalpostal.dao.InformacaoProfissionalDAO;
import com.portalpostal.model.InformacaoBancaria;
import com.portalpostal.model.InformacaoProfissional;
import java.util.List;

public class InformacaoProfissionalService {
    
    private final String nomeBD;   
    
    private InformacaoProfissionalDAO informacaoProfissionalDAO;  

    public InformacaoProfissionalService(String nomeBD) {
        this.nomeBD = nomeBD;
    }

    public void init() {
        informacaoProfissionalDAO = new InformacaoProfissionalDAO(nomeBD);
    }
    
    public List<InformacaoProfissional> findAll() throws Exception {
        init();
        return informacaoProfissionalDAO.findAll();
    }  
    
    public InformacaoProfissional find(Integer idInformacaoProfissional) throws Exception {
        init();
        return informacaoProfissionalDAO.find(idInformacaoProfissional);
    } 
    
    public InformacaoProfissional findContaCorrente(Integer idInformacaoProfissional) throws Exception {
        init();
        return find(idInformacaoProfissional);
    } 
    
    public InformacaoProfissional save(InformacaoProfissional informacaoProfissional) throws Exception {
        init();
        return informacaoProfissionalDAO.save(informacaoProfissional);
    } 
    
    public InformacaoProfissional update(InformacaoProfissional informacaoProfissional) throws Exception {
        init();
        return informacaoProfissionalDAO.update(informacaoProfissional);
    } 
    
    public InformacaoProfissional delete(Integer idInformacaoProfissional) throws Exception {
        init();
        if(!podeExcluir(idInformacaoProfissional)) throw new Exception("Esta informação não pode ser excluída!"); 
        return informacaoProfissionalDAO.remove(idInformacaoProfissional);
    }          
    
    public boolean podeExcluir(Integer idInformacaoProfissional) throws Exception {
        init();
        InformacaoProfissional informacaoProfissional = informacaoProfissionalDAO.find(idInformacaoProfissional);
        if(informacaoProfissional != null && informacaoProfissional.getColaborador() != null) return false;
        return true;                
    } 
    
}
