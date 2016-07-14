package com.portalpostal.service;

import com.portalpostal.dao.VeiculoDAO;
import com.portalpostal.model.Veiculo;
import com.portalpostal.model.VeiculoCombustivel;
import com.portalpostal.model.VeiculoManutencao;
import com.portalpostal.model.VeiculoMulta;
import com.portalpostal.model.VeiculoSeguro;
import com.portalpostal.model.VeiculoSinistro;
import java.util.List;

public class VeiculoService {
    
    private final String nomeBD;
    
    private VeiculoDAO veiculoDAO;
    private VeiculoCombustivelService veiculoCombustivelService;
    private VeiculoManutencaoService veiculoManutencaoService;
    private VeiculoMultaService veiculoMultaService;
    private VeiculoSeguroService veiculoSeguroService;
    private VeiculoSinistroService veiculoSinistroService;

    public VeiculoService(String nomeBD) {
        this.nomeBD = nomeBD;
    }

    public void init() {
        veiculoDAO = new VeiculoDAO(nomeBD);
        veiculoCombustivelService = new VeiculoCombustivelService(nomeBD);
        veiculoManutencaoService = new VeiculoManutencaoService(nomeBD);
        veiculoMultaService = new VeiculoMultaService(nomeBD);
        veiculoSeguroService = new VeiculoSeguroService(nomeBD);
        veiculoSinistroService = new VeiculoSinistroService(nomeBD);
    }
    
    public List<Veiculo> findAll() throws Exception {
        init();
        return veiculoDAO.findAll();
    }  
    
    public Veiculo find(Integer idVeiculo) throws Exception {
        init();
        return veiculoDAO.find(idVeiculo);
    }  
    
    public Veiculo findByPlaca(String placa) throws Exception {
        init();
        return veiculoDAO.findByPlaca(placa);
    } 
    
    public Veiculo save(Veiculo veiculo) throws Exception {
        init();
        validation(veiculo);
        return veiculoDAO.save(veiculo);
    } 
    
    public Veiculo update(Veiculo veiculo) throws Exception {
        init();
        validation(veiculo);
        return veiculoDAO.update(veiculo);
    } 
    
    public Veiculo delete(Integer idVeiculo) throws Exception {
        init();
        if(!podeExcluir(idVeiculo)) throw new Exception("O veículo não pode ser excluído!"); 
        return veiculoDAO.remove(idVeiculo);
    }    
    
    private boolean podeExcluir(Integer idVeiculo) throws Exception {
        List<VeiculoCombustivel> combustiveis = veiculoCombustivelService.findByIdVeiculo(idVeiculo);
        if(!combustiveis.isEmpty()) return false;
        List<VeiculoManutencao> manutencoes = veiculoManutencaoService.findByIdVeiculo(idVeiculo);
        if(!manutencoes.isEmpty()) return false;
        List<VeiculoMulta> multas = veiculoMultaService.findByIdVeiculo(idVeiculo);
        if(!multas.isEmpty()) return false;
        List<VeiculoSeguro> seguros = veiculoSeguroService.findByIdVeiculo(idVeiculo);
        if(!seguros.isEmpty()) return false;
        List<VeiculoSinistro> sinistros = veiculoSinistroService.findByIdVeiculo(idVeiculo);
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
