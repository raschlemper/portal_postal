'use strict';

var app = angular.module('Financeiro', [
  'ngSanitize',
  'ngCookies',
  'ngResource',
  'ui.router'    
]);

app.config(['$stateProvider', '$urlRouterProvider', '$locationProvider', 
    function ($stateProvider, $urlRouterProvider, $locationProvider) {

//    $locationProvider.html5Mode(true);
    $urlRouterProvider.otherwise('/404');

    $stateProvider
      .state('lancamentos', {
        url: '/lancamentos',
        templateUrl: 'partials/financeiro.html',
        controller: 'FinanceiroController',
        resolve: {}
      })
      .state('receita', {
        url: '/receita',
        templateUrl: 'partials/receita.html',
        controller: 'FinanceiroController',
        resolve: {}
      });

  }])