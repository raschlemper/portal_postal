'use strict';

app.factory('VeiculoCombustivelService', function($http, $q) {

    return {

        getAll: function(callback) {
            var cb = callback || angular.noop;
            var deferred = $q.defer();

            $http.get(_contextPath + "/api/veiculo/combustivel/").
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

        get: function(idVeiculoCombustivel, callback) {
            var cb = callback || angular.noop;
            var deferred = $q.defer();

            $http.get(_contextPath + "/api/veiculo/combustivel/" + idVeiculoCombustivel).
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

        save: function(data, callback) {
            var cb = callback || angular.noop;
            var deferred = $q.defer();

            $http.post(_contextPath + "/api/veiculo/combustivel/", data).
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

        update: function(idVeiculoCombustivel, data, callback) {
            var cb = callback || angular.noop;
            var deferred = $q.defer();

            $http.put(_contextPath + "/api/veiculo/combustivel/" + idVeiculoCombustivel, data).
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

        delete: function(idVeiculoCombustivel, callback) {
            var cb = callback || angular.noop;
            var deferred = $q.defer();

            $http.delete(_contextPath + "/api/veiculo/combustivel/" + idVeiculoCombustivel).
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