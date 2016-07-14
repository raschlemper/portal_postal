'use strict';

app.factory('FornecedorHandler', function() {
    
    var handleList = function(fornecedorList) { 
        return _.map(fornecedorList, function(fornecedor) {
            return handle(fornecedor);
        });
    };
    
    var handle = function(fornecedor) {  
        var fornecedorHandle = {};
        fornecedorHandle.idFornecedor = getId(fornecedor); 
        fornecedorHandle.nomeFantasia = getNomeFantasia(fornecedor);
        fornecedorHandle.razaoSocial = getRazaoSocial(fornecedor);
        fornecedorHandle.status = getStatus(fornecedor);
        fornecedorHandle.tipoPessoa = getTipoPessoa(fornecedor);
        fornecedorHandle.cpf = getCpf(fornecedor);
        fornecedorHandle.rg = getRg(fornecedor);
        fornecedorHandle.cnpj = getCnpj(fornecedor);
        fornecedorHandle.sexo = getSexo(fornecedor);
        fornecedorHandle.dataNascimento = getDataNascimento(fornecedor);
        fornecedorHandle.inscricaoEstadual = getInscricaoEstadual(fornecedor);
        fornecedorHandle.dataFundacao = getDataFundacao(fornecedor);
        fornecedorHandle.capitalSocial = getCapitalSocial(fornecedor);
        fornecedorHandle.categoria = getCategoria(fornecedor);
        fornecedorHandle.telefone = getTelefone(fornecedor);
        fornecedorHandle.celular = getCelular(fornecedor);
        fornecedorHandle.email = getEmail(fornecedor);
        fornecedorHandle.observacao = getObservacao(fornecedor);
        return fornecedorHandle;
    };
    
    var getId = function(fornecedor) {
        return fornecedor.idFornecedor || null;
    };
    
    var getNomeFantasia = function(fornecedor) {
        return fornecedor.nomeFantasia || null; 
    };
    
    var getRazaoSocial = function(fornecedor) {
        return fornecedor.razaoSocial || null; 
    };
    
    var getStatus = function(fornecedor) {
        if(!fornecedor.status) return fornecedor.status;
        return fornecedor.status.id; 
    };
    
    var getTipoPessoa = function(fornecedor) {
        if(!fornecedor.tipoPessoa) return fornecedor.tipoPessoa;
        return fornecedor.tipoPessoa.id; 
    };
    
    var getCpf = function(fornecedor) {
        return fornecedor.cpf || null; 
    };
    
    var getRg = function(fornecedor) {
        return fornecedor.rg || null; 
    };
    
    var getCnpj = function(fornecedor) {
        return fornecedor.cnpj || null; 
    };
    
    var getSexo = function(fornecedor) {
        if(!fornecedor.sexo) return fornecedor.sexo;
        return fornecedor.sexo.id; 
    };
    
    var getDataNascimento = function(fornecedor) {
        return fornecedor.dataNascimento || null; 
    };
    
    var getInscricaoEstadual = function(fornecedor) {
        return fornecedor.inscricaoEstadual || null; 
    };
    
    var getDataFundacao = function(fornecedor) {
        return fornecedor.dataFundacao || null; 
    };
    
    var getCapitalSocial = function(fornecedor) {
        return fornecedor.capitalSocial || null; 
    };
    
    var getCategoria = function(fornecedor) {
        if(!fornecedor.categoria) return fornecedor.categoria;
        return { idTipoCategoria: fornecedor.categoria.idTipoCategoria };
    };
    
    var getTelefone = function(fornecedor) {
        return fornecedor.telefone || null; 
    };
    
    var getCelular = function(fornecedor) {
        return fornecedor.celular || null; 
    };
    
    var getEmail = function(fornecedor) {
        return fornecedor.email || null; 
    };
    
    var getObservacao = function(fornecedor) {
        return fornecedor.observacao || null; 
    };

    return {
        handle: handle,
        handleList: handleList
    }

});