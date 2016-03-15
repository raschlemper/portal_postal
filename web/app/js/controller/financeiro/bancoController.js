'use strict';

app.controller('BancoController', ['$scope', 'BancoService', 'ModalService',
    function ($scope, BancoService, ModalService) {

        var init = function () {
            $scope.bancos = [];
            $scope.bancosLista = [];
            
            $scope.colunas = [
                {label: 'Número', column: 'numero'},
                {label: 'Nome', column: 'nome', filter: 'uppercase'},                
                {label: 'Website', column: 'website'}
            ]
            
            $scope.events = { 
                edit: function(banco) {
                    $scope.editar(banco.idBanco);
                },
                remove: function(banco) {
                    $scope.excluir(banco.idBanco);
                }
            }         
        };  

        var todos = function() {
            BancoService.getAll()
                .then(function (data) {
                    $scope.bancos = data;
                    $scope.bancosLista = criarBancosLista($scope.bancos);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        var criarBancosLista = function(bancos) {
            return _.map(bancos, function(banco) {
                return _.pick(banco, 'idBanco', 'numero', 'nome', 'website');
            })
        }

        $scope.salvar = function() {
            modalSalvar().then(function(result) {
                BancoService.save(result)
                    .then(function(data) {  
                        modalMessage("Banco " + data.nome +  " Inserido com sucesso!");
                        todos();
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            });
        };

        $scope.editar = function(idBanco) {
            BancoService.get(idBanco)
                .then(function(banco) {
                     modalSalvar(banco).then(function(result) {
                        BancoService.update(idBanco, result)
                            .then(function (data) {  
                                modalMessage("Banco " + data.nome + " Alterado com sucesso!");
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

        $scope.excluir = function(idBanco) {
            modalExcluir().then(function() {
                BancoService.delete(idBanco)
                    .then(function(data) { 
                        modalMessage("Banco " + data.nome + " Removido com sucesso!");
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
        
        var modalSalvar = function(banco) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/modalEditarBanco.html', 'ModalEditarBancoController', 'lg',
                {
                    banco: function() {
                        return banco;
                    }
                });
            return modalInstance.result;
        };
        
        var modalExcluir = function() {
            var modalInstance = ModalService.modalExcluir('Excluir Banco?', 'Deseja realmente excluir este banco?');
            return modalInstance.result;
        };

        todos();
        init();

    }]);
