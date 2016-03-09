package com.portalpostal.dao;

import Controle.ContrErroLog;
import Util.Sql2oConnexao;
import com.portalpostal.dao.handler.SinistroHandler;
import com.portalpostal.dao.handler.VeiculoSinistroHandler;
import com.portalpostal.model.VeiculoSinistro;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.Connection;

public class VeiculoSinistroDAO {   
    
//    private Connection connection;
    private SinistroHandler sinistroHandler;
    private VeiculoSinistroHandler veiculoSinistroHandler;
    private String nomeBD;

    public VeiculoSinistroDAO(String nomeBD) {     
//        connection = Sql2oConnexao.getInstance(nomeBD, this.getClass());
        sinistroHandler = new SinistroHandler();
        veiculoSinistroHandler = new VeiculoSinistroHandler();
        this.nomeBD = nomeBD;
    } 

    public List<VeiculoSinistro> findAll() {
        String sql = "SELECT * FROM veiculo_sinistro, veiculo WHERE veiculo.idVeiculo = veiculo_sinistro.idVeiculo "
                   + "ORDER BY veiculo_sinistro.idVeiculo, veiculo_sinistro.data";
        Connection connection = Sql2oConnexao.getInstance(nomeBD, this.getClass());
        try {              
            return connection.createQuery(sql)
                    .executeAndFetch(veiculoSinistroHandler);
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculoSinistro", "SQLException", sql, e.toString());
        } finally {
            connection.close();
            System.out.println("close concetion " + Sql2oConnexao.contConn + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
        return new ArrayList<>();
    }

    public VeiculoSinistro find(Integer idVeiculoSinistro) throws Exception {
        String sql = "SELECT * FROM veiculo_sinistro, veiculo "
                   + "WHERE veiculo.idVeiculo = veiculo_sinistro.idVeiculo AND idVeiculoSinistro = :idVeiculoSinistro";
        Connection connection = Sql2oConnexao.getInstance(nomeBD, this.getClass());
        try {              
            return connection.createQuery(sql)                
                    .addParameter("idVeiculoSinistro", idVeiculoSinistro)
                    .executeAndFetchFirst(veiculoSinistroHandler);
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculoSinistro", "SQLException", sql, e.toString());
        } finally {
            connection.close();
            System.out.println("close concetion " + Sql2oConnexao.contConn + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
        return null;
    }

    public VeiculoSinistro save(VeiculoSinistro veiculo) throws Exception {        
        String sql = "INSERT INTO veiculo_sinistro (idVeiculo, tipo, boletimOcorrencia, data, local, responsavel, descricao) "
                   + "VALUES(:idVeiculo, :tipo, :boletimOcorrencia, :data, :local, :responsavel, :descricao)";
        Connection connection = Sql2oConnexao.getInstance(nomeBD, this.getClass());
        try {
            Integer idVeiculo = connection.createQuery(sql, true)
                    .addParameter("idVeiculo", veiculo.getVeiculo().getIdVeiculo())
                    .addParameter("tipo", veiculo.getTipo().ordinal())
                    .addParameter("boletimOcorrencia", veiculo.getBoletimOcorrencia())
                    .addParameter("data", veiculo.getData())
                    .addParameter("local", veiculo.getLocal())
                    .addParameter("responsavel", veiculo.getResponsavel().ordinal())
                    .addParameter("descricao", veiculo.getDescricao())
                    .executeUpdate().getKey(Integer.class); 
            return find(idVeiculo);
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculoSinistro", "SQLException", sql, e.toString());
        } finally {
            connection.close();
            System.out.println("close concetion " + Sql2oConnexao.contConn + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
        return null;
    }

    public VeiculoSinistro update(VeiculoSinistro veiculo) throws Exception {        
        String sql = "UPDATE veiculo_sinistro "
                   + "SET idVeiculo = :idVeiculo, tipo = :tipo, boletimOcorrencia = :boletimOcorrencia, data = :data, local = :local, "
                   + "responsavel = :responsavel, descricao = :descricao "
                   + "WHERE idVeiculoSinistro = :idVeiculoSinistro ";
        Connection connection = Sql2oConnexao.getInstance(nomeBD, this.getClass());
        try {
            connection.createQuery(sql)
                .addParameter("idVeiculoSinistro", veiculo.getIdVeiculoSinistro())
                .addParameter("idVeiculo", veiculo.getVeiculo().getIdVeiculo())
                .addParameter("tipo", veiculo.getTipo().ordinal())
                .addParameter("boletimOcorrencia", veiculo.getBoletimOcorrencia())
                .addParameter("data", veiculo.getData())
                .addParameter("local", veiculo.getLocal())
                .addParameter("responsavel", veiculo.getResponsavel().ordinal())
                .addParameter("descricao", veiculo.getDescricao())
                .executeUpdate();          
            return veiculo;
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculoSinistro", "SQLException", sql, e.toString());
        } finally {
            connection.close();
            System.out.println("close concetion " + Sql2oConnexao.contConn + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
        return null;
    }

    public VeiculoSinistro remove(Integer idVeiculoSinistro) throws Exception { 
        String sql = "DELETE FROM veiculo_sinistro WHERE idVeiculoSinistro = :idVeiculoSinistro ";
        Connection connection = Sql2oConnexao.getInstance(nomeBD, this.getClass());
        try {
            VeiculoSinistro veiculo = find(idVeiculoSinistro);
            connection.createQuery(sql)
                .addParameter("idVeiculoSinistro", idVeiculoSinistro)
                .executeUpdate();  
            return veiculo;
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculoSinistro", "SQLException", sql, e.toString());
        } finally {
            connection.close();
            System.out.println("close concetion " + Sql2oConnexao.contConn + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
        return null;
    }

    public List<VeiculoSinistro> findByIdVeiculo(Integer idVeiculo) {
        String sql = "SELECT * FROM veiculo_sinistro WHERE idVeiculo = :idVeiculo";
        Connection connection = Sql2oConnexao.getInstance(nomeBD, this.getClass());
        try {              
            return connection.createQuery(sql) 
                    .addParameter("idVeiculo", idVeiculo)
                    .executeAndFetch(sinistroHandler);
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculoSinistro", "SQLException", sql, e.toString());
        } finally {
            connection.close();
            System.out.println("close concetion " + Sql2oConnexao.contConn + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
        return new ArrayList<>();
    }
}
