'use strict';

app.factory('FavorecidoService', function($http, PromiseService) {

    return {

        getAll: function() {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/favorecido/"));
        },

        get: function(idFavorecido) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/favorecido/" + idFavorecido));
        },

        save: function(data) {
            return PromiseService.execute(
                    $http.post(_contextPath + "/api/financeiro/favorecido/", data));
        },

        update: function(idFavorecido, data) {
            return PromiseService.execute(
                    $http.put(_contextPath + "/api/financeiro/favorecido/" + idFavorecido, data));
        },

        delete: function(idFavorecido) {
            return PromiseService.execute(
                    $http.delete(_contextPath + "/api/financeiro/favorecido/" + idFavorecido));
        }

    }

});