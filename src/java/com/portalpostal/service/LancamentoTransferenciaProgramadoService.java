package com.portalpostal.service;

import com.portalpostal.dao.LancamentoTransferenciaProgramadoDAO;
import com.portalpostal.model.LancamentoProgramado;
import com.portalpostal.model.LancamentoTransferenciaProgramado;
import java.util.List;

public class LancamentoTransferenciaProgramadoService {
    
    private final String nomeBD;
    
    private LancamentoTransferenciaProgramadoDAO lancamentoTransferenciaProgramadoDAO;
    private LancamentoProgramadoService lancamentoProgramadoService;

    public LancamentoTransferenciaProgramadoService(String nomeBD) {
        this.nomeBD = nomeBD;
    }

    public void init() {
        lancamentoTransferenciaProgramadoDAO = new LancamentoTransferenciaProgramadoDAO(nomeBD);
        lancamentoProgramadoService = new LancamentoProgramadoService(nomeBD);
    }
    
    public List<LancamentoTransferenciaProgramado> findAll() throws Exception {
        init();
        return lancamentoTransferenciaProgramadoDAO.findAll();
    }  
    
    public LancamentoTransferenciaProgramado find(Integer idLancamentoTransferenciaProgramado) throws Exception {
        init();
        return lancamentoTransferenciaProgramadoDAO.find(idLancamentoTransferenciaProgramado);
    } 
    
    public LancamentoTransferenciaProgramado save(LancamentoTransferenciaProgramado lancamentoTransferenciaProgramado) throws Exception {
        init();
        LancamentoProgramado origem = lancamentoProgramadoService.save(lancamentoTransferenciaProgramado.getLancamentoProgramadoOrigem());
        LancamentoProgramado destino = lancamentoProgramadoService.save(lancamentoTransferenciaProgramado.getLancamentoProgramadoDestino());
        lancamentoTransferenciaProgramado.setLancamentoProgramadoOrigem(origem);
        lancamentoTransferenciaProgramado.setLancamentoProgramadoDestino(destino);
        return lancamentoTransferenciaProgramadoDAO.save(lancamentoTransferenciaProgramado);
    } 
    
    public LancamentoTransferenciaProgramado update(LancamentoTransferenciaProgramado lancamentoTransferenciaProgramado) throws Exception {
        init();
        return lancamentoTransferenciaProgramadoDAO.update(lancamentoTransferenciaProgramado);
    } 
    
    public LancamentoTransferenciaProgramado delete(Integer idLancamentoTransferenciaProgramado) throws Exception {
        init();
        return lancamentoTransferenciaProgramadoDAO.remove(idLancamentoTransferenciaProgramado);
    }   
    
}
