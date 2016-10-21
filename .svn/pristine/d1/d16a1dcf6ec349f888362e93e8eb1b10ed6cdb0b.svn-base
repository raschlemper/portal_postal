package com.portalpostal.service;

import com.portalpostal.dao.LancamentoDAO;
import com.portalpostal.model.Lancamento;
import com.portalpostal.model.LancamentoProgramado;
import com.portalpostal.model.LancamentoRateio;
import com.portalpostal.model.LancamentoTransferencia;
import com.portalpostal.model.Saldo;
import com.portalpostal.model.dd.TipoModeloLancamento;
import com.portalpostal.model.dd.TipoSituacaoLancamentoProgramado;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LancamentoService {
    
    private final String nomeBD;
    
    private LancamentoDAO lancamentoDAO;
    private LancamentoProgramadoService lancamentoProgramadoService;
    private LancamentoTransferenciaService lancamentoTransferenciaService;
    private LancamentoConciliadoService lancamentoConciliadoService;
    private LancamentoRateioService lancamentoRateioService;
    private LancamentoProgramadoParcelaService lancamentoprogramadoParcelaService;

    public LancamentoService(String nomeBD) {
        this.nomeBD = nomeBD;
    }

    public void init() {
        lancamentoDAO = new LancamentoDAO(nomeBD);
        lancamentoProgramadoService = new LancamentoProgramadoService(nomeBD);
        lancamentoTransferenciaService = new LancamentoTransferenciaService(nomeBD);
        lancamentoConciliadoService = new LancamentoConciliadoService(nomeBD);
        lancamentoRateioService = new LancamentoRateioService(nomeBD);
        lancamentoprogramadoParcelaService = new LancamentoProgramadoParcelaService(nomeBD);
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
        lancamento = setRateio(lancamento);
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
    
    public List<Lancamento> findByFavorecido(Integer idFavorecido) throws Exception {
        init();
        List<Lancamento> lancamentos = lancamentoDAO.findByFavorecido(idFavorecido);
        lancamentos = setRateios(lancamentos);
        return lancamentos;
    } 
    
    public List<Lancamento> findByColaborador(Integer idColaborador) throws Exception {
        init();
        List<Lancamento> lancamentos = lancamentoDAO.findByColaborador(idColaborador);
        lancamentos = setRateios(lancamentos);
        return lancamentos;
    } 
    
    public List<Lancamento> findByFornecedor(Integer idFornecedor) throws Exception {
        init();
        List<Lancamento> lancamentos = lancamentoDAO.findByFornecedor(idFornecedor);
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
        lancamento = setRateio(lancamento);
        return lancamento;
    }
    
    public Lancamento findByNumeroParcela(Integer idLancamentoProgramado, Integer numeroParcela) throws Exception {
        init();
        Lancamento lancamento = lancamentoDAO.findByNumeroParcela(idLancamentoProgramado, numeroParcela);
        if(lancamento != null) { lancamento = setRateio(lancamento); }
        return lancamento;
    } 
    
    public Lancamento save(Lancamento lancamento) throws Exception {
        init();
        Lancamento lancamentoResult = lancamentoDAO.save(ajustaLancamentoRateio(lancamento));
        removerLancamentoConciliado(lancamentoResult);
        lancamentoResult.setRateios(lancamento.getRateios());
        lancamentoResult.setRateios(saveOrUpdateRateio(lancamentoResult));
        return lancamentoResult;
    } 
    
    public Lancamento update(Lancamento lancamento) throws Exception {
        init();
        Lancamento lancamentoResult = lancamentoDAO.update(ajustaLancamentoRateio(lancamento));
        removerLancamentoConciliado(lancamentoResult);
        lancamentoResult.setRateios(saveOrUpdateRateio(lancamento));
        return lancamentoResult;
    } 

    public void updateNumeroLoteConciliado(Date data, Integer numeroLoteConciliado) throws Exception {
        init();        
        lancamentoDAO.updateNumeroLoteConciliado(data, numeroLoteConciliado);
    }
    
    public Lancamento updateSituacao(Lancamento lancamento) throws Exception {
        init();
        return lancamentoDAO.updateSituacao(lancamento);
    } 
    
    public Lancamento delete(Integer idLancamento) throws Exception {
        init();
        Lancamento lancamento = find(idLancamento);
        removerLancamentoTransferencia(lancamento);
        removerLancamentoProgramado(lancamento);
        removerLancamentoConciliado(lancamento);
        removerLancamentoParcela(lancamento);
        removerLancamentoRateio(lancamento, null);
        return lancamentoDAO.remove(idLancamento);     
    } 
    
    // ***** RATEIO ***** //
    
    private List<Lancamento> setRateios(List<Lancamento> lancamentos) throws Exception {
        for (Lancamento lancamento : lancamentos) {
            setRateio(lancamento);
        }
        return lancamentos;
    }
    
    private Lancamento setRateio(Lancamento lancamento) throws Exception {
        lancamento.setRateios(getRateios(lancamento.getIdLancamento()));
        return lancamento;
    }
    
    private List<LancamentoRateio> getRateios(Integer idLancamento) throws Exception {
        return lancamentoRateioService.findByLancamento(idLancamento);
    }
    
    private List<LancamentoRateio> saveOrUpdateRateio(Lancamento lancamento) throws Exception {
        removerLancamentoRateio(lancamento, lancamento.getRateios());
        List<LancamentoRateio> rateiosLista = new ArrayList<LancamentoRateio>();
        if(lancamento.getRateios() == null) return rateiosLista;
        for (LancamentoRateio rateio : lancamento.getRateios()) {
//            rateio.setLancamento(createLancamentoToRateio(lancamento));
            rateio.setLancamento(getLancamentoToRateio(lancamento));
            if(rateio.getIdLancamentoRateio() != null) {
                rateiosLista.add(lancamentoRateioService.update(rateio));
            } else {
                rateiosLista.add(lancamentoRateioService.save(rateio));
            }
        }
        return rateiosLista;
    }
    
    private Lancamento getLancamentoToRateio(Lancamento lancamento) {
        Lancamento rateio = new Lancamento();
        rateio.setIdLancamento(lancamento.getIdLancamento());
        return rateio;
    }
    
    private Lancamento ajustaLancamentoRateio(Lancamento lancamento) {
        if(lancamento.getRateios() == null || lancamento.getRateios().isEmpty()) { return lancamento; }
        lancamento.setPlanoConta(null);
        lancamento.setCentroCusto(null);
        lancamento.setObservacao(null);
        lancamento.setNumeroLoteConciliado(null);
        return lancamento;
    }
    
    private Lancamento createLancamentoToRateio(Lancamento lancamento) {        
        Lancamento lancamentoRateio = new Lancamento();
        lancamentoRateio.setIdLancamento(lancamento.getIdLancamento());
        return lancamentoRateio;
    }
    
    private void removerLancamentoRateio(Lancamento lancamento, List<LancamentoRateio> rateiosLancamento) throws Exception {
        List<LancamentoRateio> rateios = getRateios(lancamento.getIdLancamento());
        for (LancamentoRateio rateio : rateios) {    
            if(rateiosLancamento == null || rateiosLancamento.isEmpty() || !rateiosLancamento.contains(rateio)) {
                lancamentoRateioService.delete(rateio.getIdLancamentoRateio());
            } 
        }
    }
    
    // ***** TRANSFERENCIA ***** //
    
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
    
    // ***** PROGRAMACAO ***** //
    
    private void removerLancamentoProgramado(Lancamento lancamento) throws Exception {
        LancamentoProgramado lancamentoProgamado = lancamento.getLancamentoProgramado();
        if(lancamentoProgamado != null) {
            Integer numeroParcela = lancamento.getNumeroParcela() - 1;
            lancamentoProgamado.setNumeroParcela(numeroParcela);
            updateSituacaoLancamentoProgramadoEncerrado(lancamentoProgamado);    
            lancamentoProgramadoService.updateNumeroParcela(lancamentoProgamado);            
        }
    }
    
    private void updateSituacaoLancamentoProgramadoEncerrado(LancamentoProgramado lancamentoProgamado) throws Exception {        
        if(lancamentoProgamado.getSituacao() == TipoSituacaoLancamentoProgramado.ENCERRADO) {
            lancamentoProgamado.setSituacao(TipoSituacaoLancamentoProgramado.ATIVO);
        }
        lancamentoProgramadoService.updateSituacao(lancamentoProgamado);     
    }
    
    // ***** CONCILIACAO ***** //
    
    private void removerLancamentoConciliado(Lancamento lancamento) throws Exception {
        lancamentoDAO.removeNumeroLoteConciliado(lancamento.getDataLancamento());
        if(lancamento.getModelo() == TipoModeloLancamento.CONCILIADO) {
            lancamentoConciliadoService.deleteByLancamento(lancamento.getIdLancamento());
        }
    }
    
    // ***** PARCELA ***** //
    
    private void removerLancamentoParcela(Lancamento lancamento) throws Exception {
        lancamentoprogramadoParcelaService.removeLancamento(lancamento.getIdLancamento());
    }
    
}
