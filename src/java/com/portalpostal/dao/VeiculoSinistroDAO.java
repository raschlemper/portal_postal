package com.portalpostal.dao;

import Controle.ContrErroLog;
import Util.Sql2oConnexao;
import com.portalpostal.dao.handler.VeiculoSinistroHandler;
import com.portalpostal.model.VeiculoSinistro;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.Connection;

public class VeiculoSinistroDAO {   
    
    private Connection connection;
    private VeiculoSinistroHandler handler;

    public VeiculoSinistroDAO(String nomeBD) {
        connection = Sql2oConnexao.conect(nomeBD);
        handler = new VeiculoSinistroHandler();
    } 

    public List<VeiculoSinistro> findAll() {
        String sql = "SELECT * FROM veiculo_sinistro, veiculo WHERE veiculo.idVeiculo = veiculo_sinistro.idVeiculo "
                   + "ORDER BY veiculo_sinistro.idVeiculo, veiculo_sinistro.data";
        try {              
            return connection.createQuery(sql)
                    .executeAndFetch(handler);
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculoSinistro", "SQLException", sql, e.toString());
        } finally {
            connection.close();
        }
        return new ArrayList<>();
    }

    public VeiculoSinistro find(Integer idVeiculoSinistro) throws Exception {
        String sql = "SELECT * FROM veiculo_sinistro, veiculo "
                   + "WHERE veiculo.idVeiculo = veiculo_sinistro.idVeiculo AND idVeiculoSinistro = :idVeiculoSinistro";
        try {              
            return connection.createQuery(sql)                
                    .addParameter("idVeiculoSinistro", idVeiculoSinistro)
                    .executeAndFetchFirst(handler);
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculoSinistro", "SQLException", sql, e.toString());
        } finally {
            connection.close();
        }
        return null;
    }

    public VeiculoSinistro save(VeiculoSinistro veiculo) throws Exception {        
        String sql = "INSERT INTO veiculo_sinistro (idVeiculo, tipo, boletimOcorrencia, data, local, responsavel, descricao) "
                   + "VALUES(:idVeiculo, :tipo, :boletimOcorrencia, :data, :local, :responsavel, :descricao)";
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
        }
        return null;
    }

    public VeiculoSinistro update(VeiculoSinistro veiculo) throws Exception {        
        String sql = "UPDATE veiculo_sinistro "
                   + "SET idVeiculo = :idVeiculo, tipo = :tipo, boletimOcorrencia = :boletimOcorrencia, data = :data, local = :local, "
                   + "responsavel = :responsavel, descricao = :descricao "
                   + "WHERE idVeiculoSinistro = :idVeiculoSinistro ";
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
        }
        return null;
    }

    public VeiculoSinistro remove(Integer idVeiculoSinistro) throws Exception { 
        String sql = "DELETE FROM veiculo_sinistro WHERE idVeiculoSinistro = :idVeiculoSinistro ";
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
        }
        return null;
    }
}
