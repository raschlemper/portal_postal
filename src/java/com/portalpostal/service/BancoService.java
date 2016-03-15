package com.portalpostal.service;

import com.portalpostal.dao.BancoDAO;
import com.portalpostal.dao.VeiculoCombustivelDAO;
import com.portalpostal.dao.VeiculoDAO;
import com.portalpostal.dao.VeiculoManutencaoDAO;
import com.portalpostal.dao.VeiculoMultaDAO;
import com.portalpostal.dao.VeiculoSeguroDAO;
import com.portalpostal.dao.VeiculoSinistroDAO;
import com.portalpostal.model.Banco;
import com.portalpostal.model.Veiculo;
import com.portalpostal.model.VeiculoCombustivel;
import com.portalpostal.model.VeiculoManutencao;
import com.portalpostal.model.VeiculoMulta;
import com.portalpostal.model.VeiculoSeguro;
import com.portalpostal.model.VeiculoSinistro;
import java.util.List;

public class BancoService {
    
    private final BancoDAO bancoDAO;

    public BancoService(String nomeBD) {
        bancoDAO = new BancoDAO(nomeBD);
    }
    
    public List<Banco> findAll() throws Exception {
        return bancoDAO.findAll();
    }  
    
    public Banco find(Integer idBanco) throws Exception {
        return bancoDAO.find(idBanco);
    } 
    
    public Banco save(Banco banco) throws Exception {
        return bancoDAO.save(banco);
    } 
    
    public Banco update(Banco banco) throws Exception {
        return bancoDAO.update(banco);
    } 
    
    public Banco delete(Integer idBanco) throws Exception {
        return bancoDAO.remove(idBanco);
    } 
    
}
