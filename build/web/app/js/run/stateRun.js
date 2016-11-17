'use strict';

app.run(['$rootScope', '$state', 'MESSAGES', function($rootScope, $state, MESSAGES) {
        
    $rootScope.version = MESSAGES.VERSION;

    $rootScope.$on('$stateChangeSuccess', function(e, toState, toParams, fromState, fromParams){
    });
    
    $rootScope.$on('$stateChangeStart', function(e, toState, toParams, fromState, fromParams) {
    });
    
}]);