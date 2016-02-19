'use strict';

veiculo.factory('VeiculoService', function($http, $q) {

    return {

        marcaVeiculo: function(tipo, callback) {
            var cb = callback || angular.noop;
            var deferred = $q.defer();

            $http.get("http://fipeapi.appspot.com/api/1/" + tipo + "/marcas.json")
                .success(function(data) {
                    deferred.resolve(data);
                    return cb();
                })
                .error(function(err) {
                    deferred.reject(err);
                    return cb(err);
                }
                .bind(this));

            return deferred.promise;
        }

    }

});