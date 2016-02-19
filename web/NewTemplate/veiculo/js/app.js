'use strict';

var veiculo = angular.module('Veiculo', [
  'ngSanitize',
  'ngCookies',
  'ngResource',
  'ui.router'    
]);

veiculo.config(['$stateProvider', '$urlRouterProvider', '$locationProvider', 
    function ($stateProvider, $urlRouterProvider, $locationProvider) {

//    $locationProvider.html5Mode(true);
    $urlRouterProvider.otherwise('/404');

    $stateProvider
      .state('cadastro', {
        url: '/cadastro',
        templateUrl: 'partials/cadastro.html',
        controller: 'VeiculoController',
        resolve: {}
      });

  }])