package com.portalpostal.dao;

import Controle.ContrErroLog;
import Util.Sql2oConnexao;
import com.portalpostal.dao.handler.SeguroHandler;
import com.portalpostal.dao.handler.VeiculoSeguroHandler;
import com.portalpostal.model.VeiculoSeguro;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.Connection;

public class VeiculoSeguroDAO {   
    
//    private Connection connection;
    private SeguroHandler seguroHandler;
    private VeiculoSeguroHandler veiculoSeguroHandler;
    private String nomeBD;

    public VeiculoSeguroDAO(String nomeBD) { 
//        connection = Sql2oConnexao.getInstance(nomeBD, this.getClass());
        seguroHandler = new SeguroHandler();
        veiculoSeguroHandler = new VeiculoSeguroHandler();
        this.nomeBD = nomeBD;
    } 

    public List<VeiculoSeguro> findAll() {
        String sql = "SELECT * FROM veiculo_seguro, veiculo WHERE veiculo.idVeiculo = veiculo_seguro.idVeiculo "
                   + "ORDER BY veiculo_seguro.idVeiculo";
        Connection connection = Sql2oConnexao.getInstance(nomeBD, this.getClass());
        try {              
            return connection.createQuery(sql)
                    .executeAndFetch(veiculoSeguroHandler);
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculoSeguro", "SQLException", sql, e.toString());
        } finally {
            connection.close();
            System.out.println("close concetion " + Sql2oConnexao.contConn + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
        return new ArrayList<>();
    }

    public VeiculoSeguro find(Integer idVeiculoSeguro) throws Exception {
        String sql = "SELECT * FROM veiculo_seguro, veiculo "
                   + "WHERE veiculo.idVeiculo = veiculo_seguro.idVeiculo AND idVeiculoSeguro = :idVeiculoSeguro ";
        Connection connection = Sql2oConnexao.getInstance(nomeBD, this.getClass());
        try {              
            return connection.createQuery(sql)                
                    .addParameter("idVeiculoSeguro", idVeiculoSeguro)
                    .executeAndFetchFirst(veiculoSeguroHandler);
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculoSeguro", "SQLException", sql, e.toString());
        } finally {
            connection.close();
            System.out.println("close concetion " + Sql2oConnexao.contConn + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
        return null;
    }

    public VeiculoSeguro save(VeiculoSeguro veiculo) throws Exception {        
        String sql = "INSERT INTO veiculo_seguro (idVeiculo, numeroApolice, corretora, assegurado, valorFranquia, indenizacao, dataInicioVigencia, dataFimVigencia) "
                   + "VALUES(:idVeiculo, :numeroApolice, :corretora, :assegurado, :valorFranquia, :indenizacao, :dataInicioVigencia, :dataFimVigencia)";
        Connection connection = Sql2oConnexao.getInstance(nomeBD, this.getClass());
        try {
            Integer idVeiculo = connection.createQuery(sql, true)
                    .addParameter("idVeiculo", veiculo.getVeiculo().getIdVeiculo())
                    .addParameter("numeroApolice", veiculo.getNumeroApolice())
                    .addParameter("corretora", veiculo.getCorretora())
                    .addParameter("assegurado", veiculo.getAssegurado())
                    .addParameter("valorFranquia", veiculo.getValorFranquia())
                    .addParameter("indenizacao", veiculo.getIndenizacao().ordinal())
                    .addParameter("dataInicioVigencia", veiculo.getDataInicioVigencia())
                    .addParameter("dataFimVigencia", veiculo.getDataFimVigencia())
                    .executeUpdate().getKey(Integer.class); 
            return find(idVeiculo);
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculoSeguro", "SQLException", sql, e.toString());
        } finally {
            connection.close();
            System.out.println("close concetion " + Sql2oConnexao.contConn + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
        return null;
    }

    public VeiculoSeguro update(VeiculoSeguro veiculo) throws Exception {        
        String sql = "UPDATE veiculo_seguro "
                   + "SET idVeiculo = :idVeiculo, numeroApolice = :numeroApolice, corretora = :corretora, assegurado = :assegurado, valorFranquia = :valorFranquia, "
                   + "indenizacao = :indenizacao, dataInicioVigencia = :dataInicioVigencia, dataFimVigencia = :dataFimVigencia "
                   + "WHERE idVeiculoSeguro = :idVeiculoSeguro ";
        Connection connection = Sql2oConnexao.getInstance(nomeBD, this.getClass());
        try {
            connection.createQuery(sql)
                .addParameter("idVeiculoSeguro", veiculo.getIdVeiculoSeguro())
                .addParameter("idVeiculo", veiculo.getVeiculo().getIdVeiculo())
                .addParameter("numeroApolice", veiculo.getNumeroApolice())
                .addParameter("corretora", veiculo.getCorretora())
                .addParameter("assegurado", veiculo.getAssegurado())
                .addParameter("valorFranquia", veiculo.getValorFranquia())
                .addParameter("indenizacao", veiculo.getIndenizacao().ordinal())
                .addParameter("dataInicioVigencia", veiculo.getDataInicioVigencia())
                .addParameter("dataFimVigencia", veiculo.getDataFimVigencia())
                .executeUpdate();          
            return veiculo;
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculoSeguro", "SQLException", sql, e.toString());
        } finally {
            connection.close();
            System.out.println("close concetion " + Sql2oConnexao.contConn + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
        return null;
    }

    public VeiculoSeguro remove(Integer idVeiculoSeguro) throws Exception { 
        String sql = "DELETE FROM veiculo_seguro WHERE idVeiculoSeguro = :idVeiculoSeguro ";
        Connection connection = Sql2oConnexao.getInstance(nomeBD, this.getClass());
        try {
            VeiculoSeguro veiculo = find(idVeiculoSeguro);
            connection.createQuery(sql)
                .addParameter("idVeiculoSeguro", idVeiculoSeguro)
                .executeUpdate();  
            return veiculo;
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculoSeguro", "SQLException", sql, e.toString());
        } finally {
            connection.close();
            System.out.println("close concetion " + Sql2oConnexao.contConn + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
        return null;
    }

    public List<VeiculoSeguro> findByIdVeiculo(Integer idVeiculo) {
        String sql = "SELECT * FROM veiculo_seguro WHERE idVeiculo = :idVeiculo";
        Connection connection = Sql2oConnexao.getInstance(nomeBD, this.getClass());
        try {              
            return connection.createQuery(sql)        
                    .addParameter("idVeiculo", idVeiculo)
                    .executeAndFetch(seguroHandler);
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculoSeguro", "SQLException", sql, e.toString());
        } finally {
            connection.close();
            System.out.println("close concetion " + Sql2oConnexao.contConn + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
        return new ArrayList<>();
    }
}
