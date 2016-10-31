package com.portalpostal.service;

import com.portalpostal.dao.LancamentoProgramadoTransferenciaDAO;
import com.portalpostal.model.LancamentoProgramado;
import com.portalpostal.model.LancamentoProgramadoTransferencia;
import java.util.List;

public class LancamentoProgramadoTransferenciaService {
    
    private final String nomeBD;
    
    private LancamentoProgramadoTransferenciaDAO lancamentoProgramadoTransferenciaDAO;
    private LancamentoProgramadoService lancamentoProgramadoService;

    public LancamentoProgramadoTransferenciaService(String nomeBD) {
        this.nomeBD = nomeBD;
    }

    public void init() {
        lancamentoProgramadoTransferenciaDAO = new LancamentoProgramadoTransferenciaDAO(nomeBD);
        lancamentoProgramadoService = new LancamentoProgramadoService(nomeBD);
    }
    
    public List<LancamentoProgramadoTransferencia> findAll() throws Exception {
        init();
        return lancamentoProgramadoTransferenciaDAO.findAll();
    }  
    
    public LancamentoProgramadoTransferencia find(Integer idLancamentoProgramadoTransferencia) throws Exception {
        init();
        LancamentoProgramadoTransferencia lancamentoProgramadoTransferencia = lancamentoProgramadoTransferenciaDAO.find(idLancamentoProgramadoTransferencia);
        lancamentoProgramadoTransferencia.setLancamentoProgramadoOrigem(lancamentoProgramadoService.find(
                lancamentoProgramadoTransferencia.getLancamentoProgramadoOrigem().getIdLancamentoProgramado()));
        lancamentoProgramadoTransferencia.setLancamentoProgramadoDestino(lancamentoProgramadoService.find(
                lancamentoProgramadoTransferencia.getLancamentoProgramadoDestino().getIdLancamentoProgramado()));
        return lancamentoProgramadoTransferencia;
    }  
    
    public LancamentoProgramadoTransferencia findByLancamento(Integer idLancamento) throws Exception {
        init();
        LancamentoProgramadoTransferencia transferencia = lancamentoProgramadoTransferenciaDAO.findByLancamentoOrigem(idLancamento);
        if(transferencia == null) { transferencia = lancamentoProgramadoTransferenciaDAO.findByLancamentoDestino(idLancamento); }
        return transferencia;
    } 
    
    public LancamentoProgramadoTransferencia save(LancamentoProgramadoTransferencia lancamentoProgramadoTransferencia) throws Exception {
        init();
        LancamentoProgramado origem = lancamentoProgramadoService.save(lancamentoProgramadoTransferencia.getLancamentoProgramadoOrigem());
        LancamentoProgramado destino = lancamentoProgramadoService.save(lancamentoProgramadoTransferencia.getLancamentoProgramadoDestino());
        lancamentoProgramadoTransferencia.setLancamentoProgramadoOrigem(origem);
        lancamentoProgramadoTransferencia.setLancamentoProgramadoDestino(destino);
        return lancamentoProgramadoTransferenciaDAO.save(lancamentoProgramadoTransferencia);
    } 
    
    public LancamentoProgramadoTransferencia update(LancamentoProgramadoTransferencia lancamentoProgramadoTransferencia) throws Exception {
        init();
        LancamentoProgramado origem = lancamentoProgramadoService.update(lancamentoProgramadoTransferencia.getLancamentoProgramadoOrigem());
        LancamentoProgramado destino = lancamentoProgramadoService.update(lancamentoProgramadoTransferencia.getLancamentoProgramadoDestino());
        lancamentoProgramadoTransferencia.setLancamentoProgramadoOrigem(origem);
        lancamentoProgramadoTransferencia.setLancamentoProgramadoDestino(destino);
        return lancamentoProgramadoTransferenciaDAO.update(lancamentoProgramadoTransferencia);
    } 
    
    public LancamentoProgramadoTransferencia delete(Integer idLancamentoProgramadoTransferencia) throws Exception {
        init();
        return lancamentoProgramadoTransferenciaDAO.remove(idLancamentoProgramadoTransferencia);
    }   
    
}
