package com.portalpostal.service;

import com.portalpostal.dao.VeiculoSeguroDAO;
import com.portalpostal.model.VeiculoSeguro;
import java.util.Date;
import java.util.List;

public class VeiculoSeguroService {
    
    private final String nomeBD;
    
    private VeiculoSeguroDAO veiculoSeguroDAO;

    public VeiculoSeguroService(String nomeBD) {
        this.nomeBD = nomeBD;
    }

    public void init() {
        veiculoSeguroDAO = new VeiculoSeguroDAO(nomeBD);
    }
    
    public List<VeiculoSeguro> findAll() throws Exception {
        init();
        return veiculoSeguroDAO.findAll();
    }  
    
    public VeiculoSeguro find(Integer idVeiculoSeguro) throws Exception {
        init();
        return veiculoSeguroDAO.find(idVeiculoSeguro);
    } 
    
    public VeiculoSeguro save(VeiculoSeguro veiculo) throws Exception {
        init();
        return veiculoSeguroDAO.save(veiculo);
    } 
    
    public VeiculoSeguro update(VeiculoSeguro veiculo) throws Exception {
        init();
        return veiculoSeguroDAO.update(veiculo);
    } 
    
    public VeiculoSeguro delete(Integer idVeiculoSeguro) throws Exception {
        init();
        return veiculoSeguroDAO.remove(idVeiculoSeguro);
    } 
    
    public List<VeiculoSeguro> findByIdVeiculo(Integer idVeiculo) throws Exception {
        init();
        return veiculoSeguroDAO.findByIdVeiculo(idVeiculo);
    } 
    
    public List<VeiculoSeguro> findNotRenewByRangeDate(Date dataInicio, Date dataFim) throws Exception {
        init();
        return veiculoSeguroDAO.findNotRenewByRangeDate(dataInicio, dataFim);
    } 
    
}
