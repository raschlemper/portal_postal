package com.portalpostal.service;

import com.portalpostal.dao.PlanoContaDAO;
import com.portalpostal.model.Banco;
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
    
    public List<PlanoConta> findStructure() throws Exception {
        List<PlanoConta> grupos = planoContaDAO.findWithoutGrupo(); 
        findContas(grupos);
        return grupos;
    }  

    public List<PlanoConta> findByTipo(Integer tipo) throws Exception {
        List<PlanoConta> grupos = planoContaDAO.findByTipo(tipo); 
        return grupos;
    }

    public List<PlanoConta> findContaResultadoByTipo(Integer tipo) throws Exception {
        List<PlanoConta> grupos = planoContaDAO.findContaResultadoByTipo(tipo); 
        return grupos;
    }

    public List<PlanoConta> findStructureByTipo(Integer tipo) throws Exception {
        List<PlanoConta> grupos = planoContaDAO.findWithoutGrupoByTipo(tipo); 
        findContas(grupos);
        return grupos;
    }

    public PlanoConta findByTipoGrupoCodigo(Integer tipo, Integer grupo, Integer codigo) throws Exception {
        return planoContaDAO.findByTipoGrupoCodigo(tipo, grupo, codigo); 
    }
    
    public PlanoConta find(Integer idPlanoConta) throws Exception {
        PlanoConta planoConta =  planoContaDAO.find(idPlanoConta);
        List<PlanoConta> grupos = planoContaDAO.findByGrupo(planoConta);        
        if(!grupos.isEmpty()) { planoConta.setContas(grupos); }
        return planoConta;
    } 
    
    public PlanoConta save(PlanoConta planoConta) throws Exception {
        validation(planoConta);
        return planoContaDAO.save(planoConta);
    } 
    
    public PlanoConta update(PlanoConta planoConta) throws Exception {
        validation(planoConta);
        return planoContaDAO.update(planoConta);
    } 
    
    public PlanoConta delete(Integer idPlanoConta) throws Exception {
        return planoContaDAO.remove(idPlanoConta);
    } 
    
    private void findContas(List<PlanoConta> contas) throws Exception {            
        for (PlanoConta conta : contas) {
            List<PlanoConta> grupos = planoContaDAO.findByGrupo(conta);
            if(grupos.isEmpty()) return;
            conta.setContas(grupos);
            findContas(grupos);
        }
    } 
    
    private void validation(PlanoConta planoConta) throws Exception {  
        if(existePlanoConta(planoConta)) {
            throw new Exception("Este Banco já foi cadastrado!");
        } 
    }  
    
    private boolean existePlanoConta(PlanoConta planoConta) throws Exception {
        PlanoConta planoContaExist = planoContaDAO.findByTipoGrupoCodigo(
                planoConta.getTipo().ordinal(), planoConta.getGrupo().getIdPlanoConta(), planoConta.getCodigo());
        if(planoContaExist == null) return false;
        if(planoContaExist.getIdPlanoConta().equals(planoConta.getIdPlanoConta())) return false;
        return true;
    }
    
}
