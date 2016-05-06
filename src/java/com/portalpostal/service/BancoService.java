package com.portalpostal.service;

import com.portalpostal.dao.BancoDAO;
import com.portalpostal.dao.ContaCorrenteDAO;
import com.portalpostal.model.Banco;
import com.portalpostal.model.ContaCorrente;
import java.util.List;

public class BancoService {
    
    private final BancoDAO bancoDAO;    
    private final ContaCorrenteDAO contaCorrenteDAO;

    public BancoService(String nomeBD) {
        bancoDAO = new BancoDAO(nomeBD);
        contaCorrenteDAO = new ContaCorrenteDAO(nomeBD);
    }
    
    public List<Banco> findAll() throws Exception {
        return bancoDAO.findAll();
    }  
    
    public Banco find(Integer idBanco) throws Exception {
        return bancoDAO.find(idBanco);
    } 
    
    public Banco findContaCorrente(Integer idBanco) throws Exception {
        Banco banco = find(idBanco);
        banco.setContaCorrentes(contaCorrenteDAO.findByBanco(idBanco));
        return banco;
    } 
    
    public Banco findByNumero(Integer numero) throws Exception {
        return bancoDAO.findByNumero(numero);
    } 
    
    public Banco save(Banco banco) throws Exception {
        validation(banco);
        return bancoDAO.save(banco);
    } 
    
    public Banco update(Banco banco) throws Exception {
        validation(banco);
        return bancoDAO.update(banco);
    } 
    
    public Banco delete(Integer idBanco) throws Exception {
        if(!podeExcluir(idBanco)) throw new Exception("Este banco não pode ser excluído!"); 
        return bancoDAO.remove(idBanco);
    }    
    
    public boolean podeExcluir(Integer idBanco) throws Exception {
        List<ContaCorrente> contaCorrentes = contaCorrenteDAO.findByBanco(idBanco);
        if(!contaCorrentes.isEmpty()) return false;
        return true;                
    } 
    
    private void validation(Banco banco) throws Exception {  
        if(existeBanco(banco)) {
            throw new Exception("Este Banco já foi cadastrado!");
        } 
    }  
    
    private boolean existeBanco(Banco banco) throws Exception {
        Banco bancoNumero = bancoDAO.findByNumero(banco.getNumero());
        if(bancoNumero == null) return false;
        if(bancoNumero.getIdBanco().equals(banco.getIdBanco())) return false;
        return true;
    }
    
}
