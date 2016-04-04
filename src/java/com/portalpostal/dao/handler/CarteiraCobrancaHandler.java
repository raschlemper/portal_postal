package com.portalpostal.dao.handler;

import com.portalpostal.model.CarteiraCobranca;
import com.portalpostal.model.ContaCorrente;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sql2o.ResultSetHandler;

public class CarteiraCobrancaHandler extends GenericHandler implements ResultSetHandler<CarteiraCobranca> {
        
    public CarteiraCobrancaHandler() {
        super("carteira_cobranca");
    }
    
    public CarteiraCobrancaHandler(String table) {
        super(table);
    }

    public CarteiraCobranca handle(ResultSet result) throws SQLException {
        CarteiraCobranca carteiraCobranca = new CarteiraCobranca();
        carteiraCobranca.setIdCarteiraCobranca(getInt(result, "idCarteira"));
        carteiraCobranca.setNome(getString(result, "nome"));
        carteiraCobranca.setContaCorrente(getContaCorrente(result));
        carteiraCobranca.setCodigoBeneficiario(getInt(result, "cod_beneficiario"));
        carteiraCobranca.setCodigoBeneficiarioDv(getInt(result, "cod_beneficiario_dv"));
        carteiraCobranca.setCodigoConvenio(getInt(result, "cod_convenio"));
        carteiraCobranca.setCodigoCarteira(getInt(result, "cod_carteira"));
        carteiraCobranca.setAceite(getBoolean(result, "aceite"));
        carteiraCobranca.setBaixa(getBoolean(result, "baixa"));
        carteiraCobranca.setEspecieDocumento(getString(result, "especie_doc"));
        carteiraCobranca.setLocalPagamento(getString(result, "local_pagamento"));
        carteiraCobranca.setInstrucao01(getString(result, "instrucao01"));
        carteiraCobranca.setInstrucao02(getString(result, "instrucao02"));
        carteiraCobranca.setInstrucao03(getString(result, "instrucao03"));
        carteiraCobranca.setInstrucao04(getString(result, "instrucao04"));
        carteiraCobranca.setInstrucao05(getString(result, "instrucao05"));
        carteiraCobranca.setBeneficiarioNome(getString(result, "beneficiario_nome"));
        carteiraCobranca.setBeneficiarioDocumento(getString(result, "beneficiario_doc"));
        carteiraCobranca.setBeneficiarioLogradouro(getString(result, "beneficiario_logradouro"));
        carteiraCobranca.setBeneficiarioBairro(getString(result, "beneficiario_bairro"));
        carteiraCobranca.setBeneficiarioCidade(getString(result, "beneficiario_cidade"));
        carteiraCobranca.setBeneficiarioUf(getString(result, "beneficiario_uf"));
        carteiraCobranca.setBeneficiarioCep(getString(result, "beneficiario_cep"));
        
        return carteiraCobranca;
    }
    
    private ContaCorrente getContaCorrente(ResultSet result) throws SQLException {
        if(!existColumn(result, "conta_corrente.idContaCorrente")) return null;  
        return new ContaCorrenteHandler().handle(result);
    }
    
}
