package com.portalpostal.validation;

import com.portalpostal.model.CarteiraCobranca;

public class CarteiraCobrancaValidation extends Validation<CarteiraCobranca>{

    @Override
    public boolean validar(CarteiraCobranca contaCorrente) {
        if(!validarNome(contaCorrente)) return false;   
        if(!validarBeneficiario(contaCorrente)) return false;   
        if(!validarBeneficiarioDv(contaCorrente)) return false;   
        if(!validarCarteira(contaCorrente)) return false;   
        return true;
    }

    public boolean validarNome(CarteiraCobranca carteiraCobranca) {          
        if(campoNotNull(carteiraCobranca.getNome())) return true; 
        setMsg("Preencha o nome do cartão de crédito!");
        return false;        
    }  

    public boolean validarBeneficiario(CarteiraCobranca carteiraCobranca) {          
        if(campoNotNull(carteiraCobranca.getCodigoBeneficiario())) return true; 
        setMsg("Preencha o código do beneficiário da carteira de cobrança!");
        return false;        
    }   

    public boolean validarBeneficiarioDv(CarteiraCobranca carteiraCobranca) {          
        if(campoNotNull(carteiraCobranca.getCodigoBeneficiario())) return true; 
        setMsg("Preencha o dígito verificador (DV) do beneficiário da carteira de cobrança!");
        return false;        
    }  

    public boolean validarCarteira(CarteiraCobranca carteiraCobranca) {          
        if(campoNotNull(carteiraCobranca.getCodigoCarteira())) return true; 
        setMsg("Preencha o código da carteira de cobrança!");
        return false;        
    }  
    
}
