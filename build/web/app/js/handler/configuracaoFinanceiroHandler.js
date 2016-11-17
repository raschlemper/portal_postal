'use strict';

app.factory('ConfiguracaoFinanceiroHandler', function() {
    
    var handleList = function(configuracaoList) { 
        return _.map(configuracaoList, function(configuracao) {
            return handle(configuracao);
        });
    };
    
    var handle = function(configuracao) {  
        var configuracaoHandle = {};
        configuracaoHandle.idConfiguracaoFinanceiro = getId(configuracao); 
        configuracaoHandle.favorecido = getFavorecido(configuracao);
        configuracaoHandle.historico = getHistorico(configuracao);
        configuracaoHandle.planoConta = getPlanoConta(configuracao);
        configuracaoHandle.centroCusto = getCentroCusto(configuracao);
        configuracaoHandle.periodoLancamento = getPeriodoLancamento(configuracao);
        configuracaoHandle.periodoLancamentoProgramado = getPeriodoLancamentoProgramado(configuracao);
        configuracaoHandle.limiteLancamento = getLimiteLancamento(configuracao);
        return configuracaoHandle;
    };
    
    var getId = function(configuracao) {
        return configuracao.idConfiguracaoFinanceiro || null;
    };
    
    var getFavorecido = function(configuracao) {
        return configuracao.favorecido;        
    };
    
    var getHistorico = function(configuracao) {
        return configuracao.historico;        
    };
    
    var getPlanoConta = function(configuracao) {
        return configuracao.planoConta;        
    };
    
    var getCentroCusto = function(configuracao) {
        return configuracao.centroCusto;        
    };
    
    var getPeriodoLancamento = function(configuracao) {
        if(!configuracao.periodoLancamento) return configuracao.periodoLancamento;
        return configuracao.periodoLancamento.id; 
    };
    
    var getPeriodoLancamentoProgramado = function(configuracao) {
        if(!configuracao.periodoLancamentoProgramado) return configuracao.periodoLancamentoProgramado;
        return configuracao.periodoLancamentoProgramado.id; 
    };
    
    var getLimiteLancamento = function(configuracao) {
        if(!configuracao.limiteLancamento) return configuracao.limiteLancamento;
        return configuracao.limiteLancamento.id; 
    };

    return {
        handle: handle,
        handleList: handleList
    }

});