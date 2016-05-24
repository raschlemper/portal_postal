package com.portalpostal.service;

import com.portalpostal.dao.LancamentoDAO;
import com.portalpostal.dao.LancamentoProgramadoDAO;
import com.portalpostal.dao.PlanoContaDAO;
import com.portalpostal.model.Lancamento;
import com.portalpostal.model.LancamentoProgramado;
import com.portalpostal.model.PlanoConta;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PlanoContaService {
    
    private final PlanoContaDAO planoContaDAO;
    private final LancamentoDAO lancamentoDAO;
    private final LancamentoProgramadoDAO lancamentoProgramadoDAO;

    public PlanoContaService(String nomeBD) {
        planoContaDAO = new PlanoContaDAO(nomeBD);
        lancamentoDAO = new LancamentoDAO(nomeBD);
        lancamentoProgramadoDAO = new LancamentoProgramadoDAO(nomeBD);
    }
    
    public List<PlanoConta> findAll() throws Exception {
        return planoContaDAO.findAll();
    }  
    
    public List<PlanoConta> findStructure() throws Exception {
        List<PlanoConta> grupos = planoContaDAO.findWithoutGrupo(); 
        findContas(grupos, null, null);
        return grupos;
    }  

    public PlanoConta findLancamento(Integer idPlanoConta) throws Exception {
        PlanoConta planoConta = find(idPlanoConta);
        planoConta.setLancamentos(lancamentoDAO.findByPlanoConta(idPlanoConta));
        return planoConta;
    } 

    public PlanoConta findLancamentoProgramado(Integer idPlanoConta) throws Exception {
        PlanoConta planoConta = find(idPlanoConta);
        planoConta.setLancamentosProgramados(lancamentoProgramadoDAO.findByPlanoConta(idPlanoConta));
        return planoConta;
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
        findContas(grupos, null, null);
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
        if(!podeExcluir(idPlanoConta)) throw new Exception("Este plano de conta não pode ser excluído!"); 
        return planoContaDAO.remove(idPlanoConta);
    }     
    
    public boolean podeExcluir(Integer idPlanoConta) throws Exception {
        List<Lancamento> lancamentos = lancamentoDAO.findByPlanoConta(idPlanoConta);
        if(!lancamentos.isEmpty()) return false;
        List<LancamentoProgramado> lancamentoProgramados = lancamentoProgramadoDAO.findByPlanoConta(idPlanoConta);
        if(!lancamentoProgramados.isEmpty()) return false;
        return true;                
    }  
    
    private void findContas(List<PlanoConta> contas, Map<Integer, Integer> estrutura, Integer nivel) throws Exception { 
        nivel = getNivel(nivel);
        for (PlanoConta conta : contas) {
            conta.setNivel(nivel);
            estrutura = getEstrutura(conta, estrutura, nivel);
            List<PlanoConta> grupos = planoContaDAO.findByGrupo(conta);
            if(grupos.isEmpty()) { continue; }
            conta.setContas(grupos);            
            findContas(grupos, estrutura, nivel);
        }
    } 
    
    private Integer getNivel(Integer nivel) {    
        if(nivel == null) { nivel = 0; }
        nivel++;    
        return nivel;
    }
    
    private Map<Integer, Integer> getEstrutura(PlanoConta conta, Map<Integer, Integer> estrutura, Integer nivel) {
            if(estrutura == null) { estrutura = new HashMap<Integer,Integer>(); }
        estrutura.put(nivel, conta.getCodigo());
        Map<Integer, Integer> estruturaClone = new HashMap<Integer,Integer>();
        for (Map.Entry<Integer, Integer> entrySet : estrutura.entrySet()) {
            Integer key = entrySet.getKey();
            Integer value = entrySet.getValue();
            if(key <= nivel) { estruturaClone.put(key, value); }            
        }
        conta.setEstrutura(estruturaClone);
        return estrutura;
    }
    
    private void validation(PlanoConta planoConta) throws Exception {  
        if(existePlanoConta(planoConta)) {
            throw new Exception("Este plano de conta já foi cadastrado!");
        } 
    }  
    
    private boolean existePlanoConta(PlanoConta planoConta) throws Exception {
        PlanoConta planoContaExist = planoContaDAO.findByTipoGrupoCodigo(
                planoConta.getTipo().ordinal(), planoConta.getGrupo().getIdPlanoConta(), planoConta.getCodigo());
        if(planoContaExist == null) return false;
        if(planoContaExist.getIdPlanoConta().equals(planoConta.getIdPlanoConta())) return false;
        return true;
    }  
    
    private void getEstrutura(List<PlanoConta> planoContas) {
        for(PlanoConta planoConta : planoContas) {            
            planoConta.setDescricao(getCode(planoConta.getEstrutura()) + " - " + planoConta.getNome()); 
            if(!planoConta.getContas().isEmpty()) { getEstrutura(planoConta.getContas()); }
        }
    }
            
    private String getCode(Map<Integer, Integer> estruturas) {
        String code = "";
        for (Map.Entry<Integer, Integer> entrySet : estruturas.entrySet()) {
            Integer value = entrySet.getValue();
            if(code != null) { code = code + "." + value; }
            else { code = value.toString(); }
        }
        return code;
    }
    
}
