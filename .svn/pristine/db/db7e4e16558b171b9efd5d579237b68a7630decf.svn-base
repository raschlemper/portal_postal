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

        getSaldo: function() {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/conta/saldo"));
        },

        getSaldoLancamento: function(idConta, data) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/conta/" + idConta + "/saldo/lancamento?data=" + data));
        },

        getLancamento: function(idConta, dataInicio, dataFim) {
            var url = _contextPath + "/api/financeiro/conta/" + idConta + "/lancamento";
            if(dataInicio && dataFim) { url += "?dataInicio=" + dataInicio + "&dataFim=" + dataFim; }
            return PromiseService.execute($http.get(url));
        },

        getLancamentoProgramado: function(idConta, dataInicio, dataFim) {
            var url = _contextPath + "/api/financeiro/conta/" + idConta + "/lancamento/programado";
            if(dataInicio && dataFim) { url += "?dataInicio=" + dataInicio + "&dataFim=" + dataFim; }
            return PromiseService.execute($http.get(url));
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