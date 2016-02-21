'use strict';

veiculo.factory('loadingInterceptor', ['$rootScope', '$q', '$timeout', 
    function ($rootScope, $q, $timeout) {
        return {
            request: function (config) {
                $rootScope.requestInProgress = $rootScope.requestInProgress || 0;
                if($rootScope.requestInProgress <= 0) { waitMsg(); }
                $rootScope.requestInProgress++;
                return config;
            },
            requestError: function (rejection) {
                $timeout(function () {
                    $rootScope.requestInProgress--;                    
                    if($rootScope.requestInProgress <= 0) { $('.my-modal').modal('hide'); }
                }, 500);
                return $q.reject(rejection);
            },
            response: function (response) {
                $timeout(function () {
                    $rootScope.requestInProgress--;                 
                    if($rootScope.requestInProgress <= 0) { $('.my-modal').modal('hide'); }
                }, 500);
                return response;
            },
            responseError: function (rejection) {
                $timeout(function () {
                    $rootScope.requestInProgress--;                 
                    if($rootScope.requestInProgress <= 0) { $('.my-modal').modal('hide'); }
                }, 500);
                return $q.reject(rejection);
            }
        };
    }]);

veiculo.config(['$httpProvider', 
    function ($httpProvider) {
        $httpProvider.interceptors.push('loadingInterceptor');
    }]);