package com.portalpostal.dao;

import Controle.ContrErroLog;
import Util.Sql2oConnexao;
import com.portalpostal.dao.handler.MultaHandler;
import com.portalpostal.dao.handler.VeiculoMultaHandler;
import com.portalpostal.model.VeiculoMulta;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.Connection;

public class VeiculoMultaDAO {   
    
//    private Connection connection;
    private MultaHandler multaHandler;
    private VeiculoMultaHandler veiculoMultaHandler;
    private String nomeBD;

    public VeiculoMultaDAO(String nomeBD) { 
//        connection = Sql2oConnexao.getInstance(nomeBD, this.getClass());
        multaHandler = new MultaHandler();
        veiculoMultaHandler = new VeiculoMultaHandler();
        this.nomeBD = nomeBD;
    } 

    public List<VeiculoMulta> findAll() {
        String sql = "SELECT * FROM veiculo_multa, veiculo WHERE veiculo.idVeiculo = veiculo_multa.idVeiculo "
                   + "ORDER BY veiculo_multa.idVeiculo, veiculo_multa.data";
        Connection connection = Sql2oConnexao.getInstance(nomeBD, this.getClass());
        try {              
            return connection.createQuery(sql)
                    .executeAndFetch(veiculoMultaHandler);
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculoMulta", "SQLException", sql, e.toString());
        } finally {
            connection.close();
            System.out.println("close concetion " + Sql2oConnexao.contConn + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
        return new ArrayList<>();
    }

    public VeiculoMulta find(Integer idVeiculoMulta) throws Exception {
        String sql = "SELECT * FROM veiculo_multa, veiculo "
                   + "WHERE veiculo.idVeiculo = veiculo_multa.idVeiculo AND idVeiculoMulta = :idVeiculoMulta";
        Connection connection = Sql2oConnexao.getInstance(nomeBD, this.getClass());
        try {              
            return connection.createQuery(sql)                
                    .addParameter("idVeiculoMulta", idVeiculoMulta)
                    .executeAndFetchFirst(veiculoMultaHandler);
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculoMulta", "SQLException", sql, e.toString());
        } finally {
            connection.close();
            System.out.println("close concetion " + Sql2oConnexao.contConn + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
        return null;
    }

    public VeiculoMulta save(VeiculoMulta veiculo) throws Exception {        
        String sql = "INSERT INTO veiculo_multa (idVeiculo, condutor, numero, data, valor, descontada, local, descricao) "
                   + "VALUES(:idVeiculo, :condutor, :numero, :data, :valor, :descontada, :local, :descricao)";
        Connection connection = Sql2oConnexao.getInstance(nomeBD, this.getClass());
        try {
            Integer idVeiculo = connection.createQuery(sql, true)
                    .addParameter("idVeiculo", veiculo.getVeiculo().getIdVeiculo())
                    .addParameter("condutor", veiculo.getCondutor())
                    .addParameter("numero", veiculo.getNumero())
                    .addParameter("data", veiculo.getData())
                    .addParameter("valor", veiculo.getValor())
                    .addParameter("descontada", veiculo.getDescontada())
                    .addParameter("local", veiculo.getLocal())
                    .addParameter("descricao", veiculo.getDescricao())
                    .executeUpdate().getKey(Integer.class); 
            return find(idVeiculo);
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculoMulta", "SQLException", sql, e.toString());
        } finally {
            connection.close();
            System.out.println("close concetion " + Sql2oConnexao.contConn + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
        return null;
    }

    public VeiculoMulta update(VeiculoMulta veiculo) throws Exception {        
        String sql = "UPDATE veiculo_multa "
                   + "SET idVeiculo = :idVeiculo, condutor = :condutor, numero = :numero, data = :data, valor = :valor, descontada = :descontada, "
                   + "local = :local, descricao = :descricao "
                   + "WHERE idVeiculoMulta = :idVeiculoMulta ";
        Connection connection = Sql2oConnexao.getInstance(nomeBD, this.getClass());
        try {
            connection.createQuery(sql)
                .addParameter("idVeiculoMulta", veiculo.getIdVeiculoMulta())
                .addParameter("idVeiculo", veiculo.getVeiculo().getIdVeiculo())
                .addParameter("condutor", veiculo.getCondutor())
                .addParameter("numero", veiculo.getNumero())
                .addParameter("data", veiculo.getData())
                .addParameter("valor", veiculo.getValor())
                .addParameter("descontada", veiculo.getDescontada())
                .addParameter("local", veiculo.getLocal())
                .addParameter("descricao", veiculo.getDescricao()) 
                .executeUpdate();          
            return veiculo;
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculoMulta", "SQLException", sql, e.toString());
        } finally {
            connection.close();
            System.out.println("close concetion " + Sql2oConnexao.contConn + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
        return null;
    }

    public VeiculoMulta remove(Integer idVeiculoMulta) throws Exception { 
        String sql = "DELETE FROM veiculo_multa WHERE idVeiculoMulta = :idVeiculoMulta ";
        Connection connection = Sql2oConnexao.getInstance(nomeBD, this.getClass());
        try {
            VeiculoMulta veiculo = find(idVeiculoMulta);
            connection.createQuery(sql)
                .addParameter("idVeiculoMulta", idVeiculoMulta)
                .executeUpdate();  
            return veiculo;
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculoMulta", "SQLException", sql, e.toString());
        } finally {
            connection.close();
            System.out.println("close concetion " + Sql2oConnexao.contConn + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
        return null;
    }

    public List<VeiculoMulta> findByIdVeiculo(Integer idVeiculo) {
        String sql = "SELECT * FROM veiculo_multa WHERE idVeiculo = :idVeiculo";
        Connection connection = Sql2oConnexao.getInstance(nomeBD, this.getClass());
        try {              
            return connection.createQuery(sql)               
                    .addParameter("idVeiculo", idVeiculo)
                    .executeAndFetch(multaHandler);
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculoMulta", "SQLException", sql, e.toString());
        } finally {
            connection.close();
            System.out.println("close concetion " + Sql2oConnexao.contConn + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
        return new ArrayList<>();
    }
}
