'use strict';

app.factory('CartaoCreditoService', function($http, $q) {

    return {

        getAll: function(callback) {
            var cb = callback || angular.noop;
            var deferred = $q.defer();

            $http.get(_contextPath + "/api/financeiro/cartaocredito/").
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

        get: function(idCartaoCredito, callback) {
            var cb = callback || angular.noop;
            var deferred = $q.defer();

            $http.get(_contextPath + "/api/financeiro/cartaocredito/" + idCartaoCredito).
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

            $http.post(_contextPath + "/api/financeiro/cartaocredito/", data).
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

        update: function(idCartaoCredito, data, callback) {
            var cb = callback || angular.noop;
            var deferred = $q.defer();

            $http.put(_contextPath + "/api/financeiro/cartaocredito/" + idCartaoCredito, data).
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

        delete: function(idCartaoCredito, callback) {
            var cb = callback || angular.noop;
            var deferred = $q.defer();

            $http.delete(_contextPath + "/api/financeiro/cartaocredito/" + idCartaoCredito).
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