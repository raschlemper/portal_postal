'use strict';

app.factory('LancamentoRateioHandler', function() {
    
    var handleList = function(lancamentoRateioList) { 
        if(!lancamentoRateioList) return null;
        return _.map(lancamentoRateioList, function(lancamentoRateio) {
            return handle(lancamentoRateio);
        });
    };
    
    var handle = function(lancamentoRateio) { 
        var lancamentoRateioHandle = {};
        lancamentoRateioHandle.idLancamentoRateio = getId(lancamentoRateio);
        lancamentoRateioHandle.planoConta = getPlanoConta(lancamentoRateio); 
        lancamentoRateioHandle.centroCusto = getCentroCusto(lancamentoRateio);
        lancamentoRateioHandle.lancamento = null;
        lancamentoRateioHandle.valor = getValor(lancamentoRateio);
        lancamentoRateioHandle.observacao = getObservacao(lancamentoRateio);
        return lancamentoRateioHandle;
    };
    
    var getId = function(lancamentoRateio) {
        return lancamentoRateio.idLancamentoRateio || null;
    };
    
    var getPlanoConta = function(lancamentoRateio) {
        if(!lancamentoRateio.planoConta) return lancamentoRateio.planoConta;    
        return { idPlanoConta: lancamentoRateio.planoConta.idPlanoConta }; 
    };
    
    var getCentroCusto = function(lancamentoRateio) {
        if(!lancamentoRateio.centroCusto) return lancamentoRateio.centroCusto;    
        return { idCentroCusto: lancamentoRateio.centroCusto.idCentroCusto }; 
    };
    
    var getValor = function(lancamentoRateio) {
        return lancamentoRateio.valor || 0;
    };
    
    var getObservacao = function(lancamentoRateio) {
        return lancamentoRateio.observacao || null;
    };

    return {
        handle: handle,
        handleList: handleList
    }

});