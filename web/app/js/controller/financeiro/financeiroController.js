'use strict';

app.controller('FinanceiroController', ['$scope', '$q', '$filter', 'ContaService', 'LancamentoService', 'ModalService', 'LISTAS',
    function ($scope, $q, $filter, ContaService, LancamentoService, ModalService, LISTAS) {
            
        var init = function () {
            $scope.tipos = LISTAS.lancamento;
            $scope.anoAtual = new Date().getFullYear();
            $scope.mesAtual = new Date().getMonth();
            contasSaldos(); 
            saldosByTipo();
        }; 
        
        // Contas ////////////////
        
        var contasSaldos =function() {
            ContaService.getSaldo()
                .then(function (data) {
                    $scope.contas = createContasSaldosLista(data);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        }
        
        var createContasSaldosLista = function(contas) {    
            return _.map(contas, function(conta) {  
                conta.saldo += conta.valorSaldoAbertura;
                return _.pick(conta, 'idConta', 'nome', 'saldo');
            })
        }
        
        // Receita X Despesas ////////////////
        
        var configChartReceitaDespesa = function() {        
            $scope.configChartReceitaDespesa = { 
                options: { 
                    "chart": { "type": "column" }
                },
                title: " ",
                xAxis: {
                    categories: getDescricaoMeses()   
                },
                yAxis: {
                    min: 0,
                    title: {text: ' '}
                },
                series: [{"name": $scope.tipos[0].descricao, "data": $scope.receitas, color: '#90ed7d'},
                         {"name": $scope.tipos[1].descricao, "data": $scope.despesas, color: '#f45b5b'}],
                size: {
                    height: 308
                }     
//                loading: true
            };
        };
        
        var getDescricaoMeses = function() {
            var meses = getLastThreeMonths();
            return _.map(meses, function(mes) {
                return mes.descricao;
            })
        }
        
        var getLastThreeMonths = function() {            
            return _.filter(LISTAS.meses, function(mes) {
                return ($scope.mesAtual === mes.id || $scope.mesAtual - 1 === mes.id || $scope.mesAtual - 2 === mes.id);
            })
        };
                
        var saldosByTipo = function() {
            var meses = getLastThreeMonths();
            $q.all([
                LancamentoService.findSaldoByTipo($scope.tipos[0].id, $scope.anoAtual, meses[0].id + 1, meses[meses.length - 1].id + 1),
                LancamentoService.findSaldoByTipo($scope.tipos[1].id, $scope.anoAtual, meses[0].id + 1, meses[meses.length - 1].id + 1)
            ]).then(function(values) {  
                    setSaldosByTipo(meses, values[0], $scope.tipos[0]);
                    setSaldosByTipo(meses, values[1], $scope.tipos[1]);           
                    configChartReceitaDespesa();
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        var setSaldosByTipo = function(meses, saldos, tipo) {
            angular.forEach(saldos, function(saldo) {
                var listaSaldos = getMesSaldo(meses, saldo);
                if(tipo.codigo === 'receita') { $scope.receitas = listaSaldos; }
                else if(tipo.codigo === 'despesa') { $scope.despesas = listaSaldos; }
            });
        }
        
        var getMesSaldo = function(meses, saldo) {
            var values = [];
            angular.forEach(meses, function(mes) {
                if(mes.id === saldo.mes - 1) return values.push(saldo.valor);
                values.push(0);
            });
            return values;
        }
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };
        
        init();

    }]);
