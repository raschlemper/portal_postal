package com.portalpostal.dao;

import com.portalpostal.dao.handler.SeguroHandler;
import com.portalpostal.model.VeiculoSeguro;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VeiculoSeguroDAO extends GenericDAO {   
    
    private SeguroHandler seguroHandler;

    public VeiculoSeguroDAO(String nameDB) { 
        super(nameDB, VeiculoSeguroDAO.class);
        seguroHandler = new SeguroHandler();
    } 

    public List<VeiculoSeguro> findAll() throws Exception {
        String sql = "SELECT * FROM veiculo_seguro, veiculo WHERE veiculo.idVeiculo = veiculo_seguro.idVeiculo "
                   + "ORDER BY veiculo_seguro.idVeiculo";
        return findAll(sql, null, seguroHandler);
    }

    public VeiculoSeguro find(Integer idVeiculoSeguro) throws Exception {
        String sql = "SELECT * FROM veiculo_seguro, veiculo "
                   + "WHERE veiculo.idVeiculo = veiculo_seguro.idVeiculo AND idVeiculoSeguro = :idVeiculoSeguro ";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idVeiculoSeguro", idVeiculoSeguro);
        return (VeiculoSeguro) find(sql, params, seguroHandler);
    }

    public VeiculoSeguro save(VeiculoSeguro veiculo) throws Exception { 
        String sql = "INSERT INTO veiculo_seguro (idVeiculo, numeroApolice, corretora, assegurado, valorFranquia, indenizacao, "
                   + "dataInicioVigencia, dataFimVigencia) "
                   + "VALUES(:idVeiculo, :numeroApolice, :corretora, :assegurado, :valorFranquia, :indenizacao, :dataInicioVigencia, "
                   + ":dataFimVigencia)";     
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idVeiculo", veiculo.getVeiculo().getIdVeiculo());
        params.put("numeroApolice", veiculo.getNumeroApolice());
        params.put("corretora", veiculo.getCorretora());
        params.put("assegurado", veiculo.getAssegurado());
        params.put("valorFranquia", veiculo.getValorFranquia());
        params.put("indenizacao", veiculo.getIndenizacao().ordinal());
        params.put("dataInicioVigencia", veiculo.getDataInicioVigencia());
        params.put("dataFimVigencia", veiculo.getDataFimVigencia());
        Integer idVeiculoSeguro = save(sql, params, seguroHandler);
        return find(idVeiculoSeguro);
    }

    public VeiculoSeguro update(VeiculoSeguro veiculo) throws Exception { 
        String sql = "UPDATE veiculo_seguro "
                   + "SET idVeiculo = :idVeiculo, numeroApolice = :numeroApolice, corretora = :corretora, assegurado = :assegurado, "
                   + "valorFranquia = :valorFranquia, indenizacao = :indenizacao, dataInicioVigencia = :dataInicioVigencia, "
                   + "dataFimVigencia = :dataFimVigencia "
                   + "WHERE idVeiculoSeguro = :idVeiculoSeguro ";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idVeiculoSeguro", veiculo.getIdVeiculoSeguro());
        params.put("idVeiculo", veiculo.getVeiculo().getIdVeiculo());
        params.put("numeroApolice", veiculo.getNumeroApolice());
        params.put("corretora", veiculo.getCorretora());
        params.put("assegurado", veiculo.getAssegurado());
        params.put("valorFranquia", veiculo.getValorFranquia());
        params.put("indenizacao", veiculo.getIndenizacao().ordinal());
        params.put("dataInicioVigencia", veiculo.getDataInicioVigencia());
        params.put("dataFimVigencia", veiculo.getDataFimVigencia());
        update(sql, params, seguroHandler);
        return veiculo;  
    }

    public VeiculoSeguro remove(Integer idVeiculoSeguro) throws Exception { 
        String sql = "DELETE FROM veiculo_seguro WHERE idVeiculoSeguro = :idVeiculoSeguro ";
        VeiculoSeguro veiculo = find(idVeiculoSeguro);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idVeiculoSeguro", idVeiculoSeguro);
        remove(sql, params, seguroHandler);
        return veiculo;
    }

    public List<VeiculoSeguro> findByIdVeiculo(Integer idVeiculo) throws Exception {
        String sql = "SELECT * FROM veiculo_seguro WHERE idVeiculo = :idVeiculo";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idVeiculo", idVeiculo);
        return findAll(sql, params, seguroHandler);
    } 

    public List<VeiculoSeguro> findNotRenewByRangeDate(Date dataInicio, Date dataFim) throws Exception {
        String sql = "SELECT * FROM veiculo_seguro, veiculo "
                   + "WHERE veiculo.idVeiculo = veiculo_seguro.idVeiculo "
                   + "AND veiculo_seguro.dataFimVigencia between :dataInicio and :dataFim";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dataInicio", dataInicio);
        params.put("dataFim", dataFim);
        return findAll(sql, params, seguroHandler);
    }
}
