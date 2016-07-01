'use strict';

app.factory('ColaboradorService', function($http, PromiseService) {

    return {

        getAll: function() {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/colaborador/"));
        },

        get: function(idColaborador) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/colaborador/" + idColaborador));
        },

        getLancamento: function(idConta) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/colaborador/" + idColaborador + "/lancamento"));
        },

        getLancamentoProgramado: function(idConta) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/colaborador/" + idColaborador + "/lancamento/programado"));
        },


        save: function(data) {
            return PromiseService.execute(
                    $http.post(_contextPath + "/api/financeiro/colaborador/", data));
        },

        update: function(idColaborador, data) {
            return PromiseService.execute(
                    $http.put(_contextPath + "/api/financeiro/colaborador/" + idColaborador, data));
        },

        delete: function(idColaborador) {
            return PromiseService.execute(
                    $http.delete(_contextPath + "/api/financeiro/colaborador/" + idColaborador));
        }

    }

});