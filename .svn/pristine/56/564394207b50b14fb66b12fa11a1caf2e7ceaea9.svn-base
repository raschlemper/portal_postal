'use strict';

app.factory('CepService', function($http, $q) {

    return {

        cep: function(cep, callback) {
            var cb = callback || angular.noop;
            var deferred = $q.defer();

            $http.jsonp("http://api.postmon.com.br/v1/cep/" + cep + '?callback=JSON_CALLBACK').
                success(function(data) {
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