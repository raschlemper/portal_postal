'use strict';

app.factory('FipeService', function($http, $q) {

    return {

        marcaVeiculo: function(tipo, callback) {
            var cb = callback || angular.noop;
            var deferred = $q.defer();

            $http.jsonp("http://fipeapi.appspot.com/api/1/" + tipo + "/marcas.json?callback=JSON_CALLBACK").
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
        },

        modeloVeiculo: function(tipo, marca, callback) {
            var cb = callback || angular.noop;
            var deferred = $q.defer();

            $http.jsonp("http://fipeapi.appspot.com/api/1/" + tipo + "/veiculos/" + marca + ".json?callback=JSON_CALLBACK").
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