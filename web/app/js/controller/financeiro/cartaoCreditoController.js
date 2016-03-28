'use strict';

app.controller('CartaoCreditoController', ['$scope', 'CartaoCreditoService', 'ModalService',
    function ($scope, CartaoCreditoService, ModalService) {

        var init = function () {
            $scope.cartaoCreditos = [];
            $scope.cartaoCreditosLista = [];
            initTable();      
        };  
        
        var initTable = function() {            
            $scope.colunas = [
                {label: 'Nome', column: 'nome'},
                {label: 'Bandeira', column: 'bandeira', class: 'col-md-3', filter: {name: 'uppercase'}},                         
                {label: 'Limite', column: 'valorLimiteCredito', class: 'col-md-2', filter: {name: 'currency', args: 'R$ '}}
            ]            
            $scope.events = { 
                edit: function(cartaoCredito) {
                    $scope.editar(cartaoCredito.idCartaoCredito);
                },
                remove: function(cartaoCredito) {
                    $scope.excluir(cartaoCredito.idCartaoCredito);
                },
                view: function(cartaoCredito) {
                    $scope.visualizar(cartaoCredito.idCartaoCredito);
                }
            }             
        }

        var todos = function() {
            CartaoCreditoService.getAll()
                .then(function (data) {
                    $scope.cartaoCreditos = data;
                    $scope.cartaoCreditosLista = criarCartaoCreditosLista($scope.cartaoCreditos);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        var criarCartaoCreditosLista = function(cartaoCreditos) {
            return _.map(cartaoCreditos, function(cartaoCredito) {
                return _.pick(cartaoCredito, 'idCartaoCredito', 'nome', 'bandeira', 'valorLimiteCredito');
            })
        };

        $scope.visualizar = function(idCartaoCredito) {
            CartaoCreditoService.get(idCartaoCredito)
                .then(function(cartaoCredito) {
                     modalVisualizar(cartaoCredito).then(function(result) {
                         $scope.editar(result);
                     })          
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        $scope.salvar = function() {
            modalSalvar().then(function(result) {
                CartaoCreditoService.save(result)
                    .then(function(data) {  
                        modalMessage("Cartão de Crédito " + data.nome +  " Inserido com sucesso!");
                        todos();
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            });
        };

        $scope.editar = function(idCartaoCredito) {
            CartaoCreditoService.get(idCartaoCredito)
                .then(function(cartaoCredito) {
                     modalSalvar(cartaoCredito).then(function(result) {
                        CartaoCreditoService.update(idCartaoCredito, result)
                            .then(function (data) {  
                                modalMessage("Cartão de Crédito " + data.nome + " Alterado com sucesso!");
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

        $scope.excluir = function(idCartaoCredito) {
            modalExcluir().then(function() {
                CartaoCreditoService.delete(idCartaoCredito)
                    .then(function(data) { 
                        modalMessage("Cartão de Crédito " + data.nome + " Removido com sucesso!");
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
        
        var modalVisualizar = function(cartaoCredito) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/modalVisualizarCartaoCredito.html', 'ModalVisualizarCartaoCreditoController', 'md',
                {
                    cartaoCredito: function() {
                        return cartaoCredito;
                    }
                });
            return modalInstance.result;
        };
        
        var modalSalvar = function(cartaoCredito) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/modalEditarCartaoCredito.html', 'ModalEditarCartaoCreditoController', 'lg',
                {
                    cartaoCredito: function() {
                        return cartaoCredito;
                    }
                });
            return modalInstance.result;
        };
        
        var modalExcluir = function() {
            var modalInstance = ModalService.modalExcluir('Excluir CartaoCredito?', 'Deseja realmente excluir este Cartão de Crédito?');
            return modalInstance.result;
        };

        todos();
        init();

    }]);
