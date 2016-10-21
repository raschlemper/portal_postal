'use strict';

app.factory('LancamentoService', function($http, PromiseService) {

    return {

        getAll: function() {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/"));
        },

        getSaldo: function(dataInicio, dataFim) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/saldo?dataInicio=" + dataInicio + "&dataFim=" + dataFim));
        },

        getSaldoPlanoConta: function(dataInicio, dataFim) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/planoconta/saldo?dataInicio=" + dataInicio + "&dataFim=" + dataFim));
        },

        getSaldoPlanoContaCompetencia: function(dataInicio, dataFim) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/planoconta/saldo/competencia?dataInicio=" + dataInicio + "&dataFim=" + dataFim));
        },

        getSaldoTipo: function(dataInicio, dataFim) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/tipo/saldo?dataInicio=" + dataInicio + "&dataFim=" + dataFim));
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

        updateAll: function(data) {
            return PromiseService.execute(
                    $http.put(_contextPath + "/api/financeiro/lancamento/", data));
        },

        delete: function(idLancamento) {
            return PromiseService.execute(
                    $http.delete(_contextPath + "/api/financeiro/lancamento/" + idLancamento));
        }

    }

});