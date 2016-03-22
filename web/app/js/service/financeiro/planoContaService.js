'use strict';

app.factory('PlanoContaService', function($http, $q) {

    return {

        getAll: function() {
            return $http.get(_contextPath + "/api/financeiro/planoconta/");
        },

        getStructure: function() {
            return $http.get(_contextPath + "/api/financeiro/planoconta/structure");
        },

        get: function(idPlanoConta) {
            return $http.get(_contextPath + "/api/financeiro/planoconta/" + idPlanoConta);
        },

        save: function(data) {
            return $http.post(_contextPath + "/api/financeiro/planoconta/", data);
        },

        update: function(idPlanoConta, data) {
            return $http.put(_contextPath + "/api/financeiro/planoconta/" + idPlanoConta, data);
        },

        delete: function(idPlanoConta) {
            return $http.delete(_contextPath + "/api/financeiro/planoconta/" + idPlanoConta);
        }

    }

});