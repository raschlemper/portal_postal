'use strict';

app.factory('VeiculoManutencaoService', function($http, $q) {

    return {

        getAll: function(callback) {
            var cb = callback || angular.noop;
            var deferred = $q.defer();

            $http.get(_contextPath + "/api/veiculo/manutencao/").
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

        get: function(idVeiculoManutencao, callback) {
            var cb = callback || angular.noop;
            var deferred = $q.defer();

            $http.get(_contextPath + "/api/veiculo/manutencao/" + idVeiculoManutencao).
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

            $http.post(_contextPath + "/api/veiculo/manutencao/", data).
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

        update: function(idVeiculoManutencao, data, callback) {
            var cb = callback || angular.noop;
            var deferred = $q.defer();

            $http.put(_contextPath + "/api/veiculo/manutencao/" + idVeiculoManutencao, data).
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

        delete: function(idVeiculoManutencao, callback) {
            var cb = callback || angular.noop;
            var deferred = $q.defer();

            $http.delete(_contextPath + "/api/veiculo/manutencao/" + idVeiculoManutencao).
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