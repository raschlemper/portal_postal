'use strict';

app.factory('loadingInterceptor', ['$rootScope', '$q', '$timeout',
    function ($rootScope, $q, $timeout) {
        return {
            request: function (config) {
                $rootScope.requestInProgress = $rootScope.requestInProgress || 0;
                if($rootScope.requestInProgress <= 0) {                     
                    if(config.url.indexOf("partials/modal/modalExcluir.html") >= 0 || 
                       config.url.indexOf("partials/modal/modalMessage.html") >= 0 || 
                       config.url.indexOf("template/typeahead/typeahead-match.html") >= 0) { 
                        $rootScope.requestInProgress++;
                        return config; 
                    }
                    waitMsg(); 
                }
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
                    if(response.status == '401') { window.location = '../index.jsp?msgLog=3'; }
                }, 500);
                return response;
            },
            responseError: function (rejection) {
                $timeout(function () {
                    $rootScope.requestInProgress--;                 
                    if($rootScope.requestInProgress <= 0) { $('.my-modal').modal('hide'); }
                    if(rejection.status == '401') { window.location = '../index.jsp?msgLog=3'; }
                }, 500);
                return $q.reject(rejection);
            }
        };
    }]);

app.config(['$httpProvider', 
    function ($httpProvider) {
        $httpProvider.interceptors.push('loadingInterceptor');
    }]);