'use strict';

app.factory('LancamentoService', function($http, PromiseService) {
    
    var lancamentoFromRateio = function(lancamento) {
        var rateios = [];
        _.map(lancamento.rateios, function(rateio) {
            var lancamentoRateio = angular.copy(lancamento);
            lancamentoRateio.planoConta = rateio.planoConta;
            lancamentoRateio.centroCusto = rateio.centroCusto;
            lancamentoRateio.valor = rateio.valor;
            lancamentoRateio.observacao = rateio.observacao;
            rateios.push(lancamentoRateio);
        });
        return rateios;
    }

    return {

        getAll: function() {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/"));
        },

        getFavorecidos: function() {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/favorecido"));
        },

        getSaldo: function(dataInicio, dataFim) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/saldo?dataInicio=" + dataInicio + "&dataFim=" + dataFim));
        },

        getSaldoPlanoConta: function(dataInicio, dataFim) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/planoconta/saldo?dataInicio=" + dataInicio + "&dataFim=" + dataFim));
        },

        getSaldoCentroCusto: function(dataInicio, dataFim) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/centrocusto/saldo?dataInicio=" + dataInicio + "&dataFim=" + dataFim));
        },

        getSaldoPlanoContaCompetencia: function(dataInicio, dataFim) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/planoconta/saldo/competencia?dataInicio=" + dataInicio + "&dataFim=" + dataFim));
        },

        getSaldoCentroCustoCompetencia: function(dataInicio, dataFim) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/centrocusto/saldo/competencia?dataInicio=" + dataInicio + "&dataFim=" + dataFim));
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

        updateSituacao: function(data) {
            return PromiseService.execute(
                    $http.put(_contextPath + "/api/financeiro/lancamento/situacao", data));
        },

        updateAll: function(data) {
            return PromiseService.execute(
                    $http.put(_contextPath + "/api/financeiro/lancamento/", data));
        },

        delete: function(idLancamento) {
            return PromiseService.execute(
                    $http.delete(_contextPath + "/api/financeiro/lancamento/" + idLancamento));
        },

        deleteAll: function(data) {
            return PromiseService.execute(
                    $http.post(_contextPath + "/api/financeiro/lancamento/delete", data));
        },
        
        lancamentoFromRateio: function(lancamento) {
            return lancamentoFromRateio(lancamento);
        },
        
        lancamentosFromRateio: function(lancamentos) {
            var rateios = [];
            _.union(rateios, _.map(lancamentos, function(lancamento) {
                return lancamentoFromRateio(lancamento);
            }));
            return rateios;
        }

    }

});