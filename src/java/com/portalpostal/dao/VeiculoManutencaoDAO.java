package com.portalpostal.dao;

import Controle.ContrErroLog;
import Util.Sql2oConnexao;
import com.portalpostal.dao.handler.ManutencaoHandler;
import com.portalpostal.dao.handler.VeiculoManutencaoHandler;
import com.portalpostal.model.VeiculoManutencao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.sql2o.Connection;

public class VeiculoManutencaoDAO extends GenericDAO {   
    
    private ManutencaoHandler manutencaoHandler;
    private VeiculoManutencaoHandler veiculoManutencaoHandler;

    public VeiculoManutencaoDAO(String nameDB) { 
        super(nameDB, VeiculoManutencaoDAO.class);
        manutencaoHandler = new ManutencaoHandler();
        veiculoManutencaoHandler = new VeiculoManutencaoHandler();
    } 

    public List<VeiculoManutencao> findAll() throws Exception {
        String sql = "SELECT * FROM veiculo_manutencao, veiculo WHERE veiculo.idVeiculo = veiculo_manutencao.idVeiculo "
                   + "ORDER BY veiculo_manutencao.idVeiculo, veiculo_manutencao.dataManutencao";
        return findAll(sql, null, veiculoManutencaoHandler);
    }

    public VeiculoManutencao find(Integer idVeiculoManutencao) throws Exception {
        String sql = "SELECT * FROM veiculo_manutencao, veiculo "
                   + "WHERE veiculo.idVeiculo = veiculo_manutencao.idVeiculo AND idVeiculoManutencao = :idVeiculoManutencao";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idVeiculoManutencao", idVeiculoManutencao);
        return (VeiculoManutencao) find(sql, params, veiculoManutencaoHandler);
    }

    public VeiculoManutencao save(VeiculoManutencao veiculo) throws Exception {    
        String sql = "INSERT INTO veiculo_manutencao (idVeiculo, tipo, quilometragem, valor, dataManutencao, dataAgendamento, descricao) "
                   + "VALUES(:idVeiculo, :tipo, :quilometragem, :valor, :dataManutencao, :dataAgendamento, :descricao)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idVeiculo", veiculo.getVeiculo().getIdVeiculo());
        params.put("tipo", veiculo.getTipo().ordinal());
        params.put("quilometragem", veiculo.getQuilometragem());
        params.put("valor", veiculo.getValor());
        params.put("dataManutencao", veiculo.getDataManutencao());
        params.put("dataAgendamento", veiculo.getDataAgendamento());
        params.put("descricao", veiculo.getDescricao());
        Integer idVeiculoManutencao = save(sql, params, veiculoManutencaoHandler);
        return find(idVeiculoManutencao);
    }

    public VeiculoManutencao update(VeiculoManutencao veiculo) throws Exception { 
        String sql = "UPDATE veiculo_manutencao "
                   + "SET idVeiculo = :idVeiculo, tipo = :tipo, quilometragem = :quilometragem, valor = :valor, dataManutencao = :dataManutencao, "
                   + "dataAgendamento = :dataAgendamento, descricao = :descricao "
                   + "WHERE idVeiculoManutencao = :idVeiculoManutencao ";     
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idVeiculoManutencao", veiculo.getIdVeiculoManutencao());
        params.put("idVeiculo", veiculo.getVeiculo().getIdVeiculo());
        params.put("tipo", veiculo.getTipo().ordinal());
        params.put("quilometragem", veiculo.getQuilometragem());
        params.put("valor", veiculo.getValor());
        params.put("dataManutencao", veiculo.getDataManutencao());
        params.put("dataAgendamento", veiculo.getDataAgendamento());
        params.put("descricao", veiculo.getDescricao());      
        update(sql, params, veiculoManutencaoHandler);
        return veiculo;  
    }

    public VeiculoManutencao remove(Integer idVeiculoManutencao) throws Exception { 
        String sql = "DELETE FROM veiculo_manutencao WHERE idVeiculoManutencao = :idVeiculoManutencao ";
        VeiculoManutencao veiculo = find(idVeiculoManutencao);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idVeiculoManutencao", idVeiculoManutencao);
        remove(sql, params, veiculoManutencaoHandler);
        return veiculo;
    }    

    public List<VeiculoManutencao> findByIdVeiculo(Integer idVeiculo) throws Exception {
        String sql = "SELECT * FROM veiculo_manutencao WHERE idVeiculo = :idVeiculo";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idVeiculo", idVeiculo);
        return findAll(sql, params, manutencaoHandler);
    }
}
