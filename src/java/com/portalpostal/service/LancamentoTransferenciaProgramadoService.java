package com.portalpostal.service;

import com.portalpostal.dao.LancamentoTransferenciaProgramadoDAO;
import com.portalpostal.model.LancamentoProgramado;
import com.portalpostal.model.LancamentoTransferenciaProgramado;
import java.util.List;

public class LancamentoTransferenciaProgramadoService {
    
    private final LancamentoTransferenciaProgramadoDAO lancamentoTransferenciaProgramadoDAO;
    private final LancamentoProgramadoService lancamentoProgramadoService;

    public LancamentoTransferenciaProgramadoService(String nomeBD) {
        lancamentoTransferenciaProgramadoDAO = new LancamentoTransferenciaProgramadoDAO(nomeBD);
        lancamentoProgramadoService = new LancamentoProgramadoService(nomeBD);
    }
    
    public List<LancamentoTransferenciaProgramado> findAll() throws Exception {
        return lancamentoTransferenciaProgramadoDAO.findAll();
    }  
    
    public LancamentoTransferenciaProgramado find(Integer idLancamentoTransferenciaProgramado) throws Exception {
        return lancamentoTransferenciaProgramadoDAO.find(idLancamentoTransferenciaProgramado);
    } 
    
    public LancamentoTransferenciaProgramado save(LancamentoTransferenciaProgramado lancamentoTransferenciaProgramado) throws Exception {
        LancamentoProgramado origem = lancamentoProgramadoService.save(lancamentoTransferenciaProgramado.getLancamentoProgramadoOrigem());
        LancamentoProgramado destino = lancamentoProgramadoService.save(lancamentoTransferenciaProgramado.getLancamentoProgramadoDestino());
        lancamentoTransferenciaProgramado.setLancamentoProgramadoOrigem(origem);
        lancamentoTransferenciaProgramado.setLancamentoProgramadoDestino(destino);
        return lancamentoTransferenciaProgramadoDAO.save(lancamentoTransferenciaProgramado);
    } 
    
    public LancamentoTransferenciaProgramado update(LancamentoTransferenciaProgramado lancamentoTransferenciaProgramado) throws Exception {
        return lancamentoTransferenciaProgramadoDAO.update(lancamentoTransferenciaProgramado);
    } 
    
    public LancamentoTransferenciaProgramado delete(Integer idLancamentoTransferenciaProgramado) throws Exception {
        return lancamentoTransferenciaProgramadoDAO.remove(idLancamentoTransferenciaProgramado);
    }   
    
}
