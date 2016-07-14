'use strict';

app.factory('TipoCategoriaService', function($http, PromiseService) {

    return {

        getAll: function() {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/tipo/categoria/"));
        },

        get: function(idTipoCategoria) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/tipo/categoria/" + idTipoCategoria));
        },

        getByDescricao: function(descricao) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/tipo/categoria/descricao/" + descricao));
        },

        save: function(data) {
            return PromiseService.execute(
                    $http.post(_contextPath + "/api/financeiro/tipo/categoria/", data));
        },

        update: function(idTipoCategoria, data) {
            return PromiseService.execute(
                    $http.put(_contextPath + "/api/financeiro/tipo/categoria/" + idTipoCategoria, data));
        },

        delete: function(idTipoCategoria) {
            return PromiseService.execute(
                    $http.delete(_contextPath + "/api/financeiro/tipo/categoria/" + idTipoCategoria));
        }

    }

});