'use strict';

app.controller('LancamentoController', ['$scope', 'LancamentoService', 'ModalService',
    function ($scope, LancamentoService, ModalService) {

        var init = function () {
            $scope.lancamentos = [];
            $scope.lancamentosLista = [];
            initTable();      
        };  
        
        var initTable = function() {            
            $scope.colunas = [
                {label: 'Plano Conta', column: 'planoConta.nome'},
                {label: 'Tipo', column: 'planoConta.tipo.descricao'},                
                {label: 'Valor', column: 'valor', filter: {name: 'currency', args: 'R$ '}},                
                {label: 'Data', column: 'data', filter: {name: 'date', args: 'dd/MM/yyyy'}},                
                {label: 'Histórico', column: 'historico'}
            ]            
            $scope.events = { 
                edit: function(lancamento) {
                    $scope.editar(lancamento.idLancamento);
                },
                remove: function(lancamento) {
                    $scope.excluir(lancamento.idLancamento);
                }
            }             
        }

        var todos = function() {
            LancamentoService.getAll()
                .then(function (data) {
                    $scope.lancamentos = data;
                    $scope.lancamentosLista = criarLancamentosLista($scope.lancamentos);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        var criarLancamentosLista = function(lancamentos) {
            return _.map(lancamentos, function(lancamento) {
                lancamento.conta = lancamento.conta.idConta;
                delete lancamento.planoConta.codigo;
                delete lancamento.planoConta.grupo;
                delete lancamento.planoConta.contas;                
                return _.pick(lancamento, 'idLancamento', 'conta', 'planoConta', 'data', 'valor', 'historico');
            })
        }

        $scope.salvar = function() {
            modalSalvar().then(function(result) {
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

        $scope.editar = function(idLancamento) {
            LancamentoService.get(idLancamento)
                .then(function(lancamento) {
                     modalSalvar(lancamento).then(function(result) {
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
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };
        
        var modalSalvar = function(lancamento) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/lancamento/modalEditarLancamento.html', 'ModalEditarLancamentoController', 'lg',
                {
                    lancamento: function() {
                        return lancamento;
                    }
                });
            return modalInstance.result;
        };
        
        var modalExcluir = function() {
            var modalInstance = ModalService.modalExcluir('Excluir Lançamento?', 'Deseja realmente excluir este lançamento?');
            return modalInstance.result;
        };

        todos();
        init();

    }]);
