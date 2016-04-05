package com.portalpostal.dao;

import com.portalpostal.dao.handler.CarteiraCobrancaHandler;
import com.portalpostal.model.CarteiraCobranca;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarteiraCobrancaDAO extends GenericDAO { 
    
    private CarteiraCobrancaHandler carteiraCobrancaHandler;

    public CarteiraCobrancaDAO(String nameDB) { 
        super(nameDB, CarteiraCobrancaDAO.class);
        carteiraCobrancaHandler = new CarteiraCobrancaHandler();
    } 

    public List<CarteiraCobranca> findAll() throws Exception {
        String sql = "SELECT * FROM carteira_cobranca, conta_corrente "
                   + "WHERE carteira_cobranca.idContaCorrente = conta_corrente.idContaCorrente "
                   + "ORDER BY carteira_cobranca.idCarteira";        
        return findAll(sql, null, carteiraCobrancaHandler);
    }

    public CarteiraCobranca find(Integer idCarteiraCobranca) throws Exception {
        String sql = "SELECT * FROM carteira_cobranca, conta_corrente "
                   + "WHERE carteira_cobranca.idContaCorrente = conta_corrente.idContaCorrente "
                   + "AND carteira_cobranca.idCarteira = :idCarteiraCobranca";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idCarteiraCobranca", idCarteiraCobranca);
        return (CarteiraCobranca) find(sql, params, carteiraCobrancaHandler);
    }

    public CarteiraCobranca save(CarteiraCobranca carteiraCobranca) throws Exception {  
        String sql = "INSERT INTO carteira_cobranca (idContaCorrente, nome, cod_beneficiario, cod_beneficiario_dv, "
                   + "cod_convenio, cod_carteira, aceite, baixa, especie_doc, local_pagamento, instrucao01, instrucao02, "
                   + "instrucao03, instrucao04, instrucao05, beneficiario_nome, beneficiario_doc, beneficiario_logradouro, "
                   + "beneficiario_bairro, beneficiario_cidade, beneficiario_uf, beneficiario_cep) "
                   + "VALUES(:idContaCorrente, :nome, :codigoBeneficiario, :codigoBeneficiarioDv, :codigoConvenio, :codigoCarteira, "
                   + ":aceite, :baixa, :especieDocumento, :localPagamento, :instrucao01, :instrucao02, :instrucao03, :instrucao04, "
                   + ":instrucao05, :beneficiarioNome, :beneficiarioDocumento, :beneficiarioLogradouro, :beneficiarioBairro, "
                   + ":beneficiarioCidade, :beneficiarioUf, :beneficiarioCep)";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idContaCorrente", carteiraCobranca.getContaCorrente().getIdContaCorrente());
        params.put("nome", carteiraCobranca.getNome());
        params.put("codigoBeneficiario", carteiraCobranca.getCodigoBeneficiario());
        params.put("codigoBeneficiarioDv", carteiraCobranca.getCodigoBeneficiarioDv());
        params.put("codigoConvenio", carteiraCobranca.getCodigoConvenio());
        params.put("codigoCarteira", carteiraCobranca.getCodigoCarteira());
        params.put("aceite", carteiraCobranca.getAceite());      
        params.put("baixa", carteiraCobranca.getBaixa());     
        params.put("especieDocumento", carteiraCobranca.getEspecieDocumento()); 
        params.put("localPagamento", carteiraCobranca.getLocalPagamento()); 
        params.put("instrucao01", carteiraCobranca.getInstrucao01()); 
        params.put("instrucao02", carteiraCobranca.getInstrucao02()); 
        params.put("instrucao03", carteiraCobranca.getInstrucao03()); 
        params.put("instrucao04", carteiraCobranca.getInstrucao04()); 
        params.put("instrucao05", carteiraCobranca.getInstrucao05()); 
        params.put("beneficiarioNome", carteiraCobranca.getBeneficiarioNome()); 
        params.put("beneficiarioDocumento", carteiraCobranca.getBeneficiarioDocumento()); 
        params.put("beneficiarioLogradouro", carteiraCobranca.getBeneficiarioLogradouro()); 
        params.put("beneficiarioBairro", carteiraCobranca.getBeneficiarioBairro()); 
        params.put("beneficiarioCidade", carteiraCobranca.getBeneficiarioCidade()); 
        params.put("beneficiarioUf", carteiraCobranca.getBeneficiarioUf()); 
        params.put("beneficiarioCep", carteiraCobranca.getBeneficiarioCep()); 
        Integer idCarteiraCobranca = save(sql, params, carteiraCobrancaHandler);
        return find(idCarteiraCobranca);
    }

    public CarteiraCobranca update(CarteiraCobranca carteiraCobranca) throws Exception {
        String sql = "UPDATE carteira_cobranca "
                   + "SET idContaCorrente = :idContaCorrente, nome = :nome, cod_beneficiario = :codigoBeneficiario, "
                   + "cod_beneficiario_dv = :codigoBeneficiarioDv, cod_convenio = :codigoConvenio, cod_carteira = :codigoCarteira, "
                   + "aceite = :aceite, baixa = :baixa, especie_doc = :especieDocumento, local_pagamento = :localPagamento, "
                   + "instrucao01 = :instrucao01, instrucao02 = :instrucao02, instrucao03 = :instrucao03, instrucao04 = :instrucao04, "
                   + "instrucao05 = :instrucao05, beneficiario_nome = :beneficiarioNome, beneficiario_doc = :beneficiarioDocumento, "
                   + "beneficiario_logradouro = :beneficiarioLogradouro, beneficiario_bairro = :beneficiarioBairro, "
                   + "beneficiario_cidade = :beneficiarioCidade, beneficiario_uf = :beneficiarioUf, beneficiario_cep = :beneficiarioCep "
                   + "WHERE idCarteira = :idCarteiraCobranca ";        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idCarteiraCobranca", carteiraCobranca.getIdCarteiraCobranca());
        params.put("idContaCorrente", carteiraCobranca.getContaCorrente().getIdContaCorrente());
        params.put("nome", carteiraCobranca.getNome());
        params.put("codigoBeneficiario", carteiraCobranca.getCodigoBeneficiario());
        params.put("codigoBeneficiarioDv", carteiraCobranca.getCodigoBeneficiarioDv());
        params.put("codigoConvenio", carteiraCobranca.getCodigoConvenio());
        params.put("codigoCarteira", carteiraCobranca.getCodigoCarteira());
        params.put("aceite", carteiraCobranca.getAceite());      
        params.put("baixa", carteiraCobranca.getBaixa());     
        params.put("especieDocumento", carteiraCobranca.getEspecieDocumento()); 
        params.put("localPagamento", carteiraCobranca.getLocalPagamento()); 
        params.put("instrucao01", carteiraCobranca.getInstrucao01()); 
        params.put("instrucao02", carteiraCobranca.getInstrucao02()); 
        params.put("instrucao03", carteiraCobranca.getInstrucao03()); 
        params.put("instrucao04", carteiraCobranca.getInstrucao04()); 
        params.put("instrucao05", carteiraCobranca.getInstrucao05()); 
        params.put("beneficiarioNome", carteiraCobranca.getBeneficiarioNome()); 
        params.put("beneficiarioDocumento", carteiraCobranca.getBeneficiarioDocumento()); 
        params.put("beneficiarioLogradouro", carteiraCobranca.getBeneficiarioLogradouro()); 
        params.put("beneficiarioBairro", carteiraCobranca.getBeneficiarioBairro()); 
        params.put("beneficiarioCidade", carteiraCobranca.getBeneficiarioCidade()); 
        params.put("beneficiarioUf", carteiraCobranca.getBeneficiarioUf()); 
        params.put("beneficiarioCep", carteiraCobranca.getBeneficiarioCep()); 
        update(sql, params, carteiraCobrancaHandler);
        return carteiraCobranca;  
    }

    public CarteiraCobranca remove(Integer idCarteiraCobranca) throws Exception { 
        String sql = "DELETE FROM carteira_cobranca WHERE idCarteira = :idCarteiraCobranca ";
        CarteiraCobranca carteiraCobranca = find(idCarteiraCobranca);
        Map<String, Object> params = new HashMap<String, Object>();        
        params.put("idCarteiraCobranca", idCarteiraCobranca);
        remove(sql, params, carteiraCobrancaHandler);
        return carteiraCobranca;
    }

    public CarteiraCobranca findByCarteiraCobranca(Integer idContaCorrente, Integer codigoBeneficiario, 
            Integer codigoBeneficiarioDv, Integer codigoCarteira) throws Exception {     
        String sql = "SELECT * FROM carteira_cobranca, conta_corrente "
                   + "WHERE carteira_cobranca.idContaCorrente = conta_corrente.idContaCorrente "
                   + "AND carteira_cobranca.idContaCorrente = :idContaCorrente AND carteira_cobranca.cod_beneficiario = :codigoBeneficiario "
                   + "AND carteira_cobranca.cod_beneficiario_dv = :codigoBeneficiarioDv AND carteira_cobranca.cod_carteira = :codigoCarteira ";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("idContaCorrente", idContaCorrente);
        params.put("codigoBeneficiario", codigoBeneficiario);
        params.put("codigoBeneficiarioDv", codigoBeneficiarioDv);
        params.put("codigoCarteira", codigoCarteira);
        return (CarteiraCobranca) find(sql, params, carteiraCobrancaHandler);
    }
    
}
