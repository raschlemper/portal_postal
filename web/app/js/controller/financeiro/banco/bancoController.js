'use strict';

app.controller('BancoController', 
    ['$scope', 'BancoService', 'ModalService', 'MESSAGES',
    function ($scope, BancoService, ModalService, MESSAGES) {

        var init = function () {
            $scope.bancos = [];
            $scope.bancosLista = [];
            initTable();      
            todos();
        };  

        // ***** TABLE ***** //
        
        var initTable = function() {            
            $scope.colunas = [
                {label: 'Número', column: 'numero'},
                {label: 'Nome', column: 'nome', headerClass: 'col-md-4', filter: {name: 'uppercase', args: null}},                
                {label: 'Website', column: 'website', headerClass: 'col-md-4'}
            ]            
            $scope.linha = {
                events: { 
                    edit: function(banco) {
                        $scope.editar(banco);
                    },
                    remove: function(banco) {
                        $scope.excluir(banco);
                    }
                }   
            }          
        };

        // ***** CONTROLLER ***** //

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
        
        var save = function(banco) {
            BancoService.save(banco)
                .then(function(data) {  
                    modalMessage("Banco " + data.nome +  " Inserido com sucesso!");
                    todos();
                })
                .catch(function(e) {
                    modalMessage(e);
                });            
        };

        // ***** EDITAR ***** //

        $scope.editar = function(banco) {
            BancoService.get(banco.idBanco)
                .then(function(result) {
                    editar(result);
                })
                .catch(function(e) {
                    modalMessage(e.error);
                });
           
        };

        var editar = function(banco) {
            modalSalvar(banco)
                .then(function(result) {
                    update(result);
                });            
        };
        
        var update = function(banco) {
            BancoService.update(banco.idBanco, banco)
                .then(function (data) {  
                    modalMessage("Banco " + data.nome + " Alterado com sucesso!");
                    todos();
                })
                .catch(function(e) {
                    modalMessage(e);
                });            
        };

        // ***** EXCLUIR ***** //

        $scope.excluir = function(banco) {
            BancoService.getContaCorrente(banco.idBanco)
                .then(function(result) {   
                    if(result.contaCorrentes.length) {
                        modalMessage("Este banco não pode ser excluído! <br/> Existem Contas Correntes vinculadas a este banco.");
                    } else {
                        excluir(banco);
                    }
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        }; 
        
        var excluir = function(banco) {
            modalExcluir()
                .then(function() {
                    remove(banco);
                });
        };
        
        var remove = function(banco) {
            BancoService.delete(banco.idBanco)
                .then(function(data) { 
                    modalMessage("Banco " + data.nome + " Removido com sucesso!");
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
        
        var modalSalvar = function(banco) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/banco/modalBancoEditar.html', 'ModalBancoEditarController', 'lg',
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
        
        init();

    }]);
