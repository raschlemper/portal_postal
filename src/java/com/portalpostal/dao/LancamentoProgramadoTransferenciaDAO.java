package com.portalpostal.dao;

import com.portalpostal.dao.handler.LancamentoProgramadoTransferenciaHandler;
import com.portalpostal.model.LancamentoProgramadoTransferencia;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LancamentoProgramadoTransferenciaDAO extends GenericDAO { 
    
    private final LancamentoProgramadoTransferenciaHandler lancamentoTransferenciaProgramadoHandler;

    public LancamentoProgramadoTransferenciaDAO(String nameDB) { 
        super(nameDB, LancamentoProgramadoTransferenciaDAO.class);
        this.lancamentoTransferenciaProgramadoHandler = new LancamentoProgramadoTransferenciaHandler();
    } 

    public List<LancamentoProgramadoTransferencia> findAll() throws Exception {
        String sql = "SELECT * FROM lancamento_programado_transferencia, "
                   + "lancamentoProgramado lancamentoProgramadoOrigem, lancamentoProgramado lancamentoProgramadoDestino "
                   + "WHERE lancamento_programado_transferencia.idLancamentoProgramadoOrigem = lancamentoProgramadoOrigem.idLancamentoProgramado "
                   + "AND lancamento_programado_transferencia.idLancamentoProgramadoDestino = lancamentoProgramadoDestino.idLancamentoProgramado "
                   + "ORDER BY lancamento_programado_transferencia.idLancamentoTransferenciaProgramado";        
        return findAll(sql, null, lancamentoTransferenciaProgramadoHandler);
    }

    public LancamentoProgramadoTransferencia find(Integer idLancamentoTransferenciaProgramado) throws Exception {
        String sql = "SELECT * FROM lancamento_programado_transferencia, lancamentoProgramado lancamentoProgramadoOrigem, lancamentoProgramado lancamentoProgramadoDestino "
                   + "WHERE lancamento_programado_transferencia.idLancamentoProgramadoOrigem = lancamentoProgramadoOrigem.idLancamentoProgramado "
                   + "AND lancamento_programado_transferencia.idLancamentoProgramadoDestino = lancamentoProgramadoDestino.idLancamentoProgramado "
                   + "AND lancamento_programado_transferencia.idLancamentoTransferenciaProgramado = :idLancamentoTransferenciaProgramado";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoTransferenciaProgramado", idLancamentoTransferenciaProgramado);
        return (LancamentoProgramadoTransferencia) find(sql, params, lancamentoTransferenciaProgramadoHandler);
    }

    public LancamentoProgramadoTransferencia findByLancamentoOrigem(Integer idLancamentoProgramadoOrigem) throws Exception {
        String sql = "SELECT * FROM lancamento_programado_transferencia, lancamentoProgramado lancamentoProgramadoOrigem, lancamentoProgramado lancamentoProgramadoDestino "
                   + "WHERE lancamento_programado_transferencia.idLancamentoProgramadoOrigem = lancamentoProgramadoOrigem.idLancamentoProgramado "
                   + "AND lancamento_programado_transferencia.idLancamentoProgramadoDestino = lancamentoProgramadoDestino.idLancamentoProgramado "
                   + "AND lancamento_programado_transferencia.idLancamentoProgramadoOrigem = :idLancamentoProgramadoOrigem";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoProgramadoOrigem", idLancamentoProgramadoOrigem);
        return (LancamentoProgramadoTransferencia) find(sql, params, lancamentoTransferenciaProgramadoHandler);
    }

    public LancamentoProgramadoTransferencia findByLancamentoDestino(Integer idLancamentoProgramadoDestino) throws Exception {
        String sql = "SELECT * FROM lancamento_programado_transferencia, lancamentoProgramado lancamentoProgramadoOrigem, lancamentoProgramado lancamentoProgramadoDestino "
                   + "WHERE lancamento_programado_transferencia.idLancamentoProgramadoOrigem = lancamentoProgramadoOrigem.idLancamentoProgramado "
                   + "AND lancamento_programado_transferencia.idLancamentoProgramadoDestino = lancamentoProgramadoDestino.idLancamentoProgramado "
                   + "AND lancamento_programado_transferencia.idLancamentoProgramadoDestino = :idLancamentoProgramadoDestino";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoProgramadoDestino", idLancamentoProgramadoDestino);
        return (LancamentoProgramadoTransferencia) find(sql, params, lancamentoTransferenciaProgramadoHandler);
    }

    public LancamentoProgramadoTransferencia save(LancamentoProgramadoTransferencia lancamentoTransferenciaProgramado) throws Exception {  
        String sql = "INSERT INTO lancamento_programado_transferencia (idLancamentoProgramadoOrigem, idLancamentoProgramadoDestino, numero, "
                   + "documento, formaPagamento, frequencia, dataCompetencia, dataEmissao, dataVencimento, valor, historico, usuario) "
                   + "VALUES(:idLancamentoProgramadoOrigem, :idLancamentoProgramadoDestino, :numero, :documento, :formaPagamento, "
                   + ":frequencia, :dataCompetencia, :dataEmissao, :dataVencimento, :valor, :historico, :usuario)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoProgramadoOrigem", lancamentoTransferenciaProgramado.getLancamentoProgramadoOrigem().getIdLancamentoProgramado());
        params.put("idLancamentoProgramadoDestino", lancamentoTransferenciaProgramado.getLancamentoProgramadoDestino().getIdLancamentoProgramado()); 
        params.put("numero", lancamentoTransferenciaProgramado.getNumero());          
        params.put("documento", lancamentoTransferenciaProgramado.getDocumento().getIdTipoDocumento());           
        params.put("formaPagamento", lancamentoTransferenciaProgramado.getFormaPagamento().getIdTipoFormaPagamento());         
        params.put("frequencia", lancamentoTransferenciaProgramado.getFrequencia().ordinal());   
        params.put("dataCompetencia", lancamentoTransferenciaProgramado.getDataCompetencia());       
        params.put("dataEmissao", lancamentoTransferenciaProgramado.getDataEmissao());       
        params.put("dataVencimento", lancamentoTransferenciaProgramado.getDataVencimento());      
        params.put("valor", lancamentoTransferenciaProgramado.getValor());   
        params.put("historico", lancamentoTransferenciaProgramado.getHistorico());                
        params.put("usuario", lancamentoTransferenciaProgramado.getUsuario());
        Integer idLancamentoTransferenciaProgramado = save(sql, params, lancamentoTransferenciaProgramadoHandler);
        return find(idLancamentoTransferenciaProgramado);
    }

    public LancamentoProgramadoTransferencia update(LancamentoProgramadoTransferencia lancamentoTransferenciaProgramado) throws Exception {
        String sql = "UPDATE lancamento_programado_transferencia "
                   + "SET idLancamentoProgramadoOrigem = :idLancamentoProgramadoOrigem, idLancamentoProgramadoDestino = :idLancamentoProgramadoDestino "
                   + "numero = :numero, documento = :documento, formaPagamento = :formaPagamento, frequencia = :frequencia, dataCompetencia = :dataCompetencia, "
                   + "dataEmissao = :dataEmissao, dataVencimento = :dataVencimento, valor = :valor, historico = :historico, usuario = :usuario "
                   + "WHERE idLancamentoTransferenciaProgramado = :idLancamentoTransferenciaProgramado ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoTransferenciaProgramado", lancamentoTransferenciaProgramado.getIdLancamentoTransferenciaProgramado());
        params.put("idLancamentoProgramadoOrigem", lancamentoTransferenciaProgramado.getLancamentoProgramadoOrigem().getIdLancamentoProgramado());
        params.put("idLancamentoProgramadoDestino", lancamentoTransferenciaProgramado.getLancamentoProgramadoDestino().getIdLancamentoProgramado());
        params.put("numero", lancamentoTransferenciaProgramado.getNumero());               
        params.put("documento", lancamentoTransferenciaProgramado.getDocumento().getIdTipoDocumento());           
        params.put("formaPagamento", lancamentoTransferenciaProgramado.getFormaPagamento().getIdTipoFormaPagamento());         
        params.put("frequencia", lancamentoTransferenciaProgramado.getFrequencia().ordinal());  
        params.put("dataCompetencia", lancamentoTransferenciaProgramado.getDataCompetencia());       
        params.put("dataEmissao", lancamentoTransferenciaProgramado.getDataEmissao());       
        params.put("dataVencimento", lancamentoTransferenciaProgramado.getDataVencimento());      
        params.put("valor", lancamentoTransferenciaProgramado.getValor());   
        params.put("historico", lancamentoTransferenciaProgramado.getHistorico());              
        params.put("usuario", lancamentoTransferenciaProgramado.getUsuario());
        update(sql, params, lancamentoTransferenciaProgramadoHandler);
        return lancamentoTransferenciaProgramado;  
    }

    public LancamentoProgramadoTransferencia remove(Integer idLancamentoTransferenciaProgramado) throws Exception { 
        String sql = "DELETE FROM lancamento_programado_transferencia WHERE idLancamentoTransferenciaProgramado = :idLancamentoTransferenciaProgramado ";
        LancamentoProgramadoTransferencia lancamentoTransferenciaProgramado = find(idLancamentoTransferenciaProgramado);
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idLancamentoTransferenciaProgramado", idLancamentoTransferenciaProgramado);
        remove(sql, params, lancamentoTransferenciaProgramadoHandler);
        return lancamentoTransferenciaProgramado;
    }
}
