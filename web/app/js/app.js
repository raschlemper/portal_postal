'use strict';

var app = angular.module('Veiculo', [
    'ngSanitize',
    'ngCookies',
    'ngResource',
    'ngAnimate',
    'ui.router',
    'ui.bootstrap',
    'ui.mask',
    'ui.utils.masks',
    'datatables',
    'datatables.bootstrap'
]);

app.config(['$stateProvider', '$urlRouterProvider',
    function ($stateProvider, $urlRouterProvider) {

        $urlRouterProvider.otherwise('/404');

        $stateProvider
            .state('veiculo', {
                abstract: true,
                url: '/veiculo',
                template: '<div ui-view></div>'
            })
            .state('veiculo.cadastro', {
                url: '/cadastro',
                templateUrl: 'partials/veiculo/veiculo.html',
                controller: 'VeiculoController',
                resolve: {}
            });

    }]);