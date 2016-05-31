'use strict';

app.controller('LancamentoController', 
    ['$scope', '$filter', 'LancamentoService', 'LancamentoProgramadoService', 'LancamentoTransferenciaService', 'LancamentoConciliadoService', 'ContaService', 'PlanoContaService', 'CentroCustoService', 'ModalService', 'DatePickerService', 'ListaService', 'LISTAS', 'MESSAGES',
    function ($scope, $filter, LancamentoService, LancamentoProgramadoService, LancamentoTransferenciaService, LancamentoConciliadoService, ContaService, PlanoContaService, CentroCustoService, ModalService, DatePickerService, ListaService, LISTAS, MESSAGES) {

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
            contas();
//            initTipos();
            initTable();
        };  
        
//        var initTipos = function() {       
//            var modelo = angular.copy($scope.modelos[1]);
//            var maxTipo = _.max($scope.tipos, function(tipo){ return tipo.id; });
//            modelo.id = maxTipo.id + 1;
//            $scope.tipos.push(modelo);
//        }
        
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
                {label: 'Favorecido', column: 'favorecido'},                
                {label: 'Histórico', column: 'historico'},
                {label: 'Depósito', column: 'deposito', headerClass: 'no-sort', dataClass:'text-right', filter: {name: 'currency', args: ''}},  
                {label: 'Pagamento', column: 'pagamento', headerClass: 'no-sort', dataClass:'text-right', filter: {name: 'currency', args: ''}},  
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
                                $scope.editar($scope.conta, lancamento.idLancamento, lancamento.tipo, false);
                            });
                        } else {
                            $scope.editar($scope.conta, lancamento.idLancamento, lancamento.tipo, false);
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
                        $scope.visualizar($scope.conta, lancamento.idLancamento);
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
        }
        
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
                    modalMessage(e);
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
                
                if(lancamento.modelo.id === $scope.modelos[1].id) { 
//                    var modeloTransferencia = $scope.tipos[$scope.tipos.length - 1];
//                    lancamento.tipo = modeloTransferencia;
                    lancamento.planoConta = $scope.modelos[1].descricao;
                }
                
                lancamento.tipo.modelo = lancamento.modelo;
                
                if(lancamento.rateios && lancamento.rateios.length) {
                    lancamento.planoConta = "Diversos";
                    lancamento.centroCusto = "Diversos";
                }
                
                return _.pick(lancamento, 'idLancamento', 'tipo', 'anexos', 'dataLancamento', 'numero', 'planoConta', 'centroCusto', 'favorecido', 'deposito', 'pagamento', 'saldo', 'historico', 'situacao', 'numeroLoteConciliado');
            })
        };
        
        var calculateSaldo = function(lancamentos) {
            var saldo = 0;
            $scope.saldoTotal = 0;
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

        $scope.visualizar = function(conta, idLancamento) {
            LancamentoService.get(idLancamento)
                .then(function(lancamento) {
                     modalVisualizar(lancamento).then(function(result) {
                         $scope.editar(conta, result, lancamento.tipo, false);
                     })          
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        $scope.salvar = function(conta, tipo) {
            if(!validaConta(conta)) return;
            modalSalvar(conta, null, tipo, false).then(function(result) {
                result = ajustarDados(result);
                LancamentoService.save(result)
                    .then(function(data) {  
                        modalMessage(MESSAGES.lancamento.sucesso.INSERIDO_SUCESSO);
                        todos(conta);
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            });
        };

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
        }
        
        var saveTransferencia = function(conta, lancamentoTransferencia) {
            LancamentoTransferenciaService.save(lancamentoTransferencia)
                .then(function(data) {  
                    modalMessage(MESSAGES.lancamento.transferir.sucesso.INSERIDO_SUCESSO);
                    todos(conta);
                })
                .catch(function(e) {
                    modalMessage(e);
                });        
        }
        
        var updateTransferencia = function(conta, lancamentoTransferencia) {
            LancamentoTransferenciaService.update(lancamentoTransferencia.idLancamentoTransferencia, lancamentoTransferencia)
                .then(function(data) {  
                    modalMessage(MESSAGES.lancamento.transferir.sucesso.ALTERADO_SUCESSO);
                    todos(conta);
                })
                .catch(function(e) {
                    modalMessage(e);
                });        
        }

        $scope.conciliar = function(conta) {
            modalConciliar(conta).then(function(result) {
                result = ajustarDadosConciliado(conta, result);
                LancamentoConciliadoService.create(result)
                    .then(function(data) {  
                        modalMessage(MESSAGES.lancamento.conciliar.sucesso.INSERIDO_SUCESSO);
                        todos(conta);
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            });
        };

        $scope.compensarTodos = function(conta, lancamentos) {
            modalConfirmarCompensado().then(function() {
                var lancamentosCompletos = [];
                _.map(lancamentos, function(lancamento) {
                    if(!lancamento.selected) return;
                    lancamentosCompletos.push(compensar(conta, lancamento));
                });
                compensarTodos(conta, lancamentosCompletos);
            });
        };

        $scope.compensar = function(conta, lancamento) {
            modalConfirmarCompensado().then(function() {
                var lancamentosCompletos = [];
                lancamentosCompletos.push(compensar(conta, lancamento));
                compensarTodos(conta, lancamentosCompletos);
            });
        };
        
        var compensarTodos = function(conta, lancamentos) {
            LancamentoService.updateAll(lancamentos)
                .then(function (data) {  
                    modalMessage(MESSAGES.lancamento.compensar.sucesso.INSERIDO_SUCESSO);
                    todos(conta);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        }

        var compensar = function(conta, lancamento) {
            var lancamentoCompleto = ListaService.getLancamentoValue($scope.lancamentos, lancamento.idLancamento);
            lancamentoCompleto.conta = conta;
            lancamentoCompleto.situacao = $scope.situacoes[2];
            lancamentoCompleto = ajustarDados(lancamentoCompleto);
            return lancamentoCompleto;
        };

        $scope.editar = function(conta, idLancamento, tipo, goToAnexo) {
            if(!validaConta(conta)) return;
            LancamentoService.get(idLancamento)
                .then(function(lancamento) {
                    if(lancamento.lancamentoTransferencia) {
                        $scope.transferir(conta, lancamento.lancamentoTransferencia);
                    } else {                    
                        modalSalvar(conta, lancamento, tipo, goToAnexo).then(function(result) {
                            result = ajustarDados(result);
                            LancamentoService.update(idLancamento, result)
                                .then(function (data) {  
                                    modalMessage(MESSAGES.lancamento.compensar.sucesso.ALTERADO_SUCESSO);
                                    todos(conta);
                                })
                                .catch(function(e) {
                                    modalMessage(e);
                                });
                        });                        
                    }
                })
                .catch(function(e) {
                    modalMessage(e.error);
                });
           
        };

        $scope.excluir = function(conta, lancamento) {
            if(!validaConta(conta)) return;
            if(lancamento.lancamentoProgramado) {
                LancamentoProgramadoService.get(lancamento.lancamentoProgramado.idLancamentoProgramado)
                    .then(function(lancamento) {
                        if(lancamento.idLancamento !== lancamento.lancamentoProgramado.idLancamentoProgramado) {                
                            modalMessage(MESSAGES.lancamento.info.EXCLUIR_POSTERIOR);
                            return;
                        }     
                        excluir(conta, lancamento.idLancamento);
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            } else {
                excluir(conta, lancamento.idLancamento);
            }  
            
        }; 
        
        var excluir = function(conta, idLancamento) {
            LancamentoService.get(idLancamento)
                .then(function(lancamento) {
                    if(lancamento.lancamentoProgramado && lancamento.numeroParcela < lancamento.lancamentoProgramado.numeroParcela) {                
                        modalMessage(MESSAGES.lancamento.info.EXCLUIR_POSTERIOR);
                        return;
                    }     
                    modalExcluir().then(function() {
                        LancamentoService.delete(idLancamento)
                            .then(function(data) { 
                                modalMessage(MESSAGES.lancamento.sucesso.REMOVIDO_SUCESSO);
                                todos(conta);                        
                            })
                            .catch(function(e) {
                                modalMessage(e);
                            });
                    });
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        var validaConta = function(conta) {
            if(conta.status.id === $scope.statusConta[1].id) {
                modalMessage(MESSAGES.conta.info.CONTA_ENCERRADO);
                return false;
            };
            return true;
        }
        
        var validaConciliado = function(idLancamento) {
            var lancamento = ListaService.getLancamentoValue($scope.lancamentos, idLancamento);
            if(lancamento.numeroLoteConciliado) { return true; }
            return false
        }
        
        var ajustarDados = function(data) {                 
            data.conta = { idConta: data.conta.idConta };  
            if(data.planoConta) {     
                data.planoConta = { idPlanoConta: data.planoConta.idPlanoConta }; 
            }
            if(data.centroCusto) {
                data.centroCusto = { idCentroCusto: data.centroCusto.idCentroCusto };
            }
            if(data.lancamentoProgramado) {
                data.lancamentoProgramado = { idLancamentoProgramado: data.lancamentoProgramado.idLancamentoProgramado }; 
            }
            data.tipo = data.tipo.id; 
            data.dataEmissao = data.dataEmissao || moment();
            data.dataVencimento = data.dataVencimento || data.dataLancamento || moment();
            data.dataLancamento = data.dataLancamento || moment();
            data.dataCompensacao = null;
            data.situacao = data.situacao.id;
            data.modelo = data.modelo.id; 
            data.valorDesconto = data.valorDesconto || 0;
            data.valorJuros = data.valorJuros || 0;
            data.valorMulta = data.valorMulta || 0;
            if(data.situacao === $scope.situacoes[2].id) { data.dataCompensacao = moment(); }
            ajustarDadosRateio(data);
            return data;
        }
        
        var ajustarDadosRateio = function(data) {
            if(!data.rateios) return null;
            _.map(data.rateios, function(rateio) { 
                if(rateio.planoConta) {     
                    rateio.planoConta = { idPlanoConta: rateio.planoConta.idPlanoConta }; 
                }
                if(rateio.centroCusto) {
                    rateio.centroCusto = { idCentroCusto: rateio.centroCusto.idCentroCusto };
                }   
            });
        }
        
        var ajustarDadosTransferencia = function(data) {
            var modelo = $scope.modelos[1];
            var lancamentoTransferencia = { 
                idLancamentoTransferencia: data.idLancamentoTransferencia || null,
                lancamentoOrigem: getLancamento(data.contaOrigem, null, null, $scope.tipos[1], modelo, data, data.lancamentoOrigem),
                lancamentoDestino: getLancamento(data.contaDestino, null, null,  $scope.tipos[0], modelo, data, data.lancamentoDestino),
                numero: data.numero,
                dataCompetencia: data.dataCompetencia,
                dataEmissao: data.dataEmissao || moment(),
                dataLancamento: data.dataLancamento || moment(),
                valor: data.valor,
                historico: data.historico,
                observacao: data.observacao
            };    
            return lancamentoTransferencia;
        }
        
        var ajustarDadosConciliado = function(conta, data) {                 
            var lancamentoConciliado = { 
                idLancamentoConciliado: data.idLancamentoConciliado || null,
                lancamento: getLancamento(conta, data.planoConta, data.centroCusto, data.tipo, $scope.modelos[5], data, null),          
                conta: { idConta: conta.idConta },  
                planoConta: { idPlanoConta: data.planoConta.idPlanoConta },
                centroCusto: { idCentroCusto: data.centroCusto.idCentroCusto },
                tipo: data.tipo.id,
                dataCompetencia: data.dataCompetencia,
                dataEmissao: data.dataEmissao || moment(),
                dataLancamento: data.dataLancamento || moment(),
                valor: data.valor,
                historico: data.historico
            };     
            return lancamentoConciliado;
        }
        
        var getLancamento = function(conta, planoConta, centroCusto, tipo, modelo, data, lancamentoOriginal) {
            var lancamento = {
                idLancamento: (lancamentoOriginal && lancamentoOriginal.idLancamento) || null,
                conta: null,
                planoConta: null,
                centroCusto: null,
                tipo: null,
                favorecido: null,
                numero: data.numero,
                numeroParcela: 0,
                dataCompetencia: data.dataCompetencia,
                dataEmissao: data.dataEmissao || moment(),
                dataVencimento: data.dataLancamento || moment(),
                dataLancamento: data.dataLancamento || moment(),
                dataCompensacao: null,
                valor: data.valor,      
                valorDesconto: 0,       
                valorJuros: 0,       
                valorMulta: 0,        
                situacao: (data && data.situacao) || $scope.situacoes[0],  
                modelo: modelo.id,
                historico: data.historico,
                observacao: data.observacao,
                lancamentoProgramado: null
            }
            if(conta) { lancamento.conta = { idConta: conta.idConta }; }
            if(planoConta) { lancamento.planoConta = { idPlanoConta: planoConta.idPlanoConta }; }
            if(centroCusto) { lancamento.centroCusto = { idCentroCusto: centroCusto.idCentroCusto }; }
            if(tipo) { lancamento.tipo = tipo.id; }
            lancamento.situacao = lancamento.situacao.id;
            return lancamento;
        }
        
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
            var modalInstance = ModalService.modalDefault('partials/financeiro/lancamento/modalLancamento.html', 'ModalLancamentoEditarController', 'lg',
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
        
        var modalExcluir = function() {
            var modalInstance = ModalService.modalExcluir(MESSAGES.lancamento.info.ALERT_EXCLUIR, MESSAGES.lancamento.info.CONFIRMAR_EXCLUIR);
            return modalInstance.result;
        };
        
        init();

    }]);
