'use strict';

app.factory('TipoFormaPagamentoService', function($http, PromiseService) {

    return {

        getAll: function() {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/tipo/formapagamento/"));
        },

        get: function(idTipoFormaPagamento) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/tipo/formapagamento/" + idTipoFormaPagamento));
        },

        getByDescricao: function(descricao) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/tipo/formapagamento/descricao/" + descricao));
        },

        save: function(data) {
            return PromiseService.execute(
                    $http.post(_contextPath + "/api/financeiro/tipo/formapagamento/", data));
        },

        update: function(idTipoFormaPagamento, data) {
            return PromiseService.execute(
                    $http.put(_contextPath + "/api/financeiro/tipo/formapagamento/" + idTipoFormaPagamento, data));
        },

        delete: function(idTipoFormaPagamento) {
            return PromiseService.execute(
                    $http.delete(_contextPath + "/api/financeiro/tipo/formapagamento/" + idTipoFormaPagamento));
        }

    }

});