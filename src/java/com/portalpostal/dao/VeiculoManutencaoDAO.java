package com.portalpostal.dao;

import Controle.ContrErroLog;
import Util.Sql2oConnexao;
import com.portalpostal.dao.handler.ManutencaoHandler;
import com.portalpostal.dao.handler.VeiculoManutencaoHandler;
import com.portalpostal.model.VeiculoManutencao;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.Connection;

public class VeiculoManutencaoDAO {   
    
//    private Connection connection;
    private ManutencaoHandler manutencaoHandler;
    private VeiculoManutencaoHandler veiculoManutencaoHandler;
    private String nomeBD;

    public VeiculoManutencaoDAO(String nomeBD) { 
//        connection = Sql2oConnexao.getInstance(nomeBD, this.getClass());
        manutencaoHandler = new ManutencaoHandler();
        veiculoManutencaoHandler = new VeiculoManutencaoHandler();
        this.nomeBD = nomeBD;
    } 

    public List<VeiculoManutencao> findAll() {
        String sql = "SELECT * FROM veiculo_manutencao, veiculo WHERE veiculo.idVeiculo = veiculo_manutencao.idVeiculo "
                   + "ORDER BY veiculo_manutencao.idVeiculo, veiculo_manutencao.dataManutencao";
        Connection connection = Sql2oConnexao.getInstance(nomeBD, this.getClass());
        try {              
            return connection.createQuery(sql)
                    .executeAndFetch(veiculoManutencaoHandler);
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculoManutencao", "SQLException", sql, e.toString());
        } finally {
            connection.close();
            System.out.println("close concetion " + Sql2oConnexao.contConn + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
        return new ArrayList<>();
    }

    public VeiculoManutencao find(Integer idVeiculoManutencao) throws Exception {
        String sql = "SELECT * FROM veiculo_manutencao, veiculo "
                   + "WHERE veiculo.idVeiculo = veiculo_manutencao.idVeiculo AND idVeiculoManutencao = :idVeiculoManutencao";
        Connection connection = Sql2oConnexao.getInstance(nomeBD, this.getClass());
        try {              
            return connection.createQuery(sql)                
                    .addParameter("idVeiculoManutencao", idVeiculoManutencao)
                    .executeAndFetchFirst(veiculoManutencaoHandler);
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculoManutencao", "SQLException", sql, e.toString());
        } finally {
            connection.close();
            System.out.println("close concetion " + Sql2oConnexao.contConn + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
        return null;
    }

    public VeiculoManutencao save(VeiculoManutencao veiculo) throws Exception {        
        String sql = "INSERT INTO veiculo_manutencao (idVeiculo, tipo, quilometragem, valor, dataManutencao, dataAgendamento, descricao) "
                   + "VALUES(:idVeiculo, :tipo, :quilometragem, :valor, :dataManutencao, :dataAgendamento, :descricao)";
        Connection connection = Sql2oConnexao.getInstance(nomeBD, this.getClass());
        try {
            Integer idVeiculo = connection.createQuery(sql, true)
                    .addParameter("idVeiculo", veiculo.getVeiculo().getIdVeiculo())
                    .addParameter("tipo", veiculo.getTipo().ordinal())
                    .addParameter("quilometragem", veiculo.getQuilometragem())
                    .addParameter("valor", veiculo.getValor())
                    .addParameter("dataManutencao", veiculo.getDataManutencao())
                    .addParameter("dataAgendamento", veiculo.getDataAgendamento())
                    .addParameter("descricao", veiculo.getDescricao())
                    .executeUpdate().getKey(Integer.class); 
            return find(idVeiculo);
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculoManutencao", "SQLException", sql, e.toString());
        } finally {
            connection.close();
            System.out.println("close concetion " + Sql2oConnexao.contConn + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
        return null;
    }

    public VeiculoManutencao update(VeiculoManutencao veiculo) throws Exception {        
        String sql = "UPDATE veiculo_manutencao "
                   + "SET idVeiculo = :idVeiculo, tipo = :tipo, quilometragem = :quilometragem, valor = :valor, dataManutencao = :dataManutencao, "
                   + "dataAgendamento = :dataAgendamento, descricao = :descricao "
                   + "WHERE idVeiculoManutencao = :idVeiculoManutencao ";
        Connection connection = Sql2oConnexao.getInstance(nomeBD, this.getClass());
        try {
            connection.createQuery(sql)
                .addParameter("idVeiculoManutencao", veiculo.getIdVeiculoManutencao())
                .addParameter("idVeiculo", veiculo.getVeiculo().getIdVeiculo())
                .addParameter("tipo", veiculo.getTipo().ordinal())
                .addParameter("quilometragem", veiculo.getQuilometragem())
                .addParameter("valor", veiculo.getValor())
                .addParameter("dataManutencao", veiculo.getDataManutencao())
                .addParameter("dataAgendamento", veiculo.getDataAgendamento())
                .addParameter("descricao", veiculo.getDescricao())  
                .executeUpdate();          
            return veiculo;
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculoManutencao", "SQLException", sql, e.toString());
        } finally {
            connection.close();
            System.out.println("close concetion " + Sql2oConnexao.contConn + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
        return null;
    }

    public VeiculoManutencao remove(Integer idVeiculoManutencao) throws Exception { 
        String sql = "DELETE FROM veiculo_manutencao WHERE idVeiculoManutencao = :idVeiculoManutencao ";
        Connection connection = Sql2oConnexao.getInstance(nomeBD, this.getClass());
        try {
            VeiculoManutencao veiculo = find(idVeiculoManutencao);
            connection.createQuery(sql)
                .addParameter("idVeiculoManutencao", idVeiculoManutencao)
                .executeUpdate();  
            return veiculo;
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculoManutencao", "SQLException", sql, e.toString());
        } finally {
            connection.close();
            System.out.println("close concetion " + Sql2oConnexao.contConn + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
        return null;
    }    

    public List<VeiculoManutencao> findByIdVeiculo(Integer idVeiculo) throws Exception {
        String sql = "SELECT * FROM veiculo_manutencao WHERE idVeiculo = :idVeiculo";
        Connection connection = Sql2oConnexao.getInstance(nomeBD, this.getClass());
        try {              
            return connection.createQuery(sql)                
                    .addParameter("idVeiculo", idVeiculo)
                    .executeAndFetch(manutencaoHandler);
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculoManutencao", "SQLException", sql, e.toString());
        } finally {
            connection.close();
            System.out.println("close concetion " + Sql2oConnexao.contConn + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
        return null;
    }
}
