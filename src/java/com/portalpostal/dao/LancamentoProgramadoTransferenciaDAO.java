package com.portalpostal.dao;

import com.portalpostal.dao.handler.LancamentoProgramadoTransferenciaHandler;
import com.portalpostal.model.LancamentoProgramadoTransferencia;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LancamentoProgramadoTransferenciaDAO extends GenericDAO { 
    
    private final LancamentoProgramadoTransferenciaHandler lancamentoProgramadoTransferenciaHandler;

    public LancamentoProgramadoTransferenciaDAO(String nameDB) { 
        super(nameDB, LancamentoProgramadoTransferenciaDAO.class);
        this.lancamentoProgramadoTransferenciaHandler = new LancamentoProgramadoTransferenciaHandler();
    } 

    public List<LancamentoProgramadoTransferencia> findAll() throws Exception {
        String sql = "SELECT * FROM lancamento_programado_transferencia, "
                   + "lancamentoProgramado lancamentoProgramadoOrigem, lancamentoProgramado lancamentoProgramadoDestino "
                   + "WHERE lancamento_programado_transferencia.idLancamentoProgramadoOrigem = lancamentoProgramadoOrigem.idLancamentoProgramado "
                   + "AND lancamento_programado_transferencia.idLancamentoProgramadoDestino = lancamentoProgramadoDestino.idLancamentoProgramado "
                   + "ORDER BY lancamento_programado_transferencia.idLancamentoTransferenciaProgramado";        
        return findAll(sql, null, lancamentoProgramadoTransferenciaHandler);
    }

    public LancamentoProgramadoTransferencia find(Integer idLancamentoProgramadoTransferencia) throws Exception {
        String sql = "SELECT * FROM lancamento_programado_transferencia, lancamentoProgramado lancamentoProgramadoOrigem, lancamentoProgramado lancamentoProgramadoDestino "
                   + "WHERE lancamento_programado_transferencia.idLancamentoProgramadoOrigem = lancamentoProgramadoOrigem.idLancamentoProgramado "
                   + "AND lancamento_programado_transferencia.idLancamentoProgramadoDestino = lancamentoProgramadoDestino.idLancamentoProgramado "
                   + "AND lancamento_programado_transferencia.idLancamentoProgramadoTransferencia = :idLancamentoProgramadoTransferencia";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoProgramadoTransferencia", idLancamentoProgramadoTransferencia);
        return (LancamentoProgramadoTransferencia) find(sql, params, lancamentoProgramadoTransferenciaHandler);
    }

    public LancamentoProgramadoTransferencia findByLancamentoOrigem(Integer idLancamentoProgramadoOrigem) throws Exception {
        String sql = "SELECT * FROM lancamento_programado_transferencia, lancamentoProgramado lancamentoProgramadoOrigem, lancamentoProgramado lancamentoProgramadoDestino "
                   + "WHERE lancamento_programado_transferencia.idLancamentoProgramadoOrigem = lancamentoProgramadoOrigem.idLancamentoProgramado "
                   + "AND lancamento_programado_transferencia.idLancamentoProgramadoDestino = lancamentoProgramadoDestino.idLancamentoProgramado "
                   + "AND lancamento_programado_transferencia.idLancamentoProgramadoOrigem = :idLancamentoProgramadoOrigem";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoProgramadoOrigem", idLancamentoProgramadoOrigem);
        return (LancamentoProgramadoTransferencia) find(sql, params, lancamentoProgramadoTransferenciaHandler);
    }

    public LancamentoProgramadoTransferencia findByLancamentoDestino(Integer idLancamentoProgramadoDestino) throws Exception {
        String sql = "SELECT * FROM lancamento_programado_transferencia, lancamentoProgramado lancamentoProgramadoOrigem, lancamentoProgramado lancamentoProgramadoDestino "
                   + "WHERE lancamento_programado_transferencia.idLancamentoProgramadoOrigem = lancamentoProgramadoOrigem.idLancamentoProgramado "
                   + "AND lancamento_programado_transferencia.idLancamentoProgramadoDestino = lancamentoProgramadoDestino.idLancamentoProgramado "
                   + "AND lancamento_programado_transferencia.idLancamentoProgramadoDestino = :idLancamentoProgramadoDestino";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoProgramadoDestino", idLancamentoProgramadoDestino);
        return (LancamentoProgramadoTransferencia) find(sql, params, lancamentoProgramadoTransferenciaHandler);
    }

    public LancamentoProgramadoTransferencia save(LancamentoProgramadoTransferencia lancamentoProgramadoTransferencia) throws Exception {  
        String sql = "INSERT INTO lancamento_programado_transferencia (idLancamentoProgramadoOrigem, idLancamentoProgramadoDestino, numero, "
                   + "documento, formaPagamento, frequencia, dataCompetencia, dataEmissao, dataVencimento, valor, historico, usuario) "
                   + "VALUES(:idLancamentoProgramadoOrigem, :idLancamentoProgramadoDestino, :numero, :documento, :formaPagamento, "
                   + ":frequencia, :dataCompetencia, :dataEmissao, :dataVencimento, :valor, :historico, :usuario)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoProgramadoOrigem", lancamentoProgramadoTransferencia.getLancamentoProgramadoOrigem().getIdLancamentoProgramado());
        params.put("idLancamentoProgramadoDestino", lancamentoProgramadoTransferencia.getLancamentoProgramadoDestino().getIdLancamentoProgramado()); 
        params.put("numero", lancamentoProgramadoTransferencia.getNumero());          
        params.put("documento", lancamentoProgramadoTransferencia.getDocumento().getIdTipoDocumento());           
        params.put("formaPagamento", lancamentoProgramadoTransferencia.getFormaPagamento().getIdTipoFormaPagamento());         
        params.put("frequencia", lancamentoProgramadoTransferencia.getFrequencia().ordinal());   
        params.put("dataCompetencia", lancamentoProgramadoTransferencia.getDataCompetencia());       
        params.put("dataEmissao", lancamentoProgramadoTransferencia.getDataEmissao());       
        params.put("dataVencimento", lancamentoProgramadoTransferencia.getDataVencimento());      
        params.put("valor", lancamentoProgramadoTransferencia.getValor());   
        params.put("historico", lancamentoProgramadoTransferencia.getHistorico());                
        params.put("usuario", lancamentoProgramadoTransferencia.getUsuario());
        Integer idLancamentoTransferenciaProgramado = save(sql, params, lancamentoProgramadoTransferenciaHandler);
        return find(idLancamentoTransferenciaProgramado);
    }

    public LancamentoProgramadoTransferencia update(LancamentoProgramadoTransferencia lancamentoProgramadoTransferencia) throws Exception {
        String sql = "UPDATE lancamento_programado_transferencia "
                   + "SET idLancamentoProgramadoOrigem = :idLancamentoProgramadoOrigem, idLancamentoProgramadoDestino = :idLancamentoProgramadoDestino "
                   + "numero = :numero, documento = :documento, formaPagamento = :formaPagamento, frequencia = :frequencia, dataCompetencia = :dataCompetencia, "
                   + "dataEmissao = :dataEmissao, dataVencimento = :dataVencimento, valor = :valor, historico = :historico, usuario = :usuario "
                   + "WHERE idLancamentoProgramadoTransferencia = :idLancamentoProgramadoTransferencia ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoTransferenciaProgramado", lancamentoProgramadoTransferencia.getIdLancamentoProgramadoTransferencia());
        params.put("idLancamentoProgramadoOrigem", lancamentoProgramadoTransferencia.getLancamentoProgramadoOrigem().getIdLancamentoProgramado());
        params.put("idLancamentoProgramadoDestino", lancamentoProgramadoTransferencia.getLancamentoProgramadoDestino().getIdLancamentoProgramado());
        params.put("numero", lancamentoProgramadoTransferencia.getNumero());               
        params.put("documento", lancamentoProgramadoTransferencia.getDocumento().getIdTipoDocumento());           
        params.put("formaPagamento", lancamentoProgramadoTransferencia.getFormaPagamento().getIdTipoFormaPagamento());         
        params.put("frequencia", lancamentoProgramadoTransferencia.getFrequencia().ordinal());  
        params.put("dataCompetencia", lancamentoProgramadoTransferencia.getDataCompetencia());       
        params.put("dataEmissao", lancamentoProgramadoTransferencia.getDataEmissao());       
        params.put("dataVencimento", lancamentoProgramadoTransferencia.getDataVencimento());      
        params.put("valor", lancamentoProgramadoTransferencia.getValor());   
        params.put("historico", lancamentoProgramadoTransferencia.getHistorico());              
        params.put("usuario", lancamentoProgramadoTransferencia.getUsuario());
        update(sql, params, lancamentoProgramadoTransferenciaHandler);
        return lancamentoProgramadoTransferencia;  
    }

    public LancamentoProgramadoTransferencia remove(Integer idLancamentoProgramadoTransferencia) throws Exception { 
        String sql = "DELETE FROM lancamento_programado_transferencia WHERE idLancamentoProgramadoTransferencia = :idLancamentoProgramadoTransferencia ";
        LancamentoProgramadoTransferencia lancamentoTransferenciaProgramado = find(idLancamentoProgramadoTransferencia);
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idLancamentoProgramadoTransferencia", idLancamentoProgramadoTransferencia);
        remove(sql, params, lancamentoProgramadoTransferenciaHandler);
        return lancamentoTransferenciaProgramado;
    }
}
