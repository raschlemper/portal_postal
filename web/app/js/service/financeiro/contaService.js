'use strict';

app.factory('ContaService', function($http, PromiseService) {

    return {

        getAll: function() {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/conta/"));
        },

        get: function(idConta) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/conta/" + idConta));
        },

        getLancamento: function(idConta) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/conta/" + idConta + "/lancamento"));
        },

        save: function(data) {
            return PromiseService.execute(
                    $http.post(_contextPath + "/api/financeiro/conta/", data));
        },

        update: function(idConta, data) {
            return PromiseService.execute(
                    $http.put(_contextPath + "/api/financeiro/conta/" + idConta, data));
        },

        delete: function(idConta) {
            return PromiseService.execute(
                    $http.delete(_contextPath + "/api/financeiro/conta/" + idConta));
        }

    }

});