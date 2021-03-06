package com.portalpostal.dao;

import com.portalpostal.dao.handler.LancamentoHandler;
import com.portalpostal.dao.handler.SaldoHandler;
import com.portalpostal.model.Lancamento;
import com.portalpostal.model.Saldo;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LancamentoDAO extends GenericDAO { 
    
    private final LancamentoHandler lancamentoHandler;
    private final SaldoHandler saldoHandler;

    public LancamentoDAO(String nameDB) { 
        super(nameDB, LancamentoDAO.class);
        this.lancamentoHandler = new LancamentoHandler();
        this.saldoHandler = new SaldoHandler();
    } 

    public List<Lancamento> findAll(Date dataInicio, Date dataFim) throws Exception {
        String sql = "SELECT *, (SELECT count(1) FROM lancamento_anexo WHERE lancamento.idLancamento = lancamento_anexo.idLancamento) as anexos "
                   + "FROM conta, lancamento "
                   + "LEFT OUTER JOIN favorecido ON(lancamento.idFavorecido = favorecido.idFavorecido) "
                   + "LEFT OUTER JOIN plano_conta ON(lancamento.idPlanoConta = plano_conta.idPlanoConta) "
                   + "LEFT OUTER JOIN centro_custo ON(lancamento.idCentroCusto = centro_custo.idCentroCusto) "
                   + "LEFT OUTER JOIN lancamento_transferencia ON(lancamento.idLancamento = lancamento_transferencia.idLancamentoOrigem OR "
                                                               + "lancamento.idLancamento = lancamento_transferencia.idLancamentoDestino) "
                   + "LEFT OUTER JOIN lancamento_programado ON(lancamento.idLancamentoProgramado = lancamento_programado.idLancamentoProgramado) "
                   + "WHERE lancamento.idConta = conta.idConta "
                   + "AND DATE(lancamento.dataLancamento) "
                   + "BETWEEN IFNULL(:dataInicio, DATE(lancamento.dataLancamento)) AND IFNULL(:dataFim, DATE(lancamento.dataLancamento)) "
                   + "ORDER BY lancamento.dataLancamento"; 
        Map<String, Object> params = new HashMap<String, Object>();  
        params.put("dataInicio", dataInicio);       
        params.put("dataFim", dataFim);        
        return findAll(sql, params, lancamentoHandler);
    }

    public Lancamento find(Integer idLancamento) throws Exception {
        String sql = "SELECT * FROM conta, lancamento "
                   + "LEFT OUTER JOIN favorecido ON(lancamento.idFavorecido = favorecido.idFavorecido) "
                   + "LEFT OUTER JOIN plano_conta ON(lancamento.idPlanoConta = plano_conta.idPlanoConta) "
                   + "LEFT OUTER JOIN centro_custo ON(lancamento.idCentroCusto = centro_custo.idCentroCusto) "
                   + "LEFT OUTER JOIN lancamento_transferencia ON(lancamento.idLancamento = lancamento_transferencia.idLancamentoOrigem OR "
                                                               + "lancamento.idLancamento = lancamento_transferencia.idLancamentoDestino) "
                   + "LEFT OUTER JOIN lancamento_programado ON(lancamento.idLancamentoProgramado = lancamento_programado.idLancamentoProgramado) "
                   + "WHERE lancamento.idConta = conta.idConta "
                   + "AND lancamento.idLancamento = :idLancamento";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamento", idLancamento);
        return (Lancamento) find(sql, params, lancamentoHandler);
    }

    public Lancamento findLastByLancamentoConciliado(Integer idConta) throws Exception {
        String sql = "SELECT * FROM lancamento "
                   + "WHERE numeroLoteConciliado IS NOT NULL "
                   + "AND idConta = :idConta "
                   + "ORDER BY dataLancamento DESC "
                   + "LIMIT 1";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idConta", idConta);
        return (Lancamento) find(sql, params, lancamentoHandler);
    }

    public List<Lancamento> findLancamentoNotConciliadoByDataLancamento(Date data) throws Exception {
        String sql = "SELECT * FROM lancamento "
                   + "WHERE lancamento.dataLancamento <= :data";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("data", data);
        return findAll(sql, params, lancamentoHandler);
    }

    public List<Lancamento> findByConta(Integer idConta, Date dataInicio, Date dataFim) throws Exception {
        String sql = "SELECT lancamento.*, plano_conta.*, centro_custo.*, lancamento_transferencia.*, lancamento_programado.*, favorecido.*, conta.*, "
                   + "(SELECT count(1) FROM lancamento_anexo WHERE lancamento.idLancamento = lancamento_anexo.idLancamento) as anexos "
                   + "FROM conta, lancamento "
                   + "LEFT OUTER JOIN favorecido ON(lancamento.idFavorecido = favorecido.idFavorecido) "
                   + "LEFT OUTER JOIN plano_conta ON(lancamento.idPlanoConta = plano_conta.idPlanoConta) "
                   + "LEFT OUTER JOIN centro_custo ON(lancamento.idCentroCusto = centro_custo.idCentroCusto) "
                   + "LEFT OUTER JOIN lancamento_transferencia ON(lancamento.idLancamento = lancamento_transferencia.idLancamentoOrigem OR "
                                                               + "lancamento.idLancamento = lancamento_transferencia.idLancamentoDestino) "
                   + "LEFT OUTER JOIN lancamento_programado ON(lancamento.idLancamentoProgramado = lancamento_programado.idLancamentoProgramado) "
                   + "WHERE conta.idConta = lancamento.idConta "
                   + "AND conta.idConta = :idConta "
                   + "AND DATE(lancamento.dataLancamento) "
                   + "BETWEEN IFNULL(:dataInicio, DATE(lancamento.dataLancamento)) AND IFNULL(:dataFim, DATE(lancamento.dataLancamento)) "
                   + "ORDER BY lancamento.dataLancamento";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idConta", idConta);       
        params.put("dataInicio", dataInicio);       
        params.put("dataFim", dataFim);        
        return findAll(sql, params, lancamentoHandler);
    }

    public List<Lancamento> findByPlanoConta(Integer idPlanoConta) throws Exception {
        String sql = "SELECT lancamento.* FROM lancamento, plano_conta "
                   + "WHERE lancamento.idPlanoConta = plano_conta.idPlanoConta "
                   + "AND lancamento.idPlanoConta = :idPlanoConta "
                   + "ORDER BY lancamento.dataLancamento";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idPlanoConta", idPlanoConta);       
        return findAll(sql, params, lancamentoHandler);
    }

    public List<Lancamento> findByCentroCusto(Integer idCentroCusto) throws Exception {
        String sql = "SELECT lancamento.* FROM lancamento, centro_custo "
                   + "WHERE lancamento.idCentroCusto = centro_custo.idCentroCusto "
                   + "AND lancamento.idCentroCusto = :idCentroCusto "
                   + "ORDER BY lancamento.dataLancamento";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idCentroCusto", idCentroCusto);       
        return findAll(sql, params, lancamentoHandler);
    }

    public List<Lancamento> findByLancamentoProgramado(Integer idLancamentoProgramado) throws Exception {
        String sql = "SELECT * FROM lancamento "
                   + "WHERE lancamento.idLancamentoProgramado = :idLancamentoProgramado";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoProgramado", idLancamentoProgramado);
        return findAll(sql, params, lancamentoHandler);
    }

    public List<Lancamento> findByFavorecido(Integer idFavorecido) throws Exception {
        String sql = "SELECT * FROM lancamento "
                   + "WHERE lancamento.idFavorecido = :idFavorecido";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idFavorecido", idFavorecido);
        return findAll(sql, params, lancamentoHandler);
    }

    public List<Lancamento> findByColaborador(Integer idColaborador) throws Exception {
        String sql = "SELECT * FROM lancamento, favorecido "
                   + "WHERE lancamento.idFavorecido = favorecido.idFavorecido "
                   + "AND favorecido.idColaborador = :idColaborador";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idColaborador", idColaborador);
        return findAll(sql, params, lancamentoHandler);
    }

    public List<Lancamento> findByFornecedor(Integer idFornecedor) throws Exception {
        String sql = "SELECT * FROM lancamento, favorecido "
                   + "WHERE lancamento.idFavorecido = favorecido.idFavorecido "
                   + "AND favorecido.idFornecedor = :idFornecedor";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idFornecedor", idFornecedor);
        return findAll(sql, params, lancamentoHandler);
    }

    public List<Saldo> findSaldo(Date dataInicio, Date dataFim) throws Exception {
        String sql = "SELECT DATE(lancamento.dataLancamento) as data, "
                          + "SUM( IF(lancamento.tipo = 0, IFNULL(lancamento_rateio.valor, lancamento.valor), IFNULL(lancamento_rateio.valor, lancamento.valor) * -1) ) as valor "
                   + "FROM lancamento "
                   + "LEFT OUTER JOIN lancamento_rateio ON(lancamento.idLancamento = lancamento_rateio.idLancamento) "
                   + "WHERE DATE(dataLancamento) BETWEEN :dataInicio AND :dataFim "
                   + "GROUP BY data "
                   + "ORDER BY data";            
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dataInicio", dataInicio);       
        params.put("dataFim", dataFim);   
        return findAll(sql, params, saldoHandler);
    }

    public List<Saldo> findSaldoByConta(Integer idConta, Date dataInicio, Date dataFim) throws Exception {
        String sql = "SELECT idConta as id, DATE(max(lancamento.dataLancamento)) as data, "
                          + "SUM( IF(lancamento.tipo = 0, IFNULL(lancamento_rateio.valor, lancamento.valor), IFNULL(lancamento_rateio.valor, lancamento.valor) * -1) ) as valor "
                   + "FROM lancamento "
                   + "LEFT OUTER JOIN lancamento_rateio ON(lancamento.idLancamento = lancamento_rateio.idLancamento) "
                   + "WHERE DATE(lancamento.dataLancamento) "
                   + "BETWEEN IFNULL(:dataInicio, DATE(lancamento.dataLancamento)) AND IFNULL(:dataFim, DATE(lancamento.dataLancamento)) "
                   + "AND lancamento.idConta = IFNULL(:idConta, lancamento.idConta) "
                   + "GROUP BY idConta "
                   + "ORDER BY data";            
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dataInicio", dataInicio);       
        params.put("dataFim", dataFim);        
        params.put("idConta", idConta);   
        return findAll(sql, params, saldoHandler);
    }

    public List<Saldo> findSaldoPlanoConta(Date dataInicio, Date dataFim) throws Exception {
        String sql = "SELECT IFNULL(lancamento_rateio.idPlanoConta, lancamento.idPlanoConta) as id, "
                          + "DATE(lancamento.dataLancamento) as data, "
                          + "SUM(IFNULL(lancamento_rateio.valor, lancamento.valor)) as valor "
                   + "FROM lancamento "
                   + "LEFT OUTER JOIN lancamento_rateio ON(lancamento.idLancamento = lancamento_rateio.idLancamento) "
                   + "WHERE dataLancamento is not null AND DATE(dataLancamento) BETWEEN :dataInicio AND :dataFim "
                   + "AND lancamento.modelo NOT IN(1,3) "
                   + "GROUP BY id, data "
                   + "ORDER BY id, data";            
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dataInicio", dataInicio);       
        params.put("dataFim", dataFim);   
        return findAll(sql, params, saldoHandler);
    }  

    public List<Saldo> findSaldoTipoPlanoConta(Date dataInicio, Date dataFim, Integer tipo) throws Exception {
        String sql = "SELECT IFNULL(lancamento_rateio.idPlanoConta, lancamento.idPlanoConta) as id, "
                          + "DATE(lancamento.dataLancamento) as data, "
                          + "SUM(IFNULL(lancamento_rateio.valor, lancamento.valor)) as valor "
                   + "FROM lancamento "
                   + "LEFT OUTER JOIN lancamento_rateio ON(lancamento.idLancamento = lancamento_rateio.idLancamento) "
                   + "WHERE dataLancamento is not null AND DATE(dataLancamento) BETWEEN :dataInicio AND :dataFim "
                   + "AND lancamento.modelo NOT IN(1,3) AND tipo = :tipo "
                   + "GROUP BY id, data "
                   + "ORDER BY id, data";            
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dataInicio", dataInicio);       
        params.put("dataFim", dataFim);   
        params.put("tipo", tipo); 
        return findAll(sql, params, saldoHandler);
    }  

    public List<Saldo> findSaldoCentroCusto(Date dataInicio, Date dataFim) throws Exception {
        String sql = "SELECT IFNULL(lancamento_rateio.idCentroCusto, lancamento.idCentroCusto) as id, "
                          + "DATE(lancamento.dataLancamento) as data, "
                          + "SUM(IFNULL(lancamento_rateio.valor, lancamento.valor)) as valor "
                   + "FROM lancamento "
                   + "LEFT OUTER JOIN lancamento_rateio ON(lancamento.idLancamento = lancamento_rateio.idLancamento) "
                   + "WHERE dataLancamento is not null AND DATE(dataLancamento) BETWEEN :dataInicio AND :dataFim "
                   + "AND lancamento.modelo NOT IN(1,3) "
                   + "GROUP BY id, data "
                   + "ORDER BY id, data";            
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dataInicio", dataInicio);       
        params.put("dataFim", dataFim);   
        return findAll(sql, params, saldoHandler);
    }  

    public List<Saldo> findSaldoPlanoContaCompetencia(Date dataInicio, Date dataFim) throws Exception {
        String sql = "SELECT IFNULL(lancamento_rateio.idPlanoConta, lancamento.idPlanoConta) as id, "
                          + "DATE(lancamento.dataCompetencia) as data, "
                          + "SUM(IFNULL(lancamento_rateio.valor, lancamento.valor)) as valor "
                   + "FROM lancamento "
                   + "LEFT OUTER JOIN lancamento_rateio ON(lancamento.idLancamento = lancamento_rateio.idLancamento) "
                   + "WHERE dataCompetencia is not null AND DATE(dataCompetencia) BETWEEN :dataInicio AND :dataFim "
                   + "AND lancamento.modelo NOT IN(1,3) "
                   + "GROUP BY id, data "
                   + "ORDER BY id, data";            
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dataInicio", dataInicio);       
        params.put("dataFim", dataFim);   
        return findAll(sql, params, saldoHandler);
    }   

    public List<Saldo> findSaldoTipoPlanoContaCompetencia(Date dataInicio, Date dataFim, Integer tipo) throws Exception {
        String sql = "SELECT IFNULL(lancamento_rateio.idPlanoConta, lancamento.idPlanoConta) as id, "
                          + "DATE(lancamento.dataCompetencia) as data, "
                          + "SUM(IFNULL(lancamento_rateio.valor, lancamento.valor)) as valor "
                   + "FROM lancamento "
                   + "LEFT OUTER JOIN lancamento_rateio ON(lancamento.idLancamento = lancamento_rateio.idLancamento) "
                   + "WHERE dataCompetencia is not null AND DATE(dataCompetencia) BETWEEN :dataInicio AND :dataFim "
                   + "AND lancamento.modelo NOT IN(1,3) AND tipo = :tipo "
                   + "GROUP BY id, data "
                   + "ORDER BY id, data";            
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dataInicio", dataInicio);       
        params.put("dataFim", dataFim);   
        params.put("tipo", tipo); 
        return findAll(sql, params, saldoHandler);
    }  

    public List<Saldo> findSaldoCentroCustoCompetencia(Date dataInicio, Date dataFim) throws Exception {
        String sql = "SELECT IFNULL(lancamento_rateio.idCentroCusto, lancamento.idCentroCusto) as id, "
                          + "DATE(lancamento.dataCompetencia) as data, "
                          + "SUM(IFNULL(lancamento_rateio.valor, lancamento.valor)) as valor "
                   + "FROM lancamento "
                   + "LEFT OUTER JOIN lancamento_rateio ON(lancamento.idLancamento = lancamento_rateio.idLancamento) "
                   + "WHERE dataCompetencia is not null AND DATE(dataCompetencia) BETWEEN :dataInicio AND :dataFim "
                   + "AND lancamento.modelo NOT IN(1,3) "
                   + "GROUP BY id, data "
                   + "ORDER BY id, data";            
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dataInicio", dataInicio);       
        params.put("dataFim", dataFim);   
        return findAll(sql, params, saldoHandler);
    }     

    public List<Saldo> findSaldoTipo(Date dataInicio, Date dataFim) throws Exception {
        String sql = "SELECT lancamento.tipo as id, DATE(lancamento.dataLancamento) as data, "
                          + "SUM(IFNULL(lancamento_rateio.valor, lancamento.valor)) as valor "
                   + "FROM lancamento "
                   + "LEFT OUTER JOIN lancamento_rateio ON(lancamento.idLancamento = lancamento_rateio.idLancamento) "
                   + "WHERE DATE(dataLancamento) BETWEEN :dataInicio AND :dataFim "
                   + "AND lancamento.modelo NOT IN(1,3) "
                   + "GROUP BY lancamento.tipo, data " 
                   + "ORDER BY lancamento.tipo, data";     
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dataInicio", dataInicio);       
        params.put("dataFim", dataFim);   
        return findAll(sql, params, saldoHandler);
    }
    
    public List<Integer> findYearFromLancamento() throws Exception {
        String sql = "SELECT year(dataLancamento) ano FROM lancamento GROUP BY ano ORDER BY ano;";  
        return findAll(sql, null, Integer.class);
    }

    public Lancamento findLastByLancamentoProgramado(Integer idLancamentoProgramado) throws Exception {
        String sql = "SELECT idLancamento FROM lancamento lanc "
                   + "WHERE lanc.idLancamentoProgramado = :idLancamentoProgramado "
                   + "AND lanc.numeroParcela = (SELECT max(lanc1.numeroParcela) "
                                             + "FROM lancamento lanc1 "
                                             + "WHERE lanc1.idLancamentoProgramado = lanc.idLancamentoProgramado)";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoProgramado", idLancamentoProgramado);
        Integer idLancamento = (Integer) find(sql, params, Integer.class);
        return (Lancamento) find(idLancamento);
    }
    
    public Lancamento findByNumeroParcela(Integer idLancamentoProgramado, Integer numeroParcela) throws Exception {
        String sql = "SELECT * FROM lancamento "
                   + "WHERE idLancamentoProgramado is not null "
                   + "AND idLancamentoProgramado = :idLancamentoProgramado AND numeroParcela = :numeroParcela";  
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoProgramado", idLancamentoProgramado);
        params.put("numeroParcela", numeroParcela);
        return (Lancamento) find(sql, params, lancamentoHandler);
    }
    
    public void removeNumeroLoteConciliado(Date data) throws Exception {
        String sql = "UPDATE lancamento SET numeroLoteConciliado = null "
                   + "WHERE lancamento.dataLancamento >= :data ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("data", data);
        update(sql, params, lancamentoHandler);
    }

    public Lancamento save(Lancamento lancamento) throws Exception {  
        String sql = "INSERT INTO lancamento (idConta, idPlanoConta, idCentroCusto, idLancamentoProgramado, numeroLoteConciliado, "
                   + "tipo, idFavorecido, numero, numeroParcela, dataCompetencia, dataEmissao, dataVencimento, dataLancamento, "
                   + "dataCompensacao, valor, valorDesconto, valorJuros, valorMulta, situacao, modelo, autenticacao, historico, observacao, usuario) "
                   + "VALUES(:idConta, :idPlanoConta, :idCentroCusto, :idLancamentoProgramado, :numeroLoteConciliado, :tipo, :idFavorecido, "
                   + ":numero, :numeroParcela, :dataCompetencia, :dataEmissao, :dataVencimento, :dataLancamento, :dataCompensacao, "
                   + ":valor, :valorDesconto, :valorJuros, :valorMulta, :situacao, :modelo, :autenticacao, :historico, :observacao, :usuario)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idConta", lancamento.getConta().getIdConta());
        params.put("idPlanoConta", (lancamento.getPlanoConta() == null ? null : lancamento.getPlanoConta().getIdPlanoConta()));
        params.put("idCentroCusto", (lancamento.getCentroCusto()== null ? null : lancamento.getCentroCusto().getIdCentroCusto()));
        params.put("idLancamentoProgramado", (lancamento.getLancamentoProgramado() == null ? null : lancamento.getLancamentoProgramado().getIdLancamentoProgramado()));
        params.put("tipo", lancamento.getTipo().ordinal());     
        params.put("idFavorecido", (lancamento.getFavorecido() == null ? null : lancamento.getFavorecido().getIdFavorecido()));     
        params.put("numero", lancamento.getNumero());            
        params.put("numeroParcela", lancamento.getNumeroParcela());        
        params.put("dataCompetencia", lancamento.getDataCompetencia());                  
        params.put("dataEmissao", lancamento.getDataEmissao());      
        params.put("dataVencimento", lancamento.getDataVencimento());      
        params.put("dataLancamento", lancamento.getDataLancamento());      
        params.put("dataCompensacao", lancamento.getDataCompensacao());      
        params.put("valor", lancamento.getValor());       
        params.put("valorDesconto", lancamento.getValorDesconto());       
        params.put("valorJuros", lancamento.getValorJuros());       
        params.put("valorMulta", lancamento.getValorMulta());    
        params.put("situacao", lancamento.getSituacao().ordinal());  
        params.put("modelo", lancamento.getModelo().ordinal());  
        params.put("numeroLoteConciliado", lancamento.getNumeroLoteConciliado());
        params.put("autenticacao", lancamento.getAutenticacao());      
        params.put("historico", lancamento.getHistorico());      
        params.put("observacao", lancamento.getObservacao());           
        params.put("usuario", lancamento.getUsuario()); 
        Integer idLancamento = save(sql, params, lancamentoHandler);
        return find(idLancamento);
    }

    public Lancamento update(Lancamento lancamento) throws Exception {
        String sql = "UPDATE lancamento "
                   + "SET idConta = :idConta, idPlanoConta = :idPlanoConta, idCentroCusto = :idCentroCusto, idLancamentoProgramado = :idLancamentoProgramado, "
                   + "numeroLoteConciliado = :numeroLoteConciliado, tipo = :tipo, idFavorecido = :idFavorecido, numero = :numero, "
                   + "numeroParcela = :numeroParcela, dataCompetencia = :dataCompetencia, dataEmissao = :dataEmissao, dataVencimento = :dataVencimento, "
                   + "dataLancamento = :dataLancamento, dataCompensacao = :dataCompensacao, valor = :valor, "
                   + "valorDesconto = :valorDesconto, valorJuros = :valorJuros, valorMulta = :valorMulta, "
                   + "situacao = :situacao, modelo = :modelo, autenticacao = :autenticacao, historico = :historico, observacao = :observacao, usuario = :usuario "
                   + "WHERE idLancamento = :idLancamento ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamento", lancamento.getIdLancamento());
        params.put("idConta", lancamento.getConta().getIdConta());
        params.put("idPlanoConta", (lancamento.getPlanoConta() == null ? null : lancamento.getPlanoConta().getIdPlanoConta()));
        params.put("idCentroCusto", (lancamento.getCentroCusto()== null ? null : lancamento.getCentroCusto().getIdCentroCusto()));
        params.put("idLancamentoProgramado", (lancamento.getLancamentoProgramado()== null ? null : lancamento.getLancamentoProgramado().getIdLancamentoProgramado()));
        params.put("tipo", lancamento.getTipo().ordinal());     
        params.put("idFavorecido", (lancamento.getFavorecido() == null ? null : lancamento.getFavorecido().getIdFavorecido())); 
        params.put("numero", lancamento.getNumero());                   
        params.put("numeroParcela", lancamento.getNumeroParcela());               
        params.put("dataCompetencia", lancamento.getDataCompetencia());     
        params.put("dataEmissao", lancamento.getDataEmissao());      
        params.put("dataVencimento", lancamento.getDataVencimento());      
        params.put("dataLancamento", lancamento.getDataLancamento());      
        params.put("dataCompensacao", lancamento.getDataCompensacao());      
        params.put("valor", lancamento.getValor());          
        params.put("valorDesconto", lancamento.getValorDesconto());       
        params.put("valorJuros", lancamento.getValorJuros());       
        params.put("valorMulta", lancamento.getValorMulta());    
        params.put("situacao", lancamento.getSituacao().ordinal());  
        params.put("modelo", lancamento.getModelo().ordinal());  
        params.put("numeroLoteConciliado", lancamento.getNumeroLoteConciliado());
        params.put("autenticacao", lancamento.getAutenticacao());      
        params.put("historico", lancamento.getHistorico());     
        params.put("observacao", lancamento.getObservacao());           
        params.put("usuario", lancamento.getUsuario()); 
        update(sql, params, lancamentoHandler);
        return lancamento;  
    }

    public void updateNumeroLoteConciliado(Date data, Integer numeroLoteConciliado) throws Exception {
        String sql = "UPDATE lancamento SET numeroLoteConciliado = :numeroLoteConciliado "
                   + "WHERE lancamento.dataLancamento <= :data ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("data", data);
        params.put("numeroLoteConciliado", numeroLoteConciliado);
        update(sql, params, lancamentoHandler);
    }

    public Lancamento updateConta(Lancamento lancamento) throws Exception {
        String sql = "UPDATE lancamento SET idConta = :idConta, usuario = :usuario "
                   + "WHERE idLancamento = :idLancamento ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamento", lancamento.getIdLancamento());  
        params.put("idConta", lancamento.getConta().getIdConta());           
        params.put("usuario", lancamento.getUsuario()); 
        update(sql, params, lancamentoHandler);
        return lancamento;  
    }

    public Lancamento updateSituacao(Lancamento lancamento) throws Exception {
        String sql = "UPDATE lancamento SET situacao = :situacao, usuario = :usuario "
                   + "WHERE idLancamento = :idLancamento ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamento", lancamento.getIdLancamento());  
        params.put("situacao", lancamento.getSituacao().ordinal());           
        params.put("usuario", lancamento.getUsuario()); 
        update(sql, params, lancamentoHandler);
        return lancamento;  
    }

    public Lancamento remove(Integer idLancamento) throws Exception { 
        String sql = "DELETE FROM lancamento WHERE idLancamento = :idLancamento ";
        Lancamento lancamento = find(idLancamento);
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idLancamento", idLancamento);
        remove(sql, params, lancamentoHandler);
        return lancamento;
    }

    public void removeAllPlanoConta() throws Exception { 
        String sql = "UPDATE lancamento SET idPlanoConta = null ";
        remove(sql, null, lancamentoHandler);
    }
}
