'use strict';

app.factory('LancamentoTransferenciaService', function($http, PromiseService) {

    return {

        getAll: function() {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/transferencia/"));
        },

        get: function(idLancamentoTransferencia) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/transferencia/" + idLancamentoTransferencia));
        },

        save: function(data) {
            return PromiseService.execute(
                    $http.post(_contextPath + "/api/financeiro/lancamento/transferencia/", data));
        },

        update: function(idLancamentoTransferencia, data) {
            return PromiseService.execute(
                    $http.put(_contextPath + "/api/financeiro/lancamento/transferencia/" + idLancamentoTransferencia, data));
        },

        delete: function(idLancamentoTransferencia) {
            return PromiseService.execute(
                    $http.delete(_contextPath + "/api/financeiro/lancamento/transferencia/" + idLancamentoTransferencia));
        }

    }

});