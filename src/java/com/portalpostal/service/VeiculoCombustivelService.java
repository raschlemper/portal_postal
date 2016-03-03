package com.portalpostal.service;

import com.portalpostal.dao.VeiculoCombustivelDAO;
import com.portalpostal.model.VeiculoCombustivel;
import java.util.List;

public class VeiculoCombustivelService {
    
    private final VeiculoCombustivelDAO veiculoCombustivelDAO;

    public VeiculoCombustivelService(String nomeBD) {
        veiculoCombustivelDAO = new VeiculoCombustivelDAO(nomeBD);
    }
    
    public List<VeiculoCombustivel> findAll() throws Exception {
        return veiculoCombustivelDAO.findAll();
    }  
    
    public VeiculoCombustivel find(Integer idVeiculoCombustivel) throws Exception {
        return veiculoCombustivelDAO.find(idVeiculoCombustivel);
    } 
    
    public VeiculoCombustivel save(VeiculoCombustivel veiculo) throws Exception {
        return veiculoCombustivelDAO.save(veiculo);
    } 
    
    public VeiculoCombustivel update(VeiculoCombustivel veiculo) throws Exception {
        return veiculoCombustivelDAO.update(veiculo);
    } 
    
    public VeiculoCombustivel delete(Integer idVeiculoCombustivel) throws Exception {
        return veiculoCombustivelDAO.remove(idVeiculoCombustivel);
    } 
    
    public VeiculoCombustivel findLastCombustivelByIdVeiculo(Integer idVeiculo) throws Exception {
        return veiculoCombustivelDAO.findLastCombustivelByIdVeiculo(idVeiculo);
    } 
    
}
