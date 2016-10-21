package com.portalpostal.dao;

import com.portalpostal.dao.handler.LancamentoProgramadoParcelaHandler;
import com.portalpostal.model.LancamentoProgramadoParcela;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LancamentoProgramadoParcelaDAO extends GenericDAO { 
    
    private final LancamentoProgramadoParcelaHandler lancamentoProgramadoParcelaHandler;

    public LancamentoProgramadoParcelaDAO(String nameDB) { 
        super(nameDB, LancamentoProgramadoParcelaDAO.class);
        lancamentoProgramadoParcelaHandler = new LancamentoProgramadoParcelaHandler();
    } 

    public List<LancamentoProgramadoParcela> findAll() throws Exception {
        String sql = "SELECT * FROM lancamento_programado_parcela "
                   + "LEFT OUTER JOIN plano_conta ON(lancamento_programado_parcela.idPlanoConta = plano_conta.idPlanoConta) "
                   + "LEFT OUTER JOIN centro_custo ON(lancamento_programado_parcela.idCentroCusto = centro_custo.idCentroCusto) "
                   + "LEFT OUTER JOIN lancamento ON(lancamento_programado_parcela.idLancamento = lancamento.idLancamento) "
                   + "ORDER BY lancamento_programado_parcela.idLancamentoProgramadoParcela";        
        return findAll(sql, null, lancamentoProgramadoParcelaHandler);
    }

    public LancamentoProgramadoParcela find(Integer idLancamentoProgramadoParcela) throws Exception {
        String sql = "SELECT * FROM lancamento_programado_parcela "
                   + "LEFT OUTER JOIN plano_conta ON(lancamento_programado_parcela.idPlanoConta = plano_conta.idPlanoConta) "
                   + "LEFT OUTER JOIN centro_custo ON(lancamento_programado_parcela.idCentroCusto = centro_custo.idCentroCusto) "
                   + "LEFT OUTER JOIN lancamento ON(lancamento_programado_parcela.idLancamento = lancamento.idLancamento) "
                   + "WHERE lancamento_programado_parcela.idLancamentoProgramadoParcela = :idLancamentoProgramadoParcela";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoProgramadoParcela", idLancamentoProgramadoParcela);
        return (LancamentoProgramadoParcela) find(sql, params, lancamentoProgramadoParcelaHandler);
    }

    public List<LancamentoProgramadoParcela> findByLancamentoProgramado(Integer idLancamentoProgramado) throws Exception {
        String sql = "SELECT * FROM lancamento_programado_parcela "
                   + "LEFT OUTER JOIN plano_conta ON(lancamento_programado_parcela.idPlanoConta = plano_conta.idPlanoConta) "
                   + "LEFT OUTER JOIN centro_custo ON(lancamento_programado_parcela.idCentroCusto = centro_custo.idCentroCusto) "
                   + "LEFT OUTER JOIN lancamento ON(lancamento_programado_parcela.idLancamento = lancamento.idLancamento) "
                   + "WHERE lancamento_programado_parcela.idLancamentoProgramado = :idLancamentoProgramado";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoProgramado", idLancamentoProgramado);
        return findAll(sql, params, lancamentoProgramadoParcelaHandler);
    }

    public LancamentoProgramadoParcela save(LancamentoProgramadoParcela lancamentoProgramadoParcela) throws Exception {  
        String sql = "INSERT INTO lancamento_programado_parcela (idPlanoConta, idCentroCusto, idLancamentoProgramado, idLancamento, numero, dataVencimento, valor) "
                   + "VALUES(:idPlanoConta, :idCentroCusto, :idLancamentoProgramado, :idLancamento, :numero, :dataVencimento, :valor)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idPlanoConta", (lancamentoProgramadoParcela.getPlanoConta() == null ? null : lancamentoProgramadoParcela.getPlanoConta().getIdPlanoConta()));
        params.put("idCentroCusto", (lancamentoProgramadoParcela.getCentroCusto()== null ? null : lancamentoProgramadoParcela.getCentroCusto().getIdCentroCusto()));
        params.put("idLancamentoProgramado", (lancamentoProgramadoParcela.getLancamentoProgramado()== null ? null : lancamentoProgramadoParcela.getLancamentoProgramado().getIdLancamentoProgramado()));
        params.put("idLancamento", (lancamentoProgramadoParcela.getLancamento()== null ? null : lancamentoProgramadoParcela.getLancamento().getIdLancamento()));
        params.put("numero", lancamentoProgramadoParcela.getNumero());      
        params.put("dataVencimento", lancamentoProgramadoParcela.getDataVencimento()); 
        params.put("valor", lancamentoProgramadoParcela.getValor()); 
        Integer idLancamentoProgramadoParcela = save(sql, params, lancamentoProgramadoParcelaHandler);
        return find(idLancamentoProgramadoParcela);
    }

    public LancamentoProgramadoParcela update(LancamentoProgramadoParcela lancamentoProgramadoParcela) throws Exception {
        String sql = "UPDATE lancamento_programado_parcela "
                   + "SET idPlanoConta = :idPlanoConta, idCentroCusto = :idCentroCusto, idLancamentoProgramado = :idLancamentoProgramado, "
                   + " idLancamento = :idLancamento, numero = :numero, dataVencimento = :dataVencimento, valor = :valor "
                   + "WHERE idLancamentoProgramadoParcela = :idLancamentoProgramadoParcela ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idLancamentoProgramadoParcela", lancamentoProgramadoParcela.getIdLancamentoProgramadoParcela());
        params.put("idPlanoConta", (lancamentoProgramadoParcela.getPlanoConta() == null ? null : lancamentoProgramadoParcela.getPlanoConta().getIdPlanoConta()));
        params.put("idCentroCusto", (lancamentoProgramadoParcela.getCentroCusto()== null ? null : lancamentoProgramadoParcela.getCentroCusto().getIdCentroCusto()));
        params.put("idLancamentoProgramado", (lancamentoProgramadoParcela.getLancamentoProgramado()== null ? null : lancamentoProgramadoParcela.getLancamentoProgramado().getIdLancamentoProgramado()));
        params.put("idLancamento", (lancamentoProgramadoParcela.getLancamento()== null ? null : lancamentoProgramadoParcela.getLancamento().getIdLancamento()));
        params.put("numero", lancamentoProgramadoParcela.getNumero());      
        params.put("dataVencimento", lancamentoProgramadoParcela.getDataVencimento()); 
        params.put("valor", lancamentoProgramadoParcela.getValor()); 
        update(sql, params, lancamentoProgramadoParcelaHandler);
        return lancamentoProgramadoParcela;  
    }

    public void removeLancamento(Integer idLancamento) throws Exception { 
        String sql = "UPDATE lancamento_programado_parcela SET idLancamento = null WHERE idLancamento = :idLancamento ";
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idLancamento", idLancamento);
        update(sql, params, lancamentoProgramadoParcelaHandler);
    }

    public LancamentoProgramadoParcela remove(Integer idLancamentoProgramadoParcela) throws Exception { 
        String sql = "DELETE FROM lancamento_programado_parcela WHERE idLancamentoProgramadoParcela = :idLancamentoProgramadoParcela ";
        LancamentoProgramadoParcela lancamentoProgramadoParcela = find(idLancamentoProgramadoParcela);
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idLancamentoProgramadoParcela", idLancamentoProgramadoParcela);
        remove(sql, params, lancamentoProgramadoParcelaHandler);
        return lancamentoProgramadoParcela;
    }

    public void removeAllPlanoConta() throws Exception { 
        String sql = "UPDATE lancamento_programado_parcela SET idPlanoConta = null ";
        remove(sql, null, lancamentoProgramadoParcelaHandler);
    }
    
}
