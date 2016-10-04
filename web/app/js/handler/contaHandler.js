'use strict';

app.factory('ContaHandler', function() {
    
    var handleList = function(contaList) { 
        return _.map(contaList, function(conta) {
            return handle(conta);
        });
    };
    
    var handle = function(conta) {  
        var contaHandle = {};
        contaHandle.idConta = getId(conta); 
        contaHandle.contaCorrente = getContaCorrente(conta);
        contaHandle.cartaoCredito = getCartaoCredito(conta);
        contaHandle.nome = getNome(conta);
        contaHandle.tipo = getTipo(conta);
        contaHandle.status = getStatus(conta);
        contaHandle.dataAbertura = getDataAbertura(conta);
        contaHandle.valorSaldoAbertura = getValorSaldoAbertura(conta);
        contaHandle.saldo = getSaldo(conta);
        contaHandle.codigoIntegracao = getCodigoIntegracao(conta);
        contaHandle.visivel = getVisivel(conta);
        return contaHandle;
    };
    
    var getId = function(conta) {
        return conta.idConta || null;
    };
    
    var getContaCorrente = function(conta) {
        if(!conta.contaCorrente) return conta.contaCorrente;
        return { idContaCorrente: conta.contaCorrente.idContaCorrente };        
    };
    
    var getCartaoCredito = function(conta) {
        if(!conta.cartaoCredito) return conta.cartaoCredito;
        return { idCartaoCredito: conta.cartaoCredito.idCartaoCredito };        
    };
    
    var getNome = function(conta) {
        return conta.nome || null; 
    };
    
    var getTipo = function(conta) {
        if(!conta.tipo) return conta.tipo;
        return conta.tipo.id; 
    };
    
    var getStatus = function(conta) {
        if(!conta.status) return conta.status;
        return conta.status.id; 
    };
    
    var getDataAbertura = function(conta) {
        return conta.dataAbertura || null; 
    };
    
    var getValorSaldoAbertura = function(conta) {
        return conta.valorSaldoAbertura || null; 
    };
    
    var getSaldo = function(conta) {
        return conta.saldo || null; 
    };
    
    var getCodigoIntegracao = function(conta) {
        return conta.codigoIntegracao || 1; 
    };
    
    var getVisivel = function(conta) {
        return conta.visivel; 
    };

    return {
        handle: handle,
        handleList: handleList
    }

});