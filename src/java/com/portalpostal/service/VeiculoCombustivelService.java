package com.portalpostal.service;

import com.portalpostal.dao.VeiculoCombustivelDAO;
import com.portalpostal.model.VeiculoCombustivel;
import java.util.List;

public class VeiculoCombustivelService {
    
    private final String nomeBD;
    
    private VeiculoCombustivelDAO veiculoCombustivelDAO;

    public VeiculoCombustivelService(String nomeBD) {
        this.nomeBD = nomeBD;
    }

    public void init() {
        veiculoCombustivelDAO = new VeiculoCombustivelDAO(nomeBD);
    }
    
    public List<VeiculoCombustivel> findAll() throws Exception {
        init();
        return veiculoCombustivelDAO.findAll();
    }  
    
    public VeiculoCombustivel find(Integer idVeiculoCombustivel) throws Exception {
        init();
        return veiculoCombustivelDAO.find(idVeiculoCombustivel);
    } 
    
    public VeiculoCombustivel save(VeiculoCombustivel veiculo) throws Exception {
        init();
        return veiculoCombustivelDAO.save(veiculo);
    } 
    
    public VeiculoCombustivel update(VeiculoCombustivel veiculo) throws Exception {
        return veiculoCombustivelDAO.update(veiculo);
    } 
    
    public VeiculoCombustivel delete(Integer idVeiculoCombustivel) throws Exception {
        init();
        return veiculoCombustivelDAO.remove(idVeiculoCombustivel);
    } 
    
    public VeiculoCombustivel findLastCombustivelByIdVeiculo(Integer idVeiculo) throws Exception {
        init();
        return veiculoCombustivelDAO.findLastCombustivelByIdVeiculo(idVeiculo);
    } 
    
    public List<VeiculoCombustivel> findByIdVeiculo(Integer idVeiculo) throws Exception {
        init();
        return veiculoCombustivelDAO.findByIdVeiculo(idVeiculo);
    } 
    
}
