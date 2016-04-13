'use strict';

app.controller('FinanceiroController', ['$scope', '$q', '$filter', 'ContaService', 'PlanoContaService', 'LancamentoService', 'SaldoService', 'GroupService', 'ModalService', 'ListaService', 'LISTAS',
    function ($scope, $q, $filter, ContaService, PlanoContaService, LancamentoService, SaldoService, GroupService, ModalService, ListaService, LISTAS) {
            
        var init = function () {
            $scope.tipos = LISTAS.lancamento;
            $scope.anoAtual = new Date().getFullYear();
            $scope.mesAtual = new Date().getMonth();
            contasSaldos(); 
            saldosByTipo(); 
            saldosDespesa();
            configChartSaldos();
        }; 
        
        // Contas /////
        
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
        
        // Receita X Despesas /////
        
        var configChartReceitaDespesa = function(receitas, despesas) {        
            $scope.configChartReceitaDespesa = { 
                title: " ",
                options: { 
                    "chart": { "type": "column" }
                },
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
                    var saldos = formatTipo(data);
                    saldos = GroupService.saldo(saldos, ['id','ano','mes']);
                    SaldoService.saldoTipoLancamento($scope.tipos, saldos, getLastThreeMonths());
                    var receitas = getValuesTipoLancamento($scope.tipos[0]);
                    var despesas = getValuesTipoLancamento($scope.tipos[1]);  
                    configChartReceitaDespesa(receitas, despesas);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        var getValuesTipoLancamento = function(tipo) {
            var saldo = _.filter($scope.tipos, function(item) { return item.id === tipo.id; });
            var tipos = _.pluck(saldo, 'saldos');
            return _.values(tipos[0])
        }
        
        var formatTipo = function(saldos) {
            return _.map(saldos, function(saldo) {
                saldo.idTipo = saldo.id;
                saldo.mes = moment(saldo.data).format('MM');
                saldo.ano = moment(saldo.data).format('YYYY');
                return _.pick(saldo, ['id', 'mes', 'ano', 'valor']);
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
                xAxis: {
                    categories: ['1750', '1800', '1850', '1900', '1950', '1999', '2050'],
                    tickmarkPlacement: 'on',
                    title: {
                        enabled: false
                    }
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
        };
        
        // Saldos 90 dias /////
        
        var configChartSaldos = function(values) {        
            $scope.configChartSaldos = { 
                title: " ",
                options: { 
                    chart: { type: "area" },    
                },
                series: [{
                    name: 'Asia',
                    data: [502, 635, 809, 947, 1402, 3634, 5268]
                }, {
                    name: 'Africa',
                    data: [106, 107, 111, 133, 221, 767, 1766]
                }, {
                    name: 'Europe',
                    data: [163, 203, 276, 408, 547, 729, 628]
                }, {
                    name: 'America',
                    data: [18, 31, 54, 156, 339, 818, 1201]
                }, {
                    name: 'Oceania',
                    data: [2, 2, 2, 6, 13, 30, 46]
                }],
                size: {
                    height: 308
                }     
                //loading: true
            };
        };  
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };
        
        init();

    }]);
