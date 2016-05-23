package com.portalpostal.validation;

import com.portalpostal.model.Lancamento;
import com.portalpostal.model.LancamentoAnexo;

public class LancamentoAnexoValidation extends Validation<LancamentoAnexo>{

    @Override
    public boolean validar(LancamentoAnexo lancamentoAnexo) {
        if(!validarLancamento(lancamentoAnexo)) return false;   
        if(!validarNome(lancamentoAnexo)) return false; 
        if(!validarAnexo(lancamentoAnexo)) return false; 
        return true;
    }    

    public boolean validarLancamento(LancamentoAnexo lancamentoAnexo) {          
        if(campoNotNull(lancamentoAnexo.getLancamento())) return true; 
        setMsg("Preencha o lançamento do anexo!");
        return false;        
    }    

    public boolean validarNome(LancamentoAnexo lancamentoAnexo) {          
        if(campoNotNull(lancamentoAnexo.getNome())) return true; 
        setMsg("Preencha o nome do anexo!");
        return false;        
    }    

    public boolean validarAnexo(LancamentoAnexo lancamentoAnexo) {        
        if(campoNotNull(lancamentoAnexo.getAnexo())) return true; 
        setMsg("Selecione o anexo!");  
        return false;        
    }  
    
}
