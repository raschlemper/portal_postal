package com.portalpostal.dao;

import com.portalpostal.dao.handler.LancamentoProgramadoHandler;
import com.portalpostal.model.LancamentoProgramado;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LancamentoProgramadoDAO extends GenericDAO { 
    
    private final LancamentoProgramadoHandler lancamentoProgramadoHandler;

    public LancamentoProgramadoDAO(String nameDB) { 
        super(nameDB, LancamentoProgramadoDAO.class);
        this.lancamentoProgramadoHandler = new LancamentoProgramadoHandler();
    } 

    public List<LancamentoProgramado> findAll(Date dataInicio, Date dataFim) throws Exception {
        String sql = "SELECT * FROM conta, tipo_documento, tipo_forma_pagamento, lancamento_programado "
                   + "LEFT OUTER JOIN favorecido ON(lancamento_programado.idFavorecido = favorecido.idFavorecido) "
                   + "LEFT OUTER JOIN plano_conta ON(lancamento_programado.idPlanoConta = plano_conta.idPlanoConta) "
                   + "LEFT OUTER JOIN centro_custo ON(lancamento_programado.idCentroCusto = centro_custo.idCentroCusto) "
                   + "LEFT OUTER JOIN lancamento_programado_transferencia "
                                + "ON(lancamento_programado.idLancamentoProgramado = lancamento_programado_transferencia.idLancamentoProgramadoOrigem OR "
                                   + "lancamento_programado.idLancamentoProgramado = lancamento_programado_transferencia.idLancamentoProgramadoDestino) "
                   + "WHERE lancamento_programado.idConta = conta.idConta "
                   + "AND lancamento_programado.idTipoDocumento = tipo_documento.idTipoDocumento " 
                   + "AND lancamento_programado.idTipoFormaPagamento = tipo_forma_pagamento.idTipoFormaPagamento " 
                   + "AND DATE(lancamento_programado.dataVencimento) "
                   + "BETWEEN IFNULL(:dataInicio, DATE(lancamento_programado.dataVencimento)) AND IFNULL(:dataFim, DATE(lancamento_programado.dataVencimento)) "
                   + "ORDER BY lancamento_programado.dataVencimento"; 
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("dataInicio", dataInicio);       
        params.put("dataFim", dataFim);           
        return findAll(sql, params, lancamentoProgramadoHandler);
    }

    public List<LancamentoProgramado> findAllAtivo(Date dataInicio, Date dataFim) throws Exception {
        String sql = "SELECT * FROM conta, lancamento_programado "
                   + "LEFT OUTER JOIN favorecido ON(lancamento_programado.idFavorecido = favorecido.idFavorecido) "
                   + "LEFT OUTER JOIN plano_conta ON(lancamento_programado.idPlanoConta = plano_conta.idPlanoConta) "
                   + "LEFT OUTER JOIN lancamento_programado_transferencia "
                                + "ON(lancamento_programado.idLancamentoProgramado = lancamento_programado_transferencia.idLancamentoProgramadoOrigem OR "
                                   + "lancamento_programado.idLancamentoProgramado = lancamento_programado_transferencia.idLancamentoProgramadoDestino) "
                   + "WHERE lancamento_programado.idConta = conta.idConta "
                   + "AND DATE(lancamento_programado.dataVencimento) "
                   + "BETWEEN IFNULL(:dataInicio, DATE(lancamento_programado.dataVencimento)) AND IFNULL(:dataFim, DATE(lancamento_programado.dataVencimento)) "
                   + "AND lancamento_programado.situacao = 0 ";    
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dataInicio", dataInicio);       
        params.put("dataFim", dataFim);        
        return findAll(sql, params, lancamentoProgramadoHandler);
    }

    public LancamentoProgramado find(Integer idLancamentoProgramado) throws Exception {
        String sql = "SELECT * FROM conta, tipo_documento, tipo_forma_pagamento, lancamento_programado "
                   + "LEFT OUTER JOIN favorecido ON(lancamento_programado.idFavorecido = favorecido.idFavorecido) "
                   + "LEFT OUTER JOIN plano_conta ON(lancamento_programado.idPlanoConta = plano_conta.idPlanoConta) "
                   + "LEFT OUTER JOIN centro_custo ON(lancamento_programado.idCentroCusto = centro_custo.idCentroCusto) "
                   + "LEFT OUTER JOIN lancamento_programado_transferencia "
                                + "ON(lancamento_programado.idLancamentoProgramado = lancamento_programado_transferencia.idLancamentoProgramadoOrigem OR "
                                   + "lancamento_programado.idLancamentoProgramado = lancamento_programado_transferencia.idLancamentoProgramadoDestino) "
                   + "WHERE lancamento_programado.idConta = conta.idConta "
                   + "AND lancamento_programado.idTipoDocumento = tipo_documento.idTipoDocumento " 
                   + "AND lancamento_programado.idTipoFormaPagamento = tipo_forma_pagamento.idTipoFormaPagamento " 
                   + "AND lancamento_programado.idLancamentoProgramado = :idLancamentoProgramado";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoProgramado", idLancamentoProgramado);
        return (LancamentoProgramado) find(sql, params, lancamentoProgramadoHandler);
    }

    public List<LancamentoProgramado> findByConta(Integer idConta, Date dataInicio, Date dataFim) throws Exception {
        String sql = "SELECT lancamento_programado.*, plano_conta.*, tipo_documento.*, tipo_forma_pagamento.*, centro_custo.*, favorecido.* "
                   + "FROM conta, tipo_documento, tipo_forma_pagamento, lancamento_programado "
                   + "LEFT OUTER JOIN favorecido ON(lancamento_programado.idFavorecido = favorecido.idFavorecido) "
                   + "LEFT OUTER JOIN plano_conta ON(lancamento_programado.idPlanoConta = plano_conta.idPlanoConta) "
                   + "LEFT OUTER JOIN centro_custo ON(lancamento_programado.idCentroCusto = centro_custo.idCentroCusto) "
                   + "LEFT OUTER JOIN lancamento_programado_transferencia "
                                + "ON(lancamento_programado.idLancamentoProgramado = lancamento_programado_transferencia.idLancamentoProgramadoOrigem OR "
                                   + "lancamento_programado.idLancamentoProgramado = lancamento_programado_transferencia.idLancamentoProgramadoDestino) "
                   + "WHERE conta.idConta = lancamento_programado.idConta "
                   + "AND lancamento_programado.idTipoDocumento = tipo_documento.idTipoDocumento " 
                   + "AND lancamento_programado.idTipoFormaPagamento = tipo_forma_pagamento.idTipoFormaPagamento " 
                   + "AND conta.idConta = :idConta "
                   + "AND DATE(lancamento_programado.dataVencimento) "
                   + "BETWEEN IFNULL(:dataInicio, DATE(lancamento_programado.dataVencimento)) AND IFNULL(:dataFim, DATE(lancamento_programado.dataVencimento)) "
                   + "ORDER BY lancamento_programado.dataVencimento";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idConta", idConta);
        params.put("dataInicio", dataInicio);       
        params.put("dataFim", dataFim);               
        return findAll(sql, params, lancamentoProgramadoHandler);
    }

    public List<LancamentoProgramado> findByPlanoConta(Integer idPlanoConta) throws Exception {
        String sql = "SELECT * FROM lancamento_programado, plano_conta, tipo_documento, tipo_forma_pagamento "
                   + "WHERE lancamento_programado.idPlanoConta = plano_conta.idPlanoConta "
                   + "AND lancamento_programado.idTipoDocumento = tipo_documento.idTipoDocumento " 
                   + "AND lancamento_programado.idTipoFormaPagamento = tipo_forma_pagamento.idTipoFormaPagamento " 
                   + "AND lancamento_programado.idPlanoConta = :idPlanoConta " 
                   + "ORDER BY lancamento_programado.dataVencimento";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idPlanoConta", idPlanoConta);       
        return findAll(sql, params, lancamentoProgramadoHandler);
    }

    public List<LancamentoProgramado> findByCentroCusto(Integer idCentroCusto) throws Exception {
        String sql = "SELECT * FROM lancamento_programado, centro_custo, tipo_documento, tipo_forma_pagamento "
                   + "WHERE lancamento_programado.idCentroCusto = centro_custo.idCentroCusto "
                   + "AND lancamento_programado.idTipoDocumento = tipo_documento.idTipoDocumento " 
                   + "AND lancamento_programado.idTipoFormaPagamento = tipo_forma_pagamento.idTipoFormaPagamento " 
                   + "AND lancamento_programado.idCentroCusto = :idCentroCusto " 
                   + "ORDER BY lancamento_programado.dataVencimento";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idCentroCusto", idCentroCusto);       
        return findAll(sql, params, lancamentoProgramadoHandler);
    }

    public List<LancamentoProgramado> findByFavorecido(Integer idFavorecido) throws Exception {
        String sql = "SELECT * FROM lancamento_programado "
                   + "WHERE lancamento_programado.idFavorecido = :idFavorecido";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idFavorecido", idFavorecido);
        return findAll(sql, params, lancamentoProgramadoHandler);
    }

    public List<LancamentoProgramado> findByColaborador(Integer idColaborador) throws Exception {
        String sql = "SELECT * FROM lancamento_programado, favorecido "
                   + "WHERE lancamento_programado.idFavorecido = favorecido.idFavorecido "
                   + "AND favorecido.idColaborador = :idColaborador";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idColaborador", idColaborador);
        return findAll(sql, params, lancamentoProgramadoHandler);
    }

    public List<LancamentoProgramado> findByFornecedor(Integer idFornecedor) throws Exception {
        String sql = "SELECT * FROM lancamento_programado, favorecido "
                   + "WHERE lancamento_programado.idFavorecido = favorecido.idFavorecido "
                   + "AND favorecido.idFornecedor = :idFornecedor";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idFornecedor", idFornecedor);
        return findAll(sql, params, lancamentoProgramadoHandler);
    }

    public LancamentoProgramado save(LancamentoProgramado lancamentoProgramado) throws Exception {  
        String sql = "INSERT INTO lancamento_programado (idConta, idPlanoConta, idCentroCusto, tipo, idFavorecido, "
                   + "numero, idTipoDocumento, idTipoFormaPagamento, frequencia, quantidadeParcela, numeroParcela, dataCompetencia, "
                   + "dataEmissao, dataVencimento, valor, situacao, modelo, historico, observacao, usuario) "
                   + "VALUES(:idConta, :idPlanoConta, :idCentroCusto, :tipo, :idFavorecido, :numero, :idTipoDocumento, "
                   + ":idTipoFormaPagamento, :frequencia, :quantidadeParcela, :numeroParcela, :dataCompetencia, :dataEmissao, "
                   + ":dataVencimento, :valor, :situacao, :modelo, :historico, :observacao, :usuario)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idConta", lancamentoProgramado.getConta().getIdConta());
        params.put("idPlanoConta", (lancamentoProgramado.getPlanoConta() == null ? null : lancamentoProgramado.getPlanoConta().getIdPlanoConta()));
        params.put("idCentroCusto", (lancamentoProgramado.getCentroCusto() == null ? null : lancamentoProgramado.getCentroCusto().getIdCentroCusto()));
        params.put("tipo", lancamentoProgramado.getTipo().ordinal()); 
        params.put("idFavorecido", (lancamentoProgramado.getFavorecido() == null ? null : lancamentoProgramado.getFavorecido().getIdFavorecido()));     
        params.put("numero", lancamentoProgramado.getNumero());        
        params.put("idTipoDocumento", lancamentoProgramado.getDocumento().getIdTipoDocumento());        
        params.put("idTipoFormaPagamento", lancamentoProgramado.getFormaPagamento().getIdTipoFormaPagamento());          
        params.put("frequencia", lancamentoProgramado.getFrequencia().ordinal());        
        params.put("quantidadeParcela", lancamentoProgramado.getQuantidadeParcela());           
        params.put("numeroParcela", lancamentoProgramado.getNumeroParcela());    
        params.put("dataCompetencia", lancamentoProgramado.getDataCompetencia());       
        params.put("dataEmissao", lancamentoProgramado.getDataEmissao());    
        params.put("dataVencimento", lancamentoProgramado.getDataVencimento());
        params.put("valor", lancamentoProgramado.getValor());    
        params.put("situacao", lancamentoProgramado.getSituacao().ordinal());   
        params.put("modelo", lancamentoProgramado.getModelo().ordinal());  
        params.put("historico", lancamentoProgramado.getHistorico());       
        params.put("observacao", lancamentoProgramado.getObservacao());          
        params.put("usuario", lancamentoProgramado.getUsuario()); 
        Integer idLancamentoProgramado = save(sql, params, lancamentoProgramadoHandler);
        return find(idLancamentoProgramado);
    }

    public LancamentoProgramado update(LancamentoProgramado lancamentoProgramado) throws Exception {
        String sql = "UPDATE lancamento_programado "
                   + "SET idConta = :idConta, idPlanoConta = :idPlanoConta, idCentroCusto = :idCentroCusto, "
                   + "tipo = :tipo, idFavorecido = :idFavorecido, numero = :numero, idTipoDocumento = :idTipoDocumento, "
                   + "idTipoFormaPagamento = :idTipoFormaPagamento, frequencia = :frequencia, quantidadeParcela = :quantidadeParcela, "
                   + "numeroParcela = :numeroParcela, dataCompetencia = :dataCompetencia, dataEmissao = :dataEmissao, dataVencimento = :dataVencimento, "
                   + "valor = :valor, situacao = :situacao, modelo = :modelo, historico = :historico, observacao = :observacao, usuario = :usuario "
                   + "WHERE idLancamentoProgramado = :idLancamentoProgramado ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoProgramado", lancamentoProgramado.getIdLancamentoProgramado());
        params.put("idConta", lancamentoProgramado.getConta().getIdConta());
        params.put("idPlanoConta", (lancamentoProgramado.getPlanoConta() == null ? null : lancamentoProgramado.getPlanoConta().getIdPlanoConta()));
        params.put("idCentroCusto", (lancamentoProgramado.getCentroCusto() == null ? null : lancamentoProgramado.getCentroCusto().getIdCentroCusto()));
        params.put("tipo", lancamentoProgramado.getTipo().ordinal());     
        params.put("idFavorecido", (lancamentoProgramado.getFavorecido() == null ? null : lancamentoProgramado.getFavorecido().getIdFavorecido()));  
        params.put("numero", lancamentoProgramado.getNumero());        
        params.put("idTipoDocumento", lancamentoProgramado.getDocumento().getIdTipoDocumento());        
        params.put("idTipoFormaPagamento", lancamentoProgramado.getFormaPagamento().getIdTipoFormaPagamento());          
        params.put("frequencia", lancamentoProgramado.getFrequencia().ordinal());     
        params.put("quantidadeParcela", lancamentoProgramado.getQuantidadeParcela());          
        params.put("numeroParcela", lancamentoProgramado.getNumeroParcela());    
        params.put("dataCompetencia", lancamentoProgramado.getDataCompetencia());          
        params.put("dataEmissao", lancamentoProgramado.getDataEmissao());    
        params.put("dataVencimento", lancamentoProgramado.getDataVencimento());
        params.put("valor", lancamentoProgramado.getValor());    
        params.put("situacao", lancamentoProgramado.getSituacao().ordinal());    
        params.put("modelo", lancamentoProgramado.getModelo().ordinal());  
        params.put("historico", lancamentoProgramado.getHistorico());        
        params.put("observacao", lancamentoProgramado.getObservacao());         
        params.put("usuario", lancamentoProgramado.getUsuario()); 
        update(sql, params, lancamentoProgramadoHandler);
        return lancamentoProgramado;  
    }

    public LancamentoProgramado updateNumeroParcela(LancamentoProgramado lancamentoProgramado) throws Exception {
        String sql = "UPDATE lancamento_programado "
                   + "SET numeroParcela = :numeroParcela "
                   + "WHERE idLancamentoProgramado = :idLancamentoProgramado ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoProgramado", lancamentoProgramado.getIdLancamentoProgramado());        
        params.put("numeroParcela", lancamentoProgramado.getNumeroParcela());    
        update(sql, params, lancamentoProgramadoHandler);
        return lancamentoProgramado;  
    }

    public LancamentoProgramado updateSituacao(LancamentoProgramado lancamentoProgramado) throws Exception {
        String sql = "UPDATE lancamento_programado "
                   + "SET situacao = :situacao "
                   + "WHERE idLancamentoProgramado = :idLancamentoProgramado ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoProgramado", lancamentoProgramado.getIdLancamentoProgramado());        
        params.put("situacao", lancamentoProgramado.getSituacao().ordinal());  
        update(sql, params, lancamentoProgramadoHandler);
        return lancamentoProgramado;  
    }

    public LancamentoProgramado remove(Integer idLancamentoProgramado) throws Exception { 
        String sql = "DELETE FROM lancamento_programado WHERE idLancamentoProgramado = :idLancamentoProgramado ";
        LancamentoProgramado lancamentoProgramado = find(idLancamentoProgramado);
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idLancamentoProgramado", idLancamentoProgramado);
        remove(sql, params, lancamentoProgramadoHandler);
        return lancamentoProgramado;
    }
}
