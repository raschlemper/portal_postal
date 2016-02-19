'use strict';

var veiculo = angular.module('Veiculo', [
  'ngSanitize',
  'ngCookies',
  'ngResource',
  'ui.router',
  'ui.mask',
  'ui.utils.masks'
]);

veiculo.config(['$stateProvider', '$urlRouterProvider',  
    function ($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/404');

    $stateProvider
      .state('cadastro', {
        url: '/cadastro',
        templateUrl: 'partials/cadastro.html',
        controller: 'VeiculoController',
        resolve: {}
      });

}]);