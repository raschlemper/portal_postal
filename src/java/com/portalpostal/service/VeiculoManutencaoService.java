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
    
    public VeiculoManutencao find(Integer idVeiculoManutencao) throws Exception {
        return veiculoManutencaoDAO.find(idVeiculoManutencao);
    } 
    
    public VeiculoManutencao save(VeiculoManutencao veiculo) throws Exception {
        return veiculoManutencaoDAO.save(veiculo);
    } 
    
    public VeiculoManutencao update(VeiculoManutencao veiculo) throws Exception {
        return veiculoManutencaoDAO.update(veiculo);
    } 
    
    public VeiculoManutencao delete(Integer idVeiculoManutencao) throws Exception {
        return veiculoManutencaoDAO.remove(idVeiculoManutencao);
    }  
    
    public List<VeiculoManutencao> findByIdVeiculo(Integer idVeiculo) throws Exception {
        return veiculoManutencaoDAO.findByIdVeiculo(idVeiculo);
    } 
    
}
