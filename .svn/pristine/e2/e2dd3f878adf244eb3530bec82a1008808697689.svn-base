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

            $http.get(_contextPath + "/api/veiculo/" + idVeiculo).
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

        getByPlaca: function(placa, callback) {
            var cb = callback || angular.noop;
            var deferred = $q.defer();

            $http.get(_contextPath + "/api/veiculo/placa/" + placa).
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

        getCombustivel: function(idVeiculo, callback) {
            var cb = callback || angular.noop;
            var deferred = $q.defer();

            $http.get(_contextPath + "/api/veiculo/" + idVeiculo + "/combustivel").
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

        getManutencao: function(idVeiculo, callback) {
            var cb = callback || angular.noop;
            var deferred = $q.defer();

            $http.get(_contextPath + "/api/veiculo/" + idVeiculo + "/manutencao").
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

        getMulta: function(idVeiculo, callback) {
            var cb = callback || angular.noop;
            var deferred = $q.defer();

            $http.get(_contextPath + "/api/veiculo/" + idVeiculo + "/multa").
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

        getSeguro: function(idVeiculo, callback) {
            var cb = callback || angular.noop;
            var deferred = $q.defer();

            $http.get(_contextPath + "/api/veiculo/" + idVeiculo + "/seguro").
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

        getSinistro: function(idVeiculo, callback) {
            var cb = callback || angular.noop;
            var deferred = $q.defer();

            $http.get(_contextPath + "/api/veiculo/" + idVeiculo + "/sinistro").
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

            $http.post(_contextPath + "/api/veiculo/", data).
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

        update: function(idVeiculo, data, callback) {
            var cb = callback || angular.noop;
            var deferred = $q.defer();

            $http.put(_contextPath + "/api/veiculo/" + idVeiculo, data).
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

            $http.delete(_contextPath + "/api/veiculo/" + idVeiculo).
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