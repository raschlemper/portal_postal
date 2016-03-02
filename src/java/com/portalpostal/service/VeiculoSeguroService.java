package com.portalpostal.service;

import com.portalpostal.dao.VeiculoSeguroDAO;
import com.portalpostal.model.VeiculoSeguro;
import java.util.List;

public class VeiculoSeguroService {
    
    private final VeiculoSeguroDAO veiculoSeguroDAO;

    public VeiculoSeguroService(String nomeBD) {
        veiculoSeguroDAO = new VeiculoSeguroDAO(nomeBD);
    }
    
    public List<VeiculoSeguro> findAll() throws Exception {
        return veiculoSeguroDAO.findAll();
    }  
    
    public VeiculoSeguro find(Integer idVeiculoSeguro) throws Exception {
        return veiculoSeguroDAO.find(idVeiculoSeguro);
    } 
    
    public VeiculoSeguro save(VeiculoSeguro veiculo) throws Exception {
        return veiculoSeguroDAO.save(veiculo);
    } 
    
    public VeiculoSeguro update(VeiculoSeguro veiculo) throws Exception {
        return veiculoSeguroDAO.update(veiculo);
    } 
    
    public VeiculoSeguro delete(Integer idVeiculoSeguro) throws Exception {
        return veiculoSeguroDAO.remove(idVeiculoSeguro);
    } 
    
}
