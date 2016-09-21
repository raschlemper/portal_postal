package com.portalpostal.dao;

import com.portalpostal.dao.handler.PlanoContaHandler;
import com.portalpostal.model.PlanoConta;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlanoContaDAO extends GenericDAO { 
    
    private final PlanoContaHandler planoContaHandler;

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

    public List<PlanoConta> findByTipo(Integer tipo) throws Exception {
        String sql = "SELECT * FROM plano_conta "
                   + "WHERE plano_conta.tipo = :tipo "
                   + "ORDER BY plano_conta.codigo ";    
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tipo", tipo);        
        return findAll(sql, params, planoContaHandler);
    }

    public List<PlanoConta> findContaResultadoByTipo(Integer tipo) throws Exception {
        String sql = "SELECT * FROM plano_conta "
                   + "WHERE plano_conta.tipo = :tipo "
                   + " AND NOT EXISTS (SELECT 1 FROM plano_conta plano1 WHERE plano_conta.idPlanoConta = plano1.grupo)"
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

    public PlanoConta findByTipoGrupoCodigo(Integer tipo, Integer idGrupo, Integer codigo) throws Exception {
        String sql = "SELECT * FROM plano_conta "
                   + "WHERE tipo = :tipo AND grupo = :idGrupo AND codigo = :codigo";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tipo", tipo);    
        params.put("idGrupo", idGrupo);    
        params.put("codigo", codigo);    
        return (PlanoConta) find(sql, params, planoContaHandler);
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

    public void removeAll() throws Exception { 
        String sql = "TRUNCATE TABLE plano_conta ";
        execute(sql, null);
    }

    public void insertFromDefault() throws Exception {
        String sql = "INSERT INTO plano_conta (idPlanoConta, tipo, codigo, nome, grupo) " +
                     "SELECT idPlanoConta, tipo, codigo, nome, grupo FROM plano_conta_padrao "; 
        execute(sql, null);
    }
    
}
