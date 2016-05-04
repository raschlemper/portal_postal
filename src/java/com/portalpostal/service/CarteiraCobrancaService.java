package com.portalpostal.service;

import com.portalpostal.dao.CarteiraCobrancaDAO;
import com.portalpostal.dao.ContaDAO;
import com.portalpostal.model.CarteiraCobranca;
import java.util.List;

public class CarteiraCobrancaService {
    
    private final CarteiraCobrancaDAO carteiraCobrancaDAO; 
    private final ContaDAO contaDAO;

    public CarteiraCobrancaService(String nomeBD) {
        carteiraCobrancaDAO = new CarteiraCobrancaDAO(nomeBD);
        contaDAO = new ContaDAO(nomeBD);
    }
    
    public List<CarteiraCobranca> findAll() throws Exception {
        return carteiraCobrancaDAO.findAll();
    }  
    
    public CarteiraCobranca find(Integer idCarteiraCobranca) throws Exception {
        return carteiraCobrancaDAO.find(idCarteiraCobranca);
    } 
    
    public CarteiraCobranca findConta(Integer idCarteiraCobranca) throws Exception {
        CarteiraCobranca carteiraCobranca = find(idCarteiraCobranca);
        carteiraCobranca.setContas(contaDAO.findByContaCorrente(idCarteiraCobranca));
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
        return carteiraCobrancaDAO.remove(idCarteiraCobranca);
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
