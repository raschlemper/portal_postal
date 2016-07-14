'use strict';

app.controller('FornecedorController', 
    ['$scope', '$q', 'FornecedorService', 'ModalService', 'FornecedorHandler', 'MESSAGES',
    function ($scope, $q, FornecedorService, ModalService, FornecedorHandler, MESSAGES) {

        var init = function () {
            $scope.fornecedores = [];
            $scope.fornecedoresLista = [];
            initTable();      
            todos();
        };  

        // ***** TABLE ***** //
        
        var initTable = function() {            
            $scope.colunas = [
                {label: 'Nome', column: 'nomeFantasia'},
                {label: 'Pessoa', column: 'tipoPessoa'},                         
                {label: 'Celular', column: 'celular', filter: {name: 'telefone', args: ''}},                         
                {label: 'Email', column: 'email'},                         
                {label: 'Situação', column: 'situacao'}
            ]            
            $scope.linha = {
                events: { 
                    edit: function(fornecedor) {
                        $scope.editar(fornecedor);
                    },
                    remove: function(fornecedor) {
                        $scope.excluir(fornecedor);
                    },
                    view: function(fornecedor) {
                        $scope.visualizar(fornecedor);
                    }
                }
            }             
        };

        // ***** CONTROLLER ***** //

        var todos = function() {
            FornecedorService.getAll()
                .then(function (data) {
                    $scope.fornecedores = data;
                    $scope.fornecedoresLista = criarFornecedorsLista($scope.fornecedores);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        var criarFornecedorsLista = function(fornecedores) {
            return _.map(fornecedores, function(fornecedor) {
                fornecedor.tipoPessoa = fornecedor.tipoPessoa.descricao;
                fornecedor.situacao = fornecedor.status.descricao;
                return _.pick(fornecedor, 'idFornecedor', 'nomeFantasia', 'tipoPessoa', 'celular', 'email', 'situacao');
            })
        };

        // ***** VISUALIZAR ***** //

        $scope.visualizar = function(fornecedor) {
            FornecedorService.get(fornecedor.idFornecedor)
                .then(function(result) {
                     visualizar(result);          
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        var visualizar = function(fornecedor) {
            modalVisualizar(fornecedor).then(function(result) {
                $scope.editar(result);
            })  
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

        var save = function(fornecedor) {
            FornecedorService.save(fornecedor)
                .then(function(data) {  
                    modalMessage("Fornecedor " + data.nomeFantasia +  " Inserido com sucesso!");
                    todos();
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        // ***** EDITAR ***** //

        $scope.editar = function(fornecedor) {
            FornecedorService.get(fornecedor.idFornecedor)
                .then(function(result) {
                     editar(result);
                })
                .catch(function(e) {
                    modalMessage(e.error);
                });           
        };

        var editar = function(fornecedor) {
            modalSalvar(fornecedor)
                .then(function(result) {
                    result = ajustarDados(result);
                    update(result);
                });
        };

        var update = function(fornecedor) {
            FornecedorService.update(fornecedor.idFornecedor, fornecedor)
                .then(function (data) {  
                    modalMessage("Fornecedor " + data.nomeFantasia + " Alterado com sucesso!");
                    todos();
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        // ***** EXCLUIR ***** //

        $scope.excluir = function(fornecedor) {
            $q.all([FornecedorService.getLancamento(fornecedor.idFornecedor),
                    FornecedorService.getLancamentoProgramado(fornecedor.idFornecedor)])
                .then(function(values) {   
                    if(values[0].lancamentos.length || values[1].lancamentosProgramados.length) {
                        modalMessage("Este fornecedor não pode ser excluído! <br/> Existem Lançamentos vinculados a esta fornecedor.");
                    } else {
                        excluir(fornecedor);
                    }
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        }; 
        
        var excluir = function(fornecedor) {
            modalExcluir()
                .then(function() {
                    remove(fornecedor);
                });
        };
        
        var remove = function(fornecedor) {
            FornecedorService.delete(fornecedor.idFornecedor)
                .then(function(data) { 
                    modalMessage("Fornecedor " + data.nomeFantasia + " Removido com sucesso!");
                    todos();                        
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        // ***** AJUSTAR ***** //
        
        var ajustarDados = function(data) {  
            var fornecedor = FornecedorHandler.handle(data);            
            if(!_.isEmpty(data.endereco)) {
                fornecedor.endereco = data.endereco;
            }
            return fornecedor;
        };

        // ***** MODAL ***** //
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };
        
        var modalVisualizar = function(fornecedor) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/fornecedor/modalFornecedorVisualizar.html', 'ModalFornecedorVisualizarController', 'md',
                {
                    fornecedor: function() {
                        return fornecedor;
                    }
                });
            return modalInstance.result;
        };
        
        var modalSalvar = function(fornecedor) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/fornecedor/modalFornecedorEditar.html', 'ModalFornecedorEditarController', 'lg',
                {
                    fornecedor: function() {
                        return fornecedor;
                    }
                });
            return modalInstance.result;
        };
        
        var modalExcluir = function() {
            var modalInstance = ModalService.modalExcluir('Excluir Fornecedor?', 'Deseja realmente excluir este Fornecedor?');
            return modalInstance.result;
        };
        
        init();

    }]);
