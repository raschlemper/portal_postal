package com.portalpostal.service;

import com.portalpostal.dao.CarteiraCobrancaDAO;
import com.portalpostal.model.CarteiraCobranca;
import com.portalpostal.model.Conta;
import java.util.List;

public class CarteiraCobrancaService {
    
    private final CarteiraCobrancaDAO carteiraCobrancaDAO; 
    private final ContaService contaService;

    public CarteiraCobrancaService(String nomeBD) {
        carteiraCobrancaDAO = new CarteiraCobrancaDAO(nomeBD);
        contaService = new ContaService(nomeBD);
    }
    
    public List<CarteiraCobranca> findAll() throws Exception {
        return carteiraCobrancaDAO.findAll();
    }  
    
    public CarteiraCobranca find(Integer idCarteiraCobranca) throws Exception {
        return carteiraCobrancaDAO.find(idCarteiraCobranca);
    } 

    public List<CarteiraCobranca> findByContaCorrente(Integer idContaCorrente) throws Exception {
        return carteiraCobrancaDAO.findByContaCorrente(idContaCorrente);
    }
    
    public CarteiraCobranca findConta(Integer idCarteiraCobranca) throws Exception {
        CarteiraCobranca carteiraCobranca = find(idCarteiraCobranca);
        carteiraCobranca.setContas(contaService.findByContaCorrente(idCarteiraCobranca));
        return carteiraCobranca;
    } 
    
    public CarteiraCobranca save(CarteiraCobranca carteiraCobranca) throws Exception {
        validation(carteiraCobranca);
        return carteiraCobrancaDAO.save(carteiraCobranca);
    } 
    
    public CarteiraCobranca update(CarteiraCobranca carteiraCobranca) throws Exception {
        validation(carteiraCobranca);
        return carteiraCobrancaDAO.update(carteiraCobranca);
    } 
    
    public CarteiraCobranca delete(Integer idCarteiraCobranca) throws Exception {
        if(!podeExcluir(idCarteiraCobranca)) throw new Exception("Esta carteira de cobrança não pode ser excluída!"); 
        return carteiraCobrancaDAO.remove(idCarteiraCobranca);
    }       
    
    public boolean podeExcluir(Integer idCarteiraCobranca) throws Exception {
        List<Conta> contas = contaService.findByCarteiraCobranca(idCarteiraCobranca);
        if(!contas.isEmpty()) return false;
        return true;                
    } 
    
    public CarteiraCobranca findByCarteiraCobranca(Integer idContaCorrente, Integer codigoBeneficiario, 
            Integer codigoBeneficiarioDv, Integer codigoCarteira) throws Exception {
        return carteiraCobrancaDAO.findByCarteiraCobranca(idContaCorrente, codigoBeneficiario,
                codigoBeneficiarioDv, codigoCarteira);
    }    
    
    private void validation(CarteiraCobranca carteiraCobranca) throws Exception {  
        if(existeCarteiraCobranca(carteiraCobranca)) {
            throw new Exception("Esta Conta Corrente já foi cadastrada!");
        }
    }  
    
    private boolean existeCarteiraCobranca(CarteiraCobranca carteiraCobranca) throws Exception {
        CarteiraCobranca carteira = carteiraCobrancaDAO.findByCarteiraCobranca(carteiraCobranca.getContaCorrente().getIdContaCorrente(), 
                carteiraCobranca.getCodigoBeneficiario(), carteiraCobranca.getCodigoBeneficiarioDv(), carteiraCobranca.getCodigoCarteira());
        if(carteira == null) return false;
        if(carteira.getContaCorrente().getIdContaCorrente().equals(carteiraCobranca.getContaCorrente().getIdContaCorrente()) &&
           carteira.getCodigoBeneficiario().equals(carteiraCobranca.getCodigoBeneficiario()) && 
           carteira.getCodigoBeneficiarioDv().equals(carteiraCobranca.getCodigoBeneficiarioDv()) && 
           carteira.getCodigoCarteira().equals(carteiraCobranca.getCodigoCarteira())) return false;
        return true;
    }
    
}
