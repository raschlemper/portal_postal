'use strict';

app.factory('LancamentoService', function($http, PromiseService) {

    return {

        getAll: function() {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/"));
        },

        findSaldoByTipo: function(tipo, ano, mesInicio, mesFim) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/tipo/" + tipo + "/saldo?ano=" + ano + "&mesInicio=" + mesInicio + "&mesFim=" + mesFim));
        },

        findYearFromLancamento: function() {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/anos"));
        },

        get: function(idLancamento) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/" + idLancamento));
        },

        save: function(data) {
            return PromiseService.execute(
                    $http.post(_contextPath + "/api/financeiro/lancamento/", data));
        },

        update: function(idLancamento, data) {
            return PromiseService.execute(
                    $http.put(_contextPath + "/api/financeiro/lancamento/" + idLancamento, data));
        },

        delete: function(idLancamento) {
            return PromiseService.execute(
                    $http.delete(_contextPath + "/api/financeiro/lancamento/" + idLancamento));
        }

    }

});