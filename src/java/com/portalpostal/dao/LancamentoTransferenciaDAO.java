package com.portalpostal.dao;

import com.portalpostal.dao.handler.LancamentoTransferenciaHandler;
import com.portalpostal.model.LancamentoTransferencia;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LancamentoTransferenciaDAO extends GenericDAO { 
    
    private LancamentoTransferenciaHandler lancamentoTransferenciaHandler;

    public LancamentoTransferenciaDAO(String nameDB) { 
        super(nameDB, LancamentoTransferenciaDAO.class);
        lancamentoTransferenciaHandler = new LancamentoTransferenciaHandler();
    } 

    public List<LancamentoTransferencia> findAll() throws Exception {
        String sql = "SELECT * FROM lancamento_transferencia, conta contaOrigem, conta contaDestino "
                   + "WHERE lancamento_transferencia.idContaOrigem = contaOrigem.idConta "
                   + "AND lancamento_transferencia.idContaDestino = contaDestino.idConta "
                   + "ORDER BY lancamento_transferencia.idLancamentoTransferencia";        
        return findAll(sql, null, lancamentoTransferenciaHandler);
    }

    public LancamentoTransferencia find(Integer idLancamentoTransferencia) throws Exception {
        String sql = "SELECT * FROM lancamento_transferencia, conta contaOrigem, conta contaDestino "
                   + "WHERE lancamento_transferencia.idContaOrigem = contaOrigem.idConta "
                   + "AND lancamento_transferencia.idContaDestino = contaDestino.idConta "
                   + "AND lancamento_transferencia.idLancamentoTransferencia = :idLancamentoTransferencia";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoTransferencia", idLancamentoTransferencia);
        return (LancamentoTransferencia) find(sql, params, lancamentoTransferenciaHandler);
    }

    public LancamentoTransferencia save(LancamentoTransferencia lancamentoTransferencia) throws Exception {  
        String sql = "INSERT INTO lancamento_transferencia (idContaOrigem, idContaDestino, data, valor, historico) "
                   + "VALUES(:idContaOrigem, :idContaDestino, :tipo, :data, :valor, :historico)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idContaOrigem", lancamentoTransferencia.getContaOrigem().getIdConta());
        params.put("idContaDestino", lancamentoTransferencia.getContaDestino().getIdConta());
        params.put("data", lancamentoTransferencia.getData());      
        params.put("valor", lancamentoTransferencia.getValor());    
        params.put("historico", lancamentoTransferencia.getHistorico());       
        Integer idLancamentoTransferencia = save(sql, params, lancamentoTransferenciaHandler);
        return find(idLancamentoTransferencia);
    }

    public LancamentoTransferencia update(LancamentoTransferencia lancamentoTransferencia) throws Exception {
        String sql = "UPDATE lancamento_transferencia "
                   + "SET idContaOrigem = :idContaOrigem, idContaDestino = :idContaDestino, "
                   + "data = :data, valor = :valor, historico = :historico "
                   + "WHERE idLancamentoTransferencia = :idLancamentoTransferencia ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoTransferencia", lancamentoTransferencia.getIdLancamentoTransferencia());
        params.put("idContaOrigem", lancamentoTransferencia.getContaOrigem().getIdConta());
        params.put("idContaDestino", lancamentoTransferencia.getContaDestino().getIdConta());
        params.put("data", lancamentoTransferencia.getData());      
        params.put("valor", lancamentoTransferencia.getValor());    
        params.put("historico", lancamentoTransferencia.getHistorico());       
        update(sql, params, lancamentoTransferenciaHandler);
        return lancamentoTransferencia;  
    }

    public LancamentoTransferencia remove(Integer idLancamentoTransferencia) throws Exception { 
        String sql = "DELETE FROM lancamento_transferencia WHERE idLancamentoTransferencia = :idLancamentoTransferencia ";
        LancamentoTransferencia lancamentoTransferencia = find(idLancamentoTransferencia);
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idLancamentoTransferencia", idLancamentoTransferencia);
        remove(sql, params, lancamentoTransferenciaHandler);
        return lancamentoTransferencia;
    }
}
