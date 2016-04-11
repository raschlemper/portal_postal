'use strict';

app.factory('PlanoContaService', function($http, PromiseService) {    
        
    var flatten = function(estruturas, estruturasLista) {
        angular.forEach(estruturas, function(estrutura) {
            estruturasLista.push({ 
                idPlanoConta: estrutura.idPlanoConta,
                tipo: estrutura.tipo,
                nivel: estrutura.nivel, 
                ehGrupo: (estrutura.contas ? true : false),
                idGrupo: (estrutura.grupo ? estrutura.grupo.idPlanoConta : null),
                descricao: getCode(estrutura.estrutura) + ' - ' + estrutura.nome 
            });
            if(estrutura.contas) { flatten(estrutura.contas, estruturasLista); }
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
                    $http.get(_contextPath + "/api/financeiro/planoconta/"));
        },

        getStructure: function() {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/planoconta/structure"));
        },

        getByTipo: function(tipo) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/planoconta/tipo/" + tipo));
        },
        
        findContaResultadoByTipo: function(tipo) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/planoconta/tipo/" + tipo + "/resultado"));
        },

        getStructureByTipo: function(tipo) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/planoconta/tipo/" + tipo + "/structure"));
        },

        getByTipoGrupoCodigo: function(tipo, grupo, codigo) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/planoconta/tipo/" + tipo + "/grupo/" + grupo + "/codigo/" + codigo));
        },

        getSaldo: function(ano) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/planoconta/saldo?ano=" + ano));
        },

        get: function(idPlanoConta) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/planoconta/" + idPlanoConta));
        },

        save: function(data) {
            return PromiseService.execute(
                    $http.post(_contextPath + "/api/financeiro/planoconta/", data));
        },

        update: function(idPlanoConta, data) {
            return PromiseService.execute(
                    $http.put(_contextPath + "/api/financeiro/planoconta/" + idPlanoConta, data));
        },

        delete: function(idPlanoConta) {
            return PromiseService.execute(
                    $http.delete(_contextPath + "/api/financeiro/planoconta/" + idPlanoConta));
        },
        
        flatten: function(estruturas) {
            var estruturaLista = [];
            flatten(estruturas, estruturaLista);
            return estruturaLista;
        }

    }

});