package com.portalpostal.service;

import com.portalpostal.dao.PlanoContaDAO;
import com.portalpostal.model.Lancamento;
import com.portalpostal.model.LancamentoProgramado;
import com.portalpostal.model.PlanoConta;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlanoContaService {
    
    private final String nomeBD;
    
    private PlanoContaDAO planoContaDAO;
    private LancamentoService lancamentoService;
    private LancamentoProgramadoService lancamentoProgramadoService;
    private LancamentoProgramadoParcelaService lancamentoProgramadoParcelaService;
    private LancamentoProgramadoRateioService lancamentoProgramadoRateioService;

    public PlanoContaService(String nomeBD) {
        this.nomeBD = nomeBD;
    }

    public void init() {
        planoContaDAO = new PlanoContaDAO(nomeBD);
        lancamentoService = new LancamentoService(nomeBD);
        lancamentoProgramadoService = new LancamentoProgramadoService(nomeBD);
        lancamentoProgramadoParcelaService = new LancamentoProgramadoParcelaService(nomeBD);
        lancamentoProgramadoRateioService = new LancamentoProgramadoRateioService(nomeBD);
    }
    
    public List<PlanoConta> findAll() throws Exception {
        init();
        return planoContaDAO.findAll();
    }  
    
    public List<PlanoConta> findStructure() throws Exception {
        init();
        List<PlanoConta> grupos = planoContaDAO.findWithoutGrupo(); 
        List<PlanoConta> planoContas = planoContaDAO.findAll(); 
        findContas(planoContas, grupos, null, null);
        return grupos;
    }  

    public PlanoConta findLancamento(Integer idPlanoConta) throws Exception {
        init();
        PlanoConta planoConta = find(idPlanoConta);
        planoConta.setLancamentos(lancamentoService.findByPlanoConta(idPlanoConta));
        return planoConta;
    } 

    public PlanoConta findLancamentoProgramado(Integer idPlanoConta) throws Exception {
        init();
        PlanoConta planoConta = find(idPlanoConta);
        planoConta.setLancamentosProgramados(lancamentoProgramadoService.findByPlanoConta(idPlanoConta));
        return planoConta;
    }

    public List<PlanoConta> findByTipo(Integer tipo) throws Exception {
        init();
        List<PlanoConta> grupos = planoContaDAO.findByTipo(tipo); 
        return grupos;
    }

    public List<PlanoConta> findContaResultadoByTipo(Integer tipo) throws Exception {
        init();
        List<PlanoConta> grupos = planoContaDAO.findContaResultadoByTipo(tipo); 
        return grupos;
    }

    public List<PlanoConta> findStructureByTipo(Integer tipo) throws Exception {
        init();
        List<PlanoConta> grupos = planoContaDAO.findWithoutGrupoByTipo(tipo); 
        List<PlanoConta> planoContas = planoContaDAO.findByTipo(tipo); 
        findContas(planoContas, grupos, null, null);
        return grupos;
    } 

    public PlanoConta findByTipoGrupoCodigo(Integer tipo, Integer grupo, Integer codigo) throws Exception {
        init();
        return planoContaDAO.findByTipoGrupoCodigo(tipo, grupo, codigo); 
    }
    
    public PlanoConta find(Integer idPlanoConta) throws Exception {
        init();
        PlanoConta planoConta =  planoContaDAO.find(idPlanoConta);
        List<PlanoConta> grupos = planoContaDAO.findByGrupo(planoConta);        
        if(!grupos.isEmpty()) { planoConta.setContas(grupos); }
        return planoConta;
    } 
    
    public PlanoConta save(PlanoConta planoConta) throws Exception {
        init();
        validation(planoConta);
        return planoContaDAO.save(planoConta);
    } 
    
    public PlanoConta update(PlanoConta planoConta) throws Exception {
        init();
        validation(planoConta);
        return planoContaDAO.update(planoConta);
    } 
    
    public PlanoConta delete(Integer idPlanoConta) throws Exception {
        init();
        if(!podeExcluir(idPlanoConta)) throw new Exception("Este plano de conta não pode ser excluído!"); 
        return planoContaDAO.remove(idPlanoConta);
    }   
    
    public void setDefault() throws Exception {
        init();
        lancamentoService.deleteAllPlanoConta();
        lancamentoProgramadoService.deleteAllPlanoConta();
        lancamentoProgramadoParcelaService.deleteAllPlanoConta();
        lancamentoProgramadoRateioService.deleteAllPlanoConta();
        planoContaDAO.removeAll();
        planoContaDAO.insertFromDefault();
    }     
    
    private boolean podeExcluir(Integer idPlanoConta) throws Exception {
        List<Lancamento> lancamentos = lancamentoService.findByPlanoConta(idPlanoConta);
        if(!lancamentos.isEmpty()) return false;
        List<LancamentoProgramado> lancamentoProgramados = lancamentoProgramadoService.findByPlanoConta(idPlanoConta);
        if(!lancamentoProgramados.isEmpty()) return false;
        return true;                
    }  
    
    private void findContas(List<PlanoConta> planoContas, List<PlanoConta> contas, Map<Integer, Integer> estrutura, Integer nivel) throws Exception {
        nivel = getNivel(nivel);
        for (PlanoConta conta : contas) {
            conta.setNivel(nivel);
            estrutura = getEstrutura(conta, estrutura, nivel);
            List<PlanoConta> grupos = findByGrupo(planoContas, conta);
            if(grupos.isEmpty()) { continue; }
            conta.setContas(grupos);            
            findContas(planoContas, grupos, estrutura, nivel);
        }
    } 
    
    private List<PlanoConta> findByGrupo(List<PlanoConta> planoContas, PlanoConta grupo) {
        List<PlanoConta> planos = new ArrayList<PlanoConta>();
        for (PlanoConta planoConta : planoContas) {
            if(planoConta.getGrupo() != null && planoConta.getGrupo().getIdPlanoConta().equals(grupo.getIdPlanoConta())) {
                planos.add(planoConta);
            }
        }
        return planos;
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
