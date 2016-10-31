package com.portalpostal.service;

import com.portalpostal.dao.EnderecoDAO;
import com.portalpostal.model.Endereco;
import java.util.List;

public class EnderecoService {
    
    private final String nomeBD;   
    
    private EnderecoDAO enderecoDAO;  

    public EnderecoService(String nomeBD) {
        this.nomeBD = nomeBD;
    }

    public void init() {
        enderecoDAO = new EnderecoDAO(nomeBD);
    }
    
    public List<Endereco> findAll() throws Exception {
        init();
        return enderecoDAO.findAll();
    }  
    
    public Endereco find(Integer idEndereco) throws Exception {
        init();
        return enderecoDAO.find(idEndereco);
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
        Endereco enderecoColaborador = enderecoDAO.findVinculoColaborador(idEndereco);
        if(enderecoColaborador != null) return false;
        Endereco enderecoFornecedor = enderecoDAO.findVinculoFornecedor(idEndereco);
        if(enderecoFornecedor != null) return false;
        return true;                
    } 
    
    // ***** COLABORADOR ***** // 
    
    public List<Endereco> findByColaborador(Integer idColaborador) throws Exception {
        init();
        return enderecoDAO.findByColaborador(idColaborador);
    } 
    
    public Endereco saveColaborador(Integer idColaborador, Endereco endereco) throws Exception {
        init();
        endereco = save(endereco);
        enderecoDAO.saveColaborador(idColaborador, endereco.getIdEndereco());
        return endereco;
    } 
    
    public Endereco deleteColaborador(Integer idColaborador, Integer idEndereco) throws Exception {
        init();      
        enderecoDAO.removeColaborador(idColaborador, idEndereco);
        return enderecoDAO.remove(idEndereco);
    }  
    
    // ***** FORNACEDOR ***** // 
    
    public List<Endereco> findByFornecedor(Integer idFornecedor) throws Exception {
        init();
        return enderecoDAO.findByFornecedor(idFornecedor);
    } 
    
    public Endereco saveFornecedor(Integer idFornecedor, Endereco endereco) throws Exception {
        init();
        endereco = save(endereco);
        enderecoDAO.saveFornecedor(idFornecedor, endereco.getIdEndereco());
        return endereco;
    } 
    
    public Endereco deleteFornecedor(Integer idFornecedor, Integer idEndereco) throws Exception {
        init();      
        enderecoDAO.removeFornecedor(idFornecedor, idEndereco);
        return enderecoDAO.remove(idEndereco);
    } 
    
}
