'use strict';

app.factory('LancamentoProgramadoTransferenciaService', function($http, PromiseService) {

    return {

        getAll: function() {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/programado/transferencia/"));
        },

        get: function(idLancamentoProgramadoTransferencia) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/programado/transferencia/" + idLancamentoProgramadoTransferencia));
        },

        save: function(data) {
            return PromiseService.execute(
                    $http.post(_contextPath + "/api/financeiro/lancamento/programado/transferencia/", data));
        },

        update: function(idLancamentoProgramadoTransferencia, data) {
            return PromiseService.execute(
                    $http.put(_contextPath + "/api/financeiro/lancamento/programado/transferencia/" + idLancamentoProgramadoTransferencia, data));
        },

        delete: function(idLancamentoProgramadoTransferencia) {
            return PromiseService.execute(
                    $http.delete(_contextPath + "/api/financeiro/lancamento/programado/transferencia/" + idLancamentoProgramadoTransferencia));
        }

    }

});