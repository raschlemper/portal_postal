package com.portalpostal.service;

import com.portalpostal.dao.ColaboradorDAO;
import com.portalpostal.model.Colaborador;
import com.portalpostal.model.Endereco;
import com.portalpostal.model.Favorecido;
import com.portalpostal.model.InformacaoBancaria;
import com.portalpostal.model.InformacaoProfissional;
import com.portalpostal.model.Lancamento;
import com.portalpostal.model.LancamentoProgramado;
import java.util.List;

public class ColaboradorService {
    
    private final String nomeBD;   
    
    private ColaboradorDAO colaboradorDAO;    
    private EnderecoService enderecoService; 
    private InformacaoProfissionalService informacaoProfissionalService; 
    private InformacaoBancariaService informacaoBancariaService; 
    private FavorecidoService favorecidoService;

    public ColaboradorService(String nomeBD) {
        this.nomeBD = nomeBD;
    }

    public void init() {
        colaboradorDAO = new ColaboradorDAO(nomeBD);
        enderecoService = new EnderecoService(nomeBD);
        informacaoProfissionalService = new InformacaoProfissionalService(nomeBD);
        informacaoBancariaService = new InformacaoBancariaService(nomeBD);
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
        colaboradorDAO.save(colaborador);
        return saveOrUpdate(colaborador);
    } 
    
    public Colaborador update(Colaborador colaborador) throws Exception {
        init();
        validation(colaborador);
        colaboradorDAO.update(colaborador);
        return saveOrUpdate(colaborador);
    } 
    
    public Colaborador delete(Integer idColaborador) throws Exception {
        init();
        if(!podeExcluir(idColaborador)) throw new Exception("Este colaborador não pode ser excluído!"); 
        Colaborador colaborador = find(idColaborador);
        removerEndereco(colaborador);
        removerInformacaoProfissional(colaborador);
        removerInformacaoBancaria(colaborador);
        return colaboradorDAO.remove(idColaborador);
    }    
    
    public boolean podeExcluir(Integer idColaborador) throws Exception {
        init();
        Favorecido favorecido = favorecidoService.findByColaborador(idColaborador);
        if(favorecido != null) return false;
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
    
    private Colaborador saveOrUpdate(Colaborador colaborador) throws Exception {
        if(colaborador == null) return colaborador;
        if(colaborador.getEndereco() != null) { colaborador = saveOrUpdateEndereco(colaborador); }
        if(colaborador.getInformacaoProfissional() != null) { colaborador = saveOrUpdateInformacaoProfissional(colaborador); }
        if(colaborador.getInformacaoBancaria() != null) { colaborador = saveOrUpdateInformacaoBancaria(colaborador); }
        return colaborador;
    } 
    
    private Colaborador saveOrUpdateEndereco(Colaborador colaborador) throws Exception {
        Endereco endereco = colaborador.getEndereco();
        if(endereco.getIdEndereco() != null) {
            endereco = enderecoService.update(endereco);
        } else {
            endereco = enderecoService.save(endereco);
        }
        colaborador.setEndereco(endereco);
        return colaborador;
    } 
    
    private Colaborador saveOrUpdateInformacaoProfissional(Colaborador colaborador) throws Exception {
        InformacaoProfissional informacaoProfissional = colaborador.getInformacaoProfissional();
        informacaoProfissional.setColaborador(colaborador);
        if(informacaoProfissional.getIdInformacaoProfissional() != null) {
            informacaoProfissional = informacaoProfissionalService.update(informacaoProfissional);
        } else {
            informacaoProfissional = informacaoProfissionalService.save(informacaoProfissional);
        }
        colaborador.setInformacaoProfissional(informacaoProfissional);
        return colaborador;
    } 
    
    private Colaborador saveOrUpdateInformacaoBancaria(Colaborador colaborador) throws Exception {
        InformacaoBancaria informacaoBancaria = colaborador.getInformacaoBancaria();
        informacaoBancaria.setColaborador(colaborador);
        if(informacaoBancaria.getIdInformacaoBancaria() != null) {
            informacaoBancaria = informacaoBancariaService.update(informacaoBancaria);
        } else {
            informacaoBancaria = informacaoBancariaService.save(informacaoBancaria);
        }
        colaborador.setInformacaoBancaria(informacaoBancaria);
        return colaborador;
    } 
    
    private void removerEndereco(Colaborador colaborador) throws Exception {
        Endereco endereco = colaborador.getEndereco();
        if(endereco != null) {   
            enderecoService.update(endereco);            
        }
    }
    
    private void removerInformacaoProfissional(Colaborador colaborador) throws Exception {
        InformacaoProfissional informacaoProfissional = colaborador.getInformacaoProfissional();
        if(informacaoProfissional != null) {   
            informacaoProfissionalService.update(informacaoProfissional);            
        }
    }
    
    private void removerInformacaoBancaria(Colaborador colaborador) throws Exception {
        InformacaoBancaria informacaoBancaria = colaborador.getInformacaoBancaria();
        if(informacaoBancaria != null) {   
            informacaoBancariaService.update(informacaoBancaria);            
        }
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
