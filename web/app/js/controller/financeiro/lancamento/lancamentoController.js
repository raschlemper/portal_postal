'use strict';

app.controller('LancamentoController', 
    ['$scope', 'LancamentoService', 'LancamentoTransferenciaService', 'LancamentoConciliadoService', 'ContaService', 'PlanoContaService', 
    'CentroCustoService', 'ReportService', 'ModalService', 'DatePickerService', 'ListaService', 'LancamentoHandler', 'LancamentoRateioHandler', 
    'LancamentoTransferenciaHandler', 'LancamentoConciliadoHandler', 'FinanceiroValidation', 'LISTAS', 'MESSAGES',
    function ($scope, LancamentoService, LancamentoTransferenciaService, LancamentoConciliadoService, ContaService, PlanoContaService, 
        CentroCustoService, ReportService, ModalService, DatePickerService, ListaService, LancamentoHandler, LancamentoRateioHandler, 
        LancamentoTransferenciaHandler, LancamentoConciliadoHandler, FinanceiroValidation, LISTAS, MESSAGES) {

        var init = function () {
            $scope.lancamentos = [];
            $scope.lancamentosLista = [];     
            $scope.tipos = LISTAS.lancamento;
            $scope.modelos = LISTAS.modeloLancamento;
            $scope.situacoes = LISTAS.situacaoLancamento;
            $scope.statusConta = LISTAS.statusConta;
            $scope.datepickerDataInicio = angular.copy(DatePickerService.default);      
            $scope.datepickerDataFim = angular.copy(DatePickerService.default);    
            $scope.saldoTotal = 0;
            $scope.lancSearch = {};
            getTitle();
            contas();
            initTable();
        };  

        // ***** TABLE ***** //

        var initTable = function() {            
            $scope.colunas = [              
                {label: '', column: 'tipo', headerClass: 'no-sort', dataClass:'text-center col-tipo', filter: {name: 'tipoLancamento', args: ''}},               
    //                {label: '', column: 'anexos', headerClass: 'no-sort', dataClass:'text-center col-defualt', filter: {name: 'anexo', args: '', callback: 'linha.events.anexar(item)'}},           
                {label: '', column: 'situacao.descricao', headerClass: 'no-sort', dataClass:'text-center col-compensado', filter: {name: 'situacaoLancamento', args: '', callback: 'linha.events.compensar(item)'}}, 
                {label: '', column: 'numeroLoteConciliado', headerClass: 'no-sort', dataClass:'text-center col-reconciliado', filter: {name: 'conciliadoLancamento', args: ''}},         
                {label: 'Data', column: 'dataLancamento', dataClass: 'text-center cel-data', filter: {name: 'date', args: 'dd/MM/yy'}},                
                {label: 'Número', column: 'numero'},               
                {label: 'Plano Conta', column: 'planoConta'},   
                {label: 'Centro Custo', column: 'centroCusto', showColumn: true, selected: false},
                {label: 'Usuário', column: 'usuario', showColumn: true, selected: false},
                {label: 'Favorecido', column: 'favorecido'},                
                {label: 'Histórico', column: 'historico'},
                {label: 'Crédito', column: 'deposito', headerClass: 'no-sort', dataClass:'text-right', filter: {name: 'currency', args: ''}},  
                {label: 'Débito', column: 'pagamento', headerClass: 'no-sort', dataClass:'text-right', filter: {name: 'currency', args: ''}},  
                {label: 'Saldo', column: 'saldo', headerClass: 'no-sort', dataClass:'text-right', filter: {name: 'saldoLancamento', args: ''}}
            ]            
            $scope.linha = {
                conditionalClass: function(item) {
                    if(item.tipo.id === $scope.tipos[0].id) return 'text-primary';
                    else if(item.tipo.id === $scope.tipos[1].id) return 'text-danger';
                    else if(item.tipo.id === $scope.tipos[$scope.tipos.length - 1].id) return 'text-warning';
                },
                events: { 
                    edit: function(lancamento) {
                        if(validaConciliado(lancamento.idLancamento)) {
                            modalConfirmarConciliado().then(function() {
                                $scope.editar($scope.conta, lancamento, false);
                            });
                        } else {
                            $scope.editar($scope.conta, lancamento, false);
                        }
                    },
                    remove: function(lancamento) {
                        if(validaConciliado(lancamento.idLancamento)) {
                            modalConfirmarConciliado().then(function() {
                                $scope.excluir($scope.conta, lancamento);
                            });
                        } else {
                            $scope.excluir($scope.conta, lancamento);
                        }

                    },
                    view: function(lancamento) {
                        $scope.visualizar($scope.conta, lancamento);
                    },
                    compensar: function(lancamento) {
                        $scope.compensar($scope.conta, lancamento, true);
                    },
                    anexar: function(lancamento) {
                        $scope.editar($scope.conta, lancamento.idLancamento, lancamento.tipo, true);
                    }
                }
            };
            $scope.events = { 
                table: function(lancamentos) {
                    calculateSaldo(lancamentos);
                }
            }
        };

        $scope.filter = function(lista, search) { 
            lista = _.filter(lista, function(item) {
                return filterByData(item, search);
            });    
            lista = filterTipo(lista, search);
            return lista;
        };

        var filterByData = function(item, search) {
            var dataInicio = moment(search.dataInicio);
            var dataFim = moment(search.dataFim);
            if((!search.dataInicio || !dataInicio.isValid()) || (!search.dataFim || !dataFim.isValid())) return true;
            var data = moment(item.dataLancamento);
            return (data.isBefore(search.dataFim) && data.isAfter(search.dataInicio) 
                    || (data.isSame(search.dataInicio) || data.isSame(search.dataFim)));
        };  

        var filterTipo = function(lista, search) {          
            if(!search.tipo) return lista;
            var tipo = JSON.parse(search.tipo); 
            lista = _.filter(lista, function(item) {
                return item.tipo.id === tipo.id;
            });
            return lista;
        };
        
        $scope.filterByDays = function(days) {
            $scope.lancSearch.dataInicio = moment().add(days, 'days').format('YYYY-MM-DD');
            $scope.lancSearch.dataFim = moment().add(days, 'days').format('YYYY-MM-DD');
        };

        // ***** CONTROLLER ***** //

        var getTitle = function() {
            $scope.title = MESSAGES.lancamento.title.LISTA; 
        };

        var contas = function() {
            ContaService.getAll()
                .then(function (data) {
                    $scope.contas = data;
                    $scope.conta = $scope.contas[0];
                    planoContas();
                })
                .catch(function (e) {
                    console.log(e);
                });
        };

        $scope.changeConta = function(conta) {
            $scope.conta = conta;
            todos(conta);
        }

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
                    todos($scope.conta);
                })
                .catch(function (e) {
                    console.log(e);
                });
        };

        var todos = function(conta) {
            ContaService.getLancamento(conta.idConta)
                .then(function (data) {
                    $scope.lancamentos = angular.copy(data.lancamentos);
                    $scope.lancamentosLista = criarLancamentosLista(data);
                })
                .catch(function(e) {
                    console.log(e);
                });
        };

        var criarLancamentosLista = function(data) {
            return _.map(data.lancamentos, function(lancamento) { 

                if(lancamento.tipo.id === $scope.tipos[1].id) { 
                    lancamento.pagamento = lancamento.valor * -1;
                    lancamento.deposito = null;
                } 
                else if(lancamento.tipo.id === $scope.tipos[0].id) { 
                    lancamento.deposito = lancamento.valor; 
                    lancamento.pagamento = null;
                }  

                if(lancamento.numeroParcela) { lancamento.numero = lancamento.numero + '-' + lancamento.numeroParcela; }

                if(lancamento.planoConta && lancamento.planoConta.idPlanoConta) { 
                    var planoConta = ListaService.getPlanoContaValue($scope.planoContas, lancamento.planoConta.idPlanoConta); 
                    lancamento.planoConta = planoConta.descricao;                    
                } else {
                    lancamento.planoConta = null;
                }

                if(lancamento.centroCusto && lancamento.centroCusto.idCentroCusto) { 
                    var centroCusto = ListaService.getCentroCustoValue($scope.centroCustos, lancamento.centroCusto.idCentroCusto); 
                    lancamento.centroCusto = centroCusto.descricao;                    
                } else {
                    lancamento.centroCusto = null;
                }

                if(lancamento.modelo.id === $scope.modelos[1].id || lancamento.modelo.id === $scope.modelos[3].id) { 
                    lancamento.planoConta = lancamento.modelo.descricao;
                }

                lancamento.tipo.modelo = lancamento.modelo;

                if(lancamento.rateios && lancamento.rateios.length) {
                    lancamento.planoConta = "Diversos";
                    lancamento.centroCusto = "Diversos";
                }
                
                if(lancamento.favorecido) { lancamento.favorecido = lancamento.favorecido.nome; }

                return _.pick(lancamento, 'idLancamento', 'tipo', 'anexos', 'dataLancamento', 'numero', 'planoConta', 'centroCusto', 'usuario', 'favorecido', 'deposito', 'pagamento', 'saldo', 'historico', 'situacao', 'numeroLoteConciliado');
            })
        };

        var calculateSaldo = function(lancamentos) {
            var saldo = 0;
            $scope.saldoTotal = 0;
            $scope.saldoTotal += ($scope.conta && $scope.conta.valorSaldoAbertura) || 0;
            return _.map(lancamentos, function(lancamento) {
                if(lancamento.pagamento) { 
                    saldo += lancamento.pagamento;
                    $scope.saldoTotal += lancamento.pagamento;
                } 
                else if(lancamento.deposito) {
                    saldo += lancamento.deposito;
                    $scope.saldoTotal += lancamento.deposito;
                }
                lancamento.saldo = saldo;
            });
        };

        var getLancamentoSelecionados = function(conta, lancamentos, callback) {            
            var lancamentosSelecionados = [];
            _.map(lancamentos, function(lancamento) {
                if(!lancamento.selected) return;
                if(callback) { lancamento = callback(conta, lancamento); }
                lancamentosSelecionados.push(angular.copy(lancamento));
            });
            return lancamentosSelecionados;
        };

        var getLancamentosProgramado = function(lancamentos) {               
            var lancamentosSelecionados = [];
            _.map(lancamentos, function(lancamento) {        
                if(!existeLancamentoProgramadoParceladoPosterior(lancamento)) {                
                    lancamentosSelecionados.push(lancamento);
                }                 
            });
            return lancamentosSelecionados;
        };

        // ***** VISUALIZAR ***** //

        $scope.visualizar = function(conta, lancamento) {
            LancamentoService.get(lancamento.idLancamento)
                .then(function(result) {
                     visualizar(conta, result)         
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        var visualizar = function(conta, lancamento) {
            modalVisualizar(lancamento)
               .then(function(lancamentoEditar) {
                   $scope.editar(conta, lancamentoEditar, false);
               });
        };

        // ***** SALVAR ***** //

        $scope.salvar = function(conta, tipo) {
            if(!validaConta(conta)) return;
            salvar(conta, tipo);
        };

        var salvar = function(conta, tipo) {
            modalSalvar(conta, null, tipo, false).then(function(result) {
                result = ajustarDados(result);
                save(conta, result);
            });
        };

        var save = function(conta, lancamento) {
            LancamentoService.save(lancamento)
                .then(function(data) {  
                    modalMessage(MESSAGES.lancamento.sucesso.INSERIDO_SUCESSO);
                    todos(conta);
                })
                .catch(function(e) {
                    modalMessage(e);
                });

        };

        // ***** EDITAR ***** //

        $scope.editar = function(conta, lancamento, goToAnexo) {
            if(!validaConta(conta)) return;
            LancamentoService.get(lancamento.idLancamento)
                .then(function(result) {
                    if(result.lancamentoTransferencia) {
                        $scope.transferir(conta, result.lancamentoTransferencia);
                    } else {   
                        editar(conta, result, goToAnexo);
                    }
                })
                .catch(function(e) {
                    modalMessage(e.error);
                });

        };

        var editar = function(conta, lancamento, goToAnexo) {                   
            modalSalvar(conta, lancamento, lancamento.tipo, goToAnexo)
                .then(function(result) {
                    result = ajustarDados(result);
                    update(conta, result);
                });            
        }

        var update = function(conta, lancamento) {
            LancamentoService.update(lancamento.idLancamento, lancamento)
                .then(function (data) {  
                    modalMessage(MESSAGES.lancamento.sucesso.ALTERADO_SUCESSO);
                    todos(conta);
                })
                .catch(function(e) {
                    modalMessage(e);
                });            
        }

        // ***** EXCLUIR ***** //

        $scope.excluir = function(conta, lancamento) {
            if(!validaConta(conta)) return;
            LancamentoService.get(lancamento.idLancamento)
                .then(function(data) {
//                    if(data.lancamentoProgramado) {
//                        excluirLancamentoProgramado(conta, data);
//                    } else {
                        excluir(conta, data);
//                    }  
                })
                .catch(function(e) {
                    modalMessage(e);
                });            
        }; 

        var excluirLancamentoProgramado = function(conta, lancamento) {    
            if(existeLancamentoProgramadoParceladoPosterior(lancamento)) {                
                modalMessage(MESSAGES.lancamento.info.EXCLUIR_POSTERIOR);
            } else {
                excluir(conta, lancamento);
            } 
        };

        var excluir = function(conta, lancamento) {
            modalExcluir(MESSAGES.lancamento.info.CONFIRMAR_EXCLUIR)
                .then(function() {
                    remove(conta, lancamento);
                });
        };

        var remove = function(conta, lancamento) {
            LancamentoService.delete(lancamento.idLancamento)
                .then(function(data) { 
                    modalMessage(MESSAGES.lancamento.sucesso.REMOVIDO_SUCESSO);
                    todos(conta);                        
                })
                .catch(function(e) {
                    modalMessage(e);
                });            
        };

        // ***** EXCLUIR TODOS ***** //

        $scope.excluirTodos = function(conta, lancamentos) {
            if(!validaConta(conta)) return;
            var lancamentoSelecionados = getLancamentoSelecionados(conta, lancamentos, ajustarDadosExcluir);  
            if(!existeLancamentoSelecionado(lancamentoSelecionados)) return;
//            var lancamentosValidos = getLancamentosProgramado(lancamentoSelecionados);
            var msg = MESSAGES.lancamento.info.CONFIRMAR_EXCLUIR_TODOS;
//            if(lancamentosValidos.length !== lancamentoSelecionados.length) { 
//                msg = MESSAGES.lancamento.info.CONFIRMAR_EXCLUIR_PROGRAMADOS_TODOS;
//            }
            excluirTodos(conta, lancamentoSelecionados, msg);
        }; 

        var excluirTodos = function(conta, lancamentos, message) {
            modalExcluir(message)
                .then(function() {
                    removeAll(conta, lancamentos);
                });
        };

        var removeAll = function(conta, lancamentos) {
            var lancamentoList = [];
            _.map(lancamentos, function(lancamento) {
                lancamentoList.push(ajustarDados(lancamento));
            });
            LancamentoService.deleteAll(lancamentoList)
                .then(function(data) { 
                    modalMessage(MESSAGES.lancamento.sucesso.REMOVIDO_SUCESSO_TODOS);
                    todos(conta);                        
                })
                .catch(function(e) {
                    modalMessage(e);
                });            
        };

        // ***** TRANSFERIR ***** //

        $scope.transferir = function(conta, lancamentoTransferencia) {
            if(!validaConta(conta)) return;
            if(!lancamentoTransferencia) {
                transferir(conta, lancamentoTransferencia);
            } else {
                LancamentoTransferenciaService.get(lancamentoTransferencia.idLancamentoTransferencia)
                    .then(function(lancamentoTransferencia) {
                        transferir(conta, lancamentoTransferencia);
                    })
                    .catch(function(e) {
                        modalMessage(e.error);
                    });
            }            
        };

        var transferir = function(conta, lancamentoTransferencia) {
            modalTransferir(lancamentoTransferencia).then(function(result) {
                result = ajustarDadosTransferencia(result);
                if(lancamentoTransferencia) {  
                    updateTransferencia(conta, result);
                } else {
                    saveTransferencia(conta, result);
                }
            });
        };

        var saveTransferencia = function(conta, lancamentoTransferencia) {
            LancamentoTransferenciaService.save(lancamentoTransferencia)
                .then(function(data) {  
                    modalMessage(MESSAGES.lancamento.transferir.sucesso.INSERIDO_SUCESSO);
                    todos(conta);
                })
                .catch(function(e) {
                    modalMessage(e);
                });        
        };

        var updateTransferencia = function(conta, lancamentoTransferencia) {
            LancamentoTransferenciaService.update(lancamentoTransferencia.idLancamentoTransferencia, lancamentoTransferencia)
                .then(function(data) {  
                    modalMessage(MESSAGES.lancamento.transferir.sucesso.ALTERADO_SUCESSO);
                    todos(conta);
                })
                .catch(function(e) {
                    modalMessage(e);
                });        
        };

        // ***** RECONCILIAR ***** //

        $scope.conciliar = function(conta) {
            modalConciliar(conta).then(function(result) {
                result = ajustarDadosConciliado(conta, result);
                conciliar(conta, result);
            });
        };

        var conciliar = function(conta, lancamentoConciliado) {
            LancamentoConciliadoService.create(lancamentoConciliado)
                .then(function(data) {  
                    modalMessage(MESSAGES.lancamento.conciliar.sucesso.INSERIDO_SUCESSO);
                    todos(conta);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        // ***** COMPENSAR ***** //

        $scope.compensarTodos = function(conta, lancamentos) {
            var lancamentoSelecionados = getLancamentoSelecionados(conta, angular.copy(lancamentos), ajustarDadosCompensar)
            if(!existeLancamentoSelecionado(lancamentoSelecionados)) return;
            modalConfirmarCompensado().then(function() {
                compensarTodos(conta, lancamentoSelecionados);
            });
        };

        $scope.compensar = function(conta, lancamento) {
            var lancamentosCompletos = [];
            lancamentosCompletos.push(ajustarDadosCompensar(conta, lancamento));
            compensarTodos(conta, lancamentosCompletos);
        };

        var compensarTodos = function(conta, lancamentos) {
            LancamentoService.updateSituacao(lancamentos)
                .then(function (data) { 
                    todos(conta);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        // ***** REPORT ***** //
        
        $scope.report = function(lancamentos) {
            var params = LancamentoService.report(lancamentos);   
            ReportService.pdf('lancamento', params);
        };

        // ***** VALIDAR ***** //

        var validaConta = function(conta) {
            return FinanceiroValidation.contaEncerrada(conta);
        };

        var validaConciliado = function(idLancamento) {
            var lancamento = ListaService.getLancamentoValue($scope.lancamentos, idLancamento);
            if(lancamento.numeroLoteConciliado) { return true; }
            return false
        };

        var existeLancamentoSelecionado = function(lancamentosSelecionados) {
            if(lancamentosSelecionados && lancamentosSelecionados.length) return true;
            modalMessage(MESSAGES.lancamento.programar.info.SEM_LANCAMENTO_SELECIONADO);
            return false;
        };

        var existeLancamentoProgramadoParceladoPosterior = function(lancamento) {   
            if(lancamento.modelo.id !== $scope.modelos[4].id) return false;         
            if(lancamento.lancamentoProgramado 
                    && lancamento.numeroParcela < lancamento.lancamentoProgramado.numeroParcela) {                
                return true;
            } 
            return false;
        };

        // ***** AJUSTAR ***** //

        var ajustarDadosExcluir = function(conta, lancamento) { 
            var lancamentoCompleto = ListaService.getLancamentoValue($scope.lancamentos, lancamento.idLancamento);
            lancamentoCompleto.conta = conta;
            return lancamentoCompleto;
        };

        var ajustarDadosCompensar = function(conta, lancamento) {
            var lancamentoCompleto = ListaService.getLancamentoValue($scope.lancamentos, lancamento.idLancamento);
            lancamentoCompleto.conta = conta;
            if(lancamentoCompleto.situacao.id === $scope.situacoes[2].id) { 
                lancamentoCompleto.situacao = $scope.situacoes[0]; 
            } else {
                lancamentoCompleto.situacao = $scope.situacoes[2];                 
            }
            lancamentoCompleto = ajustarDados(lancamentoCompleto);
            return lancamentoCompleto;
        };

        var ajustarDados = function(data) {  
            var lancamento = LancamentoHandler.handle(data);
            lancamento.rateios = LancamentoRateioHandler.handleList(data.rateios);
            return lancamento;
        };

        var ajustarDadosTransferencia = function(data) { 
            var modelo = $scope.modelos[1];
            var lancamentoTransferencia = LancamentoTransferenciaHandler.handle(data);
            lancamentoTransferencia.lancamentoOrigem = getLancamento(data.contaOrigem, null, null, $scope.tipos[1], modelo, 
                data.dataLancamentoOrigem, data.dataCompetenciaOrigem, data, data.lancamentoOrigem);
            lancamentoTransferencia.lancamentoDestino = getLancamento(data.contaDestino, null, null,  $scope.tipos[0], modelo, 
                data.dataLancamentoDestino, data.dataCompetenciaDestino, data, data.lancamentoDestino);
            return lancamentoTransferencia;
        };

        var ajustarDadosConciliado = function(conta, data) {    
            var lancamento = getLancamento(conta, data.planoConta, data.centroCusto, data.tipo, $scope.modelos[5], 
                data.dataLancamento, data.dataCompetencia, data, null);
            var lancamentoConciliado = LancamentoConciliadoHandler.handle(data);
            lancamentoConciliado.lancamento = lancamento;
            return lancamentoConciliado;
        };

        var getLancamento = function(conta, planoConta, centroCusto, tipo, modelo, dataLancamento, dataCompetencia, data, lancamentoOriginal) {
            data.idLancamento = (lancamentoOriginal && lancamentoOriginal.idLancamento) || null;
            data.conta = conta;
            data.planoConta = planoConta;
            data.centroCusto = centroCusto;
            data.tipo = tipo;
            data.modelo = modelo;
            data.dataLancamento = dataLancamento;
            data.dataCompetencia = dataCompetencia;
            data.situacao = (data && data.situacao) || $scope.situacoes[0];
            return LancamentoHandler.handle(data);
        };

        // ***** MODAL ***** //

        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };

        var modalVisualizar = function(lancamento) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/lancamento/modalLancamentoVisualizar.html', 'ModalLancamentoVisualizarController', 'lg',
                {
                    lancamento: function() {
                        return lancamento;
                    }
                });
            return modalInstance.result;
        };

        var modalSalvar = function(conta, lancamento, tipo, anexo) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/lancamento/modalLancamento.html', 'ModalLancamentoController', 'lg',
                {
                    lancamento: function() {
                        return lancamento;
                    },
                    conta: function() {
                        return conta;
                    },
                    tipo: function() {
                        return tipo;
                    },
                    anexo: function() {
                        return anexo;
                    }
                });
            return modalInstance.result;
        };

        var modalTransferir = function(lancamentoTransferencia) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/lancamento/modalLancamentoTransferir.html', 'ModalLancamentoTransferirController', 'lg',
                {
                    lancamentoTransferencia: function() {
                        return lancamentoTransferencia;
                    }
                });
            return modalInstance.result;
        };

        var modalConciliar = function(conta) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/lancamento/modalLancamentoConciliar.html', 'ModalLancamentoConciliarController', 'lg',
                {
                    conta: function() {
                        return conta;
                    },
                });
            return modalInstance.result;
        };

        var modalConfirmarConciliado = function() {
            var modalInstance = ModalService.modalConfirmar(MESSAGES.lancamento.info.ALERT, MESSAGES.lancamento.conciliar.info.CONFIRMAR_ALTERAR);
            return modalInstance.result;
        };

        var modalConfirmarCompensado = function() {
            var modalInstance = ModalService.modalConfirmar(MESSAGES.lancamento.info.ALERT, MESSAGES.lancamento.compensar.info.CONFIRMAR_TODOS);
            return modalInstance.result;
        };

        var modalExcluir = function(message) {
            var modalInstance = ModalService.modalExcluir(MESSAGES.lancamento.info.ALERT_EXCLUIR, message);
            return modalInstance.result;
        };

        init();

    }]);
