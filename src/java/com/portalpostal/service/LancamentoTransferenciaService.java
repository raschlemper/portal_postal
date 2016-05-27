package com.portalpostal.service;

import com.portalpostal.dao.LancamentoTransferenciaDAO;
import com.portalpostal.model.Lancamento;
import com.portalpostal.model.LancamentoTransferencia;
import java.util.List;

public class LancamentoTransferenciaService {
    
    private final String nomeBD;
    
    private LancamentoTransferenciaDAO lancamentoTransferenciaDAO;
    private LancamentoService lancamentoService;

    public LancamentoTransferenciaService(String nomeBD) {
        this.nomeBD = nomeBD;
    }

    public void init() {
        lancamentoTransferenciaDAO = new LancamentoTransferenciaDAO(nomeBD);
        lancamentoService = new LancamentoService(nomeBD);
    }
    
    public List<LancamentoTransferencia> findAll() throws Exception {
        init();
        return lancamentoTransferenciaDAO.findAll();
    }  
    
    public LancamentoTransferencia find(Integer idLancamentoTransferencia) throws Exception {
        init();
        return lancamentoTransferenciaDAO.find(idLancamentoTransferencia);
    }  
    
    public LancamentoTransferencia findByLancamento(Integer idLancamento) throws Exception {
        init();
        LancamentoTransferencia transferencia = lancamentoTransferenciaDAO.findByLancamentoOrigem(idLancamento);
        if(transferencia == null) { transferencia = lancamentoTransferenciaDAO.findByLancamentoDestino(idLancamento); }
        return transferencia;
    } 
    
    public LancamentoTransferencia save(LancamentoTransferencia lancamentoTransferencia) throws Exception {
        init();
        Lancamento origem = lancamentoService.save(lancamentoTransferencia.getLancamentoOrigem());
        Lancamento destino = lancamentoService.save(lancamentoTransferencia.getLancamentoDestino());
        lancamentoTransferencia.setLancamentoOrigem(origem);
        lancamentoTransferencia.setLancamentoDestino(destino);
        return lancamentoTransferenciaDAO.save(lancamentoTransferencia);
    } 
    
    public LancamentoTransferencia update(LancamentoTransferencia lancamentoTransferencia) throws Exception {
        init();
        return lancamentoTransferenciaDAO.update(lancamentoTransferencia);
    } 
    
    public LancamentoTransferencia delete(Integer idLancamentoTransferencia) throws Exception {
        init();
        return lancamentoTransferenciaDAO.remove(idLancamentoTransferencia);
    }   
    
}
