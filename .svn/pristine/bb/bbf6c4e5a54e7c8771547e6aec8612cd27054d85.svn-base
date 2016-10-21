'use strict';

app.factory('PromiseService', function($http, $q) {

    return {
    
        execute: function(http, callback) {
            var cb = callback || angular.noop;
            var deferred = $q.defer();
            http.success(function(data) {
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
