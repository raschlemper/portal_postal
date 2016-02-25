'use strict';

app.factory('VeiculoService', function($http, $q) {

    return {

        getAll: function(callback) {
            var cb = callback || angular.noop;
            var deferred = $q.defer();

            $http.get(_contextPath + "/api/veiculo/").
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

        get: function(idVeiculo, callback) {
            var cb = callback || angular.noop;
            var deferred = $q.defer();

            $http.get(_contextPath + "/api/veiculo?action=get&idVeiculo=" + idVeiculo).
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

            $http.post(_contextPath + "/api/veiculo?action=save", data).
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

        delete: function(idVeiculo, callback) {
            var cb = callback || angular.noop;
            var deferred = $q.defer();

            $http.post(_contextPath + "/api/veiculo?action=delete&idVeiculo=" + idVeiculo).
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