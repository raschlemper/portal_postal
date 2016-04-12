'use strict';

app.controller('FinanceiroController', ['$scope', '$q', '$filter', 'ContaService', 'PlanoContaService', 'LancamentoService', 'SaldoService', 'ModalService', 'ListaService', 'LISTAS',
    function ($scope, $q, $filter, ContaService, PlanoContaService, LancamentoService, SaldoService, ModalService, ListaService, LISTAS) {
            
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
                series: [{name: $scope.tipos[0].descricao, data: receitas, color: '#90ed7d'},
                         {name: $scope.tipos[1].descricao, data: despesas, color: '#f45b5b'}],
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
            var dataInicio = moment().endOf("month").subtract(3, 'months').format('YYYY-MM-DD');
            var dataFim = moment().endOf("month").format('YYYY-MM-DD');
            LancamentoService.getSaldoTipo(dataInicio, dataFim)
                .then(function(data) {  
                    var valuesReceita = _.filter(data, function(item) { return item.id === 0; });
                    var valuesDespesa = _.filter(data, function(item) { return item.id === 1; });
                    var receitas = _.pluck(valuesReceita, 'valor');
                    var despesas = _.pluck(valuesDespesa, 'valor');         
                    configChartReceitaDespesa(receitas, despesas);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        // Despesas /////      
        
        var configChartDespesa = function(values) {        
            $scope.configChartDespesa = { 
                title: " ",
                options: { 
                    chart: { type: "pie" },                
                    plotOptions: {
                        pie: {
                            allowPointSelect: true,
                            cursor: 'pointer',
                            dataLabels: {
                                enabled: false
                            },
                            showInLegend: true
                        }
                    },
                },
                series: [{                        
                    name: 'Brands',
                    colorByPoint: true,
                    type: "pie",
                    data: values
                }],
                size: {
                    height: 308
                }     
                //loading: true
            };
        };  
        
        var saldosDespesa = function() {
            var dataInicio = moment().startOf("month").format('YYYY-MM-DD');
            var dataFim = moment().endOf("month").format('YYYY-MM-DD');
            var mesAtual = ListaService.getValue(LISTAS.meses, moment().format('MM')-1);
            $q.all([PlanoContaService.getStructureByTipo($scope.tipos[1].id), 
                    LancamentoService.getSaldoPlanoConta(dataInicio, dataFim)])
               .then(function(values) {  
                    var estruturas = values[0];
                    var saldos = formatPlanoConta(values[1]);
                    PlanoContaService.estrutura(estruturas);
                    var estruturas = PlanoContaService.flatten(estruturas);
                    SaldoService.saldoPlanoContaByMes(estruturas, saldos, mesAtual);
                    SaldoService.saldoPlanoContaGrupo(estruturas);
                    estruturas = _.filter(estruturas, function(estrutura) {
                        return estrutura.nivel === 2;
                    });
                    configChartDespesa(getDataChartDespesa(estruturas, mesAtual));
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        var formatPlanoConta = function(saldos) {
            return _.map(saldos, function(saldo) {
                saldo.idPlanoConta = saldo.id;
                saldo.mes = moment(saldo.data).format('MM');
                saldo.ano = moment(saldo.data).format('YYYY');
                return _.pick(saldo, ['idPlanoConta', 'mes', 'ano', 'valor']);
            });
            
        };
        
        var getDataChartDespesa = function(estruturas, mes) {            
            return _.map(estruturas, function(estrutura) {
                return {name: estrutura.descricao, y: estrutura.saldos[mes.id]};
            });            
        }
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };
        
        init();

    }]);
