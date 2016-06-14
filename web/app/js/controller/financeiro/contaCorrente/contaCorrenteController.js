'use strict';

app.controller('ContaCorrenteController', 
    ['$scope', '$filter', '$q', 'ContaCorrenteService', 'ModalService', 'ContaCorrenteHandler', 'MESSAGES',
    function ($scope, $filter, $q, ContaCorrenteService, ModalService, ContaCorrenteHandler, MESSAGES) {

        var init = function () {
            $scope.contaCorrentes = [];
            $scope.contaCorrentesLista = [];
            initTable(); 
            todos();
        };  

        // ***** TABLE ***** //   
        
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
        };

        // ***** CONTROLLER ***** //

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

        // ***** VISUALIZAR ***** //

        $scope.visualizar = function(idContaCorrente) {
            ContaCorrenteService.get(idContaCorrente)
                .then(function(contaCorrente) {
                    visualizar(contaCorrente);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        var visualizar = function(contaCorrente) {
            modalVisualizar(contaCorrente).then(function(result) {
                $scope.editar(result);
            });
        };

        // ***** SALVAR ***** //

        $scope.salvar = function() {
            modalSalvar()
                .then(function(result) {
                    result = ajustarDados(result);
                    salvar(result);
                });
        };
        
        var salvar = function(contaCorrente) {
            ContaCorrenteService.save(contaCorrente)
                .then(function(data) {  
                    modalMessage("Conta Corrente " + data.nome +  " Inserida com sucesso!");
                    todos();
                })
                .catch(function(e) {
                    modalMessage(e);
                });
            
        };

        // ***** EDITAR ***** //

        $scope.editar = function(idContaCorrente) {
            ContaCorrenteService.get(idContaCorrente)
                .then(function(contaCorrente) {
                    editar(contaCorrente);
                })
                .catch(function(e) {
                    modalMessage(e.error);
                });
           
        };
        
        var editar = function(contaCorrente) {
            modalSalvar(contaCorrente).then(function(result) {
                result = ajustarDados(result);
                update(contaCorrente);
            });            
        };
        
        var update = function(contaCorrente) {
            ContaCorrenteService.update(contaCorrente.idContaCorrente, contaCorrente)
                .then(function (data) {  
                    modalMessage("Conta Corrente " + data.nome + " Alterada com sucesso!");
                    todos();
                })
                .catch(function(e) {
                    modalMessage(e);
                });  
        };

        // ***** EXCLUIR ***** //

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
            modalExcluir()
                .then(function() {
                    remove(idContaCorrente);
                });            
        };
        
        var remove = function(idContaCorrente) {
            ContaCorrenteService.delete(idContaCorrente)
                .then(function(data) { 
                    modalMessage("Conta Corrente " + data.nome + " Removida com sucesso!");
                    todos();                        
                })
                .catch(function(e) {
                    modalMessage(e);
                });  
        };

        // ***** AJUSTAR ***** //
        
        var ajustarDados = function(data) {  
            return ContaCorrenteHandler.handle(data);
        };

        // ***** MODAL ***** //
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };
        
        var modalVisualizar = function(contaCorrente) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/contaCorrente/modalContaCorrenteVisualizar.html', 'ModalContaCorrenteVisualizarController', 'md',
                {
                    contaCorrente: function() {
                        return contaCorrente;
                    }
                });
            return modalInstance.result;
        };
        
        var modalSalvar = function(contaCorrente) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/contaCorrente/modalContaCorrenteEditar.html', 'ModalContaCorrenteEditarController', 'lg',
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
        
        init();

    }]);
