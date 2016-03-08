package com.portalpostal.dao;

import Controle.ContrErroLog;
import Util.Sql2oConnexao;
import com.portalpostal.dao.handler.VeiculoHandler;
import com.portalpostal.model.Veiculo;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.Connection;
import org.sql2o.Query;

public class VeiculoDAO {   
    
    private Connection connection;
    private VeiculoHandler veiculoHandler;

    public VeiculoDAO(String nomeBD) {
        connection = Sql2oConnexao.conect(nomeBD);
        veiculoHandler = new VeiculoHandler();
    } 

    public List<Veiculo> findAll() throws Exception {
        String sql = "SELECT * FROM veiculo ORDER BY marca, modelo, placa";
        try {              
            return connection.createQuery(sql)
                    .executeAndFetch(veiculoHandler);
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculo", "SQLException", sql, e.toString());
        } finally {
            connection.close();
        }
        return new ArrayList<>();
    }

    public Veiculo find(Integer idVeiculo) throws Exception {
        String sql = "SELECT * FROM veiculo WHERE idVeiculo = :idVeiculo";
        try {              
            return connection.createQuery(sql)
                    .addParameter("idVeiculo", idVeiculo)
                    .executeAndFetchFirst(veiculoHandler);
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculo", "SQLException", sql, e.toString());
        } finally {
            connection.close();
        }
        return null;
    }

    public Veiculo save(Veiculo veiculo) throws Exception {        
        String sql = "INSERT INTO veiculo (tipo, idMarca, marca, idModelo, modelo, idVersao, versao, placa, anoModelo, chassis, renavam, "
                   + "quilometragem, combustivel, status, situacao, dataCadastro) "
                   + "VALUES(:tipo, :idMarca, :marca, :idModelo, :modelo, :idVersao, :versao, :placa, :anoModelo, :chassis, :renavam, "
                   + ":quilometragem, :combustivel, :status, :situacao, now())";
        try {              
            Integer idVeiculo = connection.createQuery(sql, true)
                    .addParameter("tipo", veiculo.getTipo().ordinal())
                    .addParameter("idMarca", veiculo.getIdMarca())
                    .addParameter("marca", veiculo.getMarca())
                    .addParameter("idModelo", veiculo.getIdModelo())
                    .addParameter("modelo", veiculo.getModelo())
                    .addParameter("idVersao", veiculo.getIdVersao())
                    .addParameter("versao", veiculo.getVersao())
                    .addParameter("placa", veiculo.getPlaca())
                    .addParameter("anoModelo", veiculo.getAnoModelo())
                    .addParameter("chassis", veiculo.getChassis())
                    .addParameter("renavam", veiculo.getRenavam())
                    .addParameter("quilometragem", veiculo.getQuilometragem())
                    .addParameter("combustivel", veiculo.getCombustivel().ordinal())
                    .addParameter("status", veiculo.getStatus().ordinal())
                    .addParameter("situacao", veiculo.getSituacao().ordinal())   
                    .executeUpdate().getKey(Integer.class); 
            return find(idVeiculo);
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculo", "SQLException", sql, e.toString());
        } finally {
            connection.close();
        }
        return null;
    }

    public Veiculo update(Veiculo veiculo) throws Exception {        
        String sql = "UPDATE veiculo "
                   + "SET tipo = :tipo, idMarca = :idMarca, marca = :marca, idModelo = :idModelo, modelo = :modelo, idVersao = :idVersao, "
                   + "versao = :versao, placa = :placa, anoModelo = :anoModelo, chassis = :chassis, renavam = :renavam, "
                   + "quilometragem = :quilometragem, combustivel = :combustivel, status = :status, situacao = :situacao "
                   + "WHERE idVeiculo = :idVeiculo ";
        try {              
            connection.createQuery(sql)
                .addParameter("idVeiculo", veiculo.getIdVeiculo())
                .addParameter("tipo", veiculo.getTipo().ordinal())
                .addParameter("idMarca", veiculo.getIdMarca())
                .addParameter("marca", veiculo.getMarca())
                .addParameter("idModelo", veiculo.getIdModelo())
                .addParameter("modelo", veiculo.getModelo())
                .addParameter("idVersao", veiculo.getIdVersao())
                .addParameter("versao", veiculo.getVersao())
                .addParameter("placa", veiculo.getPlaca())
                .addParameter("anoModelo", veiculo.getAnoModelo())
                .addParameter("chassis", veiculo.getChassis())
                .addParameter("renavam", veiculo.getRenavam())
                .addParameter("quilometragem", veiculo.getQuilometragem())
                .addParameter("combustivel", veiculo.getCombustivel().ordinal())
                .addParameter("status", veiculo.getStatus().ordinal())
                .addParameter("situacao", veiculo.getSituacao().ordinal())   
                .executeUpdate();          
            return veiculo;
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculo", "SQLException", sql, e.toString());
        } finally {
            connection.close();
        }
        return null;
    }

    public Veiculo remove(Integer idVeiculo) throws Exception { 
        String sql = "DELETE FROM veiculo WHERE idVeiculo = :idVeiculo ";
        try {              
            Veiculo veiculo = find(idVeiculo);
            connection.createQuery(sql)
                .addParameter("idVeiculo", idVeiculo)
                .executeUpdate();  
            return veiculo;
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculo", "SQLException", sql, e.toString());
        } finally {
            connection.close();
        }
        return null;
    }

    public Veiculo findByPlaca(Veiculo veiculo) throws Exception {        
        String sql = "SELECT * FROM veiculo WHERE placa = :placa ";
        if(veiculo.getIdVeiculo()!= null) { sql += " AND idVeiculo <> :idVeiculo "; }
        try {              
            Query query = connection.createQuery(sql).addParameter("placa", veiculo.getPlaca());
            if(veiculo.getIdVeiculo()!= null) { query.addParameter("idVeiculo", veiculo.getIdVeiculo()); }
            return query.executeAndFetchFirst(veiculoHandler);
        } catch (Exception e) {
            ContrErroLog.inserir("HOITO - contrVeiculo", "SQLException", sql, e.toString());
        } finally {
            connection.close();
        }
        return null;
    }
}
