package com.portalpostal.service;

import com.portalpostal.dao.BancoDAO;
import com.portalpostal.model.Banco;
import com.portalpostal.model.ContaCorrente;
import java.util.List;

public class BancoService {
    
    private final String nomeBD;   
    
    private BancoDAO bancoDAO;    
    private ContaCorrenteService contaCorrenteService;

    public BancoService(String nomeBD) {
        this.nomeBD = nomeBD;
    }

    public void init() {
        bancoDAO = new BancoDAO(nomeBD);
        contaCorrenteService = new ContaCorrenteService(nomeBD);
    }
    
    public List<Banco> findAll() throws Exception {
        init();
        return bancoDAO.findAll();
    }  
    
    public Banco find(Integer idBanco) throws Exception {
        init();
        return bancoDAO.find(idBanco);
    } 
    
    public Banco findContaCorrente(Integer idBanco) throws Exception {
        init();
        Banco banco = find(idBanco);
        banco.setContaCorrentes(contaCorrenteService.findByBanco(idBanco));
        return banco;
    } 
    
    public Banco findByNumero(Integer numero) throws Exception {
        init();
        return bancoDAO.findByNumero(numero);
    } 
    
    public Banco save(Banco banco) throws Exception {
        init();
        validation(banco);
        return bancoDAO.save(banco);
    } 
    
    public Banco update(Banco banco) throws Exception {
        init();
        validation(banco);
        return bancoDAO.update(banco);
    } 
    
    public Banco delete(Integer idBanco) throws Exception {
        init();
        if(!podeExcluir(idBanco)) throw new Exception("Este banco não pode ser excluído!"); 
        return bancoDAO.remove(idBanco);
    }    
    
    public boolean podeExcluir(Integer idBanco) throws Exception {
        init();
        List<ContaCorrente> contaCorrentes = contaCorrenteService.findByBanco(idBanco);
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
