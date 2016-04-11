'use strict';

app.controller('FinanceiroController', ['$scope', '$q', '$filter', 'ContaService', 'PlanoContaService', 'LancamentoService', 'SaldoService', 'ModalService', 'LISTAS',
    function ($scope, $q, $filter, ContaService, PlanoContaService, LancamentoService, SaldoService, ModalService, LISTAS) {
            
        var init = function () {
            $scope.tipos = LISTAS.lancamento;
            $scope.anoAtual = new Date().getFullYear();
            $scope.mesAtual = new Date().getMonth();
            contasSaldos(); 
            saldosByTipo(); 
            saldosDespesa();
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
        
        var configChartReceitaDespesa = function(receitas, despesas) {        
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
                series: [{"name": $scope.tipos[0].descricao, "data": receitas, color: '#90ed7d'},
                         {"name": $scope.tipos[1].descricao, "data": despesas, color: '#f45b5b'}],
                size: {
                    height: 308
                }     
                //loading: true
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
                    var receitas = _.pluck(values[0], 'valor');
                    var despesas = _.pluck(values[1], 'valor');         
                    configChartReceitaDespesa(receitas, despesas);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        // Despesas /////      
        
        var configChartDespesa = function() {        
            $scope.configChartDespesa = { 
                options: { 
                    "chart": { "type": "areaspline" }
                },
                title: " ",
                xAxis: {
                    categories: getDescricaoMeses()   
                },
                yAxis: {
                    min: 0,
                    title: {text: ' '}
                },
                series: [{"name":"Some data","data":[1,2,4],"id":"series-0","type":"pie"}],
                size: {
                    height: 308
                }     
                //loading: true
            };
        };  
        
        var saldosDespesa = function() {
            $q.all([PlanoContaService.getStructureByTipo($scope.tipos[1].id), LancamentoService.getPlanoContaSaldo(2016, 4, 4)])
               .then(function(values) {  
                    var estruturas = values[0];
                    var saldos = values[1];
                    estruturas = PlanoContaService.estrutura(estruturas);
                    estruturas = PlanoContaService.flatten(estruturas);
                    SaldoService.saldoByMes(estruturas, saldos, LISTAS.meses[3]);
                    SaldoService.saldoGrupo(estruturas);
                    console.log(_.filter(estruturas, function(estrutura) {
                        return estrutura.nivel === 2;
                    }));
                    configChartDespesa();
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };
        
        init();

    }]);
