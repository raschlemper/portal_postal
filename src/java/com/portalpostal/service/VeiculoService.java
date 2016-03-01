package com.portalpostal.service;

import com.portalpostal.dao.VeiculoDAO;
import com.portalpostal.model.Veiculo;
import java.util.List;

public class VeiculoService {
    
    private final VeiculoDAO veiculoDAO;

    public VeiculoService(String nomeBD) {
        veiculoDAO = new VeiculoDAO(nomeBD);
    }
    
    public List<Veiculo> findAll() throws Exception {
        return veiculoDAO.findAll();
    }  
    
    public Veiculo find(Integer idVeiculo) throws Exception {
        return veiculoDAO.find(idVeiculo);
    } 
    
    public Veiculo save(Veiculo veiculo) throws Exception {
        validation(veiculo);
        return veiculoDAO.save(veiculo);
    } 
    
    public Veiculo update(Veiculo veiculo) throws Exception {
        validation(veiculo);
        return veiculoDAO.update(veiculo);
    } 
    
    public Veiculo delete(Integer idVeiculo) throws Exception {
        return veiculoDAO.remove(idVeiculo);
    }     
    
    private void validation(Veiculo veiculo) throws Exception {  
        if(existeVeiculo(veiculo)) {
            throw new Exception("Este Veículo já foi cadastrado!");
        } 
    }     
    
    private boolean existeVeiculo(Veiculo veiculo) throws Exception {
        Veiculo veiculoPlaca = veiculoDAO.findByPlaca(veiculo);
        if(veiculoPlaca == null) return false;
        return true;
    }
    
}
