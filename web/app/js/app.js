'use strict';

var app = app || angular.module('App', [
    'ngSanitize',
    'ngCookies',
    'ngResource',
    'ngAnimate',
    'ui.router',
    'ui.bootstrap',
    'ui.mask',
    'ui.utils.masks',
    'datatables',
    'datatables.bootstrap',
    'highcharts-ng',
    'angularFileUpload'
]);

app.config(['$stateProvider', '$urlRouterProvider', '$locationProvider',
    function ($stateProvider, $urlRouterProvider, $locationProvider) {

        $locationProvider.html5Mode(true);
        $urlRouterProvider.otherwise(function(){
            window.location = '../NewTemplate/Dashboard/index.jsp';
        });

        $stateProvider
                            
            /* ***** HELP ***** */
            
            .state('help', {
                abstract: true,
                url: '/help',
                template: '<div ui-view></div>'
            })                
            .state('help.home', {
                url: '/',
                templateUrl: 'partials/help/help.html',
                controller: 'HelpController',
                resolve: {}
            })
                                            
            /* ***** APP ***** */
            
            .state('app', {
                abstract: true,
                template: '<div ui-view></div>',
                controller: 'AppController'
            })
        
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
            
            .state('app.financeiro', {
                abstract: true,
                url: '/financeiro',
                template: '<div ui-view></div>'
            })            
            .state('app.financeiro.home', {
                url: '/',
                templateUrl: 'partials/financeiro/financeiro.html',
                controller: 'FinanceiroController',
                resolve: {}
            })          
            .state('app.financeiro.chart', {
                url: '/chart/:tipo',
                templateUrl: 'partials/financeiro/chart.html',
                controller: 'FinanceiroController',
                resolve: {}
            })
            .state('app.financeiro.demonstrativo', {
                url: '/demonstrativo',
                templateUrl: 'partials/financeiro/demonstrativo.html',
                controller: 'DemonstrativoController',
                resolve: {}
            })
//            .state('app.financeiro.banco', {
//                url: '/banco',
//                templateUrl: 'partials/financeiro/banco/banco.html',
//                controller: 'BancoController',
//                resolve: {}
//            })
            .state('app.financeiro.contacorrente', {
                url: '/contacorrente',
                templateUrl: 'partials/financeiro/contaCorrente/contaCorrente.html',
                controller: 'ContaCorrenteController',
                resolve: {}
            })
            .state('app.financeiro.carteiracobranca', {
                url: '/carteiracobranca',
                templateUrl: 'partials/financeiro/carteiraCobranca/carteiraCobranca.html',
                controller: 'CarteiraCobrancaController',
                resolve: {}
            })
            .state('app.financeiro.cartaocredito', {
                url: '/cartaocredito',
                templateUrl: 'partials/financeiro/cartaoCredito/cartaoCredito.html',
                controller: 'CartaoCreditoController',
                resolve: {}
            })
            .state('app.financeiro.planoconta', {
                url: '/planoconta',
                templateUrl: 'partials/financeiro/planoConta/planoConta.html',
                controller: 'PlanoContaController',
                resolve: {}
            })
            .state('app.financeiro.centrocusto', {
                url: '/centrocusto',
                templateUrl: 'partials/financeiro/centroCusto/centroCusto.html',
                controller: 'CentroCustoController',
                resolve: {}
            })
            .state('app.financeiro.conta', {
                url: '/conta',
                templateUrl: 'partials/financeiro/conta/conta_lista.html',
                controller: 'ContaController',
                resolve: {}
            })
            .state('app.financeiro.lancamento', {
                url: '/lancamento',
                templateUrl: 'partials/financeiro/lancamento/lancamento.html',
                controller: 'LancamentoController',
                resolve: {}
            })
            .state('app.financeiro.lancamentoprogramado', {
                url: '/lancamento/programado',
                templateUrl: 'partials/financeiro/lancamentoProgramado/lancamentoProgramado.html',
                controller: 'LancamentoProgramadoController',
                resolve: {}
            })
            .state('app.financeiro.lancamentoprogramado.edit', {
                url: '/lancamento/programado/:id',
                templateUrl: 'partials/financeiro/lancamentoProgramado/lancamentoProgramado.html',
                controller: 'LancamentoProgramadoController',
                resolve: {}
            });

    }]);

