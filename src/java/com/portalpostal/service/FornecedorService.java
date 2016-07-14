package com.portalpostal.service;

import com.portalpostal.dao.FornecedorDAO;
import com.portalpostal.model.Fornecedor;
import com.portalpostal.model.Endereco;
import com.portalpostal.model.Favorecido;
import com.portalpostal.model.InformacaoBancaria;
import com.portalpostal.model.InformacaoProfissional;
import com.portalpostal.model.dd.TipoFavorecido;
import java.util.List;

public class FornecedorService {
    
    private final String nomeBD;   
    
    private FornecedorDAO fornecedorDAO;    
    private EnderecoService enderecoService; 
    private FavorecidoService favorecidoService;
    private LancamentoService lancamentoService;
    private LancamentoProgramadoService lancamentoProgramadoService;

    public FornecedorService(String nomeBD) {
        this.nomeBD = nomeBD;
    }

    public void init() {
        fornecedorDAO = new FornecedorDAO(nomeBD);
        enderecoService = new EnderecoService(nomeBD);
        favorecidoService = new FavorecidoService(nomeBD);
        lancamentoService = new LancamentoService(nomeBD);
        lancamentoProgramadoService = new LancamentoProgramadoService(nomeBD);
    }
    
    public List<Fornecedor> findAll() throws Exception {
        init();
        List<Fornecedor> fornecedores = fornecedorDAO.findAll();
        fornecedores = setEnderecos(fornecedores);
        return fornecedores;
    }  
    
    public Fornecedor find(Integer idFornecedor) throws Exception {
        init();
        Fornecedor fornecedor = fornecedorDAO.find(idFornecedor);
        fornecedor = setEndereco(fornecedor);
        return fornecedor;
    }  
    
    public Fornecedor findLancamento(Integer idFornecedor) throws Exception {
        init();
        Fornecedor fornecedor = find(idFornecedor);
        fornecedor.setLancamentos(lancamentoService.findByFornecedor(idFornecedor));
        return fornecedor;
    } 
    
    public Fornecedor findLancamentoProgramado(Integer idFornecedor) throws Exception {
        init();
        Fornecedor fornecedor = find(idFornecedor);
        fornecedor.setLancamentosProgramados(lancamentoProgramadoService.findByFornecedor(idFornecedor));
        return fornecedor;
    } 
    
    public Fornecedor save(Fornecedor fornecedor) throws Exception {
        init();
        validation(fornecedor);
        Fornecedor fornecedorResult = fornecedorDAO.save(fornecedor);  
        fornecedorResult.setEndereco(fornecedor.getEndereco());   
        return saveOrUpdate(fornecedorResult);
    } 
    
    public Fornecedor update(Fornecedor fornecedor) throws Exception {
        init();
        validation(fornecedor);
        Fornecedor fornecedorResult = fornecedorDAO.update(fornecedor);
        fornecedorResult.setEndereco(fornecedor.getEndereco());   
        return saveOrUpdate(fornecedorResult);
    } 
    
    public Fornecedor delete(Integer idFornecedor) throws Exception {
        init();
        if(!podeExcluir(idFornecedor)) throw new Exception("Este fornecedor não pode ser excluído!"); 
        Fornecedor fornecedor = find(idFornecedor);
        removerEndereco(fornecedor);
        removerFavorecido(fornecedor);
        return fornecedorDAO.remove(idFornecedor);
    }    
    
    public boolean podeExcluir(Integer idFornecedor) throws Exception {
        init();
        Favorecido favorecido = favorecidoService.findByFornecedor(idFornecedor);
        if(favorecido == null) return true;        
        if(!favorecidoService.podeExcluir(idFornecedor)) return false;
        return true;                
    } 
    
    private void validation(Fornecedor fornecedor) throws Exception {  
        if(existeFornecedor(fornecedor)) {
            throw new Exception("Este Fornecedor já foi cadastrado!");
        } 
    }  
    
    private boolean existeFornecedor(Fornecedor fornecedor) throws Exception {
        Fornecedor fornecedorCpf = fornecedorDAO.findByCpf(fornecedor.getCpf());
        if(fornecedorCpf == null) return false;
        if(fornecedorCpf.getIdFornecedor().equals(fornecedor.getIdFornecedor())) return false;
        return true;
    }
    
    private Fornecedor saveOrUpdate(Fornecedor fornecedor) throws Exception {
        if(fornecedor == null) return fornecedor;
        if(fornecedor.getEndereco() != null) { fornecedor = saveOrUpdateEndereco(fornecedor); }
        saveOrUpdateFavorecido(fornecedor);
        return find(fornecedor.getIdFornecedor());
    }
    
    // ***** ENDERECO ***** //
    
    private List<Fornecedor> setEnderecos(List<Fornecedor> fornecedores) throws Exception {
        for (Fornecedor fornecedor : fornecedores) {
            setEndereco(fornecedor);
        }
        return fornecedores;
    }
    
    private Fornecedor setEndereco(Fornecedor fornecedor) throws Exception {
        fornecedor.setEndereco(getEnderecos(fornecedor.getIdFornecedor()));
        return fornecedor;
    }
    
    private Endereco getEnderecos(Integer idFornecedor) throws Exception {
        List<Endereco> enderecos = enderecoService.findByFornecedor(idFornecedor);
        if(enderecos != null && !enderecos.isEmpty()) return enderecos.get(0);
        return null;
    } 
    
    private Fornecedor saveOrUpdateEndereco(Fornecedor fornecedor) throws Exception {
        Endereco endereco = fornecedor.getEndereco();
        if(endereco.getIdEndereco() != null) {
            endereco = enderecoService.update(endereco);
        } else {
            endereco = enderecoService.saveFornecedor(fornecedor.getIdFornecedor(), endereco);
        }
        fornecedor.setEndereco(endereco);
        return fornecedor;
    } 
    
    private void removerEndereco(Fornecedor fornecedor) throws Exception {
        Endereco endereco = fornecedor.getEndereco();
        if(endereco != null) {     
            enderecoService.deleteFornecedor(fornecedor.getIdFornecedor(), endereco.getIdEndereco());    
            enderecoService.delete(endereco.getIdEndereco());           
        }
    }
    
    // ***** FAVORECIDO ***** //
    
    private Fornecedor saveOrUpdateFavorecido(Fornecedor fornecedor) throws Exception {
        Favorecido favorecido = getFavorecido(fornecedor);
        if(favorecido.getIdFavorecido() != null) {
            favorecido = favorecidoService.update(favorecido);
        } else {
            favorecido = favorecidoService.save(favorecido);
        }
        return fornecedor;
    } 
    
    private Favorecido getFavorecido(Fornecedor fornecedor) throws Exception {
        Favorecido favorecido = favorecidoService.findByFornecedor(fornecedor.getIdFornecedor());
        if(favorecido == null) { favorecido = new Favorecido(); }
        favorecido.setTipo(TipoFavorecido.FORNECEDOR);
        favorecido.setNome(fornecedor.getNomeFantasia());
        favorecido.setFornecedor(fornecedor);
        return favorecido;
    }
    
    private void removerFavorecido(Fornecedor fornecedor) throws Exception {
        Favorecido favorecido = getFavorecido(fornecedor);
        if(favorecido != null) {   
            favorecidoService.deleteByFornecedor(fornecedor.getIdFornecedor());            
        }
    }
    
}
