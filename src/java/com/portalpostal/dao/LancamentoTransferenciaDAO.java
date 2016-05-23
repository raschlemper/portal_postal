package com.portalpostal.dao;

import com.portalpostal.dao.handler.LancamentoTransferenciaHandler;
import com.portalpostal.model.LancamentoTransferencia;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LancamentoTransferenciaDAO extends GenericDAO { 
    
    private final LancamentoTransferenciaHandler lancamentoTransferenciaHandler;

    public LancamentoTransferenciaDAO(String nameDB) { 
        super(nameDB, LancamentoTransferenciaDAO.class);
        lancamentoTransferenciaHandler = new LancamentoTransferenciaHandler();
    } 

    public List<LancamentoTransferencia> findAll() throws Exception {
        String sql = "SELECT * FROM lancamento_transferencia, lancamento lancamentoOrigem, lancamento lancamentoDestino "
                   + "WHERE lancamento_transferencia.idLancamentoOrigem = lancamentoOrigem.idLancamento "
                   + "AND lancamento_transferencia.idLancamentoDestino = lancamentoDestino.idLancamento "
                   + "ORDER BY lancamento_transferencia.idLancamentoTransferencia";        
        return findAll(sql, null, lancamentoTransferenciaHandler);
    }

    public LancamentoTransferencia find(Integer idLancamentoTransferencia) throws Exception {
        String sql = "SELECT * FROM lancamento_transferencia, lancamento lancamentoOrigem, lancamento lancamentoDestino "
                   + "WHERE lancamento_transferencia.idLancamentoOrigem = lancamentoOrigem.idLancamento "
                   + "AND lancamento_transferencia.idLancamentoDestino = lancamentoDestino.idLancamento "
                   + "AND lancamento_transferencia.idLancamentoTransferencia = :idLancamentoTransferencia";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoTransferencia", idLancamentoTransferencia);
        return (LancamentoTransferencia) find(sql, params, lancamentoTransferenciaHandler);
    }

    public LancamentoTransferencia save(LancamentoTransferencia lancamentoTransferencia) throws Exception {  
        String sql = "INSERT INTO lancamento_transferencia (idLancamentoOrigem, idLancamentoDestino, numero, "
                   + "dataCompetencia, dataEmissao, dataLancamento, valor, historico) "
                   + "VALUES(:idLancamentoOrigem, :idLancamentoDestino, :numero, :dataCompetencia, :dataEmissao, "
                   + ":dataLancamento, :valor, :historico)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoOrigem", lancamentoTransferencia.getLancamentoOrigem().getIdLancamento());
        params.put("idLancamentoDestino", lancamentoTransferencia.getLancamentoDestino().getIdLancamento()); 
        params.put("numero", lancamentoTransferencia.getNumero());                 
        params.put("dataCompetencia", lancamentoTransferencia.getDataCompetencia());       
        params.put("dataEmissao", lancamentoTransferencia.getDataEmissao());       
        params.put("dataLancamento", lancamentoTransferencia.getDataLancamento());      
        params.put("valor", lancamentoTransferencia.getValor());   
        params.put("historico", lancamentoTransferencia.getHistorico());         
        Integer idLancamentoTransferencia = save(sql, params, lancamentoTransferenciaHandler);
        return find(idLancamentoTransferencia);
    }

    public LancamentoTransferencia update(LancamentoTransferencia lancamentoTransferencia) throws Exception {
        String sql = "UPDATE lancamento_transferencia "
                   + "SET idLancamentoOrigem = :idLancamentoOrigem, idLancamentoDestino = :idLancamentoDestino "
                   + "numero = :numero, dataCompetencia = :dataCompetencia, dataEmissao = :dataEmissao, "
                   + "dataLancamento = :dataLancamento, valor = :valor, historico = :historico "
                   + "WHERE idLancamentoTransferencia = :idLancamentoTransferencia ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoTransferencia", lancamentoTransferencia.getIdLancamentoTransferencia());
        params.put("idLancamentoOrigem", lancamentoTransferencia.getLancamentoOrigem().getIdLancamento());
        params.put("idLancamentoDestino", lancamentoTransferencia.getLancamentoDestino().getIdLancamento());
        params.put("numero", lancamentoTransferencia.getNumero());              
        params.put("dataCompetencia", lancamentoTransferencia.getDataCompetencia());            
        params.put("dataEmissao", lancamentoTransferencia.getDataEmissao());      
        params.put("dataLancamento", lancamentoTransferencia.getDataLancamento());           
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
