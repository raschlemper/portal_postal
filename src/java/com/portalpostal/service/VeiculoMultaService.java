package com.portalpostal.service;

import com.portalpostal.dao.VeiculoMultaDAO;
import com.portalpostal.model.VeiculoMulta;
import java.util.List;

public class VeiculoMultaService {
    
    private final VeiculoMultaDAO veiculoMultaDAO;

    public VeiculoMultaService(String nomeBD) {
        veiculoMultaDAO = new VeiculoMultaDAO(nomeBD);
    }
    
    public List<VeiculoMulta> findAll() throws Exception {
        return veiculoMultaDAO.findAll();
    }  
    
    public VeiculoMulta find(Integer idVeiculoMulta) throws Exception {
        return veiculoMultaDAO.find(idVeiculoMulta);
    } 
    
    public VeiculoMulta save(VeiculoMulta veiculo) throws Exception {
        return veiculoMultaDAO.save(veiculo);
    } 
    
    public VeiculoMulta update(VeiculoMulta veiculo) throws Exception {
        return veiculoMultaDAO.update(veiculo);
    } 
    
    public VeiculoMulta delete(Integer idVeiculoMulta) throws Exception {
        return veiculoMultaDAO.remove(idVeiculoMulta);
    } 
    
    public List<VeiculoMulta> findByIdVeiculo(Integer idVeiculo) throws Exception {
        return veiculoMultaDAO.findByIdVeiculo(idVeiculo);
    } 
    
}
