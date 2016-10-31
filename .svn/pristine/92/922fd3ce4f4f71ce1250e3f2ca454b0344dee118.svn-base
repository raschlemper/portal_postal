'use strict';

app.factory('VeiculoSinistroHandler', function() {
    
    var handleList = function(veiculoSinistroList) { 
        return _.map(veiculoSinistroList, function(veiculoSinistro) {
            return handle(veiculoSinistro);
        });
    };
    
    var handle = function(veiculoSinistro) {  
        var veiculoSinistroHandle = {};
        veiculoSinistroHandle.idVeiculoSinistro = getId(veiculoSinistro); 
        veiculoSinistroHandle.tipo = getTipo(veiculoSinistro);
        veiculoSinistroHandle.boletimOcorrencia = getBoletimOcorrencia(veiculoSinistro);
        veiculoSinistroHandle.data = getData(veiculoSinistro);
        veiculoSinistroHandle.responsavel = getResponsavel(veiculoSinistro);
        veiculoSinistroHandle.local = getLocal(veiculoSinistro);
        veiculoSinistroHandle.descricao = getDescricao(veiculoSinistro);
        return veiculoSinistroHandle;
    };
    
    var getId = function(veiculoSinistro) {
        return veiculoSinistro.idVeiculoSinistro || null;
    };
    
    var getTipo = function(veiculoSinistro) {
        if(!veiculoSinistro.tipo) return veiculoSinistro.tipo;
        return veiculoSinistro.tipo.id; 
    };
    
    var getBoletimOcorrencia = function(veiculoSinistro) {
        return veiculoSinistro.boletimOcorrencia || null; 
    };
        
    var getData = function(veiculoSinistro) {
        return veiculoSinistro.data || null; 
    };
    
    var getResponsavel = function(veiculoSinistro) {
        if(!veiculoSinistro.responsavel) return veiculoSinistro.responsavel;
        return veiculoSinistro.responsavel.id; 
    };
    
    var getLocal = function(veiculoSinistro) {
        return veiculoSinistro.local || null; 
    };
    
    var getDescricao = function(veiculoSinistro) {
        return veiculoSinistro.descricao || null; 
    };

    return {
        handle: handle,
        handleList: handleList
    }

});