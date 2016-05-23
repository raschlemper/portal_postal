'use strict';

app.factory('CentroCustoService', function($http, PromiseService) {    
        
    var flatten = function(estruturas, estruturasLista) {
        angular.forEach(estruturas, function(estrutura) {
            estruturasLista.push({ 
                idCentroCusto: estrutura.idCentroCusto,
                nivel: estrutura.nivel, 
                ehGrupo: (estrutura.contas ? true : false),
                idGrupo: (estrutura.grupo ? estrutura.grupo.idCentroCusto : null),
                descricao: estrutura.descricao 
            });
            if(estrutura.contas) { flatten(estrutura.contas, estruturasLista); }
        });
    };
    
    var getEstrutura = function(estruturas) {
        angular.forEach(estruturas, function(estrutura) {     
            estrutura.descricao = getCode(estrutura.estrutura) + ' - ' + estrutura.nome; 
            if(estrutura.contas) { getEstrutura(estrutura.contas); }
        });
    }
            
    var getCode = function(estruturas) {
        var code = '';
        angular.forEach(estruturas, function(estrutura) {
            if(code) { code = code + '.' + estrutura; }
            else { code = estrutura; }
        });
        return code;
    }
        
    return {

        getAll: function() {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/centrocusto/"));
        },

        getStructure: function() {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/centrocusto/structure"));
        },

//        getByTipo: function(tipo) {
//            return PromiseService.execute(
//                    $http.get(_contextPath + "/api/financeiro/centrocusto/tipo/" + tipo));
//        },
        
//        findContaResultadoByTipo: function(tipo) {
//            return PromiseService.execute(
//                    $http.get(_contextPath + "/api/financeiro/centrocusto/tipo/" + tipo + "/resultado"));
//        },

//        getStructureByTipo: function(tipo) {
//            return PromiseService.execute(
//                    $http.get(_contextPath + "/api/financeiro/centrocusto/tipo/" + tipo + "/structure"));
//        },

        getByGrupoCodigo: function(grupo, codigo) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/centrocusto/grupo/" + grupo + "/codigo/" + codigo));
        },

        get: function(idCentroCusto) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/centrocusto/" + idCentroCusto));
        },

        getLancamento: function(idCentroCusto) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/centrocusto/" + idCentroCusto + '/lancamento'));
        },

        getLancamentoProgramado: function(idCentroCusto) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/centrocusto/" + idCentroCusto + '/lancamento/programado'));
        },

        save: function(data) {
            return PromiseService.execute(
                    $http.post(_contextPath + "/api/financeiro/centrocusto/", data));
        },

        update: function(idCentroCusto, data) {
            return PromiseService.execute(
                    $http.put(_contextPath + "/api/financeiro/centrocusto/" + idCentroCusto, data));
        },

        delete: function(idCentroCusto) {
            return PromiseService.execute(
                    $http.delete(_contextPath + "/api/financeiro/centrocusto/" + idCentroCusto));
        },
        
        flatten: function(estruturas) {
            var estruturasLista = [];
            flatten(estruturas, estruturasLista);
            return estruturasLista;
        }, 
        
        estrutura: function(estruturas) {
            getEstrutura(estruturas);
        }, 

    }

});