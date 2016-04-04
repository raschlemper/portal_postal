'use strict';

app.factory('CarteiraCobrancaService', function($http, PromiseService) {

    return {

        getAll: function() {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/carteiracobranca/"));
        },

        get: function(idCarteiraCobranca) {
            return PromiseService.execute(
                $http.get(_contextPath + "/api/financeiro/carteiracobranca/" + idCarteiraCobranca));
        },
        
        getByCarteiraCobranca: function(idContaCorrente, beneficiario, beneficiarioDv, carteira) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/carteiracobranca/" + idContaCorrente +
                        "/beneficiario/" + beneficiario + "/" + beneficiarioDv + 
                        "/carteira/" + carteira));
        },

        save: function(data) {
            return PromiseService.execute(
                $http.post(_contextPath + "/api/financeiro/carteiracobranca/", data));
        },

        update: function(idCarteiraCobranca, data) {
            return PromiseService.execute(
                $http.put(_contextPath + "/api/financeiro/carteiracobranca/" + idCarteiraCobranca, data));
        },

        delete: function(idCarteiraCobranca) {
            return PromiseService.execute(
                $http.delete(_contextPath + "/api/financeiro/carteiracobranca/" + idCarteiraCobranca));
        }

    }

});