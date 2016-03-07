'use strict';

var app = angular.module('App', [
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

app.config(['$stateProvider', '$urlRouterProvider', '$locationProvider',
    function ($stateProvider, $urlRouterProvider, $locationProvider) {

        $locationProvider.html5Mode(true);
        $urlRouterProvider.otherwise(function(){
            window.location = '../NewTemplate/Dashboard/index.jsp';
        });

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
            })
            .state('veiculo.manutencao', {
                url: '/manutencao',
                templateUrl: 'partials/veiculo/veiculoManutencao.html',
                controller: 'VeiculoManutencaoController',
                resolve: {}
            })
            .state('veiculo.combustivel', {
                url: '/combustivel',
                templateUrl: 'partials/veiculo/veiculoCombustivel.html',
                controller: 'VeiculoCombustivelController',
                resolve: {}
            })
            .state('veiculo.multa', {
                url: '/multa',
                templateUrl: 'partials/veiculo/veiculoMulta.html',
                controller: 'VeiculoMultaController',
                resolve: {}
            })
            .state('veiculo.seguro', {
                url: '/seguro',
                templateUrl: 'partials/veiculo/veiculoSeguro.html',
                controller: 'VeiculoSeguroController',
                resolve: {}
            })
            .state('veiculo.sinistro', {
                url: '/sinistro',
                templateUrl: 'partials/veiculo/veiculoSinistro.html',
                controller: 'VeiculoSinistroController',
                resolve: {}
            });

    }]);