'use strict';

app.factory('ConfiguracaoService', function($http, PromiseService) {

    return {

        get: function() {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/configuracao/"));
        },
        
        save: function(data) {
            return PromiseService.execute(
                    $http.put(_contextPath + "/api/financeiro/configuracao/", data));
        }

    }

});