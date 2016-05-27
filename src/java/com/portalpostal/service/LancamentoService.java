package com.portalpostal.service;

import com.portalpostal.dao.LancamentoDAO;
import com.portalpostal.model.Lancamento;
import com.portalpostal.model.LancamentoConciliado;
import com.portalpostal.model.LancamentoProgramado;
import com.portalpostal.model.LancamentoTransferencia;
import com.portalpostal.model.Saldo;
import com.portalpostal.model.dd.TipoModeloLancamento;
import java.util.Date;
import java.util.List;

public class LancamentoService {
    
    private final String nomeBD;
    
    private LancamentoDAO lancamentoDAO;
    private LancamentoProgramadoService lancamentoProgramadoService;
    private LancamentoTransferenciaService lancamentoTransferenciaService;
    private LancamentoConciliadoService lancamentoConciliadoService;

    public LancamentoService(String nomeBD) {
        this.nomeBD = nomeBD;
    }

    public void init() {
        lancamentoDAO = new LancamentoDAO(nomeBD);
        lancamentoProgramadoService = new LancamentoProgramadoService(nomeBD);
        lancamentoTransferenciaService = new LancamentoTransferenciaService(nomeBD);
        lancamentoConciliadoService = new LancamentoConciliadoService(nomeBD);
    }
    
    public List<Lancamento> findAll() throws Exception {
        init();
        return lancamentoDAO.findAll();
    }  
    
    public Lancamento find(Integer idLancamento) throws Exception {
        init();
        return lancamentoDAO.find(idLancamento);
    }  

    public List<Lancamento> findLancamentoNotConciliadoByDataLancamento(Date data) throws Exception {
        init();
        return lancamentoDAO.findLancamentoNotConciliadoByDataLancamento(data);
    }
    
    public List<Lancamento> findByConta(Integer idConta) throws Exception {
        init();
        return lancamentoDAO.findByConta(idConta);
    } 

    public List<Lancamento> findByPlanoConta(Integer idPlanoConta) throws Exception {
        init();
        return lancamentoDAO.findByPlanoConta(idPlanoConta);
    }

    public List<Lancamento> findByCentroCusto(Integer idCentroCusto) throws Exception {
        init();
        return lancamentoDAO.findByCentroCusto(idCentroCusto);
    }
    
    public List<Lancamento> findByLancamentoProgramado(Integer idLancamentoProgramado) throws Exception {
        init();
        return lancamentoDAO.findByLancamentoProgramado(idLancamentoProgramado);
    } 
    
    public List<Saldo> findSaldo(Date dataInicio, Date dataFim) throws Exception {
        init();
        return lancamentoDAO.findSaldo(dataInicio, dataFim);
    } 
    
    public List<Saldo> findSaldoPlanoConta(Date dataInicio, Date dataFim) throws Exception {
        init();
        return lancamentoDAO.findSaldoPlanoConta(dataInicio, dataFim);
    }  
    
    public List<Saldo> findSaldoPlanoContaCompetencia(Date dataInicio, Date dataFim) throws Exception {
        init();
        return lancamentoDAO.findSaldoPlanoContaCompetencia(dataInicio, dataFim);
    } 
    
    public List<Saldo> findSaldoTipo(Date dataInicio, Date dataFim) throws Exception {
        init();
        return lancamentoDAO.findSaldoTipo(dataInicio, dataFim);
    } 
    
    public List<Saldo> findSaldoConciliado(Date data) throws Exception {
        init();
        return lancamentoDAO.findSaldoConciliado(data);
    } 
    
    public List<Integer> findYearFromLancamento() throws Exception {
        init();
        return lancamentoDAO.findYearFromLancamento();
    } 
    
    public Lancamento findLastByLancamentoProgramado(Integer idLancamentoProgramado) throws Exception {
        init();
        return lancamentoDAO.findLastByLancamentoProgramado(idLancamentoProgramado);
    }
    
    public Lancamento findByNumeroParcela(Integer idLancamentoProgramado, Integer numeroParcela) throws Exception {
        init();
        return lancamentoDAO.findByNumeroParcela(idLancamentoProgramado, numeroParcela);
    } 
    
