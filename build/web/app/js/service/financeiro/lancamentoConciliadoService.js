'use strict';

app.factory('LancamentoConciliadoService', function($http, PromiseService) {

    return {

        getAll: function() {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/conciliado/"));
        },

        getByData: function(data) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/conciliado/data/" + data));
        },

        get: function(idLancamentoConciliado) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/conciliado/" + idLancamentoConciliado));
        },

        getLastByConta: function(idConta) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/conciliado/conta/" + idConta + "/last"));
        },

        save: function(data) {
            return PromiseService.execute(
                    $http.post(_contextPath + "/api/financeiro/lancamento/conciliado/", data));
        },

        update: function(idLancamentoConciliado, data) {
            return PromiseService.execute(
                    $http.put(_contextPath + "/api/financeiro/lancamento/conciliado/" + idLancamentoConciliado, data));
        },

        create: function(data) {
            return PromiseService.execute(
                    $http.post(_contextPath + "/api/financeiro/lancamento/conciliado/create", data));
        },

        delete: function(idLancamentoConciliado) {
            return PromiseService.execute(
                    $http.delete(_contextPath + "/api/financeiro/lancamento/conciliado/" + idLancamentoConciliado));
        }

    }

});