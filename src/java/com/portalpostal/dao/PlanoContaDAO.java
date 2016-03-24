package com.portalpostal.dao;

import com.portalpostal.dao.handler.PlanoContaHandler;
import com.portalpostal.model.PlanoConta;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlanoContaDAO extends GenericDAO { 
    
    private PlanoContaHandler planoContaHandler;

    public PlanoContaDAO(String nameDB) { 
        super(nameDB, PlanoContaDAO.class);
        planoContaHandler = new PlanoContaHandler();
    } 

    public List<PlanoConta> findAll() throws Exception {
        String sql = "SELECT * FROM plano_conta ORDER BY plano_conta.codigo";        
        return findAll(sql, null, planoContaHandler);
    }

    public List<PlanoConta> findWithoutGrupo() throws Exception {
        String sql = "SELECT * FROM plano_conta WHERE grupo IS NULL ORDER BY plano_conta.codigo ";        
        return findAll(sql, null, planoContaHandler);
    }

    public List<PlanoConta> findWithoutGrupoByTipo(Integer tipo) throws Exception {
        String sql = "SELECT * FROM plano_conta "
                   + "WHERE grupo IS NULL AND plano_conta.tipo = :tipo "
                   + "ORDER BY plano_conta.codigo ";    
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tipo", tipo);        
        return findAll(sql, params, planoContaHandler);
    }

    public List<PlanoConta> findByGrupo(PlanoConta grupo) throws Exception {
        String sql = "SELECT * FROM plano_conta WHERE grupo = :grupo ORDER BY plano_conta.codigo";    
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("grupo", grupo.getIdPlanoConta());    
        return findAll(sql, params, planoContaHandler);
    }

    public PlanoConta find(Integer idPlanoConta) throws Exception {
        String sql = "SELECT * FROM plano_conta WHERE plano_conta.idPlanoConta = :idPlanoConta";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idPlanoConta", idPlanoConta);
        return (PlanoConta) find(sql, params, planoContaHandler);
    }

    public PlanoConta save(PlanoConta planoConta) throws Exception {  
        String sql = "INSERT INTO plano_conta (tipo, codigo, nome, grupo) "
                   + "VALUES(:tipo, :codigo, :nome, :grupo)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tipo", planoConta.getTipo().ordinal());
        params.put("codigo", planoConta.getCodigo());  
        params.put("nome", planoConta.getNome());  
        if(planoConta.getGrupo() == null) { params.put("grupo", null); }
        else { params.put("grupo", planoConta.getGrupo().getIdPlanoConta()); }
        Integer idPlanoConta = save(sql, params, planoContaHandler);
        return find(idPlanoConta);
    }

    public PlanoConta update(PlanoConta planoConta) throws Exception {
        String sql = "UPDATE plano_conta "
                   + "SET tipo = :tipo, codigo = :codigo, nome = :nome, grupo = :grupo "
                   + "WHERE idPlanoConta = :idPlanoConta ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idPlanoConta", planoConta.getIdPlanoConta());
        params.put("tipo", planoConta.getTipo().ordinal());
        params.put("codigo", planoConta.getCodigo());  
        params.put("nome", planoConta.getNome());  
        if(planoConta.getGrupo() == null) { params.put("grupo", null); }
        else { params.put("grupo", planoConta.getGrupo().getIdPlanoConta()); }
        update(sql, params, planoContaHandler);
        return planoConta;  
    }

    public PlanoConta remove(Integer idPlanoConta) throws Exception { 
        String sql = "DELETE FROM plano_conta WHERE idPlanoConta = :idPlanoConta ";
        PlanoConta planoConta = find(idPlanoConta);
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idPlanoConta", idPlanoConta);
        remove(sql, params, planoContaHandler);
        return planoConta;
    }
    
}
