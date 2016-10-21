'use strict';

app.factory('PlanoContaHandler', function() {
    
    var handleList = function(planoContaList) { 
        return _.map(planoContaList, function(planoConta) {
            return handle(planoConta);
        });
    };
    
    var handle = function(planoConta) {  
        var planoContaHandle = {};
        planoContaHandle.idPlanoConta = getId(planoConta); 
        planoContaHandle.tipo = getTipo(planoConta); 
        planoContaHandle.codigo = getCodigo(planoConta);
        planoContaHandle.nome = getNome(planoConta);
        planoContaHandle.grupo = getGrupo(planoConta);
        return planoContaHandle;
    };
    
    var getId = function(planoConta) {
        return planoConta.idPlanoConta || null;
    };
    
    var getTipo = function(planoConta) {
        if(!planoConta.tipo) return planoConta.tipo;
        return planoConta.tipo.id; 
    };
    
    var getCodigo = function(planoConta) {
        return planoConta.codigo || null; 
    };
    
    var getNome = function(planoConta) {
        return planoConta.nome || null; 
    };
    
    var getGrupo = function(planoConta) {
        if(!planoConta.grupo) return planoConta.grupo;
        return { idPlanoConta: planoConta.grupo.idPlanoConta };
    }

    return {
        handle: handle,
        handleList: handleList
    }

});