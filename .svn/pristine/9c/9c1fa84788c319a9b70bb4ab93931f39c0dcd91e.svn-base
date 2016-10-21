'use strict';

app.factory('VeiculoSeguroHandler', function() {
    
    var handleList = function(veiculoSeguroList) { 
        return _.map(veiculoSeguroList, function(veiculoSeguro) {
            return handle(veiculoSeguro);
        });
    };
    
    var handle = function(veiculoSeguro) {  
        var veiculoSeguroHandle = {};
        veiculoSeguroHandle.idVeiculoSeguro = getId(veiculoSeguro); 
        veiculoSeguroHandle.numeroApolice = getNumeroApolice(veiculoSeguro);
        veiculoSeguroHandle.corretora = getCorretora(veiculoSeguro);
        veiculoSeguroHandle.assegurado = getAssegurado(veiculoSeguro);
        veiculoSeguroHandle.valorFranquia = getValorFranquia(veiculoSeguro);
        veiculoSeguroHandle.indenizacao = getIndenizacao(veiculoSeguro);
        veiculoSeguroHandle.dataInicioVigencia = getDataInicioVigencia(veiculoSeguro);
        veiculoSeguroHandle.dataFimVigencia = getDataFimVigencia(veiculoSeguro);
        return veiculoSeguroHandle;
    };
    
    var getId = function(veiculoSeguro) {
        return veiculoSeguro.idVeiculoSeguro || null;
    };
    
    var getNumeroApolice = function(veiculoSeguro) {
        return veiculoSeguro.numeroApolice || null; 
    };
    
    var getCorretora = function(veiculoSeguro) {
        return veiculoSeguro.corretora || null; 
    };
    
    var getAssegurado = function(veiculoSeguro) {
        return veiculoSeguro.assegurado || null; 
    };
    
    var getValorFranquia = function(veiculoSeguro) {
        return veiculoSeguro.valorFranquia || null; 
    };
    
    var getIndenizacao = function(veiculoSeguro) {
        if(!veiculoSeguro.indenizacao) return veiculoSeguro.indenizacao;
        return veiculoSeguro.indenizacao.id; 
    };
    
    var getDataInicioVigencia = function(veiculoSeguro) {
        return veiculoSeguro.dataInicioVigencia || null; 
    };
    
    var getDataFimVigencia = function(veiculoSeguro) {
        return veiculoSeguro.dataFimVigencia || null; 
    };

    return {
        handle: handle,
        handleList: handleList
    }

});