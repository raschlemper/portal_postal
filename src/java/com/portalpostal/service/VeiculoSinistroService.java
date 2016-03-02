package com.portalpostal.service;

import com.portalpostal.dao.VeiculoSinistroDAO;
import com.portalpostal.model.VeiculoSinistro;
import java.util.List;

public class VeiculoSinistroService {
    
    private final VeiculoSinistroDAO veiculoSinistroDAO;

    public VeiculoSinistroService(String nomeBD) {
        veiculoSinistroDAO = new VeiculoSinistroDAO(nomeBD);
    }
    
    public List<VeiculoSinistro> findAll() throws Exception {
        return veiculoSinistroDAO.findAll();
    }  
    
    public VeiculoSinistro find(Integer idVeiculoSinistro) throws Exception {
        return veiculoSinistroDAO.find(idVeiculoSinistro);
    } 
    
    public VeiculoSinistro save(VeiculoSinistro veiculo) throws Exception {
        return veiculoSinistroDAO.save(veiculo);
    } 
    
    public VeiculoSinistro update(VeiculoSinistro veiculo) throws Exception {
        return veiculoSinistroDAO.update(veiculo);
    } 
    
    public VeiculoSinistro delete(Integer idVeiculoSinistro) throws Exception {
        return veiculoSinistroDAO.remove(idVeiculoSinistro);
    } 
    
}
