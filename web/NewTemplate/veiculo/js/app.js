'use strict';

var veiculo = angular.module('Veiculo', [
  'ngSanitize',
  'ngCookies',
  'ngResource',
  'ngAnimate',
  'ui.router',
  'ui.bootstrap',
  'ui.mask',
  'ui.utils.masks',
  //'veasyTable',
  'datatables',
  'datatables.bootstrap'
]);

veiculo.config(['$stateProvider', '$urlRouterProvider',  
    function ($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/404');

    $stateProvider
      .state('cadastro', {
        url: '/cadastro',
        templateUrl: 'partials/veiculo.html',
        controller: 'VeiculoController',
        resolve: {}
      });

}]);