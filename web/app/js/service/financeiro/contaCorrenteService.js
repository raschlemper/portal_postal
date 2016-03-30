'use strict';

app.factory('ContaCorrenteService', function($http, PromiseService) {

    return {

        getAll: function() {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/contacorrente/"));
        },

        get: function(idContaCorrente) {
            return PromiseService.execute(
                $http.get(_contextPath + "/api/financeiro/contacorrente/" + idContaCorrente));
        },
        
        getByContaCorrente: function(banco, agencia, agenciaDv, contacorrente, contacorrenteDv) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/contacorrente/" +
                        "banco/" + banco + "/agencia/" + agencia + "/" + agenciaDv + 
                        "/contacorrente/" + contacorrente + "/" + contacorrenteDv));
        },

        save: function(data) {
            return PromiseService.execute(
                $http.post(_contextPath + "/api/financeiro/contacorrente/", data));
        },

        update: function(idContaCorrente, data) {
            return PromiseService.execute(
                $http.put(_contextPath + "/api/financeiro/contacorrente/" + idContaCorrente, data));
        },

        delete: function(idContaCorrente) {
            return PromiseService.execute(
                $http.delete(_contextPath + "/api/financeiro/contacorrente/" + idContaCorrente));
        }

    }

});