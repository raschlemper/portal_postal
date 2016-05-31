package com.portalpostal.service;

import com.portalpostal.dao.LancamentoDAO;
import com.portalpostal.model.Lancamento;
import com.portalpostal.model.LancamentoProgramado;
import com.portalpostal.model.LancamentoRateio;
import com.portalpostal.model.LancamentoTransferencia;
import com.portalpostal.model.Saldo;
import com.portalpostal.model.dd.TipoModeloLancamento;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LancamentoService {
    
    private final String nomeBD;
    
    private LancamentoDAO lancamentoDAO;
    private LancamentoProgramadoService lancamentoProgramadoService;
    private LancamentoTransferenciaService lancamentoTransferenciaService;
    private LancamentoRateioService lancamentoRateioService;

    public LancamentoService(String nomeBD) {
        this.nomeBD = nomeBD;
    }

    public void init() {
        lancamentoDAO = new LancamentoDAO(nomeBD);
        lancamentoProgramadoService = new LancamentoProgramadoService(nomeBD);
        lancamentoTransferenciaService = new LancamentoTransferenciaService(nomeBD);
        lancamentoRateioService = new LancamentoRateioService(nomeBD);
    }
    
    public List<Lancamento> findAll() throws Exception {
        init();
        List<Lancamento> lancamentos = lancamentoDAO.findAll();
        lancamentos = setRateios(lancamentos);
        return lancamentos;
    }  
    
    public Lancamento find(Integer idLancamento) throws Exception {
        init();
        Lancamento lancamento = lancamentoDAO.find(idLancamento);
        lancamento = setRateios(lancamento);
        return lancamento;
    }  

    public List<Lancamento> findLancamentoNotConciliadoByDataLancamento(Date data) throws Exception {
        init();
        List<Lancamento> lancamentos = lancamentoDAO.findLancamentoNotConciliadoByDataLancamento(data);
        lancamentos = setRateios(lancamentos);
        return lancamentos;
    }
    
    public List<Lancamento> findByConta(Integer idConta) throws Exception {
        init();
        List<Lancamento> lancamentos = lancamentoDAO.findByConta(idConta);
        lancamentos = setRateios(lancamentos);
        return lancamentos;
    } 

    public List<Lancamento> findByPlanoConta(Integer idPlanoConta) throws Exception {
        init();
        List<Lancamento> lancamentos = lancamentoDAO.findByPlanoConta(idPlanoConta);
        lancamentos = setRateios(lancamentos);
        return lancamentos;
    }

    public List<Lancamento> findByCentroCusto(Integer idCentroCusto) throws Exception {
        init();
        List<Lancamento> lancamentos = lancamentoDAO.findByCentroCusto(idCentroCusto);
        lancamentos = setRateios(lancamentos);
        return lancamentos;
    }
    
    public List<Lancamento> findByLancamentoProgramado(Integer idLancamentoProgramado) throws Exception {
        init();
        List<Lancamento> lancamentos = lancamentoDAO.findByLancamentoProgramado(idLancamentoProgramado);
        lancamentos = setRateios(lancamentos);
        return lancamentos;
    } 
    
    public List<Saldo> findSaldo(Date dataInicio, Date dataFim) throws Exception {
        init();
        return lancamentoDAO.findSaldo(dataInicio, dataFim);
    } 
    
    public List<Saldo> findSaldoPlanoConta(Date dataInicio, Date dataFim) throws Exception {
        init();
        return lancamentoDAO.findSaldoPlanoConta(dataInicio, dataFim);
    }  
    
    public List<Saldo> findSaldoCentroCusto(Date dataInicio, Date dataFim) throws Exception {
        init();
        return lancamentoDAO.findSaldoCentroCusto(dataInicio, dataFim);
    }  
    
    public List<Saldo> findSaldoPlanoContaCompetencia(Date dataInicio, Date dataFim) throws Exception {
        init();
        return lancamentoDAO.findSaldoPlanoContaCompetencia(dataInicio, dataFim);
    }  
    
    public List<Saldo> findSaldoCentroCustoCompetencia(Date dataInicio, Date dataFim) throws Exception {
        init();
        return lancamentoDAO.findSaldoCentroCustoCompetencia(dataInicio, dataFim);
    } 
    
    public List<Saldo> findSaldoTipo(Date dataInicio, Date dataFim) throws Exception {
        init();
        return lancamentoDAO.findSaldoTipo(dataInicio, dataFim);
    } 
    
    public List<Integer> findYearFromLancamento() throws Exception {
        init();
        return lancamentoDAO.findYearFromLancamento();
    } 
    
    public Lancamento findLastByLancamentoProgramado(Integer idLancamentoProgramado) throws Exception {
        init();
        Lancamento lancamento = lancamentoDAO.findLastByLancamentoProgramado(idLancamentoProgramado);
        lancamento = setRateios(lancamento);
        return lancamento;
    }
    
    public Lancamento findByNumeroParcela(Integer idLancamentoProgramado, Integer numeroParcela) throws Exception {
        init();
        Lancamento lancamento = lancamentoDAO.findByNumeroParcela(idLancamentoProgramado, numeroParcela);
        lancamento = setRateios(lancamento);
        return lancamento;
    } 
    
    public Lancamento save(Lancamento lancamento) throws Exception {
        init();
        removerLancamentoConciliado(lancamento);
        lancamento.setRateios(saveOrUpdateRateio(lancamento));
        return lancamentoDAO.save(lancamento);
    } 
    
    public Lancamento update(Lancamento lancamento) throws Exception {
        init();
        removerLancamentoConciliado(lancamento);
        lancamento.setRateios(saveOrUpdateRateio(lancamento));
        lancamento.setNumeroLoteConciliado(null);
        return lancamentoDAO.update(lancamento);
    } 

    public void updateNumeroLoteConciliado(Date data, Integer numeroLoteConciliado) throws Exception {
        init();        
        lancamentoDAO.updateNumeroLoteConciliado(data, numeroLoteConciliado);
    }
    
    public Lancamento delete(Integer idLancamento) throws Exception {
        init();
        Lancamento lancamento = find(idLancamento);
        removerLancamentoTransferencia(lancamento);
        removerLancamentoProgramado(lancamento);
        removerLancamentoConciliado(lancamento);
        removerRateio(lancamento.getRateios());
        return lancamentoDAO.remove(idLancamento);     
    } 
    
    private List<Lancamento> setRateios(List<Lancamento> lancamentos) throws Exception {
        for (Lancamento lancamento : lancamentos) {
            setRateios(lancamento);
        }
        return lancamentos;
    }
    
    private Lancamento setRateios(Lancamento lancamento) throws Exception {
        lancamento.setRateios(getRateios(lancamento.getIdLancamento()));
        return lancamento;
    }
    
    private List<LancamentoRateio> getRateios(Integer idLancamento) throws Exception {
        return lancamentoRateioService.findByLancamento(idLancamento);
    }
    
    private List<LancamentoRateio> saveOrUpdateRateio(Lancamento lancamento) throws Exception {
        if(lancamento.getRateios() == null || lancamento.getRateios().isEmpty()) {
            removerRateio(getRateios(lancamento.getIdLancamento()));
            return null;
        }
        List<LancamentoRateio> rateiosLista = new ArrayList<LancamentoRateio>();
        lancamento.setPlanoConta(null);
        lancamento.setCentroCusto(null);
        lancamento.setObservacao(null);
        for (LancamentoRateio rateio : lancamento.getRateios()) {
            rateio.setLancamento(lancamento);
            if(rateio.getIdLancamentoRateio() != null) {
                rateiosLista.add(lancamentoRateioService.update(rateio));
            } else {
                rateiosLista.add(lancamentoRateioService.save(rateio));
            }
        }
        return rateiosLista;
    }
    
    private List<LancamentoRateio> removerRateio(List<LancamentoRateio> rateios) throws Exception {
        List<LancamentoRateio> rateiosLista = new ArrayList<LancamentoRateio>();
        for (LancamentoRateio rateio : rateios) {
            rateiosLista.add(lancamentoRateioService.delete(rateio.getIdLancamentoRateio()));
        }
        return rateiosLista;
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
        lancamentoDAO.removeNumeroLoteConciliado(lancamento.getDataLancamento());
    }
    
}
