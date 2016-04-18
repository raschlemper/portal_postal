package com.portalpostal.dao;

import com.portalpostal.dao.handler.TipoFormaPagamentoHandler;
import com.portalpostal.model.TipoDocumento;
import com.portalpostal.model.TipoFormaPagamento;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TipoFormaPagamentoDAO extends GenericDAO { 
    
    private final TipoFormaPagamentoHandler tipoDocumentoHandler;

    public TipoFormaPagamentoDAO(String nameDB) { 
        super(nameDB, TipoFormaPagamentoDAO.class);
        tipoDocumentoHandler = new TipoFormaPagamentoHandler();
    } 

    public List<TipoFormaPagamento> findAll() throws Exception {
        String sql = "SELECT * FROM tipo_forma_pagamento "
                   + "ORDER BY tipo_forma_pagamento.idTipoFormaPagamento";        
        return findAll(sql, null, tipoDocumentoHandler);
    }

    public TipoFormaPagamento find(Integer idTipoFormaPagamento) throws Exception {
        String sql = "SELECT * FROM tipo_forma_pagamento "
                   + "WHERE tipo_forma_pagamento.idTipoFormaPagamento = :idTipoFormaPagamento";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idTipoFormaPagamento", idTipoFormaPagamento);
        return (TipoFormaPagamento) find(sql, params, tipoDocumentoHandler);
    }

    public TipoFormaPagamento findByDescricao(String descricao) throws Exception {
        String sql = "SELECT * FROM tipo_forma_pagamento "
                   + "WHERE tipo_forma_pagamento.descricao = :descricao";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("descricao", descricao);
        return (TipoFormaPagamento) find(sql, params, tipoDocumentoHandler);
    }

    public TipoFormaPagamento save(TipoFormaPagamento tipoDocumento) throws Exception {  
        String sql = "INSERT INTO tipo_forma_pagamento (descricao) "
                   + "VALUES(:descricao)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("descricao", tipoDocumento.getDescricao());
        Integer idTipoFormaPagamento = save(sql, params, tipoDocumentoHandler);
        return find(idTipoFormaPagamento);
    }

    public TipoFormaPagamento update(TipoFormaPagamento tipoDocumento) throws Exception {
        String sql = "UPDATE tipo_forma_pagamento "
                   + "SET descricao = :descricao "
                   + "WHERE idTipoFormaPagamento = :idTipoFormaPagamento ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idTipoFormaPagamento", tipoDocumento.getIdTipoFormaPagamento());
        params.put("descricao", tipoDocumento.getDescricao());
        update(sql, params, tipoDocumentoHandler);
        return tipoDocumento;  
    }

    public TipoFormaPagamento remove(Integer idTipoFormaPagamento) throws Exception { 
        String sql = "DELETE FROM tipo_forma_pagamento WHERE idTipoFormaPagamento = :idTipoFormaPagamento ";
        TipoFormaPagamento tipoDocumento = find(idTipoFormaPagamento);
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idTipoFormaPagamento", idTipoFormaPagamento);
        remove(sql, params, tipoDocumentoHandler);
        return tipoDocumento;
    }
}
