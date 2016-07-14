'use strict';

app.factory('authInterceptor', ['$q', '$timeout',
    function ($q, $timeout) {
        return {
            request: function (config) {
                return config;
            },
            requestError: function (rejection) {
                return $q.reject(rejection);
            },
            response: function (response) {
                $timeout(function () {
                    if(response.status == '401') { window.location = '../index.jsp?msgLog=3'; }
                }, 500);
                return response;
            },
            responseError: function (rejection) {
                $timeout(function () {
                    if(rejection.status == '401') { window.location = '../index.jsp?msgLog=3'; }
                }, 500);
                return $q.reject(rejection);
            }
        };
    }]);

app.config(['$httpProvider', 
    function ($httpProvider) {
        $httpProvider.interceptors.push('authInterceptor');
    }]);