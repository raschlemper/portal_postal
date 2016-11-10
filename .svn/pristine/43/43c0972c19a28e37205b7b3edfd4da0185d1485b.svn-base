'use strict';

app.controller('LancamentoProgramadoController', 
    ['$scope', '$filter', '$state', 'LancamentoProgramadoService', 'ContaService', 'PlanoContaService', 'CentroCustoService', 'LancamentoProgramadoTransferenciaService',
     'FrequenciaLancamentoService', 'ConfiguracaoService', 'ReportService', 'ModalService', 'DatePickerService', 'PeriodoService', 'LancamentoProgramadoHandler', 
     'LancamentoProgramadoParcelaHandler', 'LancamentoProgramadoRateioHandler', 'LancamentoProgramadoTransferenciaHandler', 'LancamentoHandler', 'FinanceiroValidation', 
     'ListaService', 'LISTAS', 'MESSAGES',
    function ($scope, $filter, $state, LancamentoProgramadoService, ContaService, PlanoContaService, CentroCustoService, LancamentoProgramadoTransferenciaService,
        FrequenciaLancamentoService, ConfiguracaoService, ReportService, ModalService, DatePickerService, PeriodoService, LancamentoProgramadoHandler, LancamentoProgramadoParcelaHandler, 
        LancamentoProgramadoRateioHandler, LancamentoProgramadoTransferenciaHandler, LancamentoHandler, FinanceiroValidation, ListaService, LISTAS, MESSAGES) {

        var init = function () {
            $scope.lancamentoProgramados = [];
            $scope.lancamentoProgramadosLista = []; 
            $scope.lancamentoProgramadosListaTable = [];
            $scope.tipos = LISTAS.lancamento;
            $scope.modelos = LISTAS.modeloLancamento;
            $scope.situacoes = LISTAS.situacaoLancamentoProgramado;
            $scope.periodos = LancamentoProgramadoService.periodos;
            $scope.tiposLancamento = [];
//            $scope.datepickerDataInicio = angular.copy(DatePickerService.default);      
//            $scope.datepickerDataFim = angular.copy(DatePickerService.default);  
            $scope.outOfRangePeriodo = false;
            $scope.lancSearch = {};
            getTitle();
            createListTipoLancamento();
            contas();
            initTable(); 
        };  
        
        // ***** TABLE ***** //
        
        var initTable = function() {            
            $scope.colunas = [              
                {label: '', column: 'tipo', headerClass: 'no-sort', dataClass:'text-center col-tipo', filter: {name: 'tipoLancamento', args: ''}},       
                {label: 'Conta', column: 'conta.nome', showColumn: true, selected: false},                      
                {label: 'Vencimento', column: 'dataVencimento', filter: {name: 'date', args: 'dd/MM/yyyy'}},                
                {label: 'Número', column: 'numero'},         
                {label: 'Plano Conta', column: 'planoConta.descricao'},   
                {label: 'Centro Custo', column: 'centroCusto.descricao', showColumn: true, selected: false},  
                {label: 'Usuário', column: 'usuario', showColumn: true, selected: false},           
                {label: 'Favorecido', column: 'favorecido'},  
                {label: 'Valor', column: 'valor', headerClass: 'no-sort', filter: {name: 'currency', args: ''}},              
                {label: 'Situação', column: 'situacao.descricao'},  
                {label: 'Frequência', column: 'frequencia.descricao'}
            ]            
            $scope.linha = {
                conditionalClass: function(item) {
                    var css = null;
                    if(item.tipo.id === $scope.tipos[0].id) { css = 'text-primary'; }
                    else if(item.tipo.id === $scope.tipos[1].id) { css = 'text-danger'; }
                    else if(item.tipo.id === $scope.tipos[$scope.tipos.length - 1].id) { css = 'text-warning'; }
                    if(moment(item.dataVencimento).isBefore(getDataHoje())) { css += ' line-vencida'; }
                    return css;
                },
                events: { 
                    edit: function(lancamentoProgramado) {
                        $scope.editar($scope.lancSearch.conta, lancamentoProgramado);
                    },
                    remove: function(lancamentoProgramado) {
                        $scope.excluir($scope.lancSearch.conta, lancamentoProgramado);
                    },
                    view: function(lancamentoProgramado) {
                        $scope.visualizar($scope.lancSearch.conta, lancamentoProgramado);
                    }
                }
            };
            $scope.events = { 
                table: function(lancamentos) {
                    $scope.lancamentoProgramadosListaTable = angular.copy(lancamentos);
                }
            }
        };
        
        $scope.filter = function(lista, search) {
            var tipoSelected = (search.tipo ? JSON.parse(search.tipo) : search.tipo);
            var planoContaSelected = (search.planoConta ? PlanoContaService.getChildrenListPlanoConta(null, search.planoConta) : search.planoConta);
            var centroCustoSelected = (search.centroCusto ? CentroCustoService.getChildrenListCentroCusto(null, search.centroCusto) : search.centroCusto);
            lista = _.filter(lista, function(item) {
                return filterTipo(item, tipoSelected) && 
                       filterByPlanoConta(item, planoContaSelected) &&
                       filterByCentroCusto(item, centroCustoSelected);
            });  
            return lista;
        };
        
        var filterTipo = function(item, tipoSelected) {          
            if(!tipoSelected) return true;
            return item.tipoLancamento === tipoSelected.codigo;
        };

        var filterByPlanoConta = function(item, planoContaSelected) {          
            if(!planoContaSelected) return true;
            var selected = false;
            planoContaSelected.map(function(conta) {                
                if(item.planoConta && item.planoConta === conta.descricao) { selected = true; }
            })
            return selected;
        };

        var filterByCentroCusto = function(item, centroCustoSelected) {          
            if(!centroCustoSelected) return true;
            var selected = false;
            centroCustoSelected.map(function(custo) {                
                if(item.centroCusto && item.centroCusto === custo.descricao) { selected = true; }
            })
            return selected;
        };
        
        // ***** CONTROLLER ***** //
        
        var getTitle = function() {
            $scope.title = MESSAGES.lancamento.programar.title.LISTA; 
        };
        
        var createListTipoLancamento = function() {
            $scope.tiposLancamento.push( { codigo: $scope.tipos[0].codigo, descricao: 'Contas a Receber' });
            $scope.tiposLancamento.push( { codigo: $scope.tipos[1].codigo, descricao: 'Contas a Pagar' });
            $scope.tiposLancamento.push( { codigo: $scope.modelos[3].codigo, descricao: 'Transf. Programada' });
        };
        
        var getDataHoje = function() {
            return moment().startOf('day');
        };
        
        var contas = function() {
            ContaService.getAll()
                .then(function (data) {
                    $scope.contas = data;
                    if($state.params.id) { $scope.editar($scope.lancSearch.conta, $state.params.id); }
                    planoContas();
                })
                .catch(function (e) {
                    console.log(e);
                });
        };

        $scope.changeConta = function(conta) {
            if(!validaPeriodo($scope.lancSearch.dataInicio, $scope.lancSearch.dataFim)) return;
            $scope.lancSearch.conta = conta;
            if(conta) { todosByConta(conta); }
            else { todos(); }
        };
        
        var planoContas = function() {
            PlanoContaService.getStructure()
                .then(function (data) {
                    $scope.planoContas = data;
                    PlanoContaService.estrutura($scope.planoContas);
                    $scope.planoContas = PlanoContaService.flatten($scope.planoContas);                 
                    centroCustos();
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        var centroCustos = function() {
            CentroCustoService.getStructure()
                .then(function (data) {
                    $scope.centroCustos = data;
                    CentroCustoService.estrutura($scope.centroCustos);
                    $scope.centroCustos = CentroCustoService.flatten($scope.centroCustos);  
                    configuracao();  
                })
                .catch(function (e) {
                    console.log(e);
                });
        };

        var configuracao = function() {            
            ConfiguracaoService.get()
                .then(function(data) { 
                    var periodo = data.periodoLancamentoProgramado || $scope.periodos[1];
                    $scope.changePeriodo($scope.lancSearch.conta, periodo);      
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        var todos = function() {
            var dataInicio;
            var dataFim;
            if($scope.lancSearch.dataInicio) { 
                setDataInicio($scope.lancSearch.dataInicio); 
                dataInicio = $scope.lancSearch.dataInicio.format('YYYY-MM-DD');
            }
            if($scope.lancSearch.dataFim) { 
                setDataFim($scope.lancSearch.dataFim); 
                dataFim = $scope.lancSearch.dataFim.format('YYYY-MM-DD');
            }
            LancamentoProgramadoService.getAll(dataInicio, dataFim)
                .then(function (data) {
                    $scope.lancamentoProgramados = data;
                    $scope.lancamentoProgramadosLista = criarLancamentoProgramadosLista($scope.lancamentoProgramados);
                    ListaService.getTableValue($scope.colunas, 'conta.nome').selected = true;
                })
                .catch(function(e) {
                    console.log(e);
                });
        };

        var todosByConta = function(conta) {
            var dataInicio;
            var dataFim;
            if($scope.lancSearch.dataInicio) { 
                setDataInicio($scope.lancSearch.dataInicio); 
                dataInicio = $scope.lancSearch.dataInicio.format('YYYY-MM-DD');
            }
            if($scope.lancSearch.dataFim) { 
                setDataFim($scope.lancSearch.dataFim); 
                dataFim = $scope.lancSearch.dataFim.format('YYYY-MM-DD');
            }
            if(!conta || !conta.idConta) { todos(); }
            else {  
                ContaService.getLancamentoProgramado(conta.idConta, dataInicio, dataFim)
                    .then(function (data) {
                        $scope.lancamentoProgramados = data.lancamentosProgramados;
                        $scope.lancamentoProgramadosLista = criarLancamentoProgramadosLista($scope.lancamentoProgramados);
                    ListaService.getTableValue($scope.colunas, 'conta.nome').selected = false;
                    })
                    .catch(function(e) {
                        console.log(e);
                    });
            }
        };

        $scope.changePeriodo = function(conta, periodo) {
            if(!periodo) return;
            $scope.lancSearch.periodo = periodo;
            var datas = PeriodoService.dateByPeriodo(periodo); 
            setDataInicio(datas.dataInicio);
            setDataFim(datas.dataFim);
            todosByConta(conta);
        };
        
        $scope.changeDataInicio = function(conta, dataInicio) {
            changeData(conta, dataInicio, $scope.lancSearch.dataFim);
        };
        
        $scope.changeDataFim = function(conta, dataFim) {
            changeData(conta, $scope.lancSearch.dataInicio, dataFim);
        };
        
        var changeData = function(conta, dataInicio, dataFim) {
            $scope.lancSearch.periodo = null;
            $scope.lancSearch.dataInicio = getDate(dataInicio);
            $scope.lancSearch.dataFim = getDate(dataFim);
            if(!validaPeriodo($scope.lancSearch.dataInicio, $scope.lancSearch.dataFim)) { 
                $scope.outOfRangePeriodo = true;
            } else {
                $scope.outOfRangePeriodo = false;
                todosByConta(conta);                        
            }
        };
        
        var setDataInicio = function(dataInicio) {
            if(dataInicio) { $scope.lancSearch.dataInicio = getDate(dataInicio); }
            else { $scope.lancSearch.dataInicio = null; }
//            angular.element("#dataInicio").val(dataInicio.format('DD/MM/YYYY'));
        };
        
        var setDataFim = function(dataFim) {
            if(dataFim) { $scope.lancSearch.dataFim = getDate(dataFim); }
            else { $scope.lancSearch.dataFim = null; }
//            angular.element("#dataFim").val(dataFim.format('DD/MM/YYYY'));                        
        };
        
        var getDate = function(date) {
            if(!date) return null;
            if(!angular.isDate(date)) {
                return moment(date, "DD/MM/YYYY"); 
            } 
            if(moment.isDate(date)) {
                return date
            }
            return null;
        };
        
        var criarLancamentoProgramadosLista = function(lancamentosProgramados) {
            return _.map(lancamentosProgramados, function(lancamentoProgramado) {  
                
                lancamentoProgramado.tipo.modelo = lancamentoProgramado.modelo;
                
                var complementoDescricaoFrequencia = '';
                if(lancamentoProgramado.quantidadeParcela) { 
                    var saldo = getSaldoLancamentoProgramadoParcelado(lancamentoProgramado);
                    complementoDescricaoFrequencia = ' - ' + lancamentoProgramado.quantidadeParcelaAbertas + 'x (' + $filter('currency')(saldo, 'R$ ') + ')';
//                    lancamentoProgramado.tipo.modelo = $scope.modelos[4];
                    lancamentoProgramado.valor = lancamentoProgramado.valor / lancamentoProgramado.quantidadeParcela;
                }                
                
                if(lancamentoProgramado.tipo.modelo.id === $scope.modelos[3].id) { lancamentoProgramado.tipoLancamento = $scope.modelos[3].codigo; }
                else if(lancamentoProgramado.tipo.id === $scope.tipos[0].id) { lancamentoProgramado.tipoLancamento = lancamentoProgramado.tipo.codigo; }
                else if(lancamentoProgramado.tipo.id === $scope.tipos[1].id) { lancamentoProgramado.tipoLancamento = lancamentoProgramado.tipo.codigo; }
                
                if(lancamentoProgramado.planoConta && lancamentoProgramado.planoConta.idPlanoConta) { 
                    var planoConta = ListaService.getPlanoContaValue($scope.planoContas, lancamentoProgramado.planoConta.idPlanoConta); 
                    lancamentoProgramado.planoConta = { descricao: planoConta.descricao };                    
                } else {
                    lancamentoProgramado.planoConta = { descricao: null };
                }
                
                if(lancamentoProgramado.centroCusto && lancamentoProgramado.centroCusto.idCentroCusto) { 
                    var centroCusto = ListaService.getCentroCustoValue($scope.centroCustos, lancamentoProgramado.centroCusto.idCentroCusto); 
                    lancamentoProgramado.centroCusto = { descricao: centroCusto.descricao };                    
                } else {
                    lancamentoProgramado.centroCusto = { descricao: null };
                }
                
                if(lancamentoProgramado.lancamentos && lancamentoProgramado.lancamentos.length) {
                    lancamentoProgramado.lancamentos = lancamentoProgramado.lancamentos.length;
                }

                if(lancamentoProgramado.rateios && lancamentoProgramado.rateios.length) {
                    lancamentoProgramado.planoConta = { descricao: "Diversos" };
                    lancamentoProgramado.centroCusto = { descricao: "Diversos" };
                }
                
                if(lancamentoProgramado.favorecido) { lancamentoProgramado.favorecido = lancamentoProgramado.favorecido.nome; }

                if(lancamentoProgramado.modelo.id === $scope.modelos[3].id) { 
                    lancamentoProgramado.planoConta = { descricao: $scope.modelos[3].descricao };
                    lancamentoProgramado.centroCusto = { descricao: $scope.modelos[3].descricao };
                }
                                
//                lancamentoProgramado.situacao = lancamentoProgramado.situacao.descricao;
                lancamentoProgramado.frequencia.descricao += complementoDescricaoFrequencia;
//                lancamentoProgramado.numeroParcela = lancamentoProgramado.numero;
//                lancamentoProgramado.tipo.modelo = $scope.modelos;
                
                return _.pick(lancamentoProgramado, 'idLancamentoProgramado', 'conta', 'tipo', 'tipoLancamento', 'dataVencimento', 'numero', 
                'planoConta', 'centroCusto', 'usuario', 'favorecido', 'valor', 'situacao', 'frequencia', 'existeLancamento');
            })
        };
        
        var getSaldoLancamentoProgramadoParcelado = function(lancamentoProgramado) {
            var saldo = 0;
            _.map(lancamentoProgramado.parcelas, function(parcela) {  
                if(!parcela.lancamento) { saldo += parcela.valor; }
            });   
            return saldo;
        }
        
        var getLancamentoProgramadosSelecionados = function(conta, lancamentosProgramados, callback) {            
            var lancamentosProgramadosSelecionados = [];
            _.map(lancamentosProgramados, function(lancamentoProgramado) {
                if(!lancamentoProgramado.selected) return;
                if(callback) { lancamentoProgramado = callback(conta, lancamentoProgramado); }
                lancamentosProgramadosSelecionados.push(angular.copy(lancamentoProgramado));
            });
            return lancamentosProgramadosSelecionados;
        };
        
        var getLancamentos = function(lancamentosProgramados) {               
            var lancamentosProgramadosSelecionados = [];
            _.map(lancamentosProgramados, function(lancamentoProgramado) {        
                if(!existeLancamentoVinculado(lancamentoProgramado)) {                
                    lancamentosProgramadosSelecionados.push(lancamentoProgramado);
                }                 
            });
            return lancamentosProgramadosSelecionados;
        };
        
        // ***** VISUALIZAR ***** //

        $scope.visualizar = function(conta, lancamentoProgramado) {
            LancamentoProgramadoService.get(lancamentoProgramado.idLancamentoProgramado)
                .then(function(result) {
                     visualizar(conta, result);         
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        var visualizar = function(conta, lancamentoProgramado) {
            modalVisualizar(lancamentoProgramado)
               .then(function(lancamentoProgramadoEditar) {
                   $scope.editar(conta, lancamentoProgramadoEditar, lancamentoProgramado);
               });
        };
        
        // ***** SALVAR ***** //

        $scope.salvar = function(conta, tipo) {
            salvar(conta, tipo);
        };
        
        var salvar = function(conta, tipo) {
            modalSalvar(conta, tipo, null).then(function(result) {
                var gerarLancamento = result.gerarLancamento;
                result = ajustarDados(result);
                save(conta, result, gerarLancamento);
            });
        };
        
        var save = function(conta, lancamentoProgramado, gerarLancamento) {
            if(gerarLancamento) { criarLancamento(conta, lancamentoProgramado, true); } 
            else { 
                LancamentoProgramadoService.save(lancamentoProgramado)
                    .then(function(data) { 
                        //modalMessage(MESSAGES.lancamento.programar.sucesso.INSERIDO_SUCESSO);
                        todosByConta(conta);
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            };            
        };
        
        // ***** EDITAR ***** //

        $scope.editar = function(conta, lancamentoProgramado) {
            LancamentoProgramadoService.get(lancamentoProgramado.idLancamentoProgramado)
                .then(function(result) {
                    if(result.lancamentoProgramadoTransferencia) {
                        $scope.transferir(conta, result.lancamentoProgramadoTransferencia);
                    } else {   
                        editar(conta, result);
                    }
                })
                .catch(function(e) {
                    modalMessage(e.error);
                });           
        };
        
        var editar = function(conta, lancamentoProgramado) {                   
            modalSalvar(conta, lancamentoProgramado.tipo, lancamentoProgramado)
                .then(function(result) {
                    var gerarLancamento = result.gerarLancamento;
                    result = ajustarDados(result);
                    if(gerarLancamento) { criarLancamento(conta, result, true); } 
                    else { update(conta, result); }
                });            
        };
        
        var update = function(conta, lancamentoProgramado) {
            LancamentoProgramadoService.update(lancamentoProgramado.idLancamentoProgramado, lancamentoProgramado)
                .then(function (data) {  
                    modalMessage(MESSAGES.lancamento.programar.sucesso.ALTERADO_SUCESSO);
                    todosByConta(conta);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        // ***** CREATE ***** //
        
        var criarLancamento = function(conta, lancamentoProgramado, showMsg) { 
            if(lancamentoProgramado.idLancamentoProgramado) {
                LancamentoProgramadoService.getByNumeroParcela(lancamentoProgramado.idLancamentoProgramado, lancamentoProgramado.numeroParcela)
                    .then(function(data) {  
                        if(data) {
                            modalMessage(MESSAGES.lancamento.programar.info.PARCELA_EXISTENTE);
                        } else {
                            create(conta, lancamentoProgramado, showMsg);
                        }
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            } else {
                create(conta, lancamentoProgramado, showMsg);
            }
        };
        
        var criarLancamentoTransferencia = function(conta, lancamentoProgramadoTransferencia) { 
            criarLancamento(conta, lancamentoProgramadoTransferencia.lancamentoProgramadoOrigem, false);
            criarLancamento(conta, lancamentoProgramadoTransferencia.lancamentoProgramadoDestino, true);
        };
        
        var create = function(conta, lancamentoProgramado, showMsg) { 
            lancamentoProgramado = ajustarLancamentoProgramadoFrequencia(lancamentoProgramado);
            encerrarLancamentoProgramado(lancamentoProgramado, lancamentoProgramado.lancamentos[0]); 
            LancamentoProgramadoService.create(lancamentoProgramado)
                .then(function(data) {  
                    if(showMsg) {
                        //modalMessage(MESSAGES.lancamento.sucesso.INSERIDO_SUCESSO);
                        todosByConta(conta);                        
                    }
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        // ***** EXCLUIR ***** //
        
        $scope.excluir = function(conta, lancamentoProgramado) {            
            LancamentoProgramadoService.getLancamento(lancamentoProgramado.idLancamentoProgramado)
                .then(function(data) {   
                    if(data.lancamentos) {
                        excluirLancamentos(conta, data);                        
                    } else {
                        excluir(conta, data);
                    }
                });
        };
        
        var excluirLancamentos = function(conta, lancamentoProgramado) {    
            if(existeLancamentoVinculado(lancamentoProgramado)) {                
                modalMessage(MESSAGES.lancamento.programar.info.LANCAMENTO_VINCULADOS);
            } else {
                excluir(conta, lancamentoProgramado);
            } 
        };
        
        var excluir = function(conta, lancamentoProgramado) {
            modalExcluir(MESSAGES.lancamento.programar.info.CONFIRMAR_EXCLUIR)
                .then(function() {
                    remover(conta, lancamentoProgramado);
                });
        };
        
        var remover = function(conta, lancamentoProgramado) {
            LancamentoProgramadoService.delete(lancamentoProgramado.idLancamentoProgramado)
                .then(function(data) { 
                    modalMessage(MESSAGES.lancamento.programar.sucesso.REMOVIDO_SUCESSO);
                    todosByConta(conta);                        
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        }; 
        
        // ***** EXCLUIR TODOS ***** //

        $scope.excluirTodos = function(conta, lancamentos) {
            var lancamentoSelecionados = getLancamentoProgramadosSelecionados(conta, lancamentos);   
            if(!existeLancamentoSelecionado(lancamentoSelecionados)) return;
            var lancamentosValidos = getLancamentos(lancamentoSelecionados);
            var msg = MESSAGES.lancamento.programar.info.CONFIRMAR_EXCLUIR_TODOS;
            if(lancamentosValidos.length !== lancamentoSelecionados.length) { 
                msg = MESSAGES.lancamento.programar.info.CONFIRMAR_EXCLUIR_LANCAMENTOS_TODOS;
            }
            excluirTodos(conta, lancamentosValidos, msg);
        }; 
        
        var excluirTodos = function(conta, lancamentos, message) {
            modalExcluir(message)
                .then(function() {
                    removeAll(conta, lancamentos);
                });
        };
        
        var removeAll = function(conta, lancamentosProgramados) {
            lancamentosProgramados = _.map(lancamentosProgramados, function(lancamentoProgramado) {
                return ajustarDados(lancamentoProgramado);
            });
            LancamentoProgramadoService.deleteAll(lancamentosProgramados)
                .then(function(data) { 
                    modalMessage(MESSAGES.lancamento.programar.sucesso.REMOVIDO_SUCESSO_TODOS);
                    todosByConta(conta);                        
                })
                .catch(function(e) {
                    modalMessage(e);
                });            
        }
        
        // ***** TRANSFERIR ***** //

        $scope.transferir = function(conta, lancamentoProgramadoTransferencia) {
            if(!lancamentoProgramadoTransferencia) {
                transferir(conta, lancamentoProgramadoTransferencia);
            } else {
                LancamentoProgramadoTransferenciaService.get(lancamentoProgramadoTransferencia.idLancamentoProgramadoTransferencia)
                    .then(function(lancamentoProgramadoTransferencia) {
                        transferir(conta, lancamentoProgramadoTransferencia);
                    })
                    .catch(function(e) {
                        modalMessage(e.error);
                    });
            }            
        };

        var transferir = function(conta, lancamentoProgramadoTransferencia) {
            modalTransferir(lancamentoProgramadoTransferencia).then(function(result) {
                var gerarLancamento = result.gerarLancamento;
                result = ajustarDadosTransferencia(result);
                if(lancamentoProgramadoTransferencia) {  
                    updateTransferencia(conta, result, gerarLancamento);
                } else {
                    saveTransferencia(conta, result, gerarLancamento);
                }
            });
        }

        var saveTransferencia = function(conta, lancamentoProgramadoTransferencia, gerarLancamento) {
            if(gerarLancamento) { criarLancamentoTransferencia(conta, lancamentoProgramadoTransferencia); } 
            else { 
                LancamentoProgramadoTransferenciaService.save(lancamentoProgramadoTransferencia)
                    .then(function(data) {  
                        //modalMessage(MESSAGES.lancamento.transferir.sucesso.INSERIDO_SUCESSO);
                        todosByConta(conta);
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });  
            }
        }

        var updateTransferencia = function(conta, lancamentoProgramadoTransferencia, gerarLancamento) {
            if(gerarLancamento) { criarLancamentoTransferencia(conta, lancamentoProgramadoTransferencia); } 
            else { 
                LancamentoProgramadoTransferenciaService
                        .update(lancamentoProgramadoTransferencia.idLancamentoProgramadoTransferencia, lancamentoProgramadoTransferencia)
                    .then(function(data) {  
                        modalMessage(MESSAGES.lancamento.transferir.sucesso.ALTERADO_SUCESSO);
                        todosByConta(conta);
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });        
            }
        }
        
        var encerrarLancamentoProgramado = function(lancamentoProgramado, lancamento) {
            if(lancamentoProgramado.frequencia === 0) { // Único
                lancamentoProgramado.situacao = $scope.situacoes[2].id;
            }
            if(lancamento.numeroParcela === lancamentoProgramado.quantidadeParcela) {
                lancamentoProgramado.situacao = $scope.situacoes[2].id;                
            }            
        };

        // ***** MOVER ***** //

        $scope.moverContaTodos = function(conta, lancamentos) {    
            var lancamentoSelecionados = getLancamentoProgramadosSelecionados(conta, angular.copy(lancamentos), null)
            if(!existeLancamentoSelecionado(lancamentoSelecionados)) return;
            modalMoverConta(conta).then(function(data) {
                lancamentoSelecionados = getLancamentoProgramadosSelecionados(data, angular.copy(lancamentos), ajustarDadosMoverConta)
                moverContaTodos(conta, lancamentoSelecionados);
            });
        };

        var moverContaTodos = function(conta, lancamentos) {
            LancamentoProgramadoService.updateConta(lancamentos)
                .then(function (data) { 
                    todos(conta);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        // ***** REPORT ***** //
        
        $scope.report = function(lancamentos) {
            var params = LancamentoProgramadoService.report(lancamentos);   
            ReportService.pdf('lancamento/programado', params);
        };
                
        // ***** VALIDAR ***** //

        var validaPeriodo = function(dataInicio, dataFim) {
            if($scope.lancSearch.periodo) { return FinanceiroValidation.periodoLancamentos(dataInicio, dataFim, 90); }
            else { return FinanceiroValidation.periodoLancamentos(dataInicio, dataFim, 120); }
        };
        
        var existeLancamentoSelecionado = function(lancamentosSelecionados) {
            if(lancamentosSelecionados && lancamentosSelecionados.length) return true;
            modalMessage(MESSAGES.lancamento.programar.info.SEM_LANCAMENTO_SELECIONADO);
            return false;
        };
        
        var existeLancamentoVinculado = function(lancamentoProgramado) {   
            return lancamentoProgramado.existeLancamento;
        };
        
        // ***** AJUSTAR ***** //

        var ajustarDadosMoverConta = function(conta, lancamentoProgramado) {
            var lancamentoProgramadoCompleto = ListaService.getLancamentoProgramadoValue($scope.lancamentoProgramados, lancamentoProgramado.idLancamentoProgramado);
            lancamentoProgramadoCompleto.conta = conta;
            lancamentoProgramadoCompleto = ajustarDados(lancamentoProgramadoCompleto);
            return lancamentoProgramadoCompleto;
        };
        
        var ajustarDados = function(data) {   
            var lancamentos = [];
            if(data.lancamentos && data.lancamentos.length) { lancamentos = ajustarDadosLancamentos(data.lancamentos); }
            var lancamentoProgramado = LancamentoProgramadoHandler.handle(data);
            lancamentoProgramado.lancamentos = lancamentos;
            lancamentoProgramado.parcelas = LancamentoProgramadoParcelaHandler.handleList(data.parcelas);
            lancamentoProgramado.rateios = LancamentoProgramadoRateioHandler.handleList(data.rateios);
            return lancamentoProgramado;
        } 
        
        var ajustarDadosLancamentos = function(lancamentos) {
            var lancamentoList = [];
            _.map(lancamentos, function(lancamento) {
                lancamentoList.push(lancamento);
            });
            return lancamentoList;
        }
        
        var ajustarDadosTransferencia = function(data) {  
            var modelo = $scope.modelos[3];
            var lancamentoProgramadoTransferencia = LancamentoProgramadoTransferenciaHandler.handle(data); 
            lancamentoProgramadoTransferencia.lancamentoProgramadoOrigem = getLancamentoProgramado(data.contaOrigem, $scope.tipos[1], modelo, 
                data.dataVencimentoOrigem, data.dataCompetenciaOrigem, data, data.lancamentoProgramadoOrigem);
            lancamentoProgramadoTransferencia.lancamentoProgramadoDestino = getLancamentoProgramado(data.contaDestino, $scope.tipos[0], modelo, 
                data.dataVencimentoDestino, data.dataCompetenciaDestino, data, data.lancamentoProgramadoDestino);
            return lancamentoProgramadoTransferencia;
        }

        var getLancamentoProgramado = function(conta, tipo, modelo, dataLancamento, dataCompetencia, data, lancamentoProgramadoOriginal) {
            data.idLancamentoProgramado = (lancamentoProgramadoOriginal && lancamentoProgramadoOriginal.idLancamentoProgramado) || null;
            data.conta = conta;
            data.tipo = tipo;
            data.modelo = modelo;
            data.situacao = (data && data.situacao) || $scope.situacoes[0];
            data.numeroParcela = data.numeroParcela || 0;
            data.dataLancamento = dataLancamento;
            data.dataCompetencia = dataCompetencia;
            setLancamentoTransferencia(data);
            var lancamentoProgramadoHandle = LancamentoProgramadoHandler.handle(data);
            lancamentoProgramadoHandle.lancamentos = data.lancamentos;
            return lancamentoProgramadoHandle;
        };
        
        var setLancamentoTransferencia = function(lancamentoProgramado) { 
//            lancamentoProgramado.historico = '(' + lancamentoProgramado.modelo.descricao + ') ' + lancamentoProgramado.historico || "";
            var lancamentoHandle = LancamentoHandler.handle(lancamentoProgramado);
            lancamentoProgramado.lancamentos = [];
            lancamentoProgramado.lancamentos.push(lancamentoHandle);
        };
        
        var ajustarLancamentoProgramadoFrequencia = function(lancamentoProgramado) {             
            if((lancamentoProgramado.parcelas && lancamentoProgramado.parcelas.length)) {
                lancamentoProgramado.dataCompetencia = FrequenciaLancamentoService.addData(lancamentoProgramado.frequencia, lancamentoProgramado.dataCompetencia) || moment();
                lancamentoProgramado.dataVencimento = FrequenciaLancamentoService.addData(lancamentoProgramado.frequencia, lancamentoProgramado.dataVencimento) || moment();
                var parcela = getFirstParcelaAberta(lancamentoProgramado);
                if(parcela) { lancamentoProgramado.dataVencimento = parcela.dataVencimento || moment(); } 
            } else {
                lancamentoProgramado.dataCompetencia = FrequenciaLancamentoService.addData(lancamentoProgramado.frequencia, lancamentoProgramado.dataCompetencia) || moment();
                lancamentoProgramado.dataVencimento = FrequenciaLancamentoService.addData(lancamentoProgramado.frequencia, lancamentoProgramado.dataVencimento) || moment();
            }
            return lancamentoProgramado;
        };
        
        var getFirstParcelaAberta = function(lancamentoProgramado) {
            var numeroParcela = 0
            if(lancamentoProgramado.lancamentos && lancamentoProgramado.lancamentos.length){
                numeroParcela = lancamentoProgramado.lancamentos[0].numeroParcela;
            };
            var parcelas = lancamentoProgramado.parcelas || [];
            return _.find(parcelas, function(parcela) { 
                return !parcela.lancamento && numeroParcela !== parcela.numero; 
            });
        }
        
        // ***** MODAL ***** //
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };
        
        var modalVisualizar = function(lancamentoProgramado) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/lancamentoProgramado/modalLancamentoProgramadoVisualizar.html', 'ModalLancamentoProgramadoVisualizarController', 'lg',
                {
                    lancamentoProgramado: function() {
                        return lancamentoProgramado;
                    }
                });
            return modalInstance.result;
        };
        
        var modalSalvar = function(conta, tipo, lancamentoProgramado) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/lancamentoProgramado/modalLancamentoProgramado.html', 'ModalLancamentoProgramadoController', 'lg',
                {
                    lancamentoProgramado: function() {
                        return lancamentoProgramado;
                    },
                    tipo: function() {
                        return tipo;
                    },
                    conta: function() {
                        return conta;
                    }
                });
            return modalInstance.result;
        };
        
        var modalTransferir = function(lancamentoProgramadoTransferencia) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/lancamentoProgramado/modalLancamentoProgramadoTransferir.html', 'ModalLancamentoProgramadoTransferirController', 'lg', 
                {
                    lancamentoProgramadoTransferencia: function() {
                        return lancamentoProgramadoTransferencia;
                    }
                });
            return modalInstance.result;
        };

        var modalMoverConta = function(conta) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/conta/modalMoverConta.html', 'ModalMoverContaController', 'lg', 
                {
                    conta: function() {
                        return conta;
                    }                
                });
            return modalInstance.result;
        };
        
        var modalExcluir = function(message) {
            var modalInstance = ModalService.modalExcluir(MESSAGES.lancamento.programar.info.ALERT_EXCLUIR, message);
            return modalInstance.result;
        };
        
        init();

    }]);
