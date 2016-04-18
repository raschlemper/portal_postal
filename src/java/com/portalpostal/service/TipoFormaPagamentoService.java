package com.portalpostal.service;

import com.portalpostal.dao.TipoFormaPagamentoDAO;
import com.portalpostal.model.TipoFormaPagamento;
import java.util.List;

public class TipoFormaPagamentoService {
    
    private final TipoFormaPagamentoDAO tipoFormaPagamentoDAO;

    public TipoFormaPagamentoService(String nomeBD) {
        tipoFormaPagamentoDAO = new TipoFormaPagamentoDAO(nomeBD);
    }
    
    public List<TipoFormaPagamento> findAll() throws Exception {
        return tipoFormaPagamentoDAO.findAll();
    }  
    
    public TipoFormaPagamento find(Integer idTipoFormaPagamento) throws Exception {
        return tipoFormaPagamentoDAO.find(idTipoFormaPagamento);
    } 
    
    public TipoFormaPagamento findByDescricao(String descricao) throws Exception {
        return tipoFormaPagamentoDAO.findByDescricao(descricao);
    } 
    
    public TipoFormaPagamento save(TipoFormaPagamento tipoFormaPagamento) throws Exception {
        validation(tipoFormaPagamento);
        return tipoFormaPagamentoDAO.save(tipoFormaPagamento);
    } 
    
    public TipoFormaPagamento update(TipoFormaPagamento tipoFormaPagamento) throws Exception {
        validation(tipoFormaPagamento);
        return tipoFormaPagamentoDAO.update(tipoFormaPagamento);
    } 
    
    public TipoFormaPagamento delete(Integer idTipoFormaPagamento) throws Exception {
        return tipoFormaPagamentoDAO.remove(idTipoFormaPagamento);
    }   
    
    private void validation(TipoFormaPagamento tipoFormaPagamento) throws Exception {  
        if(existeTipoFormaPagamento(tipoFormaPagamento)) {
            throw new Exception("Este TipoFormaPagamento já foi cadastrado!");
        } 
    }  
    
    private boolean existeTipoFormaPagamento(TipoFormaPagamento tipoFormaPagamento) throws Exception {
        TipoFormaPagamento tipoFormaPagamentoDescricao = tipoFormaPagamentoDAO.findByDescricao(tipoFormaPagamento.getDescricao());
        if(tipoFormaPagamentoDescricao == null) return false;
        if(tipoFormaPagamentoDescricao.getIdTipoFormaPagamento().equals(tipoFormaPagamento.getIdTipoFormaPagamento())) return false;
        return true;
    }
    
}
