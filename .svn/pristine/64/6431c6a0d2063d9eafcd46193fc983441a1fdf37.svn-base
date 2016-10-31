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
        this.lancamentoAnexoHandler = new LancamentoAnexoHandler();
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

    public List<LancamentoAnexo> findByLancamento(Integer idLancamento) throws Exception {
        String sql = "SELECT * FROM lancamento_anexo WHERE lancamento_anexo.idLancamento = :idLancamento";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamento", idLancamento);
        return findAll(sql, params, lancamentoAnexoHandler);
    }

    public LancamentoAnexo save(LancamentoAnexo lancamentoAnexo) throws Exception {  
        String sql = "INSERT INTO lancamento_anexo (idLancamento, nome, tipo, anexo, usuario) "
                   + "VALUES(:idLancamento, :nome, :tipo, :anexo, :usuario)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamento", lancamentoAnexo.getLancamento().getIdLancamento());
        params.put("nome", lancamentoAnexo.getNome());
        params.put("tipo", lancamentoAnexo.getTipo());
        params.put("anexo", lancamentoAnexo.getAnexo());               
        params.put("usuario", lancamentoAnexo.getUsuario()); 
        Integer idLancamentoAnexo = save(sql, params, lancamentoAnexoHandler);
        return find(idLancamentoAnexo);
    }

    public LancamentoAnexo update(LancamentoAnexo lancamentoAnexo) throws Exception {
        String sql = "UPDATE lancamento_anexo "
                   + "SET idLancamento = :idLancamento, nome = :nome, tipo= :tipo, anexo = :anexo, usuario = :usuario "
                   + "WHERE idLancamentoAnexo = :idLancamentoAnexo ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoAnexo", lancamentoAnexo.getIdLancamentoAnexo());
        params.put("idLancamento", lancamentoAnexo.getLancamento().getIdLancamento());
        params.put("nome", lancamentoAnexo.getNome());
        params.put("tipo", lancamentoAnexo.getTipo());
        params.put("anexo", lancamentoAnexo.getAnexo());           
        params.put("usuario", lancamentoAnexo.getUsuario()); 
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

    public LancamentoAnexo removeByLancamento(Integer idLancamento) throws Exception { 
        String sql = "DELETE FROM lancamento_anexo WHERE idLancamento = :idLancamento ";
        LancamentoAnexo lancamentoAnexo = find(idLancamento);
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idLancamento", idLancamento);
        remove(sql, params, lancamentoAnexoHandler);
        return lancamentoAnexo;
    }
    
}
