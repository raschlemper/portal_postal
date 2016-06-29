'use strict';

app.factory('LancamentoProgramadoRateioHandler', function() {
    
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
        lancamentoProgramadoRateioHandle.lancamento = getLancamento(lancamentoProgramadoRateio);;
        lancamentoProgramadoRateioHandle.valor = getValor(lancamentoProgramadoRateio);
        lancamentoProgramadoRateioHandle.observacao = getObservacao(lancamentoProgramadoRateio);
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
    
    var getValor = function(lancamentoProgramadoRateio) {
        return lancamentoProgramadoRateio.valorParcela || 0;
    };
    
    var getObservacao = function(lancamentoProgramadoRateio) {
        return lancamentoProgramadoRateio.observacao || null;
    };

    return {
        handle: handle,
        handleList: handleList
    }

});