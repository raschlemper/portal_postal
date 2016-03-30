'use strict';

app.controller('ContaCorrenteController', ['$scope', '$filter', 'ContaCorrenteService', 'ModalService',
    function ($scope, $filter, ContaCorrenteService, ModalService) {

        var init = function () {
            $scope.contaCorrentes = [];
            $scope.contaCorrentesLista = [];
            initTable();      
        };  
        
        var initTable = function() {            
            $scope.colunas = [
                {label: 'Nome', column: 'nome'},
                {label: 'Banco', column: 'banco.nome', class: 'col-md-3'},                         
                {label: 'Agência', column: 'agencia', class: 'col-md-2'},                         
                {label: 'Conta Corrente', column: 'contaCorrente', class: 'col-md-2'}
            ]            
            $scope.events = { 
                edit: function(contaCorrente) {
                    $scope.editar(contaCorrente.idContaCorrente);
                },
                remove: function(contaCorrente) {
                    $scope.excluir(contaCorrente.idContaCorrente);
                },
                view: function(contaCorrente) {
                    $scope.visualizar(contaCorrente.idContaCorrente);
                }
            }             
        }

        var todos = function() {
            ContaCorrenteService.getAll()
                .then(function (data) {
                    $scope.contaCorrentes = data;
                    $scope.contaCorrentesLista = criarContaCorrentesLista($scope.contaCorrentes);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        var criarContaCorrentesLista = function(contaCorrentes) {
            return _.map(contaCorrentes, function(contaCorrente) {
                contaCorrente.agencia = $filter('number')(contaCorrente.agencia) + '-' + contaCorrente.agenciaDv;
                contaCorrente.contaCorrente = $filter('number')(contaCorrente.contaCorrente) + '-' + contaCorrente.contaCorrenteDv;
                return _.pick(contaCorrente, 'idContaCorrente', 'nome', 'banco', 'agencia', 'contaCorrente');
            })
        };

        $scope.visualizar = function(idContaCorrente) {
            ContaCorrenteService.get(idContaCorrente)
                .then(function(contaCorrente) {
                     modalVisualizar(contaCorrente).then(function(result) {
                         $scope.editar(result);
                     })          
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        $scope.salvar = function() {
            modalSalvar().then(function(result) {
                ContaCorrenteService.save(result)
                    .then(function(data) {  
                        modalMessage("Conta Corrente " + data.nome +  " Inserida com sucesso!");
                        todos();
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            });
        };

        $scope.editar = function(idContaCorrente) {
            ContaCorrenteService.get(idContaCorrente)
                .then(function(contaCorrente) {
                     modalSalvar(contaCorrente).then(function(result) {
                        ContaCorrenteService.update(idContaCorrente, result)
                            .then(function (data) {  
                                modalMessage("Conta Corrente " + data.nome + " Alterada com sucesso!");
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

        $scope.excluir = function(idContaCorrente) {
            modalExcluir().then(function() {
                ContaCorrenteService.delete(idContaCorrente)
                    .then(function(data) { 
                        modalMessage("Conta Corrente " + data.nome + " Removida com sucesso!");
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
        
        var modalVisualizar = function(contaCorrente) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/contaCorrente/modalVisualizarContaCorrente.html', 'ModalVisualizarContaCorrenteController', 'md',
                {
                    contaCorrente: function() {
                        return contaCorrente;
                    }
                });
            return modalInstance.result;
        };
        
        var modalSalvar = function(contaCorrente) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/contaCorrente/modalEditarContaCorrente.html', 'ModalEditarContaCorrenteController', 'lg',
                {
                    contaCorrente: function() {
                        return contaCorrente;
                    }
                });
            return modalInstance.result;
        };
        
        var modalExcluir = function() {
            var modalInstance = ModalService.modalExcluir('Excluir Conta Corrente?', 'Deseja realmente excluir este Conta Corrente?');
            return modalInstance.result;
        };

        todos();
        init();

    }]);
