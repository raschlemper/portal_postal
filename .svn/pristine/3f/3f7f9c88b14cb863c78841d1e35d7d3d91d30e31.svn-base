package com.portalpostal.dao;

import com.portalpostal.dao.handler.LancamentoAnexoHandler;
import com.portalpostal.model.LancamentoAnexo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LancamentoAnexoDAO extends GenericDAO { 
    
    private final LancamentoAnexoHandler lancamentoAnexoHandler;

    public LancamentoAnexoDAO(String nameDB) { 
        super(nameDB, LancamentoAnexoDAO.class);
        lancamentoAnexoHandler = new LancamentoAnexoHandler();
    } 

    public List<LancamentoAnexo> findAll() throws Exception {
        String sql = "SELECT * FROM lancamento_anexo ORDER BY lancamento_anexo.idLancamentoAnexo";        
        return findAll(sql, null, lancamentoAnexoHandler);
    }

    public LancamentoAnexo find(Integer idLancamentoAnexo) throws Exception {
        String sql = "SELECT * FROM lancamento_anexo WHERE lancamento_anexo.idLancamentoAnexo = :idLancamentoAnexo";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoAnexo", idLancamentoAnexo);
        return (LancamentoAnexo) find(sql, params, lancamentoAnexoHandler);
    }

    public LancamentoAnexo save(LancamentoAnexo lancamentoAnexo) throws Exception {  
        String sql = "INSERT INTO lancamento_anexo (idLancamento, nome, anexo) "
                   + "VALUES(:idLancamento, :nome, :anexo)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamento", lancamentoAnexo.getLancamento().getIdLancamento());
        params.put("nome", lancamentoAnexo.getNome());
        params.put("anexo", lancamentoAnexo.getAnexo());      
        Integer idLancamentoAnexo = save(sql, params, lancamentoAnexoHandler);
        return find(idLancamentoAnexo);
    }

    public LancamentoAnexo update(LancamentoAnexo lancamentoAnexo) throws Exception {
        String sql = "UPDATE lancamento_anexo "
                   + "SET idLancamento = :idLancamento, nome = :nome, anexo = :anexo "
                   + "WHERE idLancamentoAnexo = :idLancamentoAnexo ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoAnexo", lancamentoAnexo.getIdLancamentoAnexo());
        params.put("idLancamento", lancamentoAnexo.getLancamento().getIdLancamento());
        params.put("nome", lancamentoAnexo.getNome());
        params.put("anexo", lancamentoAnexo.getAnexo());      
        update(sql, params, lancamentoAnexoHandler);
        return lancamentoAnexo;  
    }

    public LancamentoAnexo remove(Integer idLancamentoAnexo) throws Exception { 
        String sql = "DELETE FROM lancamento_anexo WHERE idLancamentoAnexo = :idLancamentoAnexo ";
        LancamentoAnexo lancamentoAnexo = find(idLancamentoAnexo);
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idLancamentoAnexo", idLancamentoAnexo);
        remove(sql, params, lancamentoAnexoHandler);
        return lancamentoAnexo;
    }
    
}
