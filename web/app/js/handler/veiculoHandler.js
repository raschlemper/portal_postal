'use strict';

app.factory('VeiculoHandler', function() {
    
    var handleList = function(veiculoList) { 
        return _.map(veiculoList, function(veiculo) {
            return handle(veiculo);
        });
    };
    
    var handle = function(veiculo) {  
        var veiculoHandle = {};
        veiculoHandle.idVeiculo = getId(veiculo); 
        veiculoHandle.tipo = getTipo(veiculo);
        veiculoHandle.idMarca = getCodigoMarca(veiculo);
        veiculoHandle.marca = getMarca(veiculo);
        veiculoHandle.idModelo = getCodigoModelo(veiculo);
        veiculoHandle.modelo = getModelo(veiculo);
        veiculoHandle.idVersao = getCodigoVersao(veiculo);
        veiculoHandle.versao = getVersao(veiculo);
        veiculoHandle.placa = getPlaca(veiculo);
        veiculoHandle.anoModelo = getAnoModelo(veiculo);
        veiculoHandle.chassis = getChassis(veiculo);
        veiculoHandle.renavam = getRenavam(veiculo);
        veiculoHandle.quilometragem = getQuilometragem(veiculo);
        veiculoHandle.dataCadastro = getDataCadastro(veiculo);
        return veiculoHandle;
    };
    
    var getId = function(veiculo) {
        return veiculo.idVeiculoCombustivel || null;
    };
    
    var getTipo = function(veiculo) {
        if(!veiculo.tipo) return veiculo.tipo;
        return veiculo.tipo.id; 
    };
    
    var getCodigoMarca = function(veiculo) {
        if(!veiculo.marca) return veiculo.marca;
        return veiculo.marca.id; 
    };
    
    var getMarca = function(veiculo) {
        if(!veiculo.marca) return veiculo.marca;
        return veiculo.marca.name; 
    };
    
    var getCodigoModelo = function(veiculo) {
        if(!veiculo.modelo) return veiculo.modelo;
        return veiculo.modelo.id; 
    };
    
    var getModelo = function(veiculo) {
        if(!veiculo.modelo) return veiculo.modelo;
        return veiculo.modelo.name; 
    };
    
    var getCodigoVersao = function(veiculo) {
        if(!veiculo.versao) return veiculo.versao;
        return veiculo.versao.id; 
    };
    
    var getVersao = function(veiculo) {
        if(!veiculo.versao) return veiculo.versao;
        return veiculo.versao.name; 
    };
    
    var getPlaca = function(veiculo) {
        return veiculo.placa || null; 
    };
    
    var getAnoModelo = function(veiculo) {
        return veiculo.anoModelo || null; 
    };
    
    var getChassis = function(veiculo) {
        return veiculo.chassis || null; 
    };
    
    var getRenavam = function(veiculo) {
        return veiculo.renavam || null; 
    };
    
    var getQuilometragem = function(veiculo) {
        return veiculo.quilometragem || null; 
    };
    
    var getDataCadastro = function(veiculo) {
        return veiculo.dataCadastro || null; 
    };

    return {
        handle: handle,
        handleList: handleList
    }

});