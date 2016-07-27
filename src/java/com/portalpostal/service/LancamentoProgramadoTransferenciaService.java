package com.portalpostal.service;

import com.portalpostal.dao.LancamentoProgramadoTransferenciaDAO;
import com.portalpostal.model.LancamentoProgramado;
import com.portalpostal.model.LancamentoProgramadoTransferencia;
import java.util.List;

public class LancamentoProgramadoTransferenciaService {
    
    private final String nomeBD;
    
    private LancamentoProgramadoTransferenciaDAO lancamentoTransferenciaProgramadoDAO;
    private LancamentoProgramadoService lancamentoProgramadoService;

    public LancamentoProgramadoTransferenciaService(String nomeBD) {
        this.nomeBD = nomeBD;
    }

    public void init() {
        lancamentoTransferenciaProgramadoDAO = new LancamentoProgramadoTransferenciaDAO(nomeBD);
        lancamentoProgramadoService = new LancamentoProgramadoService(nomeBD);
    }
    
    public List<LancamentoProgramadoTransferencia> findAll() throws Exception {
        init();
        return lancamentoTransferenciaProgramadoDAO.findAll();
    }  
    
    public LancamentoProgramadoTransferencia find(Integer idLancamentoTransferenciaProgramado) throws Exception {
        init();
        LancamentoProgramadoTransferencia lancamentoTransferenciaProgramado = lancamentoTransferenciaProgramadoDAO.find(idLancamentoTransferenciaProgramado);
        lancamentoTransferenciaProgramado.setLancamentoProgramadoOrigem(lancamentoProgramadoService.find(
                lancamentoTransferenciaProgramado.getLancamentoProgramadoOrigem().getIdLancamentoProgramado()));
        lancamentoTransferenciaProgramado.setLancamentoProgramadoDestino(lancamentoProgramadoService.find(
                lancamentoTransferenciaProgramado.getLancamentoProgramadoDestino().getIdLancamentoProgramado()));
        return lancamentoTransferenciaProgramado;
    }  
    
    public LancamentoProgramadoTransferencia findByLancamento(Integer idLancamento) throws Exception {
        init();
        LancamentoProgramadoTransferencia transferencia = lancamentoTransferenciaProgramadoDAO.findByLancamentoOrigem(idLancamento);
        if(transferencia == null) { transferencia = lancamentoTransferenciaProgramadoDAO.findByLancamentoDestino(idLancamento); }
        return transferencia;
    } 
    
    public LancamentoProgramadoTransferencia save(LancamentoProgramadoTransferencia lancamentoTransferenciaProgramado) throws Exception {
        init();
        LancamentoProgramado origem = lancamentoProgramadoService.save(lancamentoTransferenciaProgramado.getLancamentoProgramadoOrigem());
        LancamentoProgramado destino = lancamentoProgramadoService.save(lancamentoTransferenciaProgramado.getLancamentoProgramadoDestino());
        lancamentoTransferenciaProgramado.setLancamentoProgramadoOrigem(origem);
        lancamentoTransferenciaProgramado.setLancamentoProgramadoDestino(destino);
        return lancamentoTransferenciaProgramadoDAO.save(lancamentoTransferenciaProgramado);
    } 
    
    public LancamentoProgramadoTransferencia update(LancamentoProgramadoTransferencia lancamentoTransferenciaProgramado) throws Exception {
        init();
        return lancamentoTransferenciaProgramadoDAO.update(lancamentoTransferenciaProgramado);
    } 
    
    public LancamentoProgramadoTransferencia delete(Integer idLancamentoTransferenciaProgramado) throws Exception {
        init();
        return lancamentoTransferenciaProgramadoDAO.remove(idLancamentoTransferenciaProgramado);
    }   
    
}
