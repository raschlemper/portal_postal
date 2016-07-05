package com.portalpostal.service;

import com.portalpostal.dao.ColaboradorDAO;
import com.portalpostal.model.Colaborador;
import com.portalpostal.model.Endereco;
import com.portalpostal.model.Favorecido;
import com.portalpostal.model.InformacaoBancaria;
import com.portalpostal.model.InformacaoProfissional;
import com.portalpostal.model.dd.TipoFavorecido;
import java.util.List;

public class ColaboradorService {
    
    private final String nomeBD;   
    
    private ColaboradorDAO colaboradorDAO;    
    private EnderecoService enderecoService; 
    private InformacaoProfissionalService informacaoProfissionalService; 
    private InformacaoBancariaService informacaoBancariaService; 
    private FavorecidoService favorecidoService;
    private LancamentoService lancamentoService;
    private LancamentoProgramadoService lancamentoProgramadoService;

    public ColaboradorService(String nomeBD) {
        this.nomeBD = nomeBD;
    }

    public void init() {
        colaboradorDAO = new ColaboradorDAO(nomeBD);
        enderecoService = new EnderecoService(nomeBD);
        informacaoProfissionalService = new InformacaoProfissionalService(nomeBD);
        informacaoBancariaService = new InformacaoBancariaService(nomeBD);
        favorecidoService = new FavorecidoService(nomeBD);
        lancamentoService = new LancamentoService(nomeBD);
        lancamentoProgramadoService = new LancamentoProgramadoService(nomeBD);
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
    
    public Colaborador findLancamento(Integer idColaborador) throws Exception {
        init();
        Colaborador colaborador = find(idColaborador);
        colaborador.setLancamentos(lancamentoService.findByColaborador(idColaborador));
        return colaborador;
    } 
    
    public Colaborador findLancamentoProgramado(Integer idColaborador) throws Exception {
        init();
        Colaborador colaborador = find(idColaborador);
        colaborador.setLancamentosProgramados(lancamentoProgramadoService.findByColaborador(idColaborador));
        return colaborador;
    } 
    
    public Colaborador save(Colaborador colaborador) throws Exception {
        init();
        validation(colaborador);
        Colaborador colaboradorResult = colaboradorDAO.save(colaborador);  
        colaboradorResult.setEndereco(colaborador.getEndereco());        
        colaboradorResult.setInformacaoProfissional(colaborador.getInformacaoProfissional());    
        colaboradorResult.setInformacaoBancaria(colaborador.getInformacaoBancaria());
        return saveOrUpdate(colaboradorResult);
    } 
    
    public Colaborador update(Colaborador colaborador) throws Exception {
        init();
        validation(colaborador);
        Colaborador colaboradorResult = colaboradorDAO.update(colaborador);
        colaboradorResult.setEndereco(colaborador.getEndereco());        
        colaboradorResult.setInformacaoProfissional(colaborador.getInformacaoProfissional());    
        colaboradorResult.setInformacaoBancaria(colaborador.getInformacaoBancaria());
        return saveOrUpdate(colaboradorResult);
    } 
    
    public Colaborador delete(Integer idColaborador) throws Exception {
        init();
        if(!podeExcluir(idColaborador)) throw new Exception("Este colaborador não pode ser excluído!"); 
        Colaborador colaborador = find(idColaborador);
        removerEndereco(colaborador);
        removerInformacaoProfissional(colaborador);
        removerInformacaoBancaria(colaborador);
        removerFavorecido(colaborador);
        return colaboradorDAO.remove(idColaborador);
    }    
    
    public boolean podeExcluir(Integer idColaborador) throws Exception {
        init();
        Favorecido favorecido = favorecidoService.findByColaborador(idColaborador);
        if(favorecido != null) return false;
        return true;                
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
    
    private Colaborador saveOrUpdate(Colaborador colaborador) throws Exception {
        if(colaborador == null) return colaborador;
        if(colaborador.getEndereco() != null) { colaborador = saveOrUpdateEndereco(colaborador); }
        if(colaborador.getInformacaoProfissional() != null) { colaborador = saveOrUpdateInformacaoProfissional(colaborador); }
        if(colaborador.getInformacaoBancaria() != null) { colaborador = saveOrUpdateInformacaoBancaria(colaborador); }
        saveOrUpdateFavorecido(colaborador);
        return find(colaborador.getIdColaborador());
    }
    
    // ***** ENDERECO ***** //
    
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
    
    private void removerEndereco(Colaborador colaborador) throws Exception {
        Endereco endereco = colaborador.getEndereco();
        if(endereco != null) {   
            enderecoService.update(endereco);            
        }
    }
    
    // ***** INFORMACAO PROFISSIONAL ***** //
    
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
    
    private void removerInformacaoProfissional(Colaborador colaborador) throws Exception {
        InformacaoProfissional informacaoProfissional = colaborador.getInformacaoProfissional();
        if(informacaoProfissional != null) {   
            informacaoProfissionalService.delete(informacaoProfissional.getIdInformacaoProfissional());            
        }
    }
    
    // ***** INFORMACAO BANCARIA ***** //
    
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
    
    private void removerInformacaoBancaria(Colaborador colaborador) throws Exception {
        InformacaoBancaria informacaoBancaria = colaborador.getInformacaoBancaria();
        if(informacaoBancaria != null) {   
            informacaoBancariaService.delete(informacaoBancaria.getIdInformacaoBancaria());            
        }
    }
    
    // ***** FAVORECIDO ***** //
    
    private Colaborador saveOrUpdateFavorecido(Colaborador colaborador) throws Exception {
        Favorecido favorecido = getFavorecido(colaborador);
        if(favorecido.getIdFavorecido() != null) {
            favorecido = favorecidoService.update(favorecido);
        } else {
            favorecido = favorecidoService.save(favorecido);
        }
        return colaborador;
    } 
    
    private Favorecido getFavorecido(Colaborador colaborador) throws Exception {
        Favorecido favorecido = favorecidoService.findByColaborador(colaborador.getIdColaborador());
        if(favorecido == null) { favorecido = new Favorecido(); }
        favorecido.setTipo(TipoFavorecido.COLABORADOR);
        favorecido.setNome(colaborador.getNome());
        favorecido.setColaborador(colaborador);
        return favorecido;
    }
    
    private void removerFavorecido(Colaborador colaborador) throws Exception {
        InformacaoBancaria informacaoBancaria = colaborador.getInformacaoBancaria();
        if(informacaoBancaria != null) {   
            favorecidoService.deleteByColaborador(colaborador.getIdColaborador());            
        }
    }
    
}
