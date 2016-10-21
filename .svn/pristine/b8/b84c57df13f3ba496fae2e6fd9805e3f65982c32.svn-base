package com.portalpostal.dao;

import com.portalpostal.dao.handler.LancamentoTransferenciaProgramadoHandler;
import com.portalpostal.model.LancamentoTransferenciaProgramado;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LancamentoTransferenciaProgramadoDAO extends GenericDAO { 
    
    private final LancamentoTransferenciaProgramadoHandler lancamentoTransferenciaProgramadoHandler;

    public LancamentoTransferenciaProgramadoDAO(String nameDB) { 
        super(nameDB, LancamentoTransferenciaProgramadoDAO.class);
        lancamentoTransferenciaProgramadoHandler = new LancamentoTransferenciaProgramadoHandler();
    } 

    public List<LancamentoTransferenciaProgramado> findAll() throws Exception {
        String sql = "SELECT * FROM lancamento_programado_transferencia, "
                   + "lancamentoProgramado lancamentoProgramadoOrigem, lancamentoProgramado lancamentoProgramadoDestino "
                   + "WHERE lancamento_programado_transferencia.idLancamentoProgramadoOrigem = lancamentoProgramadoOrigem.idLancamentoProgramado "
                   + "AND lancamento_programado_transferencia.idLancamentoProgramadoDestino = lancamentoProgramadoDestino.idLancamentoProgramado "
                   + "ORDER BY lancamento_programado_transferencia.idLancamentoTransferenciaProgramado";        
        return findAll(sql, null, lancamentoTransferenciaProgramadoHandler);
    }

    public LancamentoTransferenciaProgramado find(Integer idLancamentoTransferenciaProgramado) throws Exception {
        String sql = "SELECT * FROM lancamento_programado_transferencia, lancamentoProgramado lancamentoProgramadoOrigem, lancamentoProgramado lancamentoProgramadoDestino "
                   + "WHERE lancamento_programado_transferencia.idLancamentoProgramadoOrigem = lancamentoProgramadoOrigem.idLancamentoProgramado "
                   + "AND lancamento_programado_transferencia.idLancamentoProgramadoDestino = lancamentoProgramadoDestino.idLancamentoProgramado "
                   + "AND lancamento_programado_transferencia.idLancamentoTransferenciaProgramado = :idLancamentoTransferenciaProgramado";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoTransferenciaProgramado", idLancamentoTransferenciaProgramado);
        return (LancamentoTransferenciaProgramado) find(sql, params, lancamentoTransferenciaProgramadoHandler);
    }

    public LancamentoTransferenciaProgramado save(LancamentoTransferenciaProgramado lancamentoTransferenciaProgramado) throws Exception {  
        String sql = "INSERT INTO lancamento_programado_transferencia (idLancamentoProgramadoOrigem, idLancamentoProgramadoDestino, numero, "
                   + "documento, formaPagamento, frequencia, dataEmissao, valor, historico) "
                   + "VALUES(:idLancamentoProgramadoOrigem, :idLancamentoProgramadoDestino, :numero, :documento, :formaPagamento, "
                   + ":frequencia, :dataEmissao, :valor, :historico)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoProgramadoOrigem", lancamentoTransferenciaProgramado.getLancamentoProgramadoOrigem().getIdLancamentoProgramado());
        params.put("idLancamentoProgramadoDestino", lancamentoTransferenciaProgramado.getLancamentoProgramadoDestino().getIdLancamentoProgramado()); 
        params.put("numero", lancamentoTransferenciaProgramado.getNumero());          
        params.put("documento", lancamentoTransferenciaProgramado.getDocumento().getIdTipoDocumento());           
        params.put("formaPagamento", lancamentoTransferenciaProgramado.getFormaPagamento().getIdTipoFormaPagamento());         
        params.put("frequencia", lancamentoTransferenciaProgramado.getFrequencia().ordinal());           
        params.put("dataEmissao", lancamentoTransferenciaProgramado.getDataEmissao());      
        params.put("valor", lancamentoTransferenciaProgramado.getValor());   
        params.put("historico", lancamentoTransferenciaProgramado.getHistorico());         
        Integer idLancamentoTransferenciaProgramado = save(sql, params, lancamentoTransferenciaProgramadoHandler);
        return find(idLancamentoTransferenciaProgramado);
    }

    public LancamentoTransferenciaProgramado update(LancamentoTransferenciaProgramado lancamentoTransferenciaProgramado) throws Exception {
        String sql = "UPDATE lancamento_programado_transferencia "
                   + "SET idLancamentoProgramadoOrigem = :idLancamentoProgramadoOrigem, idLancamentoProgramadoDestino = :idLancamentoProgramadoDestino "
                   + "numero = :numero, documento = :documento, formaPagamento = :formaPagamento, frequencia = :frequencia, dataEmissao = :dataEmissao, "
                   + "valor = :valor, historico = :historico "
                   + "WHERE idLancamentoTransferenciaProgramado = :idLancamentoTransferenciaProgramado ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoTransferenciaProgramado", lancamentoTransferenciaProgramado.getIdLancamentoTransferenciaProgramado());
        params.put("idLancamentoProgramadoOrigem", lancamentoTransferenciaProgramado.getLancamentoProgramadoOrigem().getIdLancamentoProgramado());
        params.put("idLancamentoProgramadoDestino", lancamentoTransferenciaProgramado.getLancamentoProgramadoDestino().getIdLancamentoProgramado());
        params.put("numero", lancamentoTransferenciaProgramado.getNumero());               
        params.put("documento", lancamentoTransferenciaProgramado.getDocumento().getIdTipoDocumento());           
        params.put("formaPagamento", lancamentoTransferenciaProgramado.getFormaPagamento().getIdTipoFormaPagamento());         
        params.put("frequencia", lancamentoTransferenciaProgramado.getFrequencia().ordinal());           
        params.put("dataEmissao", lancamentoTransferenciaProgramado.getDataEmissao());      
        params.put("valor", lancamentoTransferenciaProgramado.getValor());   
        params.put("historico", lancamentoTransferenciaProgramado.getHistorico());            
        update(sql, params, lancamentoTransferenciaProgramadoHandler);
        return lancamentoTransferenciaProgramado;  
    }

    public LancamentoTransferenciaProgramado remove(Integer idLancamentoTransferenciaProgramado) throws Exception { 
        String sql = "DELETE FROM lancamento_programado_transferencia WHERE idLancamentoTransferenciaProgramado = :idLancamentoTransferenciaProgramado ";
        LancamentoTransferenciaProgramado lancamentoTransferenciaProgramado = find(idLancamentoTransferenciaProgramado);
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idLancamentoTransferenciaProgramado", idLancamentoTransferenciaProgramado);
        remove(sql, params, lancamentoTransferenciaProgramadoHandler);
        return lancamentoTransferenciaProgramado;
    }
}
