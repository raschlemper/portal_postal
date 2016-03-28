'use strict';

app.controller('ContaController', ['$scope', 'ContaService', 'ModalService', 'LISTAS',
    function ($scope, ContaService, ModalService, LISTAS) {

        var init = function () {
            $scope.contas = [];
            $scope.contasLista = [];
            $scope.tipos = LISTAS.tipoConta;
            $scope.collapsed = [];
            criarTipoContaLista($scope.tipos);
        };  

        var todos = function() {
            ContaService.getAll()
                .then(function (data) {
                    $scope.contas = data;
                    $scope.contasLista = $scope.contas;
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        var criarTipoContaLista = function(tipos) {
            return _.map($scope.tipos, function(tipo) {
                $scope.collapsed[tipo.codigo] = true;
            })
        };
        
        $scope.existContas = function(contasLista, tipo) {
            var contas = $scope.getContas(contasLista, tipo);
            return (contas.length)
        }
        
        $scope.getContas = function(contasLista, tipo) {
            return _.filter(contasLista, function(conta) { 
                return conta.tipo.id === tipo.id; 
            });
        }
        
        $scope.salvar = function() {
            modalSalvar().then(function(result) {
                ContaService.save(result)
                    .then(function(data) {  
                        modalMessage("Conta " + data.nome +  " Inserido com sucesso!");
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
                                modalMessage("Conta " + data.nome + " Alterado com sucesso!");
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
                        modalMessage("Conta " + data.nome + " Removido com sucesso!");
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
        
        var modalSalvar = function(conta) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/modalEditarConta.html', 'ModalEditarContaController', 'lg',
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
