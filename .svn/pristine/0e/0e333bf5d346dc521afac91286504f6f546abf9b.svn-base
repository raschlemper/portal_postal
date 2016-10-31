package com.portalpostal.service;

import com.portalpostal.dao.VeiculoSinistroDAO;
import com.portalpostal.model.VeiculoSinistro;
import java.util.List;

public class VeiculoSinistroService {
    
    private final String nomeBD;
    
    private VeiculoSinistroDAO veiculoSinistroDAO;

    public VeiculoSinistroService(String nomeBD) {
        this.nomeBD = nomeBD;
    }

    public void init() {
        veiculoSinistroDAO = new VeiculoSinistroDAO(nomeBD);
    }
    
    public List<VeiculoSinistro> findAll() throws Exception {
        init();
        return veiculoSinistroDAO.findAll();
    }  
    
    public VeiculoSinistro find(Integer idVeiculoSinistro) throws Exception {
        init();
        return veiculoSinistroDAO.find(idVeiculoSinistro);
    } 
    
    public VeiculoSinistro save(VeiculoSinistro veiculo) throws Exception {
        init();
        return veiculoSinistroDAO.save(veiculo);
    } 
    
    public VeiculoSinistro update(VeiculoSinistro veiculo) throws Exception {
        init();
        return veiculoSinistroDAO.update(veiculo);
    } 
    
    public VeiculoSinistro delete(Integer idVeiculoSinistro) throws Exception {
        init();
        return veiculoSinistroDAO.remove(idVeiculoSinistro);
    } 
    
    public List<VeiculoSinistro> findByIdVeiculo(Integer idVeiculo) throws Exception {
        init();
        return veiculoSinistroDAO.findByIdVeiculo(idVeiculo);
    } 
    
}
