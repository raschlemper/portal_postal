package com.portalpostal.service;

import com.portalpostal.dao.FavorecidoDAO;
import com.portalpostal.model.Favorecido;
import com.portalpostal.model.Lancamento;
import com.portalpostal.model.LancamentoProgramado;
import java.util.List;

public class FavorecidoService {
    
    private final String nomeBD;   
    
    private FavorecidoDAO favorecidoDAO;  
    private LancamentoService lancamentoService;
    private LancamentoProgramadoService lancamentoProgramadoService;

    public FavorecidoService(String nomeBD) {
        this.nomeBD = nomeBD;
    }

    public void init() {
        favorecidoDAO = new FavorecidoDAO(nomeBD);
        lancamentoService = new LancamentoService(nomeBD);
        lancamentoProgramadoService = new LancamentoProgramadoService(nomeBD);
    }
    
    public List<Favorecido> findAll() throws Exception {
        init();
        return favorecidoDAO.findAll();
    }  
    
    public Favorecido find(Integer idFavorecido) throws Exception {
        init();
        return favorecidoDAO.find(idFavorecido);
    }  
    
    public Favorecido save(Favorecido favorecido) throws Exception {
        init();
        return favorecidoDAO.save(favorecido);
    } 
    
    public Favorecido update(Favorecido favorecido) throws Exception {
        init();
        return favorecidoDAO.update(favorecido);
    } 
    
    public Favorecido delete(Integer idFavorecido) throws Exception {
        init();
        if(!podeExcluir(idFavorecido)) throw new Exception("Este favorecido não pode ser excluído!"); 
        return favorecidoDAO.remove(idFavorecido);
    }   
    
    public boolean podeExcluir(Integer idFavorecido) throws Exception {
        init();
        List<Lancamento> lancamentos = lancamentoService.findByFavorecido(idFavorecido);
        if(!lancamentos.isEmpty()) return false;
        List<LancamentoProgramado> lancamentoProgramados = lancamentoProgramadoService.findByFavorecido(idFavorecido);
        if(!lancamentoProgramados.isEmpty()) return false;
        return true;                 
    }  
    
    // ***** COLABORADOR ***** // 
    
    public Favorecido findByColaborador(Integer idColaborador) throws Exception {
        init();
        return favorecidoDAO.findByColaborador(idColaborador);
    }   
    
    public void deleteByColaborador(Integer idFavorecido) throws Exception {
        init();
        if(!podeExcluir(idFavorecido)) throw new Exception("Este favorecido não pode ser excluído!"); 
        favorecidoDAO.removeByColaborador(idFavorecido);
    }    
    
    // ***** FORNECEDOR ***** // 
    
    public Favorecido findByFornecedor(Integer idFornecedor) throws Exception {
        init();
        return favorecidoDAO.findByFornecedor(idFornecedor);
    }   
    
    public void deleteByFornecedor(Integer idFavorecido) throws Exception {
        init();
        if(!podeExcluir(idFavorecido)) throw new Exception("Este favorecido não pode ser excluído!"); 
        favorecidoDAO.removeByFornecedor(idFavorecido);
    }  
    
    // ***** CLIENTE ***** // 
    
    public Favorecido findByCliente(Integer idCliente) throws Exception {
        init();
        return favorecidoDAO.findByCliente(idCliente);
    }   
    
    public void deleteByCliente(Integer idCliente) throws Exception {
        init();
        if(!podeExcluir(idCliente)) throw new Exception("Este favorecido não pode ser excluído!"); 
        favorecidoDAO.removeByCliente(idCliente);
    }  
    
}
