'use strict';

app.controller('ContaController', 
    ['$scope', '$q', 'ContaService', 'ModalService', 'ContaHandler', 'LISTAS', 'MESSAGES',
    function ($scope, $q, ContaService, ModalService, ContaHandler, LISTAS, MESSAGES) {

        var init = function () {
            $scope.tipos = LISTAS.tipoConta;
            $scope.contas = [];
            $scope.contasLista = [];
            $scope.collapsed = [];
            initTable();
            todos();
        };  

        // ***** TABLE ***** //
        
        var initTable = function() {            
            $scope.colunas = [
                {label: 'Nome', column: 'nome'},
                {label: 'Tipo', column: 'tipo.descricao', headerClass: 'col-md-2'},                
                {label: 'Status', column: 'status.descricao', headerClass: 'col-md-2'}
            ]            
            $scope.events = { 
                view: function(conta) {
                    $scope.visualizar(conta.idConta);                    
                },
                edit: function(conta) {
                    $scope.editar(conta.idConta);
                },
                remove: function(conta) {
                    $scope.excluir(conta.idConta);
                }
            }             
        };

        // ***** CONTROLLER ***** //

        var todos = function() {
            ContaService.getAll()
                .then(function (data) {
                    $scope.contas = data;
                    $scope.contasLista = criarContasLista($scope.contas);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        var criarContasLista = function(contas) {
            return _.map(contas, function(conta) {
                return _.pick(conta, 'idConta', 'nome', 'tipo', 'status');
            })
        };

        // ***** VISUALIZAR ***** //

        $scope.visualizar = function(idConta) {
            ContaService.get(idConta)
                .then(function(conta) {
                     visualizar(conta);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        var visualizar = function(conta) {
            modalVisualizar(conta)
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
                    result = ajustarDados(result);
                    save(result);
                });
        };
        
        var save = function(conta) {
            ContaService.save(conta)
                .then(function(data) {  
                    modalMessage("Conta " + data.nome +  " Inserida com sucesso!");
                    todos();
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        // ***** EDITAR ***** //

        $scope.editar = function(idConta) {
            ContaService.get(idConta)
                .then(function(conta) {
                    editar(conta);
                })
                .catch(function(e) {
                    modalMessage(e.error);
                });
           
        };

        var editar = function(conta) {
            modalSalvar(conta)
                .then(function(result) {
                    result = ajustarDados(result);
                    update(result);
                });           
        };

        var update = function(conta) {
            ContaService.update(conta.idConta, conta)
               .then(function (data) {  
                   modalMessage("Conta " + data.nome + " Alterada com sucesso!");
                   todos();
               })
               .catch(function(e) {
                   modalMessage(e);
               });      
        };

        // ***** EXCLUIR ***** //

        $scope.excluir = function(idConta) {
            $q.all([ContaService.getLancamento(idConta),
                    ContaService.getLancamentoProgramado(idConta)])
                .then(function(values) {   
                    if(values[0].lancamentos.length || values[1].lancamentosProgramados.length) {
                        modalMessage("Esta conta não pode ser excluída! <br/> Existem Lançamentos vinculados a esta conta.");
                    } else {
                        excluir(idConta);
                    }
                });
        }; 
        
        var excluir = function(idConta) {
            modalExcluir()
                .then(function() {
                    remove(idConta);
                });
        };
        
        var remove = function(idConta) {
            ContaService.delete(idConta)
                .then(function(data) { 
                    modalMessage("Conta " + data.nome + " Removida com sucesso!");
                    todos();                        
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        // ***** AJUSTAR ***** //
        
        var ajustarDados = function(data) {  
            return ContaHandler.handle(data);
        };

        // ***** MODAL ***** //
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };
        
        var modalVisualizar = function(conta) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/conta/modalContaVisualizar.html', 'ModalContaVisualizarController', 'md',
                {
                    conta: function() {
                        return conta;
                    }
                });
            return modalInstance.result;
        };
        
        var modalSalvar = function(conta) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/conta/modalContaEditar.html', 'ModalContaEditarController', 'lg',
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
        
        init();

    }]);
