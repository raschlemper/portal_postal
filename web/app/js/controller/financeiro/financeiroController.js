'use strict';

app.controller('FinanceiroController', ['$scope', '$q', '$filter', 'ContaCorrenteService', 'ContaService', 'PlanoContaService', 'LancamentoService', 'LancamentoProgramadoService', 'SaldoService', 'FrequenciaLancamentoService', 'GroupService', 'ModalService', 'ListaService', 'LISTAS',
    function ($scope, $q, $filter, ContaCorrenteService, ContaService, PlanoContaService, LancamentoService, LancamentoProgramadoService, SaldoService, FrequenciaLancamentoService, GroupService, ModalService, ListaService, LISTAS) {
            
        var init = function () {
            $scope.sizeChart = 240;
            $scope.tipos = LISTAS.lancamento;
            $scope.situacoes = LISTAS.situacaoLancamentoProgramado;
            $scope.anoAtual = new Date().getFullYear();
            $scope.mesAtual = new Date().getMonth();
            $scope.saldoTotal = 0;
            contasSaldos(); 
            chartSaldoByTipo(); 
            chartSaldoDespesa();
            chartSaldo();
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
                $scope.saldoTotal += conta.saldo;
                return _.pick(conta, 'idConta', 'nome', 'saldo');
            })
        }
        
        // Receita X Despesas /////
        
        var configChartReceitaDespesa = function(receitas, despesas) {        
            $scope.configChartReceitaDespesa = { 
                title: " ",
                options: { 
                    chart: { "type": "column" }
                },
                xAxis: {
                    categories: getDescricaoMeses(),
                    title: {text: ' '} 
                },
                yAxis: {
                    min: 0,
                    title: {text: ' '}
                },
                series: [{name: $scope.tipos[0].descricao, data: receitas, color: '#90ed7d'},
                         {name: $scope.tipos[1].descricao, data: despesas, color: '#f45b5b'}],
                size: {
                    height: $scope.sizeChart
                },
                credits: {
                    enabled: false
                }
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
                
        var chartSaldoByTipo = function() {
            var dataInicio = moment().endOf("month").subtract(3, 'months').format('YYYY-MM-DD');
            var dataFim = moment().endOf("month").format('YYYY-MM-DD');
            LancamentoService.getSaldoTipo(dataInicio, dataFim)
                .then(function(data) {  
                    var saldos = formatTipo(data);
                    saldos = GroupService.saldo(saldos, ['id','ano','mes']);
                    SaldoService.saldoTipoLancamento($scope.tipos, saldos, getLastThreeMonths());
                    var receitas = getDataChartTipoLancamento($scope.tipos[0]);
                    var despesas = getDataChartTipoLancamento($scope.tipos[1]);  
                    configChartReceitaDespesa(receitas, despesas);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        var formatTipo = function(saldos) {
            return _.map(saldos, function(saldo) {
                saldo.idTipo = saldo.id;
                saldo.mes = moment(saldo.data).format('MM');
                saldo.ano = moment(saldo.data).format('YYYY');
                return _.pick(saldo, ['id', 'mes', 'ano', 'valor']);
            });            
        };
        
        var getDataChartTipoLancamento = function(tipo) {
            //FIXME achar outra maneira de fazer o filtro, esta retornando um array de array
            var saldo = _.filter($scope.tipos, function(item) { return item.id === tipo.id; });
            var tipos = _.pluck(saldo, 'saldos');
            return _.values(tipos[0])
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
                    height: $scope.sizeChart
                },
                credits: {
                    enabled: false
                }
            };
        };  
        
        var chartSaldoDespesa = function() {
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
        
        var configChartSaldo = function(categorias, valores, limitConta) {        
            $scope.configChartSaldos = { 
                title: " ",     
                options: { 
                    chart: { type: "area" }
                },
                xAxis: {
                    categories: categorias,
                    title: { enabled: false }
                },
                yAxis: {
                    title: { text: ' ' },
                    min: limitConta,
                    plotLines: [{
                        label: {
                            text: 'Limite Conta Corrente',
                            style: {
                                color: '#FF6161'
                            }
                        },
                        dashStyle: 'shortdot',
                        color: '#FE2E2E',
                        value: limitConta,
                        width: 1   
                    }]
                },
                series: [{
                    name: 'Saldos',    
                    data: valores
                }],
                size: {
                    height: $scope.sizeChart
                },           
                legend: { enabled: false },
                credits: {
                    enabled: false
                }
            };
        };   
        
        var chartSaldo = function() {
            var dataInicio = moment().endOf("month").format('YYYY-MM-DD');
            var dataFim = moment().endOf("month").add(3, 'months').format('YYYY-MM-DD');
            $q.all([LancamentoProgramadoService.getByDataVencimento(dataInicio, dataFim),
                    LancamentoService.getSaldo(dataInicio, dataFim),
                    ContaCorrenteService.getAll()])
               .then(function(values) { 
                    var lancamentosSaldos = getLancamentos(values[0], dataInicio, dataFim);
                    var saldos = formatSaldo(values[1]);
                    var limiteContaCorrente = getSaldoContaCorrente(values[2]);
                    var categorias = getDataChartSaldo(saldos, 'data');
                    var valores = getDataChartSaldo(saldos, 'valor');
                    configChartSaldo(categorias, valores, limiteContaCorrente);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        var getLancamentos = function(programados, dataInicio, dataFim) {  
            _.map(programados, function(programado) {
                getSaldo(programado, dataInicio, dataFim);
            })
        };
        
        var getSaldo = function(programado, dataInicio, dataFim) {
            if(programado.situacao === $scope.situacoes[2]) return;
            if(programado.numeroParcela > programado.quantidadeParcela) return;
            var dataVencimento = moment(programado.dataVencimento);
            if(dataVencimento.isBefore(dataInicio) || dataVencimento.isAfter(dataFim)) return;
                console.log({id: programado.idLancamentoProgramado,
                             data: dataVencimento,
                             valor: programado.valor});            
            getSaldo(FrequenciaLancamentoService.execute(programado), dataInicio, dataFim);
            
        }
        
        var formatSaldo = function(saldos) {
            return _.map(saldos, function(saldo) {
                saldo.data = $filter('date')(saldo.data, 'dd/MM');
//                saldo.valor = $filter('currency')(saldo.valor, '')
                return _.pick(saldo, ['data', 'valor']);
            });
            
        };
        
        var getSaldoContaCorrente = function(contasCorrentes) {
            var limite = 0;
            _.map(contasCorrentes, function(contaCorrente) {
                if(contaCorrente && contaCorrente.limite) {
                    limite -= contaCorrente.limite;
                }
            });
            return limite;
        }
        
        var getDataChartSaldo = function(saldos, field) {  
            var values = _.pluck(saldos, field);
            return _.values(values);
        };
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };
        
        init();

    }]);
