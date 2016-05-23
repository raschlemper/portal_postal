package com.portalpostal.service;

import com.portalpostal.dao.LancamentoDAO;
import com.portalpostal.dao.LancamentoProgramadoDAO;
import com.portalpostal.dao.CentroCustoDAO;
import com.portalpostal.model.Lancamento;
import com.portalpostal.model.LancamentoProgramado;
import com.portalpostal.model.CentroCusto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CentroCustoService {
    
    private final CentroCustoDAO centroCustoDAO;
    private final LancamentoDAO lancamentoDAO;
    private final LancamentoProgramadoDAO lancamentoProgramadoDAO;

    public CentroCustoService(String nomeBD) {
        centroCustoDAO = new CentroCustoDAO(nomeBD);
        lancamentoDAO = new LancamentoDAO(nomeBD);
        lancamentoProgramadoDAO = new LancamentoProgramadoDAO(nomeBD);
    }
    
    public List<CentroCusto> findAll() throws Exception {
        return centroCustoDAO.findAll();
    }  
    
    public List<CentroCusto> findStructure() throws Exception {
        List<CentroCusto> grupos = centroCustoDAO.findWithoutGrupo(); 
        findContas(grupos, null, null);
        return grupos;
    }  

    public CentroCusto findLancamento(Integer idCentroCusto) throws Exception {
        CentroCusto centroCusto = find(idCentroCusto);
        centroCusto.setLancamentos(lancamentoDAO.findByCentroCusto(idCentroCusto));
        return centroCusto;
    } 

    public CentroCusto findLancamentoProgramado(Integer idCentroCusto) throws Exception {
        CentroCusto centroCusto = find(idCentroCusto);
        centroCusto.setLancamentosProgramados(lancamentoProgramadoDAO.findByCentroCusto(idCentroCusto));
        return centroCusto;
    }

//    public List<CentroCusto> findByTipo(Integer tipo) throws Exception {
//        List<CentroCusto> grupos = centroCustoDAO.findByTipo(tipo); 
//        return grupos;
//    }

//    public List<CentroCusto> findContaResultadoByTipo(Integer tipo) throws Exception {
//        List<CentroCusto> grupos = centroCustoDAO.findContaResultadoByTipo(tipo); 
//        return grupos;
//    }

//    public List<CentroCusto> findStructureByTipo(Integer tipo) throws Exception {
//        List<CentroCusto> grupos = centroCustoDAO.findWithoutGrupoByTipo(tipo); 
//        findContas(grupos, null, null);
//        return grupos;
//    } 

    public CentroCusto findByGrupoCodigo(Integer grupo, Integer codigo) throws Exception {
        return centroCustoDAO.findByGrupoCodigo(grupo, codigo); 
    }
    
    public CentroCusto find(Integer idCentroCusto) throws Exception {
        CentroCusto centroCusto =  centroCustoDAO.find(idCentroCusto);
        List<CentroCusto> grupos = centroCustoDAO.findByGrupo(centroCusto);        
        if(!grupos.isEmpty()) { centroCusto.setCentros(grupos); }
        return centroCusto;
    } 
    
    public CentroCusto save(CentroCusto centroCusto) throws Exception {
        validation(centroCusto);
        return centroCustoDAO.save(centroCusto);
    } 
    
    public CentroCusto update(CentroCusto centroCusto) throws Exception {
        validation(centroCusto);
        return centroCustoDAO.update(centroCusto);
    } 
    
    public CentroCusto delete(Integer idCentroCusto) throws Exception {
        if(!podeExcluir(idCentroCusto)) throw new Exception("Este centro de custo não pode ser excluído!"); 
        return centroCustoDAO.remove(idCentroCusto);
    }     
    
    public boolean podeExcluir(Integer idCentroCusto) throws Exception {
        List<Lancamento> lancamentos = lancamentoDAO.findByCentroCusto(idCentroCusto);
        if(!lancamentos.isEmpty()) return false;
        List<LancamentoProgramado> lancamentoProgramados = lancamentoProgramadoDAO.findByCentroCusto(idCentroCusto);
        if(!lancamentoProgramados.isEmpty()) return false;
        return true;                
    }  
    
    private void findContas(List<CentroCusto> contas, Map<Integer, Integer> estrutura, Integer nivel) throws Exception { 
        nivel = getNivel(nivel);
        for (CentroCusto conta : contas) {
            conta.setNivel(nivel);
            estrutura = getEstrutura(conta, estrutura, nivel);
            List<CentroCusto> grupos = centroCustoDAO.findByGrupo(conta);
            if(grupos.isEmpty()) { continue; }
            conta.setCentros(grupos);            
            findContas(grupos, estrutura, nivel);
        }
    } 
    
    private Integer getNivel(Integer nivel) {    
        if(nivel == null) { nivel = 0; }
        nivel++;    
        return nivel;
    }
    
    private Map<Integer, Integer> getEstrutura(CentroCusto conta, Map<Integer, Integer> estrutura, Integer nivel) {
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
    
    private void validation(CentroCusto centroCusto) throws Exception {  
        if(existeCentroCusto(centroCusto)) {
            throw new Exception("Este Banco já foi cadastrado!");
        } 
    }  
    
    private boolean existeCentroCusto(CentroCusto centroCusto) throws Exception {
        CentroCusto centroCustoExist = centroCustoDAO.findByGrupoCodigo(
                centroCusto.getGrupo().getIdCentroCusto(), centroCusto.getCodigo());
        if(centroCustoExist == null) return false;
        if(centroCustoExist.getIdCentroCusto().equals(centroCusto.getIdCentroCusto())) return false;
        return true;
    }  
    
    private void getEstrutura(List<CentroCusto> centroCustos) {
        for(CentroCusto centroCusto : centroCustos) {            
            centroCusto.setDescricao(getCode(centroCusto.getEstrutura()) + " - " + centroCusto.getNome()); 
            if(!centroCusto.getCentros().isEmpty()) { getEstrutura(centroCusto.getCentros()); }
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
