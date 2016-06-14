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
                        $scope.editar(banco.idBanco);
                    },
                    remove: function(banco) {
                        $scope.excluir(banco.idBanco);
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

        $scope.editar = function(idBanco) {
            BancoService.get(idBanco)
                .then(function(banco) {
                    editar(banco);
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
            BancoService.update(banco.idBanco, result)
                .then(function (data) {  
                    modalMessage("Banco " + data.nome + " Alterado com sucesso!");
                    todos();
                })
                .catch(function(e) {
                    modalMessage(e);
                });            
        };

        // ***** EXCLUIR ***** //

        $scope.excluir = function(idBanco) {
            BancoService.getContaCorrente(idBanco)
                .then(function(banco) {   
                    if(banco.contaCorrentes.length) {
                        modalMessage("Este banco não pode ser excluído! <br/> Existem Contas Correntes vinculadas a este banco.");
                    } else {
                        excluir(idBanco);
                    }
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        }; 
        
        var excluir = function(idBanco) {
            modalExcluir()
                .then(function() {
                    remove(idBanco);
                });
        };
        
        var remove = function(idBanco) {
            BancoService.delete(idBanco)
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
