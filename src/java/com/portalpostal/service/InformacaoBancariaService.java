package com.portalpostal.service;

import com.portalpostal.dao.InformacaoBancariaDAO;
import com.portalpostal.model.InformacaoBancaria;
import java.util.List;

public class InformacaoBancariaService {
    
    private final String nomeBD;   
    
    private InformacaoBancariaDAO informacaoBancariaDAO;  

    public InformacaoBancariaService(String nomeBD) {
        this.nomeBD = nomeBD;
    }

    public void init() {
        informacaoBancariaDAO = new InformacaoBancariaDAO(nomeBD);
    }
    
    public List<InformacaoBancaria> findAll() throws Exception {
        init();
        return informacaoBancariaDAO.findAll();
    }  
    
    public InformacaoBancaria find(Integer idInformacaoBancaria) throws Exception {
        init();
        return informacaoBancariaDAO.find(idInformacaoBancaria);
    } 
    
    public InformacaoBancaria findContaCorrente(Integer idInformacaoBancaria) throws Exception {
        init();
        return find(idInformacaoBancaria);
    } 
    
    public InformacaoBancaria save(InformacaoBancaria informacaoBancaria) throws Exception {
        init();
        return informacaoBancariaDAO.save(informacaoBancaria);
    } 
    
    public InformacaoBancaria update(InformacaoBancaria informacaoBancaria) throws Exception {
        init();
        return informacaoBancariaDAO.update(informacaoBancaria);
    } 
    
    public InformacaoBancaria delete(Integer idInformacaoBancaria) throws Exception {
        init();
        return informacaoBancariaDAO.remove(idInformacaoBancaria);
    }    
    
}
