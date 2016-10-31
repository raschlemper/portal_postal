'use strict';

app.factory('InformacaoBancariaHandler', function() {
    
    var handleList = function(informacaoBancariaList) { 
        return _.map(informacaoBancariaList, function(informacaoBancaria) {
            return handle(informacaoBancaria);
        });
    };
    
    var handle = function(informacaoBancaria) {  
        var informacaoBancariaHandle = {};
        informacaoBancariaHandle.idInformacaoBancaria = getId(informacaoBancaria); 
        informacaoBancariaHandle.colaborador = getColaborador(informacaoBancaria);
        informacaoBancariaHandle.tipoConta = getTipoConta(informacaoBancaria);
        informacaoBancariaHandle.banco = getBanco(informacaoBancaria);
        informacaoBancariaHandle.agencia = getAgencia(informacaoBancaria);
        informacaoBancariaHandle.agenciaDv = getAgenciaDv(informacaoBancaria);
        informacaoBancariaHandle.contaCorrente = getContaCorrente(informacaoBancaria);
        informacaoBancariaHandle.contaCorrenteDv = getContaCorrenteDv(informacaoBancaria);
        return informacaoBancariaHandle;
    };
    
    var getId = function(informacaoBancaria) {
        return informacaoBancaria.idInformacaoBancaria || null;
    };
    
    var getColaborador = function(informacaoBancaria) {
        if(!informacaoBancaria.colaborador) return informacaoBancaria.colaborador;
        return { idColaborador: informacaoBancaria.colaborador.idColaborador };        
    };
    
    var getTipoConta = function(informacaoBancaria) {
        if(!informacaoBancaria.tipoConta) return informacaoBancaria.tipoConta;
        return informacaoBancaria.tipoConta.id; 
    };
    
    var getBanco = function(informacaoBancaria) {
        if(!informacaoBancaria.banco) return informacaoBancaria.banco;
        return { idBanco: informacaoBancaria.banco.idBanco };    
    };
    
    var getAgencia = function(informacaoBancaria) {
        return informacaoBancaria.agencia || null; 
    };
    
    var getAgenciaDv = function(informacaoBancaria) {
        return informacaoBancaria.agenciaDv || null; 
    };
    
    var getContaCorrente = function(informacaoBancaria) {
        return informacaoBancaria.contaCorrente || null; 
    };
    
    var getContaCorrenteDv = function(informacaoBancaria) {
        return informacaoBancaria.contaCorrenteDv || null; 
    };

    return {
        handle: handle,
        handleList: handleList
    }

});