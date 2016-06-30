'use strict';

app.factory('LancamentoProgramadoParcelaHandler', function() {
    
    var handleList = function(lancamentoProgramadoParcelaList) { 
        if(!lancamentoProgramadoParcelaList) return null;
        return _.map(lancamentoProgramadoParcelaList, function(lancamentoProgramadoParcela) {
            return handle(lancamentoProgramadoParcela);
        });
    };
    
    var handle = function(lancamentoProgramadoParcela) { 
        var lancamentoProgramadoParcelaHandle = {};
        lancamentoProgramadoParcelaHandle.idLancamentoProgramadoParcela = getId(lancamentoProgramadoParcela);
        lancamentoProgramadoParcelaHandle.planoConta = getPlanoConta(lancamentoProgramadoParcela); 
        lancamentoProgramadoParcelaHandle.centroCusto = getCentroCusto(lancamentoProgramadoParcela);
        lancamentoProgramadoParcelaHandle.lancamento = getLancamento(lancamentoProgramadoParcela);
        lancamentoProgramadoParcelaHandle.numero = getNumero(lancamentoProgramadoParcela);
        lancamentoProgramadoParcelaHandle.dataVencimento = getDataVencimento(lancamentoProgramadoParcela);
        lancamentoProgramadoParcelaHandle.valor = getValor(lancamentoProgramadoParcela);
        return lancamentoProgramadoParcelaHandle;
    };
    
    var getId = function(lancamentoProgramadoParcela) {
        return lancamentoProgramadoParcela.idLancamentoProgramadoParcela || null;
    };
    
    var getPlanoConta = function(lancamentoProgramadoParcela) {
        if(!lancamentoProgramadoParcela.planoConta) return lancamentoProgramadoParcela.planoConta;    
        return { idPlanoConta: lancamentoProgramadoParcela.planoConta.idPlanoConta }; 
    };
    
    var getCentroCusto = function(lancamentoProgramadoParcela) {
        if(!lancamentoProgramadoParcela.centroCusto) return lancamentoProgramadoParcela.centroCusto;    
        return { idCentroCusto: lancamentoProgramadoParcela.centroCusto.idCentroCusto }; 
    };
    
    var getLancamento = function(lancamentoProgramadoParcela) {
        if(!lancamentoProgramadoParcela.lancamento) return lancamentoProgramadoParcela.lancamento;
        return { idLancamentoProgramado: lancamentoProgramadoParcela.lancamento.idLancamento }; 
    };
    
    var getNumero = function(lancamentoProgramadoParcela) {
        return lancamentoProgramadoParcela.numero || null;
    };
    
    var getDataVencimento = function(lancamentoProgramadoParcela) {
        return lancamentoProgramadoParcela.dataVencimento || null;
    };
    
    var getValor = function(lancamentoProgramadoParcela) {
        return lancamentoProgramadoParcela.valor || 0;
    };

    return {
        handle: handle,
        handleList: handleList
    }

});