'use strict';

app.factory('LancamentoProgramadoParcelaHandler', function() {
    
    var handleList = function(lancamentoProgramadoRateioList) { 
        if(!lancamentoProgramadoRateioList) return null;
        return _.map(lancamentoProgramadoRateioList, function(lancamentoProgramadoRateio) {
            return handle(lancamentoProgramadoRateio);
        });
    };
    
    var handle = function(lancamentoProgramadoRateio) { 
        var lancamentoProgramadoRateioHandle = {};
        lancamentoProgramadoRateioHandle.idLancamentoProgramadoRateio = getId(lancamentoProgramadoRateio);
        lancamentoProgramadoRateioHandle.planoConta = getPlanoConta(lancamentoProgramadoRateio); 
        lancamentoProgramadoRateioHandle.centroCusto = getCentroCusto(lancamentoProgramadoRateio);
        lancamentoProgramadoRateioHandle.lancamento = getLancamento(lancamentoProgramadoRateio);
        lancamentoProgramadoRateioHandle.numero = getNumero(lancamentoProgramadoRateio);
        lancamentoProgramadoRateioHandle.dataVencimento = getDataVencimento(lancamentoProgramadoRateio);
        lancamentoProgramadoRateioHandle.valor = getValor(lancamentoProgramadoRateio);
        return lancamentoProgramadoRateioHandle;
    };
    
    var getId = function(lancamentoProgramadoRateio) {
        return lancamentoProgramadoRateio.idLancamentoProgramadoRateio || null;
    };
    
    var getPlanoConta = function(lancamentoProgramadoRateio) {
        if(!lancamentoProgramadoRateio.planoConta) return lancamentoProgramadoRateio.planoConta;    
        return { idPlanoConta: lancamentoProgramadoRateio.planoConta.idPlanoConta }; 
    };
    
    var getCentroCusto = function(lancamentoProgramadoRateio) {
        if(!lancamentoProgramadoRateio.centroCusto) return lancamentoProgramadoRateio.centroCusto;    
        return { idCentroCusto: lancamentoProgramadoRateio.centroCusto.idCentroCusto }; 
    };
    
    var getLancamento = function(lancamentoProgramadoRateio) {
        if(!lancamentoProgramadoRateio.lancamento) return lancamentoProgramadoRateio.lancamento;
        return { idLancamentoProgramado: lancamentoProgramadoRateio.lancamento.idLancamento }; 
    };
    
    var getNumero = function(lancamentoProgramadoRateio) {
        return lancamentoProgramadoRateio.numero || null;
    };
    
    var getDataVencimento = function(lancamentoProgramadoRateio) {
        return lancamentoProgramadoRateio.dataVencimento || null;
    };
    
    var getValor = function(lancamentoProgramadoRateio) {
        return lancamentoProgramadoRateio.valor || 0;
    };

    return {
        handle: handle,
        handleList: handleList
    }

});