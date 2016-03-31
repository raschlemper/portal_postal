'use strict';

app.controller('ContaController', ['$scope', 'ContaService', 'ModalService', 'LISTAS',
    function ($scope, ContaService, ModalService, LISTAS) {

        var init = function () {
            $scope.tipos = LISTAS.tipoConta;
            $scope.contas = [];
            $scope.contasLista = [];
            $scope.collapsed = [];
            initTable();
        };  
        
        var initTable = function() {            
            $scope.colunas = [
                {label: 'Nome', column: 'nome'},
                {label: 'Tipo', column: 'tipo.descricao', class: 'col-md-2'},                
                {label: 'Status', column: 'status.descricao', class: 'col-md-2'}
            ]            
            $scope.events = { 
                view: function(conta) {
                    $scope.visualizar(conta.idConta);                    
                },
                edit: function(conta) {
                    $scope.editar(conta.idConta);
                },
                remove: function(conta) {
                    $scope.excluir(conta.idConta);
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
                result = ajustarDados(result);
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
                        result = ajustarDados(result);
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
        
        var ajustarDados = function(data) {            
            data.tipo = data.tipo.id;
            data.status = data.status.id;
            return data;
        }
        
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
