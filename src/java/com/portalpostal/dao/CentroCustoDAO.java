package com.portalpostal.dao;

import com.portalpostal.dao.handler.CentroCustoHandler;
import com.portalpostal.model.CentroCusto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CentroCustoDAO extends GenericDAO { 
    
    private final CentroCustoHandler centroCustoHandler;

    public CentroCustoDAO(String nameDB) { 
        super(nameDB, CentroCustoDAO.class);
        centroCustoHandler = new CentroCustoHandler();
    } 

    public List<CentroCusto> findAll() throws Exception {
        String sql = "SELECT * FROM centro_custo ORDER BY centro_custo.codigo";        
        return findAll(sql, null, centroCustoHandler);
    }

    public List<CentroCusto> findWithoutGrupo() throws Exception {
        String sql = "SELECT * FROM centro_custo WHERE grupo IS NULL ORDER BY centro_custo.codigo ";        
        return findAll(sql, null, centroCustoHandler);
    }

//    public List<CentroCusto> findWithoutGrupoByTipo(Integer tipo) throws Exception {
//        String sql = "SELECT * FROM centro_custo "
//                   + "WHERE grupo IS NULL AND centro_custo.tipo = :tipo "
//                   + "ORDER BY centro_custo.codigo ";    
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("tipo", tipo);        
//        return findAll(sql, params, centroCustoHandler);
//    }

//    public List<CentroCusto> findByTipo(Integer tipo) throws Exception {
//        String sql = "SELECT * FROM centro_custo "
//                   + "WHERE centro_custo.tipo = :tipo "
//                   + "ORDER BY centro_custo.codigo ";    
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("tipo", tipo);        
//        return findAll(sql, params, centroCustoHandler);
//    }

//    public List<CentroCusto> findContaResultadoByTipo(Integer tipo) throws Exception {
//        String sql = "SELECT * FROM centro_custo "
//                   + "WHERE centro_custo.tipo = :tipo "
//                   + " AND NOT EXISTS (SELECT 1 FROM centro_custo plano1 WHERE centro_custo.idCentroCusto = plano1.grupo)"
//                   + "ORDER BY centro_custo.codigo ";    
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("tipo", tipo);        
//        return findAll(sql, params, centroCustoHandler);
//    }

    public List<CentroCusto> findByGrupo(CentroCusto grupo) throws Exception {
        String sql = "SELECT * FROM centro_custo WHERE grupo = :grupo ORDER BY centro_custo.codigo";    
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("grupo", grupo.getIdCentroCusto());    
        return findAll(sql, params, centroCustoHandler);
    }

    public CentroCusto findByGrupoCodigo(Integer idGrupo, Integer codigo) throws Exception {
        String sql = "SELECT * FROM centro_custo "
                   + "WHERE grupo = :idGrupo AND codigo = :codigo";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idGrupo", idGrupo);    
        params.put("codigo", codigo);    
        return (CentroCusto) find(sql, params, centroCustoHandler);
    }

    public CentroCusto find(Integer idCentroCusto) throws Exception {
        String sql = "SELECT * FROM centro_custo WHERE centro_custo.idCentroCusto = :idCentroCusto";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idCentroCusto", idCentroCusto);
        return (CentroCusto) find(sql, params, centroCustoHandler);
    }

    public CentroCusto save(CentroCusto centroCusto) throws Exception {  
        String sql = "INSERT INTO centro_custo (codigo, nome, grupo) "
                   + "VALUES(:codigo, :nome, :grupo)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("codigo", centroCusto.getCodigo());  
        params.put("nome", centroCusto.getNome());  
        if(centroCusto.getGrupo() == null) { params.put("grupo", null); }
        else { params.put("grupo", centroCusto.getGrupo().getIdCentroCusto()); }
        Integer idCentroCusto = save(sql, params, centroCustoHandler);
        return find(idCentroCusto);
    }

    public CentroCusto update(CentroCusto centroCusto) throws Exception {
        String sql = "UPDATE centro_custo "
                   + "SET codigo = :codigo, nome = :nome, grupo = :grupo "
                   + "WHERE idCentroCusto = :idCentroCusto ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idCentroCusto", centroCusto.getIdCentroCusto());
        params.put("codigo", centroCusto.getCodigo());  
        params.put("nome", centroCusto.getNome());  
        if(centroCusto.getGrupo() == null) { params.put("grupo", null); }
        else { params.put("grupo", centroCusto.getGrupo().getIdCentroCusto()); }
        update(sql, params, centroCustoHandler);
        return centroCusto;  
    }

    public CentroCusto remove(Integer idCentroCusto) throws Exception { 
        String sql = "DELETE FROM centro_custo WHERE idCentroCusto = :idCentroCusto ";
        CentroCusto centroCusto = find(idCentroCusto);
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idCentroCusto", idCentroCusto);
        remove(sql, params, centroCustoHandler);
        return centroCusto;
    }
    
}
