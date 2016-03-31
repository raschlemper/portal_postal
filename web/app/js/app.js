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
        
            /* ***** VEÍCULO ***** */
            
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
            })
            
            
            /* ***** FINANCEIRO ***** */
            
            .state('financeiro', {
                abstract: true,
                url: '/financeiro',
                templateUrl: 'partials/financeiro/financeiro.html'
            })
            .state('financeiro.banco', {
                url: '/banco',
                templateUrl: 'partials/financeiro/banco/banco.html',
                controller: 'BancoController',
                resolve: {}
            })
            .state('financeiro.contacorrente', {
                url: '/contacorrente',
                templateUrl: 'partials/financeiro/contaCorrente/contaCorrente.html',
                controller: 'ContaCorrenteController',
                resolve: {}
            })
            .state('financeiro.cartaocredito', {
                url: '/cartaocredito',
                templateUrl: 'partials/financeiro/cartaoCredito/cartaoCredito.html',
                controller: 'CartaoCreditoController',
                resolve: {}
            })
            .state('financeiro.planoconta', {
                url: '/planoconta',
                templateUrl: 'partials/financeiro/planoConta/planoConta.html',
                controller: 'PlanoContaController',
                resolve: {}
            })
            .state('financeiro.conta', {
                url: '/conta',
                templateUrl: 'partials/financeiro/conta/conta_lista.html',
                controller: 'ContaController',
                resolve: {}
            });

    }]);

