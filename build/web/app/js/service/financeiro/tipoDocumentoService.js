'use strict';

app.factory('TipoDocumentoService', function($http, PromiseService) {

    return {

        getAll: function() {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/tipo/documento/"));
        },

        get: function(idTipoDocumento) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/tipo/documento/" + idTipoDocumento));
        },

        getByDescricao: function(descricao) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/tipo/documento/descricao/" + descricao));
        },

        save: function(data) {
            return PromiseService.execute(
                    $http.post(_contextPath + "/api/financeiro/tipo/documento/", data));
        },

        update: function(idTipoDocumento, data) {
            return PromiseService.execute(
                    $http.put(_contextPath + "/api/financeiro/tipo/documento/" + idTipoDocumento, data));
        },

        delete: function(idTipoDocumento) {
            return PromiseService.execute(
                    $http.delete(_contextPath + "/api/financeiro/tipo/documento/" + idTipoDocumento));
        }

    }

});