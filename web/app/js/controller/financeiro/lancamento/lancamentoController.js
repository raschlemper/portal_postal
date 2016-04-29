'use strict';

app.controller('LancamentoController', ['$scope', '$filter', 'LancamentoService', 'LancamentoProgramadoService', 'LancamentoTransferenciaService', 'LancamentoConciliadoService', 'ContaService', 'ModalService', 'DatePickerService', 'LISTAS',
    function ($scope, $filter, LancamentoService, LancamentoProgramadoService, LancamentoTransferenciaService, LancamentoConciliadoService, ContaService, ModalService, DatePickerService, LISTAS) {

        var init = function () {
            $scope.lancamentos = [];
            $scope.lancamentosLista = [];
            $scope.tipos = LISTAS.lancamento;
            $scope.modelos = LISTAS.modeloLancamento;
            $scope.situacoes = LISTAS.situacaoLancamento;
            $scope.datepickerDataInicio = angular.copy(DatePickerService.default);      
            $scope.datepickerDataFim = angular.copy(DatePickerService.default);    
            $scope.lancSearch = {};
            contas();
            initTable();      
        };  
        
        var initTable = function() {            
            $scope.colunas = [              
                {label: 'Tipo', column: 'tipo.descricao'},         
                {label: 'Data', column: 'dataLancamento', filter: {name: 'date', args: 'dd/MM/yyyy'}},                
                {label: 'Número', column: 'numero'},               
                {label: 'Favorecido', column: 'favorecido'},                
                {label: 'Histórico', column: 'historico'},
                {label: 'Depósito', column: 'deposito', headerClass: 'no-sort', dataClass:'text-right', filter: {name: 'currency', args: ''}},  
                {label: 'Pagamento', column: 'pagamento', headerClass: 'no-sort', dataClass:'text-right', filter: {name: 'currency', args: ''}},  
                {label: 'Saldo', column: 'saldo', headerClass: 'no-sort', dataClass:'text-right', filter: {name: 'currency', args: ''}}
            ]            
            $scope.linha = {
                conditionalClass: function(item) {
                    if(item.deposito) return 'text-primary';
                    else if(item.pagamento) return 'text-danger';
                },
                events: { 
                    edit: function(lancamento) {
                        $scope.editar($scope.conta, lancamento.idLancamento);
                    },
                    remove: function(lancamento) {
                        $scope.excluir($scope.conta, lancamento.idLancamento);
                    },
                    view: function(lancamento) {
                        $scope.visualizar($scope.conta, lancamento.idLancamento);
                    },
                    table: function(lancamentos) {
                        calculateSaldo(lancamentos);
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
                    todos($scope.conta);
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        $scope.changeConta = function(conta) {
            $scope.conta = conta;
            todos(conta);
        }

        var todos = function(conta) {
            ContaService.getLancamento(conta.idConta)
                .then(function (data) {
                    $scope.lancamentos = data.lancamentos;
                    $scope.lancamentosLista = criarLancamentosLista(data);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        var criarLancamentosLista = function(data) {
            return _.map(data.lancamentos, function(lancamento) { 
                if(lancamento.tipo.codigo === 'despesa') { lancamento.pagamento = lancamento.valor * -1; } 
                else if(lancamento.tipo.codigo === 'receita') { lancamento.deposito = lancamento.valor; }  
                lancamento.numero = lancamento.numero + '-' + lancamento.numeroParcela;
                return _.pick(lancamento, 'idLancamento', 'tipo', 'dataLancamento', 'numero', 'favorecido', 'deposito', 'pagamento', 'saldo', 'historico');
            })
        };
        
        var calculateSaldo = function(lancamentos) {
            var saldo = 0;
            return _.map(lancamentos, function(lancamento) {
                if((lancamento.tipo && lancamento.tipo.codigo === 'despesa') || 
                        (lancamento.tipo.codigo === 'despesa')) { 
                    saldo += lancamento.pagamento;
                } 
                else if((lancamento.tipo && lancamento.tipo.codigo === 'receita') || 
                        (lancamento.tipo.codigo === 'receita')) {
                    saldo += lancamento.deposito;
                }
                lancamento.saldo = saldo;
            })            
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
                result = ajustarDadosConciliado(result);
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

        $scope.editar = function(conta, idLancamento) {
            if(!validaConciliado()) return;
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
                        excluir(lancamento.idLancamento);
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            } else {
                excluir(lancamento.idLancamento)
            }  
            
        }; 
        
        var excluir = function(idLancamento) {
            if(!validaConciliado()) return;
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
        };
        
        var validaConciliado = function(lancamento) {
            if(lancamento.lancamentoConciliado) {
                var modalInstance = modalConciliar();
                return modalInstance;
            }
            return false
        }
        
        var ajustarDados = function(data) {                 
            data.conta = { idConta: data.conta.idConta };       
            data.planoConta = { idPlanoConta: data.planoConta.idPlanoConta }; 
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
            return data;
        }
        
        var ajustarDadosTransferencia = function(data) {                 
            var lancamentoTransferencia = { 
                idLancamentoTransferencia: null,
                lancamentoOrigem: getLancamento(data.contaOrigem, data),
                lancamentoDestino: getLancamento(data.contaDestino, data),
                numero: data.numero,
                competencia: data.competencia,
                dataEmissao: data.dataEmissao || moment(),
                dataLancamento: data.dataLancamento || moment(),
                valor: data.valor,
                historico: data.historico
            }; 
            lancamentoTransferencia.lancamentoOrigem.tipo = $scope.tipos[1].id;
            lancamentoTransferencia.lancamentoDestino.tipo = $scope.tipos[0].id;    
            return lancamentoTransferencia;
        }
        
        var ajustarDadosConciliado = function(data) {                 
            var lancamentoConciliado = { 
                idLancamentoConciliado: null,
                tipo: data.tipo.id,
                lancamento: getLancamento(data.conta, data),
                competencia: data.competencia,
                dataEmissao: data.dataEmissao || moment(),
                dataLancamento: data.dataLancamento || moment(),
                valor: data.valor,
                historico: data.historico
            };     
            return lancamentoConciliado;
        }
        
        var getLancamento = function(conta, data) {
            var modelo = $scope.modelos[1];
            var lancamento = {
                idLancamento: null,
                planoConta: null,
                favorecido: null,
                numero: data.numero,
                competencia: data.competencia,
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
                observacao: null,
                lancamentoProgramado: null,
                conta: { idConta: conta.idConta },
            }
            lancamento.situacao = lancamento.situacao.id;
            lancamento.historico = '(' + modelo.descricao + ') ' + lancamento.historico;
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
        
        var modalConfirmar = function() {
            var modalInstance = ModalService.modalConfirmar('Alerta Lançamento?', 'Este lançamento está conciliado, <br/> as alterações poderão impactar no lançamento de conciliação! <br/> Deseja continuar?');
            return modalInstance.result;
        };
        
        var modalExcluir = function() {
            var modalInstance = ModalService.modalExcluir('Excluir Lançamento?', 'Deseja realmente excluir este lançamento?');
            return modalInstance.result;
        };
        
        init();

    }]);
