'use strict';

app.controller('FluxoCaixaController', 
    ['$scope', 'LancamentoProgramadoService', 'ContaService', 'PlanoContaService', 'ContaCorrenteService', 'DatePickerService', 'ListaService', 'LISTAS', 'MESSAGES',
    function ($scope, LancamentoProgramadoService, ContaService, PlanoContaService, ContaCorrenteService, DatePickerService, ListaService, LISTAS, MESSAGES) {

        var init = function () {
            $scope.lancamentos = [];
            $scope.lancamentosLista = [];     
            $scope.tipos = LISTAS.lancamento;
            $scope.modelos = LISTAS.modeloLancamento; 
//            $scope.lancSearch.dataInicio = null;
//            $scope.lancSearch.dataFim = null;
            $scope.lancSearch = {};
            $scope.lancSearch.vencidos = false;
            getTitle();
            periodos(3);
            initTable();
        };  

        // ***** TABLE ***** //

        var initTable = function() {            
            $scope.colunas = [              
                {label: '', column: 'tipo', headerClass: 'no-sort', dataClass:'text-center col-tipo', filter: {name: 'tipoLancamento', args: ''}},               
                {label: 'Conta', column: 'conta.nome'}, 
                {label: 'Vencimento', column: 'dataVencimento', dataClass: 'text-center cel-data', filter: {name: 'date', args: 'dd/MM/yy'}},   
                {label: 'Favorecido', column: 'favorecido'},     
                {label: 'Plano Conta', column: 'planoConta'},   
                {label: 'Valor', column: 'valor', headerClass: 'no-sort', dataClass:'text-right', filter: {name: 'currency', args: ''}},  
                {label: 'Saldo', column: 'saldo', headerClass: 'no-sort', dataClass:'text-right', filter: {name: 'currency', args: ''}},
                {label: 'Núm. Doc', column: 'numero'},
                {label: 'Núm. Parcela', column: 'numeroParcela'}
            ]            
            $scope.linha = {
                conditionalClass: function(item) {
                    if(item.tipo.id === $scope.tipos[0].id) return 'text-primary';
                    else if(item.tipo.id === $scope.tipos[1].id) return 'text-danger';
                    else if(item.tipo.id === $scope.tipos[$scope.tipos.length - 1].id) return 'text-warning';
                }
            };
            $scope.events = { 
                table: function(lancamentos) {
                    calculateSaldo(lancamentos);
                    initChartFluxoCaixa(lancamentos);
                }
            }
        };

        $scope.filter = function(lista, search) { 
            var contaSelected = (search.conta ? JSON.parse(search.conta) : search.conta);
            lista = _.filter(lista, function(item) {
                return filterConta(item, contaSelected);
            });
//            if(!_.isEmpty(search)) { initChartFluxoCaixa(lista); }
            return lista;
        };

        $scope.filterByData = function(dataInicio, dataFim) {
            $scope.lancSearch.dataInicio = getDate(dataInicio);
            $scope.lancSearch.dataFim = getDate(dataFim);
            if(dataInicio && $scope.lancSearch.dataInicio.isValid() && dataFim && $scope.lancSearch.dataFim.isValid()) {
            $scope.lancSearch.periodo = $scope.periodos[0];
                todos($scope.lancSearch.dataInicio.format('YYYY-MM-DD'), $scope.lancSearch.dataFim.format('YYYY-MM-DD'), $scope.lancSearch.vencidos)
            }
        }; 
        
        var filterConta = function(item, contaSelected) {          
            if(!contaSelected) return true;
            return item.conta.idConta === contaSelected.idConta;
        }

        // ***** CONTROLLER ***** //

        var getTitle = function() {
//            $scope.title = MESSAGES.lancamento.title.LISTA; 
        };
        
        var toFixe = function(value, fixe) {
            return parseFloat(value.toFixed(fixe));
        }; 
        
        var periodos = function(index) {
            $scope.periodos = [];
            $scope.periodos.push({'id': -1, 'codigo': -1, 'descricao': "Selecione o Período"});
            for(var i=0; i<12; i++) {
                    var numeroPeriodo = i+1;
                var descricao = "Próximo " + numeroPeriodo + " mês";
                if(numeroPeriodo > 1) { descricao = "Próximos " + numeroPeriodo + " meses"; }
                $scope.periodos.push({'id': i, 'codigo': numeroPeriodo, 'descricao': descricao});
            }
            $scope.changePeriodo($scope.periodos[index], $scope.lancSearch.vencidos);
            contas();
        }
        
        var contas = function() {
            ContaService.getAll()
                .then(function (data) {
                    $scope.contas = data;
                    planoContas();
                })
                .catch(function (e) {
                    console.log(e);
                });
        };

        var planoContas = function() {
            PlanoContaService.getStructure()
                .then(function (data) {
                    $scope.planoContas = data;
                    PlanoContaService.estrutura($scope.planoContas);
                    $scope.planoContas = PlanoContaService.flatten($scope.planoContas);                 
                    todosByPeriodo($scope.lancSearch.periodo.codigo, $scope.lancSearch.vencidos);
                })
                .catch(function (e) {
                    console.log(e);
                });
        };

        var todosByPeriodo = function(periodos, vencidos) {
            if(periodos == -1) return;
            var dataInicio = moment().format('YYYY-MM-DD');
            var dataFim = moment().add(periodos, 'months').format('YYYY-MM-DD');
            todos(dataInicio, dataFim, vencidos);
        };

        var todos = function(dataInicio, dataFim, vencidos) {
            LancamentoProgramadoService.getAllAtivo(dataInicio, dataFim)
                .then(function (data) {
                    $scope.lancamentos = getLancamentos(angular.copy(data), dataInicio, dataFim, vencidos);
                    $scope.lancamentos.sort(compareData);
                    $scope.lancamentosLista = criarLancamentosLista(angular.copy($scope.lancamentos));
                    $scope.lancamentosLista = $scope.filter($scope.lancamentosLista, $scope.lancSearch);
//                    if(!_.isEmpty($scope.lancSearch)) { initChartFluxoCaixa($scope.lancamentosLista); }
                })
                .catch(function(e) {
                    console.log(e);
                });
        };       
        
        var getLancamentos = function(programados, dataInicio, dataFim, vencidos) {
            var resultado = [];
            _.map(programados, function(programado) {
                var lancamentos = LancamentoProgramadoService.lancamentoProgramado(angular.copy(programado), dataInicio, dataFim);
                _.map(lancamentos, function(lancamento) {
                    lancamento.dataVencimento = moment(lancamento.dataVencimento).format('YYYY-MM-DD');
                    resultado.push(lancamento);
                });
                if(vencidos) {
                    var lancamentosVencidos = LancamentoProgramadoService.lancamentoProgramadoVencido(angular.copy(programado), dataInicio);
                    _.map(lancamentosVencidos, function(lancamento) {
                        lancamento.dataVencimento = moment(lancamento.dataVencimento).format('YYYY-MM-DD');
                        resultado.push(lancamento);
                    });
                }
            });
            return resultado;
        };
        
        var compareData = function (a,b) {
            a = moment(a.dataVencimento);
            b = moment(b.dataVencimento);
            if(a.isBefore(b)) return -1;
            if(a.isAfter(b)) return 1;
            return 0;
        };

        var criarLancamentosLista = function(lancamentos) {
            return _.map(lancamentos, function(lancamento) {  
                
                //lancamento.tipo.modelo = $scope.modelos[2];
                lancamento.tipo.modelo = lancamento.modelo;
                
                if(lancamento.quantidadeParcela) { 
                    lancamento.tipo.modelo = $scope.modelos[4];
                    lancamento.valor = lancamento.valor / lancamento.quantidadeParcela;
                }     
                
                if(lancamento.planoConta && lancamento.planoConta.idPlanoConta) { 
                    var planoConta = ListaService.getPlanoContaValue($scope.planoContas, lancamento.planoConta.idPlanoConta); 
                    lancamento.planoConta = planoConta.descricao;                    
                } else {
                    lancamento.planoConta = null;
                }

                if(lancamento.rateios && lancamento.rateios.length) {
                    lancamento.planoConta = "Diversos";
                }
                
                if(lancamento.favorecido) { lancamento.favorecido = lancamento.favorecido.nome; }
                
                return _.pick(lancamento, 'idLancamentoProgramado', 'conta', 'tipo', 'dataVencimento', 'numero', 'planoConta', 'numeroParcela', 
                'favorecido', 'valor');
            })
        };

        var calculateSaldo = function(lancamentos) {
            var saldo = 0;
            return _.map(lancamentos, function(lancamento) {
                if(lancamento.tipo.id === 0) { 
                    saldo += lancamento.valor;
                } 
                else if(lancamento.tipo.id === 1) {
                    saldo -= lancamento.valor;
                }
                lancamento.saldo = saldo;
            });
        };
        
        $scope.changePeriodo = function(periodo, vencidos) {
            if(!periodo) return;
            $scope.lancSearch.periodo = periodo;
            var dataInicio = moment();
            var dataFim = moment().add(periodo.id + 1, 'months');
            setDataInicio(dataInicio, vencidos);
            setDataFim(dataFim, vencidos);
            todos(dataInicio.format('YYYY-MM-DD'), dataFim.format('YYYY-MM-DD'), vencidos);
        }
        
        $scope.pesquisar = function(periodo, vencidos) {
            todosByPeriodo(periodo.codigo, vencidos);
        };
        
        var setDataInicio = function(dataInicio) {
            $scope.lancSearch.dataInicio = getDate(dataInicio);
        };
        
        var setDataFim = function(dataFim) {
            $scope.lancSearch.dataFim = getDate(dataFim);                       
        };
        
        var getDate = function(date) {
            if(!angular.isDate(date)) {
                return moment(date, "DD/MM/YYYY"); 
            } 
            return date;
        };

        // ***** GRÁFICO ***** //
        
        var initChartFluxoCaixa = function(lancamentos) {
            setValoresChart(lancamentos);
        }
        
        var changeChartFluxoCaixa = function(lancamentos) {
            var categorias = getDataChartSaldo(lancamentos, 'dataVencimento', formatDate);
            var valores = getDataChartSaldo(lancamentos, 'saldo', formatSaldo);
            $scope.configChartFluxoCaixa.xAxis.categories = categorias;
            $scope.configChartFluxoCaixa.series[0].data = valores;
        }
        
        var setValoresChart = function(lancamentos) {
            ContaCorrenteService.getAll()
               .then(function(data) { 
                    var categorias = getDataChartSaldo(lancamentos, 'dataVencimento', formatDate);
                    var valores = getDataChartSaldo(lancamentos, 'saldo', formatSaldo);
                    var limiteContaCorrente = getSaldoContaCorrente(data);
                    if($scope.configChartFluxoCaixa) { changeChartFluxoCaixa(lancamentos); }
                    else { configChartFluxoCaixa(categorias, valores, limiteContaCorrente); }
                })
                .catch(function(e) {
                    modalMessage(e);
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
        
        var configChartFluxoCaixa = function(categorias, valores, limitConta) {        
            $scope.configChartFluxoCaixa = {
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
                legend: { enabled: false },
                credits: {
                    enabled: false
                }
            };
        };   
        
        var getDataChartSaldo = function(saldos, field, callback) {  
            var values = _.pluck(saldos, field);
            if(callback) { values = applyCallback(values, callback); }
            return _.values(values);
        };
        
        var applyCallback = function(values, callback) {
            var valuesFormat = [];
            values.map(function(value) {
                valuesFormat.push(callback(value));
            });
            return valuesFormat;
        };
        
        var formatDate = function(date) {
            var data = moment(date, 'YYYY-MM-DD');
            return data.format('DD/MM/YYYY');
        };
        
        var formatSaldo = function(saldo) {
            return toFixe(saldo, 2);
        };

        // ***** VALIDAR ***** //

        // ***** AJUSTAR ***** //

        // ***** MODAL ***** //

        init();

    }]);
