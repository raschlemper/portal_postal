'use strict';

app.factory('VeiculoManutencaoHandler', function() {
    
    var handleList = function(veiculoManutencaoList) { 
        return _.map(veiculoManutencaoList, function(veiculoManutencao) {
            return handle(veiculoManutencao);
        });
    };
    
    var handle = function(veiculoManutencao) {  
        var veiculoManutencaoHandle = {};
        veiculoManutencaoHandle.idVeiculoManutencao = getId(veiculoManutencao); 
        veiculoManutencaoHandle.tipo = getTipo(veiculoManutencao);
        veiculoManutencaoHandle.quilometragem = getQuilometragem(veiculoManutencao);
        veiculoManutencaoHandle.valor = getValor(veiculoManutencao);
        veiculoManutencaoHandle.dataManutencao = getDataManutencao(veiculoManutencao);
        veiculoManutencaoHandle.dataAgendamento = getDataAgendamento(veiculoManutencao);
        veiculoManutencaoHandle.descricao = getDescricao(veiculoManutencao);
        return veiculoManutencaoHandle;
    };
    
    var getId = function(veiculoManutencao) {
        return veiculoManutencao.idVeiculoManutencao || null;
    };
    
    var getTipo = function(veiculoManutencao) {
        if(!veiculoManutencao.tipo) return veiculoManutencao.tipo;
        return veiculoManutencao.tipo.id; 
    };
    
    var getQuilometragem = function(veiculoManutencao) {
        return veiculoManutencao.quilometragem || null; 
    };
    
    var getValor = function(veiculoManutencao) {
        return veiculoManutencao.valor || null; 
    };
    
    var getDataManutencao = function(veiculoManutencao) {
        return veiculoManutencao.dataManutencao || null; 
    };
    
    var getDataAgendamento = function(veiculoManutencao) {
        return veiculoManutencao.dataAgendamento || null; 
    };
    
    var getDescricao = function(veiculoManutencao) {
        return veiculoManutencao.descricao || null; 
    };

    return {
        handle: handle,
        handleList: handleList
    }

});