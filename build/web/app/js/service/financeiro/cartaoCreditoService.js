'use strict';

app.factory('CartaoCreditoService', function($http, PromiseService) {

    return {

        getAll: function(callback) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/cartaocredito/"));
        },

        get: function(idCartaoCredito, callback) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/cartaocredito/" + idCartaoCredito));
        },

        getConta: function(idCartaoCredito, callback) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/cartaocredito/" + idCartaoCredito + '/conta'));
        },

        save: function(data, callback) {
            return PromiseService.execute(
                    $http.post(_contextPath + "/api/financeiro/cartaocredito/", data));
        },

        update: function(idCartaoCredito, data, callback) {
            return PromiseService.execute(
                    $http.put(_contextPath + "/api/financeiro/cartaocredito/" + idCartaoCredito, data));
        },

        delete: function(idCartaoCredito, callback) {
            return PromiseService.execute(
                    $http.delete(_contextPath + "/api/financeiro/cartaocredito/" + idCartaoCredito));
        }

    }

});