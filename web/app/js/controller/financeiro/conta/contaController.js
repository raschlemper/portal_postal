'use strict';

app.controller('ContaController', ['$scope', 'ContaService', 'ModalService',
    function ($scope, ContaService, ModalService) {

        var init = function () {
            $scope.contas = [];
            $scope.contasLista = [];
            $scope.collapsed = [];
            initTable();
        };  
        
        var initTable = function() {            
            $scope.colunas = [
                {label: 'Nome', column: 'nome'},
                {label: 'Tipo', column: 'tipo', class: 'col-md-4'},                
                {label: 'Status', column: 'status', class: 'col-md-4'}
            ]            
            $scope.events = { 
                view: function() {
                    
                },
                edit: function(conta) {
                    $scope.editar(conta.idBanco);
                },
                remove: function(conta) {
                    $scope.excluir(conta.idBanco);
                }
            }             
        };

        var todos = function() {
            ContaService.getAll()
                .then(function (data) {
                    $scope.contas = data;
                    $scope.contasLista = criarContasLista($scope.contas);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        var criarContasLista = function(contas) {
            return _.map(contas, function(conta) {
                return _.pick(conta, 'idConta', 'nome', 'tipo', 'status');
            })
        };

        $scope.visualizar = function(idConta) {
            ContaService.get(idConta)
                .then(function(conta) {
                     modalVisualizar(conta).then(function(result) {
                         $scope.editar(result);
                     })          
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        $scope.salvar = function() {
            modalSalvar().then(function(result) {
                ContaService.save(result)
                    .then(function(data) {  
                        modalMessage("Conta " + data.nome +  " Inserida com sucesso!");
                        todos();
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            });
        };

        $scope.editar = function(idConta) {
            ContaService.get(idConta)
                .then(function(conta) {
                     modalSalvar(conta).then(function(result) {
                        ContaService.update(idConta, result)
                            .then(function (data) {  
                                modalMessage("Conta " + data.nome + " Alterada com sucesso!");
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

        $scope.excluir = function(idConta) {
            modalExcluir().then(function() {
                ContaService.delete(idConta)
                    .then(function(data) { 
                        modalMessage("Conta " + data.nome + " Removida com sucesso!");
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
        
        var modalVisualizar = function(conta) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/conta/modalVisualizarConta.html', 'ModalVisualizarContaController', 'md',
                {
                    conta: function() {
                        return conta;
                    }
                });
            return modalInstance.result;
        };
        
        var modalSalvar = function(conta) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/conta/modalEditarConta.html', 'ModalEditarContaController', 'lg',
                {
                    conta: function() {
                        return conta;
                    }
                });
            return modalInstance.result;
        };
        
        var modalExcluir = function() {
            var modalInstance = ModalService.modalExcluir('Excluir Conta?', 'Deseja realmente excluir este conta?');
            return modalInstance.result;
        };

        todos();
        init();

    }]);
