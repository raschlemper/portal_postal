'use strict';

app.factory('VeiculoCombustivelHandler', function() {
    
    var handleList = function(veiculoCombustivelList) { 
        return _.map(veiculoCombustivelList, function(veiculoCombustivel) {
            return handle(veiculoCombustivel);
        });
    };
    
    var handle = function(veiculoCombustivel) {  
        var veiculoCombustivelHandle = {};
        veiculoCombustivelHandle.idVeiculoCombustivel = getId(veiculoCombustivel); 
        veiculoCombustivelHandle.tipo = getTipo(veiculoCombustivel);
        veiculoCombustivelHandle.quantidade = getQuantidade(veiculoCombustivel);
        veiculoCombustivelHandle.valorUnitario = getValorUnitario(veiculoCombustivel);
        veiculoCombustivelHandle.data = getData(veiculoCombustivel);
        veiculoCombustivelHandle.valorTotal = getValorTotal(veiculoCombustivel);
        veiculoCombustivelHandle.quilometragem = getQuilometragem(veiculoCombustivel);
        return veiculoCombustivelHandle;
    };
    
    var getId = function(veiculoCombustivel) {
        return veiculoCombustivel.idVeiculoCombustivel || null;
    };
    
    var getTipo = function(veiculoCombustivel) {
        if(!veiculoCombustivel.tipo) return veiculoCombustivel.tipo;
        return veiculoCombustivel.tipo.id; 
    };
    
    var getQuantidade = function(veiculoCombustivel) {
        return veiculoCombustivel.quantidade || null; 
    };
    
    var getValorUnitario = function(veiculoCombustivel) {
        return veiculoCombustivel.valorUnitario || null; 
    };
    
    var getData = function(veiculoCombustivel) {
        return veiculoCombustivel.data || null; 
    };
    
    var getValorTotal = function(veiculoCombustivel) {
        return veiculoCombustivel.valorTotal || null; 
    };
    
    var getQuilometragem = function(veiculoCombustivel) {
        return veiculoCombustivel.quilometragem || null; 
    };

    return {
        handle: handle,
        handleList: handleList
    }

});