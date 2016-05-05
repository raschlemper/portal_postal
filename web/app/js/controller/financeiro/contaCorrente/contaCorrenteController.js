'use strict';

app.controller('ContaCorrenteController', ['$scope', '$filter', '$q', 'ContaCorrenteService', 'ModalService',
    function ($scope, $filter, $q, ContaCorrenteService, ModalService) {

        var init = function () {
            $scope.contaCorrentes = [];
            $scope.contaCorrentesLista = [];
            initTable();      
        };  
        
        var initTable = function() {            
            $scope.colunas = [
                {label: 'Nome', column: 'nome'},
                {label: 'Banco', column: 'banco', headerClass: 'col-md-3'},                         
                {label: 'Agência', column: 'agencia', headerClass: 'col-md-2'},                         
                {label: 'Conta Corrente', column: 'contaCorrente', class: 'col-md-2'}
            ]            
            $scope.linha = {
                events: { 
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
                contaCorrente.banco = contaCorrente.banco.nome;
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
            $q.all([ContaCorrenteService.getCartaoCredito(idContaCorrente),
                    ContaCorrenteService.getCarteiraCobranca(idContaCorrente),
                    ContaCorrenteService.getConta(idContaCorrente)])
                .then(function(values) {   
                    if(values[0].cartaoCreditos.length) {
                        modalMessage("Esta conta corrente não pode ser excluída! <br/> Existem Cartões de Crédito vinculados a esta conta corrente.");
                    } else if(values[1].carteiraCobrancas.length) {
                        modalMessage("Esta conta corrente não pode ser excluída! <br/> Existem Carteiras de Cobrança vinculadas a esta conta corrente.");
                    } else if(values[2].contas.length) {
                        modalMessage("Esta conta corrente não pode ser excluída! <br/> Existem Contas vinculadas a esta conta corrente.");
                    } else {
                        excluir(idContaCorrente);
                    }
                });
        }; 
        
        var excluir = function(idContaCorrente) {
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
        }
        
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
