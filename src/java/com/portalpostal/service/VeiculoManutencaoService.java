package com.portalpostal.service;

import com.portalpostal.dao.VeiculoManutencaoDAO;
import com.portalpostal.model.VeiculoManutencao;
import java.util.Date;
import java.util.List;

public class VeiculoManutencaoService {
    
    private final String nomeBD;
    
    private VeiculoManutencaoDAO veiculoManutencaoDAO;

    public VeiculoManutencaoService(String nomeBD) {
        this.nomeBD = nomeBD;
    }

    public void init() {
        veiculoManutencaoDAO = new VeiculoManutencaoDAO(nomeBD);
    }
    
    public List<VeiculoManutencao> findAll() throws Exception {
        init();
        return veiculoManutencaoDAO.findAll();
    }  
    
    public VeiculoManutencao find(Integer idVeiculoManutencao) throws Exception {
        init();
        return veiculoManutencaoDAO.find(idVeiculoManutencao);
    } 
    
    public VeiculoManutencao save(VeiculoManutencao veiculo) throws Exception {
        init();
        return veiculoManutencaoDAO.save(veiculo);
    } 
    
    public VeiculoManutencao update(VeiculoManutencao veiculo) throws Exception {
        init();
        return veiculoManutencaoDAO.update(veiculo);
    } 
    
    public VeiculoManutencao delete(Integer idVeiculoManutencao) throws Exception {
        init();
        return veiculoManutencaoDAO.remove(idVeiculoManutencao);
    }  
    
    public List<VeiculoManutencao> findByIdVeiculo(Integer idVeiculo) throws Exception {
        init();
        return veiculoManutencaoDAO.findByIdVeiculo(idVeiculo);
    } 
    
    public List<VeiculoManutencao> findNotDoneByRangeDate(Date dataInicio, Date dataFim) throws Exception {
        init();
        return veiculoManutencaoDAO.findNotDoneByRangeDate(dataInicio, dataFim);
    } 
        
}
