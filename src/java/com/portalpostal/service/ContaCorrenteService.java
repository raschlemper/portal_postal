package com.portalpostal.service;

import com.portalpostal.dao.CartaoCreditoDAO;
import com.portalpostal.dao.CarteiraCobrancaDAO;
import com.portalpostal.dao.ContaCorrenteDAO;
import com.portalpostal.dao.ContaDAO;
import com.portalpostal.model.CartaoCredito;
import com.portalpostal.model.CarteiraCobranca;
import com.portalpostal.model.Conta;
import com.portalpostal.model.ContaCorrente;
import java.util.List;

public class ContaCorrenteService {
    
    private final ContaCorrenteDAO contaCorrenteDAO;    
    private final CartaoCreditoDAO cartaoCreditoDAO;   
    private final CarteiraCobrancaDAO carteiraCobrancaDAO;
    private final ContaDAO contaDAO;

    public ContaCorrenteService(String nomeBD) {
        contaCorrenteDAO = new ContaCorrenteDAO(nomeBD);
        cartaoCreditoDAO = new CartaoCreditoDAO(nomeBD);
        carteiraCobrancaDAO = new CarteiraCobrancaDAO(nomeBD);
        contaDAO = new ContaDAO(nomeBD);
    }
    
    public List<ContaCorrente> findAll() throws Exception {
        return contaCorrenteDAO.findAll();
    }  
    
    public ContaCorrente find(Integer idContaCorrente) throws Exception {
        return contaCorrenteDAO.find(idContaCorrente);
    }  
    
    public List<ContaCorrente> findAllCarteiraCobranca() throws Exception {
        List<ContaCorrente> contas = contaCorrenteDAO.findAll();
        for (ContaCorrente contaCorrente : contas) {
            contaCorrente.setCarteiraCobrancas(carteiraCobrancaDAO.findByContaCorrente(contaCorrente.getIdContaCorrente()));
        }
        return contas;
    }  
    
    
    public ContaCorrente findCartaoCredito(Integer idContaCorrente) throws Exception {
        ContaCorrente contaCorrente = find(idContaCorrente);
        contaCorrente.setCartaoCreditos(cartaoCreditoDAO.findByContaCorrente(idContaCorrente));
        return contaCorrente;
    }  
    
    public ContaCorrente findCarteiraCobranca(Integer idContaCorrente) throws Exception {
        ContaCorrente contaCorrente = find(idContaCorrente);
        contaCorrente.setCarteiraCobrancas(carteiraCobrancaDAO.findByContaCorrente(idContaCorrente));
        return contaCorrente;
    } 
    
    public ContaCorrente findConta(Integer idContaCorrente) throws Exception {
        ContaCorrente contaCorrente = find(idContaCorrente);
        contaCorrente.setContas(contaDAO.findByContaCorrente(idContaCorrente));
        return contaCorrente;
    } 
    
    public ContaCorrente save(ContaCorrente contaCorrente) throws Exception {
        validation(contaCorrente);
        return contaCorrenteDAO.save(contaCorrente);
    } 
    
    public ContaCorrente update(ContaCorrente contaCorrente) throws Exception {
        validation(contaCorrente);
        return contaCorrenteDAO.update(contaCorrente);
    } 
    
    public ContaCorrente delete(Integer idContaCorrente) throws Exception {
        if(!podeExcluir(idContaCorrente)) throw new Exception("Esta conta corrente não pode ser excluída!");
        return contaCorrenteDAO.remove(idContaCorrente);
    }       
    
    public boolean podeExcluir(Integer idContaCorrente) throws Exception {
        List<CartaoCredito> cartaoCreditos = cartaoCreditoDAO.findByContaCorrente(idContaCorrente);
        if(!cartaoCreditos.isEmpty()) return false;
        List<CarteiraCobranca> carteiraCobrancas = carteiraCobrancaDAO.findByContaCorrente(idContaCorrente);
        if(!carteiraCobrancas.isEmpty()) return false;
        List<Conta> contas = contaDAO.findByContaCorrente(idContaCorrente);
        if(!contas.isEmpty()) return false;
        return true;                
    } 
    
    public ContaCorrente findByContaCorrente(Integer idBanco, Integer agencia, Integer agenciaDv, 
            Integer contaCorrente, Integer contaCorrenteDv) throws Exception {
        return contaCorrenteDAO.findByContaCorrente(idBanco, agencia, agenciaDv, contaCorrente, contaCorrenteDv);
    }    
    
    private void validation(ContaCorrente contaCorrente) throws Exception {  
        if(existeContaCorrente(contaCorrente)) {
            throw new Exception("Esta Conta Corrente já foi cadastrada!");
        }
    }  
    
    private boolean existeContaCorrente(ContaCorrente contaCorrente) throws Exception {
        ContaCorrente conta = contaCorrenteDAO.findByContaCorrente(contaCorrente.getBanco().getIdBanco(), contaCorrente.getAgencia(), 
                contaCorrente.getAgenciaDv(), contaCorrente.getContaCorrente(), contaCorrente.getContaCorrenteDv());
        if(conta == null) return false;
        if(conta.getBanco().getIdBanco().equals(contaCorrente.getBanco().getIdBanco()) &&
           conta.getAgencia().equals(contaCorrente.getAgencia()) && 
           conta.getContaCorrente().equals(contaCorrente.getContaCorrente())) return false;
        return true;
    }
    
}
