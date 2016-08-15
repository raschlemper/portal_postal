'use strict';

app.factory('ContaCorrenteHandler', function() {
    
    var handleList = function(contaCorrenteList) { 
        return _.map(contaCorrenteList, function(contaCorrente) {
            return handle(contaCorrente);
        });
    };
    
    var handle = function(contaCorrente) {  
        var contaCorrenteHandle = {};
        contaCorrenteHandle.idContaCorrente = getId(contaCorrente); 
        contaCorrenteHandle.nome = getNome(contaCorrente);
        contaCorrenteHandle.banco = getBanco(contaCorrente);
        contaCorrenteHandle.agencia = getAgencia(contaCorrente);
        contaCorrenteHandle.agenciaDv = getAgenciaDv(contaCorrente);
        contaCorrenteHandle.contaCorrente = getContaCorrente(contaCorrente);
        contaCorrenteHandle.contaCorrenteDv = getContaCorrenteDv(contaCorrente);
        contaCorrenteHandle.poupanca = getPoupanca(contaCorrente);
        contaCorrenteHandle.limite = getLimite(contaCorrente);
        return contaCorrenteHandle;
    };
    
    var getId = function(contaCorrente) {
        return contaCorrente.idContaCorrente || null;
    };
    
    var getNome = function(contaCorrente) {
        return contaCorrente.nome || null; 
    };
    
    var getBanco = function(contaCorrente) {
        if(!contaCorrente.banco) return contaCorrente.banco;
        return { idBanco: contaCorrente.banco.idBanco };
    };
    
    var getAgencia = function(contaCorrente) {
        return contaCorrente.agencia || null; 
    };
    
    var getAgenciaDv = function(contaCorrente) {
        if(contaCorrente.agenciaDv == 0) return 0;
        return contaCorrente.agenciaDv || null; 
    };
    
    var getContaCorrente = function(contaCorrente) {
        return contaCorrente.contaCorrente || null; 
    }
    
    var getContaCorrenteDv = function(contaCorrente) {
        if(contaCorrente.contaCorrenteDv == 0) return 0;
        return contaCorrente.contaCorrenteDv || null; 
    }
    
    var getPoupanca = function(contaCorrente) {
        return contaCorrente.poupanca || false; 
    }
    
    var getLimite = function(contaCorrente) {
        return contaCorrente.limite || 0; 
    }

    return {
        handle: handle,
        handleList: handleList
    }

});