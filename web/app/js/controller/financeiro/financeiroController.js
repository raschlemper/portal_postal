'use strict';

app.controller('FinanceiroController', ['$scope', '$q', '$filter', '$state', 'ContaCorrenteService', 'ContaService', 'PlanoContaService', 'LancamentoService', 'LancamentoProgramadoService', 'SaldoService', 'PeriodoService', 'GroupService', 'ModalService', 'ListaService', 'LISTAS',
    function ($scope, $q, $filter, $state, ContaCorrenteService, ContaService, PlanoContaService, LancamentoService, LancamentoProgramadoService, SaldoService, PeriodoService, GroupService, ModalService, ListaService, LISTAS) {
            
        var init = function () {
            $scope.sizeChart = 240;
            $scope.tipos = LISTAS.lancamento;
            $scope.situacoes = LISTAS.situacaoLancamentoProgramado;
            $scope.meses = LISTAS.meses;
            $scope.anoAtual = new Date().getFullYear();
            $scope.mesAtual = new Date().getMonth();
            $scope.saldoTotal = 0;
            $scope.configChartReceitaDespesa = [];
            $scope.configChartDespesa = [];
            $scope.configChartSaldos = [];
            initChart($state.params.tipo);
        }; 
        
        var getPeriodo = function(mes, ano) {
            return PeriodoService.periodoOneYear(mes, ano);
        }
        
        var initChart = function(tipo) {
            if(!tipo) {
                initContas();
                initChartReceitaDespesa()
                initChartDespesa();
                initChartSaldo();  
                initSaldoProgramado();
            } else {
                if(tipo === 'receitadespesa') { 
                    $scope.sizeChart = 400;
                    $scope.showChartReceitaDespesa = true;
                    var dataInicio = moment($scope.anoAtual + '-01-01').format('YYYY-MM-DD');
                    var dataFim = moment($scope.anoAtual + '-12-31').format('YYYY-MM-DD');
                    chartSaldoByTipo(LISTAS.meses, dataInicio, dataFim);     
                }
                if(tipo === 'despesa') { 
                    $scope.sizeChart = 200;
                    $scope.showChartDespesa = true;
                    $scope.mesesDespesa = getPeriodo($scope.meses[0], $scope.anoAtual);
                    _.map($scope.mesesDespesa, function(mes) {
                        chartSaldoDespesa(mes);
                    })
                }
            }
        }
        
        // Contas /////
        
        var initContas = function() {
            contasSaldos();            
        }
        
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
        
        var initChartReceitaDespesa = function() {
            var dataInicio = moment().endOf("month").subtract(3, 'months').format('YYYY-MM-DD');
            var dataFim = moment().endOf("month").format('YYYY-MM-DD');
            chartSaldoByTipo(getLastThreeMonths(), dataInicio, dataFim);             
        }
        
        var configChartReceitaDespesa = function(meses, receitas, despesas) {        
            $scope.configChartReceitaDespesa.push({ 
                title: " ",
                options: { 
                    chart: { "type": "column" }
                },
                xAxis: {
                    categories: getDescricaoMeses(meses),
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
            });
        };
        
        var getDescricaoMeses = function(meses) {
            return _.map(meses, function(mes) {
                return mes.descricao;
            })
        }
        
        var getLastThreeMonths = function() {            
            return _.filter(LISTAS.meses, function(mes) {
                return ($scope.mesAtual === mes.id || $scope.mesAtual - 1 === mes.id || $scope.mesAtual - 2 === mes.id);
            })
        };
                
        var chartSaldoByTipo = function(meses, dataInicio, dataFim) {
            LancamentoService.getSaldoTipo(dataInicio, dataFim)
                .then(function(data) {  
                    var saldos = formatTipo(data);
                    saldos = GroupService.saldo(saldos, ['id','ano','mes']);
                    SaldoService.saldoTipoLancamento($scope.tipos, saldos, meses);
                    var receitas = getDataChartTipoLancamento($scope.tipos[0]);
                    var despesas = getDataChartTipoLancamento($scope.tipos[1]);  
                    configChartReceitaDespesa(meses, receitas, despesas);
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
        
        var initChartDespesa = function() {
            var dataMonth = moment().add(7, 'months');     
            var dataYear = dataMonth.subtract(1, 'year');         
            var mesAtual = ListaService.getValue($scope.meses, dataMonth.month());
            $scope.mesesDespesa = getPeriodo(mesAtual, dataYear.year());  
            $scope.mesDespesaSelected = $scope.mesesDespesa[5];
            $scope.changeMesDespesas($scope.mesDespesaSelected);
        };
        
        $scope.changeMesDespesas = function(mes) {    
            $scope.configChartDespesa[mes.order] = {}
            chartSaldoDespesa(mes);
        }
        
        var configChartDespesa = function(index, values) {        
            $scope.configChartDespesa[index] = { 
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
        
        var chartSaldoDespesa = function(mes) {
            var data = moment().month(mes.id).year(mes.ano);
            var dataInicio = data.startOf("month").format('YYYY-MM-DD');
            var dataFim = data.endOf("month").format('YYYY-MM-DD');
//            var mesAtual = ListaService.getValue(LISTAS.meses, moment().format('MM')-1);
            $q.all([PlanoContaService.getStructureByTipo($scope.tipos[1].id), 
                    LancamentoService.getSaldoPlanoConta(dataInicio, dataFim)])
               .then(function(values) {  
                    var estruturas = values[0];
                    var saldos = formatPlanoConta(values[1]);
                    PlanoContaService.estrutura(estruturas);
                    var estruturas = PlanoContaService.flatten(estruturas);
                    SaldoService.saldoPlanoContaByMes(estruturas, saldos, mes);
                    SaldoService.saldoPlanoContaGrupo(estruturas);
                    estruturas = _.filter(estruturas, function(estrutura) {
                        return estrutura.nivel === 2;
                    });
                    configChartDespesa(mes.order, getDataChartDespesa(estruturas, mes));
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
                return {name: estrutura.descricao, y: estrutura.saldos[mes.order]};
            });            
        };
        
        // Saldos 90 dias /////
        
        var initChartSaldo = function() {
            chartSaldo();            
        }
        
        var configChartSaldo = function(categorias, valores, limitConta) {        
            $scope.configChartSaldos.push({ 
                title: " ",     
                options: { 
                    chart: { type: "area" },           
                    plotOptions: {
                        area: {
                            marker: {
                                enabled: false,
                                symbol: 'circle',
                                radius: 2,
                                states: {
                                    hover: {
                                        enabled: true
                                    }
                                }
                            }
                        }
                    },
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
            });
        };   
        
        var chartSaldo = function() {
            var dataInicio = moment().format('YYYY-MM-DD');
            var dataFim = moment().add(3, 'months').format('YYYY-MM-DD');
            $q.all([LancamentoProgramadoService.getAllAtivo(),
                    LancamentoService.getSaldo(dataInicio, dataFim),
                    ContaCorrenteService.getAll()])
               .then(function(values) { 
                    var saldos = getLancamentos(values[1], values[0], dataInicio, dataFim);
                    addDataSemSaldo(moment(dataInicio), moment(dataInicio), moment(dataFim), saldos);
                    saldos = ajusteSaldos(saldos);
                    var limiteContaCorrente = getSaldoContaCorrente(values[2]);
                    var categorias = getDataChartSaldo(saldos, 'data');
                    var valores = getDataChartSaldo(saldos, 'valor');
                    valores = addSaldoContas($scope.saldoTotal, valores);
                    configChartSaldo(categorias, valores, limiteContaCorrente);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        var getLancamentos = function(saldos, programados, dataInicio, dataFim) { 
            _.map(saldos, function(saldo) {
                saldo.data = moment(saldo.data).format('YYYY-MM-DD');
            });
            _.map(programados, function(programado) {
                var lancamentos = LancamentoProgramadoService.lancamentoProgramado(programado, dataInicio, dataFim);
                getSaldo(saldos, lancamentos);
            });
            return saldos;
        };
        
        var getSaldo = function(saldos, lancamentos) {
            _.map(lancamentos, function(lancamento) {        
                var dataVencimento = moment(lancamento.dataVencimento);            
                saldos.push({id: lancamento.idLancamentoProgramado,
                             data: dataVencimento.format('YYYY-MM-DD'),
                             valor: lancamento.valor}); 
            });
        }
        
        var ajusteSaldos = function(saldos) {
            var saldoGroup = [];
            saldos.sort(compareData);
            saldos = _.groupBy(saldos, 'data');
            _.map(saldos, function(saldo, data) {
                var total = 0;
                _.map(saldo, function(item) {
                    total += item.valor
                });
                saldoGroup.push(getDataSaldo(data, total));
            })
            return formatSaldo(saldoGroup);            
        };
        
        var addDataSemSaldo = function(data, dataInicio, dataFim, saldos) {
            if(data.isBefore(dataInicio) || data.isAfter(dataFim)) return;
            saldos.push(getDataSaldo(data.format('YYYY-MM-DD'), 0));
            addDataSemSaldo(data.add(1,'days'), dataInicio, dataFim, saldos)
        }
        
        var getDataSaldo = function(data, total) {
            return {data: data, valor: total};
        }
        
        var compareData = function (a,b) {
            a = moment(a.data);
            b = moment(b.data);
            if(a.isBefore(b)) return -1;
            if(a.isAfter(b)) return 1;
            return 0;
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
        
        var addSaldoContas = function(saldoContas, saldos) {
            return _.map(saldos, function(saldo) {
                return saldo + saldoContas;
            })
        }
        // Saldos Programados /////
        
        var initSaldoProgramado = function() {
            LancamentoProgramadoService.getAllAtivo()
               .then(function(data) {
                    var dataAtual = moment().format('YYYY-MM-DD');
                    var dataUmMes = moment().add(1, "M").format('YYYY-MM-DD');
                    var dataDoisMes = moment().add(2, "M").format('YYYY-MM-DD');
                    var dataTresMes = moment().add(3, "M").format('YYYY-MM-DD');
                    $scope.lancamentosVencido = getValoresReceitaDespesa([]);
                    $scope.lancamentosHoje = getValoresReceitaDespesa([]);
                    $scope.lancamentosUmMes = getValoresReceitaDespesa([]);
                    $scope.lancamentosDoisMeses = getValoresReceitaDespesa([]);
                    $scope.lancamentosTresMeses = getValoresReceitaDespesa([]);
                    _.map(data, function(programado) {
                        $scope.lancamentosVencido = getValoresReceitaDespesa(LancamentoProgramadoService.lancamentoProgramadoVencido(angular.copy(programado), dataAtual));
                        $scope.lancamentosHoje = getValoresReceitaDespesa(LancamentoProgramadoService.lancamentoProgramado(angular.copy(programado), dataAtual, dataAtual));
                        $scope.lancamentosUmMes = getValoresReceitaDespesa(LancamentoProgramadoService.lancamentoProgramado(angular.copy(programado), dataAtual, dataUmMes));
                        $scope.lancamentosDoisMeses = getValoresReceitaDespesa(LancamentoProgramadoService.lancamentoProgramado(angular.copy(programado), dataUmMes, dataDoisMes));
                        $scope.lancamentosTresMeses = getValoresReceitaDespesa(LancamentoProgramadoService.lancamentoProgramado(angular.copy(programado), dataDoisMes, dataTresMes));
                    });
                })
                .catch(function(e) {
                    modalMessage(e);
                });            
        }
        
        var getValoresReceitaDespesa = function(lancamentos) {
            var receita = 0;
            var despesa = 0;
            _.map(lancamentos, function(lancamento) {
               if(lancamento.tipo.id === $scope.tipos[0].id) { receita += lancamento.valor; }
               if(lancamento.tipo.id === $scope.tipos[1].id) { despesa -= lancamento.valor; }
            });
            return {receita: receita, despesa: despesa};
        }
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };
        
        init();

    }]);
