package com.portalpostal.service;

import com.portalpostal.dao.ColaboradorDAO;
import com.portalpostal.model.Colaborador;
import com.portalpostal.model.Endereco;
import com.portalpostal.model.Lancamento;
import java.util.List;

public class ColaboradorService {
    
    private final String nomeBD;   
    
    private ColaboradorDAO colaboradorDAO;    
    private EnderecoService enderecoService; 
    private FavorecidoService favorecidoService;

    public ColaboradorService(String nomeBD) {
        this.nomeBD = nomeBD;
    }

    public void init() {
        colaboradorDAO = new ColaboradorDAO(nomeBD);
        enderecoService = new EnderecoService(nomeBD);
        favorecidoService = new FavorecidoService(nomeBD);
    }
    
    public List<Colaborador> findAll() throws Exception {
        init();
        List<Colaborador> colaboradores = colaboradorDAO.findAll();
        colaboradores = setEnderecos(colaboradores);
        return colaboradores;
    }  
    
    public Colaborador find(Integer idColaborador) throws Exception {
        init();
        Colaborador colaborador = colaboradorDAO.find(idColaborador);
        colaborador = setEndereco(colaborador);
        return colaborador;
    } 
    
    public Colaborador save(Colaborador colaborador) throws Exception {
        init();
        validation(colaborador);
        return colaboradorDAO.save(colaborador);
    } 
    
    public Colaborador update(Colaborador colaborador) throws Exception {
        init();
        validation(colaborador);
        return colaboradorDAO.update(colaborador);
    } 
    
    public Colaborador delete(Integer idColaborador) throws Exception {
        init();
        if(!podeExcluir(idColaborador)) throw new Exception("Este colaborador não pode ser excluído!"); 
        return colaboradorDAO.remove(idColaborador);
    }    
    
    public boolean podeExcluir(Integer idColaborador) throws Exception {
        init();
        Favorecido favorecido = favorecidoService.findByColaborador(idColaborador);
        if(favorecido == null) return false;
        return true;                
    } 
    
    private List<Colaborador> setEnderecos(List<Colaborador> colaboradores) throws Exception {
        for (Colaborador colaborador : colaboradores) {
            setEndereco(colaborador);
        }
        return colaboradores;
    }
    
    private Colaborador setEndereco(Colaborador colaborador) throws Exception {
        colaborador.setEndereco(getEnderecos(colaborador.getIdColaborador()));
        return colaborador;
    }
    
    private Endereco getEnderecos(Integer idColaborador) throws Exception {
        List<Endereco> enderecos = enderecoService.findByColaborador(idColaborador);
        if(enderecos != null && !enderecos.isEmpty()) return enderecos.get(0);
        return null;
    }
    
    private void validation(Colaborador colaborador) throws Exception {  
        if(existeColaborador(colaborador)) {
            throw new Exception("Este Colaborador já foi cadastrado!");
        } 
    }  
    
    private boolean existeColaborador(Colaborador colaborador) throws Exception {
        Colaborador colaboradorCpf = colaboradorDAO.findByCpf(colaborador.getCpf());
        if(colaboradorCpf == null) return false;
        if(colaboradorCpf.getIdColaborador().equals(colaborador.getIdColaborador())) return false;
        return true;
    }
    
}
