'use strict';

app.factory('BancoService', function($http, PromiseService) {

    return {

        getAll: function() {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/banco/"));
        },

        get: function(idBanco) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/banco/" + idBanco));
        },

        getByNumero: function(numero) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/banco/numero/" + numero));
        },

        save: function(data) {
            return PromiseService.execute(
                    $http.post(_contextPath + "/api/financeiro/banco/", data));
        },

        update: function(idBanco, data) {
            return PromiseService.execute(
                    $http.put(_contextPath + "/api/financeiro/banco/" + idBanco, data));
        },

        delete: function(idBanco) {
            return PromiseService.execute(
                    $http.delete(_contextPath + "/api/financeiro/banco/" + idBanco));
        }

    }

});