'use strict';

app.factory('PlanoContaService', function($http, PromiseService) {
    return {

        getAll: function() {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/planoconta/"));
        },

        getStructure: function() {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/planoconta/structure"));
        },

        getStructureByTipo: function(tipo) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/planoconta/tipo/" + tipo + "/structure"));
        },

        getByTipoGrupoCodigo: function(tipo, grupo, codigo) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/planoconta/tipo/" + tipo + "/grupo/" + grupo + "/codigo/" + codigo));
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
        }

    }

});