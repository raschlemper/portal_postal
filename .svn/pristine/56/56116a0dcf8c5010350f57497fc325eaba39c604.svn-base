'use strict';

app.controller('CartaoCreditoController', 
    ['$scope', 'CartaoCreditoService', 'ModalService', 'MESSAGES',
    function ($scope, CartaoCreditoService, ModalService, MESSAGES) {

        var init = function () {
            $scope.cartaoCreditos = [];
            $scope.cartaoCreditosLista = [];
            initTable();      
            todos();
        };  

        // ***** TABLE ***** //
        
        var initTable = function() {            
            $scope.colunas = [
                {label: 'Nome', column: 'nome'},
                {label: 'Bandeira', column: 'bandeira', headerClass: 'col-md-3', filter: {name: 'uppercase'}},                         
                {label: 'Limite', column: 'valorLimiteCredito', headerClass: 'col-md-2', filter: {name: 'currency', args: 'R$ '}}
            ]            
            $scope.linha = {
                events: { 
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
        };

        // ***** CONTROLLER ***** //

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

        // ***** VISUALIZAR ***** //

        $scope.visualizar = function(idCartaoCredito) {
            CartaoCreditoService.get(idCartaoCredito)
                .then(function(cartaoCredito) {
                     visualizar(cartaoCredito);        
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        var visualizar = function(cartaoCredito) {
            modalVisualizar(cartaoCredito)
                .then(function(result) {
                    $scope.editar(result);
                });
        };

        // ***** SALVAR ***** //

        $scope.salvar = function() {
            salvar();
        };

        var salvar = function() {
            modalSalvar()
                .then(function(result) {
                    save(result);
                });
        };
        
        var save = function(cartaoCredito) {
            CartaoCreditoService.save(cartaoCredito)
                .then(function(data) {  
                    modalMessage("Cartão de Crédito " + data.nome +  " Inserido com sucesso!");
                    todos();
                })
                .catch(function(e) {
                    modalMessage(e);
                });            
        };

        // ***** EDITAR ***** //

        $scope.editar = function(idCartaoCredito) {
            CartaoCreditoService.get(idCartaoCredito)
                .then(function(cartaoCredito) {
                    editar(cartaoCredito);
                })
                .catch(function(e) {
                    modalMessage(e.error);
                });           
        };

        var editar = function(cartaoCredito) {
            modalSalvar(cartaoCredito)
                .then(function(result) {
                    update(result);
                });           
        };

        var update = function(cartaoCredito) {
            CartaoCreditoService.update(cartaoCredito.idCartaoCredito, cartaoCredito)
                .then(function (data) {  
                    modalMessage("Cartão de Crédito " + data.nome + " Alterado com sucesso!");
                    todos();
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        // ***** EXCLUIR ***** //

        $scope.excluir = function(idCartaoCredito) {
            CartaoCreditoService.getConta(idCartaoCredito)
                .then(function(cartaoCredito) {   
                    if(cartaoCredito.contas.length) {
                        modalMessage("Este cartão crédito não pode ser excluído! <br/> Existem Contas vinculadas a este cartão de crédito.");
                    } else {
                        excluir(idCartaoCredito);
                    }
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        }; 
        
        var excluir = function(idCartaoCredito) {
            modalExcluir()
                .then(function() {
                    remove(idCartaoCredito);
                });            
        };
        
        var remove = function(idCartaoCredito) {
            CartaoCreditoService.delete(idCartaoCredito)
                .then(function(data) { 
                    modalMessage("Cartão de Crédito " + data.nome + " Removido com sucesso!");
                    todos();                        
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        // ***** MODAL ***** //
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };
        
        var modalVisualizar = function(cartaoCredito) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/cartaoCredito/modalCartaoCreditoVisualizar.html', 'ModalCartaoCreditoVisualizarController', 'md',
                {
                    cartaoCredito: function() {
                        return cartaoCredito;
                    }
                });
            return modalInstance.result;
        };
        
        var modalSalvar = function(cartaoCredito) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/cartaoCredito/modalCartaoCreditoEditar.html', 'ModalCartaoCreditoEditarController', 'lg',
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

        init();

    }]);
