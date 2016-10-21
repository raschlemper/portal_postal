'use strict';

app.controller('FluxoCaixaController', 
    ['$scope', 'LancamentoProgramadoService', 'ContaService', 'PlanoContaService', 'DatePickerService', 'ListaService', 'LISTAS', 'MESSAGES',
    function ($scope, LancamentoProgramadoService, ContaService, PlanoContaService, DatePickerService, ListaService, LISTAS, MESSAGES) {

        var init = function () {
            $scope.lancamentos = [];
            $scope.lancamentosLista = [];     
            $scope.tipos = LISTAS.lancamento;
            $scope.modelos = LISTAS.modeloLancamento;
            $scope.datepickerDataInicio = angular.copy(DatePickerService.default);      
            $scope.datepickerDataFim = angular.copy(DatePickerService.default);   
            $scope.vencidos = false;
            $scope.lancSearch = {};
            getTitle();
            periodos();
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
            lista = _.filter(lista, function(item) {
                return filterByData(item, search);
            });    
            lista = filterConta(lista, search);
            if(!_.isEmpty(search)) { initChartFluxoCaixa(lista); }
            return lista;
        };

        var filterByData = function(item, search) {
            var dataInicio = moment(search.dataInicio);
            var dataFim = moment(search.dataFim);
            if((!search.dataInicio || !dataInicio.isValid()) || (!search.dataFim || !dataFim.isValid())) return true;
            var data = moment(item.dataVencimento);
            return (data.isBefore(search.dataFim) && data.isAfter(search.dataInicio) 
                    || (data.isSame(search.dataInicio) || data.isSame(search.dataFim)));
        }; 
        
        var filterConta = function(lista, search) {          
            if(!search.conta) return lista;
            var conta = JSON.parse(search.conta); 
            lista = _.filter(lista, function(item) {
                return item.conta.idConta === conta.idConta;
            });
            return lista;
        }

        // ***** CONTROLLER ***** //

        var getTitle = function() {
//            $scope.title = MESSAGES.lancamento.title.LISTA; 
        };
        
        var periodos = function() {
            $scope.periodos = [];
            for(var i=0; i<12; i++) {
                var numeroPeriodo = i+1;
                var descricao = "Próximo " + numeroPeriodo + " mês";
                if(numeroPeriodo > 1) { descricao = "Próximos " + numeroPeriodo + " meses"; }
                $scope.periodos.push({'id': i, 'codigo': numeroPeriodo, 'descricao': descricao});
            }
            $scope.periodo = $scope.periodos[2]
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
                    todos($scope.periodo.codigo, $scope.vencidos);
                })
                .catch(function (e) {
                    console.log(e);
                });
        };

        var todos = function(periodos, vencidos) {
            var dataInicio = moment().format('YYYY-MM-DD');
            var dataFim = moment().add(periodos, 'months').format('YYYY-MM-DD');
            LancamentoProgramadoService.getAllAtivo()
                .then(function (data) {
                    $scope.lancamentos = getLancamentos(angular.copy(data), dataInicio, dataFim, vencidos);
                    $scope.lancamentos.sort(compareData);
                    $scope.lancamentosLista = criarLancamentosLista(angular.copy($scope.lancamentos));
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
                
                lancamento.tipo.modelo = $scope.modelos[2];
                
                if(lancamento.quantidadeParcela) { 
                    lancamento.tipo.modelo = $scope.modelos[4];
                    lancamento.valor = lancamento.valor / lancamento.quantidadeParcela;
                }                
                
//                if(lancamento.tipo.modelo.id === $scope.modelos[3].id) { lancamento.tipoLancamento = $scope.modelos[3].codigo; }
//                else if(lancamento.tipo.id === $scope.tipos[0].id) { lancamento.tipoLancamento = lancamento.tipo.codigo; }
//                else if(lancamento.tipo.id === $scope.tipos[1].id) { lancamento.tipoLancamento = lancamento.tipo.codigo; }
                
                if(lancamento.planoConta && lancamento.planoConta.idPlanoConta) { 
                    var planoConta = ListaService.getPlanoContaValue($scope.planoContas, lancamento.planoConta.idPlanoConta); 
                    lancamento.planoConta = planoConta.descricao;                    
                } else {
                    lancamento.planoConta = null;
                }
                
//                lancamento.tipo.modelo = $scope.modelos;
//                if(lancamento.lancamentos && lancamento.lancamentos.length) {
//                    lancamento.lancamentos = lancamento.lancamentos.length;
//                }

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
            todos(periodo.codigo, vencidos);
        }
        
        $scope.pesquisar = function(periodo, vencidos) {
            todos(periodo.codigo, vencidos);
        };

        // ***** GRÁFICO ***** //
        
        var initChartFluxoCaixa = function(lancamentos) {
            var categorias = getDataChartSaldo(lancamentos, 'dataVencimento');
            var valores = getDataChartSaldo(lancamentos, 'saldo');
            if($scope.configChartFluxoCaixa) { changeChartFluxoCaixa(lancamentos); }
            else { configChartFluxoCaixa(categorias, valores); }
        }
        
        var changeChartFluxoCaixa = function(lancamentos) {
            var categorias = getDataChartSaldo(lancamentos, 'dataVencimento');
            var valores = getDataChartSaldo(lancamentos, 'saldo');
            $scope.configChartFluxoCaixa.xAxis.categories = categorias;
            $scope.configChartFluxoCaixa.series[0].data = valores;
        }
        
        var configChartFluxoCaixa = function(categorias, valores) {        
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
                    title: { text: ' ' }
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
        
        var getDataChartSaldo = function(saldos, field) {  
            var values = _.pluck(saldos, field);
            return _.values(values);
        };

        // ***** VALIDAR ***** //

        // ***** AJUSTAR ***** //

        // ***** MODAL ***** //

        init();

    }]);
