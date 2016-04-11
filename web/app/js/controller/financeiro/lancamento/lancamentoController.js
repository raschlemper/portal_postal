'use strict';

app.controller('LancamentoController', ['$scope', '$filter', 'LancamentoService', 'LancamentoTransferenciaService', 'ContaService', 'ModalService', 'DatePickerService', 'LISTAS',
    function ($scope, $filter, LancamentoService, LancamentoTransferenciaService, ContaService, ModalService, DatePickerService, LISTAS) {

        var init = function () {
            $scope.lancamentos = [];
            $scope.lancamentosLista = [];
            $scope.tipos = LISTAS.lancamento;
            $scope.datepickerDataInicio = angular.copy(DatePickerService.default);      
            $scope.datepickerDataFim = angular.copy(DatePickerService.default);    
            $scope.lancSearch = {};
            contas();
            initTable();      
        };  
        
        var initTable = function() {            
            $scope.colunas = [              
                {label: 'Tipo', column: 'tipo.descricao'},         
                {label: 'Data', column: 'data', filter: {name: 'date', args: 'dd/MM/yyyy'}},                
                {label: 'Número', column: 'numero'},               
                {label: 'Favorecido', column: 'favorecido'},                
                {label: 'Histórico', column: 'historico'},
                {label: 'Depósito', column: 'deposito', class: 'no-sort', filter: {name: 'currency', args: ''}},  
                {label: 'Pagamento', column: 'pagamento', class: 'no-sort', filter: {name: 'currency', args: ''}},  
                {label: 'Saldo', column: 'saldo', class: 'no-sort', filter: {name: 'currency', args: ''}}
            ]            
            $scope.events = { 
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
            };
        };
        
        $scope.filter = function(lista, search) {
            lista = _.filter(lista, function(item) {
                return filterByData(item, search);
            });              
            return $filter('filter')(lista, search.tipo);
        };
        
        var filterByData = function(item, search) {
            if(!search.dataInicio || !search.dataFim) return true;
            var data = moment(item.data);
            return (data.isBefore(search.dataFim) && data.isAfter(search.dataInicio) 
                    || (data.isSame(search.dataInicio) || data.isSame(search.dataFim)));
        };  
        
        var contas = function() {
            ContaService.getAll()
                .then(function (data) {
                    $scope.contas = data;
                    $scope.conta = $scope.contas[0];
                    todos($scope.conta);
//                    $scope.lancamento.conta = $scope.lancamento.conta || $scope.contas[0];
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
//            $scope.conta = conta;
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
                if(lancamento.tipo.codigo === 'despesa') { lancamento.deposito = lancamento.valor; } 
                else if(lancamento.tipo.codigo === 'receita') { lancamento.pagamento = lancamento.valor; }  
                return _.pick(lancamento, 'idLancamento', 'tipo', 'data', 'numero', 'favorecido', 'deposito', 'pagamento', 'saldo', 'historico');
            })
        };
        
        var calculateSaldo = function(lancamentos) {
            var saldo = 0;
            return _.map(lancamentos, function(lancamento) {
                if((lancamento.tipo && lancamento.tipo.codigo === 'despesa') || 
                        (lancamento.tipo.codigo === 'despesa')) { 
                    saldo += lancamento.deposito;
                } 
                else if((lancamento.tipo && lancamento.tipo.codigo === 'receita') || 
                        (lancamento.tipo.codigo === 'receita')) {
                    saldo -= lancamento.pagamento;
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

        $scope.excluir = function(conta, idLancamento) {
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
        
        var ajustarDados = function(data) {                 
            data.conta = { idConta: data.conta.idConta };       
            data.planoConta = { idPlanoConta: data.planoConta.idPlanoConta }; 
            data.tipo = data.tipo.id;
            data.situacao = data.situacao.id;
            return data;
        }
        
        var ajustarDadosTransferencia = function(data) {                 
            var lancamentoTransferencia = { 
                idLancamentoTransferencia: null,
                lancamentoOrigem: getLancamento(data.contaOrigem, data),
                lancamentoDestino: getLancamento(data.contaDestino, data)
            }; 
            lancamentoTransferencia.lancamentoOrigem.tipo = $scope.tipos[1].id;
            lancamentoTransferencia.lancamentoDestino.tipo = $scope.tipos[0].id;    
            return lancamentoTransferencia;
        }
        
        var getLancamento = function(conta, data) {
            return {
                idLancamento: null,
                planoConta: null,
                favorecido: null,
                numero: data.numero,
                data: data.data,
                valor: data.valor,       
                situacao: data.situacao.id,
                historico: data.historico,
                conta: { idConta: conta.idConta }
            }
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
        
        var modalTransferir = function(conta, lancamento) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/lancamento/modalLancamentoTransferencia.html', 'ModalLancamentoTransferenciaController', 'lg');
            return modalInstance.result;
        };
        
        var modalExcluir = function() {
            var modalInstance = ModalService.modalExcluir('Excluir Lançamento?', 'Deseja realmente excluir este lançamento?');
            return modalInstance.result;
        };
        
        init();

    }]);
