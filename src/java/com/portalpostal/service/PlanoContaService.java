package com.portalpostal.service;

import com.portalpostal.dao.PlanoContaDAO;
import com.portalpostal.model.PlanoConta;
import java.util.List;

public class PlanoContaService {
    
    private final PlanoContaDAO planoContaDAO;

    public PlanoContaService(String nomeBD) {
        planoContaDAO = new PlanoContaDAO(nomeBD);
    }
    
    public List<PlanoConta> findAll() throws Exception {
        return planoContaDAO.findAll();
    }  
    
    public PlanoConta find(Integer idPlanoConta) throws Exception {
        return planoContaDAO.find(idPlanoConta);
    } 
    
    public PlanoConta save(PlanoConta planoConta) throws Exception {
        return planoContaDAO.save(planoConta);
    } 
    
    public PlanoConta update(PlanoConta planoConta) throws Exception {
        return planoContaDAO.update(planoConta);
    } 
    
    public PlanoConta delete(Integer idPlanoConta) throws Exception {
        return planoContaDAO.remove(idPlanoConta);
    } 
    
}
