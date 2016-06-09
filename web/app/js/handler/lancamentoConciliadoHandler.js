'use strict';

app.factory('LancamentoConciliadoHandler', function() {
    
    var handleList = function(lancamentoConciliadoList) { 
        return _.map(lancamentoConciliadoList, function(lancamentoConciliado) {
            return handle(lancamentoConciliado);
        });
    };
    
    var handle = function(lancamentoConciliado) {  
        var lancamentoConciliadoHandle = {};
        lancamentoConciliadoHandle.idLancamentoConciliado = getId(lancamentoConciliado);            
        lancamentoConciliadoHandle.conta = getConta(lancamentoConciliado);  
        lancamentoConciliadoHandle.planoConta = getPlanoConta(lancamentoConciliado);  
        lancamentoConciliadoHandle.centroCusto = getCentroCusto(lancamentoConciliado);
        lancamentoConciliadoHandle.lancamento = getLancamento(lancamentoConciliado);
        lancamentoConciliadoHandle.tipo = getTipo(lancamentoConciliado); 
        lancamentoConciliadoHandle.numeroLote = getNumeroLote(lancamentoConciliado);
        lancamentoConciliadoHandle.dataCompetencia = getDataCompetencia(lancamentoConciliado);
        lancamentoConciliadoHandle.dataEmissao = getDataEmissao(lancamentoConciliado);
        lancamentoConciliadoHandle.dataLancamento = getDataLancamento(lancamentoConciliado);
        lancamentoConciliadoHandle.valor = getValor(lancamentoConciliado);
        lancamentoConciliadoHandle.historico = getHistorico(lancamentoConciliado);
        lancamentoConciliadoHandle.usuario = getUsuario(lancamentoConciliado);
        return lancamentoConciliadoHandle;
    };
    
    var getConta = function(lancamentoConciliado) {
        if(!lancamentoConciliado.conta) return lancamentoConciliado.conta;
        return { idConta: lancamentoConciliado.conta.idConta };
    };
    
    var getPlanoConta = function(lancamentoConciliado) {
        if(!lancamentoConciliado.planoConta) return lancamentoConciliado.planoConta;    
        return { idPlanoConta: lancamentoConciliado.planoConta.idPlanoConta }; 
    };
    
    var getCentroCusto = function(lancamentoConciliado) {
        if(!lancamentoConciliado.centroCusto) return lancamentoConciliado.centroCusto;    
        return { idCentroCusto: lancamentoConciliado.centroCusto.idCentroCusto }; 
    };
    
    var getLancamento = function(lancamentoConciliado) {
        if(!lancamentoConciliado.lancamento) return lancamentoConciliado.lancamento;
        return { idLancamentoProgramado: lancamentoConciliado.lancamento.idLancamento }; 
    };
    
    var getTipo = function(lancamentoConciliado) {
        if(!lancamentoConciliado.tipo) return lancamentoConciliado.tipo;
        return lancamentoConciliado.tipo.id; 
    };
    
    var getDataCompetencia = function(lancamentoConciliado) {
        return lancamentoConciliado.dataCompetencia || moment(); 
    };
    
    var getDataEmissao = function(lancamentoConciliado) {
        return lancamentoConciliado.dataEmissao || moment(); 
    };
    
    var getDataLancamento = function(lancamentoConciliado) {
        return lancamentoConciliado.dataLancamento || moment(); 
    };
    
    var getValor = function(lancamentoConciliado) {
        return lancamentoConciliado.valor || 0;
    };
    
    var getNumeroLote = function(lancamentoConciliado) {
        return lancamentoConciliado.numeroLoteConciliado || null;
    };
    
    var getHistorico = function(lancamentoConciliado) {
        return lancamentoConciliado.historico || null;
    };
    
    var getUsuario = function(lancamentoConciliado) {
        return lancamentoConciliado.usuario || null;
    };

    return {
        handle: handle,
        handleList: handleList
    }

});