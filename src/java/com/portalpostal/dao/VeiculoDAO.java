package com.portalpostal.dao;

import Util.Sql2oConnexao;
import com.portalpostal.model.Veiculo;
import java.util.List;
import org.sql2o.Connection;
import org.sql2o.Query;

public class VeiculoDAO {   
    
    protected Connection connection;

    public VeiculoDAO(String nomeBD) {
        connection = Sql2oConnexao.conect(nomeBD);
    } 

    public List<Veiculo> findAll() throws Exception {
        String sql = "SELECT * FROM veiculo ORDER BY marca, modelo, placa";
        return connection.createQuery(sql).addColumnMapping("idTipo", "tipo").executeAndFetch(Veiculo.class);
    }

    public Veiculo find(Integer idVeiculo) throws Exception {
        String sql = "SELECT * FROM veiculo WHERE idVeiculo = :idVeiculo";
        return connection.createQuery(sql)
                .addColumnMapping("idTipo", "tipo")
                .addParameter("idVeiculo", idVeiculo)
                .executeAndFetchFirst(Veiculo.class);
    }

    public Veiculo save(Veiculo veiculo) throws Exception {        
        String sql = "INSERT INTO veiculo (tipo, idMarca, marca, idModelo, modelo, idVersao, versao, placa, anoModelo, chassis, renavam, "
                   + "quilometragem, combustivel, status, situacao, dataCadastro) "
                   + "VALUES(:tipo, :idMarca, :marca, :idModelo, :modelo, :idVersao, :versao, :placa, :anoModelo, :chassis, :renavam, "
                   + ":quilometragem, :combustivel, :status, :situacao, now())";
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
    }

    public Veiculo update(Veiculo veiculo) throws Exception {        
        String sql = "UPDATE veiculo "
                   + "SET tipo = :tipo, idMarca = :idMarca, marca = :marca, idModelo = :idModelo, modelo = :modelo, idVersao = :idVersao, "
                   + "versao = :versao, placa = :placa, anoModelo = :anoModelo, chassis = :chassis, renavam = :renavam, "
                   + "quilometragem = :quilometragem, combustivel = :combustivel, status = :status, situacao = :situacao "
                   + "WHERE idVeiculo = :idVeiculo ";
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
    }

    public Veiculo remove(Integer idVeiculo) throws Exception { 
        Veiculo veiculo = find(idVeiculo);
        String sql = "DELETE FROM veiculo WHERE idVeiculo = :idVeiculo ";
            connection.createQuery(sql)
                .addParameter("idVeiculo", idVeiculo)
                .executeUpdate();  
        return veiculo;
    }

    public Veiculo findByPlaca(Veiculo veiculo) throws Exception {        
        String sql = "SELECT * FROM veiculo WHERE placa = :placa ";
        if(veiculo.getIdVeiculo()!= null) { sql += " AND idVeiculo <> :idVeiculo "; }
        Query query = connection.createQuery(sql).addParameter("placa", veiculo.getPlaca());
        if(veiculo.getIdVeiculo()!= null) { query.addParameter("idVeiculo", veiculo.getIdVeiculo()); }
        return query.executeAndFetchFirst(Veiculo.class);
    }
}
