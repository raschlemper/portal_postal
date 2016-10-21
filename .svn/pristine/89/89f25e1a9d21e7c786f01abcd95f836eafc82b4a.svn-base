'use strict';

app.factory('ColaboradorHandler', function() {
    
    var handleList = function(colaboradorList) { 
        return _.map(colaboradorList, function(colaborador) {
            return handle(colaborador);
        });
    };
    
    var handle = function(colaborador) {  
        var colaboradorHandle = {};
        colaboradorHandle.idColaborador = getId(colaborador); 
        colaboradorHandle.nome = getNome(colaborador);
        colaboradorHandle.status = getStatus(colaborador);
        colaboradorHandle.cpf = getCpf(colaborador);
        colaboradorHandle.rg = getRg(colaborador);
        colaboradorHandle.sexo = getSexo(colaborador);
        colaboradorHandle.dataNascimento = getDataNascimento(colaborador);
        colaboradorHandle.telefone = getTelefone(colaborador);
        colaboradorHandle.celular = getCelular(colaborador);
        colaboradorHandle.email = getEmail(colaborador);
        colaboradorHandle.conjuge = getConjuge(colaborador);
        colaboradorHandle.estadoCivil = getEstadoCivil(colaborador);
        colaboradorHandle.naturalidade = getNaturalidade(colaborador);
        colaboradorHandle.nacionalidade = getNacionalidade(colaborador);
        colaboradorHandle.quantidadeDependente = getQuantidadeDependente(colaborador);
        colaboradorHandle.nomePai = getNomePai(colaborador);
        colaboradorHandle.nomeMae = getNomeMae(colaborador);
        colaboradorHandle.observacao = getObservacao(colaborador);
        return colaboradorHandle;
    };
    
    var getId = function(colaborador) {
        return colaborador.idColaborador || null;
    };
    
    var getNome = function(colaborador) {
        return colaborador.nome || null; 
    };
    
    var getStatus = function(colaborador) {
        if(!colaborador.status) return colaborador.status;
        return colaborador.status.id; 
    };
    
    var getCpf = function(colaborador) {
        return colaborador.cpf || null; 
    };
    
    var getRg = function(colaborador) {
        return colaborador.rg || null; 
    };
    
    var getSexo = function(colaborador) {
        if(!colaborador.sexo) return colaborador.sexo;
        return colaborador.sexo.id; 
    };
    
    var getDataNascimento = function(colaborador) {
        return colaborador.dataNascimento || null; 
    };
    
    var getTelefone = function(colaborador) {
        return colaborador.telefone || null; 
    };
    
    var getCelular = function(colaborador) {
        return colaborador.celular || null; 
    };
    
    var getEmail = function(colaborador) {
        return colaborador.email || null; 
    };
    
    var getConjuge = function(colaborador) {
        return colaborador.conjuge || null; 
    };
    
    var getEstadoCivil = function(colaborador) {
        if(!colaborador.estadoCivil) return colaborador.estadoCivil;
        return colaborador.estadoCivil.id; 
    };
    
    var getNaturalidade = function(colaborador) {
        return colaborador.naturalidade || null; 
    };
    
    var getNacionalidade = function(colaborador) {
        return colaborador.nacionalidade || null; 
    };
    
    var getQuantidadeDependente = function(colaborador) {
        return colaborador.quantidadeDependente || null; 
    };
    
    var getNomePai = function(colaborador) {
        return colaborador.nomePai || null; 
    };
    
    var getNomeMae = function(colaborador) {
        return colaborador.nomeMae || null; 
    };
    
    var getObservacao = function(colaborador) {
        return colaborador.observacao || null; 
    };

    return {
        handle: handle,
        handleList: handleList
    }

});