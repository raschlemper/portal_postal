'use strict';

app.factory('CentroCustoHandler', function() {
    
    var handleList = function(centroCustoList) { 
        return _.map(centroCustoList, function(centroCusto) {
            return handle(centroCusto);
        });
    };
    
    var handle = function(centroCusto) {  
        var centroCustoHandle = {};
        centroCustoHandle.idCentroCusto = getId(centroCusto); 
        centroCustoHandle.codigo = getCodigo(centroCusto);
        centroCustoHandle.nome = getNome(centroCusto);
        centroCustoHandle.grupo = getGrupo(centroCusto);
        return centroCustoHandle;
    };
    
    var getId = function(centroCusto) {
        return centroCusto.idCentroCusto || null;
    };
    
    var getCodigo = function(centroCusto) {
        return centroCusto.codigo || null; 
    };
    
    var getNome = function(centroCusto) {
        return centroCusto.nome || null; 
    };
    
    var getGrupo = function(centroCusto) {
        if(!centroCusto.grupo) return centroCusto.grupo;
        return { idCentroCusto: centroCusto.grupo.idCentroCusto };
    }

    return {
        handle: handle,
        handleList: handleList
    }

});