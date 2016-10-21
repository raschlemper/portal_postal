package com.portalpostal.service;

import com.portalpostal.dao.LancamentoDAO;
import com.portalpostal.dao.LancamentoTransferenciaDAO;
import com.portalpostal.model.Lancamento;
import com.portalpostal.model.LancamentoTransferencia;
import java.util.List;

public class LancamentoTransferenciaService {
    
    private final LancamentoTransferenciaDAO lancamentoTransferenciaDAO;
    private final LancamentoDAO lancamentoDAO;

    public LancamentoTransferenciaService(String nomeBD) {
        lancamentoTransferenciaDAO = new LancamentoTransferenciaDAO(nomeBD);
        lancamentoDAO = new LancamentoDAO(nomeBD);
    }
    
    public List<LancamentoTransferencia> findAll() throws Exception {
        return lancamentoTransferenciaDAO.findAll();
    }  
    
    public LancamentoTransferencia find(Integer idLancamentoTransferencia) throws Exception {
        return lancamentoTransferenciaDAO.find(idLancamentoTransferencia);
    } 
    
    public LancamentoTransferencia save(LancamentoTransferencia lancamentoTransferencia) throws Exception {
        Lancamento origem = lancamentoDAO.save(lancamentoTransferencia.getLancamentoOrigem());
        Lancamento destino = lancamentoDAO.save(lancamentoTransferencia.getLancamentoDestino());
        lancamentoTransferencia.setLancamentoOrigem(origem);
        lancamentoTransferencia.setLancamentoDestino(destino);
        return lancamentoTransferenciaDAO.save(lancamentoTransferencia);
    } 
    
    public LancamentoTransferencia update(LancamentoTransferencia lancamentoTransferencia) throws Exception {
        return lancamentoTransferenciaDAO.update(lancamentoTransferencia);
    } 
    
    public LancamentoTransferencia delete(Integer idLancamentoTransferencia) throws Exception {
        return lancamentoTransferenciaDAO.remove(idLancamentoTransferencia);
    }   
    
}
