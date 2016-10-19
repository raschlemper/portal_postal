'use strict';

app.factory('ConfiguracaoService', function($http, PromiseService) {

    return {

        get: function() {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/configuracao/financeiro/"));
        },
        
        save: function(data) {
            return PromiseService.execute(
                    $http.put(_contextPath + "/api/configuracao/financeiro/", data));
        }

    }

});