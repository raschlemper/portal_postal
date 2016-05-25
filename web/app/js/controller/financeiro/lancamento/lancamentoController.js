'use strict';

app.controller('LancamentoController', ['$scope', '$filter', 'LancamentoService', 'LancamentoProgramadoService', 'LancamentoTransferenciaService', 'LancamentoConciliadoService', 'ContaService', 'PlanoContaService', 'CentroCustoService', 'ModalService', 'DatePickerService', 'ListaService', 'LISTAS',
    function ($scope, $filter, LancamentoService, LancamentoProgramadoService, LancamentoTransferenciaService, LancamentoConciliadoService, ContaService, PlanoContaService, CentroCustoService, ModalService, DatePickerService, ListaService, LISTAS) {

        var init = function () {
            $scope.lancamentos = [];
            $scope.lancamentosLista = [];     
            $scope.tipos = LISTAS.lancamento;
            $scope.modelos = LISTAS.modeloLancamento;
            $scope.situacoes = LISTAS.situacaoLancamento;
            $scope.datepickerDataInicio = angular.copy(DatePickerService.default);      
            $scope.datepickerDataFim = angular.copy(DatePickerService.default);    
            $scope.saldoTotal = 0;
            $scope.lancSearch = {};
            contas();
            initTipos();
            initTable();
        };  
        
        var initTipos = function() {       
            var modelo = angular.copy($scope.modelos[1]);
            var maxTipo = _.max($scope.tipos, function(tipo){ return tipo.id; });
            modelo.id = maxTipo.id + 1;
            $scope.tipos.push(modelo);
        }
        
        var initTable = function() {            
            $scope.colunas = [              
                {label: '', column: 'tipo.descricao', headerClass: 'no-sort', dataClass:'text-center col-tipo', filter: {name: 'tipoLancamento', args: 'z'}},         
                {label: '', column: 'situacao.descricao', headerClass: 'no-sort', dataClass:'text-center col-compensado', filter: {name: 'situacaoLancamento', args: ''}},         
                {label: '', column: 'numeroLoteConciliado', headerClass: 'no-sort', dataClass:'text-center col-reconciliado', filter: {name: 'conciliadoLancamento', args: ''}},         
                {label: 'Data', column: 'dataLancamento', dataClass: 'text-center cel-data', filter: {name: 'date', args: 'dd/MM/yy'}},                
                {label: 'Número', column: 'numero'},               
                {label: 'Plano Conta', column: 'planoConta'},         
                {label: 'Favorecido', column: 'favorecido'},                
                {label: 'Histórico', column: 'historico'},
                {label: 'Depósito', column: 'deposito', headerClass: 'no-sort', dataClass:'text-right', filter: {name: 'currency', args: ''}},  
                {label: 'Pagamento', column: 'pagamento', headerClass: 'no-sort', dataClass:'text-right', filter: {name: 'currency', args: ''}},  
                {label: 'Saldo', column: 'saldo', headerClass: 'no-sort', dataClass:'text-right', filter: {name: 'currency', args: ''}}
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
                                $scope.editar($scope.conta, lancamento.idLancamento);
                            });
                        } else {
                            $scope.editar($scope.conta, lancamento.idLancamento);
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
            return $filter('filter')(lista, search.tipo);
        };
        
        var filterByData = function(item, search) {
            if(!search.dataInicio || !search.dataFim) return true;
            var data = moment(item.dataPagamento);
            return (data.isBefore(search.dataFim) && data.isAfter(search.dataInicio) 
                    || (data.isSame(search.dataInicio) || data.isSame(search.dataFim)));
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
                    PlanoContaService.estrutura($scope.centroCustos);
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
                
                if(lancamento.tipo.id === $scope.tipos[1].id) { lancamento.pagamento = lancamento.valor * -1; } 
                else if(lancamento.tipo.id === $scope.tipos[0].id) { lancamento.deposito = lancamento.valor; }  
                
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
                    var modeloTransferencia = $scope.tipos[$scope.tipos.length - 1];
                    lancamento.tipo = modeloTransferencia;
                    lancamento.planoConta = modeloTransferencia.descricao;
                }
                
                return _.pick(lancamento, 'idLancamento', 'tipo', 'dataLancamento', 'numero', 'planoConta', 'favorecido', 'deposito', 'pagamento', 'saldo', 'historico', 'situacao', 'numeroLoteConciliado');
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
                         $scope.editar(conta, result);
                     })          
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        $scope.salvar = function(conta) {
            modalSalvar(conta).then(function(result) {
                result = ajustarDados(result);
                LancamentoService.save(result)
                    .then(function(data) {  
                        modalMessage("Lançamento Inserido com sucesso!");
                        todos(conta);
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            });
        };

        $scope.transferir = function(conta) {
            modalTransferir().then(function(result) {
                result = ajustarDadosTransferencia(result);
                LancamentoTransferenciaService.save(result)
                    .then(function(data) {  
                        modalMessage("Lançamento Transferido com sucesso!");
                        todos(conta);
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            });
        };

        $scope.conciliar = function(conta) {
            modalConciliar().then(function(result) {
                result = ajustarDadosConciliado(conta, result);
                LancamentoConciliadoService.create(result)
                    .then(function(data) {  
                        modalMessage("Lançamento Conciliado com sucesso!");
                        todos(conta);
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            });
        };

        $scope.compensar = function(conta, lancamentos) {
            modalConfirmarCompensado().then(function() {
                var lancamentosCompletos = [];
                _.map(lancamentos, function(lancamento) {
                    if(!lancamento.selected) return;
                    var lancamentoCompleto = ListaService.getLancamentoValue($scope.lancamentos, lancamento.idLancamento);
                    lancamentoCompleto.conta = conta;
                    lancamentoCompleto.situacao = $scope.situacoes[2];
                    lancamentoCompleto = ajustarDados(lancamentoCompleto);
                    lancamentosCompletos.push(lancamentoCompleto);
                });
                LancamentoService.updateAll(lancamentosCompletos)
                    .then(function (data) {  
                        modalMessage("Lançamento Compensado com sucesso!");
                        todos(conta);
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            });
        };

        $scope.editar = function(conta, idLancamento) {
            LancamentoService.get(idLancamento)
                .then(function(lancamento) {
                    modalSalvar(conta, lancamento).then(function(result) {
                        result = ajustarDados(result);
                        LancamentoService.update(idLancamento, result)
                            .then(function (data) {  
                                modalMessage("Lançamento Alterado com sucesso!");
                                todos(conta);
                            })
                            .catch(function(e) {
                                modalMessage(e);
                            });
                    });
                })
                .catch(function(e) {
                    modalMessage(e.error);
                });
           
        };

        $scope.excluir = function(conta, lancamento) {
            if(lancamento.lancamentoProgramado) {
                LancamentoProgramadoService.get(lancamento.lancamentoProgramado.idLancamentoProgramado)
                    .then(function(lancamento) {
                        if(lancamento.idLancamento !== lancamento.lancamentoProgramado.idLancamentoProgramado) {                
                            modalMessage("Este lançamento não pode ser excluído. É necessário excluir todos os lançamentos posteriores!");
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
                        modalMessage("Este lançamento não pode ser excluído. É necessário excluir todos os lançamentos posteriores!");
                        return;
                    }     
                    modalExcluir().then(function() {
                        LancamentoService.delete(idLancamento)
                            .then(function(data) { 
                                modalMessage("Lançamento Removido com sucesso!");
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
        
        var validaConciliado = function(idLancamento) {
            var lancamento = ListaService.getLancamentoValue($scope.lancamentos, idLancamento);
            if(lancamento.numeroLoteConciliado) { return true; }
            return false
        }
        
        var ajustarDados = function(data) {                 
            data.conta = { idConta: data.conta.idConta };       
            data.planoConta = { idPlanoConta: data.planoConta.idPlanoConta }; 
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
            return data;
        }
        
        var ajustarDadosTransferencia = function(data) {
            var modelo = $scope.modelos[1];
            var lancamentoTransferencia = { 
                idLancamentoTransferencia: null,
                lancamentoOrigem: getLancamento(data.contaOrigem, null, null, $scope.tipos[1], modelo, data),
                lancamentoDestino: getLancamento(data.contaDestino, null, null,  $scope.tipos[0], modelo, data),
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
                idLancamentoConciliado: null,
                lancamento: getLancamento(conta, data.planoConta, null, data.tipo, $scope.modelos[5], data),          
                conta: { idConta: conta.idConta },  
                planoConta: { idPlanoConta: data.planoConta.idPlanoConta },
                tipo: data.tipo.id,
                dataCompetencia: data.dataCompetencia,
                dataEmissao: data.dataEmissao || moment(),
                dataLancamento: data.dataLancamento || moment(),
                valor: data.valor,
                historico: data.historico
            };     
            return lancamentoConciliado;
        }
        
        var getLancamento = function(conta, planoConta, centroCusto, tipo, modelo, data) {
            var lancamento = {
                idLancamento: null,
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
            var modalInstance = ModalService.modalDefault('partials/financeiro/lancamento/modalVisualizarLancamento.html', 'ModalVisualizarLancamentoController', 'md',
                {
                    lancamento: function() {
                        return lancamento;
                    }
                });
            return modalInstance.result;
        };
        
        var modalSalvar = function(conta, lancamento) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/lancamento/modalEditarLancamento.html', 'ModalEditarLancamentoController', 'lg',
                {
                    lancamento: function() {
                        return lancamento;
                    },
                    conta: function() {
                        return conta;
                    }
                });
            return modalInstance.result;
        };
        
        var modalTransferir = function() {
            var modalInstance = ModalService.modalDefault('partials/financeiro/lancamento/modalLancamentoTransferencia.html', 'ModalLancamentoTransferenciaController', 'lg');
            return modalInstance.result;
        };
        
        var modalConciliar = function() {
            var modalInstance = ModalService.modalDefault('partials/financeiro/lancamento/modalLancamentoConciliado.html', 'ModalLancamentoConciliadoController', 'lg');
            return modalInstance.result;
        };
        
        var modalConfirmarConciliado = function() {
            var modalInstance = ModalService.modalConfirmar('Alerta Lançamento', 'Este lançamento está conciliado. <br/> As alterações poderão impactar no lançamento de conciliação! <br/> Deseja continuar?');
            return modalInstance.result;
        };
        
        var modalConfirmarCompensado = function() {
            var modalInstance = ModalService.modalConfirmar('Alerta Lançamento', 'Deseja compensar todos os lançamentos selecionados?');
            return modalInstance.result;
        };
        
        var modalExcluir = function() {
            var modalInstance = ModalService.modalExcluir('Excluir Lançamento?', 'Deseja realmente excluir este lançamento?');
            return modalInstance.result;
        };
        
        init();

    }]);
