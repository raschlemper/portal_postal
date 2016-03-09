package com.portalpostal.dao;

import com.portalpostal.dao.handler.VeiculoHandler;
import com.portalpostal.model.Veiculo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VeiculoDAO extends GenericDAO {  
    
    private VeiculoHandler veiculoHandler;

    public VeiculoDAO(String nameDB) {
        super(nameDB, VeiculoDAO.class);
        veiculoHandler = new VeiculoHandler();
    }

    public List<Veiculo> findAll() throws Exception {
        String sql = "SELECT * FROM veiculo ORDER BY marca, modelo, placa";
        return findAll(sql, null, veiculoHandler);
    }

    public Veiculo find(Integer idVeiculo) throws Exception {
        String sql = "SELECT * FROM veiculo WHERE idVeiculo = :idVeiculo";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idVeiculo", idVeiculo);
        return (Veiculo) find(sql, params, veiculoHandler);
    }

    public Veiculo save(Veiculo veiculo) throws Exception { 
        String sql = "INSERT INTO veiculo (tipo, idMarca, marca, idModelo, modelo, idVersao, versao, placa, anoModelo, chassis, renavam, "
                   + "quilometragem, combustivel, status, situacao, dataCadastro) "
                   + "VALUES(:tipo, :idMarca, :marca, :idModelo, :modelo, :idVersao, :versao, :placa, :anoModelo, :chassis, :renavam, "
                   + ":quilometragem, :combustivel, :status, :situacao, now())";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tipo", veiculo.getTipo().ordinal());
        params.put("idMarca", veiculo.getIdMarca());
        params.put("marca", veiculo.getMarca());
        params.put("idModelo", veiculo.getIdModelo());
        params.put("modelo", veiculo.getModelo());
        params.put("idVersao", veiculo.getIdVersao());
        params.put("versao", veiculo.getVersao());
        params.put("placa", veiculo.getPlaca());
        params.put("anoModelo", veiculo.getAnoModelo());
        params.put("chassis", veiculo.getChassis());
        params.put("renavam", veiculo.getRenavam());
        params.put("quilometragem", veiculo.getQuilometragem());
        params.put("combustivel", veiculo.getCombustivel().ordinal());
        params.put("status", veiculo.getStatus().ordinal());
        params.put("situacao", veiculo.getSituacao().ordinal());        
        Integer idVeiculo = save(sql, params, veiculoHandler);
        return find(idVeiculo);
    }

    public Veiculo update(Veiculo veiculo) throws Exception {  
        String sql = "UPDATE veiculo "
                   + "SET tipo = :tipo, idMarca = :idMarca, marca = :marca, idModelo = :idModelo, modelo = :modelo, idVersao = :idVersao, "
                   + "versao = :versao, placa = :placa, anoModelo = :anoModelo, chassis = :chassis, renavam = :renavam, "
                   + "quilometragem = :quilometragem, combustivel = :combustivel, status = :status, situacao = :situacao "
                   + "WHERE idVeiculo = :idVeiculo ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tipo", veiculo.getTipo().ordinal());
        params.put("idMarca", veiculo.getIdMarca());
        params.put("marca", veiculo.getMarca());
        params.put("idModelo", veiculo.getIdModelo());
        params.put("modelo", veiculo.getModelo());
        params.put("idVersao", veiculo.getIdVersao());
        params.put("versao", veiculo.getVersao());
        params.put("placa", veiculo.getPlaca());
        params.put("anoModelo", veiculo.getAnoModelo());
        params.put("chassis", veiculo.getChassis());
        params.put("renavam", veiculo.getRenavam());
        params.put("quilometragem", veiculo.getQuilometragem());
        params.put("combustivel", veiculo.getCombustivel().ordinal());
        params.put("status", veiculo.getStatus().ordinal());
        params.put("situacao", veiculo.getSituacao().ordinal());        
        update(sql, params, veiculoHandler);
        return veiculo;  
    }

    public Veiculo remove(Integer idVeiculo) throws Exception { 
        String sql = "DELETE FROM veiculo WHERE idVeiculo = :idVeiculo ";
        Veiculo veiculo = find(idVeiculo);
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idVeiculo", idVeiculo);
        remove(sql, params, veiculoHandler);
        return veiculo;
    }

    public Veiculo findByPlaca(Veiculo veiculo) throws Exception {     
        String sql = "SELECT * FROM veiculo WHERE idVeiculo = :idVeiculo";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("placa", veiculo.getPlaca());
        if(veiculo.getIdVeiculo()!= null) { params.put("idVeiculo", veiculo.getIdVeiculo()); }
        return (Veiculo) find(sql, params, veiculoHandler);
    }
}
