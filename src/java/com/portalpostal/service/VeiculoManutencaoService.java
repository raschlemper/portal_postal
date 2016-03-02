package com.portalpostal.service;

import com.portalpostal.dao.VeiculoManutencaoDAO;
import com.portalpostal.model.VeiculoManutencao;
import java.util.List;

public class VeiculoManutencaoService {
    
    private final VeiculoManutencaoDAO veiculoManutencaoDAO;

    public VeiculoManutencaoService(String nomeBD) {
        veiculoManutencaoDAO = new VeiculoManutencaoDAO(nomeBD);
    }
    
    public List<VeiculoManutencao> findAll() throws Exception {
        return veiculoManutencaoDAO.findAll();
    }  
    
    public VeiculoManutencao find(Integer idVeiculo) throws Exception {
        return veiculoManutencaoDAO.find(idVeiculo);
    } 
    
    public VeiculoManutencao save(VeiculoManutencao veiculo) throws Exception {
        return veiculoManutencaoDAO.save(veiculo);
    } 
    
    public VeiculoManutencao update(VeiculoManutencao veiculo) throws Exception {
        return veiculoManutencaoDAO.update(veiculo);
    } 
    
    public VeiculoManutencao delete(Integer idVeiculo) throws Exception {
        return veiculoManutencaoDAO.remove(idVeiculo);
    } 
    
}
