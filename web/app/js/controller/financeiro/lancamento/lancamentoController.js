'use strict';

app.controller('LancamentoController', ['$scope', '$filter', 'LancamentoService', 'ContaService', 'ModalService', 'DatePickerService', 'LISTAS',
    function ($scope, $filter, LancamentoService, ContaService, ModalService, DatePickerService, LISTAS) {

        var init = function () {
            $scope.lancamentos = [];
            $scope.lancamentosLista = [];
            $scope.tipos = LISTAS.planoConta;
            $scope.datepickerDataInicio = angular.copy(DatePickerService.default);      
            $scope.datepickerDataFim = angular.copy(DatePickerService.default);    
            $scope.lancSearch = {};
            contas();
            initTable();      
        };  
        
        var initTable = function() {            
            $scope.colunas = [
                {label: 'Plano Conta', column: 'planoConta'},
                {label: 'Tipo', column: 'tipo'},                
                {label: 'Valor', column: 'valor', filter: {name: 'currency', args: 'R$ '}},                
                {label: 'Data', column: 'data', filter: {name: 'date', args: 'dd/MM/yyyy'}},                
                {label: 'Histórico', column: 'historico'}
            ]            
            $scope.events = { 
                edit: function(lancamento) {
                    $scope.editar($scope.conta, lancamento.idLancamento);
                },
                remove: function(lancamento) {
                    $scope.excluir(lancamento.idLancamento);
                },
                view: function(lancamento) {
                    $scope.visualizar(lancamento.idLancamento);
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
                lancamento.conta = data.idConta;
                lancamento.tipo = lancamento.planoConta.tipo.descricao;
                lancamento.planoConta = lancamento.planoConta.nome;
                return _.pick(lancamento, 'idLancamento', 'conta', 'tipo', 'planoConta', 'data', 'valor', 'historico');
            })
        };

        $scope.visualizar = function(idLancamento) {
            LancamentoService.get(idLancamento)
                .then(function(lancamento) {
                     modalVisualizar(lancamento).then(function(result) {
                         $scope.editar(result);
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
                        todos();
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            });
        };

        $scope.transferir = function(conta) {
            modalTransferir(conta).then(function(result) {
                result = ajustarDados(result);
                LancamentoService.save(result)
                    .then(function(data) {  
                        modalMessage("Lançamento Inserido com sucesso!");
                        todos();
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
                                todos();
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

        $scope.excluir = function(idLancamento) {
            modalExcluir().then(function() {
                LancamentoService.delete(idLancamento)
                    .then(function(data) { 
                        modalMessage("Lançamento Removido com sucesso!");
                        todos();                        
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            });
        }; 
        
        var ajustarDados = function(data) {                 
            data.conta = { idConta: data.conta.idConta };       
            data.planoConta = { idPlanoConta: data.planoConta.idPlanoConta }; 
            return data;
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
            var modalInstance = ModalService.modalDefault('partials/financeiro/lancamento/modalTransferirLancamento.html', 'ModalTransferirLancamentoController', 'lg',
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
        
        var modalExcluir = function() {
            var modalInstance = ModalService.modalExcluir('Excluir Lançamento?', 'Deseja realmente excluir este lançamento?');
            return modalInstance.result;
        };
        
        init();

    }]);
