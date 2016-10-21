package com.portalpostal.validation;

import com.portalpostal.model.LancamentoAnexo;

public class LancamentoAnexoValidation extends Validation<LancamentoAnexo>{

    @Override
    public boolean validar(LancamentoAnexo lancamentoAnexo) {
        if(!validarLancamento(lancamentoAnexo)) return false;   
        if(!validarNome(lancamentoAnexo)) return false;   
        if(!validarTamanho(lancamentoAnexo)) return false; 
        if(!validarTipo(lancamentoAnexo)) return false; 
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

    public boolean validarTamanho(LancamentoAnexo lancamentoAnexo) {          
        if(lancamentoAnexo.getSize() <= 102400) return true; 
        setMsg("Este arquivo esta acima do tamanho permitido!");
        return false;        
    }    

    public boolean validarTipo(LancamentoAnexo lancamentoAnexo) {          
        if(lancamentoAnexo.getTipo().equals("png") || lancamentoAnexo.getTipo().equals("jpg") || 
                lancamentoAnexo.getTipo().equals("gif") || lancamentoAnexo.getTipo().equals("pdf")) return true; 
        setMsg("Este tipo de arquivo não é permitido!");
        return false;        
    }    
 

    public boolean validarAnexo(LancamentoAnexo lancamentoAnexo) {        
        if(campoNotNull(lancamentoAnexo.getAnexo())) return true; 
        setMsg("Selecione o anexo!");  
        return false;        
    }  
    
}
