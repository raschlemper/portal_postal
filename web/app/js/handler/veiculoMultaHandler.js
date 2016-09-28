'use strict';

app.factory('VeiculoMultaHandler', function() {
    
    var handleList = function(veiculoMultaList) { 
        return _.map(veiculoMultaList, function(veiculoMulta) {
            return handle(veiculoMulta);
        });
    };
    
    var handle = function(veiculoMulta) {  
        var veiculoMultaHandle = {};
        veiculoMultaHandle.idVeiculoMulta = getId(veiculoMulta); 
        veiculoMultaHandle.condutor = getCondutor(veiculoMulta);
        veiculoMultaHandle.numero = getNumero(veiculoMulta);
        veiculoMultaHandle.data = getData(veiculoMulta);
        veiculoMultaHandle.valor = getValor(veiculoMulta);
        veiculoMultaHandle.descontada = getDescontada(veiculoMulta);
        veiculoMultaHandle.local = getLocal(veiculoMulta);
        veiculoMultaHandle.descricao = getDescricao(veiculoMulta);
        return veiculoMultaHandle;
    };
    
    var getId = function(veiculoMulta) {
        return veiculoMulta.idVeiculoMulta || null;
    };
    
    var getCondutor = function(veiculoMulta) {
        return veiculoMulta.condutor || null; 
    };
    
    var getNumero = function(veiculoMulta) {
        return veiculoMulta.numero || null; 
    };
    
    var getData = function(veiculoMulta) {
        return veiculoMulta.data || null; 
    };
    
    var getValor = function(veiculoMulta) {
        return veiculoMulta.valor || null; 
    };
    
    var getDescontada = function(veiculoMulta) {
        return veiculoMulta.descontada; 
    };
    
    var getLocal = function(veiculoMulta) {
        return veiculoMulta.local || null; 
    };
    
    var getDescricao = function(veiculoMulta) {
        return veiculoMulta.descricao || null; 
    };

    return {
        handle: handle,
        handleList: handleList
    }

});