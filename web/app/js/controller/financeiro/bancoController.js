'use strict';

app.controller('BancoController', ['$scope', 'BancoService', 'ModalService', 'DataTableService',
    function ($scope, BancoService, ModalService, DataTableService) {

        var init = function () {
            $scope.bancos = [];
            $scope.bancosLista = [];
            
            $scope.tableConfig = {
                currentPage: 1, 
                maxSize: 5, 
                limitTo: 10
            }
            
            $scope.predicate = 'numero';
            $scope.reverse = true;
        };     
        
        $scope.getStart = function() {
            if($scope.tableConfig.currentPage === 1) return 1;
            return (($scope.tableConfig.currentPage - 1) * $scope.tableConfig.limitTo) + 1;
        }  
        
        $scope.getFinish = function() {
            return $scope.getStart() + $scope.tableConfig.limitTo - 1;
        }
        
        $scope.order = function(predicate) {
            $scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse : false;
            $scope.predicate = predicate;
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

        $scope.visualizar = function(idBanco) {
            BancoService.get(idBanco)
                .then(function(banco) {
                     modalVisualizar(banco).then(function(result) {
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
                BancoService.save(result)
                    .then(function(data) {  
                        modalMessage("Banco Inserido " + data.nome +  " com sucesso!");
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
                        result = ajustarDados(result);
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
        
        var modalVisualizar = function(banco) {
            var modalInstance = ModalService.modalDefault('partials/banco/modalVisualizarBanco.html', 'ModalVisualizarBancoController', 'md',
                {
                    banco: function() {
                        return banco;
                    }
                });
            return modalInstance.result;
        };
        
        var modalSalvar = function(banco) {
            var modalInstance = ModalService.modalDefault('partials/banco/modalEditarBanco.html', 'ModalEditarBancoController', 'lg',
                {
                    banco: function() {
                        return banco;
                    }
                });
            return modalInstance.result;
        };
        
        var modalExcluir = function() {
            var modalInstance = ModalService.modalExcluir('Excluir Veículo?', 'Deseja realmente excluir este banco?');
            return modalInstance.result;
        };

        todos();
        init();

    }]);
