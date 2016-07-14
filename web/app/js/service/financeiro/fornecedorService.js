'use strict';

app.factory('FornecedorService', function($http, PromiseService) {

    return {

        getAll: function() {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/fornecedor/"));
        },

        get: function(idFornecedor) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/fornecedor/" + idFornecedor));
        },

        getLancamento: function(idFornecedor) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/fornecedor/" + idFornecedor + "/lancamento"));
        },

        getLancamentoProgramado: function(idFornecedor) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/fornecedor/" + idFornecedor + "/lancamento/programado"));
        },


        save: function(data) {
            return PromiseService.execute(
                    $http.post(_contextPath + "/api/financeiro/fornecedor/", data));
        },

        update: function(idFornecedor, data) {
            return PromiseService.execute(
                    $http.put(_contextPath + "/api/financeiro/fornecedor/" + idFornecedor, data));
        },

        delete: function(idFornecedor) {
            return PromiseService.execute(
                    $http.delete(_contextPath + "/api/financeiro/fornecedor/" + idFornecedor));
        }

    }

});