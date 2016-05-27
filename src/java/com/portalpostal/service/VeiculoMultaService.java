package com.portalpostal.service;

import com.portalpostal.dao.VeiculoMultaDAO;
import com.portalpostal.model.VeiculoMulta;
import java.util.List;

public class VeiculoMultaService {
    
    private final String nomeBD;
    
    private VeiculoMultaDAO veiculoMultaDAO;

    public VeiculoMultaService(String nomeBD) {
        this.nomeBD = nomeBD;
    }

    public void init() {
        veiculoMultaDAO = new VeiculoMultaDAO(nomeBD);
    }
    
    public List<VeiculoMulta> findAll() throws Exception {
        init();
        return veiculoMultaDAO.findAll();
    }  
    
    public VeiculoMulta find(Integer idVeiculoMulta) throws Exception {
        init();
        return veiculoMultaDAO.find(idVeiculoMulta);
    } 
    
    public VeiculoMulta save(VeiculoMulta veiculo) throws Exception {
        init();
        return veiculoMultaDAO.save(veiculo);
    } 
    
    public VeiculoMulta update(VeiculoMulta veiculo) throws Exception {
        init();
        return veiculoMultaDAO.update(veiculo);
    } 
    
    public VeiculoMulta delete(Integer idVeiculoMulta) throws Exception {
        init();
        return veiculoMultaDAO.remove(idVeiculoMulta);
    } 
    
    public List<VeiculoMulta> findByIdVeiculo(Integer idVeiculo) throws Exception {
        init();
        return veiculoMultaDAO.findByIdVeiculo(idVeiculo);
    } 
    
}