    public Lancamento save(Lancamento lancamento) throws Exception {
        init();
        removerLancamentoConciliado(lancamento);
        return lancamentoDAO.save(lancamento);
    } 
    
    public Lancamento update(Lancamento lancamento) throws Exception {
        init();
        removerLancamentoConciliado(lancamento);
        lancamento.setNumeroLoteConciliado(null);
        return lancamentoDAO.update(lancamento);
    } 

    public Lancamento updateNumeroLoteConciliado(Lancamento lancamento) throws Exception {
        init();
        return lancamentoDAO.updateNumeroLoteConciliado(lancamento);
    }
    
    public Lancamento delete(Integer idLancamento) throws Exception {
        init();
        Lancamento lancamento = find(idLancamento);
        removerLancamentoTransferencia(lancamento);
        removerLancamentoProgramado(lancamento);
        removerLancamentoConciliado(lancamento);
        return lancamentoDAO.remove(idLancamento);     
    } 
    
    private void removerLancamentoTransferencia(Lancamento lancamento) throws Exception {
        if(lancamento.getModelo() == TipoModeloLancamento.TRANSFERENCIA) {
            LancamentoTransferencia lancamentoTransferencia = lancamentoTransferenciaService.findByLancamento(lancamento.getIdLancamento());
            lancamentoTransferenciaService.delete(lancamentoTransferencia.getIdLancamentoTransferencia());
            if(lancamentoTransferencia.getLancamentoOrigem() != null) {
                lancamentoDAO.remove(lancamentoTransferencia.getLancamentoOrigem().getIdLancamento());
            }
            if(lancamentoTransferencia.getLancamentoDestino() != null) {
                lancamentoDAO.remove(lancamentoTransferencia.getLancamentoDestino().getIdLancamento());
            }
        }
    }
    
    private void removerLancamentoProgramado(Lancamento lancamento) throws Exception {
        LancamentoProgramado lancamentoProgamado = lancamento.getLancamentoProgramado();
        if(lancamentoProgamado != null) {
            Integer numeroParcela = lancamento.getNumeroParcela() - 1;
            lancamentoProgamado.setNumeroParcela(numeroParcela);
            lancamentoProgramadoService.updateNumeroParcela(lancamentoProgamado);            
        }
    }
    
    private void removerLancamentoConciliado(Lancamento lancamento) throws Exception {
        if(lancamento.getNumeroLoteConciliado() != null) {
            LancamentoConciliado lancamentoConciliado = lancamentoConciliadoService.findByLote(lancamento.getNumeroLoteConciliado());
            removeLancamentoConciliadoByLote(lancamento.getNumeroLoteConciliado());
            removeLancamentoConciliado(lancamentoConciliado.getIdLancamentoConciliado());
            removeByLancamentoConciliado(lancamentoConciliado.getLancamento().getIdLancamento());
        }
        List<LancamentoConciliado> lancamentos = lancamentoConciliadoService.findByData(lancamento.getDataLancamento());
        for (LancamentoConciliado lancamentoConciliado : lancamentos) {
            removeLancamentoConciliadoByLote(lancamentoConciliado.getNumeroLote()); 
            removeLancamentoConciliado(lancamentoConciliado.getIdLancamentoConciliado());  
            removeByLancamentoConciliado(lancamentoConciliado.getLancamento().getIdLancamento());         
        }
    }
    
    private void removeLancamentoConciliadoByLote(Integer numeroLoteConciliado) throws Exception {
        if(numeroLoteConciliado != null) {
            lancamentoDAO.removeNumeroLoteConciliado(numeroLoteConciliado);
        }
    }
    
    private void removeByLancamentoConciliado(Integer idLancamento) throws Exception {
        if(idLancamento != null) {
            lancamentoDAO.remove(idLancamento);
        }
    }
    
    private void removeLancamentoConciliado(Integer idLancamentoConciliado) throws Exception {
        if(idLancamentoConciliado != null) {
            lancamentoConciliadoService.delete(idLancamentoConciliado);
        }
    }
    
}
