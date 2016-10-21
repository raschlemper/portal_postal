package com.portalpostal.service;

import com.portalpostal.dao.VeiculoCombustivelDAO;
import com.portalpostal.dao.VeiculoDAO;
import com.portalpostal.dao.VeiculoManutencaoDAO;
import com.portalpostal.dao.VeiculoMultaDAO;
import com.portalpostal.dao.VeiculoSeguroDAO;
import com.portalpostal.dao.VeiculoSinistroDAO;
import com.portalpostal.model.Veiculo;
import com.portalpostal.model.VeiculoCombustivel;
import com.portalpostal.model.VeiculoManutencao;
import com.portalpostal.model.VeiculoMulta;
import com.portalpostal.model.VeiculoSeguro;
import com.portalpostal.model.VeiculoSinistro;
import java.util.List;

public class VeiculoService {
    
    private final VeiculoDAO veiculoDAO;
    private final VeiculoCombustivelDAO veiculoCombustivelDAO;
    private final VeiculoManutencaoDAO veiculoManutencaoDAO;
    private final VeiculoMultaDAO veiculoMultaDAO;
    private final VeiculoSeguroDAO veiculoSeguroDAO;
    private final VeiculoSinistroDAO veiculoSinistroDAO;

    public VeiculoService(String nomeBD) {
        veiculoDAO = new VeiculoDAO(nomeBD);
        veiculoCombustivelDAO = new VeiculoCombustivelDAO(nomeBD);
        veiculoManutencaoDAO = new VeiculoManutencaoDAO(nomeBD);
        veiculoMultaDAO = new VeiculoMultaDAO(nomeBD);
        veiculoSeguroDAO = new VeiculoSeguroDAO(nomeBD);
        veiculoSinistroDAO = new VeiculoSinistroDAO(nomeBD);
    }
    
    public List<Veiculo> findAll() throws Exception {
        return veiculoDAO.findAll();
    }  
    
    public Veiculo find(Integer idVeiculo) throws Exception {
        return veiculoDAO.find(idVeiculo);
    }  
    
    public Veiculo findByPlaca(String placa) throws Exception {
        return veiculoDAO.findByPlaca(placa);
    } 
    
    public Veiculo save(Veiculo veiculo) throws Exception {
        validation(veiculo);
        return veiculoDAO.save(veiculo);
    } 
    
    public Veiculo update(Veiculo veiculo) throws Exception {
        validation(veiculo);
        return veiculoDAO.update(veiculo);
    } 
    
    public Veiculo delete(Integer idVeiculo) throws Exception {
        if(!podeExcluir(idVeiculo)) throw new Exception("O veículo não pode ser excluído!"); 
        return veiculoDAO.remove(idVeiculo);
    }    
    
    public boolean podeExcluir(Integer idVeiculo) throws Exception {
        List<VeiculoCombustivel> combustiveis = veiculoCombustivelDAO.findByIdVeiculo(idVeiculo);
        if(!combustiveis.isEmpty()) return false;
        List<VeiculoManutencao> manutencoes = veiculoManutencaoDAO.findByIdVeiculo(idVeiculo);
        if(!manutencoes.isEmpty()) return false;
        List<VeiculoMulta> multas = veiculoMultaDAO.findByIdVeiculo(idVeiculo);
        if(!multas.isEmpty()) return false;
        List<VeiculoSeguro> seguros = veiculoSeguroDAO.findByIdVeiculo(idVeiculo);
        if(!seguros.isEmpty()) return false;
        List<VeiculoSinistro> sinistros = veiculoSinistroDAO.findByIdVeiculo(idVeiculo);
        if(!sinistros.isEmpty()) return false;
        return true;                
    }   
    
    private void validation(Veiculo veiculo) throws Exception {  
        if(existeVeiculo(veiculo)) {
            throw new Exception("Este Veículo já foi cadastrado!");
        } 
    }   
    
    private boolean existeVeiculo(Veiculo veiculo) throws Exception {
        Veiculo veiculoPlaca = veiculoDAO.findByPlaca(veiculo.getPlaca());
        if(veiculoPlaca == null) return false;
        return true;
    }
    
}
