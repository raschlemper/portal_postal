package com.portalpostal.service;

import com.portalpostal.dao.EnderecoDAO;
import com.portalpostal.model.Endereco;
import com.portalpostal.model.ContaCorrente;
import java.util.List;

public class EnderecoService {
    
    private final String nomeBD;   
    
    private EnderecoDAO enderecoDAO;  
    private ColaboradorService colaboradorService;  

    public EnderecoService(String nomeBD) {
        this.nomeBD = nomeBD;
    }

    public void init() {
        enderecoDAO = new EnderecoDAO(nomeBD);
        colaboradorService = new ColaboradorService(nomeBD);
    }
    
    public List<Endereco> findAll() throws Exception {
        init();
        return enderecoDAO.findAll();
    }  
    
    public Endereco find(Integer idEndereco) throws Exception {
        init();
        return enderecoDAO.find(idEndereco);
    }  
    
    public List<Endereco> findByColaborador(Integer idColaborador) throws Exception {
        init();
        return enderecoDAO.findByColaborador(idColaborador);
    }  
    
    public Endereco save(Endereco endereco) throws Exception {
        init();
        return enderecoDAO.save(endereco);
    } 
    
    public Endereco update(Endereco endereco) throws Exception {
        init();
        return enderecoDAO.update(endereco);
    } 
    
    public Endereco delete(Integer idEndereco) throws Exception {
        init();
        if(!podeExcluir(idEndereco)) throw new Exception("Este endereco não pode ser excluído!"); 
        return enderecoDAO.remove(idEndereco);
    }    
    
    public boolean podeExcluir(Integer idEndereco) throws Exception {
        init();
        Endereco enderecos = enderecoDAO.findVinculoColaborador(idEndereco);
        if(enderecos == null) return false;
        return true;                
    } 
    
}
